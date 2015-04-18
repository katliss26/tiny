package compilador;

public class RegistroSimbolo {

    private String identificador;
    private int NumLinea;
    private int DireccionMemoria;
    private String tipo;
    private int tamano;

    public RegistroSimbolo(String identificador, int numLinea, int direccionMemoria, String tipo, int tamano) {
        super();
        this.identificador = identificador;
        NumLinea = numLinea;
        DireccionMemoria = direccionMemoria;
        this.tipo = tipo;
        this.tamano = tamano;
    }

    public String getIdentificador() {
        return identificador;
    }

    public int getNumLinea() {
        return NumLinea;
    }

    public int getDireccionMemoria() {
        return DireccionMemoria;
    }

    public String getTipo() {
        return tipo;
    }

    public int getTamano() {
        return tamano;
    }

    public void setDireccionMemoria(int direccionMemoria) {
        DireccionMemoria = direccionMemoria;
    }

}
