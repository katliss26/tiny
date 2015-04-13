package ast;

/**
 *
 * @author johan_000
 */
public class NodoTipoOperacion extends NodoBase {
    
    private NodoBase HijoIzquierda;
    private NodoBase HermanoExtremaDerecha;
    
    public NodoTipoOperacion(NodoBase hijoIzquierda,NodoBase hermanoExtremaDerecha){
        super();
        HijoIzquierda = hijoIzquierda; 
        HermanoExtremaDerecha = hermanoExtremaDerecha;
    }    
    
    public NodoTipoOperacion(){
        super();
        HijoIzquierda = null;
        HermanoExtremaDerecha = null;
    }

    public void setHijoIzquierda(NodoBase HijoIzquierda) {
        this.HijoIzquierda = HijoIzquierda;
    }

    public void setHermanoExtremaDerecha(NodoBase HermanoExtremaDerecha) {
        this.HermanoExtremaDerecha = HermanoExtremaDerecha;
    }

    public NodoBase getHijoIzquierda() {
        return HijoIzquierda;
    }

    public NodoBase getHermanoExtremaDerecha() {
        return HermanoExtremaDerecha;
    }
    

}