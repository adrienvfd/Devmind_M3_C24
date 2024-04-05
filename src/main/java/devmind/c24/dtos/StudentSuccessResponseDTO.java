package devmind.c24.dtos;

import devmind.c24.model.Materie;
import lombok.Data;

import java.util.Set;


@Data
public class StudentSuccessResponseDTO {
    private final Integer id;
    private final String nume;
    private final String prenume;
    private final Set<Materie> cursuriAlese;
}
