package com.spintech.testtask.entity.actor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ActorUserKey implements Serializable {
    @Column(name = "actor_id")
    Long actorId;

    @Column(name = "user_id")
    Long userId;
}
