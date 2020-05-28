package com.spintech.testtask.repository;

import com.spintech.testtask.entity.actor.ActorStatus;
import com.spintech.testtask.entity.actor.ActorUser;
import com.spintech.testtask.entity.actor.ActorUserKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ActorUserRepository extends CrudRepository<ActorUser, ActorUserKey> {
    List<ActorUser> findByIdUserIdAndActorStatus(Long userId, ActorStatus actorStatus);
}
