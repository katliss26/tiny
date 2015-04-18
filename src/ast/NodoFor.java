package ast;

public class NodoFor extends NodoBase {

    private NodoBase inicio;
    private NodoBase compara;
    private NodoBase aumento;
    private NodoBase sentencias;

    public NodoFor(NodoBase inicio, NodoBase compara, NodoBase aumento, NodoBase sentencia) {
        super();
        this.inicio = inicio;
        this.compara = compara;
        this.aumento = aumento;
        this.sentencias = sentencia;
    }

    public NodoFor() {
        super();
        this.sentencias = null;
        this.inicio = null;
        this.compara = null;
        this.aumento = null;
    }

    public NodoBase getInicio() {
        return inicio;
    }

    public NodoBase getCompara() {
        return compara;
    }

    public NodoBase getAumento() {
        return aumento;
    }

    public NodoBase getSentencias() {
        return sentencias;
    }

    public void setInicio(NodoBase inicio) {
        this.inicio = inicio;
    }

    public void setCompara(NodoBase compara) {
        this.compara = compara;
    }

    public void setAumento(NodoBase aumento) {
        this.aumento = aumento;
    }

    public void setSentencias(NodoBase sentencias) {
        this.sentencias = sentencias;
    }

}
