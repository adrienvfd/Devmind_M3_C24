package devmind.c24.dtos;

import devmind.c24.model.Materie;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class TeacherSuccesResponseDTO {
    private long id;
    private String name;
    private Set<Materie> courses;
}
