package devmind.c24.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "adrese_studenti")
@Data
public class Adresa {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "strada")
    private String strada;

    @Column(name = "numar")
    private String numar;

    @Column(name = "localitate")
    private String localitate;

    @OneToOne(mappedBy = "adresa")
    @JsonBackReference
    private Student student;
}
