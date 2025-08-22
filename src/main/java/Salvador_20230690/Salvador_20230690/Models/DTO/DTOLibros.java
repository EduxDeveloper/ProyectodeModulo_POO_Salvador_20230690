package Salvador_20230690.Salvador_20230690.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @EqualsAndHashCode @ToString
public class DTOLibros {

    private long libro_id;

    @NotBlank(message = "El titulo no puede estar vacio")
    private String titulo;

    @NotBlank(message = "El isbn no puede estar vacio")
    private String isbn;

    private Long año_publicacion;

    @NotBlank(message = "El genero no puede estar vacio")
    private String genero;

    private Long autor_id;
}
