package ast;

public class NodoFuncion extends NodoBase {
    private NodoBase parametro;
    private NodoBase tipoParametro;
    private NodoBase sentencia;
    private NodoBase tipofuncion;
    
    public NodoFuncion(NodoBase parametro, NodoBase tipoParametro){
        super();
        this.parametro = parametro;
        this.tipoParametro = tipoParametro;
        this.sentencia = sentencia;
        this.tipofuncion = tipofuncion;
    }
    
    public NodoFuncion(NodoBase parametro){
        super();
        this.parametro = null;
        this.tipoParametro = null;
        this.sentencia = null;
        this.tipofuncion = null;
    }
}
