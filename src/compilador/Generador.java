package compilador;

import ast.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

public class Generador {
    /* Ilustracion de la disposicion de la memoria en
     * este ambiente de ejecucion para el lenguaje Tiny
     *
     * |t1	|<- mp (Maxima posicion de memoria de la TM
     * |t1	|<- desplazamientoTmp (tope actual)
     * |free|
     * |free|
     * |...	|
     * |x	|
     * |y	|<- gp
     * 
     * */
    /* desplazamientoTmp es una variable inicializada en 0
     * y empleada como el desplazamiento de la siguiente localidad
     * temporal disponible desde la parte superior o tope de la memoria
     * (la que apunta el registro MP).
     * 
     * - Se decrementa (desplazamientoTmp--) despues de cada almacenamiento y
     * 
     * - Se incrementa (desplazamientoTmp++) despues de cada eliminacion/carga en 
     *   otra variable de un valor de la pila.
     * 
     * Pudiendose ver como el apuntador hacia el tope de la pila temporal
     * y las llamadas a la funcion emitirRM corresponden a una inserccion 
     * y extraccion de esta pila
     */

    private static TablaSimbolos tablaSimbolos = null;
    private static String Nombre_ambito = "";
    private static String Nombre_funcion = "";
    private static boolean ban_declaracion = false;
    private static boolean ban_fun = false;
    private static boolean ban_Argfun = false;
    private static int desplazamientoTmp = 0;
    private static int contArgumento = 0;
    private static int dir_vector = 0;
    private static int ban_vector = 0;
    private static int inicioMain = -1;
    private static HashMap<String, Integer> elemFuncion = new HashMap<String, Integer>();
    private static HashMap<String, Integer> argFuncion = new HashMap<String, Integer>();
    private static HashMap<String, Integer> argActualFuncion = new HashMap<String, Integer>();
    private static Stack<String> pilaNombres = new Stack<String>();

    public static void setTablaSimbolos(TablaSimbolos tabla) {
        tablaSimbolos = tabla;
    }

    public static void generarCodigoObjeto(NodoBase raiz) {
        System.out.println();
        System.out.println();
        System.out.println("------ CODIGO OBJETO DEL LENGUAJE TINY GENERADO PARA LA TM ------");
        System.out.println();
        System.out.println();
        generarPreludioEstandar();
        generar(raiz);
        /*Genero el codigo de finalizacion de ejecucion del codigo*/
        UtGen.emitirComentario("Fin de la ejecucion.");
        UtGen.emitirRO("HALT", 0, 0, 0, "");
        System.out.println();
        System.out.println();
        System.out.println("------ FIN DEL CODIGO OBJETO DEL LENGUAJE TINY GENERADO PARA LA TM ------");
    }

    //Funcion principal de generacion de codigo
    //prerequisito: Fijar la tabla de simbolos antes de generar el codigo objeto 
    private static void generar(NodoBase nodo) {
        if (tablaSimbolos != null) {
            if (nodo instanceof NodoIf) {
                generarIf(nodo);
            } else if (nodo instanceof NodoRepeat) {
                generarRepeat(nodo);
            } else if (nodo instanceof NodoFor) {
                generarFor(nodo);
            } else if (nodo instanceof NodoAsignacion) {
                generarAsignacion(nodo);
            } else if (nodo instanceof NodoAsignacion_Vector) {
                generarAsignacion_vector(nodo);
            } else if (nodo instanceof NodoAsignacion_funcion) {
                generarAsignacion_funcion(nodo);
            } else if (nodo instanceof NodoLeer) {
                generarLeer(nodo);
            } else if (nodo instanceof NodoEscribir) {
                generarEscribir(nodo);
            } else if (nodo instanceof NodoValor) {
                generarValor(nodo);
            } else if (nodo instanceof NodoBooleano) {
                generarBooleano(nodo);
            } else if (nodo instanceof NodoIdentificador) {
                generarIdentificador(nodo);
            } else if (nodo instanceof NodoDeclaracion) {
                generarDeclaracion(nodo);
            } else if (nodo instanceof NodoVector) {
                generarVector(nodo);
            } else if (nodo instanceof NodoOperacion) {
                generarOperacion(nodo);
            } else if (nodo instanceof NodoFuncion) {
                generarFuncion(nodo);
            } else if (nodo instanceof Nodollamar_Funcion) {
                generarLlamarFuncion(nodo);
            } else if (nodo instanceof NodoArg_Funcion) {
                generarArgumentoFuncion(nodo);
            } else if (nodo instanceof NodoReturn) {
                generarReturn(nodo);
            } else if (nodo instanceof NodoPrincipal) {
                generarPrincipal(nodo);
            } else {
                System.out.println("BUG: Tipo de nodo a generar desconocido");
            }
            /*Si el hijo de extrema izquierda tiene hermano a la derecha lo genero tambien*/
            if (nodo.TieneHermano()) {
                generar(nodo.getHermanoDerecha());
            }
        } else {
            System.out.println("ERROR: por favor fije la tabla de simbolos a usar antes de generar codigo objeto!!!");
        }
    }

    private static void generarFuncion(NodoBase nodo) {
        int posicion;
        NodoFuncion n = (NodoFuncion) nodo;
        Nombre_ambito = n.getIdentificador().getNombre();
        UtGen.emitirComentario("Inicio de una funcion");
        posicion = UtGen.emitirSalto(0);
        elemFuncion.put(Nombre_ambito, posicion);
        generar(n.getIdentificador());
        if (n.getArgumento() != null) {
            ban_Argfun = true;
            generar(n.getArgumento());
        }
        argFuncion.put(Nombre_ambito, contArgumento);
        contArgumento = 0;
        ban_Argfun = false;
    }

    private static void generarLlamarFuncion(NodoBase nodo) {
        int direccion;
        Nodollamar_Funcion n = (Nodollamar_Funcion) nodo;
        Nombre_funcion = n.getIdentificador();
        pilaNombres.push(n.getIdentificador());
        if (n.getArg() != null) {
            generar(n.getArg());
        }
        pilaNombres.pop();
        if (pilaNombres.size() != 0) {
            Nombre_funcion = pilaNombres.peek();
        }
        contArgumento = 0;
        direccion = tablaSimbolos.getDireccion(n.getIdentificador(), n.getIdentificador());
        UtGen.emitirRM("LD", UtGen.AC, direccion, UtGen.GP, "Cargar la linea imem de la funcion");
        int posicionActual = UtGen.emitirSalto(0);
        UtGen.emitirRM("LDC", UtGen.DM, posicionActual + 2, UtGen.AC, "Cargar el inicio del main");
        UtGen.emitirRM("LDA", UtGen.PC, 0, UtGen.AC, "Salta hacia la funcion");
    }

    private static void generarArgumentoFuncion(NodoBase nodo) {
        NodoArg_Funcion n = (NodoArg_Funcion) nodo;
        int direccion, cant_arg, cant_arg_actual;

        if (argActualFuncion.containsKey(Nombre_funcion)) {
            contArgumento = argActualFuncion.get(Nombre_funcion);
            contArgumento++;
            argActualFuncion.remove(Nombre_funcion);
            argActualFuncion.put(Nombre_funcion, contArgumento);
            contArgumento = 0;
        } else {
            contArgumento++;
            argActualFuncion.put(Nombre_funcion, contArgumento);
            contArgumento = 0;
        }

        cant_arg_actual = argActualFuncion.get(Nombre_funcion);
        cant_arg = argFuncion.get(Nombre_funcion);
        if (cant_arg_actual <= cant_arg) {
            generar(n.getArg());
            direccion = tablaSimbolos.getDireccion(Nombre_funcion, Nombre_funcion);
            UtGen.emitirRM("ST", UtGen.AC, direccion + cant_arg_actual, UtGen.GP, "Asignar valor argumento de la funcion");
        } else {
            System.err.println("La cantidad de argumentos en el llamado de la funcion '" + Nombre_funcion + "' son incorrectos");
            System.exit(0);
        }

    }

    private static void generarPrincipal(NodoBase nodo) {
        NodoPrincipal n = (NodoPrincipal) nodo;
        int posicionActual;
        UtGen.emitirComentario("Inicio de un bloque");
        if (inicioMain == -1) {
            inicioMain = UtGen.emitirSalto(1);
        }
        if (n.getFuncion() != null) {
            generar(n.getFuncion());
            ban_fun = true;
        }
        if ("".equals(Nombre_ambito)) {
            posicionActual = UtGen.emitirSalto(0);
            Nombre_ambito = "main";
            UtGen.cargarRespaldo(inicioMain);
            UtGen.emitirRM_Abs("LDA", UtGen.PC, posicionActual, "Saltar las funciones y empezar con el main");
            UtGen.restaurarRespaldo();
            //UtGen.emitirRM("LDC", UtGen.DM, posicionActual, UtGen.AC, "Cargar el inicio del main");
            Iterator iterador = elemFuncion.entrySet().iterator();
            int direccion;
            Map.Entry funcion;
            while (iterador.hasNext()) {
                funcion = (Map.Entry) iterador.next();
                UtGen.emitirRM("LDC", UtGen.AC, funcion.getValue().hashCode(), UtGen.AC, "op: pop o cargo de la pila la posicion actual de la funcion");
                direccion = tablaSimbolos.getDireccion(funcion.getKey().toString(), funcion.getKey().toString());
                UtGen.emitirRM("ST", UtGen.AC, direccion, UtGen.GP, "Guarda en memoria la posicion actual de la funcion");
                //producto = iterador.next(); Si se usase tambien la otra linea comentada.
            }
        }
        generar(n.getContenido());
        if (ban_fun) {
            UtGen.emitirRM("LDA", UtGen.PC, 0, UtGen.DM, "Salta hacia el main");
            ban_fun = false;
        }
        Nombre_ambito = "";
        if (n.getBlock() != null) {
            generar(n.getBlock());
        }
    }

    private static void generarIf(NodoBase nodo) {
        NodoIf n = (NodoIf) nodo;
        int localidadSaltoElse, localidadSaltoEnd, localidadActual;
        if (UtGen.debug) {
            UtGen.emitirComentario("-> if");
        }
        /*Genero el codigo para la parte de prueba del IF*/
        generar(n.getPrueba());
        localidadSaltoElse = UtGen.emitirSalto(1);
        UtGen.emitirComentario("If: el salto hacia el else debe estar aqui");
        /*Genero la parte THEN*/
        generar(n.getParteThen());
        localidadSaltoEnd = UtGen.emitirSalto(1);
        UtGen.emitirComentario("If: el salto hacia el final debe estar aqui");
        localidadActual = UtGen.emitirSalto(0);
        UtGen.cargarRespaldo(localidadSaltoElse);
        UtGen.emitirRM_Abs("JEQ", UtGen.AC, localidadActual, "if: jmp hacia else");
        UtGen.restaurarRespaldo();
        /*Genero la parte ELSE*/
        if (n.getParteElse() != null) {
            generar(n.getParteElse());
        }
        //igualmente debo generar la sentencia que reserve en
        //localidadSaltoEnd al finalizar la ejecucion de un true
        localidadActual = UtGen.emitirSalto(0);
        UtGen.cargarRespaldo(localidadSaltoEnd);
        UtGen.emitirRM_Abs("LDA", UtGen.PC, localidadActual, "if: jmp hacia el final");
        UtGen.restaurarRespaldo();
        if (UtGen.debug) {
            UtGen.emitirComentario("<- if");
        }
    }

    private static void generarReturn(NodoBase nodo) {
        NodoReturn n = (NodoReturn) nodo;
        generar(n.getExp());
    }

    private static void generarRepeat(NodoBase nodo) {
        NodoRepeat n = (NodoRepeat) nodo;
        int localidadSaltoInicio;
        if (UtGen.debug) {
            UtGen.emitirComentario("-> repeat");
        }
        localidadSaltoInicio = UtGen.emitirSalto(0);
        UtGen.emitirComentario("repeat: el salto hacia el final (luego del cuerpo) del repeat debe estar aqui");
        /* Genero el cuerpo del repeat */
        generar(n.getCuerpo());
        /* Genero el codigo de la prueba del repeat */
        generar(n.getPrueba());
        UtGen.emitirRM_Abs("JEQ", UtGen.AC, localidadSaltoInicio, "repeat: jmp hacia el inicio del cuerpo");
        if (UtGen.debug) {
            UtGen.emitirComentario("<- repeat");
        }
    }

    private static void generarFor(NodoBase nodo) {
        NodoFor n = (NodoFor) nodo;
        int LineaInicial;
        int salto_fin;
        int posicion_actual;
        if (UtGen.debug) {
            UtGen.emitirComentario(" cabecera del for");
        }
        generar(n.getInicio());
        LineaInicial = UtGen.emitirSalto(0);
        generar(n.getCompara());
        salto_fin = UtGen.emitirSalto(1);
        UtGen.emitirComentario("contenido del for");
        generar(n.getSentencias());
        /* Genero el codigo de la prueba del repeat */
        generar(n.getAumento());
        UtGen.emitirRM_Abs("LDA", UtGen.PC, LineaInicial, "Salta al inicio del for");
        posicion_actual = UtGen.emitirSalto(0);
        UtGen.cargarRespaldo(salto_fin);
        UtGen.emitirRM_Abs("JEQ", UtGen.AC, posicion_actual, "SAlta al fin del for");
        UtGen.restaurarRespaldo();
        if (UtGen.debug) {
            UtGen.emitirComentario(" fin del for");
        }

    }

    private static void generarAsignacion(NodoBase nodo) {
        NodoAsignacion n = (NodoAsignacion) nodo;
        int direccion;
        if (UtGen.debug) {
            UtGen.emitirComentario("-> asignacion");
        }
        ban_vector = 1;
        /* Genero el codigo para la expresion a la derecha de la asignacion */
        generar(n.getExpresion());
        ban_vector = 0;
        /* Ahora almaceno el valor resultante */
        direccion = tablaSimbolos.getDireccion(Nombre_ambito, n.getID().getNombre());
        UtGen.emitirRM("ST", UtGen.AC, direccion, UtGen.GP, "asignacion: almaceno el valor para el id " + n.getID().getNombre());
        if (UtGen.debug) {
            UtGen.emitirComentario("<- asignacion");
        }
        argActualFuncion.clear();
    }

    private static void generarAsignacion_vector(NodoBase nodo) {
        NodoAsignacion_Vector n = (NodoAsignacion_Vector) nodo;
        UtGen.emitirComentario(" -> Asignacion de un vector");
        generar(n.getV());
        generar(n.getExpresion()); //La expresion que esta al lado derecho
        UtGen.emitirRM("ST", UtGen.AC, dir_vector, UtGen.GP, "ingresar el valor asignado al vector");
        //meter lo que iria en ese vector
        UtGen.emitirComentario(" <- Fin de asignacion de un vector");
    }

    private static void generarAsignacion_funcion(NodoBase nodo) {
        NodoAsignacion_funcion n = (NodoAsignacion_funcion) nodo;
        int direccion;
        direccion = tablaSimbolos.getDireccion(Nombre_ambito, Nombre_ambito);

    }

    private static void generarLeer(NodoBase nodo) {
        NodoLeer n = (NodoLeer) nodo;
        int direccion;
        if (UtGen.debug) {
            UtGen.emitirComentario("-> leer");
        }
        UtGen.emitirRO("IN", UtGen.AC, 0, 0, "leer: lee un valor entero ");
        direccion = tablaSimbolos.getDireccion(Nombre_ambito,n.getIdentificador());
        UtGen.emitirRM("ST", UtGen.AC, direccion, UtGen.GP, "leer: almaceno el valor entero leido en el id "+n.getIdentificador());
        if (UtGen.debug) {
            UtGen.emitirComentario("<- leer");
        }
    }

    private static void generarEscribir(NodoBase nodo) {
        NodoEscribir n = (NodoEscribir) nodo;
        if (UtGen.debug) {
            UtGen.emitirComentario("-> escribir");
        }
        /* Genero el codigo de la expresion que va a ser escrita en pantalla */
        generar(n.getExpresion());
        /* Ahora genero la salida */
        UtGen.emitirRO("OUT", UtGen.AC, 0, 0, "escribir: genero la salida de la expresion");
        if (UtGen.debug) {
            UtGen.emitirComentario("<- escribir");
        }
    }

    private static void generarValor(NodoBase nodo) {
        NodoValor n = (NodoValor) nodo;
        if (UtGen.debug) {
            UtGen.emitirComentario("-> constante");
        }
        UtGen.emitirRM("LDC", UtGen.AC, n.getValor(), 0, "cargar constante: " + n.getValor().toString());
        if (UtGen.debug) {
            UtGen.emitirComentario("<- constante");
        }
    }

    private static void generarBooleano(NodoBase nodo) {
        NodoBooleano n = (NodoBooleano) nodo;
        if (UtGen.debug) {
            UtGen.emitirComentario("-> constante");
        }
        if (n.getValor() == true) {
            UtGen.emitirRM("LDC", UtGen.AC, 1, 0, "cargar constante: true");
        } else if (n.getValor() == false) {
            UtGen.emitirRM("LDC", UtGen.AC, 0, 0, "cargar constante: false");
        }
        if (UtGen.debug) {
            UtGen.emitirComentario("<- constante");
        }
    }

    private static void generarDeclaracion(NodoBase nodo) {
        NodoDeclaracion n = (NodoDeclaracion) nodo;
        ban_declaracion = true;
        generar(n.getVariable());
        ban_declaracion = false;
    }

    private static void generarVector(NodoBase nodo) {
        NodoVector n = (NodoVector) nodo;
        int tamano, direccion;
        ban_vector = 2;
        tamano = tablaSimbolos.getTamano(Nombre_ambito, n.getIdentificador().getNombre());
        direccion = tablaSimbolos.getDireccion(Nombre_ambito, n.getIdentificador().getNombre());
        dir_vector = n.getValor() + direccion;
        if (tamano + direccion > dir_vector || ban_declaracion) {
            generar(n.getIdentificador());
        } else {
            System.err.println("Esta sobrepasando la memoria asignada al vector " + n.getIdentificador().getNombre());
            System.exit(0);
        }
    }

    private static void generarIdentificador(NodoBase nodo) {
        NodoIdentificador n = (NodoIdentificador) nodo;
        int direccion;
        if (UtGen.debug) {
            UtGen.emitirComentario("-> identificador");
        }
        if (ban_Argfun == true) {
            contArgumento++;
        }
        if (ban_vector == 2) {
            direccion = dir_vector;
            ban_vector = 1;
        } else {
            direccion = tablaSimbolos.getDireccion(Nombre_ambito, n.getNombre());
        }
        UtGen.emitirRM("LD", UtGen.AC, direccion, UtGen.GP, "cargar valor de identificador: " + n.getNombre());
        ban_vector = 0;
        if (UtGen.debug) {
            UtGen.emitirComentario("<- identificador");
        }
    }

    private static void generarOperacion(NodoBase nodo) {
        NodoOperacion n = (NodoOperacion) nodo;
        if (UtGen.debug) {
            UtGen.emitirComentario("-> Operacion: " + n.getOperacion());
        }

        generar(n.getOpIzquierdo());
        /* Almaceno en la pseudo pila de valor temporales el valor de la operacion izquierda */
        UtGen.emitirRM("ST", UtGen.AC, desplazamientoTmp--, UtGen.MP, "op: push en la pila tmp el resultado expresion izquierda");
        /* Genero la expresion derecha de la operacion */
        generar(n.getOpDerecho());
        /* Ahora cargo/saco de la pila el valor izquierdo */
        UtGen.emitirRM("LD", UtGen.AC1, ++desplazamientoTmp, UtGen.MP, "op: pop o cargo de la pila el valor izquierdo en AC1");
        switch (n.getOperacion()) {
            case mas:
                UtGen.emitirRO("ADD", UtGen.AC, UtGen.AC1, UtGen.AC, "op: +");
                break;
            case menos:
                UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: -");
                break;
            case por:
                UtGen.emitirRO("MUL", UtGen.AC, UtGen.AC1, UtGen.AC, "op: *");
                break;
            case entre:
                UtGen.emitirRO("DIV", UtGen.AC, UtGen.AC1, UtGen.AC, "op: /");
                break;
            case menor:
                UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: <");
                UtGen.emitirRM("JLT", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC<0)");
                UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
                UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
                UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
                break;
            case mayor:
                UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: >");
                UtGen.emitirRM("JGT", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC<0)");
                UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
                UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
                UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
                break;
            case mayor_eq:
                UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: >=");
                UtGen.emitirRM("JGE", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC<0)");
                UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
                UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
                UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
                break;
            case menor_eq:
                UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: <=");
                UtGen.emitirRM("JLE", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC<0)");
                UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
                UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
                UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
                break;
            case diferente:
                UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: !=");
                UtGen.emitirRM("JNE", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC<0)");
                UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
                UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
                UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
                break;
            case igual:
                UtGen.emitirRO("SUB", UtGen.AC, UtGen.AC1, UtGen.AC, "op: ==");
                UtGen.emitirRM("JEQ", UtGen.AC, 2, UtGen.PC, "voy dos instrucciones mas alla if verdadero (AC==0)");
                UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
                UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
                UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
                break;
            case and:
                UtGen.emitirRO("MUL", UtGen.AC, UtGen.AC1, UtGen.AC, "Verifico si los dos operandos son true");
                UtGen.emitirRM("JGT", UtGen.AC, 2, UtGen.PC, "Si es Verdadera salto");
                UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
                UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
                UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
                break;
            case or:
                UtGen.emitirRO("ADD", UtGen.AC, UtGen.AC1, UtGen.AC, "Verifico si los dos operandos son true");
                UtGen.emitirRM("JGT", UtGen.AC, 2, UtGen.PC, "Si es Verdadera salto");
                UtGen.emitirRM("LDC", UtGen.AC, 0, UtGen.AC, "caso de falso (AC=0)");
                UtGen.emitirRM("LDA", UtGen.PC, 1, UtGen.PC, "Salto incodicional a direccion: PC+1 (es falso evito colocarlo verdadero)");
                UtGen.emitirRM("LDC", UtGen.AC, 1, UtGen.AC, "caso de verdadero (AC=1)");
                break;
            default:
                UtGen.emitirComentario("BUG: tipo de operacion desconocida");
        }
        if (UtGen.debug) {
            UtGen.emitirComentario("<- Operacion: " + n.getOperacion());
        }
    }

    //TODO: enviar preludio a archivo de salida, obtener antes su nombre
    private static void generarPreludioEstandar() {
        UtGen.emitirComentario("Compilacion TINY para el codigo objeto TM");
        UtGen.emitirComentario("Archivo: " + "NOMBRE_ARREGLAR");
        /*Genero inicializaciones del preludio estandar*/
        /*Todos los registros en tiny comienzan en cero*/
        UtGen.emitirComentario("Preludio estandar:");
        UtGen.emitirRM("LD", UtGen.MP, 0, UtGen.AC, "cargar la maxima direccion desde la localidad 0");
        UtGen.emitirRM("ST", UtGen.AC, 0, UtGen.AC, "limpio el registro de la localidad 0");
    }
}
