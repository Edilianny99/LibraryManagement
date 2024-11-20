/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupo38.prueba6.entidades;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author edili
 */

@Entity
public class Imagen {
    
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy="uuid2")
    private String id;
    
    //el atributo que asigna el formato del archivo de la imagen 
    private String mime;
    
    private String nombre;
    
    //@Lob para hacerle saber a spring que ese archivo puede llegar a pesar mucho, o ser muy grande
    //@Basic, definimos que el tipo de carga va a ser de tipo lazy o floja 
    //Lazy, fetchType, quiere decir que el contenido en este caso el arreglo se va a cargar solamente cuando lo pidamos va a 
    @Lob  @Basic(fetch= FetchType.LAZY)
    private byte[] contenido;   //arreglo de bytes, la forma en la que se va a guardar el contenido de la imagen 
           
    public Imagen(){
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }
    
    
    
}
