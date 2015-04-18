/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

/**
 *
 * @author Alex
 */
public class NodoAsignacion_Vector extends NodoBase{
    private NodoVector v;
    private NodoBase expresion;

    public NodoAsignacion_Vector(NodoVector v, NodoBase expresion) {
        this.v = v;
        this.expresion = expresion;
    }

    public NodoAsignacion_Vector(NodoVector v, NodoBase expresion, NodoBase hermanoDerecha) {
        super(hermanoDerecha);
        this.v = v;
        this.expresion = expresion;
    }

    public NodoBase getExpresion() {
        return expresion;
    }

    public void setExpresion(NodoBase expresion) {
        this.expresion = expresion;
    }

    public NodoVector getV() {
        return v;
    }

    public void setV(NodoVector v) {
        this.v = v;
    }
    
}
