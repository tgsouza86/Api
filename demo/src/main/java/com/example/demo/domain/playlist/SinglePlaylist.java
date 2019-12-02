package com.example.demo.domain.playlist;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SinglePlaylist {

    private String id;

    private String name;

    private TracksPlaylist tracks;
}
