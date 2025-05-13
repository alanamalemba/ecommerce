package com.amalemba.ecommerce.controller;

import com.amalemba.ecommerce.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    private List<Student> students = new ArrayList<>(
            List.of(
                    new Student(1, "Alice", 85),
                    new Student(2, "Bob", 90),
                    new Student(3, "Charlie", 78),
                    new Student(4, "Diana", 92),
                    new Student(5, "Ethan", 67)
            )
    );

    @GetMapping("/students")
    public List<Student> getStudents() {
        return students;
    }

    @GetMapping("csrf-token")
    public CsrfToken getcsrfToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");

    }


    @PostMapping("/students")
    public Student addStudent(@RequestBody Student newStudent) {
        students.add(newStudent);
        return newStudent;
    }

}
