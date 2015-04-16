package ast;

public class NodoFor extends NodoBase {

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
    }

    public void setSentencias(NodoBase sentencias) {
        this.sentencias = sentencias;
    }
	


}
