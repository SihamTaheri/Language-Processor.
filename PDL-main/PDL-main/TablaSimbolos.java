

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TablaSimbolos {

    HashMap <Integer, Entrada> tabla;
    LinkedHashMap<Integer, Entrada> tabla2 = new LinkedHashMap<Integer, Entrada>();//tabla para obtener el orden
    public static int desplazamiento;

    int numEntradas = 0; // por revisar que empecemos en 0
    int idTabla = 0;

    public TablaSimbolos(int idTabla){
        this.numEntradas = 0;
        this.idTabla = idTabla;
        desplazamiento = 0;
       tabla = new HashMap<Integer, Entrada>();
    }
	
    // Dado un lexema, devuelve su identificador en la tabla. Si no esta, devuelve -1. 
    public int buscarEntrada(String lexema){
        int entrada = -1;

        for (Entry<Integer, TablaSimbolos.Entrada> entrad : this.tabla.entrySet()) {
           
        	if(entrad.getValue().lexema.equals(lexema)) {
        		entrada =entrad.getKey();
        	}
           
        }
       
        return entrada;
    }
    public TablaSimbolos.Entrada busc(int pos)
    {
    	Entrada ent = null;
    	
    	if(tabla.containsKey(pos))
    	{
    		ent = tabla.get(pos);
    	}
		return ent;
    	
    }
   
    // Dado un lexema, inserta en la tabla y devuelve su identificador. 
    public int insertarEntrada(String lexema){
        Entrada actual = new Entrada(lexema, numEntradas,null,-1, null,null, null);
        numEntradas++;
        tabla.put(actual.id,actual);
        return actual.id;
    }

    // Imprime en un archivo nuevo en el formato especificado la tabla de símbolos actual.
    public void volcarFichero(TablaSimbolos tb , boolean imp){ 
	    try {
            // Crear un FileWriter para el archivo
        	FileWriter escritor = new FileWriter("tablita", imp);

           BufferedWriter bufferEscritor = new BufferedWriter(escritor);
           StringBuilder resultado = new StringBuilder();
           resultado.append("CONTENIDO DE LA TABLA #"+ tb.idTabla + ":\n");
            for (Entry<Integer, TablaSimbolos.Entrada> entrada : tb.tabla.entrySet()) {
                Integer clave = entrada.getKey();
                Entrada valor = entrada.getValue();
                System.out.print("El valor de la entrada cogida es "+ valor.lexema + "\n");
                    resultado.append("* LEXEMA: '"+valor.lexema+"' \n");
                   
                    resultado.append("Atributos:  " + "\n");
                    
                    resultado.append("+ tipo: '"+valor.tipo+"'\n");
                    
                    System.out.print("VALOR TIPO"+ valor.tipo);
                    
                    if(valor.tipo.equals("funcion"))
                    {
                    	
                    	if(valor.parametros != null) {
                    	 resultado.append("+ numParam: "+valor.parametros.size()+" \n");
                    	for(int m = 0; m< valor.parametros.size() ; m++) 
                    	{
                    		 resultado.append("+ TipoParam"+m+": '"+ valor.parametros.get(m) + "' \n ");
                    	}
                    	}
                    	else 
                    	{
                    		 resultado.append("+ numParam: 0 \n ");
                    	}
                    		if(valor.retorno == null)
                    			resultado.append("+ TipoRetorno: 'void' \n");
                    		else
                    			resultado.append("+ TipoRetorno: '" + valor.retorno + "'\n");
                    		
                    		resultado.append("+EtiqFuncion: '"+valor.etiq+"'\n");
                    }
                    
                    else {
                    resultado.append("+ despl: '"+valor.desplazamiento+"'\n");
                    }
                   //O BIEN PONEMOS TODO EN RESULTADO Y LUEGO METEMOS, O PONEMOS ESTA SENTENCIA EN CADA UNO DE LOS IFS
                    resultado.append("\n");
            }
            resultado.append("------------------------------------------------\n"); 
            resultado.append("\n");
            bufferEscritor.write(resultado.toString());
            // Cerrar el BufferedWriter
            bufferEscritor.close();
          
		//TODO: completar
	   } catch (IOException e) {
            e.printStackTrace();
        }
	}

    public int obtenerultimaEntrada() { // que en realidad es la penultima
    	
    	if(tabla.isEmpty()) {
    		System.out.print("SE METE EN LA QUE DICE QUE ESTA VACIO");
    		return 0;
    	}
        if (tabla.size() < 2) {
            // El mapa no tiene suficientes entradas para tener una penúltima entrada - devolvemos la ultima(la que acabamos de meter
            return tabla.entrySet().iterator().next().getValue().getDesplaz();
        }

        Entry<Integer, Entrada> penultimaEntrada = null;
        Entry<Integer, Entrada> ultimaEntrada = null;

        for (Entry<Integer, Entrada> entrada : tabla.entrySet()) {
            penultimaEntrada = ultimaEntrada;
            ultimaEntrada = entrada;
        }

        return penultimaEntrada.getValue().getDesplaz();
    }

    
public void eliminar(int pos) 
{	
 tabla.remove(pos);
}

public class Entrada{

	//resto de parametros...
	int numTabla=-1; // localizador de en que tabla esta cada entrada
	 //Si es funcion ponemos esto a 1, nos servirá en el volcar fichero
	// TODO: parametros funcion??
	String lexema;
	int id;
	String tipo;
	int desplazamiento;
	ArrayList<String> parametros;
	String retorno;
	String etiq;
	
	//constructor para variables
	public Entrada (String lexema, int id,String tipo, int desplazamiento, ArrayList<String> parametros, String retorno,String etiq){
		this.lexema=lexema;
		this.id =id;
		this.tipo = tipo;
		this.desplazamiento = desplazamiento;
		this.parametros = parametros;
		this.retorno = retorno;
		this.etiq = etiq;
	}
	
	public void setTipo(String tipox) {
		this.tipo=tipox;
	}
	public void setDesplaz(int desplazamientox) {
		this.desplazamiento=desplazamientox;
	}
	
	public int getDesplaz() {
		return desplazamiento;
	}
	public String gettipo() {
		return tipo;
	}
}
}