/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupo38.prueba6.controladores;

import grupo38.prueba6.entidades.Autor;
import grupo38.prueba6.excepciones.MiException;
import grupo38.prueba6.servicios.AutorServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author edili
 */
@Controller
@RequestMapping("/autor") //localhost:8080/autor
public class AutorControlador {
    
    @Autowired
    private AutorServicio autorServicio;
    
    
    @GetMapping("/registrar") //localhost:8080/autor/registrar
    public String registrar(){
        return "autor_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) { //para indicarlo al controlodaro que ese es un parámetro que va a viajar por la url se marca como requestparam
        //RequestParam es para indicar que es un parámetro requerido y que va a llegar cuando se ejecute el formulario 
        System.out.println("Nombre: "+nombre);
        modelo.put("Exito", "Se ha cargado correctamente");
        try {
            autorServicio.crearAutor(nombre);
        } catch (MiException ex) {
            modelo.put("Error", ex.getMessage());
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
               return "autor_form.html";
        }
        return "index.html"; //se pide que se retorne la pagina la presionar el boton de enviar 
    }
    
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
        List<Autor> autores=autorServicio.listarAutores();
        
        modelo.addAttribute("autores", autores);
        
        return "autor_list.html";
    }
    
    @GetMapping("/modificar/{id}") //path variable, indica que va a llegar un id a traves de la url
    public String modificar(@PathVariable String id, ModelMap modelo){ //estamos indicando que la variable id es una varibale path (ruta) y que va a viajar por la url
        modelo.put("autor", autorServicio.getOne(id));
        return "autor_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,String nombre, ModelMap modelo) {
        try {
            autorServicio.modificarAutor(nombre, id);
            return "redirect:../lista";
        } catch (MiException ex) {
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", ex.getMessage());
            return "autor_modificar.html";
        }
    }
    
      
}
