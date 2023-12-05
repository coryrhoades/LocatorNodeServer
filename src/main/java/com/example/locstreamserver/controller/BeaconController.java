package com.example.locstreamserver.controller;


import com.example.locstreamserver.model.Beacon;
import com.example.locstreamserver.repository.BeaconRepository;
import com.example.locstreamserver.repository.LocatorRepository;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping(path="/beacons", produces="application/json")
@CrossOrigin(origins="*")
public class BeaconController {
    private final BeaconRepository beaconRepository;

    public BeaconController() {
        this.beaconRepository = new BeaconRepository();
    }
    @PostMapping()
    public ArrayList<Beacon> getBeaconByMac(@RequestBody Map<String, String> body) {
        if(body.get("mac") != null) {
            String mac = body.get("mac");
            return beaconRepository.getBeaconByMac(mac);
        } else {
            return null;
        }
    }


    @GetMapping()
    public ArrayList<Beacon> getBeacons() {  //return a full list of all beacons

        return beaconRepository.getBeacons();
    }

    @GetMapping("{id}")
    public ArrayList<Beacon> getBeaconById(@PathVariable int id) {  //return a full list of all beacons

        return beaconRepository.getBeaconById(id + "");
    }


    @PostMapping("/add")
    public ArrayList<Beacon> addBeacon(@RequestBody Map<String, String> body) {
        String beaconName = body.get("name");
        String macAddress = body.get("mac");;

        if(beaconName == "" || beaconName == null) {
            beaconName = "Unnamed Beacon";
        }
        if(macAddress == "" || macAddress == null ) {
            return null;
        }

        int id = beaconRepository.addBeacon(beaconName, macAddress); //pass in a name and mac address, and get back the beacon ID.
        if (id != 0) {
            return beaconRepository.getBeaconById(id + "");
        } else {
            return null;
            }


    }


}
