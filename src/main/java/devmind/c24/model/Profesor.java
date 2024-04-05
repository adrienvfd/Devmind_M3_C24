package devmind.c24.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "profesori")
@Data
public class Profesor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nume")
    private String nume;

    @OneToMany(mappedBy = "profesor")
    @JsonIgnore
    private Set<Materie> materiiPredate;
}