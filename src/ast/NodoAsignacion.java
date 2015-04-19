package ast;

public class NodoAsignacion extends NodoBase {
	//private String identificador;
        private NodoIdentificador ID;
	private NodoBase expresion;
<<<<<<< HEAD
=======

	
	public NodoAsignacion(String identificador) {
		super();
		this.identificador = identificador;
		this.expresion = null;
	}
	public NodoAsignacion(String identificador, NodoBase expresion) {
		super();
		this.identificador = identificador;
		this.expresion = expresion;
	}
>>>>>>> da5c2001c62295eb3704df7daee2b1a5d53ae787


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
