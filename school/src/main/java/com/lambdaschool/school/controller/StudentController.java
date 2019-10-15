package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.service.StudentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController
{
    @Autowired
    private StudentService studentService;

    // Please note there is no way to add students to course yet!
    @ApiOperation(value ="Returns all Students", response = Student.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List of Students Found", response = Student.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "List of Students Not Found")})
    @GetMapping(value = "/students", produces = {"application/json"})
    public ResponseEntity<?> listAllStudents()
    {
        List<Student> myStudents = studentService.findAll();
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }

    @ApiOperation(value ="Returns Student Depending on StudentId", response = Student.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Student Found", response = Student.class),
            @ApiResponse(code = 404, message = "Student Not Found")})
    @GetMapping(value = "/Student/{StudentId}",
                produces = {"application/json"})
    public ResponseEntity<?> getStudentById(
            @ApiParam(value = "Student Id", required = true, example = "1")
            @PathVariable
                    Long StudentId)
    {
        Student r = studentService.findStudentById(StudentId);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @ApiOperation(value ="Returns all Students That Have Names Containing a String", response = Student.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List of Students Found", response = Student.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "List of Students Not Found")})
    @GetMapping(value = "/student/namelike/{name}",
                produces = {"application/json"})
    public ResponseEntity<?> getStudentByNameContaining(
            @ApiParam(value = "Name", required = true, example = "Joe")
            @PathVariable String name)
    {
        List<Student> myStudents = studentService.findStudentByNameLike(name);
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }


    @ApiOperation(value ="Creates a Student", response = Student.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Student Created", response = Student.class),
            @ApiResponse(code = 404, message = "Student Not Created")})
    @PostMapping(value = "/Student",
                 consumes = {"application/json"},
                 produces = {"application/json"})
    public ResponseEntity<?> addNewStudent(
                                            @ApiParam(value="New Student", required = true)
                                            @Valid
                                           @RequestBody
                                                   Student newStudent) throws URISyntaxException
    {
        newStudent = studentService.save(newStudent);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newStudentURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{Studentid}").buildAndExpand(newStudent.getStudid()).toUri();
        responseHeaders.setLocation(newStudentURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    @ApiOperation(value ="Updates a Student", response = Student.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List of Students Found", response = Student.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "List of Students Not Found")})
    @PutMapping(value = "/Student/{Studentid}")
    public ResponseEntity<?> updateStudent(
            @ApiParam(value = "Updated Student Field(s)", required = true)
            @RequestBody
                    Student updateStudent,
            @ApiParam(value = "Student Id", required = true, example = "1")
            @PathVariable
                    long Studentid)
    {
        studentService.update(updateStudent, Studentid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value ="Deletes a Student")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Student Deleted"),
            @ApiResponse(code = 404, message = "Student Not Deleted")})
    @DeleteMapping("/Student/{Studentid}")
    public ResponseEntity<?> deleteStudentById(
            @ApiParam(value = "Student Id", required = true, example = "1")
            @PathVariable
                    long Studentid)
    {
        studentService.delete(Studentid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
