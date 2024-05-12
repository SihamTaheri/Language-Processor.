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
import java.util.Map.Entry;
import java.util.Set;


public class tablaSimbolos() {
     // si metemos la superclase
    
	this.tabla = new HashMap<String, Entrada>();
	
    
    
    
    public void setID(Integer id) {
		this.identificador = id;
	}
    public int getID() {
		return identificador;
	}
	
    public Entrada buscarPorLexema(String lexema){
			return this.tabla.get(lexema);
	}
	
    
  
     public Entrada insertarEntradaTS(String lexema) { //valornum si era un num, opera si era un operador
 	 
    	  
    		 if(this.tabla.containsKey(lexema) ) {  
        		 return tabla.get(lexema);
        	 }else {
        		 
        		 
        		 //AUNQUE SEA FUNCION O NO SE CREA ENTRADA LUEGO:
        		 Entrada entrada= new Entrada(lexema, i);
        		 tabla.put(lexema, entrada);
        		 entrada.setTabla(identificador);
        		 i++;
        		 return entrada;
        	 }

    	 return null;
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
            
           
                resultado= String.format("* LEXEMA: '%s'%n   \n", clave);
				bufferEscritor.write(resultado); //O BIEN PONEMOS TODO EN RESULTADO Y LUEGO METEMOS, O PONEMOS ESTA SENTENCIA EN CADA UNO DE LOS IFS
			       
				
				resultado= String.format("------------------------------------------------\n"); 
				bufferEscritor.write(resultado); //O BIEN PONEMOS TODO EN RESULTADO Y LUEGO METEMOS, O PONEMOS ESTA SENTENCIA EN CADA UNO DE LOS IFS

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

	//resto de parametros...
	int numTabla=-1; // localizador de en que tabla esta cada entrada
	 //Si es funcion ponemos esto a 1, nos servirá en el volcar fichero
	// TODO: parametros funcion??
	String lexema;
	int id;
	
	//constructor para variables
	public Entrada (String lexema, int id){
		this.lexema=lexema;
		this.id =id;
		entradas++;
	}
	
	//constructor para funciones
	//EN VEZ DE TIPOPARAM DEBERIA SER UN MAP DE DUPLAS EN EL QUE SE TENGA QUE METER (TIPOPARAM,MODOPARAM) SIENDO SU .SIZE EL NUMPARAMETROS QUE TENGAMOS
	
	public void setTabla(Integer tabla) {
		this.numTabla = tabla;
	}
	
}
	
public static void main(String args[]) {
	/*
LinkedHashMap<String,Integer> palRes = new LinkedHashMap();

tablaSimbolos nueva=new tablaSimbolos();
palRes.put("int", 1);
nueva.insertarEntradaTS("edad",0,0);
palRes.put("boolean", 2);
nueva.insertarEntradaTS("valido#",0,0);
palRes.put("cadena", 3);
nueva.insertarEntradaTS("nombre",0,0);
*/
//nueva.volcarEnFichero();
}

}
