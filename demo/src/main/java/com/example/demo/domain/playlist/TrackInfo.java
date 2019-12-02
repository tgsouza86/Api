package com.example.demo.domain.playlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackInfo {

    private String name;
    private List<Artist> artists;
    private String href;
    private String uri;
    private Album album;


}
