package compilador;

import java.util.*;

import ast.NodoAsignacion;
import ast.NodoAsignacion_Vector;
import ast.NodoBase;
import ast.NodoEscribir;
import ast.NodoFor;
import ast.NodoFuncion;
import ast.NodoIdentificador;
import ast.NodoIf;
import ast.NodoLeer;
import ast.NodoOperacion;
import ast.NodoPrincipal;
import ast.NodoRepeat;
import ast.NodoValor;
import ast.NodoVector;
import ast.NodoDeclaracion;

public class TablaSimbolos {

<<<<<<< HEAD
	public void cargarTabla(NodoBase raiz){
		while (raiz != null) {
	    if (raiz instanceof NodoIdentificador){
	    	InsertarSimbolo(((NodoIdentificador)raiz).getNombre(),-1);
	    	//TODO: Anadir el numero de linea y localidad de memoria correcta
	    }
=======
    private HashMap<String, HashMap<String, RegistroSimbolo>> tabla;
    private HashMap<String, RegistroSimbolo> ambito;
    private String tipo;
    private int direccion;  //Contador de las localidades de memoria asignadas a la tabla
    private int memoria;  //Tamano que ocupa la variable

    public TablaSimbolos() {
        super();
        tabla = new HashMap<String, HashMap<String, RegistroSimbolo>>();
        ambito = new HashMap<String, RegistroSimbolo>();
        direccion = 0;
        tipo = "";
    }

    public void cargarTabla(NodoBase raiz) {
        while (raiz != null) {
            if (raiz instanceof NodoIdentificador) {
                InsertarSimbolo(((NodoIdentificador) raiz).getNombre(), -1);
                //TODO: Añadir el numero de linea y localidad de memoria correcta
            }
            /* Hago el recorrido recursivo */
            if (raiz instanceof NodoIf) {
                cargarTabla(((NodoIf) raiz).getPrueba());
                cargarTabla(((NodoIf) raiz).getParteThen());
                if (((NodoIf) raiz).getParteElse() != null) {
                    cargarTabla(((NodoIf) raiz).getParteElse());
                }
            } else if (raiz instanceof NodoRepeat) {
                cargarTabla(((NodoRepeat) raiz).getCuerpo());
                cargarTabla(((NodoRepeat) raiz).getPrueba());
            }/* else if (raiz instanceof NodoAsignacion) {
             cargarTabla(((NodoAsignacion) raiz).getExpresion());
             } else if (raiz instanceof NodoEscribir) {
             cargarTabla(((NodoEscribir) raiz).getExpresion());
             } else if (raiz instanceof NodoOperacion) {
             cargarTabla(((NodoOperacion) raiz).getOpIzquierdo());
             cargarTabla(((NodoOperacion) raiz).getOpDerecho());
             }*/ else if (raiz instanceof NodoFuncion) {
                //lostiene?cargarTabla(((NodoFuncion)raiz).getHermanoDerecha());
                tipo = ((NodoFuncion) raiz).getTipo();
                cargarTabla(((NodoFuncion) raiz).getIdentificador());
                if (((NodoFuncion) raiz).getArgumento() != null) {
                    cargarTabla(((NodoFuncion) raiz).getArgumento());
                }
            } else if (raiz instanceof NodoFor) {
                cargarTabla(((NodoFor) raiz).getSentencias());
            } else if (raiz instanceof NodoDeclaracion) {
                tipo = ((NodoDeclaracion) raiz).getTipo();
                cargarTabla(((NodoDeclaracion) raiz).getVariable());
            } /*else if (raiz instanceof NodoAsignacion_Vector) {
             //cargarTabla(((NodoOperacion)raiz).getOpIzquierdo());
             //cargarTabla(((NodoOperacion)raiz).getOpDerecho());	   	
             } else if (raiz instanceof NodoLeer) {
             cargarTabla(((NodoLeer) raiz).getIdentificador());
             } else if (raiz instanceof NodoVector) {
             //cargarTabla(((NodoOperacion)raiz).getOpIzquierdo());
             //cargarTabla(((NodoOperacion)raiz).getOpDerecho());	   	
             }*/ else if (raiz instanceof NodoValor) {
                //cargarTabla(((NodoOperacion)raiz).getOpIzquierdo());
                //cargarTabla(((NodoOperacion)raiz).getOpDerecho());	   	
            } else if (raiz instanceof NodoPrincipal) {
                tipo = "";
                ambito = new HashMap<String, RegistroSimbolo>();
                if (((NodoPrincipal) raiz).getFuncion() != null) {
                    cargarTabla(((NodoPrincipal) raiz).getFuncion());
                }
                cargarTabla(((NodoPrincipal) raiz).getContenido());
                tabla.put("ambito" + (((NodoPrincipal) raiz).getAmbito()), ambito);
                if (((NodoPrincipal) raiz).getBlock() != null) {
                    cargarTabla(((NodoPrincipal) raiz).getBlock());
                }
            }

            raiz = raiz.getHermanoDerecha();
        }
    }

    //true es nuevo no existe se insertara, false ya existe NO se vuelve a insertar 
    public boolean InsertarSimbolo(String identificador, int numLinea) {
        RegistroSimbolo simbolo;
        if (ambito.containsKey(identificador)) {//
            System.out.println(identificador + ": repetido");
            return false;
        } else {
            simbolo = new RegistroSimbolo(identificador, numLinea, direccion++, tipo, memoria);
            ambito.put(identificador, simbolo);
            return true;
        }
    }

    public RegistroSimbolo BuscarSimbolo(String identificador) {
        RegistroSimbolo simbolo = (RegistroSimbolo) ambito.get(identificador);
        return simbolo;
    }

    public void ImprimirClaves() {
        System.out.println("*** Tabla de Simbolos ***");
        for (Iterator<String> it = tabla.keySet().iterator(); it.hasNext();) {
            String s = (String) it.next();
            //System.out.println("Consegui Key: " + s + " con direccion: " + BuscarSimbolo(s).getDireccionMemoria());
        }
    }
>>>>>>> 9ffbdb3569c8bdba6165506f7730643e1bc7e0a2

    public int getDireccion(String Clave) {
        return BuscarSimbolo(Clave).getDireccionMemoria();
    }

}
