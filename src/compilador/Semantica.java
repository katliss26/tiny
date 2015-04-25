/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import ast.*;
import ast.tipoOp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Semantica {     
    TablaSimbolos t;
    NodoBase r;
    String Nombre;
    int bandera, If_bool = 0, bandera_imprimir,Prueba_return=0,pruebita=0,bandera_argumentos=0,contador;
    RegistroSimbolo REGISTRO_HI, REGISTRO_HD, simbolo;

   
    HashMap<String, HashMap<String, String>> tabla=new HashMap<String, HashMap<String, String>>();
    HashMap<String, String> contenido = new HashMap<String, String>();

    public Semantica(TablaSimbolos tabla, NodoBase r) {
        this.t = tabla;
        this.r = r;
        simbolo = null;
        realizar_recorrido(this.r);

    }

    public NodoBase realizar_recorrido(NodoBase raiz) {

        NodoBase izquierda, derecha, temporal = null;

        while (raiz != null) {

            if (raiz instanceof NodoPrincipal) {

                if (((NodoPrincipal) raiz).getFuncion() != null) {
                    bandera = 1;
                    pruebita=1;
                    realizar_recorrido(((NodoPrincipal) raiz).getFuncion());
                    bandera = 0;
                    if (((NodoPrincipal) raiz).getContenido() != null) {
                        realizar_recorrido(((NodoPrincipal) raiz).getContenido());
                        if (Prueba_return>0 ) {
                            Prueba_return=0;                          
                        }
                        else {
                            
                            RegistroSimbolo REGISTRO;  
                            REGISTRO=t.BuscarSimbolo(Nombre,Nombre);
                            System.out.println(" "+REGISTRO.getTipo()+""+Nombre);
                            if(REGISTRO.getTipo().equals("INT") || REGISTRO.getTipo().equals("BOOL")  )
                            System.err.println("Error la funcion no posee return");                      
                        }

                        pruebita=0;
                    }
                    //System.out.println("ACABE");
                }
                if (((NodoPrincipal) raiz).getBlock() != null) {
                    realizar_recorrido(((NodoPrincipal) raiz).getBlock());
                    return raiz;
                }
                if (((NodoPrincipal) raiz).getContenido() != null) {
                    this.Nombre = "main";
                    realizar_recorrido(((NodoPrincipal) raiz).getContenido());
                    return raiz;
                }
//-------------------------------------Tipos de nodos------------------------------------------
            } else if (raiz instanceof NodoAsignacion) {
                //¨*System.out.println("Asignacion");
                izquierda = realizar_recorrido(((NodoAsignacion) raiz).getID());

                REGISTRO_HI = t.BuscarSimbolo(this.Nombre, ((NodoIdentificador) izquierda).getNombre());
                //System.out.println(":=");
                derecha = realizar_recorrido(((NodoAsignacion) raiz).getExpresion());
                //System.out.println("Nombre del ambito actual" + Nombre);
                if (derecha == null) {
                    imprimir("NO SE PUEDE ASIGNAR TIPOS INCOMPATIBLES", 3);
                } else {
                    if (izquierda instanceof NodoIdentificador && (derecha instanceof NodoValor || derecha instanceof NodoIdentificador)) {
                        boolean verificar = verificar_asignacion(izquierda, derecha);
                        if (verificar == true) {
                                int nv;
                                if (derecha instanceof NodoValor) {
                                    nv = ((NodoValor) derecha).getValor();
                                } else {
                                    nv = t.getValor( Nombre, ((NodoIdentificador) derecha).getNombre());
                                }
                                t.setValor(Nombre,((NodoIdentificador) izquierda).getNombre(), nv);
                                //System.out.println("ASIGNO");

                            } else {
                                //System.err.println("Error al asignar los elementos no son del mismo tipo ["+((NodoIdentificador) izquierda).getNombre()+"] y "+((NodoIdentificador) derecha).getNombre());
                            imprimir("ERROR AL ASIGNAR LOS ELEMENTOS NO SON DEL MISMO TIPO", 3);
                            
                        }

                        } else if (izquierda instanceof NodoIdentificador && derecha instanceof NodoBooleano) {
                            this.REGISTRO_HI = t.BuscarSimbolo( this.Nombre, ((NodoIdentificador) izquierda).getNombre());

                            boolean verificar = verificar_asignacion(izquierda, derecha);
                            if (verificar == true) {
                                int temp = 0;
                                if (((NodoBooleano) derecha).getValor()) {
                                    temp = 1;
                                }
                                t.setValor(Nombre,((NodoIdentificador) izquierda).getNombre(), temp);
                                //System.out.println("ASIGNO");
                            } else {
                                System.err.println("Elementos no son del mismo tipo izquierdo int derecho bool");
                                System.exit(0);
                            }
                            } 
                        
                        //-----------------funciones--------------------------
                        else if(izquierda instanceof NodoIdentificador && derecha instanceof Nodollamar_Funcion){
                           
                            RegistroSimbolo Valores_funcion,Valores_Identificador;
                            System.out.println(" nombre " + ((Nodollamar_Funcion) derecha).getIdentificador());
                            Valores_funcion= t.BuscarSimbolo( ((Nodollamar_Funcion) derecha).getIdentificador(), ((Nodollamar_Funcion) derecha).getIdentificador());
                            Valores_Identificador=t.BuscarSimbolo(Nombre,((NodoIdentificador)izquierda).getNombre());
                            //System.out.println("La funcion es de tipo"+Valores_funcion.getTipo());
                           // System.out.println(Valores_Identificador.getIdentificador()+"La funcion es de tipo"+Valores_Identificador.getTipo());
                            if(Valores_funcion.getTipo().equals("INT") || Valores_funcion.getTipo().equals("BOOL")){
                                if(!Valores_funcion.getTipo().equals(Valores_Identificador.getTipo())){
                                    System.err.println("Los datos de la funcion ["+Valores_funcion.getIdentificador()+"] y ["+Valores_Identificador.getIdentificador()+"] No coinciden");
                                }
                                else{
                                    System.out.println("FUNCIONA");
                                }
                            }
                            else{
                                System.err.println("El tipo de dato de la funcion es void no puede igualarse a una variable");
                            }

                    } else if (izquierda instanceof NodoIdentificador && derecha instanceof NodoBooleano) {
                        this.REGISTRO_HI = t.BuscarSimbolo( this.Nombre, ((NodoIdentificador) izquierda).getNombre());

                        boolean verificar = verificar_asignacion(izquierda, derecha);
                        if (verificar == true) {
                            int temp = 0;
                            if (((NodoBooleano) derecha).getValor()) {
                                temp = 1;
                            }
                            t.setValor(((NodoIdentificador) izquierda).getNombre(), Nombre, temp);
                            //System.out.println("ASIGNO");
                        } else {
                            System.out.println("Elementos no son del mismo tipo izquierdo int derecho bool");
                        }
                        } else if (derecha instanceof NodoVector) {
                            
                            boolean x = verificar_asignacion(izquierda, derecha);

                            if(!x) imprimir("ASIGNACION DEL VECTOR ERRONEA", 3);
                
                        }
                        else if (derecha instanceof Nodollamar_Funcion) {

                        RegistroSimbolo aux;

                        System.out.println(" " + ((Nodollamar_Funcion) derecha).getIdentificador());
                        aux = t.BuscarSimbolo( Nombre, ((Nodollamar_Funcion) derecha).getIdentificador());

                        if (aux != null) {
                            REGISTRO_HD = t.BuscarSimbolo(((Nodollamar_Funcion) derecha).getIdentificador(), ((Nodollamar_Funcion) derecha).getIdentificador());
                        } else {
                            REGISTRO_HD = null;
                        }

                        //REGISTRO_HD = t.BuscarSimbolo(((Nodollamar_Funcion) derecha).getIdentificador(), ((Nodollamar_Funcion) derecha).getIdentificador());
                        if (REGISTRO_HD == null) {
                            System.err.println("Funcion no esta declarada");
                        } else {

                            if ((t.BuscarSimbolo(((Nodollamar_Funcion) derecha).getIdentificador(), ((Nodollamar_Funcion) derecha).getIdentificador() )).getTipo().equals("VOID")) {
                                System.err.println("Funcion es de tipo void");
                            } else {
                                if (REGISTRO_HD.getTipo().equals(t.BuscarSimbolo( Nombre, ((NodoIdentificador) izquierda).getNombre()).getTipo())) {
                                    System.out.println("ASIGNO");
                                } else {
                                    System.err.println("Error de tipos");
                                }

                            }

                        }
                    }
                        //
                }
                             
            }else if (raiz instanceof NodoAsignacion_Vector) {
                
                izquierda = realizar_recorrido(((NodoAsignacion_Vector) raiz).getV().getIdentificador());
                REGISTRO_HI = t.BuscarSimbolo(this.Nombre, ((NodoIdentificador) izquierda).getNombre());                
                
                derecha = realizar_recorrido(((NodoAsignacion_Vector) raiz).getExpresion());                                              
                                                           
                boolean x = verificar_asignacion(izquierda, derecha);
                
                if(!x) imprimir("ASIGNACION DEL VECTOR ERRONEA", 3);
                
                
            }else if (raiz instanceof NodoIdentificador) {

                if(bandera_argumentos==1){
                    String nombre=((NodoIdentificador) raiz).getNombre();
                    String Tipo=((NodoIdentificador) raiz).getTipo();
                    System.out.println(" "+((NodoIdentificador) raiz).getNombre()+" "+((NodoIdentificador) raiz).getTipo());
                    contenido.put(nombre,Tipo);
                    tabla.put(Nombre, contenido);
                }
                
                else{
                    //System.out.println("ESTOY ENTRANDO EN LA COSa");
                
                if (bandera == 1) {
                    contenido = new HashMap<String,String>();
                    this.Nombre = ((NodoIdentificador) raiz).getNombre();
                }
                simbolo = t.BuscarSimbolo( this.Nombre, ((NodoIdentificador) raiz).getNombre());
                //System.out.println(REGISTRO_HI.getTipo() + " " + ((NodoIdentificador) raiz).getNombre());
                if(simbolo == null){
                    imprimir("identificador ["+((NodoIdentificador) raiz).getNombre()+"] no declarado",3);                    
                }                
                return raiz;
            }
            } else if (raiz instanceof NodoVector) {
                
                realizar_recorrido(((NodoVector) raiz).getIdentificador());
                
                if(((NodoVector) raiz).getExpresion() != null){
                    temporal = realizar_recorrido(((NodoVector) raiz).getExpresion());
                    
                    if( ! (temporal instanceof NodoValor || temporal instanceof NodoIdentificador ))
                        imprimir("VECTOR CON EXPRESION INVALIDA", 3);
                    return raiz;
                }
            }else if (raiz instanceof NodoValor) {
                //System.out.println("Valor: " + ((NodoValor) raiz).getValor());
                if (raiz.getHermanoDerecha() == null) {
                    return raiz;
                }
            } else if (raiz instanceof NodoBooleano) {
                //System.out.println("Booleano: " + ((NodoBooleano) raiz).getValor());
                return raiz;
            } else if (raiz instanceof NodoDeclaracion) {
                bandera_imprimir = 1;
                realizar_recorrido(((NodoDeclaracion) raiz).getVariable());
            }   else if (raiz instanceof NodoFuncion) {
                realizar_recorrido(((NodoFuncion) raiz).getIdentificador());//llamada para saber el nombre de la funcion
                if (((NodoFuncion) raiz).getArgumento() != null) {
                    
                    bandera_argumentos=1;
                    realizar_recorrido(((NodoFuncion) raiz).getArgumento());
                    //System.out.println("HOLA");
                    bandera_argumentos=0;
                    //System.out.println("adsas");
                    
                   
                            
                }
                return raiz;// me retorna al inicio

            } else if (raiz instanceof NodoFor) {
                //Inicializar la variable declarada
                //System.out.println("Ingreso nodo for");                
                realizar_recorrido(((NodoFor) raiz).getInicio());               
                                
                temporal = realizar_recorrido(((NodoFor) raiz).getCompara());

                if(temporal instanceof NodoBooleano){                
                    realizar_recorrido(((NodoFor) raiz).getAumento());
                    realizar_recorrido(((NodoFor) raiz).getSentencias());                
                }else
                //System.out.println("error");
                    imprimir("FOR CONDICION INCORRECTA", 3);
            } else if (raiz instanceof NodoRepeat) {                
                realizar_recorrido(((NodoRepeat) raiz).getCuerpo());                
                temporal = realizar_recorrido(((NodoRepeat) raiz).getPrueba());
                
                if( !(temporal instanceof NodoBooleano || ( temporal instanceof NodoIdentificador && 
                "BOOL".equals(t.BuscarSimbolo( Nombre, ((NodoIdentificador) temporal).getNombre()).getTipo()) ) ) ) 
                    imprimir("REPEAT CONDICION INCORRECTA", 3);
            }  else if (raiz instanceof NodoIf) {
                temporal = realizar_recorrido(((NodoIf) raiz).getPrueba());
                
                if ( temporal instanceof NodoBooleano || ( temporal instanceof NodoIdentificador && 
                "BOOL".equals(t.BuscarSimbolo( Nombre, ((NodoIdentificador) temporal).getNombre()).getTipo()) ) ) {
                    
                    
                    realizar_recorrido(((NodoIf) raiz).getParteThen());
                    if (((NodoIf) raiz).getParteElse() != null) {
                        realizar_recorrido(((NodoIf) raiz).getParteElse());
                    }
                } else {
                    //imprimir("IF CON CONDICION INCORRECTA", 3);
                    System.err.println("IF condicion incorrecta");
                    System.exit(0);
                } 
            }else if (raiz instanceof NodoEscribir) {
                temporal = realizar_recorrido(((NodoEscribir) raiz).getExpresion());
                if(!(temporal instanceof NodoIdentificador || temporal instanceof NodoValor 
                        || temporal instanceof NodoVector || temporal instanceof NodoBooleano
                        || temporal instanceof NodoOperacion  ))
                    imprimir("WRITE CON EXPRESION INCORRECTA", 3);
                             
            } else if (raiz instanceof NodoLeer) {
                //probar
                temporal = realizar_recorrido( new NodoIdentificador( ((NodoLeer) raiz).getIdentificador()) );
                if(temporal instanceof NodoIdentificador ) System.out.println("bien");
            } else if (raiz instanceof NodoOperacion) {

                NodoBase oI, oD;
                oI = realizar_recorrido(((NodoOperacion) raiz).getOpIzquierdo());
                oD = realizar_recorrido(((NodoOperacion) raiz).getOpDerecho());

                //Suma 
                if (((NodoOperacion) raiz).getOperacion() == tipoOp.mas) {
                    temporal = Verificar_Operaciones(oI, oD, tipoOp.mas);
                }
                //Resta
                if (((NodoOperacion) raiz).getOperacion() == tipoOp.menos) {
                    temporal = Verificar_Operaciones(oI, oD, tipoOp.menos);
                }
                // Multi
                if (((NodoOperacion) raiz).getOperacion() == tipoOp.por) {
                    temporal = Verificar_Operaciones(oI, oD, tipoOp.por);
                }
                //Divicion
                if (((NodoOperacion) raiz).getOperacion() == tipoOp.entre) {
                    temporal = Verificar_Operaciones(oI, oD, tipoOp.entre);
                }
                // >
                if (((NodoOperacion) raiz).getOperacion() == tipoOp.mayor) {
                    temporal = Verificar_Operaciones(oI, oD, tipoOp.mayor);
                }
                // <
                if (((NodoOperacion) raiz).getOperacion() == tipoOp.menor) {
                    temporal = Verificar_Operaciones(oI, oD, tipoOp.menor);
                }
                // =
                if (((NodoOperacion) raiz).getOperacion() == tipoOp.igual) {
                    temporal = Verificar_Operaciones(oI, oD, tipoOp.igual);
                }
                // >=
                if (((NodoOperacion) raiz).getOperacion() == tipoOp.mayor_eq) {
                    temporal = Verificar_Operaciones(oI, oD, tipoOp.mayor_eq);
                }// <=
                if (((NodoOperacion) raiz).getOperacion() == tipoOp.menor_eq) {
                    temporal = Verificar_Operaciones(oI, oD, tipoOp.menor_eq);
                }// and
                if (((NodoOperacion) raiz).getOperacion() == tipoOp.and) {
                    temporal = Verificar_Operaciones(oI, oD, tipoOp.and);
                }// or
                if (((NodoOperacion) raiz).getOperacion() == tipoOp.or) {
                    temporal = Verificar_Operaciones(oI, oD, tipoOp.or);
                }

            }else if (raiz instanceof Nodollamar_Funcion)  {
               // return ((Nodollamar_Funcion) raiz).getExp();
                NodoBase temp;
                 RegistroSimbolo Valores_funcion;
                 Valores_funcion= t.BuscarSimbolo( ((Nodollamar_Funcion) raiz).getIdentificador(), ((Nodollamar_Funcion) raiz).getIdentificador());
                 //System.out.println("HOLA");
                  
                 if(Valores_funcion.getTipo().equals("INT")){
                    temp=Comprobacion(1);
                    return temp;
                  }
                 if(Valores_funcion.getTipo().equals("BOOL")){
                    boolean aux=true; 
                    temp=Comprobacion(aux);
                    return temp;
                  }
                 
                
            }
             else if(raiz instanceof NodoReturn){
                Prueba_return=Prueba_return+1;
                if(pruebita==1){//esta en la funcion
                
                NodoBase r= realizar_recorrido(((NodoReturn)raiz).getExp());

                RegistroSimbolo REGISTRO;
                REGISTRO=t.BuscarSimbolo(Nombre,Nombre);
                System.out.println(" asdas"+REGISTRO.getTipo());
                
                if(!"VOID".equals(REGISTRO.getTipo())){
   



                    if(r instanceof NodoValor){                        
                            System.out.println(" "+((NodoValor)r).getValor());
                            REGISTRO=t.BuscarSimbolo(Nombre,Nombre);
                            System.out.println(" "+REGISTRO.getTipo());
                            System.out.println("");
                            if(REGISTRO.getTipo().equals("INT")){
                                Prueba_return=Prueba_return+1;
                                System.out.println("RETORNO");
                            }
                            else{
                                System.err.println("Error de tipo de funcion y retorno no compatible");
                            }
                    }
                    else if (r instanceof NodoBooleano){
                        System.out.println(" "+((NodoBooleano)r).getValor());
                            REGISTRO=t.BuscarSimbolo(Nombre,Nombre);
                            System.out.println(" "+REGISTRO.getTipo());
                            System.out.println("");
                            if(REGISTRO.getTipo().equals("BOOL")){
                                
                                System.out.println("RETORNO");
                            }
                            else{
                               System.err.println("Error de tipo de funcion y retorno no compatible");
                            }
                         }
                      else if (r instanceof NodoIdentificador){
                        System.out.println(" "+((NodoIdentificador)r).getNombre());
                            REGISTRO=t.BuscarSimbolo(Nombre,((NodoIdentificador)r).getNombre());
                            if(REGISTRO==null){
                                System.err.println("error no esta declarado el identificador");
                            }
                            else{
                                RegistroSimbolo REGISTROI,REGISTROF;
                                REGISTROF=t.BuscarSimbolo(Nombre,Nombre);
                                
                                if(!REGISTRO.getTipo().equals(REGISTROF.getTipo())){
                                    System.err.println("Error de tipos");
                                }
                                else{
                                    Prueba_return=Prueba_return+1;
                                    
                                }
                            }
                         }   
                   }// void
                }// if de bandera
                else{
                    System.err.println("El main no puede llevar return");
                }
            }//return
            raiz = raiz.getHermanoDerecha();
        }
        return temporal;
    }

    public boolean verificar_asignacion(NodoBase HI, NodoBase HD) {
        //x=numero;
        if (HI instanceof NodoIdentificador && HD instanceof NodoValor) {
            if ((t.BuscarSimbolo(this.Nombre, ((NodoIdentificador) HI).getNombre() )).getTipo().equals("INT")) {
                return true;
            } else {
                return false;
            }
        } //x=b;
        else if (HI instanceof NodoIdentificador && HD instanceof NodoIdentificador) {

            if ((t.BuscarSimbolo(this.Nombre,((NodoIdentificador) HI).getNombre() )).getTipo().equals(
                    (t.BuscarSimbolo( this.Nombre, ((NodoIdentificador) HD).getNombre() )).getTipo())) {
                return true;
            } else {
                return false;
            }
        } //x=true;
        else if (HI instanceof NodoIdentificador && HD instanceof NodoBooleano) {
            if (((NodoBooleano) HD).getValor() == true || ((NodoBooleano) HD).getValor() == false) {
                If_bool = 1;
            }
            if ((t.BuscarSimbolo( this.Nombre,((NodoIdentificador) HI).getNombre() )).getTipo().equals("BOOL") && If_bool == 1) {
                return true;
            } else {
                return false;
            }
        }
        else if(HI instanceof NodoIdentificador && HD instanceof NodoVector){            
            if ((t.BuscarSimbolo(this.Nombre,((NodoIdentificador) HI).getNombre() )).getTipo().equals(
                    (t.BuscarSimbolo( this.Nombre, ((NodoVector) HD).getIdentificador().getNombre() )).getTipo()) ) {
                return true;
            } else {
                return false;
            }
        }
        
        return true;
    }

    public NodoBase Verificar_Operaciones(NodoBase HI, NodoBase HD, tipoOp tipo) {
        NodoBase temporal = null;
        int Valor_HI, Valor_HD;
        boolean Bool_HI, Bool_HD;
        //Caso de todos sus valores sean interos
        if (HI instanceof NodoValor && HD instanceof NodoValor) {
            Valor_HI = ((NodoValor) HI).getValor();
            Valor_HD = ((NodoValor) HD).getValor();
            if (tipo == tipo.mas || tipo == tipo.menos || tipo == tipo.por || tipo == tipo.entre) {
                temporal = Operaciones_matematicas(Valor_HI, Valor_HD, tipo);
            } else {
                temporal = Operaciones_booleana(Valor_HI, Valor_HD, tipo);
            }

            return temporal;
        } else if (HI instanceof NodoBooleano && HD instanceof NodoBooleano) {
            //todos bool
            Bool_HI = ((NodoBooleano) HI).getValor();
            Bool_HD = ((NodoBooleano) HD).getValor();
            temporal = operacion_Logica(Bool_HI, Bool_HD, tipo);
            return temporal;

        } else if (HI instanceof NodoIdentificador && HD instanceof NodoIdentificador) {
            // ID y ID

            Valor_HI = (t.BuscarSimbolo( this.Nombre, ((NodoIdentificador) HI).getNombre() )).getValor();
            Valor_HD = (t.BuscarSimbolo( this.Nombre, ((NodoIdentificador) HD).getNombre())).getValor();
            this.REGISTRO_HD = t.BuscarSimbolo( this.Nombre,((NodoIdentificador) HD).getNombre());
            this.REGISTRO_HI = t.BuscarSimbolo( this.Nombre,((NodoIdentificador) HI).getNombre());

            if (REGISTRO_HD == null) {
                System.out.println("El Identificador [" + ((NodoIdentificador) HD).getNombre() + "] No esta declarado");
            } else {
                if (REGISTRO_HI == null) {
                    System.out.println("El Identificador [" + ((NodoIdentificador) HI).getNombre() + "] No esta declarado");
                } else if (REGISTRO_HD.getTipo().equals(REGISTRO_HI.getTipo())) {
                    if (tipo == tipo.mas || tipo == tipo.menos || tipo == tipo.por || tipo == tipo.entre) {
                        temporal = Operaciones_matematicas(Valor_HI, Valor_HD, tipo);
                    } else {
                        temporal = Operaciones_booleana(Valor_HI, Valor_HD, tipo);
                    }
                    return temporal;
                } else {
                    System.out.println("El identificador [" + ((NodoIdentificador) HI).getNombre()
                            + "] no es compatible con [" + ((NodoIdentificador) HD).getNombre() + "]");
                }
            }
            // Caso de hijo izquierdo un indentificador  y valor a la derecha
        } else if (HI instanceof NodoIdentificador && HD instanceof NodoValor) {
            Valor_HI = (t.BuscarSimbolo( this.Nombre, ((NodoIdentificador) HI).getNombre())).getValor();
            Valor_HD = ((NodoValor) HD).getValor();

            REGISTRO_HI = t.BuscarSimbolo(this.Nombre,((NodoIdentificador) HI).getNombre());

            if (REGISTRO_HI == null) {
                System.out.println("Error No se encuentra tipo");
            } else {
                if (REGISTRO_HI.getTipo().equals("INT")) {
                    if (tipo == tipo.mas || tipo == tipo.menos || tipo == tipo.por || tipo == tipo.entre) {
                        temporal = Operaciones_matematicas(Valor_HI, Valor_HD, tipo);
                    } else {
                        temporal = Operaciones_booleana(Valor_HI, Valor_HD, tipo);
                    }
                    return temporal;
                } else {
                    System.out.println("El identificador [" + ((NodoIdentificador) HI).getNombre()
                            + "] no es compatible con [" + ((NodoIdentificador) HD).getNombre() + "]");
                }
            }
            //Hijo izquierdo id hijo derecho bool          
        } else if (HI instanceof NodoValor && HD instanceof NodoIdentificador) {

            Valor_HI = ((NodoValor) HI).getValor();
            Valor_HD = (t.BuscarSimbolo(this.Nombre, ((NodoIdentificador) HD).getNombre())).getValor();
            this.REGISTRO_HD = t.BuscarSimbolo(this.Nombre,((NodoIdentificador) HD).getNombre());
            if (REGISTRO_HD == null) {
                System.out.println("El Identificador [" + ((NodoIdentificador) HD).getNombre() + "] No esta declarado");
            } else {
                if (REGISTRO_HD.getTipo().equals("INT")) {
                    if (tipo == tipo.mas || tipo == tipo.menos || tipo == tipo.por || tipo == tipo.entre) {
                        temporal = Operaciones_matematicas(Valor_HI, Valor_HD, tipo);
                    } else {
                        temporal = Operaciones_booleana(Valor_HI, Valor_HD, tipo);
                    }

                    return temporal;
                } else {
                    System.out.println("El identificador [" + ((NodoIdentificador) HI).getNombre()
                            + "] no es compatible con [" + ((NodoIdentificador) HD).getNombre() + "]");
                }
            }

        } else if (HI instanceof NodoIdentificador && HD instanceof NodoBooleano) {
            Valor_HI = (t.BuscarSimbolo(this.Nombre,((NodoIdentificador) HI).getNombre())).getValor();
            Bool_HD = ((NodoBooleano) HD).getValor();

            REGISTRO_HI = t.BuscarSimbolo(this.Nombre,((NodoIdentificador) HI).getNombre());

            if (REGISTRO_HI == null) {
                System.out.println("El Identificador [" + ((NodoIdentificador) HI).getNombre() + "] No esta declarado");
            } else {
                if (REGISTRO_HI.getTipo().equals("BOOL")) {
                    if (Valor_HI == 1) {
                        temporal = operacion_Logica(true, Bool_HD, tipo);
                    } else if (Valor_HI == 0) {
                        temporal = operacion_Logica(false, Bool_HD, tipo);
                    }
                    //else if (tipo == tipo.igual) temporal = Operaciones_booleana(false, Bool_HD, tipo); true == true                    
                    return temporal;
                } else {
                    //System.out.println("El identificador [" + ((NodoIdentificador) HI).getNombre()
                      //      + "] no es compatible con [" + ((NodoIdentificador) HD).getNombre() + "]");
                    imprimir("TIPOS INCOMPATIBLES", 3);
                }
            }
            //Hijo izquierdo  valor hijo derecho identificador          
        } else if (HI instanceof NodoBooleano && HD instanceof NodoIdentificador) {

            Bool_HI = ((NodoBooleano) HI).getValor();
            Valor_HD = (t.BuscarSimbolo( this.Nombre, ((NodoIdentificador) HD).getNombre())).getValor();
            this.REGISTRO_HD = t.BuscarSimbolo(this.Nombre,((NodoIdentificador) HD).getNombre());

            if (REGISTRO_HD == null) {
                System.out.println("El Identificador [" + ((NodoIdentificador) HD).getNombre() + "] No esta declarado");
            } else {
                if (REGISTRO_HD.getTipo().equals("BOOL")) {
                    if (Valor_HD == 1) {
                        temporal = operacion_Logica(true, Bool_HI, tipo);
                    } else if (Valor_HD == 0) {
                        temporal = operacion_Logica(false, Bool_HI, tipo);
                    }
                    return temporal;
                } else {
                    System.out.println("El identificador [" + ((NodoIdentificador) HI).getNombre()
                            + "] no es compatible con [" + ((NodoIdentificador) HD).getNombre() + "]");
                }
            }

        }

        return null;

    }

    public NodoBooleano Operaciones_booleana(int HI, int HD, tipoOp tipo) {
        NodoBooleano temporal = null;

        if (tipo == tipoOp.igual) {
            temporal = new NodoBooleano(HI == HD);
        } else if (tipo == tipoOp.mayor) {
            temporal = new NodoBooleano(HI > HD);
        } else if (tipo == tipoOp.menor) {
            temporal = new NodoBooleano(HI < HD);
        } else if (tipo == tipoOp.mayor_eq) {
            temporal = new NodoBooleano(HI >= HD);
        } else if (tipo == tipoOp.menor_eq) {
            temporal = new NodoBooleano(HI <= HD);
        }

        return temporal;
    }

    public NodoBooleano operacion_Logica(boolean HI, boolean HD, tipoOp tipo) {
        NodoBooleano temp = null;
        boolean aux;
        if (tipo == tipoOp.and) {
            temp = new NodoBooleano(HI && HD);
        } else if (tipo == tipoOp.or) {
            temp = new NodoBooleano(HI || HD);
        }
        return temp;
    }

    public NodoValor Operaciones_matematicas(int HI, int HD, tipoOp tipo) {
        NodoValor temporal = null;
        if (tipo == tipoOp.mas) {

            temporal = new NodoValor(HI + HD);
        }
        if (tipo == tipoOp.menos) {
            temporal = new NodoValor(HI - HD);
        }
        if (tipo == tipoOp.por) {
            temporal = new NodoValor(HI * HD);
        }
        if (tipo == tipoOp.entre) {
            temporal = new NodoValor(HI / HD);
        }
        return temporal;
    }

    public NodoBase Comprobacion(int num){
    NodoValor temp;
        temp=new NodoValor(num);
        return temp;
    }
    public NodoBase Comprobacion(boolean valor){
    NodoBooleano temp;
        temp=new NodoBooleano(valor);
        return temp;
    }
    
    public void imprimir(String mensaje,int tipo){
        //1 -> mensajes normales
        //2 -> mensajes warning
        //3 -> mensajes error y salir del programa
        switch (tipo){
                case 1:
                    System.out.println(mensaje);
                case 2:
                    System.out.println("\u001B[33m"+mensaje+"\u001B[0m");
                case 3:
                    System.err.println(mensaje);
                    System.err.println("TERMINANDO LA DEPURACION POR ERROR GRAVE");                    
                    System.exit(0);
                 
                   
        }
    }
}
