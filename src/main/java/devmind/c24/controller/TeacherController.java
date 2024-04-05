package devmind.c24.controller;

import devmind.c24.dtos.TeacherRequestDTO;
import devmind.c24.dtos.TeacherSuccesResponseDTO;
import devmind.c24.model.Materie;
import devmind.c24.model.Profesor;
import devmind.c24.repository.MaterieRepository;
import devmind.c24.repository.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/teacher")
public class TeacherController {
    private final ProfesorRepository profesorRepository;

    private final MaterieRepository materieRepository;

    @Autowired
    public TeacherController(ProfesorRepository profesorRepository, MaterieRepository materieRepository) {
        this.profesorRepository = profesorRepository;
        this.materieRepository = materieRepository;
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    ResponseEntity<List<TeacherSuccesResponseDTO>> getTeachers() {
        Iterable<Profesor> teachers = profesorRepository.findAll();

        List<TeacherSuccesResponseDTO> response = new ArrayList<>();
        for(Profesor teacher: teachers) {
            response.add(new TeacherSuccesResponseDTO(teacher.getId(), teacher.getNume(), teacher.getMateriiPredate()));
        }

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/add")
    public @Transactional @ResponseBody
    ResponseEntity<TeacherSuccesResponseDTO> addTeacher(@RequestBody TeacherRequestDTO teacher) {

        Profesor profesor = new Profesor();
        profesor.setNume(teacher.getTeacherName());
        profesor = profesorRepository.save(profesor);

        Materie materie = new Materie();
        materie.setNume(teacher.getCourseName());
        materie.setProfesor(profesor);
        materieRepository.save(materie);

        return new ResponseEntity<>(new TeacherSuccesResponseDTO(profesor.getId(), profesor.getNume(), Set.of(materie)), HttpStatus.CREATED);
    }
}

