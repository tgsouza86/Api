package com.example.demo.domain.playlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TracksPlaylist {

    private String href;
    private Integer total;
    private List<TrackPlaylist> items;
}
