package com.example.demo.repository;


import com.example.demo.domain.TokenResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenResponseRepository extends JpaRepository<TokenResponse, Long> {
}
