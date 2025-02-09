package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.service.CourseService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/courses")
public class CourseController
{
    @Autowired
    private CourseService courseService;

    @ApiOperation(value ="Returns all Courses", response = Course.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List of Courses Found", response = Course.class, responseContainer = "List"),
                            @ApiResponse(code = 404, message = "List of Courses Not Found")})
    @GetMapping(value = "/courses", produces = {"application/json"})
    public ResponseEntity<?> listAllCourses()
    {
        ArrayList<Course> myCourses = courseService.findAll();
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }

    @ApiOperation(value ="Returns all Courses - With Paging Support", response = Course.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List of Courses Found", response = Course.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "List of Courses Not Found")})
    @ApiImplicitParams({ //gives swagger info on the implicit query params
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})
    @GetMapping(value = "/courses/paging", produces = {"application/json"})
    public ResponseEntity<?> listAllCoursesByPage(@PageableDefault(page=0, size=3) Pageable pageable){
        return new ResponseEntity<>(courseService.findAllPageable(pageable), HttpStatus.OK);
    }

    @ApiOperation(value ="Returns Count of Students in Each Course", responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List Found",  responseContainer = "List"),
            @ApiResponse(code = 404, message = "List Not Found")})
    @GetMapping(value = "/studcount", produces = {"application/json"})
    public ResponseEntity<?> getCountStudentsInCourses()
    {
        return new ResponseEntity<>(courseService.getCountStudentsInCourse(), HttpStatus.OK);
    }

    @ApiOperation(value ="Deletes Course Depending on CourseId")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Course Deleted"),
            @ApiResponse(code = 404, message = "Course Not Deleted")})
    @DeleteMapping("/courses/{courseid}")
    public ResponseEntity<?> deleteCourseById(@ApiParam(value = "Course Id", required = true, example = "1") @PathVariable long courseid)
    {
        courseService.delete(courseid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
