package com.example.demo.controller;

import com.example.demo.domain.TokenResponse;
import com.example.demo.domain.playlist.AddTrack;
import com.example.demo.domain.playlist.RemoveTrack;
import com.example.demo.service.SpotifyService;
import com.example.demo.service.SpotifyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping(path = "api")
public class SpotifyController {

    @Value("${client.id.param}")
    String clientIdParam;

    @Value("${api.spotify.auth}")
    String sporifyApi;

    @Value("${redirect.param}")
    String redirectUrl;

    @Value("${response_type.param}")
    String responseType;

    @Value("${scopes.auth}")
    String scopes;


    private SpotifyService spotifyService;

    @Autowired
    SpotifyController(SpotifyServiceImpl spotifyService) {
        this.spotifyService = spotifyService;
    }

    @GetMapping("/auth")
    public ModelAndView auth() {
        return new ModelAndView("redirect:" + sporifyApi + clientIdParam
                + "&" + redirectUrl
                + "&" + responseType
                + "&" + scopes);
    }

    @GetMapping("/redirect")
    public ResponseEntity redirectUri(@RequestParam(required = false) String code,
                                      @RequestParam(required = false) String error) throws Exception {

        TokenResponse tokenResponse = this.spotifyService.getToken(code);
        return ResponseEntity.ok(tokenResponse.getId());
    }

    @GetMapping("/playlist")
    public ResponseEntity getPlaylists(@RequestParam Long id) {
        return ResponseEntity.ok(this.spotifyService.getPlaylists(id));
    }

    @PostMapping("/add")
    public ResponseEntity addTrack(@RequestParam String playlistId,
                                   @RequestBody AddTrack listUris,
                                   @RequestParam Long id) {
        return ResponseEntity.ok(this.spotifyService.addTracks(playlistId, listUris, id));
    }

    @GetMapping("/playlist/{playlistID}")
    public ResponseEntity getPlaylists(@RequestParam Long id, @PathVariable("playlistID") String playlistID) {
        return ResponseEntity.ok(this.spotifyService.getPlaylist(id, playlistID));
    }

    @DeleteMapping("/remove")
    public ResponseEntity removeTrack(@RequestParam String playlistId,
                                   @RequestBody RemoveTrack listUris,
                                   @RequestParam Long id) {
        return ResponseEntity.ok(this.spotifyService.removeTracks(playlistId, listUris, id));
    }
}
