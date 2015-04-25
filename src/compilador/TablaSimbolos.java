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

    private HashMap<String, HashMap<String, RegistroSimbolo>> tabla;
    private HashMap<String, RegistroSimbolo> ambito;
    private String tipo;
    private String nombre_ambito;
    private int direccion;  //Contador de las localidades de memoria asignadas a la tabla
    private int bandera;  //Contador de las localidades de memoria asignadas a la tabla
    private int memoria;  //Tamano que ocupa la variable

    public TablaSimbolos() {
        super();
        tabla = new HashMap<String, HashMap<String, RegistroSimbolo>>();
        ambito = new HashMap<String, RegistroSimbolo>();
        direccion = 0;
        bandera = 0;
        memoria = 0;
        tipo = "";
        nombre_ambito = "";
    }

    public void cargarTabla(NodoBase raiz) {
        while (raiz != null) {
            if (raiz instanceof NodoIdentificador) {
                if(bandera == 1){
                   nombre_ambito = ((NodoIdentificador)raiz).getNombre();
                   bandera =0;
               }               
                if(((NodoIdentificador) raiz).getTipo() != null) 
                    tipo = ((NodoIdentificador) raiz).getTipo();
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
                bandera = 1;
                tipo = ((NodoFuncion) raiz).getTipo();
                cargarTabla(((NodoFuncion) raiz).getIdentificador());
                if (((NodoFuncion) raiz).getArgumento() != null)
                    cargarTabla(((NodoFuncion) raiz).getArgumento()); 
                
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
             }*/ else if (raiz instanceof NodoVector) {
                memoria = ((NodoVector)raiz).getValor();                
                cargarTabla(((NodoVector)raiz).getIdentificador());
                direccion= direccion + (memoria - 1);
             } else if (raiz instanceof NodoValor) {
                //cargarTabla(((NodoOperacion)raiz).getOpIzquierdo());
                //cargarTabla(((NodoOperacion)raiz).getOpDerecho());	   	
            } else if (raiz instanceof NodoPrincipal) {
                tipo = "";
                ambito = new HashMap<String, RegistroSimbolo>();
                if (((NodoPrincipal) raiz).getFuncion() != null) {
                    cargarTabla(((NodoPrincipal) raiz).getFuncion());
                }
                cargarTabla(((NodoPrincipal) raiz).getContenido());
                if(nombre_ambito == ""){
                    nombre_ambito = "main";
                }
                tabla.put(nombre_ambito, ambito);
                nombre_ambito = "";
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

    public RegistroSimbolo BuscarSimbolo(String ambito, String identificador) {        
        RegistroSimbolo simbolo = (RegistroSimbolo) tabla.get(ambito).get(identificador);
        return simbolo;
    }

    public void ImprimirClaves() {
        System.out.println("*** Tabla de Simbolos ***");
        for (Iterator<String> it = tabla.keySet().iterator(); it.hasNext();) {
            String s = (String) it.next();
            //System.out.println("Consegui Key: " + s + " con direccion: " + BuscarSimbolo(s).getDireccionMemoria());
        }
    }

    public int getDireccion(String ambito, String id) {
        return BuscarSimbolo(ambito,id).getDireccionMemoria();
    }
    public int getTamano(String Clave, String ambi) {
        return BuscarSimbolo(Clave,ambi).getTamano();
    }
    public int getValor(String Clave, String ambi) {
        return BuscarSimbolo(Clave,ambi).getValor();
    }
    
    public void setValor(String Clave, String ambi, int valor) {
        RegistroSimbolo simbolo = BuscarSimbolo(Clave, ambi);
        simbolo.setValor(valor);
    }
    public void setDireccion(String Clave, String ambi, int dir) {
        RegistroSimbolo simbolo = BuscarSimbolo(Clave,ambi);
        simbolo.setDireccionMemoria(dir);
    }    
}
