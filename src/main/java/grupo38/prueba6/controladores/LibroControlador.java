/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupo38.prueba6.controladores;

import grupo38.prueba6.entidades.Autor;
import grupo38.prueba6.entidades.Editorial;
import grupo38.prueba6.entidades.Libro;
import grupo38.prueba6.excepciones.MiException;
import grupo38.prueba6.servicios.AutorServicio;
import grupo38.prueba6.servicios.EditorialServicio;
import grupo38.prueba6.servicios.LibroServicio;
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
@RequestMapping("/libro")
public class LibroControlador {
    
    @Autowired 
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;
    
    
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo){ //se usa el modelmap para proyectar lo que necesitemos, en este caso vamos a enviar la lista a la vista
        
        List<Autor> autores=autorServicio.listarAutores();
        List<Editorial> editoriales=editorialServicio.listarEditoriales();
        
        // ya al tener la lista, se debe ahora anclar all modelo para enviarlo a la vista
        //y se va a enviar la lista a a vista con el nombre que aparece entre comillas, esa será la variable, en este caso "autores"
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        
        return "libro_form.html";
    }
    
    
    @PostMapping("/registro")  //Se use el required=false para que no importa si viene vació el parámetro y no de error antes de la excepción
    public String registro(@RequestParam(required=false) Long isbn,@RequestParam String titulo, 
            @RequestParam(required=false) Integer ejemplares,@RequestParam String idAutor,
            @RequestParam String idEditorial, ModelMap modelo){ //modemap sirve para que insertemos en ese modelo todo llo que queramos mostrar en pantalla
        
        
        try {
            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
             modelo.put("exito", "El libro se ha cargado correctamente");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
           // Logger.getLogger(LibroControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "libro_form.html"; //volvemos a cargar el formulario
        }
        return "index.html";
                      
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Libro> libros= libroServicio.listarLibros();
        modelo.addAttribute("libros", libros);
        
        return "libro_list.html";
    }
}
