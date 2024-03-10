package com.project.conferencespringdemo.controllers;

import com.project.conferencespringdemo.models.Speaker;
import com.project.conferencespringdemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakerController {

    @Autowired
    private SpeakerRepository speakerRepository;

    // list endpoint, it will return all the speakers when called
    @GetMapping
    public List<Speaker> list(){
        return speakerRepository.findAll();
    }

    // return specific speaker back to the caller in JSON payload
    @GetMapping
    @RequestMapping("{id}")
    public Speaker get(@PathVariable Long id){
        return speakerRepository.getReferenceById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Speaker create(@RequestBody final Speaker speaker){
        return speakerRepository.saveAndFlush(speaker);
    }

    // deletes speaker data from db
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        speakerRepository.deleteById(id);
    }

    // update speaker data
//    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
//    public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker){
//        Speaker existingSpeaker = speakerRepository.getReferenceById(id);
//        BeanUtils.copyProperties(speaker,existingSpeaker,"speaker_id");
//        return speakerRepository.saveAndFlush(existingSpeaker);
//    }

    // modified update method to add validation that all attributes are passed in, otherwise return a 400 bad payload error.
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Speaker> update(@PathVariable Long id, @RequestBody Speaker speaker){
        // Check if any attribute is null
        if (speaker.getSpeaker_id()==null || speaker.getFirst_name()==null || speaker.getLast_name()==null || speaker.getTitle() == null || speaker.getCompany() == null || speaker.getSpeaker_bio()==null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // get the existing data from DB based on "id" parameter
        Speaker existingSpeaker = speakerRepository.getReferenceById(id);

        // BeanUtils takes existingSession and copies the incoming parameter "session" data onto it
        // ignoreProperties: allows to ignore properties on the entity that we do not want to copy from one to another. Here we want to ignore the "Session_id" as it is PK, dont want to replace it, it might replace it to null, and PK can not have null values.
        BeanUtils.copyProperties(speaker, existingSpeaker, "Speaker_id");

        // save changes and flush to DB and return ok response
        return ResponseEntity.ok(speakerRepository.saveAndFlush(existingSpeaker));
    }
}
