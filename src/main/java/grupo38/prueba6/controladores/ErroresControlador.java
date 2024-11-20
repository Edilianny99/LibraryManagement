/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupo38.prueba6.controladores;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author edili
 */
@Controller
public class ErroresControlador  implements ErrorController{
    
    //el reqwuestMapping debe ir a nivel del metodo y no de la clase
    
    
    // le estamos dando la orden al metood que entre todo recurso que venga con barra error sea del metodo que sea get o post, va a ingresar al metodo 
    @RequestMapping(value= "/error", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest){ //retorna modelo y vista
        
        
        ModelAndView errorPage= new ModelAndView("error"); // "error es el nombre de la vista que se a a hcer para manejar los errores"
        
        String errorMsg="";  //va vacio dependiendo del error que se genere 
        
        int httpErrorCode=getErrorCode(httpRequest);  //recibe el codigo de error
        
        switch(httpErrorCode){
            case 400: {
                     errorMsg="El recurso solicitado no existe";
                     break;
            }
            case 403: {
                     errorMsg="No tiene permisos para acceder al recurso.";
                     break;
            }
            case 401: {
                     errorMsg="No se encuentra Autorizado";
                     break;
            }
            case 404: {
                     errorMsg="El recurso solicitado no fue encontrado";
                     break;
            }
            case 500: {
                    errorMsg="Ocurrió un error interno";
                    break;
            }
        }
        
        errorPage.addObject("codigo", httpErrorCode);
        errorPage.addObject("mensaje", errorMsg);
        
        return errorPage; 
          
    }
    
    
    /*
    Este método recibe la petición y lo que hace es
    traer la atributo del estado del codigo y castiarlo aun entero
    */
    private int getErrorCode(HttpServletRequest httpRequest){
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }
    
    public String getErrorPath(){
        return "/error.html";
    }
    
    
}
