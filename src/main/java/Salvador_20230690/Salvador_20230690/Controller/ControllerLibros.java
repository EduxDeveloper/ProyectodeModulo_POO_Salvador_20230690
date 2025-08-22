package Salvador_20230690.Salvador_20230690.Controller;

import Salvador_20230690.Salvador_20230690.Models.DTO.DTOLibros;
import Salvador_20230690.Salvador_20230690.Services.ServicesLibros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController @RequestMapping("/ApiLibros")
public class ControllerLibros {

    @Autowired
    ServicesLibros service;

    @GetMapping("/GetLibros")
    public List<DTOLibros>ObtenerDatos(){
        return service.ObtenerLibros();
    }

}
