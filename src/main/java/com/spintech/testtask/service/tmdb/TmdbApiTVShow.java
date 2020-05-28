package com.spintech.testtask.service.tmdb;

import com.spintech.testtask.entity.tvshow.TVShow;

public interface TmdbApiTVShow {
    TVShow getTVShow(Long id);
}
