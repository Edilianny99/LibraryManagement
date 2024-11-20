/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupo38.prueba6.servicios;

import grupo38.prueba6.entidades.Imagen;
import grupo38.prueba6.entidades.Usuario;
import grupo38.prueba6.enumeraciones.Rol;
import grupo38.prueba6.excepciones.MiException;
import grupo38.prueba6.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author edili
 */

@Service
public class UsuarioServicio implements UserDetailsService{
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private ImagenServicio imagenServicio;
    
    @Transactional
    public void registrar (MultipartFile archivo,String nombre, String email, String password, String password2) throws MiException{
        validar(nombre,email,password,password2);
        
        Usuario usuario=new Usuario();
        
        usuario.setNombre(nombre);
        
        usuario.setEmail(email);
        
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        
        usuario.setRol(Rol.USER);
        
        Imagen imagen=imagenServicio.guardar(archivo);
        
        usuario.setImagen(imagen);
        
        usuarioRepositorio.save(usuario);
        
    }
    
    
    
    
    public void validar(String nombre, String email, String password, String password2) throws MiException{
        if(nombre.isEmpty() || nombre==null){
            throw new MiException("El nombre no puede ser nulo ni estar vacío");
        }
        
        if(email.isEmpty() || email==null){
            throw new MiException("El email no puede ser nulo ni estar vacío");
        }
        
        if(password.isEmpty() || password==null || password.length()<=5){
            throw new MiException("La contraseña no puede estar vacia y debe contener mas de 5 digitos");
            
        }
        
        if(!password2.equals(password)){
            throw new MiException("Las contraseñas deben ser iguales");
        }
    }

    
    
    @Override                           //se usa esto para autenticar el usuario
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { 
   
            Usuario usuario= usuarioRepositorio.buscarPorEmail(email);
            
            if(usuario != null){ //si el usuario es distinto de nulo, vamos a trabajar con spring security
                
                List<GrantedAuthority> permisos= new ArrayList();
                
                
                //crear permisos para un usuario
                GrantedAuthority p= new SimpleGrantedAuthority("ROLE_"+usuario.getRol().toString());
                
                permisos.add(p);
                
                //llamada para atrapar al usuario que ya está autenticado
                //lamada al request      attr es atributo en ingles, llamada del contexto y  pedir los atributos del contexto actual
                ServletRequestAttributes attr= (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                
                //vamos a guardarlo en un objeto del httpSesion, vamos a guardar el allamado que nos trae el atributo y en base a eso la sesión
                HttpSession sesion=attr.getRequest().getSession(true);
                
                //y en esa sesion vamo a setiar los atributos, la llave con el valor con el que va a viajar a la vista
                sesion.setAttribute("usuariosession", usuario); //lleva el valor con todo los datos del usuario autenticado 
                
               return new User(usuario.getEmail(),usuario.getPassword(),permisos);
                
            }else{
                return null;
            }
    }
    
    
    public Usuario getOne(String id){
        return usuarioRepositorio.getOne(id);
    }
    
    
    @Transactional
    public void modificar(MultipartFile archivo, String id, String nombre, String email, String password, String password2 ) throws MiException{
        
        validar(nombre,email,password,password2);
        Optional<Usuario> respuesta=usuarioRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Usuario usuario=respuesta.get();
            
            usuario.setEmail(email);
            usuario.setNombre(nombre);
            usuario.setPassword(password);
            Imagen imagen=imagenServicio.guardar(archivo);
            usuario.setImagen(imagen);
            
            usuarioRepositorio.save(usuario);
        }
        
    }
    
    
    public List<Usuario> listarUsuario(){
        
        List<Usuario> usuarios= new ArrayList();
        
        usuarios=usuarioRepositorio.findAll();
        
        return usuarios;
             
    }
    
    
    @Transactional
    public void cambiarRol(String id){
         
        Optional<Usuario> respuesta= usuarioRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            
            Usuario usuario=respuesta.get();
            
            if(usuario.getRol().equals(Rol.USER)){
                usuario.setRol(Rol.ADMIN);
            
            }else if(usuario.getRol().equals(Rol.ADMIN)){
                usuario.setRol(Rol.USER);
            }
        }
        
    }
    
}
