package ast;

public class NodoOperador extends NodoBase {
	private NodoBase expresionI;
	private NodoBase expresionD;
	private tipoOp operador;

	public NodoOperador(NodoBase expresionI,tipoOp tipoOperador, NodoBase expresionD){
		super();
		this.expresionI = expresionI;
		this.expresionD = expresionD;
		this.operador = tipoOperador;
	}
	
	public NodoOperador(tipoOp tipoOperador){
		super();
		this.expresionI = null;
		this.expresionD = null;
		this.operador = tipoOperador;		
	}

	public NodoBase getExpresionI() {
		return expresionI;
	}

	public void setExpresionI(NodoBase expresionI) {
		this.expresionI = expresionI;
	}

	public NodoBase getExpresionD() {
		return expresionD;
	}

	public void setExpresionD(NodoBase expresionD) {
		this.expresionD = expresionD;
	}

	public tipoOp getOperador() {
		return operador;
	}

	public void setOperador(tipoOp operador) {
		this.operador = operador;
	}
	
}