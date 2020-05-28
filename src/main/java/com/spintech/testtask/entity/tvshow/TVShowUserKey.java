package com.spintech.testtask.entity.tvshow;

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
public
class TVShowUserKey implements Serializable {
    @Column(name = "tvshow_id")
    Long tvShowId;

    @Column(name = "user_id")
    Long userId;
}
