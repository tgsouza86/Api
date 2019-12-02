package com.example.demo.domain.playlist;

import com.example.demo.domain.Owner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {

    private Boolean collaborative;

    private String description;

    private String href;

    private String id;

    private String name;

    private Boolean primary_color;

    private String snapshot_id;

    private String type;

    private String uri;

    private List<Images> images;

    private Owner owner;

    private TracksPlaylist tracks;
}
