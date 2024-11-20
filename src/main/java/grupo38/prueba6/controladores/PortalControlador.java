/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupo38.prueba6.controladores;

import grupo38.prueba6.entidades.Usuario;
import grupo38.prueba6.excepciones.MiException;
import grupo38.prueba6.servicios.UsuarioServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author edili
 */

//el controlador portal inicial de la aplicación
@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("/")
      public String index(){
          
          return "index.html";
      }
    
    
      @GetMapping("/registrar")
      public String registrar(){
          return "registro.html";
      }
      
      @PostMapping("/registro")
      public String registro(@RequestParam String nombre, @RequestParam String email,
             @RequestParam String password, @RequestParam String password2, ModelMap modelo, MultipartFile archivo){
        try {
            usuarioServicio.registrar(archivo,nombre, email, password, password2);
            modelo.put("exito", "usuario registrado correctamente");
            return "index.html";
            
        } catch (MiException ex) {
            
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "registro.html";
        }
      }
      
      @GetMapping("/login")    //el parametro puede venir o no por eso va false
      public String login(@RequestParam(required= false) String error, ModelMap modelo){
           if(error != null){
               modelo.put("error", "usuario o contraseña inválido");
           }
          
          
          return "login.html";
      }
      
      
      //Esta anotación hace que la persona deba cumplir con un rol activo, user o admin
      @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")     // le estamos diciendo a spring security que autorice el acceso al metodo bajo determinadas reglas
      @GetMapping("/inicio")
      public String inicio(HttpSession sesion){ //el metodo va a recibir un ibjeto de httpsesion para saber que ventana abrir si la del user o admin
          
          //va a recibir todos los datos de la sesión
          Usuario logueado=(Usuario) sesion.getAttribute("usuariosession");
          
          if(logueado.getRol().toString().equals("ADMIN")){
              return "redirect:/admin/dashboard";
          }
          
          return "inicio.html";
      }
      
      @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
      @GetMapping("/perfil")
      public String perfil(ModelMap modelo, HttpSession sesion){
             Usuario usuario=(Usuario) sesion.getAttribute("usuariosession");
             modelo.put("usuario", usuario);
             return "usuario_modificar.html";
      }
      
      
      @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
      @PostMapping("/perfil/{id}")
      public String actualizar(MultipartFile archivo,@PathVariable String id, @RequestParam String nombre,
                               @RequestParam String email,@RequestParam String password, @RequestParam String password2, ModelMap modelo){
          
                    try{
                       usuarioServicio.modificar(archivo, id, nombre, email, password, password2);
                       modelo.put("exito", "Usuario actualizado exitosamente");
                       return "inicio.html";
                    }catch(MiException ex){
                        
                        modelo.put("error", ex.getMessage());
                        modelo.put("nombre", nombre);
                        modelo.put("email", email);
                        
                        return "usuario_modificar.html";
                        
                    }
      
      }
      
      
}
