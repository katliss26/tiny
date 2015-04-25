/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

/**
 *
 * @author Alex
 */
public class Nodollamar_Funcion extends NodoBase {

    

    private String identificador;  
    private NodoBase arg;
    private NodoBase exp;
    
    public Nodollamar_Funcion(String identificador) {
        this.identificador = identificador;
    }

   
    public Nodollamar_Funcion(String i,NodoBase arg) {
       
        this.identificador=i;
        this.arg = arg;
    }

    public NodoBase getExp() {
        return exp;
    }

    public void setExp(NodoBase exp) {
        this.exp = exp;
    }

    public NodoBase getArg() {
        return arg;
    }

    public void setArg(NodoBase arg) {
        this.arg = arg;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    
    
    
    
    
    
    
   
    
    
    
}
