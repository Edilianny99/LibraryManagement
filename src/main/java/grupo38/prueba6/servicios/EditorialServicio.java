/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupo38.prueba6.servicios;

import grupo38.prueba6.entidades.Editorial;
import grupo38.prueba6.excepciones.MiException;
import grupo38.prueba6.repositorios.EditorialRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author edili
 
 */

@Service
public class EditorialServicio {
    
    @Autowired
    EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void crearEditorial(String nombre) throws MiException{
        
        validar(nombre);
        
      Editorial editorial=new Editorial();
      
      editorial.setNombre(nombre);
      
      editorialRepositorio.save(editorial);
        
    }
    
    public List<Editorial> listarEditoriales(){
        
           List<Editorial> editoriales=new ArrayList();
           
           editoriales=editorialRepositorio.findAll();
           
           return editoriales;
    }
    
    public void modificarEditorial(String id, String nombre){
        Optional<Editorial> respuesta=editorialRepositorio.findById(id);
    
          if(respuesta.isPresent()){
              
              Editorial editorial=respuesta.get();
              
              editorial.setNombre(nombre);
              
              editorialRepositorio.save(editorial);
    
          }
    }
    
    public void validar(String nombre) throws MiException{
        if(nombre==null || nombre.isEmpty()){
            throw new MiException("El nombre no puede ser nulo ni vacío");
        }
    }
    
}
