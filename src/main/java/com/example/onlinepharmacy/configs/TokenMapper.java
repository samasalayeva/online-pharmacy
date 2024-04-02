package com.example.onlinepharmacy.configs;

import org.keycloak.representations.AccessTokenResponse;
import com.example.onlinepharmacy.dtos.response.AccessToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface TokenMapper {

    TokenMapper INSTANCE = Mappers.getMapper(TokenMapper.class);

    @Mapping(target = "accessToken", source = "token")
    @Mapping(target = "refreshToken", source = "refreshToken")
    @Mapping(target = "expiresIn", source = "expiresIn")
    @Mapping(target = "refreshExpiresIn", source = "refreshExpiresIn")
    AccessToken map(AccessTokenResponse accessTokenResponse);
}
