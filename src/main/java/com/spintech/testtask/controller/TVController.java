package com.spintech.testtask.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spintech.testtask.entity.User;
import com.spintech.testtask.service.UserService;
import com.spintech.testtask.service.tmdb.TmdbApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/tv")
public class TVController {
    @Autowired
    private UserService userService;

    @Autowired
    private TmdbApi tmdbApi;


    @RequestMapping(value = "/popular", method = POST)
    public ResponseEntity popular(@RequestParam String email,
                                  @RequestParam String password) {
        if (userService.findUser(email, password) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String popularMovies = tmdbApi.popularTVShows();

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(popularMovies);
    }

    @RequestMapping(value = "/unwatchedTVShowWithFavoriteActors", method = GET)
    public ResponseEntity unwatchedTVShowWithFavoriteActors(@RequestParam String email,
                                                            @RequestParam String password) throws JsonProcessingException {
        User user = userService.findUser(email, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        String unwatchedTV = tmdbApi.unwatchedTVShowsWithFavoriteActors(user.getId());

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(unwatchedTV);
    }
}
