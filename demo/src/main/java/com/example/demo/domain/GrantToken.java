package com.example.demo.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrantToken {

    private Long id;

    private String grant_type = "authorization_code";

    private String code;

    private String redirect_uri;

}
