package com.example.locstreamserver.controller;


import com.example.locstreamserver.model.LocatorNode;
import com.example.locstreamserver.repository.LocatorRepository;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;


@RestController
@RequestMapping("/locators")
public class LocatorNodeController {
    String apiKey = "123";
    String secretKey = "345";
    private final LocatorRepository locatorRepository;

    public LocatorNodeController(LocatorRepository locatorRepository) {
        this.locatorRepository = new LocatorRepository();
    }


    @GetMapping
    public List<LocatorNode> getLocators() {   //When implementing secret key and api key pair, add this to the (): @RequestHeader("API-Key") String apiKey, @RequestHeader("Secret-Key") String secretKey
        // Call the GetAccountLocators method
        // Placeholder - Add api key & secret key handling from header info, validate before executing code below.
        return locatorRepository.GetAllLocators(apiKey, secretKey);
    }

    @GetMapping("{id}")
    public int getLocatorIdByMac(@PathVariable String id) {   //When implementing secret key and api key pair, add this to the (): @RequestHeader("API-Key") String apiKey, @RequestHeader("Secret-Key") String secretKey


        return locatorRepository.GetLocatorByMac(apiKey, secretKey, id);
    }



    @PostMapping
    public String addLocator(@RequestBody LocatorNode locator) {  //locators.add(new LocatorNode(4,"FourthTest",2,2, macAddress() ));
        // Placeholder - Add api key & secret key handling from header info, validate before executing code below.
        // use the key handling to determine what account id they are coming from, current highest locator ID
        //int accountId = 1; //temporary - Use apiKey & secretKey to find accountId
        //System.out.println(locator.toString());
        System.out.println("New locator being added @ macAddress: " + locator.getLocatorMAC());
        //System.out.println(locatorRepository.getLocatorById());

        return locatorRepository.AddLocator(locator.getLocatorName(), locator.getLocatorMAC());
    }




}
