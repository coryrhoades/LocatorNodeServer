package com.example.locstreamserver.controller;


import com.example.locstreamserver.model.Beacon;
import com.example.locstreamserver.model.Event;
import com.example.locstreamserver.model.ownerChange;
import com.example.locstreamserver.repository.BeaconRepository;
import com.example.locstreamserver.repository.EventRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/events", produces="application/json")
@CrossOrigin(origins="*")
public class BeaconEventController {
    private final EventRepository eventRepository;
    private final BeaconRepository beaconRepository;
    public BeaconEventController() {
        this.eventRepository = new EventRepository();
        this.beaconRepository = new BeaconRepository();
    }

    @GetMapping()
    public ArrayList<Event> getLast100Events() {  //return a full list of all beacons

        return eventRepository.getLast100Events();
    }
    @GetMapping("{id}")
    public ArrayList<Event> geLast100EventsById(@PathVariable int id) {  //return a full list of all beacons

        return eventRepository.getLast100EventsById(id);
    }

    @PostMapping()
    public String addEvents(@RequestBody List<Event> events) {
        //
        System.out.println(events);
        return eventRepository.addEvents(events);
        //for (Event event : events) {

        //}
        //int locatorId = Integer.parseInt(body.get("locatorId"));
        //int beaconId = Integer.parseInt(body.get("beaconId"));
        //int signalStrength = Integer.parseInt(body.get("signalStrength"));
        //String detectTime = body.get("detectionTime");

    }

    @PostMapping("{id}")
    public String  addEventsFromLocatorNode(@PathVariable int id, @RequestBody List<Event> events) {
        // Implement your logic here
        // For example, call a method that processes these events
        if(id == 1) {
            System.out.println("Request received from ID 1!");
        }
        String response = eventRepository.processEventsForLocatorNode(id, events);

        // Return an appropriate response entity
        return response;
    }


    @GetMapping("/ownerLogs")
    public ArrayList<ownerChange>  getOwnershipChanges() {
        // Implement your logic here
        // For example, call a method that processes these events
        return  beaconRepository.getOwnershipChanges();

    }



}
