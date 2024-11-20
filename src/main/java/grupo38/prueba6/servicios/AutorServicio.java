/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupo38.prueba6.servicios;

import grupo38.prueba6.entidades.Autor;
import grupo38.prueba6.excepciones.MiException;
import grupo38.prueba6.repositorios.AutorRepositorio;
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
public class AutorServicio {
    @Autowired
    AutorRepositorio autorRepositorio;
    
    
    @Transactional
    public void crearAutor(String nombre) throws MiException{
        validar(nombre);
        Autor autor=new Autor();
        
        autor.setNombre(nombre);
        
        autorRepositorio.save(autor);
    }
    
    public List<Autor> listarAutores(){
        List<Autor> autores=new ArrayList();
        
        autores=autorRepositorio.findAll();
        
        return autores;
    }
    
    public void modificarAutor(String nombre,String id) throws MiException{
        
        validar(nombre);
        Optional<Autor> respuesta=autorRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            
            Autor autor=respuesta.get();
            autor.setNombre(nombre);
            autor.setId(id);
            
            autorRepositorio.save(autor);
        }
        
    }
    
    public Autor getOne(String id){
        return autorRepositorio.getOne(id);
    }
    
    public void validar(String nombre) throws MiException{
        if(nombre.isEmpty() || nombre==null){
            throw new MiException("El nombre no puede ser vació o nulo");
        }
    }
}
