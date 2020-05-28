package com.spintech.testtask.service;

import com.spintech.testtask.entity.User;
import com.spintech.testtask.entity.tvshow.TVShowUser;

public interface TVShowService {
    TVShowUser makeAsWatched(Long id, User user);

    void deleteFromWatched(Long id, User user);
}
