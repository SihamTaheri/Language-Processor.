package src;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class tablaSimbolos {
	//PREGUNTA: no deberiamos de además implementar como una super clase de la tablaSimbolos para controlar todas las tablas de una?
	 	 
	public HashMap <String, Entrada> tabla = new HashMap();
	private int identificador=-1;
    static int contadordeid=-1;
    public static BufferedWriter bufferEscritor;
	public static FileReader archivo;
	public static BufferedReader br;
	int i=1;
	public static HashMap<String,Integer> palRes = new HashMap();

	// TODO: ?la tabla de simbolos se crea desde el semantico
	/* public void crearTS() {	
	} */
    
    public tablaSimbolos(HashMap<String,Integer> palRes) {
     // si metemos la superclase´
    this.setID(contadordeid); //por cada tabla 
	this.tabla = new HashMap<String, Entrada>();	
	tablaSimbolos.contadordeid++;
	
	this.palRes=palRes;
    }
    
    
    public void setID(Integer id) {
		this.identificador = id;
	}
	
    public Entrada buscarLexema(String lexema){
		//TODO: completar
		return this.tabla.get(lexema);
	}
	
    
    public static String obtenerUltimoValor(HashMap<String, Integer> tabla) {
        String ultimoValor = null;

        for (Map.Entry<String, Integer> entry : tabla.entrySet()) {
            ultimoValor = entry.getKey();
        }

        return ultimoValor;
    }
    
    
     public int insertarEntradaTS(String lexema, int valornum, int opera) {
    	 
    	 boolean declarado=false;
    	 boolean iniynomb=false;
    	 boolean metioentre=false;
    	 boolean argumen=false;
		//TODO: completar
		//Como es solo 1 tabla inicial para el 14 simplemente;
    	 
    	 if(palRes.containsKey(lexema)) {
    		int valor = palRes.get(lexema);
             // Eliminar el elemento del LinkedHashMap
             palRes.remove(lexema);
             // Agregar el elemento al final del LinkedHashMap
             palRes.put(lexema, valor);
	
    	 } //COMO YA DEFINIMOS AL PRINCIPIO PALRES DE LO QUE TIENE, LO QUE VA A RECIBIR ESTE METODO ES O BIEN ELEMENTOS DE PALRES
    		 //O BIEN ELEMENTOS QUE NO SON PALRES, PORQUE NO NOS VAN A PODER PASAR ELEMENTOS QUE NO SON DE PALRES Y SI SON PALABRAS RESERVADAS
    		//luego si se mete char a = 0; será lo mismo que hacer a b =0; ya que char no lo tenemos ni en la palresç
    	 //OPCIONES QUE VALORAMOS INT A, B ; PERO CUANDO LLEGUE EL PUNTO Y COMA, YA NO PUEDE INTERPRETAR LO QUE SEA COMO INT LUEGO
    	 ArrayList<String> claves2 = new ArrayList<>(palRes.keySet());

		 String ultimoValor2 = claves2.get(claves2.size() - 1); 
    	 
    	 if(lexema.equals(";")) {
    		 palRes.put("null",1); //SI HAY UN PUNTO Y COMA Y NO SE SE HA VUELTO A PONER UNA PALRES DELANTE, ES UN ERROR
    	     declarado=false;
    	 }
    	 
    	 
    	 if(this.tabla.containsKey(lexema) && !lexema.equals(";")) {  
    		 return tabla.get(lexema).id;
    	 }else {
    		 //aqui nos pueden meter una a , b o simplemente una "," un "("
    		 
    		  //DETECTAR SI ES UNA FUNCION O NO, Y SI NO, DETECTAR DE QUE TIPO ES
    		 //COÑO SI ES UN INT EL ULTIMO ELEMENTO METIDO EN LA TABLA DE PALABRAS RESERVADAS VA A SER EL "int";
    		 //lo hacemos asi, porque si no como coño sacamos que en analisis lexico, lo de antes???
             //obtenerUltimoValor(palRes);
    		 //si el valor es 0 mirar lo otro, si no, no 
    		 
    		 ArrayList<String> claves = new ArrayList<>(palRes.keySet());

    		 String ultimoValor = claves.get(claves.size() - 1);  //obtenerUltimoValor(palRes);
    		 //SI ES -1 ES UN OPERADOR
    		 
    		 if(!ultimoValor.equals("null") && lexema !=null && opera !=-1) { // SI EL ULTIMO VALOR NO ES NULL, ES PORQUE ES UNA PALabra que ha sido declarada antes con int..., ni tampoco un operador
    			 
    		 //COMO SE QUE ESTO NO ES DE UNA FUNCION QUE SE ESTA DECLARANDO??
    			 //HACEMOS SETIDFUNCION!!
    		 if(ultimoValor.equals("boolean")) {
    			 String x="bool";
    			Entrada entrada=new Entrada(lexema,x,i,1); //LOS boolean TIENEN DESPLAZAMIENTO 1
    			if(argumen) {
    				
    			}
    			this.tabla.put(lexema, entrada); 
    		 }else if(ultimoValor.equals("cadena")) {
    			 String x="constint";
    			Entrada entrada=new Entrada(lexema,x,i,2); //LOS ENTEROS TIENEN DESPLAZAMIENTO 2
    			this.tabla.put(lexema, entrada); 
    		 } else if(ultimoValor.equals("int")) {
    			 String x="entero";
    			Entrada entrada=new Entrada(lexema,x,i,2); //LOS ENTEROS TIENEN DESPLAZAMIENTO 2
    			this.tabla.put(lexema, entrada); 
    		 }
    		 
    		 //SI ES UNA FUNCION SEGURO
    		 
    		 if(ultimoValor.equals("void") && metioentre) { //la condicion metioentre es para que no se meta dentro void fun(void casa)
    			 String x="'funcion'";
    			Entrada entrada=new Entrada(lexema,x,i,2); //LOS ENTEROS TIENEN DESPLAZAMIENTO 2
    			entrada.idfuncion=1;
    			this.tabla.put(lexema, entrada); 			
    		 } 
    		  //ENTONCES PARA LA ENTREGA DE TABLA DE SIMBOLOS SIMPELEMENTE, HABRIA QUE SACAR EL LEX Y LAS DIF TABLAS QUE SE HAN CREADO NO? NO HACE FALTA PONER 
    		 //MARTA HACER SEMANTICO
    		 
    		 i++;
    		 iniynomb=true;
    		 }
    		 
    		     if((lexema.equals("(") && iniynomb==true)) { //ES UNA FUNCION
    			 ArrayList<String> claves3 = new ArrayList<>(tabla.keySet());

        		 String ultimoValor3 = claves3.get(claves3.size() - 1);  
        		 
        		 Entrada ultimaE= tabla.get(ultimoValor3);
        		 ultimaE.idfuncion=1; 
        		 declarado=false; //SIGNIFICA QUE O BIEN SE SABE QUE ES UNA FUNCION 
        		 metioentre=true;
    		   }
    		     
    		     if(declarado==false && metioentre) { //AQUI SE METEN LOS ARGUMENTOS DE LA FUNCION
    		    	 
    		    	 ArrayList<String> claves3 = new ArrayList<>(tabla.keySet());

            		 String ultimoValor3 = claves3.get(claves3.size() - 1);  
            		 
            		 Entrada ultimaE= tabla.get(ultimoValor3);
            		 
            		 //codigo para estudiar el interior de una funcion
    		     }
    		     
    		    if(declarado==false && metioentre && lexema.equals(")")) { //ES UNA FUNCION
        			 ArrayList<String> claves3 = new ArrayList<>(tabla.keySet());

            		 String ultimoValor3 = claves3.get(claves3.size() - 1);   
            		 Entrada ultimaE= tabla.get(ultimoValor3);
            		 ultimaE.idfuncion=1; 
            		 metioentre=false; 
            		 //ESTA METIDO DENTRO DE UNA FUNCION?
            		 
            		 
        		   }
    		 
    		 
    		 }
    	 
	   
	    return tabla.get(lexema).id;
		
	}

     public void volcarEnFichero(){ 
		
		int i=0;
		

        try {
            // Crear un FileWriter para el archivo
        	FileWriter escritor = new FileWriter("tablita");
            
            
           bufferEscritor = new BufferedWriter(escritor);
           
           
           bufferEscritor.write("CONTENIDO DE LA TABLA #"+ this.identificador+ "\n");
           
		for (Map.Entry<String, Entrada> entrada : this.tabla.entrySet()) {
			
			String resultado="";
            String clave = entrada.getKey();
            Entrada valor = entrada.getValue();
            
       if(valor.idfuncion==0) { //Es una entrada normal no funcion
            	
				resultado= String.format("* LEXEMA: '%s'%n   ATRIBUTOS:%n   + tipo: '%s'%n   + desplazamiento: %d%n + identificadordetoken:%d%n \n", clave, valor.tipo, valor.desp,valor.id); 
				bufferEscritor.write(resultado); //O BIEN PONEMOS TODO EN RESULTADO Y LUEGO METEMOS, O PONEMOS ESTA SENTENCIA EN CADA UNO DE LOS IFS
			       
				
				resultado= String.format("------------------------------------------------\n"); 
				bufferEscritor.write(resultado); //O BIEN PONEMOS TODO EN RESULTADO Y LUEGO METEMOS, O PONEMOS ESTA SENTENCIA EN CADA UNO DE LOS IFS
			       
			
			
		}else {//ES UNA FUNCION---Para ello valorar cuandos parametros tiene
			
			 bufferEscritor.write("\n-------------------------------------------------------\n");
			 bufferEscritor.write("TABLA DE LA FUNCION"+clave+"#"+ this.identificador);
	           
			
			resultado= String.format("* LEXEMA: '%s'%n   ATRIBUTOS:%n   + tipo: '%s'%n   + numParam: %d\n", valor.id, valor.tipo, valor.numParametros); 
			
			
			while(i<valor.numParametros) { //DEPENDIENDO DE QUE TANTOS PARAMETROS TENGA TIENES QUE METER X NUM PARAMETROS
				resultado +=String.format("* TipoParam'%d': '%s'%n   ModoParam",i,valor.tipoParam, valor.numParametros); //AQUI HABRIA QUE METER LO DEL MODO QUE LO PODRIAMOS HACER COMO LA DUPLA QUE MENCIONO ABAJO (LEER)
			}
		}
       
		}
		

            // Cerrar el BufferedWriter
        bufferEscritor.close();
		
		
		
		
		//TODO: completar
	   }catch (IOException e) {
        e.printStackTrace();
    }
	}
     
    		
     
public class Entrada{
 	//Creamos un contador estatico para nombrar el numero de entradas que tiene esta tabla
	static int entradas=0;
	// TODO: de donde sale el tipo?
	int id; //LEXEMA
	String tipo;
	int desp;
	int numParametros;
	//resto de parametros...
	int numTabla=-1; // localizador de en que tabla esta cada entrada
	String tipoParam;
	String tipoDev;
	String Etiq;
	String lexema;
	int idfuncion=0; //Si es funcion ponemos esto a 1, nos servirá en el volcar fichero
	// TODO: parametros funcion??
	/*
	public Entrada (String lexema, int id){
		this.id = id;
		this.tipo=null;
		this.desp=0;
		this.lexema=lexema;
		
	}*/
	//constructor para variables
	public Entrada (String lexema, String tipo,int id,int desp){
		this.id = id;
		this.tipo = tipo;
		this.desp=0;
		this.lexema=lexema;
		this.desp=desp;

	}
	//constructor para funciones
	//EN VEZ DE TIPOPARAM DEBERIA SER UN MAP DE DUPLAS EN EL QUE SE TENGA QUE METER (TIPOPARAM,MODOPARAM) SIENDO SU .SIZE EL NUMPARAMETROS QUE TENGAMOS
	
	public Entrada (String lexema, String tipo,int numParametros,String tipoParam,String tipoDev,String Etiq,int id){
		this.id = id;
		this.tipo = tipo;
		this.numParametros=numParametros;
		this.tipoParam=tipoParam;
		this.tipoDev=tipoDev;
		this.Etiq=Etiq;
	    this.idfuncion=1;
	    this.desp=0;
		this.lexema=lexema;
		

	}
	
	public void setTabla(Integer tabla) {
		this.numTabla = tabla;
	}
}
	
public static void main(String args[]) {
	
LinkedHashMap<String,Integer> palRes = new LinkedHashMap();

tablaSimbolos nueva=new tablaSimbolos(palRes);
palRes.put("int", 1);
nueva.insertarEntradaTS("edad",0,0);
palRes.put("boolean", 2);
nueva.insertarEntradaTS("valido#",0,0);
palRes.put("cadena", 3);
nueva.insertarEntradaTS("nombre",0,0);

nueva.volcarEnFichero();
}

}