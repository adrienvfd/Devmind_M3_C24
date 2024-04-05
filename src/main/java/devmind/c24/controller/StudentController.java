package devmind.c24.controller;


import devmind.c24.model.Materie;
import devmind.c24.model.Student;
import devmind.c24.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path = "/join", method = RequestMethod.POST)
    public ResponseEntity<Student> joinStudent(@RequestBody Student student, @RequestBody Materie materie) {
        Student joinedStudent = studentService.joinStudent(student, materie);
        return new ResponseEntity<>(joinedStudent, HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(path = "/courses", method = RequestMethod.GET)
    public ResponseEntity<Set<Materie>> getCourses(@RequestBody String cnp) {
        Optional<Student> student = studentService.getStudentByCnp(cnp);
        return student.map(value -> new ResponseEntity<>(value.getCursuriAlese(), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
