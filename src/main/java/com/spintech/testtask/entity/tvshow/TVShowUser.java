package com.spintech.testtask.entity.tvshow;

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
public class TVShowUser {
    @EmbeddedId
    TVShowUserKey id;

    @ManyToOne
    @MapsId("tvshow_id")
    @JoinColumn(name = "tvshow_id")
    TVShow tvShow;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    User user;

    @Enumerated(EnumType.STRING)
    private TVShowStatus tvShowStatus;
}
