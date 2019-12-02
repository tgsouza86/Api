package com.example.demo.repository;

import com.example.demo.domain.trackremoved.TrackRemoved;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TracksRemovedRepository extends JpaRepository<TrackRemoved, Long> {
}
