package com.example.locstreamserver.controller;
import com.example.locstreamserver.model.Beacon;
import com.example.locstreamserver.model.Event;
import com.example.locstreamserver.model.User;
import com.example.locstreamserver.model.mmWaveEvent;
import com.example.locstreamserver.repository.BeaconRepository;
import com.example.locstreamserver.repository.UserRepository;
import com.example.locstreamserver.repository.mmWaveRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(path="/users", produces="application/json")
@CrossOrigin(origins="*")
public class UserController {
    private final UserRepository userRepository;

    public UserController() { this.userRepository = new UserRepository(); }

    @PostMapping("/add")
    public String addUser(@RequestBody User user) {
        userRepository.add(user);
        return "Request received.";
    }

    @PostMapping("/auth")
    public String authenticateUser(@RequestBody User user) {
        userRepository.authenticateUser(user);
        return "Request received.";
    }

    @PostMapping("/remove")
    public String removeUser(@RequestBody User user) {
        userRepository.removeUser(user);
        return "Request received.";
    }
    @PostMapping("/update")
    public String updateUser(@RequestBody User user) {
        userRepository.updateUser(user);
        return "Request received.";
    }



}
