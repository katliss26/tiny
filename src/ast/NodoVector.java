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
	private  int  valor;

    public String getIdentificador() {
        return identificador;
    }

    public NodoVector(String identificador, int valor) {
        this.identificador = identificador;
        this.valor = valor;
    }

    public NodoVector(String identificador, int valor, NodoBase hermanoDerecha) {
        super(hermanoDerecha);
        this.identificador = identificador;
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    

   

        
  


    
}
