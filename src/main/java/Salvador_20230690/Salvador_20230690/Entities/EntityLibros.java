package Salvador_20230690.Salvador_20230690.Entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@EqualsAndHashCode @ToString
@Getter @Setter
@Table(name = "libros")
public class EntityLibros {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_libro")
    @SequenceGenerator(name = "seq_libro",sequenceName = "seq_libro", allocationSize = 1)
    @Column(name = "libro_id")
    private long libro_id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "año_publicacion")
    private Long año_publicacion;

    @Column(name = "genero")
    private String genero;

    @Column(name = "autor_id")
    private Long autor_id;

}
