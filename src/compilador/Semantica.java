/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import ast.*;
import ast.tipoOp;
public class Semantica {

    TablaSimbolos t;
    NodoBase r;
    static int sangria = 0;

    public Semantica(TablaSimbolos tabla, NodoBase r) {
        this.t = tabla;
        this.r = r;

        realizar_recorrido(this.r);


    }

    public NodoBase realizar_recorrido(NodoBase raiz) {

        NodoBase izquierda, derecha;

        while (raiz != null) {

            if (raiz instanceof NodoPrincipal) {
                if (((NodoPrincipal) raiz).getContenido() != null) {
                    realizar_recorrido(((NodoPrincipal) raiz).getContenido());
                }
            }

            if (raiz instanceof NodoAsignacion) {
                izquierda = realizar_recorrido(((NodoAsignacion) raiz).getID());
                derecha = realizar_recorrido(((NodoAsignacion) raiz).getExpresion());

                if (derecha instanceof NodoIdentificador) {
                    System.out.println("gas");
                }
                  if (derecha instanceof NodoValor) {
                    
                }
                
                

            } else if (raiz instanceof NodoIdentificador) {
                System.out.println("Identificador " + ((NodoIdentificador) raiz).getNombre());
              
                    return raiz;
               
            } else if (raiz instanceof NodoValor) {
                System.out.println("Valor: " + ((NodoValor) raiz).getValor());
                if (raiz.getHermanoDerecha() == null) {
                    return raiz;
                }
            }
       
            
        }


        return raiz;
    }
    public NodoBase operacion(NodoBase raiz,int HI,int HD){
        NodoBase temp = null;
        boolean aux;
        System.out.println("hijo izquierdo: "+HI+" hijo derecho: "+HD+"raiz: "+raiz.toString());
        tipoOp sel = ((NodoOperacion) raiz).getOperacion();
        if(sel == tipoOp.mas );
            System.out.println("hola sumar");
        if(sel == tipoOp.menos);
            System.out.println("hola restar");
        if(sel == tipoOp.por);
            System.out.println("hola multiplicacion");
        if(sel == tipoOp.entre);
            System.out.println("hola division");    
            return raiz;
    }
    
    
}
