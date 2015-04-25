/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

/**
 *
 * @author Asdrubal
 */
public class NodoBooleano extends NodoBase{
    private boolean valor;

    public NodoBooleano(boolean valor, NodoBase hermanoDerecha) {
        super(hermanoDerecha);
        this.valor = valor;
    }

    public NodoBooleano(boolean valor) {
        this.valor = valor;
    }

    public boolean getValor() {
        return valor;
    }
    
}
