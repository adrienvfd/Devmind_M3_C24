package devmind.c24.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class TeacherRequestDTO {
    private String teacherName;
    private String courseName;
}
