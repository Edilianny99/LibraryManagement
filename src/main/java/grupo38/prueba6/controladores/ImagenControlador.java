/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupo38.prueba6.controladores;

import grupo38.prueba6.entidades.Usuario;
import grupo38.prueba6.servicios.UsuarioServicio;
import java.awt.PageAttributes.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
//  import org.springframework.web.servlet.function.RequestPredicates.headers;

/**
 *
 * @author edili
 */

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
    
    @Autowired
    UsuarioServicio usuarioServicio;
    
    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenUSuario(@PathVariable String id){
        
        Usuario usuario=usuarioServicio.getOne(id);
        
        byte[] imagen = usuario.getImagen().getContenido();
        
        HttpHeaders headers= new HttpHeaders(); //estas cabeceras es para indicarle al navegador que lo que se devuelve es una imagen
        
        //tenemos que setear al header el tipo de contenido para que sepa que es una imagen 
        headers.setContentType(org.springframework.http.MediaType.IMAGE_JPEG);
        
        
        //headers es la cabecera del pedido       //el estado en el que termina una solicitud http
        return new ResponseEntity<>(imagen,headers,HttpStatus.OK);  //este metodo nos va a volver la imagen que está vinculada a un usuario
    }
    
}
