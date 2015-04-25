package ast;

public class NodoAsignacion extends NodoBase {
	//private String identificador;
        private NodoIdentificador ID;
	private NodoBase expresion;


    public NodoAsignacion(NodoIdentificador ID, NodoBase expresion) {
        this.ID = ID;
        this.expresion = expresion;
    }
                  

	public NodoBase getExpresion() {
		return expresion;
	}

	public void setExpresion(NodoBase expresion) {
		this.expresion = expresion;
	}

    public NodoIdentificador getID() {
        return ID;
    }

    public void setID(NodoIdentificador ID) {
        this.ID = ID;
    }
	
	
	
}
