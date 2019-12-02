package com.example.demo.service;

import com.example.demo.domain.TokenResponse;
import com.example.demo.domain.playlist.*;

public interface SpotifyService {


    TokenResponse getToken(String code) throws Exception;

    PlaylistResponse getPlaylists(Long id);

    String addTracks(String playlistID, AddTrack tracks, Long id);

    SinglePlaylist getPlaylist(Long id, String playlistID);

    String removeTracks(String playlistID, RemoveTrack tracks, Long id);
}

