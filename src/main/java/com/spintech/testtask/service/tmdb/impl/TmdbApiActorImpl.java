package com.spintech.testtask.service.tmdb.impl;

import com.spintech.testtask.entity.actor.Actor;
import com.spintech.testtask.exception.ThirdPartyException;
import com.spintech.testtask.repository.ActorRepository;
import com.spintech.testtask.service.tmdb.TmdbApiActor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

@Service
@Slf4j
public class TmdbApiActorImpl extends BaseTmdbApiService implements TmdbApiActor {

    @Autowired
    private ActorRepository actorRepository;

    @Override
    public Actor getActor(Long id) {
        Actor actor = actorRepository.findById(id).orElse(null);

        if (actor != null) {
            return actor;
        }

        try {
            String url = getTmdbUrl("/person/" + id);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Actor> response
                    = restTemplate.getForEntity(url, Actor.class);

            actor = response.getBody();
            actorRepository.save(actor);

        } catch (URISyntaxException | HttpClientErrorException.UnprocessableEntity e) {
            log.error("Couldn't get actor.");
            throw new ThirdPartyException("Such an actor doesn't exists.");
        }

        return actor;
    }
}
