package com.lambdaschool.school.controller;


import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.service.InstructorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/instructors")
public class InstructorController {
	@Autowired
	private InstructorService instructorService;

	@ApiOperation(value ="Returns all Instructors", response = Instructor.class, responseContainer = "List")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "List of Instructors Found", response = Instructor.class, responseContainer = "List"),
			@ApiResponse(code = 404, message = "List of Instructors Not Found")})
	@GetMapping(value = "/instructors", produces = {"application/json"})
	public ResponseEntity<?> getInstructors(){
		return new ResponseEntity<>(instructorService.findAll(), HttpStatus.OK);
	}

	@ApiOperation(value ="Creates an Instructor", response = Instructor.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Instructor Created", response = Instructor.class),
			@ApiResponse(code = 404, message = "Instructor Not Created")})
	@PostMapping(value = "/instructor", consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<?> addInstructor(@ApiParam(value = "Instructor", required = true) @Valid @RequestBody Instructor instructor){
		return new ResponseEntity<>(instructorService.save(instructor), HttpStatus.CREATED);
	}

	@ApiOperation(value ="Updates an Instructor", response = Instructor.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Instructor Created", response = Instructor.class),
			@ApiResponse(code = 404, message = "Instructor Not Created")})
	@PutMapping(value = "/instructor/{instructorid}", consumes = {"application/json"})
	public ResponseEntity<?> updateInstructor(@ApiParam(value = "Instructor", required = true) @RequestBody Instructor instructor,
											  @ApiParam(value ="Instructor Id",required = true, example = "1") @PathVariable long instructorid){
		return new ResponseEntity<>(instructorService.update(instructor, instructorid), HttpStatus.OK);
	}

	@ApiOperation(value ="Deletes an Instructor")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Instructor Deleted"),
			@ApiResponse(code = 404, message = "Instructor Not Deleted")})
	@DeleteMapping(value = "/instructor/{instructorid}")
	public ResponseEntity<?> deleteInstructor(@ApiParam(value ="Instructor Id",required = true, example = "1") @PathVariable long instructorid){
		instructorService.delete(instructorid);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
