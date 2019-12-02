package com.example.demo.domain.trackremoved;

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
public class TrackRemoved extends AbstractEntity {


    private String trackUri;

    private String playlistId;
}
