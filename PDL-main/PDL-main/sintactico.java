package src;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class sintactico {
	public static ArrayList<String> pila = new ArrayList<String>();
	public static ArrayList<Integer> pasos = new ArrayList<Integer>();
	public static String tok;
	public static int estado;
	public static BufferedWriter bufferEscrit;
	public static FileWriter error;
	public static BufferedWriter bufferErrores;
	public static boolean primeraVez;

	public static void inicializar_sentencia (/*FileWriter escrito*/) throws IOException{
		pila.clear();
		pila.add("$");
		pila.add("P");
		
		bufferErrores = Main.bufferErrores;
			
		
		primeraVez = false;
		
	}
	

	
	public static int recibir_token(String token) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		
		if(esNumerico(token))
			tok = "id";
		else {
		   tok = token;}
	
		if(tok.equals(""))
			return estado;
		paso();
		return estado;
	}
	
	static void P() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		
		pila.remove(pila.size() -1); 
		if(tok.equals("fun"))
		{
			
			pasos.add(2);

			pila.add("P");
			pila.add("F");

			paso();
		}
		else if(tok.equals("id")|| tok.equals("if")|| tok.equals("ret")|| tok.equals("while") || tok.equals("get") || tok.equals("while")|| tok.equals("let")|| tok.equals("put")) 
		{
			
			pasos.add(1);

			pila.add("P");
			pila.add("B");

			paso();
		}
		else if(tok.equals("EOF")) 
		{
		
			pasos.add(19);

			

			paso();
			
		}
		else 
		{
			gestionErrores(14, tok);
			return;
		}
	}
	
	static void F() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		if(tok.equals("fun"))
		{
		
			pasos.add(39);

			pila.add("}");
			pila.add("C");
			pila.add("{");
			pila.add(")");
			pila.add("A");
			pila.add("(");
			pila.add("H");
			pila.add("id");
			
			estado = 0;
			return;
		}
		else 
		{
			gestionErrores(3, tok);
			return;
		}
	}
	
	static void B() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		if(tok.equals("get")|| tok.equals("id") ||tok.equals("ret")||tok.equals("put"))
		{
			
			pasos.add(5);
			
			pila.add(";");
			pila.add("S");
			
			paso();
		}
		else if(tok.equals("if")) 
		{
			
			pasos.add(4);
			
			pila.add("B");
			pila.add(")");
			pila.add("E");
			pila.add("(");
			estado = 0;
			return;
			
		}
		else if(tok.equals("let")) 
		{
		
			pasos.add(6);
			
			pila.add(";");
			pila.add("T");
			pila.add("id");
			
			estado = 0;
			return;
		}
		else if(tok.equals("while")) 
		{
		
			pasos.add(7);
			
			pila.add("}");
			pila.add("C");
			pila.add("{");
			pila.add(")");
			pila.add("E");
			pila.add("(");

			estado = 0;
			return;
			
		}
		else 
		{
			gestionErrores(4, tok);
			return;
		}
	}
	
	static void W() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		if(tok.equals("%="))
		{
			
			pasos.add(8);
			pila.add("Y");
		
			paso();
		}
		else if(tok.equals("=")) 
		{
			
			pasos.add(10);
			estado = 0;
			return;
		}
		else 
		{
			gestionErrores(5, tok);
			return;
		}
	}
	static void Y() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		if(tok.equals("%="))
		{
			
			pasos.add(9);
			estado = 0;
			return;
		}
		else 
		{
			gestionErrores(5, tok);
			return;
		}
	}
	
	static void T() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		if(tok.equals("bool"))
		{
			
			pasos.add(12);
			estado = 0;
			return;
		}
		else if(tok.equals("int")) 
		{
			
			pasos.add(11);
			estado = 0;
			return;
		}
		else if(tok.equals("st")) 
		{
			
			pasos.add(13);
			estado = 0;
			return;
		}
		else 
		{
			gestionErrores(6, tok);
			return;
		}
	}
	static void E() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		if(tok.equals("!")||tok.equals("(")||tok.equals("cadena")||tok.equals("id")|| tok.equals("constint"))
		{
			
			pasos.add(14);
			pila.add("D");
			pila.add("M");
			paso();
		}
		else 
		{
			gestionErrores(7, tok);
			return;
		}
	}
	static void D() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		if(tok.equals(")")||tok.equals(";")||tok.equals(","))
		{
			
			pasos.add(16);
			
			paso();
		}
		else if(tok.equals("<")) 
		{
			
			pasos.add(15);

			pila.add("D");
			pila.add("M");
            
			estado = 0;
			return;
			
		}
		else 
		{
			gestionErrores(8, tok);
			return;
		}
	}
	static void M() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		if(tok.equals("!")||tok.equals("(")||tok.equals("cadena")||tok.equals("id") || tok.equals("constint"))
		{
		
			pasos.add(17);

			pila.add("G");
			pila.add("V");
		
			paso();
		}
		else 
		{
			gestionErrores(7, tok);
			return;
		}
	}
	
	
	static void G() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		if(tok.equals(")")||tok.equals(";")||tok.equals(",")||tok.equals("<"))
		{
			
			pasos.add(19);

		

			paso();
		}
		else if(tok.equals("%")) 
		{
			
			pasos.add(18);
			pila.add("G");
			pila.add("V");
			estado =0;
			return;
		}
		else 
		{
			gestionErrores(9, tok);
			return;
		}
	}
	
	
	static void V() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		if(tok.equals("!"))
		{
			
			pasos.add(23);

			pila.add("V");
			estado = 0;
			return;
		}
		else if(tok.equals("(")) 
		{
			
			pasos.add(22);

			pila.add(")");
			pila.add("E");
			estado = 0;
			return;
		}
		else if(tok.equals("cadena")) 
		{
			
			pasos.add(21);

			estado = 0;
			return;
		}
		else if(tok.equals("id")) 
		{
			
			pasos.add(20);

			pila.add("J");
			estado = 0;
			return;
		}
		else if(tok.equals("constint")) 
		{
			
			pasos.add(24);

			estado = 0;
			return;
		}
		else 
		{
			gestionErrores(7, tok);
			return;
		}
	}
	
	
	static void J() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		if(tok.equals("%")||tok.equals(")")||tok.equals(",")||tok.equals(";")||tok.equals("<"))
		{
			
			pasos.add(26);
			//pila.add("lamda");
			paso();
		}
		else if(tok.equals("(")) 
		{
			
			pasos.add(25);

			pila.add(")");
			pila.add("L");
			estado = 0;
			return;
		}
		else 
		{
			gestionErrores(10, tok);
			return;
		}
	}
	
	static void L() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		if(tok.equals("!")||tok.equals("(")||tok.equals("cadena")||tok.equals("id") || tok.equals("constint"))
		{
			
			pasos.add(27);

			pila.add("Q");
			pila.add("E");

			paso();
		}
		else if(tok.equals(")")) 
		{
			
			pasos.add(28);
			
		

			paso();
		}
		else 
		{
			gestionErrores(11, tok);
			return;
		}
	}
	
	static void Q() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		if(tok.equals(")"))
		{
			
			pasos.add(30);

		
			
			paso();
		}
		else if(tok.equals(",")) 
		{
			
			pasos.add(29);

			pila.add("Q");
			pila.add("E");
			estado = 0;
			return;
		}
		else 
		{
			gestionErrores(12, tok);
			return;
		}
	}
	
	
	static void S() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		if(tok.equals("get"))
		{
			
			pasos.add(34);

			pila.add("id");
			estado=0;
			return;
		}
		else if(tok.equals("id")) 
		{
			
			pasos.add(31);
			
			pila.add("N");
			estado = 0;
			return;
		}
		else if(tok.equals("put")) 
		{
		
			pasos.add(33);

			pila.add("E");
			estado = 0;
			return;
		}
		else if(tok.equals("ret")) 
		{
		
			pasos.add(32);

			pila.add("X");
			estado = 0;
			return;
		}
		else 
		{
			gestionErrores(13, tok);
			return;
		}
	}
	
	
	static void N() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		if(tok.equals("%=")||tok.equals("="))
		{
			
			pasos.add(35);

			pila.add("E");
			pila.add("W");

			paso();
		}
		else if(tok.equals("(")) 
		{
			
			pasos.add(36);

			pila.add(")");
			pila.add("L");
			estado = 0;
			return;
		}
		else 
		{
			gestionErrores(15, tok);
			return;
		}
	}
	
	static void X() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		if(tok.equals("!")||tok.equals("(")||tok.equals("cadena")||tok.equals("id") || tok.equals("constint"))
		{
			
			pasos.add(37);

			pila.add("E");
	
			paso();
		}
		else if(tok.equals(";")) 
		{
			
			pasos.add(38);


			paso();
		}
		else 
		{
			gestionErrores(16, tok);
			return;
		}
	}
	
	

	static void A() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		if(tok.equals("bool")||tok.equals("int") ||tok.equals("st")) 
		{
			
			pasos.add(40);

			pila.add("K");
			pila.add("id");
			pila.add("T");

			paso();
		}
		else if(tok.equals("void")) 
		{
			
			pasos.add(41);

			estado = 0;
			return;
		}
		else 
		{
			gestionErrores(6, tok);
			return;
		}
	}
	
	
	
	static void K() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		if(tok.equals(")"))
		{
			
			pasos.add(43);
			System.out.print("SEDEBERIAMETERAQUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");

			// pila.add("lambda");
	
			paso();
		}
		else if(tok.equals(",")) 
		{
			
			pasos.add(42);
			
			pila.add("K");
			pila.add("id");
			pila.add("T");
			estado=0;
		}
		else 
		{
			gestionErrores(12, tok);
			return;
		}
	}
	
	
	static void C() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		if(tok.equals("get")||tok.equals("id")||tok.equals("if")||tok.equals("let")||tok.equals("put")||tok.equals("ret")||tok.equals("while"))
		{
			
			pasos.add(44);

			pila.add("C");
			pila.add("B");
	
			paso();
		}
		else if(tok.equals("}")) 
		{
			
			pasos.add(45);

			

			paso();
		}
		else 
		{
			gestionErrores(17, tok);
			return;
		}
	}
	
	static void H() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		if(tok.equals("bool")||tok.equals("int") ||tok.equals("st")) 
		{
			
			pasos.add(46);

			pila.add("T");
			paso();
		}else if(tok.equals("void")) 
		{
			
			pasos.add(47);
			
			estado = 0;
			return;
		}
		else 
		{
			gestionErrores(6, tok);
			return;
		}
	}
	
	
	static void paso() throws NoSuchMethodException, SecurityException // llama al void de la cabeza de la pila
, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException
	{
		String c = pila.get(pila.size() - 1);
		System.out.print("\n"+"pila del sintactico "+ c +"\n");
		
		

		 if (c.equals(tok)){
			pila.remove(pila.size() -1);
			estado = 0;
			return;
		 }
		 else if(c.equals("$")) 
		 {
			 fin();
		 }
		 else {
			try {
				Method metodo = sintactico.class.getDeclaredMethod(c);
				
				metodo.invoke(null);	
			} catch (NoSuchMethodException e) {
				gestionErrores(2, tok + " " +c);
			    return;
			} 
		 }
	}

	public static void gestionErrores(int tipo, String token) throws IOException 
	{	
		
		estado = 1;
		if(!primeraVez) {
		   bufferErrores.write("Errores sintacticos: \n");
		   primeraVez = true;
		}
		 bufferErrores.write("\n");
		switch (tipo){
			case 1:
				bufferErrores.write("Error en el sintactico: no existe dicha produccion. Linea " + Main.nlinea +"\n");
				 bufferErrores.write("\n");
				break;
			case 2:
				String[] partes = token.split(" ");
				bufferErrores.write("Error en el sintactico: se ha encontrado un terminal " + partes[0].toString() + " que no coincide con el esperado (" + partes[1].toString() +"). Linea "+Main.nlinea+"\n");
				bufferErrores.write("\n");
				break;
			case 3: // declaracion de funciones
				bufferErrores.write("Error en el sintactico: se esperaba \"function\" en vez de "+ token + ". Linea "+Main.nlinea+"\n");
				bufferErrores.write("\n");
				break;
			case 4: // B
				bufferErrores.write("Error en el sintactico: se esperaba \"if\", \"let\", \"while\", \"put\", \"get\", \"return\" o un " +
				"identificador en vez de "+ token + ". Linea "+Main.nlinea+"\n");
				bufferErrores.write("\n");
				break;
			case 5: // Y, W			
				bufferErrores.write("Error en el sintactico: se esperaba un operador de asignacion en vez de " + token + ". Linea "+Main.nlinea+"\n");
				bufferErrores.write("\n");
				break;
			case 6: // T, A, H
				bufferErrores.write("Error en el sintactico: tipo \"" + token + "\"no valido. Linea "+Main.nlinea+"\n");
				bufferErrores.write("\n");
				break;
			case 7: // E, M, V
				bufferErrores.write("Error en el sintactico: se esperaba \"!\", \"(\", un identificador, un numero entero o una cadena " +
				"en vez de "+ token + ". Linea "+Main.nlinea+"\n");
				bufferErrores.write("\n");
				break;
			case 8: // D
				bufferErrores.write("Error en el sintactico: se esperaba el operador relacional \"<\" o la finalizacion de la sentencia con \")\", \",\" o \";\" " +
				"en vez de "+ token + ". Linea "+Main.nlinea+"\n");
				bufferErrores.write("\n");
				break;
			case 9: // G
				bufferErrores.write("Error en el sintactico: se esperaba el operador relacional \"<\", el operador arimetico \"%\" " + 
				"o la finalizacion de la sentencia con \")\", \",\" o \";\" en vez de "+ token + ". Linea "+Main.nlinea+"\n");
				bufferErrores.write("\n");
				break;
			case 10: // J
				bufferErrores.write("Error en el sintactico: se esperaba el operador relacional \"<\", el operador arimetico \"%\" " + 
				", la finalizacion de la sentencia con \")\", \",\", \";\" o \"(\" en vez de "+ token + ". Linea "+Main.nlinea+"\n");
				bufferErrores.write("\n");
				break;
			case 11: // L
				bufferErrores.write("Error en el sintactico: se esperaba \"!\", \"(\", un identificador, un numero entero, una cadena " +
				"o \")\" en vez de "+ token + ". Linea "+Main.nlinea+"\n");
				bufferErrores.write("\n");
				break;
			case 12: // Q, K
				bufferErrores.write("Error en el sintactico: se esperaba \")\" o \",\" en vez de "+ token + ". Linea "+Main.nlinea+"\n");
				bufferErrores.write("\n");
				break;
			case 13: // S
				bufferErrores.write("Error en el sintactico: se esperaba \"put\", \"get\", \"return\" o un " +
				"identificador en vez de "+ token + ". Linea "+ Main.nlinea +"\n");
				bufferErrores.write("\n");
			case 14: // P
				bufferErrores.write("Error en el sintactico: se esperaba \"if\", \"let\", \"while\", \"put\", \"get\", \"return\", \"function\", un " +
				"identificador o el fin de fichero en vez de "+ token + ". Linea "+Main.nlinea+"\n");
				bufferErrores.write("\n");
				break;
			case 15: // N			
				bufferErrores.write("Error en el sintactico: se esperaba un operador de asignacion o \")\" " +
				"en vez de " + token + ". Linea "+Main.nlinea+"\n");
				bufferErrores.write("\n");
				break;
			case 16: // L, X
				bufferErrores.write("Error en el sintactico: se esperaba \"!\", \"(\", un identificador, un numero entero, una cadena " +
				"o \";\" en vez de "+ token + ". Linea "+Main.nlinea+"\n");
				bufferErrores.write("\n");
				break;
			case 17: // C
				bufferErrores.write("Error en el sintactico: se esperaba \"if\", \"let\", \"while\", \"put\", \"get\", \"return\", un " +
				"identificador o un cierre de bloque \"}\" en vez de "+ token + ". Linea "+Main.nlinea+"\n");
				bufferErrores.write("\n");
				break;
			default: // case 18
				bufferErrores.write("Error en el sintactico: finalizacion incorrecta. Linea "+Main.nlinea+"\n");
				bufferErrores.write("\n");
				break;
		}

		System.out.print("Error sintactico");
		return;
	}	

	public static void fin() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException 
	{
		String c = pila.get(pila.size() -1);
		System.out.print("El ultimo valor es " + c);
		if(!c.equals("$")) {
			gestionErrores(18, null);
		} else{
			FileWriter escrit = new FileWriter("parser"); // annadimos una extension de archivo .txt?
			bufferEscrit = new BufferedWriter(escrit);
			bufferEscrit.write("Descendente ");
			pasos.add(3);
			for(int i = 0; i<pasos.size();i++) 
			{
				if (i != pasos.size() - 2){
					bufferEscrit.write(pasos.get(i).toString() + " ");
				}
			}
			bufferEscrit.close();
		}
	}
	
	 public static boolean esNumerico(String str) {
		 return str.matches("-?\\d+");}
}
