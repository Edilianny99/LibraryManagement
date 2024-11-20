/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupo38.prueba6.servicios;

import grupo38.prueba6.entidades.Imagen;
import grupo38.prueba6.excepciones.MiException;
import grupo38.prueba6.repositorios.ImagenRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author edili
 */

@Service
public class ImagenServicio {
    
    
    @Autowired
    private ImagenRepositorio imagenRepositorio;
           
    
    
                         //este tipo de dato es en el que se va a guardar la imagen
    public Imagen guardar(MultipartFile archivo) throws MiException{
        
        if(archivo != null){
            try{
                Imagen imagen=new Imagen();
                imagen.setMime(archivo.getContentType()); //estamos extrayendo el tipo de contenido 
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                
                return imagenRepositorio.save(imagen);
            
            }catch(Exception ex){
                System.out.println(ex.getMessage());
                
            }
            
        }
        
        return null;
        
    }
    
    public Imagen actualizar(MultipartFile archivo, String idImagen)throws MiException{
        if(archivo != null){
            try{
               Imagen imagen=new Imagen();
               
               if(idImagen != null ){
                   //vamos a crear un option que nos va a traer esta imagen del repositorio porque ya exite para remmpalzarla 
                   Optional<Imagen> respuesta=imagenRepositorio.findById(idImagen);
                   
                   if(respuesta.isPresent()){
                       imagen=respuesta.get();
                   }
               }
               
               imagen.setMime(archivo.getContentType());
               imagen.setNombre(archivo.getName());
               imagen.setContenido(archivo.getBytes());
               
              
              return  imagenRepositorio.save(imagen);
                
  
            }catch(Exception ex){
                
            }
        }
        
        return null;
        
        
    }
    
}
