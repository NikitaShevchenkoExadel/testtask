package com.spintech.testtask.service.tmdb;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface TmdbApi {
    String popularTVShows();
    String unwatchedTVShowsWithFavoriteActors(Long userId) throws JsonProcessingException;
}
