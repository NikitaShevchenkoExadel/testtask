package com.spintech.testtask.entity.actor;

import com.spintech.testtask.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActorUser {
    @EmbeddedId
    ActorUserKey id;

    @ManyToOne
    @MapsId("actor_id")
    @JoinColumn(name = "actor_id")
    Actor actor;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    User user;

    @Enumerated(EnumType.STRING)
    private ActorStatus actorStatus;
}
