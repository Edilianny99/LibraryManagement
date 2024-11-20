/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupo38.prueba6.servicios;

import grupo38.prueba6.entidades.Autor;
import grupo38.prueba6.entidades.Editorial;
import grupo38.prueba6.entidades.Libro;
import grupo38.prueba6.excepciones.MiException;
import grupo38.prueba6.repositorios.AutorRepositorio;
import grupo38.prueba6.repositorios.EditorialRepositorio;
import grupo38.prueba6.repositorios.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author edili
 */

/*
Permiitir crear,editr, habilitar o deshabilitar un libro 

*/
@Service
public class LibroServicio {
    
    @Autowired
    private LibroRepositorio libroRepositorio;
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException{
        
          validar(isbn,titulo,ejemplares,idAutor,idEditorial);
       
        Autor autor=autorRepositorio.findById(idAutor).get();
        
        Editorial editorial=editorialRepositorio.findById(idEditorial).get();
        
        Libro libro = new Libro();
        
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        
        libro.setAlta(new Date());
        
        libro.setAutor(autor);
        
        libro.setEditorial(editorial);
        
        libroRepositorio.save(libro);
    }
    
    public List<Libro> listarLibros(){
        
        List<Libro> libros=new ArrayList();
        
        libros=libroRepositorio.findAll(); //encuentra todos los libros que se encuentran en libro repositorio
        
        return libros;
    }
    
    
    @Transactional
    public void modificarLibro(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) throws MiException{
       
         validar(isbn,titulo,ejemplares,idAutor,idEditorial);
        
         Optional<Libro> respuesta=libroRepositorio.findById(isbn);
         Optional<Autor> respuestaAutor= autorRepositorio.findById(idAutor);
         Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);
         
         Autor autor= new Autor();
         Editorial editorial=new Editorial();
         
         
         if(respuestaAutor.isPresent()){
             autor=respuestaAutor.get();
         }
         
         if(respuestaEditorial.isPresent()){
             editorial=respuestaEditorial.get();
         }
         
         if(respuesta.isPresent()){
             Libro libro=respuesta.get();
             
             libro.setTitulo(titulo);
             
             libro.setAutor(autor);
             
             libro.setEditorial(editorial);
             
             libro.setEjemplares(ejemplares);
             
             libroRepositorio.save(libro);
         }
        
    }
    
    private void validar (Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial)throws MiException{
         
        if(isbn ==null){  //min 6.17
            throw new MiException("El isbn no puede ser nulo");
        }
        
        if(titulo.isEmpty() || titulo==null){
            throw new MiException("El titulo no puede ser nulo o estár vació");
        }
        
        if(ejemplares==null){
            throw new MiException("Ejemplares no puede ser nulo");
        }
        
        if(idAutor.isEmpty() || idAutor==null){
            throw new MiException("El id del autor no puede ser nulo o estar vacío");
        }
        
        if(idEditorial.isEmpty() || idEditorial==null){
            throw new MiException("El id del editorial no puede ser nulo o estar vacío");
        }
    }
    
}
