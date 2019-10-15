package com.lambdaschool.school.controller;


import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.service.InstructorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping(value = "/instructors")
public class InstructorController {
	@Autowired
	private InstructorService instructorService;

	private static final Logger logger = LoggerFactory.getLogger(InstructorController.class);

	@GetMapping(value = "/instructors", produces = {"application/json"})
	public ResponseEntity<?> getInstructors(){
		logger.info("GET /instructors/instructors" + " at " + new Date().getTime());
		return new ResponseEntity<>(instructorService.findAll(), HttpStatus.OK);
	}

	@PostMapping(value = "/instructor", consumes = {"application/json"})
	public ResponseEntity<?> addInstructor(@Valid @RequestBody Instructor instructor){
		logger.info("POST /instructors/instructor" + " at " + new Date().getTime());
		return new ResponseEntity<>(instructorService.save(instructor), HttpStatus.CREATED);
	}

	@PutMapping(value = "/instructor/{instructorid}", consumes = {"application/json"})
	public ResponseEntity<?> updateInstructor(@RequestBody Instructor instructor, @PathVariable long instructorid){
		logger.info("POST /instructors/instructor/" + instructorid + " at " + new Date().getTime());
		return new ResponseEntity<>(instructorService.update(instructor, instructorid), HttpStatus.OK);
	}

	@DeleteMapping(value = "/instructor/{instructorid}")
	public ResponseEntity<?> deleteInstructor(@PathVariable long instructorid){
		logger.info("Delete /instructors/instructor/" + instructorid + " at " + new Date().getTime());
		instructorService.delete(instructorid);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
