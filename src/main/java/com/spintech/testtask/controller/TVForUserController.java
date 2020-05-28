package com.spintech.testtask.controller;

import com.spintech.testtask.entity.User;
import com.spintech.testtask.entity.tvshow.TVShowUser;
import com.spintech.testtask.service.TVShowService;
import com.spintech.testtask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/tv")
public class TVForUserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TVShowService tvShowService;

    @RequestMapping(value = "/markAsWatched/{tv_id}", method = POST)
    public ResponseEntity markAsWatched(@PathVariable(value = "tv_id") Long id,
                                        @RequestParam String email,
                                        @RequestParam String password) {
        User user = userService.findUser(email, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        TVShowUser tvShow = tvShowService.makeAsWatched(id, user);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(tvShow);
    }


    @RequestMapping(value = "/deleteFromWatched/{tv_id}", method = DELETE)
    public ResponseEntity actorDelete(@PathVariable(value = "tv_id") Long id,
                                      @RequestParam String email,
                                      @RequestParam String password) {
        User user = userService.findUser(email, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        tvShowService.deleteFromWatched(id, user);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
    }
}
