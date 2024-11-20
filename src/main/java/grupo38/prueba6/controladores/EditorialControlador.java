/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupo38.prueba6.controladores;

import grupo38.prueba6.entidades.Editorial;
import grupo38.prueba6.excepciones.MiException;
import grupo38.prueba6.servicios.EditorialServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author edili
 */

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/registrar")
    private String registrar(){
        return "Editorial_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo){
           
       modelo.put("exito", "se ha cargado el editorial exitosamente");
        try {
            editorialServicio.crearEditorial(nombre);
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            Logger.getLogger(EditorialControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "editoria_form.html";
        }
        
        return "index.html";
        
    }
    
    @GetMapping("/lista")
    public String lista(ModelMap modelo){
        
        List<Editorial> editoriales=editorialServicio.listarEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        
        return "editorial_list.html";
        
    }
    
    
}
