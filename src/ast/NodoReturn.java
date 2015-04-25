/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

/**
 *
 * @author Alex
 */
public class NodoReturn extends NodoBase {
    NodoBase exp;

    public NodoReturn(NodoBase exp) {
        this.exp = exp;
    }

    public NodoReturn(NodoBase exp, NodoBase hermanoDerecha) {
        super(hermanoDerecha);
        this.exp = exp;
    }

    public NodoBase getExp() {
        return exp;
    }

    public void setExp(NodoBase exp) {
        this.exp = exp;
    }
    
    
    
}
