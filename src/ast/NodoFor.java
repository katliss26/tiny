package ast;

public class NodoFor extends NodoBase {

	private NodoBase as;
	private NodoBase ex;
	private NodoBase se;
	private NodoBase cuerpo;
	
	public NodoFor(NodoBase as,NodoBase ex,NodoBase se,NodoBase cuerpo) {
		super();
		this.as = as;
		this.ex = ex;
		this.se = se;
		this.cuerpo = cuerpo;
	}
	
	public NodoFor() {
		super();
		this.cuerpo = null;
		this.as = null;
		this.ex = null;
		this.se = null;
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


}
