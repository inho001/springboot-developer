package com.bhl.springdeveloper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    /*@GetMapping("/student")
    public Student getStudent() {
        return new Student("SungChul", "Park");
    }*/

    // http://localhost:8080/student?firstname=SungChul&lastname=Park
    @GetMapping("/student")
    public Student getStudent(@RequestParam("firstname") String firstname,
                              @RequestParam("lastname") String lastname) {
        return new Student(firstname, lastname);
    }

    // http://localhost:8080/student/ChanHoPark
    @GetMapping("/student/{firstname}/{lastname}")
    public Student getStudent2(@PathVariable("firstname") String firstname,
                               @PathVariable("lastname") String lastname) {
        return new Student(firstname, lastname);
    }
}
