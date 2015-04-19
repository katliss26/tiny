package ast;

public class NodoFor extends NodoBase {

<<<<<<< HEAD
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
=======
	private NodoBase incio;
	private NodoBase compara;
	private NodoBase automento;
	private NodoBase sentencias;

    public NodoFor(NodoBase incio, NodoBase compara, NodoBase automento, NodoBase sentencias) {
        this.incio = incio;
        this.compara = compara;
        this.automento = automento;
        this.sentencias = sentencias;
    }

    public NodoFor(NodoBase incio, NodoBase compara, NodoBase automento, NodoBase sentencias, NodoBase hermanoDerecha) {
        super(hermanoDerecha);
        this.incio = incio;
        this.compara = compara;
        this.automento = automento;
        this.sentencias = sentencias;
    }

    public NodoBase getAutomento() {
        return automento;
    }

    public void setAutomento(NodoBase automento) {
        this.automento = automento;
    }

    public NodoBase getCompara() {
        return compara;
    }

    public void setCompara(NodoBase compara) {
        this.compara = compara;
    }

    public NodoBase getIncio() {
        return incio;
    }

    public void setIncio(NodoBase incio) {
        this.incio = incio;
    }

    public NodoBase getSentencias() {
        return sentencias;
>>>>>>> da5c2001c62295eb3704df7daee2b1a5d53ae787
    }

    public void setSentencias(NodoBase sentencias) {
        this.sentencias = sentencias;
    }
<<<<<<< HEAD
=======
	

>>>>>>> da5c2001c62295eb3704df7daee2b1a5d53ae787

}
