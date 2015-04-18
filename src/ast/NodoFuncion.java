/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

/**
 *
 * @author Alex
 */
public class NodoFuncion extends NodoBase {
    private String tipo;
    private NodoIdentificador identificador;
    private NodoBase argumento;

    public NodoFuncion(String tipo, NodoIdentificador identificador) {
        this.tipo = tipo;
        this.identificador = identificador;   
    }
    public NodoFuncion(String tipo, NodoIdentificador identificador, NodoBase variable, NodoBase hermanoDerecha) {
        super(hermanoDerecha);
        this.tipo = tipo;
        this.identificador = identificador;
        this.argumento = variable;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the identificador
     */
    public NodoIdentificador getIdentificador() {
        return identificador;
    }

    /**
     * @param identificador the identificador to set
     */
    public void setIdentificador(NodoIdentificador identificador) {
        this.identificador = identificador;
    }

    /**
     * @return the argumento
     */
    public NodoBase getArgumento() {
        return argumento;
    }

    /**
     * @param argumento the argumento to set
     */
    public void setArgumento(NodoBase argumento) {
        this.argumento = argumento;
    }
   

    

    
    
    
   
    
    
    
}
