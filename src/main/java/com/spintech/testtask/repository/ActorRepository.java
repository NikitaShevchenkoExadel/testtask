package com.spintech.testtask.repository;

import com.spintech.testtask.entity.actor.Actor;
import org.springframework.data.repository.CrudRepository;

public interface ActorRepository extends CrudRepository<Actor, Long> {
}
