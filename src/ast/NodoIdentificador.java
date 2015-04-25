package ast;

public class NodoIdentificador extends NodoBase {
	private String nombre;
        private String tipo;

    public NodoIdentificador(String nombre, NodoBase hermanoDerecha) {
        super(hermanoDerecha);
        this.nombre = nombre;
    }

	public NodoIdentificador(String nombre) {
		super();
		this.nombre = nombre;
	}

	public NodoIdentificador() {
		super();
	}
        //arg funciones
        public NodoIdentificador(String tipo, String nombre, NodoBase hermanoDerecha) {
		super(hermanoDerecha);
		this.nombre = nombre;
                this.tipo = tipo;
                
	}

	public String getNombre() {
		return nombre;
	}
        public String getTipo() {
		return tipo;
	}

}
