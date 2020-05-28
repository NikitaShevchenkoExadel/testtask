package com.spintech.testtask.repository;

import com.spintech.testtask.entity.tvshow.TVShowStatus;
import com.spintech.testtask.entity.tvshow.TVShowUser;
import com.spintech.testtask.entity.tvshow.TVShowUserKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TVShowUserRepository extends CrudRepository<TVShowUser, TVShowUserKey> {
    List<TVShowUser> findByIdUserIdAndTvShowStatus(Long userId, TVShowStatus tvShowStatus);
}
