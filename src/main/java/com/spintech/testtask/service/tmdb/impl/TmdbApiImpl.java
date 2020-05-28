package com.spintech.testtask.service.tmdb.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spintech.testtask.entity.actor.ActorStatus;
import com.spintech.testtask.entity.actor.ActorUser;
import com.spintech.testtask.entity.tvshow.TVShowStatus;
import com.spintech.testtask.exception.ThirdPartyException;
import com.spintech.testtask.repository.ActorUserRepository;
import com.spintech.testtask.repository.TVShowUserRepository;
import com.spintech.testtask.service.tmdb.TmdbApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TmdbApiImpl extends BaseTmdbApiService implements TmdbApi {

    @Autowired
    private ActorUserRepository actorUserRepository;

    @Autowired
    private TVShowUserRepository tvShowUserRepository;


    public String popularTVShows() throws IllegalArgumentException {
        try {
            String url = getTmdbUrl("/tv/popular");

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response
                    = restTemplate.getForEntity(url, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                return null;
            }

            return response.getBody();
        } catch (URISyntaxException e) {
            log.error("Couldn't get popular tv shows");
        }
        return null;
    }


    @Override
    public String unwatchedTVShowsWithFavoriteActors(Long userId) throws JsonProcessingException {

        List<ActorUser> favoriteActors = actorUserRepository.findByIdUserIdAndActorStatus(userId, ActorStatus.FAVORITE);
        Set<Long> tvShows = getTVIdsForFavoriteActors(favoriteActors);

        Set<Long> watchedTVShowsIds = tvShowUserRepository.findByIdUserIdAndTvShowStatus(userId, TVShowStatus.WATCHED)
                .stream().map(tvShowUser -> tvShowUser.getId().getTvShowId()).collect(Collectors.toSet());
        tvShows.removeAll(watchedTVShowsIds);

        List<String> result = new ArrayList<>();
        Iterator iterator = tvShows.iterator();
        try {
            while (iterator.hasNext()) {
                String url = getTmdbUrl("/tv/" + iterator.next());

                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> responseJson
                        = restTemplate.getForEntity(url, String.class);

                result.add(responseJson.getBody());
            }
        } catch (URISyntaxException | HttpClientErrorException.UnprocessableEntity e) {
            log.error("Couldn't get tv show.");
            throw new ThirdPartyException("Such a tv show doesn't exists.");
        }

        return "{\"unwatched_tvshows\": [" + String.join(",", result) + "]}";
    }


    private Set<Long> getTVIdsForFavoriteActors(List<ActorUser> favoriteActors) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Set<Long> tvShows = new HashSet<>();

        try {
            for (ActorUser actorUser : favoriteActors) {
                String url = getTmdbUrl("/person/" + actorUser.getId().getActorId() + "/tv_credits");

                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {

                    tvShows.addAll(objectMapper.readTree(response.getBody()).get("cast").findValues("id")
                            .stream().map(JsonNode::longValue).collect(Collectors.toSet()));
                }
            }
        } catch (URISyntaxException e) {
            log.error("Couldn't get unwatched tv shows with favorites actors");
            throw new ThirdPartyException("Couldn't get unwatched tv shows with favorites actors");
        }
        return tvShows;
    }
}
