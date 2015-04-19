package ast;

public class NodoIdentificador extends NodoBase {
	private String nombre;
      
        //tipo
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

	public String getNombre() {
		return nombre;
	}

}
