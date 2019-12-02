package com.example.demo.service;

import com.example.demo.domain.GrantToken;
import com.example.demo.domain.TokenResponse;
import com.example.demo.domain.playlist.*;
import com.example.demo.domain.trackremoved.TrackRemoved;
import com.example.demo.domain.tracksadded.TracksAdded;
import com.example.demo.repository.TokenResponseRepository;
import com.example.demo.repository.TracksAddedRepository;
import com.example.demo.repository.TracksRemovedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class SpotifyServiceImpl implements SpotifyService {

    @Value("${api.spotify.token}")
    String urlToken;


    @Value("${client.id}")
    String clientId;


    @Value("${client.secret}")
    String clientSecret;

    @Value("${redirect.path}")
    String redirectUrl;

    @Value("${playlists.path}")
    String playlistUrl;

    @Value("${playlists.add.track}")
    String playlistUrlAddTrack;

    @Value("${playlist.single.path}")
    String singlePlaylistUrl;

    TokenResponseRepository tokenResponseRepository;

    TracksAddedRepository tracksAddedRepository;

    TracksRemovedRepository tracksRemovedRepository;

    @Autowired
    SpotifyServiceImpl(TokenResponseRepository tokenResponseRepository,
                       TracksAddedRepository tracksAddedRepository,
                       TracksRemovedRepository tracksRemovedRepository) {
        this.tokenResponseRepository = tokenResponseRepository;
        this.tracksAddedRepository = tracksAddedRepository;
        this.tracksRemovedRepository = tracksRemovedRepository;
    }


    /**
     * @param code Codigo gerado pelo Spotify para pegar o Token
     * @return ID da entidade salva com os dados do Token
     * @throws Exception
     */
    public TokenResponse getToken(String code) throws Exception {

        GrantToken grantToken = new GrantToken();
        grantToken.setCode(code);
        grantToken.setRedirect_uri(redirectUrl);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(clientId, clientSecret));


        HttpEntity<MultiValueMap<String, String>> entity = buildRequestToken(grantToken);

        ResponseEntity<TokenResponse> response = restTemplate.exchange(urlToken, HttpMethod.POST, entity, TokenResponse.class);

        TokenResponse tokenResponse;
        if (response.getStatusCode().value() == 200) {
            tokenResponse = tokenResponseRepository.save(Objects.requireNonNull(response.getBody()));
        } else {
            throw new Exception("Request failed");
        }
        return tokenResponse;
    }


    /**
     * Metood para pegar todas as playlists do usuario que deu authorizacao
     *
     * @param id ID retorando pelo metodo getToken.  Esse ID busca no banco a entidade salva para usar seu token
     * @return Lista com as Playlist
     */
    public PlaylistResponse getPlaylists(Long id) {
        TokenResponse tokenResponse = tokenResponseRepository.getOne(id);

        HttpHeaders headers = createHeadderBearer(tokenResponse.getAccess_token());

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(playlistUrl, HttpMethod.GET, entity, PlaylistResponse.class).getBody();
    }


    /**
     * Metodo para adicionar musicas
     *
     * @param playlistID ID fornecido pelo Spotify de uma Playlist
     * @param tracks     Lista de Musicas a serem adicionadas
     * @param id         ID da entidade para pegar o token
     * @return Sucesso ou Falha
     */
    public String addTracks(String playlistID, AddTrack tracks, Long id) {
        TokenResponse tokenResponse = tokenResponseRepository.getOne(id);

        HttpHeaders headers = createHeadderBearer(tokenResponse.getAccess_token());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AddTrack> entity = new HttpEntity<>(tracks, headers);

        RestTemplate restTemplate = new RestTemplate();

        String url = playlistUrlAddTrack.replace("{playlist_id}", playlistID);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().value() == 200) {
            for (String track : tracks.getUris()) {
                TracksAdded added = new TracksAdded();
                added.setPlaylistId(playlistID);
                added.setTrackUri(track);

                tracksAddedRepository.save(added);
            }

            return "Musica Adicionada com sucesso";
        } else {
            return "Falha ao adicionar Musica";
        }
    }

    /**
     * Metodo para busca de uma playlist
     *
     * @param id         ID para buscar o Token de acesso
     * @param playlistID Playlist ID Fornecido pelo Spotify
     * @return Detalhes de uma playlist
     */
    public SinglePlaylist getPlaylist(Long id, String playlistID) {
        TokenResponse tokenResponse = tokenResponseRepository.getOne(id);

        HttpHeaders headers = createHeadderBearer(tokenResponse.getAccess_token());

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        String url = singlePlaylistUrl.replace("{playlist_id}", playlistID);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(url, HttpMethod.GET, entity, SinglePlaylist.class).getBody();
    }

    /**
     * Metodo para remover musicas
     *
     * @param playlistID ID fornecido pelo Spotify de uma Playlist
     * @param tracks     Lista de Musicas a serem adicionadas
     * @param id         ID da entidade para pegar o token
     * @return Sucesso ou Falha
     */
    public String removeTracks(String playlistID, RemoveTrack tracks, Long id) {
        TokenResponse tokenResponse = tokenResponseRepository.getOne(id);

        HttpHeaders headers = createHeadderBearer(tokenResponse.getAccess_token());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RemoveTrack> entity = new HttpEntity<>(tracks, headers);

        RestTemplate restTemplate = new RestTemplate();

        String url = playlistUrlAddTrack.replace("{playlist_id}", playlistID);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);

        if (response.getStatusCode().value() == 200) {
            for (Track track : tracks.getTracks()) {
                TrackRemoved added = new TrackRemoved();
                added.setPlaylistId(playlistID);
                added.setTrackUri(track.getUri());

                tracksRemovedRepository.save(added);
            }

            return "Musica Removida com sucesso";
        } else {
            return "Falha ao remover Musica";
        }
    }

    /**
     * Metodo para construir o Head e Body para a request do token
     *
     * @param grantToken Codigo fornecido pela Spotify
     * @return Entity para envio na API
     */
    private HttpEntity<MultiValueMap<String, String>> buildRequestToken(GrantToken grantToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("code", grantToken.getCode());
        map.add("grant_type", grantToken.getGrant_type());
        map.add("redirect_uri", grantToken.getRedirect_uri());

        return new HttpEntity<>(map, headers);
    }

    /**
     * Criacao do Header com o Token para requisicao
     *
     * @param token Token do Spotify
     * @return header montado
     */
    private HttpHeaders createHeadderBearer(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        return headers;
    }
}
