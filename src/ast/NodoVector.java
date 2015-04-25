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
    
        private NodoIdentificador identificador;
    private  int  valor;
    private NodoBase expresion;

    public NodoIdentificador getIdentificador() {
        return identificador;
    }

    public NodoBase getExpresion() {
        return expresion;
    }

    public NodoVector(NodoIdentificador identificador, NodoBase expresion) {
        this.identificador = identificador;
        this.expresion = expresion;
    }

    public NodoVector(NodoIdentificador identificador, int valor) {
        this.identificador = identificador;
        this.valor = valor;
    }

    public NodoVector(NodoIdentificador identificador, int valor, NodoBase hermanoDerecha) {
        super(hermanoDerecha);
        this.identificador = identificador;
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}
