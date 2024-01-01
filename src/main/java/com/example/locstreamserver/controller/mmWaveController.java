package com.example.locstreamserver.controller;

import com.example.locstreamserver.model.Beacon;
import com.example.locstreamserver.model.Event;
import com.example.locstreamserver.model.mmWaveEvent;
import com.example.locstreamserver.repository.BeaconRepository;
import com.example.locstreamserver.repository.mmWaveRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/mmwave", produces="application/json")
@CrossOrigin(origins="*")
public class mmWaveController {
    private final mmWaveRepository mmwaveRepository;
    public mmWaveController() {
        this.mmwaveRepository = new mmWaveRepository();
    }

    @PostMapping()
    public String addEvents(@RequestBody List<mmWaveEvent> events) {

        System.out.println(events);
        return mmwaveRepository.addEvents(events);
            //System.out.println(event.toString());
        }



}
