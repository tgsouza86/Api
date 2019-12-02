package com.example.demo.domain.tracksadded;


import com.example.demo.domain.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
public class TracksAdded extends AbstractEntity {

    private String trackUri;

    private String playlistId;
}
