/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

/**
 *
 * @author Alex
 */
public class NodoPrincipal extends NodoBase {
   private NodoBase funcion;
   private NodoBase contenido;
   private NodoBase block;
   private int ambito;

    public NodoPrincipal(NodoBase funcion, NodoBase contenido, NodoBase block, int ambito) {
        this.funcion = funcion;
        this.contenido = contenido;
        this.block = block;
        this.ambito = ambito;
    }

    public NodoPrincipal(NodoBase funcion, NodoBase contenido, NodoBase block, NodoBase hermanoDerecha) {
        super(hermanoDerecha);
        this.funcion = funcion;
        this.contenido = contenido;
        this.block = block;
    }

    public NodoBase getBlock() {
        return block;
    }

    public void setBlock(NodoBase block) {
        this.block = block;
    }

    public NodoBase getContenido() {
        return contenido;
    }

    public void setContenido(NodoBase contenido) {
        this.contenido = contenido;
    }

    public NodoBase getFuncion() {
        return funcion;
    }

    public void setFuncion(NodoBase funcion) {
        this.funcion = funcion;
    }
    
    public int getAmbito() {
        return ambito;
    }
    
    public void setAmbito(int ambito) {
        this.ambito = ambito;
    }

}
