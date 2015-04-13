/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

/**
 *
 * @author Alex
 */
public class NodoVector extends NodoBase{
    
        private String identificador;
	private NodoBase expresion;
        
    public NodoVector(String identificador, NodoBase expresion) {
        this.identificador = identificador;
        this.expresion = expresion;
    }

    public NodoVector(String identificador, NodoBase expresion, NodoBase hermanoDerecha) {
        super(hermanoDerecha);
        this.identificador = identificador;
        this.expresion = expresion;  
    }

    public NodoBase getExpresion() {
        return expresion;
    }

    public void setExpresion(NodoBase expresion) {
        this.expresion = expresion;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

   

  


    
}
