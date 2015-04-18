/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

/**
 *
 * @author Alex
 */
public class NodoAsignacion_funcion extends NodoBase{

    private String Funcion_uno;
    private String Funcion_dos;

    public NodoAsignacion_funcion(String Funcion_uno, String Funcion_dos) {
        this.Funcion_uno = Funcion_uno;
        this.Funcion_dos = Funcion_dos;
    }

    public NodoAsignacion_funcion(String Funcion_uno, String Funcion_dos, NodoBase hermanoDerecha) {
        super(hermanoDerecha);
        this.Funcion_uno = Funcion_uno;
        this.Funcion_dos = Funcion_dos;
    }

    public String getFuncion_dos() {
        return Funcion_dos;
    }

    public void setFuncion_dos(String Funcion_dos) {
        this.Funcion_dos = Funcion_dos;
    }

    public String getFuncion_uno() {
        return Funcion_uno;
    }

    public void setFuncion_uno(String Funcion_uno) {
        this.Funcion_uno = Funcion_uno;
    }

   
 
    

   
    
   
    
    
}
