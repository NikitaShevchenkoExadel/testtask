package com.spintech.testtask.service.impl;

import com.spintech.testtask.entity.User;
import com.spintech.testtask.entity.tvshow.TVShow;
import com.spintech.testtask.entity.tvshow.TVShowStatus;
import com.spintech.testtask.entity.tvshow.TVShowUser;
import com.spintech.testtask.entity.tvshow.TVShowUserKey;
import com.spintech.testtask.repository.TVShowUserRepository;
import com.spintech.testtask.service.TVShowService;
import com.spintech.testtask.service.tmdb.TmdbApiTVShow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TVShowServiceImpl implements TVShowService {

    @Autowired
    private TVShowUserRepository tvShowUserRepository;

    @Autowired
    private TmdbApiTVShow tmdbApiTVShow;

    @Override
    public TVShowUser makeAsWatched(Long id, User user) throws IllegalArgumentException {

        TVShowUser tvShowForUser = tvShowUserRepository.findById(new TVShowUserKey(id, user.getId())).orElse(null);

        if (tvShowForUser != null) {
            tvShowForUser.setTvShowStatus(TVShowStatus.WATCHED);
            tvShowUserRepository.save(tvShowForUser);
            return tvShowForUser;
        }

        TVShow tvShow = tmdbApiTVShow.getTVShow(id);

        tvShowForUser = new TVShowUser(new TVShowUserKey(id, user.getId()), tvShow, user, TVShowStatus.WATCHED);
        tvShowUserRepository.save(tvShowForUser);

        return tvShowForUser;
    }

    @Override
    public void deleteFromWatched(Long id, User user) {

        tvShowUserRepository.deleteById(new TVShowUserKey(id, user.getId()));

//        Or it could be next implementation:
//        TVShowUser tvShowForUser = tvShowUserRepository.findById(new TVShowUserKey(id, user.getId())).orElse(null);
//        if (tvShowForUser != null){
//            tvShowForUser.setTvShowStatus(TVShowStatus.WATCHED);
//            tvShowUserRepository.save(tvShowForUser);
//        }
    }
}
