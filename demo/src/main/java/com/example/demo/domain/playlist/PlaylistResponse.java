package com.example.demo.domain.playlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistResponse {

    private String href;

    private Integer limit;

    private String next;

    private Integer offset;

    private String previous;

    private Integer total;

    private List<Playlist> items;

}