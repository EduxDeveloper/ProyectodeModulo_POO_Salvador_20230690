package Salvador_20230690.Salvador_20230690.Controller;

import Salvador_20230690.Salvador_20230690.Models.DTO.DTOLibros;
import Salvador_20230690.Salvador_20230690.Services.ServicesLibros;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
