package devmind.c24.service;

import devmind.c24.model.Materie;
import devmind.c24.model.Student;
import devmind.c24.repository.StudentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final static int STUDENTS_PER_PAGE = 3;
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Iterable<Student> getAll(Integer page) {
        if (page != null) {
            Pageable pageToSearchFor = PageRequest.of(page, STUDENTS_PER_PAGE);
            return studentRepository.findAll(pageToSearchFor);
        }
        return studentRepository.findAll();
    }

    public Student joinStudent(Student student, Materie materie) {
        student.getCursuriAlese().add(materie);
        return studentRepository.save(student);
    }

    public Optional<Student> getStudentByCnp(String cnp) {
        return studentRepository.findByCnp(cnp);
    }
}
