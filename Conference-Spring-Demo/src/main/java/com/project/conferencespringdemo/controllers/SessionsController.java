package com.project.conferencespringdemo.controllers;

import com.project.conferencespringdemo.models.Session;
import com.project.conferencespringdemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionsController {

    @Autowired
    private SessionRepository sessionRepository;

    // list endpoint, it will return all the session when called
    @GetMapping
    public List<Session> list(){
        return sessionRepository.findAll();
    }

    // return specific session back to the caller in JSON payload
    @GetMapping
    @RequestMapping(value = "{id}")
    public Session get(@PathVariable Long id){
        // returns one Session object whose session_id = id
        return sessionRepository.findById(id).orElse(null);
    }

    // creates new session in db
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Session create(@RequestBody final Session session){
        // we can save objects as we are working with it, but it does not get committed to db until flushed. saveAndFlush method helps to save the data and flush into db all together.
        return sessionRepository.saveAndFlush(session);
    }

    // deletes session data from db
//    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
//    public void delete(@PathVariable Long id){
//        sessionRepository.deleteById(id);
//    }

    //modified the delete method to handle deleting children data
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        // to delete children data, we need to delete all data for sessions from session_speakers table
        Optional<Session> optionalSession = sessionRepository.findById(id);
        if(optionalSession.isPresent()) {
            Session session = optionalSession.get();
            // delete the entries of the speakList for the specific session id
            session.getSpeakers().clear();
            sessionRepository.save(session);
            // delete sessions data from db
            sessionRepository.deleteById(id);
        }
    }

    // update session columns into DB
    // PUT expects all the attributes to be passed in, if any attribute is missed, it will update as NULL. Add validation that all attributes are passed in, otherwise return a 400 bad payload error.
    // PATCH would only update the changed attributes as needed
//    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
//    public Session update(@PathVariable Long id, @RequestBody Session session){
//        // get the existing data from DB based on "id" parameter
//        Session existingSession = sessionRepository.findById(id).orElse(null);
//        // BeanUtils takes existingSession and copies the incoming parameter "session" data onto it
//        // ignoreProperties: allows to ignore properties on the entity that we do not want to copy from one to another. Here we want to ignore the "Session_id" as it is PK, dont want to replace it, it might replace it to null, and PK can not have null values.
//        assert existingSession != null;
//        BeanUtils.copyProperties(session,existingSession,"session_id");
//        // save changes and flush to DB
//        return sessionRepository.saveAndFlush(existingSession);
//    }

    // modified update method to add validation that all attributes are passed in, otherwise return a 400 bad payload error.

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Session> update(@PathVariable Long id, @RequestBody Session session){
        // Check if any attribute is null
        if (session.getSession_id() == null || session.getSession_name() == null || session.getSession_description() == null || session.getSession_length() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // get the existing data from DB based on "id" parameter
        Session existingSession = sessionRepository.getReferenceById(id);

        // BeanUtils takes existingSession and copies the incoming parameter "session" data onto it
        // ignoreProperties: allows to ignore properties on the entity that we do not want to copy from one to another. Here we want to ignore the "Session_id" as it is PK, dont want to replace it, it might replace it to null, and PK can not have null values.
        BeanUtils.copyProperties(session, existingSession, "session_id");

        // save changes and flush to DB and return ok response
        return ResponseEntity.ok(sessionRepository.saveAndFlush(existingSession));
    }
}
