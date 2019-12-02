package com.example.demo.domain;

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
public class TokenResponse extends AbstractEntity {

    private String access_token;

    private String token_type;

    private String expires_in;

    private String refresh_token;

    private String scope;
}
