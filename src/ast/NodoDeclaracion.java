/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

/**
 *
 * @author Alex
 */
public class NodoDeclaracion extends NodoBase {

    String tipo;
    NodoBase variable;

    public NodoDeclaracion(String tipo, NodoBase variable) {
        this.tipo = tipo;
        this.variable = variable;
    }

    public NodoDeclaracion(String tipo, NodoBase variable, NodoBase hermanoDerecha) {
        super(hermanoDerecha);
        this.tipo = tipo;
        this.variable = variable;
    }

    public NodoBase getVariable() {
        return variable;
    }

    public void setVariable(NodoBase variable) {
        this.variable = variable;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
