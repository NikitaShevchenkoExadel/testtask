package com.spintech.testtask.service;

import com.spintech.testtask.entity.actor.ActorUser;
import com.spintech.testtask.entity.User;

public interface ActorService {
    ActorUser makeFavorite(Long id, User user);

    void deleteFavorite(Long id, User user);
}
