package com.spintech.testtask.service.tmdb;

import com.spintech.testtask.entity.actor.Actor;

public interface TmdbApiActor {
    Actor getActor(Long id);
}
