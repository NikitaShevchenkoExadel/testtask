package com.spintech.testtask.service.tmdb.impl;

import com.spintech.testtask.entity.tvshow.TVShow;
import com.spintech.testtask.exception.ThirdPartyException;
import com.spintech.testtask.repository.TVShowRepository;
import com.spintech.testtask.service.tmdb.TmdbApiTVShow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

@Service
@Slf4j
public class TmdbApiTVShowImpl extends BaseTmdbApiService implements TmdbApiTVShow {

    @Autowired
    private TVShowRepository tvShowRepository;

    @Override
    public TVShow getTVShow(Long id) {
        TVShow tvShow = tvShowRepository.findById(id).orElse(null);

        if (tvShow != null) {
            return tvShow;
        }

        try {
            String url = getTmdbUrl("/tv/" + id);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<TVShow> response
                    = restTemplate.getForEntity(url, TVShow.class);

            tvShow = response.getBody();
            tvShowRepository.save(tvShow);

        } catch (URISyntaxException | HttpClientErrorException.UnprocessableEntity e) {
            log.error("Couldn't get tv show.");
            throw new ThirdPartyException("Such a tv show doesn't exists.");
        }

        return tvShow;
    }
}
