package com.spintech.testtask.controller;

import com.spintech.testtask.entity.actor.ActorUser;
import com.spintech.testtask.entity.User;
import com.spintech.testtask.service.UserService;
import com.spintech.testtask.service.ActorService;
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
@RequestMapping("/person")
public class ActorController {
    @Autowired
    private UserService userService;

    @Autowired
    private ActorService actorService;

    @RequestMapping(value = "/makeFavorite/{actor_id}", method = POST)
    public ResponseEntity actor(@PathVariable(value = "actor_id") Long id,
                                  @RequestParam String email,
                                  @RequestParam String password) {
        User user = userService.findUser(email, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        ActorUser actor = actorService.makeFavorite(id, user);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(actor);
    }


    @RequestMapping(value = "/deleteFavorite/{actor_id}", method = DELETE)
    public ResponseEntity actorDelete(@PathVariable(value = "actor_id") Long id,
                                @RequestParam String email,
                                @RequestParam String password) {
        User user = userService.findUser(email, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        actorService.deleteFavorite(id, user);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
    }
}
