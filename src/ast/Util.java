package ast;

public class Util {

    static int sangria = 0;

    //Imprimo en modo texto con sangrias el AST
    public static void imprimirAST(NodoBase raiz) {
        sangria += 2;
        while (raiz != null) {
            printSpaces();
            if (raiz instanceof NodoIf) {
                System.out.println("If");
            } else if (raiz instanceof NodoPrincipal) {
                System.out.println("Inicio de un nuevo bloque");
            } else if (raiz instanceof NodoRepeat) {
                System.out.println("Repeat");
            } else if (raiz instanceof NodoAsignacion) {
                System.out.println("Asignacion a: " + ((NodoAsignacion) raiz));
            } else if (raiz instanceof NodoLeer) {
                System.out.println("Lectura: " + ((NodoLeer) raiz).getIdentificador());
            } else if (raiz instanceof NodoEscribir) {
                System.out.println("Escribir");
            } else if (raiz instanceof NodoVector) {
                System.out.println("Vector : " + ((NodoVector) raiz).getIdentificador() + "[ ]");
                System.out.println("Valor: " + ((NodoVector) raiz).getValor());
            } else if (raiz instanceof NodoAsignacion_Vector) {
                System.out.println(":=");
                System.out.println("**Expr Izquierda Operacion**");
                System.out.println("Vector : " + ((NodoAsignacion_Vector) raiz).getV().getIdentificador() + "[ ]");
                System.out.println("Valor: " + ((NodoAsignacion_Vector) raiz).getV().getValor());
                System.out.println("**Expr Derecha Operacion**");
                imprimirAST(((NodoAsignacion_Vector) raiz).getExpresion());
            } else if (raiz instanceof NodoFor) {
                printSpaces();
                System.out.println("**Inicio For**");
                imprimirAST(((NodoFor) raiz).getInicio());
                printSpaces();
                System.out.println("**Comparacion For**");
                imprimirAST(((NodoFor) raiz).getCompara());
                System.out.println("**IncrementeoDecremento For**");
                imprimirAST(((NodoFor) raiz).getAumento());
                System.out.println("**Cuerpo Sentencias**");
                imprimirAST(((NodoFor) raiz).getSentencias());
            } else if (raiz instanceof NodoOperacion
                    || raiz instanceof NodoValor
                    || raiz instanceof NodoIdentificador) {
                imprimirNodo(raiz);
            } else {
                System.out.println("Tipo de nodo desconocido");
            };

            /* Hago el recorrido recursivo */
            if (raiz instanceof NodoIf) {
                printSpaces();
                System.out.println("**Prueba IF**");
                imprimirAST(((NodoIf) raiz).getPrueba());
                printSpaces();
                System.out.println("**Then IF**");
                imprimirAST(((NodoIf) raiz).getParteThen());
                if (((NodoIf) raiz).getParteElse() != null) {
                    printSpaces();
                    System.out.println("**Else IF**");
                    imprimirAST(((NodoIf) raiz).getParteElse());
                }
            } else if (raiz instanceof NodoRepeat) {
                printSpaces();
                System.out.println("**Cuerpo REPEAT**");
                imprimirAST(((NodoRepeat) raiz).getCuerpo());
                printSpaces();
                System.out.println("**Prueba REPEAT**");
                imprimirAST(((NodoRepeat) raiz).getPrueba());
            } else if (raiz instanceof NodoAsignacion) {
                imprimirAST(((NodoAsignacion) raiz).getExpresion());
            } else if (raiz instanceof NodoEscribir) {
                imprimirAST(((NodoEscribir) raiz).getExpresion());
            } else if (raiz instanceof NodoOperacion) {
                printSpaces();
                System.out.println("**Expr Izquierda Operacion**");
                imprimirAST(((NodoOperacion) raiz).getOpIzquierdo());
                printSpaces();
                System.out.println("**Expr Derecha Operacion**");
                imprimirAST(((NodoOperacion) raiz).getOpDerecho());
            } else if (raiz instanceof NodoPrincipal) {
                printSpaces();
                imprimirAST(((NodoPrincipal) raiz).getFuncion());
                printSpaces();
                imprimirAST(((NodoPrincipal) raiz).getContenido());
                printSpaces();
                imprimirAST(((NodoPrincipal) raiz).getBlock());

            }
            raiz = raiz.getHermanoDerecha();
        }
        sangria -= 2;
    }

    /* Imprime espacios con sangria */
    static void printSpaces() {
        int i;
        for (i = 0; i < sangria; i++) {
            System.out.print(" ");
        }
    }
    /* Imprime informacion de los nodos */
    static void imprimirNodo(NodoBase raiz) {
        if (raiz instanceof NodoRepeat
                || raiz instanceof NodoLeer
                || raiz instanceof NodoEscribir) {
            System.out.println("palabra reservada: " + raiz.getClass().getName());
        }

        if (raiz instanceof NodoAsignacion) {
            System.out.println(":=");
        }

        if (raiz instanceof NodoOperacion) {
            tipoOp sel = ((NodoOperacion) raiz).getOperacion();
            if (sel == tipoOp.or) {
                System.out.println("or");
            }
            if (sel == tipoOp.and) {
                System.out.println("and");
            }
            if (sel == tipoOp.menor) {
                System.out.println("<");
            }
            if (sel == tipoOp.mayor) {
                System.out.println(">");
            }
            if (sel == tipoOp.mayor_eq) {
                System.out.println(">=");
            }
            if (sel == tipoOp.menor_eq) {
                System.out.println("<=");
            }
            if (sel == tipoOp.diferente) {
                System.out.println("!=");
            }
            if (sel == tipoOp.igual) {
                System.out.println("=");
            }
            if (sel == tipoOp.mas) {
                System.out.println("+");
            }
            if (sel == tipoOp.menos) {
                System.out.println("-");
            }
            if (sel == tipoOp.por) {
                System.out.println("*");
            }
            if (sel == tipoOp.entre) {
                System.out.println("/");
            }
        }

        if (raiz instanceof NodoValor) {
            System.out.println("NUM, val= " + ((NodoValor) raiz).getValor());
        }

        if (raiz instanceof NodoIdentificador) {
            System.out.println("ID, nombre= " + ((NodoIdentificador) raiz).getNombre());
        }

    }

}
