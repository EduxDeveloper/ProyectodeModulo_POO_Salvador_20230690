package Salvador_20230690.Salvador_20230690.Services;

import Salvador_20230690.Salvador_20230690.Entities.EntityLibros;
import Salvador_20230690.Salvador_20230690.Models.DTO.DTOLibros;
import Salvador_20230690.Salvador_20230690.Repositories.RepositoryLibros;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service @Slf4j
public class ServicesLibros {

    @Autowired
    RepositoryLibros repo;


    public List<DTOLibros>ObtenerLibros(){
        List<EntityLibros> libros = repo.findAll();
        return libros.stream().map(this::ConvertirADTO).collect(Collectors.toList());
    }

    public DTOLibros ConvertirADTO(EntityLibros entityLibros){
        DTOLibros dto = new DTOLibros();

        dto.setLibro_id(entityLibros.getLibro_id());
        dto.setTitulo(entityLibros.getTitulo());
        dto.setIsbn(entityLibros.getIsbn());
        dto.setAño_publicacion(entityLibros.getAño_publicacion());
        dto.setGenero(entityLibros.getGenero());
        dto.setAutor_id(entityLibros.getAutor_id());

        return dto;
    }

}
