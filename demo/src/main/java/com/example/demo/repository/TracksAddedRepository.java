package com.example.demo.repository;

import com.example.demo.domain.tracksadded.TracksAdded;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TracksAddedRepository extends JpaRepository<TracksAdded, Long> {
}
