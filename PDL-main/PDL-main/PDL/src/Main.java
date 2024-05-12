import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;



public class Main {
	
	public static HashMap<String,Integer> palRes = new HashMap();
	public static ArrayList<Character> idd = new ArrayList<Character>();
	public static ArrayList<Character> cadena = new ArrayList<Character>();
	public char caracter;
	public static int valor;
	public static boolean cad = false;
	public static boolean cifra = false;
	public static boolean id = false;
	public static boolean error = false;
	public static boolean comentario = false;
	public static boolean especial = false;
	public static int operador = 0;
	public static int coment = 0;
	public static BufferedWriter bufferEscritor;
	public static FileReader archivo;
	public static BufferedReader br;
	public static int p = 0;


	 public static void main(String args[]) 
	 {
		if(args.length != 1) 
		{
			
		}
		else {
		palabrasReservadas();
		 String nombreArchivo = args[0]; // Reemplaza con la ruta de tu archivo JavaScript
	        try {
	        	FileWriter escritor = new FileWriter("tokens");
	             archivo = new FileReader(nombreArchivo);
	             br = new BufferedReader(archivo);
	            bufferEscritor = new BufferedWriter(escritor);
	            lectura();
 
	            bufferEscritor.close();
	            archivo.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        
	        }
	 }
	 }
	 
	 
	 public static void lectura() throws IOException 
	 {
		 int p = 0;
		 char c;
		 char b;
		 String linea;
		 while((linea = br.readLine()) != null) 
		 {
			 comentario = false;
			 for(int i = 0; i<linea.length() && !comentario; i++)
			 {
		         c = linea.charAt(i);
				  if(Character.isDigit(c) && !id && !cad ) // identificacion de numeros
			     {
			    	 cifra = true;
			    	 if(valor == 0) 
			    	 {
			    		 valor = valor + Character.getNumericValue(c);
			    	 }
			    	 else 
			    	 {
			    		 valor = valor*10 + Character.getNumericValue(c);
			    	 }
			    	 coment = 0;
			     }
			     
			     else if(Character.isWhitespace(c) && !cad) // identificacion de espacios
			     {
			    	if(!error) {
			    	 p = 0;
			          if( idd.size() == 0 || idd.get(idd.size() -1) != '_')
			        	  genToken(); 
			          else
			        	  gestionErrores();
			    	 }
			    	else  	
			    	  gestionErrores();
			    		
			     }
			     
			     else if(((!Character.isDigit(c)&& !cifra && Character.isLetter(c)) || (Character.isDigit(c) && id) || (c == '_')) && !cad ) //identificacion de palabras
			     { 
			    	 coment = 0;
			    	 id = true;
			    	 idd.add(c);
			     }
			     
			     else if(c == '/'  && !cad) 
			     {
			    	if(coment<1)
			    		coment ++;
			    	else
			    		
			    		comentario = true;
			     }
			     else if( c =='(') 
			     {
			    	 coment = 0;
			    	 operador = 1;
			    	 genToken();
			     }
			     else if( c ==')') 
			     {
			    	 coment = 0;
			    	 operador = 2;
			    	 genToken();
			     }
			     else if( c =='{') 
			     {
			    	 coment = 0;
			    	 operador = 3;
			    	 genToken();
			     }
			     else if( c =='}') 
			     {
			    	 coment = 0;
			    	 operador = 4;
			    	 genToken();
			     }
			     else if( c ==';') 
			     {
			    	 genToken();
			    	 coment = 0;
			    	 operador = 5;
			    	 genToken();
			     }
			     else if( c ==',') 
			     {
			    	 coment = 0;
			    	 operador = 6;
			    	 genToken();
			     }
			     else if(c =='%')  
			     {
			    	 p++;
			    	 coment = 0;
			    	 operador = 7;
			    	 b = linea.charAt( i + 1);
			    	 if(b != '=') 
			    	 {
			    		 p = 0;
			    		 genToken();
			    	 }
			    	 
			    	  
			     }
			     else if( c =='=') 
			     {
			    	 if(p == 1) {
			    		 operador = 11;
			    	 genToken();}
			    	 else {
			    	 operador = 8;
			    	 coment = 0;
			    	 genToken();}
			     }
			     else if( c =='!') 
			     {
			    	 coment = 0;
			    	 operador = 9;
			    	 genToken();
			     }
			     else if( c =='<') 
			     {
			    	 coment = 0;
			    	 operador = 10;
			    	 genToken();
			     }
			     else if(c == '"') 
			     {
			    	 if(cad) {
			    	    cadena.add(c);
			    		genToken();
			    		}
			    	 else {
			    		 cadena.add(c);
			    		 cad = true;}
			     }
			     else if(cad) 
				 {
					cadena.add(c); 
				 }
			     
			     else 
			     {
			    	  error = true;
			     }
			     }
			 if(!error)
			 {
				 if( idd.size() == 0 ||idd.get(idd.size() -1 ) != '_')
	        	  genToken(); 
	          else
	        	  gestionErrores();
			  }
			 else
			   gestionErrores();
			  }
	 }
	 
	 public static void genToken() throws IOException 
	 {
		 if(cifra) 
		 {
			 String s = "< constint , " + valor + " >";
			 bufferEscritor.write(s);
			 bufferEscritor.write("\n");
			 valor = 0;
			 cifra = false;
			 coment = 0;
		 }
		 if(id) 
		 {
			 StringBuilder stringBuilder = new StringBuilder();
			 for (Character c : idd) {
				    stringBuilder.append(c);
				}
			 String cad = stringBuilder.toString();
			 
			 
			 if(palRes.containsKey(cad) || (cad.equals("boolean")) || (cad.equals("string")) || (cad.equals("return"))) 
			 {
				 if(cad.equals("return"))
					 cad = "ret";
				 if(cad.equals("boolean"))
					 cad = "bool";
				 if(cad.equals("string"))
					 cad = "st";
				 bufferEscritor.write("< " + cad + " ,>");
				 bufferEscritor.write("\n");
				 id = false;
				 idd.clear();
				 coment = 0;
			 }
			 else {
			 int n = tablaSimbolos.escribirTabla(cad);
			 String token = "< id , " + n + " >";
			 bufferEscritor.write(token);
			 bufferEscritor.write("\n");
			 id = false;
			 idd.clear();
			 coment = 0;
			 }
		 }
		 if(cad) 
		 {
			 StringBuilder stringBuilder = new StringBuilder();
			 for (Character c : cadena) {
				    stringBuilder.append(c);
				}
			 String cas = stringBuilder.toString();
			 String token = "< cad , " + cas + " >";
			 bufferEscritor.write(token);
			 bufferEscritor.write("\n");
			 id = false;
			 idd.clear();
			 coment = 0;
			 cadena.clear();
			 cad = false;
			 
		 }
		 if(operador != 0) 
		 {
			 switch(operador) 
			 {
			 case 1:
				 bufferEscritor.write("< abreparent ,>");
				 break;
			 case 2:
				 bufferEscritor.write("< cierraparent ,>");
				 break;
			 case 3:
				 bufferEscritor.write("< abrellave ,>");
				 break;
			 case 4:
				 bufferEscritor.write("< cierrallave ,>");
				 break;
			 case 5:
				 bufferEscritor.write("< puntoycoma ,>");
				 break;
			 case 6:
				 bufferEscritor.write("< coma ,>");
				 break;
			 case 7:
				 bufferEscritor.write("< mod ,>");
				 break;
			 case 8:
				 bufferEscritor.write("< igual ,>");
				 break;
			 case 9:
				 bufferEscritor.write("< neg ,>");
				 break;
			 case 10:
				 bufferEscritor.write("< menor ,>");
				 break;
			 case 11:
				 bufferEscritor.write("< asignresto ,>");
				 break;
			 }
			 bufferEscritor.write("\n");
			 coment = 0;
			 operador = 0;
		 }
		 
		 else 
		 {
			 System.out.print(" ");
			 id = false;
			 cifra = false;
			 coment = 0;
			 
		 }
	 }

	 public static void gestionErrores() 
	 {
		 idd.clear();
		 cifra = false;
		 id = false;
		 valor = 0;
		 error = false;
	 }
		 
	 public static void palabrasReservadas() 
	 {
		palRes.put("int", 1);
		palRes.put("if", 1);
		palRes.put("bool", 1);
		palRes.put("fun", 1);
		palRes.put("get", 1);
		palRes.put("let", 1);
		palRes.put("put", 1);
		palRes.put("ret", 1);
		palRes.put("st", 1);
		palRes.put("void", 1);
		palRes.put("while", 1);
		palRes.put("constint", 1);
	 }
	 
}
