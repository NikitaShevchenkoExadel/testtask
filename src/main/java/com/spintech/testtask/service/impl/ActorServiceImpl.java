package com.spintech.testtask.service.impl;

import com.spintech.testtask.entity.*;
import com.spintech.testtask.entity.actor.Actor;
import com.spintech.testtask.entity.actor.ActorStatus;
import com.spintech.testtask.entity.actor.ActorUser;
import com.spintech.testtask.entity.actor.ActorUserKey;
import com.spintech.testtask.repository.ActorUserRepository;
import com.spintech.testtask.service.ActorService;
import com.spintech.testtask.service.tmdb.TmdbApiActor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActorServiceImpl implements ActorService {

    @Autowired
    private ActorUserRepository actorUserRepository;

    @Autowired
    private TmdbApiActor tmdbApiActor;

    @Override
    public ActorUser makeFavorite(Long id, User user) throws IllegalArgumentException {

        ActorUser actorForUser = actorUserRepository.findById(new ActorUserKey(id, user.getId())).orElse(null);

        if (actorForUser != null) {
            actorForUser.setActorStatus(ActorStatus.FAVORITE);
            actorUserRepository.save(actorForUser);
            return actorForUser;
        }

        Actor actor = tmdbApiActor.getActor(id);

        actorForUser = new ActorUser(new ActorUserKey(id, user.getId()), actor, user, ActorStatus.FAVORITE);
        actorUserRepository.save(actorForUser);

        return actorForUser;
    }

    @Override
    public void deleteFavorite(Long id, User user) {
        actorUserRepository.deleteById(new ActorUserKey(id, user.getId()));

        //Or it could be next implementation:
//        ActorUser actorForUser = actorUserRepository.findById(new ActorUserKey(id, user.getId())).orElse(null);
//        if (actorForUser != null){
//            actorForUser.setActorStatus(ActorStatus.DEFAULT);
//            actorUserRepository.save(actorForUser);
//        }
    }
}
