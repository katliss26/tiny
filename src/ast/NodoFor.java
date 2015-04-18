package ast;

public class NodoFor extends NodoBase {

<<<<<<< HEAD
	private NodoBase as;
	private NodoBase ex;
	private NodoBase se;
	private NodoBase cuerpo;
	
	public NodoFor(NodoBase as,NodoBase ex,NodoBase se, NodoBase cuerpo) {
		super();
                this.cuerpo = cuerpo;
		this.as = as;
		this.ex = ex;
		this.se = se;
	}
	
	public NodoFor() {
		super();
		this.as = null;
		this.ex = null;
		this.se = null;
                this.cuerpo = null;
	}

	public NodoBase getAs() {
		return as;
	}

	public void setAs(NodoBase as) {
		this.as = as;
	}

	public NodoBase getEx() {
		return ex;
	}

	public void setEx(NodoBase ex) {
		this.ex = ex;
	}

	public NodoBase getSe() {
		return se;
	}

	public void setSe(NodoBase se) {
		this.se = se;
	}
	public NodoBase getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(NodoBase cuerpo) {
		this.cuerpo = cuerpo;
	}
=======
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
>>>>>>> 9ffbdb3569c8bdba6165506f7730643e1bc7e0a2

}
