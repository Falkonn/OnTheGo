/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alten.onthego.controllers;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ka3146
 */
@RestController
public class GreetingControll {
      private static BigInteger nextId;
    private static Map<BigInteger, Greeting> greetingMap;

    private static Greeting save(Greeting greeting) {
        if (greetingMap == null) {
            greetingMap = new HashMap<BigInteger, Greeting>();
            nextId = BigInteger.ONE;
        }
        greeting.setId(nextId);
        nextId = nextId.add(BigInteger.ONE);
        greetingMap.put(greeting.getId(), greeting);
        return greeting;
    }

    static {
        Greeting g1 = new Greeting();
        g1.setText("Hello world!");
        save(g1);

        Greeting g2 = new Greeting();
        g2.setText("Hi Khaled!");
        save(g2);
    }

    @RequestMapping(
            value ="/api/greetings",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Greeting>> getGreetings() {
        Collection<Greeting> greetings = greetingMap.values();
        return new ResponseEntity<Collection<Greeting>>(greetings,
                HttpStatus.OK);
    }

   @RequestMapping(
            value = "/api/greetings/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Greeting> getGreeting(
            @PathVariable("id") BigInteger id) {
        Greeting greeting = greetingMap.get(id);
        if (greeting == null) {
            return new ResponseEntity<Greeting>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }
}
