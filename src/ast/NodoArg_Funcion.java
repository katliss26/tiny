/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ast;

/**
 *
 * @author KatherinC
 */
public class NodoArg_Funcion extends NodoBase{
    NodoBase arg;

    public NodoArg_Funcion(NodoBase arg, NodoBase hermanoDerecha) {
        super(hermanoDerecha);
        this.arg = arg;
    }

    public NodoArg_Funcion(NodoBase arg) {
        this.arg = arg;
    }

    public NodoBase getArg() {
        return arg;
    }

    public void setArg(NodoBase arg) {
        this.arg = arg;
    }
    
    
    
}
