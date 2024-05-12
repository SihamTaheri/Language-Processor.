import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;



public class Main {
	
	public static HashMap<String,Integer> palRes = new HashMap<String,Integer>();
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
	public static BufferedWriter bufferErrores;
	public static FileReader archivo;
	public static BufferedReader br;
	public static int p = 0;
	public static int lng = 0;
	public static int nlinea = 0;
    public static int recuperarSintactico = 0;
    public static int recuperarSemantico = 0;
	public static TablaSimbolos tabla;
	public static String token;
	public static Boolean impresa = false;
	public static boolean fallaSemantico = false;

	 public static void main(String args[]) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	 {
		if(args.length != 1) 
		{
			
		}
		else {
			palabrasReservadas();
		 	String nombreArchivo = args[0]; // Reemplaza con la ruta de tu archivo JavaScript
	        try {
	        	FileWriter escritor = new FileWriter("tokens");
	        	FileWriter escrito = new FileWriter("errores");
	            archivo = new FileReader(nombreArchivo);
	            br = new BufferedReader(archivo);
	            bufferEscritor = new BufferedWriter(escritor);
	            bufferErrores = new BufferedWriter(escrito);
				//bufferErrores.write("dime que se hace main\n");
	            sintactico.inicializar_sentencia(/*escrito*/);
	           	tabla= semantico.Inicializar(/*escrito*/);
	            lectura();
	            tabla.volcarFichero(tabla, impresa);
	            bufferEscritor.close();
	            bufferErrores.close();
	            archivo.close();
	        } catch (IOException e) {
				bufferErrores.flush();
	            e.printStackTrace();
	        }
	 }
	 }
	 
	 //lectura
	 public static void lectura() throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException 
	 {
		 int p = 0;
		 char c;
		 char b;
		 String linea;
		 while((linea = br.readLine()) != null) 
		 {
			 nlinea ++;
			 comentario = false;
			 for(int i = 0; i<linea.length() && (recuperarSintactico == 0) && !fallaSemantico && !comentario; i++)
			 {
				 System.out.print("El valor del estado del semantico es " + recuperarSemantico + "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
				 
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
			    	 if(valor>32767)
			    		 gestionErrores();
			     }
			     
			     else if(Character.isWhitespace(c) && !cad) // identificacion de espacios
			     {
			    	if(!error) {
			    	 p = 0;
			          if( idd.size() == 0 || idd.get(idd.size() -1) != '_') {

			        	  token = genToken(); 
		        	      recuperarSintactico = sintactico.recibir_token(token);
		        	      if(recuperarSintactico != 1)
		        	      recuperarSemantico = semantico.recibirToken(token);
		        	      System.out.print("\n he vuelto \n");}
						else if (idd.get(idd.size() -1) == '_'){
							token = genToken(); 
		        	      recuperarSintactico = sintactico.recibir_token(token);
		        	      if(recuperarSintactico != 1)
		        	      recuperarSemantico = semantico.recibirToken(token);
		        	      System.out.print("\n he vuelto \n");
						}
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
			     else if( c =='('&& !cad) 
			     {
			    	 if(id || cifra ||cad) {
			    		 token = genToken(); 
			        	  recuperarSintactico = sintactico.recibir_token(token);
			        	  if(recuperarSintactico != 1)
			        	  recuperarSemantico = semantico.recibirToken(token);
			        	  }
			    	 coment = 0;
			    	 operador = 1;
			    	 token = genToken(); 
			    	 recuperarSintactico = sintactico.recibir_token(token);
			    	 if(recuperarSintactico != 1)
			    	 recuperarSemantico = semantico.recibirToken(token);
		        	  
			     }
			     else if( c ==')'&& !cad) 
			     {
			    	 if(id || cifra ||cad) {
			    		 token = genToken(); 
			        	  recuperarSintactico = sintactico.recibir_token(token);
			        	  if(recuperarSintactico != 1)
			        	  recuperarSemantico = semantico.recibirToken(token);
			        	  }
			    	 coment = 0;
			    	 operador = 2;
			    	 token = genToken(); 
		        	  recuperarSintactico = sintactico.recibir_token(token);
		        	  if(recuperarSintactico != 1)
		        	  recuperarSemantico = semantico.recibirToken(token);
			     }
			     else if( c =='{'&& !cad) 
			     {
			    	 if(id || cifra ||cad) {
			    		 token = genToken(); 
			        	  recuperarSintactico = sintactico.recibir_token(token);
			        	  if(recuperarSintactico != 1)
			        	  recuperarSemantico = semantico.recibirToken(token);
			        	  }
			    	 coment = 0;
			    	 operador = 3;
			    	 token = genToken(); 
		        	  recuperarSintactico = sintactico.recibir_token(token);
		        	  if(recuperarSintactico != 1)
		        	  recuperarSemantico = semantico.recibirToken(token);
			     }
			     else if( c =='}'&& !cad) 
			     {
			    	 if(id || cifra ||cad) {
			    		 token = genToken(); 
			        	  recuperarSintactico = sintactico.recibir_token(token);
			        	  if(recuperarSintactico != 1)
			        	  recuperarSemantico = semantico.recibirToken(token);
			        	  }
			    	 coment = 0;
			    	 operador = 4;
			    	 token = genToken(); 
		        	  recuperarSintactico = sintactico.recibir_token(token);
		        	  if(recuperarSintactico != 1)
		        	  recuperarSemantico = semantico.recibirToken(token);
			     }
			     else if( c ==';'&& !cad) 
			     {
			    	 if(id || cifra ||cad) {
			    		 token = genToken(); 
			        	  recuperarSintactico = sintactico.recibir_token(token);
			        	  if(recuperarSintactico != 1)
			        	  recuperarSemantico = semantico.recibirToken(token);
			        	  }
			    	 coment = 0;
			    	 operador = 5;
			    	 token = genToken(); 
			    	 System.out.print("El token qu ese va a enviar del ; es " + token);
		        	  recuperarSintactico = sintactico.recibir_token(token);
		        	  if(recuperarSintactico != 1)
		        	  recuperarSemantico = semantico.recibirToken(token);
			     }
			     else if( c ==','&& !cad) 
			     {
			    	 if(id || cifra ||cad) {
			    		 token = genToken(); 
			        	  recuperarSintactico = sintactico.recibir_token(token);
			        	  if(recuperarSintactico != 1)
			        	  recuperarSemantico = semantico.recibirToken(token);
			        	  }
			    	 coment = 0;
			    	 operador = 6;
			    	 token = genToken(); 
		        	  recuperarSintactico = sintactico.recibir_token(token);
		        	  if(recuperarSintactico != 1)
		        	  recuperarSemantico = semantico.recibirToken(token);
			     }
			     else if(c =='%'&& !cad)  
			     {
			    	 p++;
			    	 coment = 0;
			    	 operador = 7;
			    	 if(linea.length()> i+1)
			    	 b = linea.charAt( i + 1);
			    	 else
			    	 b = 'q';
			    	 if(b != '=') 
			    	 {
			    		 if(id || cifra ||cad) {
			    		 token = genToken(); 
			        	  recuperarSintactico = sintactico.recibir_token(token);
			        	  if(recuperarSintactico != 1)
			        	  recuperarSemantico = semantico.recibirToken(token);
			        	  }
			    		 p = 0;
			    		 operador = 7;
			    		 token = genToken(); 
			        	  recuperarSintactico = sintactico.recibir_token(token);
			        	  if(recuperarSintactico != 1)
			        	  recuperarSemantico = semantico.recibirToken(token);
			    	 }
			    	 
			    	  
			     }
			     else if( c =='='&& !cad) 
			     {
			    	 if(p == 1) {
			    		 if(id || cifra ||cad) {
				    		 token = genToken(); 
				        	  recuperarSintactico = sintactico.recibir_token(token);
				        	  if(recuperarSintactico != 1)
				        	  recuperarSemantico = semantico.recibirToken(token);
				        	  }
			    		 operador = 11;
			    		 token = genToken(); 
			        	  recuperarSintactico = sintactico.recibir_token(token);
			        	  if(recuperarSintactico != 1)
			        	  recuperarSemantico = semantico.recibirToken(token);
			        	  }
			    	 else {
			    		 if(id || cifra ||cad) {
				    		 token = genToken(); 
				        	  recuperarSintactico = sintactico.recibir_token(token);
				        	  if(recuperarSintactico != 1)
				        	  recuperarSemantico = semantico.recibirToken(token);
				        	  }
			    	 operador = 8;
			    	 coment = 0;
			    	 token = genToken(); 
		        	  recuperarSintactico = sintactico.recibir_token(token);
		        	  if(recuperarSintactico != 1)
		        	  recuperarSemantico = semantico.recibirToken(token);
		        	  System.out.print("El estado de recuperarSintactico tras el igual es " + recuperarSintactico + " el valor de i es " + i + " la longitud de la linea es " + linea.length() + " y el valor de comentario es " + comentario);
			    	   }
			     }
			     else if( c =='!') 
			     {
			    	 if(id || cifra ||cad) {
			    		 token = genToken(); 
			        	  recuperarSintactico = sintactico.recibir_token(token);
			        	  if(recuperarSintactico != 1)
			        	  recuperarSemantico = semantico.recibirToken(token);
			        	  }
			    	 coment = 0;
			    	 operador = 9;
			    	 token = genToken(); 
		        	  recuperarSintactico = sintactico.recibir_token(token);
		        	  if(recuperarSintactico != 1)
		        	  recuperarSemantico = semantico.recibirToken(token);
			     }
			     else if( c =='<'&& !cad) 
			     {
			    	 if(id || cifra ||cad) {
			    		 token = genToken(); 
			        	  recuperarSintactico = sintactico.recibir_token(token);
			        	  if(recuperarSintactico != 1)
			        	  recuperarSemantico = semantico.recibirToken(token);
			        	  }
			    	 coment = 0;
			    	 operador = 10;
			    	 token = genToken(); 
		        	  recuperarSintactico = sintactico.recibir_token(token);
		        	  if(recuperarSintactico != 1)
		        	  recuperarSemantico = semantico.recibirToken(token);
			     }
			     else if(c == '\'') 
			     {
			    	 if(cad) {
			    		 if(lng > 64) 
				    		 gestionErrores();
			    		 else {
			    			 if(id || cifra ||cad) {
					    		 token = genToken(); 
					        	  recuperarSintactico = sintactico.recibir_token(token);
					        	  if(recuperarSintactico != 1)
					        	  recuperarSemantico = semantico.recibirToken(token);
					        	  }
			    	    cadena.add(c);
			    	    token = genToken(); 
			        	  recuperarSintactico = sintactico.recibir_token(token);
			        	  if(recuperarSintactico != 1)
			        	  recuperarSemantico = semantico.recibirToken(token);
			        	  System.out.print("\n he vuelto \n");
			    		}
			    		}
			    	 else {
			    		 cadena.add(c);
			    		 cad = true;}
			     }
			     else if(cad) 
				 {
					cadena.add(c); 
					lng++;
				 }
			     
			     else 
			     {
			    	  error = true;
			     }
			     }
			 if(recuperarSintactico != 0 || recuperarSemantico != 0) 
			 {
				 System.out.print("ha fallado el sintactico o el semantico");
				 return;
			 }
			 
			 
			 if(!error)
			 {
				 if( idd.size() == 0 ||idd.get(idd.size() -1 ) != '_') {
	        	  token = genToken(); 
	        	  System.out.print("El token que Meto en la zona recien modificada es " + token);
	        	  recuperarSintactico = sintactico.recibir_token(token);
	        	  if(recuperarSintactico != 1)
	        	  recuperarSemantico = semantico.recibirToken(token);
	        	  //recuperarSintactico = sintactico.recibir_token(token);
	        	  System.out.print("\n he vuelto \n");
	        	  }
	          else
	        	  gestionErrores();
			  }
			 else
			   gestionErrores();
			  }
		 if((recuperarSintactico == 0) && !fallaSemantico) {
		 recuperarSintactico = sintactico.recibir_token("EOF");
		 if(recuperarSintactico != 1)
		 recuperarSemantico = semantico.recibirToken("EOF");}
		 if (recuperarSintactico!= 0)
			 System.out.print("el sintactico falaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n");
		 if(fallaSemantico)
			 System.out.print("el semantico falaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n");
			 
		 //sintactico.fin();
	 }
	 
	 public static String genToken() throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException 
	 {
		 if(cifra) 
		 {
			 String s = "< constint , " + valor + " >";
			 bufferEscritor.write(s);
			 //tablaSimbolos.insertarEntradaTS(null,valor,69);
			 bufferEscritor.write("\n");
			 valor = 0;
			 cifra = false;
			 coment = 0;
			 System.out.print("Estoy en el gentoken de cifra ");
			 return "constint";
		 }
		 if(id) 
		 {
			 StringBuilder stringBuilder = new StringBuilder();
			 for (Character c : idd) {
				    stringBuilder.append(c);
				}
			 String cad = stringBuilder.toString();
			 
			 
			 if(palRes.containsKey(cad) || (cad.equals("boolean")) || (cad.equals("string")) || (cad.equals("return") || (cad.equals("function")))) 
			 {
				 if(cad.equals("return"))
					 cad = "ret";
				 if(cad.equals("boolean"))
					 cad = "bool";
				 if(cad.equals("string"))
					 cad = "st";
				 if(cad.equals("function"))
					 cad = "fun";
				 if(cad.equals("const"))
					 cad = "const";
				//sintactico.recibir_token(cad);
				 bufferEscritor.write("< " + cad + " ,>");
				 //tablaSimbolos.insertarEntradaTS(cad,0,69);
				 bufferEscritor.write("\n");
				 id = false;
				 idd.clear();
				 coment = 0;
				 lng = 0;
				 return cad;
			 }
			 else {
				 //int n = tablaSimbolos.insertarEntradaTS(cad,0,69);
				int n = tabla.buscarEntrada(cad);
				 if (n ==  -1){
					n = tabla.insertarEntrada(cad);
				 }
				 String token = "< id , " + n + " >";
				 bufferEscritor.write(token);
				// tablaSimbolos.insertarEntradaTS(token,0,69);
				 bufferEscritor.write("\n");
				 id = false;
				 idd.clear();
				 coment = 0;
				 lng = 0;
				 return String.valueOf(n);
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
			// tablaSimbolos.insertarEntradaTS(token,0,69);
			 bufferEscritor.write("\n");
			 id = false;
			 idd.clear();
			 coment = 0;
			 cadena.clear();
			 cad = false;
			 lng = 0;
			 return "cadena";
			 
		 }
		 if(operador != 0) 
		 {
			 coment = 0;
			 
			 switch(operador) 
			 {
			 case 1:
				 bufferEscritor.write("< abreparent ,>");
				 bufferEscritor.write("\n");
				 operador = 0;
				 //tablaSimbolos.insertarEntradaTS("(",0,-1);
				 return "(";
				
			 case 2:
				 bufferEscritor.write("< cierraparent ,>");
				 bufferEscritor.write("\n");
				 operador = 0;
				// tablaSimbolos.insertarEntradaTS(")",0,-1);
				 return ")";
			 case 3:
				 bufferEscritor.write("< abrellave ,>");
				 bufferEscritor.write("\n");
				 operador = 0;
				// tablaSimbolos.insertarEntradaTS("{",0,-1);
				 return "{";
			 case 4:
				 bufferEscritor.write("< cierrallave ,>");
				 bufferEscritor.write("\n");
				 operador = 0;
				// tablaSimbolos.insertarEntradaTS("}",0,-1);
				 return "}";
			 case 5:
				 bufferEscritor.write("< puntoycoma ,>");
				 bufferEscritor.write("\n");
				 operador = 0;
				// tablaSimbolos.insertarEntradaTS(";",0,-1);
				 return ";";
			 case 6:
				 bufferEscritor.write("< coma ,>");
				 bufferEscritor.write("\n");
				 operador = 0;
				// tablaSimbolos.insertarEntradaTS(",",0,-1);
				 return ",";
			 case 7:
				 bufferEscritor.write("< mod ,>");
				 bufferEscritor.write("\n");
				 operador = 0;
				// tablaSimbolos.insertarEntradaTS("%",0,-1);
				 return "%";
			 case 8:
				 bufferEscritor.write("< igual ,>");
				 bufferEscritor.write("\n");
				 operador = 0;
				// tablaSimbolos.insertarEntradaTS("=",0,-1);
				 return "=";
			 case 9:
				 bufferEscritor.write("< neg ,>");
				 bufferEscritor.write("\n");
				 operador = 0;
				// tablaSimbolos.insertarEntradaTS("!",0,-1);
				 return "!";
			 case 10:
				 bufferEscritor.write("< menor ,>");
				 bufferEscritor.write("\n");
				 operador = 0;
				// tablaSimbolos.insertarEntradaTS("<",0,-1);
				 return "<";
			 case 11:
				 bufferEscritor.write("< asignresto ,>");
				 bufferEscritor.write("\n");
				 operador = 0;
				// tablaSimbolos.insertarEntradaTS("%=",0,-1);
				 return "%=";
			 }
			 
		 }
		 
		 else 
		 {
			 System.out.print(" ");
			 id = false;
			 cifra = false;
			 coment = 0;
			 return "";
			 
		 }
		return null;
	 }

	 public static void gestionErrores() throws IOException 
	 {
		 idd.clear();
		 cifra = false;
		 id = false;
		 error = false;
		 cad = false;
		 cadena.clear();
		
		 
		 if(lng > 64) 
		 {
			 bufferErrores.write("Error: la cadena introducida supera el l�mite de 64 caracteres en la linea " + nlinea);
			 bufferErrores.write("\n");
			 lng = 0;
		 }
		 else if(valor>32767) 
		 {
			 bufferErrores.write("Error: el valor introducido es mayor al limite en la linea " + nlinea);
			 bufferErrores.write("\n");
		 }
		 else 
		 {
			 bufferErrores.write("Error: el caracter introducido no es leido por nuestro analizador l�xico en la linea " + nlinea);
			 bufferErrores.write("\n"); 
		 }
		 valor = 0;
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

class par
{

	String tipo;
	int pos;
	public par(String tipo,int pos) 
	{
		this.tipo = tipo;
		this.pos = pos;
	}
}