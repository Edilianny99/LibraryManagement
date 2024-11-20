/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupo38.prueba6.controladores;

import grupo38.prueba6.entidades.Usuario;
import grupo38.prueba6.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author edili
 */

@Controller
@RequestMapping("/admin")
public class AdminControlador {
    
    @Autowired
    UsuarioServicio usuarioServicio;
    
    
    @GetMapping("/dashboard")  //se suele usar dashboard para todo panel que usen los adm
    public String panelAdministrativo(){
        return "panel.html";
    }
    
    @GetMapping("/usuarios")
    public String listar(ModelMap modelo){
        List<Usuario> usuarios=usuarioServicio.listarUsuario();
        modelo.addAttribute("usuarios", usuarios);
        
        return"usuario_list";
    }
    
    
    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable String id){
         usuarioServicio.cambiarRol(id);
         
         return "redirect:/admin/usuarios";
    }
}
