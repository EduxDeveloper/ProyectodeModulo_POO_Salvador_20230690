package Salvador_20230690.Salvador_20230690.Controller;

import Salvador_20230690.Salvador_20230690.Models.DTO.DTOLibros;
import Salvador_20230690.Salvador_20230690.Services.ServicesLibros;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController @RequestMapping("/ApiLibros")
public class ControllerLibros {

    @Autowired
    ServicesLibros service;

    @GetMapping("/GetLibros")
    public List<DTOLibros>ObtenerDatos(){
        return service.ObtenerLibros();
    }

    @PostMapping("/InsertLibros")
    public ResponseEntity<?> NuevoLibro(@Valid @RequestBody DTOLibros json, HttpServletRequest request){
        try {
            DTOLibros respuesta = service.InsertarLibros(json);
            if (respuesta == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "Status","Inserccion Fallida",
                        "Errortype", "ValidaconError",
                        "Message", "El provedor no fue registrado"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "Status","Succes",
                    "data",respuesta
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "Status","Error",
                    "Message","Eror al registrar el libro",
                    "Detail", e.getMessage()
            ));
        }
    }

    @PutMapping("/EditLibros/{id}")
    public ResponseEntity<?> ModificarLibro(@PathVariable Long id, @Valid @RequestBody DTOLibros json, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String , String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }try {
            DTOLibros dto = service.ActualizarLibros(id,json);
            return ResponseEntity.ok(dto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "Error","Datos Duplicados",
                    "Campo", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/DeleteLibros/{id}")
    public ResponseEntity<?> EliminarLibros(@PathVariable Long id){
        try {
            if (!service.EliminarLibro(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Mensaje de error", "Libro no encontrado").body(Map.of(
                        "Error", "Not found",
                        "Mensaje","El libro no fue encontrado",
                        "TimeStamp", Instant.now().toString())
                );
            }
            return ResponseEntity.ok().body(Map.of(
                    "Status","Proceso Completado",
                    "Message","Libro Eliminado"
            ));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(Map.of(
                    "Status","Error",
                    "Message","Error al eliminar el libro",
                    "Detail", e.getMessage()
            ));
        }
    }
}
