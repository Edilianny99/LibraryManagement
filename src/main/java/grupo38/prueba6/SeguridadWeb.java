/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupo38.prueba6;

import grupo38.prueba6.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author edili
 */
//escribir los componentes necesarios para spring security
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter{
    
    @Autowired
    public UsuarioServicio usuarioServicio;
    
    @Autowired                    //con este parámetro es para indicar al servicio cual es el servicio que tiene que utilizar para autenticar un usuario 
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        
        auth.userDetailsService(usuarioServicio)
                .passwordEncoder(new BCryptPasswordEncoder());
                
         
        
        
        
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        
         http       
                 .authorizeRequests()
                     .antMatchers("/admin/*").hasRole("ADMIN")
                     .antMatchers("/css/*","/js/*","/img/*","/**")
                     .permitAll()
                 .and().formLogin()
                      .loginPage("/login")   //la pagina de login
                      .loginProcessingUrl("/logincheck") //para procesar ese inicio de sesión se usa esa url, debe coincidir con el action del formulario de logeo
                      .usernameParameter("email")
                      .passwordParameter("password")
                      .defaultSuccessUrl("/inicio")
                      .permitAll()
                 .and().logout()                         //configurar Logout salida del sistema 
                       .logoutUrl("/logout")
                       .logoutSuccessUrl("/") //si la sesión cierra con éxito entonces va a volver al index
                       .permitAll()
                 .and().csrf()
                       .disable();
    }
    
}
