package Salvador_20230690.Salvador_20230690.Services;

import Salvador_20230690.Salvador_20230690.Entities.EntityLibros;
import Salvador_20230690.Salvador_20230690.Exception.ExceptionLibroNoEncontrado;
import Salvador_20230690.Salvador_20230690.Exception.ExceptionLibroNoRegistrado;
import Salvador_20230690.Salvador_20230690.Models.DTO.DTOLibros;
import Salvador_20230690.Salvador_20230690.Repositories.RepositoryLibros;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
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


    public DTOLibros InsertarLibros(DTOLibros data){
        if (data == null || data.getTitulo().isEmpty()){
            throw new IllegalArgumentException("Ningun campo deve estar vacio");
        }
        try {
            EntityLibros entity = convertirAEntity(data);
            EntityLibros libroGuardado = repo.save(entity);
            return ConvertirADTO(libroGuardado);
        }catch (Exception e){
            log.error("Error al insertar libro" + e.getMessage());
            throw new ExceptionLibroNoRegistrado("El libro no pudo ser registrado");
        }
    }

    public EntityLibros convertirAEntity(DTOLibros data){
        EntityLibros entity = new EntityLibros();

        entity.setTitulo(data.getTitulo());
        entity.setIsbn(data.getIsbn());
        entity.setAño_publicacion(data.getAño_publicacion());
        entity.setGenero(data.getGenero());
        entity.setAutor_id(data.getAutor_id());

        return entity;
    }


    public DTOLibros ActualizarLibros(Long id, @Valid DTOLibros json){
        EntityLibros existente = repo.findById(id).orElseThrow(() -> new ExceptionLibroNoEncontrado("El libro no fue encontrado"));

        existente.setTitulo(json.getTitulo());
        existente.setIsbn(json.getIsbn());
        existente.setAño_publicacion(json.getAño_publicacion());
        existente.setGenero(json.getGenero());
        existente.setAutor_id(json.getAutor_id());

        EntityLibros LibroActualizado = repo.save(existente);
        return ConvertirADTO(LibroActualizado);
    }

    public boolean EliminarLibro(Long id){
        try {
            EntityLibros libroExiste = repo.findById(id).orElse(null);
            if (libroExiste != null){
                repo.deleteById(id);
                return true;
            }else {
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se encontro el libro con el id: " + id + ". Vuelva a intentarlo", 1);
        }
    }

    public List<DTOLibros>BuscarLibrosPorTitulo(String titulo){
        List<EntityLibros> todosLosLibros = repo.findAll();

        List<EntityLibros> LibrosFiltrados = todosLosLibros.stream().filter(libro->
                libro.getTitulo().equalsIgnoreCase(titulo)).collect(Collectors.toList()
        );

        return LibrosFiltrados.stream().map(this::ConvertirADTO).collect(Collectors.toList());
    }
}
