


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map.Entry;



// TO-DO revisar los pops de cada funcion ya que no estan bien puestos, deben de tener el numero de elementos que se meten en la pila aux, que son los 
// que se meten directamente en esa pila mas los que se meten en la otra, ademas de poner el resto de pops

public class semantico {
	
	public static ArrayList<entrada> pil_aux = new ArrayList<entrada>();
	public static ArrayList<entrada> pila = new ArrayList<entrada>();
	public static ArrayList<Integer> pasos = new ArrayList<Integer>();
	public static String tok;
	public static int estado;
	public static BufferedWriter bufferEscrit;
	public static boolean zona_declaracion;
	public static int indice = 1;
	public static int desplTS;
	public static int desplAct;
	public static int ntope = 0;
	public static TablaSimbolos auxiliar;
	public static TablaSimbolos local;
	public static int posToken;
	public static boolean zon_fun;
	public static int funcion_id;
	public static ArrayList<String> parametros;
	public static ArrayList<String> parametros_aux;
	public static ArrayList<String> param;
	public static int indice_atributos;
	public static String retfun;
	public static int indice_atributos_funcion;
	public static boolean idFun;
	public static int fun_aux_id;
	public static int fun_aux_pos;
	public static boolean sinArg;
	public static int indiceFun;
	public static boolean rt;
	public static boolean esperaRet;
	public static String tipRet;
	public static boolean asignacion;
	public static boolean dentroFun;
	public static boolean eunafun;
	public static int postok2;
	public static BufferedWriter bufferErrores;
	public static boolean declImpl;
	public static int idddd;
	
	
	public static void CrearTs() 
	{
		auxiliar = local;
		indice++;
		local = new TablaSimbolos(indice);
		Main.tabla = local;
	}

	public static void CerrarTs() 
	{
		local.volcarFichero(local, Main.impresa);
		Main.impresa = true;
		local = auxiliar;
		Main.tabla = local;
	}
	
	public static TablaSimbolos Inicializar() 
	{
		pila.clear();
		pila.add(new entrada("$", "-","-"));
		pila.add(new entrada("P", "-","-"));
		//pila.add(new entrada("fun0", "-"));
		estado = 0;
		zona_declaracion = false;
		zon_fun = false;
		desplTS = 0;
		indice_atributos = 0;
		local = new TablaSimbolos(indice);
		parametros = new ArrayList<String>();
		param= new ArrayList<String>();
		idFun = false;
		indiceFun = 0;
		rt = false;
		esperaRet = false;
		eunafun=false;
		bufferErrores = Main.bufferErrores;
		declImpl = false;
		return local;
		
	}
	
	public static int recibirToken(String token) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException 
	{
		if(esNumerico(token)) {
		posToken = Integer.parseInt(token);
		
			tok = "id";
			}
		else
		  tok = token;
		
		
		if(tok.equals(""))
			return estado;
		paso();
		return estado;
	}
	
	
	static void P() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		
		pil_aux.add(pila.get(pila.size() - 1));
		ntope ++;
		pila.remove(pila.size() -1); // MARTA: igual lo podria quitar el propio paso
		if(tok.equals("fun"))
		{
			
			pasos.add(2);

			pila.add(new entrada("Pop1","-","-"));
			pila.add(new entrada("P", "-","-"));
			pila.add(new entrada("F", "-","-"));

			paso();
		}
		else if(tok.equals("id")|| tok.equals("if")|| tok.equals("ret")|| tok.equals("while") || tok.equals("get") || tok.equals("while")|| tok.equals("let")|| tok.equals("put")) 
		{
			
			pasos.add(1);
			
			pila.add(new entrada("Pop1","-","-"));
			pila.add(new entrada("P", "-","-"));
			pila.add(new entrada("B", "-","-"));

			paso();
		}
		else if(tok.equals("EOF")) 
		{
			
			pasos.add(19);
			paso();
			
		}
		else 
		{
			gestionErrores(1);
			return;
		}
	}
	
	static void F() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pil_aux.add(pila.get(pila.size() - 1));
		ntope ++;
		pila.remove(pila.size() -1);
		if(tok.equals("fun"))
		{
			
			
			pasos.add(39);
			ntope++;
			pil_aux.add(new entrada("fun","-","-"));
			pila.add(new entrada("Pop9","-","-"));
		
			pila.add(new entrada("}", "-", "-"));
			pila.add(new entrada("fun0396", "-", "-"));
			pila.add(new entrada("fun0395", "-", "-"));
			pila.add(new entrada("C", "-", "-"));
			pila.add(new entrada("{", "-", "-"));
			pila.add(new entrada("fun0394", "-", "-"));
			pila.add(new entrada(")", "-", "-"));
			pila.add(new entrada("fun0393", "-", "-"));
			pila.add(new entrada("A", "-", "-"));
			pila.add(new entrada("(", "-", "-"));
			pila.add(new entrada("fun0392",  "-", "-"));
			pila.add(new entrada("H", "-", "-"));
			pila.add(new entrada("id", "-", "-"));
			pila.add(new entrada("fun0391", "-", "-"));
			
			
			estado = 0;
			return;
		}
		else 
		{
			gestionErrores(1);
			return;
		}
	}
	
	static void B() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pil_aux.add(pila.get(pila.size() - 1));
		ntope ++;
		pila.remove(pila.size() -1);
		if(tok.equals("get")|| tok.equals("id") ||tok.equals("ret")||tok.equals("put"))
		{
			
			pasos.add(5);
			pila.add(new entrada("Pop2","-","-"));
			pila.add(new entrada(";", "-", "-"));
			pila.add(new entrada("fun05", "-", "-"));
			pila.add(new entrada("S", "-", "-"));
			
			paso();
		}
		else if(tok.equals("if")) 
		{

			pil_aux.add(new entrada("if","-","-"));
			
			pasos.add(4);
			ntope++;
			pila.add(new entrada("Pop5","-","-"));
			pila.add(new entrada("fun04", "-", "-"));
			pila.add(new entrada("B", "-", "-"));
			pila.add(new entrada(")", "-", "-"));
			pila.add(new entrada("E", "-", "-"));
			pila.add(new entrada("(", "-", "-"));
			
			estado = 0;
			return;
			
		}
		else if(tok.equals("let")) 
		{
			
			pasos.add(6);

			pil_aux.add(new entrada("let","-","-"));
			ntope++;
			
			pila.add(new entrada("Pop4","-","-"));
			
			pila.add(new entrada(";", "-", "-"));
			pila.add(new entrada("fun062", "-", "-"));
			pila.add(new entrada("T", "-", "-"));
			pila.add(new entrada("id", "-", "-"));
			pila.add(new entrada("fun061", "-", "-"));
			
			estado = 0;
			return;
		}
		else if(tok.equals("while")) 
		{
			
			pasos.add(7);

			pil_aux.add(new entrada("while","-","-"));
			ntope++;
			//ntope++;
			
			pila.add(new entrada("Pop7","-","-"));
			pila.add(new entrada("fun07", "-", "-"));
			pila.add(new entrada("}", "-", "-"));
			pila.add(new entrada("C", "-", "-"));
			pila.add(new entrada("{", "-", "-"));
			pila.add(new entrada(")", "-", "-"));
			pila.add(new entrada("E", "-", "-"));
			pila.add(new entrada("(", "-", "-"));
			
			estado = 0;
			return;
			
		}
		else 
		{
			gestionErrores(1);
			return;
		}
	}
	
	static void W() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pil_aux.add(pila.get(pila.size() - 1));
		ntope ++;
		pila.remove(pila.size() -1);
		if(tok.equals("%="))
		{
			asignacion = false;
			
			pasos.add(8);
			pila.add(new entrada("Pop1","-","-"));
			pila.add(new entrada("Y", "-", "-"));
			paso();
		}
		else if(tok.equals("=")) 
		{
			asignacion = true;
			pila.add(new entrada("Pop1","-","-"));
			pil_aux.add(new entrada("=","-","-"));
			ntope++;
			pasos.add(10);
			estado = 0;
			return;
		}
		else 
		{
			gestionErrores(1);
			return;
		}
	}
	static void Y() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pil_aux.add(pila.get(pila.size() - 1));
		ntope ++;
		pila.remove(pila.size() -1);
		if(tok.equals("%="))
		{

			pila.add(new entrada("Pop1","-","-"));
			pil_aux.add(new entrada("%=","-","-"));
			ntope++;
			pasos.add(9);
			estado = 0;
			return;
		}
		else 
		{
			gestionErrores(1);
			return;
		}
	}
	
	static void T() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pil_aux.add(pila.get(pila.size() - 1));
		ntope ++;
		pila.remove(pila.size() -1);
		if(tok.equals("bool"))
		{

			pil_aux.add(new entrada("bool","-","-"));
			ntope ++;
			pasos.add(12);
			pila.add(new entrada("Pop1","-","-"));
			pila.add(new entrada("fun012", "-", "-"));
			estado = 0;
			
			return;
		}
		else if(tok.equals("int")) 
		{
			ntope++;
			pasos.add(11);
			pila.add(new entrada("Pop1","-","-"));
			pil_aux.add(new entrada("int","-","-"));
			pila.add(new entrada("fun011", "-", "-"));
			
			estado = 0;
			
			return;
		}else if(tok.equals("st")) 
		{

			pil_aux.add(new entrada("st","-","-"));
			ntope++;
			pasos.add(13);
			pila.add(new entrada("Pop1","-","-"));
			pila.add(new entrada("fun013", "-", "-"));
			estado = 0;
			return;
		}
		else 
		{
			gestionErrores(1);
			return;
		}
		
		
		
	}
	static void E() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pil_aux.add(pila.get(pila.size() - 1));
		ntope ++;
		pila.remove(pila.size() -1);
		if(tok.equals("!")||tok.equals("(")||tok.equals("cadena")||tok.equals("id")|| tok.equals("constint"))
		{
			pasos.add(14);

			pila.add(new entrada("Pop2","-","-"));
			pila.add(new entrada("fun014", "-","-"));
			pila.add(new entrada("D", "-", "-"));
			pila.add(new entrada("M", "-", "-"));
			
			//pila.add("V");
			//pila.add("G");
			paso();
		}
		else 
		{
			gestionErrores(1);
			return;
		}
	}
	static void D() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		
		pil_aux.add(pila.get(pila.size() - 1));
		ntope ++;
		pila.remove(pila.size() -1);
		if(tok.equals(")")||tok.equals(";")||tok.equals(","))
		{
			System.out.print(16);
			pasos.add(16);
			//pila.add("lamda");
			pila.add(new entrada("fun016", "-", "-"));
			paso();
		}else if(tok.equals("<")) 
		{
			System.out.print(15);
			pasos.add(15);

			pila.add(new entrada("Pop3","-","-"));
			pil_aux.add(new entrada("<","-","-"));
			ntope++;
			pila.add(new entrada("fun015", "-", "-"));
			pila.add(new entrada("D", "-", "-"));
			pila.add(new entrada("M", "-", "-"));
			
			estado = 0;
			return;
			
		}
		else 
		{
			
			gestionErrores(1);
			return;
		}
	}
	static void M() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pil_aux.add(pila.get(pila.size() - 1));
		ntope ++;
		pila.remove(pila.size() -1);
		if(tok.equals("!")||tok.equals("(")||tok.equals("cadena")||tok.equals("id") || tok.equals("constint"))
		{
			System.out.print(17);
			pasos.add(17);
			pila.add(new entrada("Pop2","-","-"));
			pila.add(new entrada("fun017", "-", "-"));
			pila.add(new entrada("G", "-", "-"));
			pila.add(new entrada("V", "-", "-"));
			
			
		
			paso();
		}
		else 
		{
			gestionErrores(1);
			return;
		}
	}
	
	
	static void G() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pil_aux.add(pila.get(pila.size() - 1));
		ntope ++;
		pila.remove(pila.size() -1);
		if(tok.equals(")")||tok.equals(";")||tok.equals(",")||tok.equals("<"))
		{
			System.out.print(19);
			pasos.add(19);

			// pila.add("lamda");
			pila.add(new entrada("fun019", "-", "-"));

			paso();
		}else if(tok.equals("%")) 
		{
			pasos.add(18);
			pila.add(new entrada("Pop3","-","-"));
			pil_aux.add(new entrada("%","-","-"));
			ntope++;
			pila.add(new entrada("fun018", "-", "-"));
			pila.add(new entrada("G", "-", "-"));
			pila.add(new entrada("V", "-", "-"));
			
			estado =0;
			return;
		}
		else 
		{
			gestionErrores(1);
			return;
		}
	}
	
	
	static void V() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pil_aux.add(pila.get(pila.size() - 1));
		ntope ++;
		pila.remove(pila.size() -1);
		if(tok.equals("!"))
		{

			pil_aux.add(new entrada("!","-","-"));
			ntope++;
			System.out.print(23);
			pasos.add(23);
			pila.add(new entrada("Pop2","-","-"));
			pila.add(new entrada("fun023", "-", "-"));
			pila.add(new entrada("V", "-", "-"));
			
			estado = 0;
			return;
		}
		else if(tok.equals("(")) 
		{
			System.out.print(22);
			pasos.add(22);
			pila.add(new entrada("Pop3","-","-"));
			pil_aux.add(new entrada("(","-","-"));
			pila.add(new entrada("fun022", "-", "-"));
			ntope++;
			pila.add(new entrada(")", "-", "-"));
			pila.add(new entrada("E", "-", "-"));
			
			estado = 0;
			return;
		}else if(tok.equals("cadena")) 
		{
			pila.add(new entrada("Pop1","-","-"));
			pil_aux.add(new entrada("cadena","-","-"));
			System.out.print(21);
			ntope++;
			pasos.add(21);
			pila.add(new entrada("fun021", "-", "-"));
			estado = 0;
			return;
		}else if(tok.equals("id")) 
		{
			pila.add(new entrada("Pop2","-","-"));
			pil_aux.add(new entrada("id","-","-"));
			System.out.print(20);
			ntope++;
			pasos.add(20);
			pila.add(new entrada("J", "-", "-"));
			pila.add(new entrada("fun020", "-", "-"));
			estado = 0;
			return;
		}else if(tok.equals("constint")) 
		{
			
			pila.add(new entrada("Pop1","-","-"));
			pil_aux.add(new entrada("constint","-","-"));
			ntope++;
			System.out.print(24);
			pasos.add(24);
			pila.add(new entrada("fun024", "-", "-"));
			estado = 0;
			return;
		}
		else 
		{
			gestionErrores(1);
			return;
		}
	}
	
	
	static void J() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pil_aux.add(pila.get(pila.size() - 1));
		ntope ++;
		pila.remove(pila.size() -1);
		if(tok.equals("%")||tok.equals(")")||tok.equals(",")||tok.equals(";")||tok.equals("<"))
		{
			System.out.print(26);
			pasos.add(26);
			//pila.add("lamda");
			pila.add(new entrada("fun026", "-", "-"));
			paso();
		}else if(tok.equals("(")) 
		{
			System.out.print(25);
			pasos.add(25);
			pila.add(new entrada("Pop3","-","-"));
			ntope++;
			pil_aux.add(new entrada("(","-","-"));
			pila.add(new entrada("fun025", "-", "-"));
			pila.add(new entrada(")", "-", "-"));
			pila.add(new entrada("L", "-", "-"));
			estado = 0;
			return;
		}
		else 
		{
			gestionErrores(1);
			return;
		}
	}
	
	static void L() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pil_aux.add(pila.get(pila.size() - 1));
		ntope ++;
		pila.remove(pila.size() -1);
		if(tok.equals("!")||tok.equals("(")||tok.equals("cadena")||tok.equals("id") || tok.equals("constint"))
		{
			System.out.print(27);
			pasos.add(27);
			pila.add(new entrada("Pop2","-","-"));
			pila.add(new entrada("fun027", "-", "-"));
			pila.add(new entrada("Q", "-", "-"));
			pila.add(new entrada("E", "-", "-"));
			sinArg = false;
			paso();
		}else if(tok.equals(")")) 
		{
			System.out.print(28);
			pasos.add(28);
			pila.add(new entrada("fun028", "-", "-"));
			//pila.add("lamda");
			sinArg = true;
			paso();
		}
		else 
		{
			gestionErrores(1);
			return;
		}
	}
	
	static void Q() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pil_aux.add(pila.get(pila.size() - 1));
		ntope ++;
		pila.remove(pila.size() -1);
		if(tok.equals(")"))
		{
			System.out.print(30);
			pasos.add(30);
			pila.add(new entrada("fun030", "-", "-"));

			//pila.add("lamda");
			
			paso();
		}else if(tok.equals(",")) 
		{
			System.out.print(29);
			pasos.add(29);
			ntope++;
			pil_aux.add(new entrada(",","-","-"));
			pila.add(new entrada("Pop3","-","-"));
			pila.add(new entrada("fun029", "-", "-"));
			pila.add(new entrada("Q", "-", "-"));
			pila.add(new entrada("E", "-", "-"));
			estado = 0;
			return;
		}
		else 
		{
			gestionErrores(1);
			return;
		}
	}
	
	
	static void S() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pil_aux.add(pila.get(pila.size() - 1));
		ntope ++;
		pila.remove(pila.size() -1);
		if(tok.equals("get"))
		{
			System.out.print(34);
			pasos.add(34);
			ntope++;
			pila.add(new entrada("Pop2","-","-"));
			pil_aux.add(new entrada("get","-","-"));
			pila.add(new entrada("fun034", "-", "-"));
			pila.add(new entrada("id", "-", "-"));
			estado=0;
			return;
		
			
		}
		else if(tok.equals("id")) 
		{
			System.out.print(31);
			pasos.add(31);
			ntope++;
			pila.add(new entrada("Pop2","-","-"));
			pil_aux.add(new entrada("id","-","-"));
			pila.add(new entrada("fun0311", "-", "-"));
			pila.add(new entrada("N", "-", "-"));
			pila.add(new entrada("fun0312","-","-"));
			estado = 0;
			return;
		}else if(tok.equals("put")) 
		{
			System.out.print(33);
			pasos.add(33);
			ntope++;
			
			pila.add(new entrada("Pop2","-","-"));
			pil_aux.add(new entrada("put","-","-"));
			pila.add(new entrada("fun033", "-", "-"));
			pila.add(new entrada("E", "-", "-"));
			estado = 0;
			return;
		}else if(tok.equals("ret")) 
		{
			System.out.print(32);
			pasos.add(32);
			ntope++;
			pila.add(new entrada("Pop2","-","-"));
			pil_aux.add(new entrada("ret","-","-"));
			pila.add(new entrada("fun032", "-", "-"));
			pila.add(new entrada("X", "-", "-"));
			estado = 0;
			rt = true;
			return;
		}
		else 
		{
			gestionErrores(1);
			return;
		}
	}
	
	
	static void N() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pil_aux.add(pila.get(pila.size() - 1));
		ntope ++;
		pila.remove(pila.size() -1);
		if(tok.equals("%=")||tok.equals("="))
		{
			System.out.print(35);
			pasos.add(35);
			pila.add(new entrada("Pop2","-","-"));
			pila.add(new entrada("fun035", "-", "-"));
			pila.add(new entrada("E", "-", "-"));
			pila.add(new entrada("W", "-", "-"));

			paso();
		}else if(tok.equals("(")) 
		{
			System.out.print(36);
			pasos.add(36);
			ntope++;
			pila.add(new entrada("Pop3","-","-"));
			pil_aux.add(new entrada("(","-","-"));
			pila.add(new entrada("fun036", "-", "-"));
			pila.add(new entrada(")", "-", "-"));
			pila.add(new entrada("L", "-", "-"));
			estado = 0;
			return;
		}
		else 
		{
			gestionErrores(1);
			return;
		}
	}
	
	static void X() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pil_aux.add(pila.get(pila.size() - 1));
		ntope ++;
		pila.remove(pila.size() -1);
		if(tok.equals("!")||tok.equals("(")||tok.equals("cadena")||tok.equals("id") || tok.equals("constint"))
		{
			System.out.print(37);
			pasos.add(37);
			pila.add(new entrada("Pop1","-","-"));
			pila.add(new entrada("fun037", "-", "-"));
			pila.add(new entrada("E", "-", "-"));
	
			paso();
		}else if(tok.equals(";")) 
		{
			System.out.print(38);
			pasos.add(38);

			// pila.add("lamda");
			pila.add(new entrada("fun038", "-", "-"));

			paso();
		}
		else 
		{
			gestionErrores(1);
			return;
		}
	}
	
	

	static void A() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pil_aux.add(pila.get(pila.size() - 1));
		ntope ++;
		pila.remove(pila.size() -1);
		if(tok.equals("bool")||tok.equals("int") ||tok.equals("st")) 
		{
			System.out.print(40);
			pasos.add(40);
			pila.add(new entrada("Pop3","-","-"));
			pila.add(new entrada("fun0402", "-", "-"));
			pila.add(new entrada("K", "-", "-"));
			pila.add(new entrada("fun0401", "-", "-"));
			pila.add(new entrada("id", "-", "-"));
			pila.add(new entrada("T", "-", "-"));

			paso();
		}else if(tok.equals("void")) 
		{
			System.out.print(41);
			pasos.add(41);
			ntope++;
			pila.add(new entrada("Pop1","-","-"));
			pil_aux.add(new entrada("void","-","-"));
			pila.add(new entrada("fun041", "-", "-"));
			estado = 0;
			return;
		}
		
		else 
		{
			gestionErrores(1);
			return;
		}
	}
	
	
	
	static void K() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pil_aux.add(pila.get(pila.size() - 1));
		ntope ++;
		pila.remove(pila.size() -1);
		if(tok.equals(")"))
		{
			System.out.print(43);
			pasos.add(43);
			pila.add(new entrada("fun044", "-", "-"));
			// pila.add("lambda");
	
			paso();
		}else if(tok.equals(",")) 
		{
			System.out.print(42);
			pasos.add(42);
			ntope++;
			pila.add(new entrada("Pop4","-","-"));
			pil_aux.add(new entrada(",","-","-"));
			pila.add(new entrada("K", "-", "-"));
			pila.add(new entrada("fun043", "-", "-"));
			pila.add(new entrada("id", "-", "-"));
			pila.add(new entrada("T", "-", "-"));
			estado = 0;
			return;
		}
		else 
		{
			gestionErrores(1);
			return;
		}
	}
	
	
	static void C() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pil_aux.add(pila.get(pila.size() - 1));
		ntope ++;
		pila.remove(pila.size() -1);
		if(tok.equals("get")||tok.equals("id")||tok.equals("if")||tok.equals("let")||tok.equals("put")||tok.equals("ret")||tok.equals("while"))
		{
			System.out.print(44);
			pasos.add(44);
			pila.add(new entrada("Pop2","-","-"));
			pila.add(new entrada("fun045", "-", "-"));
			pila.add(new entrada("C", "-", "-"));
			pila.add(new entrada("B", "-", "-"));
			
			paso();
		}else if(tok.equals("}")) 
		{
			System.out.print(45);
			pasos.add(45);
			
			pila.add(new entrada("fun046", "-", "-"));
			// pila.add("lambda");

			paso();
		}
		else 
		{
			gestionErrores(1);
			return;
		}
	}
	
	static void H() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pil_aux.add(pila.get(pila.size() - 1));
		ntope ++;
		pila.remove(pila.size() -1);
		if(tok.equals("bool")||tok.equals("int") ||tok.equals("st")) 
		{
			System.out.print(46);
			pasos.add(46);
			pila.add(new entrada("Pop1","-","-"));
			pila.add(new entrada("fun047", "-", "-"));
			pila.add(new entrada("T", "-", "-"));
			System.out.print("se mete aqui guarro");
			paso();
		}else if(tok.equals("void")) 
		{
			pila.add(new entrada("Pop1","-","-"));
			pil_aux.add(new entrada("void","-","-"));
			ntope++;
			System.out.print(47);
			pasos.add(47);
			pila.add(new entrada("fun048", "-", "-"));
			estado = 0;
			return;
		}
		else 
		{
			gestionErrores(1);
			return;
		}
	}
	
	static void paso() throws NoSuchMethodException, SecurityException // llama al void de la cabeza de la pila
, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException
	{
		entrada c = pila.get(pila.size() - 1);
		System.out.print("\n"+ "pila del semantico " + c.tok +"\n");
		
		 // MARTA. DONE: comprobar que el metodo no sea el propio token; si lo es, y no es el dolar, se pide el siguiente token
		 // si es el dolar, se finaliza el sintactico

		 // DONE: comprobar que si es terminal y no es el token que nos pasa

		
	
		 if (c.tok.equals(tok)){ // primera condicion de salida
			 pil_aux.add(pila.get(pila.size() - 1));
			 ntope ++;
			 pila.remove(pila.size() -1);
			estado = 0;
			return;
		 }
		 else if(c.tok.equals("$")) 
		 {
			 fin();
		 }
		 else {
			try {
				
				Method metodo = semantico.class.getDeclaredMethod(c.tok);
				// si da null o si da error, llamamos a gestionErrores
				metodo.invoke(null);	// MARTA: entiendo que cada metodo es cada simbolo no terminal? Tipo, H, F, P y demas?
			} catch (NoSuchMethodException e) {
				gestionErrores(2);
			    return;
			} 
		 }
	}

	public static void gestionErrores(int tipo) throws IOException // TODO: gestion de errores
	{	
		// tipos de errores: 1) si no hay produccion, 2) si se encuentra un terminal no esperado, 3) si no hay $ al final de la sentencia
		estado = 1;

		switch (tipo){
			case 1:
				System.out.print ("Error: no existe dicha produccionnnnnnn\n");
				// bufferErrores.write("Error: la cadena introducida supera el límite de 64 caracteres en la linea " + nlinea);
				break;
			case 2:
				System.out.print ("Error: se ha encontrado un terminal no esperadooooo enel semanticoooo\n");
				// bufferErrores.write("Error: la cadena introducida supera el límite de 64 caracteres en la linea " + nlinea);
				break;
			case 3:
				System.out.print("Error: Variable declarada previamente con otro tipo \n");
			default: // case 3:
				System.out.print ("Error: finalizacion incorrectaaaaaaa\n");
				// bufferErrores.write("Error: la cadena introducida supera el límite de 64 caracteres en la linea " + nlinea);
				break;
		}

		System.out.print("Error semantico ojo");
		return;
	}	
	
	public static void fun04() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		// B -> if ( E ) B’{B.tipo:= if E.tipo = bool then B’.tipo else tipo_error;  
		// B.tipoderetorno := B’.tipoderetorno} 5 SINTETIZADO  
		// B -> if ( E ) B’ {4}   
		pila.remove(pila.size() -1);
		
		
		if(!pil_aux.get(ntope-2).tip.equals("bool") && !pil_aux.get(ntope-3).tip.equals("bool") )
			GestorErrores(6,0,null,null);
		if (pil_aux.get(ntope-3).tip.equals("bool")){
			pil_aux.get(ntope-6).tip = pil_aux.get(ntope-1).tip;
		} else {
			pil_aux.get(ntope-6).tip = "tipo_error";
		}

		pil_aux.get(ntope-6).ret= pil_aux.get(ntope-1).ret;

		paso();
	}
	
	public static void fun05() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		
		
		
		pil_aux.get(ntope-3).tip= pil_aux.get(ntope-2).tip;
		pil_aux.get(ntope-3).ret= pil_aux.get(ntope-2).ret;
		paso();
	}
	
	public static void fun061() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
	    zona_declaracion = true;
		
		paso();
	}
	
	public static void fun062() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);

		//  B-> let {6.1} id T ; {6.2} 
		// {insertarTABSIMB(id.posTABSIMB,T.tipo,desplaza); 
		// desplaza := desplaza + T.ancho; 
		// zona_dec = false; 
		// B.tipoderetorno = tipo_okey} 

		
		System.out.print("\n El valor de ntope es " + ntope);
		TablaSimbolos.Entrada ent = local.busc(posToken);
		if(ent.tipo == null) 
		{
			
			System.out.print("\n El valor de la fun062 " + pil_aux.get(ntope-1).tok);
			//ent.tipo = pil_aux.get(ntope-1).tip;
			
			//modificamos para la TS
			System.out.print("La entrada de la que vamos a modificar es de " + ent.lexema+ "\n");
			System.out.print("El tipo de la entrada de la que vamos a modificar es (ntope-3) " + pil_aux.get(ntope-3).tip+ "\n");
			
			//	System.out.print("EL VALOR DE NTOPE+1 ES " + pil_aux.get(ntope+1).tip+ "\n"); //ESTA AQUIIII, LA COSA ES QUE 
			//EN LA PRIMERA ITERACION ESTA EN NTOPE Y EN LA SEGUNDA ESTA EN NTOPE +1, LUEGO SE NOS HAN OLVIDADO POR HACER
			//2 POPS DE PILA AUX
			System.out.print("EL VALOR DE NTOPE-1 (en la 6.1)ES " + pil_aux.get(ntope-1).tip+ "\n");
			System.out.print("EL VALOR DE NTOPE-2 (en la 6.1)ES " + pil_aux.get(ntope-2).tip+ "\n");
			System.out.print("EL VALOR DE NTOPE-3 (en la 6.1)ES " + pil_aux.get(ntope-3).tip+ "\n");
			System.out.print("EL VALOR DE NTOPE-4 (en la 6.1)ES " + pil_aux.get(ntope-4).tip+ "\n");
			
			System.out.print("EL NUMERO DE ENTRADAS QUR TIENE LA TABLA AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAES "+local.numEntradas+"\n");
			
			ent.setTipo(pil_aux.get(ntope-2).tip);
			
			ent.setDesplaz(local.desplazamiento);
			local.desplazamiento += valdesp(ent.tipo);
			zona_declaracion = false;

			pil_aux.get(ntope-5).tip = "tipo_okey";
			//Tratamos el antecedente B (cogido de las funciones en las que declaras el tipo	
			//NO HACE FALTA, SI ES ERROR SALTARA ERROR Y PUNTO
		}
		else 
		{
			GestorErrores(9,0,null,null);
		}	
		paso();
	}
	
	public static void fun07() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		
		System.out.print("EL VALOR DE NTOPE-1 (en la 07)ES " + pil_aux.get(ntope-1).tok+ "'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''\n");//}
		System.out.print("EL VALOR DE NTOPE-2 (en la while)ES " + pil_aux.get(ntope-2).tok+ "\n");//C
		System.out.print("EL VALOR DE NTOPE-3 (en la while)ES " + pil_aux.get(ntope-3).tok+ "\n");//{
		System.out.print("EL VALOR DE NTOPE-4 (en la while)ES " + pil_aux.get(ntope-4).tok+ "\n");//)
		System.out.print("EL VALOR DE NTOPE-5 (en la while)ES " + pil_aux.get(ntope-5).tip+ "\n");//E
		if(!pil_aux.get(ntope-5).tip.equals("bool") &&!pil_aux.get(ntope-4).tip.equals("bool") )
			GestorErrores(6,0,null,null);
		paso();
	}
	
	public static void fun011() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		entrada entrada = pil_aux.get(pil_aux.size() -2);
		entrada.tip = "int";
		pil_aux.remove(pil_aux.size()-2);
		pil_aux.add(pil_aux.size()-2, entrada);
		
		paso();
	}
	
	public static void fun012() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		entrada entrada = pil_aux.get(pil_aux.size() -2);
		entrada.tip = "bool";
		pil_aux.remove(pil_aux.size()-2);
		pil_aux.add(pil_aux.size()-2, entrada);
		
		paso();
	}
	
	public static void fun013() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		entrada entrada = pil_aux.get(pil_aux.size() -2);
		entrada.tip = "st";
		pil_aux.remove(pil_aux.size()-2);
		pil_aux.add(pil_aux.size()-2, entrada);
		
		paso();
	}
	
	public static void fun014() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		
		
		//System.out.print("El token con el que estoy es "+ local.busc(posToken).lexema + "ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");
		System.out.print("EL VALOR DE NTOPE-2(tipo) (en la en 014)ES " + pil_aux.get(ntope-1).tok+ "\n");
		System.out.print("EL VALOR DE NTOPE-1(tipo) (en la en 014)ES " + pil_aux.get(ntope-1).tip+ "\n");
		System.out.print("EL VALOR DE NTOPE-2(tipo) (en la en 014)ES " + pil_aux.get(ntope-2).tip+ "\n");
		System.out.print("EL VALOR DE NTOPE-2 (en la en 014)ES " + pil_aux.get(ntope-2).tok+ "\n"); //M
		System.out.print("EL VALOR DE NTOPE-3 (en la 014)ES " + pil_aux.get(ntope-3).tok+ "\n");//E
		System.out.print("EL VALOR DE NTOPE-4 (en la 14)ES " + pil_aux.get(ntope-4).tok+ "\n");
		System.out.print("EL VALOR DE NTOPE-4 (en la 14)ES LA 14------SJDFJHUUUUUUHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
		
		/*if( pil_aux.get(ntope-1).tip.equals("void")|| ( pil_aux.get(ntope-1).tip.equals("int") &&  pil_aux.get(ntope-2).tip.equals("int")) ){
			 pil_aux.get(ntope-3).tip = pil_aux.get(ntope-2).tip;
		}else {
			pil_aux.get(ntope-3).tip = "tipo_error";
		}*/
		if(pil_aux.get(ntope-3).tip.equals("-")) 
		{
			pil_aux.get(ntope-3).tip = pil_aux.get(ntope-2).tip;
		}
		System.out.print("EL VALOR DE NTOPE-3 (en la 014)ES " + pil_aux.get(ntope-3).tok+ "\n");//E
		System.out.print("EL VALOR DE NTOPE-3 (en la 014)ES " + pil_aux.get(ntope-3).tip+ "\n");//E
		//pil_aux.get(ntope-3).tip = pil_aux.get(ntope-2).tip;
		paso();
	}
	
	public static void fun015() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);

		System.out.print("EL VALOR DE NTOPE-1 (en la en 015)ES " + pil_aux.get(ntope-1).tip+ "\n");//D
		System.out.print("EL VALOR DE NTOPE-2 (en la en 015)ES " + pil_aux.get(ntope-2).tip+ "\n");//M
		System.out.print("EL VALOR DE NTOPE-3 (en la 015)ES " + pil_aux.get(ntope-3).tip+ "\n");//<
		System.out.print("EL VALOR DE NTOPE-4 (en la 015)ES " + pil_aux.get(ntope-4).tip+ "\n"); //D
		System.out.print("EL VALOR DE NTOPE-5 (en la 015)ES " + pil_aux.get(ntope-5).tip+ "\n"); //M
		System.out.print("EL VALOR DE NTOPE-6 (en la 015)ES " + pil_aux.get(ntope-6).tok+ "\n"); //E
		
		if(!(pil_aux.get(ntope-2).tip.equals("int") && pil_aux.get(ntope-5).tip.equals("int")))
			GestorErrores(4,0,null,null);
		pil_aux.get(ntope-6).tip = "bool";
		if( pil_aux.get(ntope-1).tip.equals("void")|| ( pil_aux.get(ntope-1).tip.equals("int") &&  pil_aux.get(ntope-2).tip.equals("int")) ){
			 pil_aux.get(ntope-4).tip = pil_aux.get(ntope-2).tip;//AHORA NO SERA -3 SINO -4 POR EL <
		}else {
			pil_aux.get(ntope-4).tip = "tipo_error";
		}
		paso();
	}
	
	public static void fun016() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		System.out.print("EL VALOR DE NTOPE-1 (en la en 016)ES " + pil_aux.get(ntope-1).tok+ "\n");//D
		System.out.print("EL VALOR DE NTOPE-2 (en la en 016)ES " + pil_aux.get(ntope-2).tip+ "\n"); //M
		System.out.print("EL VALOR DE NTOPE-3 (en la 016)ES " + pil_aux.get(ntope-3).tok+ "\n");//<
		System.out.print("EL VALOR DE NTOPE-4 (en la 016)ES " + pil_aux.get(ntope-4).tok+ "\n");//D
		System.out.print("EL VALOR DE NTOPE-5 (en la 016)ES " + pil_aux.get(ntope-5).tok+ "\n");//M
		System.out.print("EN EL D DE LAMDA EL TOKEN QUE SE METE ES " + pil_aux.get(ntope-1).tok+ "\n");
	    pil_aux.get(ntope-1).tip= "void";
		
		paso();
	}
	
	public static void fun017() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		System.out.print("EL VALOR DE NTOPE-1 (en la en 17)ES " + pil_aux.get(ntope-1).tip+ "\n");
		System.out.print("EL VALOR DE NTOPE-2 (en la en 17)ES " + pil_aux.get(ntope-2).tip+ "\n");
		System.out.print("EL VALOR DE NTOPE-3 (en la 17)ES " + pil_aux.get(ntope-3).tip+ "\n");
		System.out.print("EL VALOR DE NTOPE-4 (en la 17)ES " + pil_aux.get(ntope-4).tip+ "\n");
		System.out.print("----------------------------------------------------------");
		System.out.print("EL VALOR DE NTOPE-1 (en la en 17)ES " + pil_aux.get(ntope-1).tok+ "\n");
		System.out.print("EL VALOR DE NTOPE-2 (en la en 17)ES " + pil_aux.get(ntope-2).tok+ "\n");
		System.out.print("EL VALOR DE NTOPE-3 (en la 17)ES " + pil_aux.get(ntope-3).tok+ "\n");
		System.out.print("EL VALOR DE NTOPE-4 (en la 17)ES " + pil_aux.get(ntope-4).tok+ "\n");
		System.out.print("EL VALOR DE NTOPE-4 (en la 17)ES VVVVVVVVVVVVVVVVVVVVVVFBHHHHHHHHWOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO ");
		if( pil_aux.get(ntope-1).tip.equals("void")|| ( pil_aux.get(ntope-1).tip.equals(pil_aux.get(ntope-2).tip) ) ){
			pil_aux.get(ntope-3).tip = pil_aux.get(ntope-2).tip;
		}else {
			pil_aux.get(ntope-3).tip = "tipo_error";
		}
		System.out.print("EL VALOR DE NTOPE-1 (en la en 17)ES " + pil_aux.get(ntope-1).tip+ "\n");
		System.out.print("EL VALOR DE NTOPE-2 (en la en 17)ES " + pil_aux.get(ntope-2).tip+ "\n");
		System.out.print("EL VALOR DE NTOPE-3 (en la 17)ES " + pil_aux.get(ntope-3).tip+ "\n");
		System.out.print("EL VALOR DE NTOPE-4 (en la 17)ES " + pil_aux.get(ntope-4).tip+ "\n");
		paso();
	}
	
	public static void fun018() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		System.out.print("EL VALOR DE NTOPE-1 (en la en 18)ES " + pil_aux.get(ntope-1).tip+ "\n");//G
		System.out.print("EL VALOR DE NTOPE-2 (en la en G)ES " + pil_aux.get(ntope-2).tip+ "\n");//V
		System.out.print("EL VALOR DE NTOPE-3 (en la G)ES " + pil_aux.get(ntope-3).tok+ "\n");
		System.out.print("EL VALOR DE NTOPE-4 (en la G)ES " + pil_aux.get(ntope-4).tip+ "\n");//G
		System.out.print("EL VALOR DE NTOPE-1 (en la en G)ES " + pil_aux.get(ntope-5).tip+ "\n");//V
		System.out.print("EL VALOR DE NTOPE-2 (en la en G)ES " + pil_aux.get(ntope-6).tok+ "\n");
		
		
		if( pil_aux.get(ntope-1).tip.equals("void")|| ( pil_aux.get(ntope-1).tip.equals(pil_aux.get(ntope-2).tip) ) ){
			 pil_aux.get(ntope-4).tip= pil_aux.get(ntope-2).tip;
		}else {
			//error
		}
		paso();
	}
	
	public static void fun019() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		System.out.print("EL VALOR DE NTOPE-1 (en la en 019)ES " + pil_aux.get(ntope-1).tip+ "\n");
		System.out.print("EL VALOR DE NTOPE-2 (en la en 019)ES " + pil_aux.get(ntope-2).tip+ "\n");
		System.out.print("EL VALOR DE NTOPE-3 (en la 019)ES " + pil_aux.get(ntope-3).tip+ "\n"); //M
		System.out.print("EL VALOR DE NTOPE-4 (en la 019)ES " + pil_aux.get(ntope-4).tip+ "\n");
		System.out.print("EL VALOR DE NTOPE-1 token(en la en 019)ES " + pil_aux.get(ntope-1).tok+ "\n");
		System.out.print("EL VALOR DE NTOPE-2 token(en la en 019)ES " + pil_aux.get(ntope-2).tok+ "\n");
		System.out.print("EL VALOR DE NTOPE-3 token(en la 019)ES " + pil_aux.get(ntope-3).tok+ "\n"); //M
		System.out.print("EL VALOR DE NTOPE-4 token(en la 019)ES " + pil_aux.get(ntope-4).tok+ "\n");
		System.out.print("EN EL D DE LAMDA EL TOKEN QUE SE METE ES " + pil_aux.get(ntope-1).tok+ "\n");
	    pil_aux.get(ntope-1).tip= "void";
		
		paso();
	}
	
	public static void fun020() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		
		TablaSimbolos.Entrada enti = local.busc(posToken);
		
		idddd = posToken;
		boolean p = false;
		if(zon_fun) 
		{
			TablaSimbolos.Entrada ent = local.busc(posToken);
			 for (Entry<Integer, TablaSimbolos.Entrada> entrada : auxiliar.tabla.entrySet()) 
			 {
				 if(entrada.getValue().lexema.equals(ent.lexema)) 
				 {
					 pil_aux.get(ntope-2).tip =entrada.getValue().tipo;
					 if(pil_aux.get(ntope-2).tip.equals("funcion")) 
					 {
						 p=false;
					 }
						 else 
					 {
						 local.eliminar(posToken);
						 p = true;
					 }
				 }
			 }
		}
		
		if(pil_aux.get(ntope-2).tip.equals("-")) 
		pil_aux.get(ntope-2).tip=enti.tipo;
		if(!p&&!dentroFun && local.busc(posToken).tipo !=null && local.busc(posToken).tipo.equals("funcion")) {
			pil_aux.get(ntope-2).tip = enti.retorno;
			postok2 = posToken;
			eunafun=true;
		}
		
		if(zon_fun && !p) 
		{
			if(idFun == false) {
			String tipo_retorno = "";
			String etiqueta = "";
			boolean encontrada = false;
			TablaSimbolos.Entrada ent = local.busc(posToken);
			 for (Entry<Integer, TablaSimbolos.Entrada> entrada : auxiliar.tabla.entrySet()) 
			 {
				 if(entrada.getValue().lexema.equals(ent.lexema)) 
				 {
					 if(entrada.getValue().tipo.equals("funcion")) 
					 {
						 encontrada = true;
						 etiqueta=entrada.getValue().etiq;
						 postok2=entrada.getKey(); 
						 pil_aux.get(ntope-2).tip=entrada.getValue().retorno;
						 tipo_retorno = entrada.getValue().retorno;
					 }
					 
				 }
			 }
			if(encontrada)
			{
				ent.etiq=etiqueta;
				dentroFun = true;
				ent.tipo = "funcion";
				parametros_aux =  new ArrayList<String>();
				idFun = true;
				indice_atributos_funcion = 0;
				fun_aux_id = posToken;
				ent.retorno = tipo_retorno;
				eunafun=true;
				
			}
			}
			else 
			{
				TablaSimbolos.Entrada ent = local.busc(posToken);
				parametros_aux.add(ent.tipo);
				
			}
		}
		if(rt) 
		{
		 TablaSimbolos.Entrada ent = local.busc(posToken);
		 
		}
		else 
		{
			TablaSimbolos.Entrada ent = local.busc(posToken);
			if(ent == null)
			{
				// SALTAR ERROR
			}
		}
		p = false;
		
		
		paso();
	}
	
	public static void fun021() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		
		pil_aux.get(ntope-2).tip = "st";
		paso();
	}
	
	public static void fun022() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		
		pila.remove(pila.size() -1);
	
		
		pil_aux.get(ntope-4).tip= pil_aux.get(ntope-2).tip;
		
		
		
		paso();
	}
	
	public static void fun023() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);

		if (pil_aux.get(ntope-1).tip.equals("bool")){
			pil_aux.get(ntope-3).tip = "bool";
			//pil_aux.get(ntope-3).ancho = 1;
		} else {
			GestorErrores(10,0,null,null);
			pil_aux.get(ntope-3).tip = pil_aux.get(ntope-1).tip;
		}

		paso();
	}
	
	public static void fun024() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		
		pil_aux.get(ntope-2).tip="int";
		
		paso();
	}
	
	
	
	public static void fun025() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
	
		if(pil_aux.get(ntope-6).tip == null) 
		{
			GestorErrores(7,0,null,null);
			pil_aux.get(ntope-6).tip = "error";
			local.eliminar(idddd);
		}
		
		
		if(eunafun) {
			
			
			if(zon_fun) {
				if(param.size() != 0 && auxiliar.busc(postok2).parametros == null) 
				{
					GestorErrores(8,0,null,null);
				}
				else if(param.size() == auxiliar.busc(postok2).parametros.size() ) {
					
				for (int i = 0; i < param.size(); i++) {
		            if (!param.get(param.size()-i-1).equals(auxiliar.busc(postok2).parametros.get(i))) {
		            	
		                System.out.println("Diferencia en el índice :");
		                System.out.println("tu llamada a la funcion definida previamente[" + i + "] = " + param.get(param.size()-i-1));
		                System.out.println("Paramteros de la funcion definidos previamente[" + i + "] = " + auxiliar.busc(postok2).parametros.get(i));
		                GestorErrores(5,i,param.get(param.size()-i-1),auxiliar.busc(postok2).parametros.get(i));
		            }
		        }
				
				System.out.print("VAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
				}else {
					GestorErrores(8,0,null,null);
					 
				}
				
			}else {
				if(param.size() != 0 && local.busc(postok2).parametros == null) 
				{
					GestorErrores(8,0,null,null);
				}
				else if(param.size() == local.busc(postok2).parametros.size()) {
					
					for (int i = 0; i < param.size(); i++) {
			            if (!param.get(param.size()-i-1).equals(local.busc(postok2).parametros.get(i))) {
			            	
			                System.out.println("Diferencia en el índice :");
			                System.out.println("tu llamada a la funcion definida previamente[" + i + "] = " + param.get(param.size()-i-1));
			                System.out.println("Paramteros de la funcion definidos previamente[" + i + "] = " + auxiliar.busc(postok2).parametros.get(i));
			                GestorErrores(5,i,param.get(param.size()-i-1),local.busc(postok2).parametros.get(i));
			            }
			        }
					
					System.out.print("VAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
					}else {
						GestorErrores(8,0,null,null);
						
					}
				
			}
			 
			
		}

		if(idFun) 
		{		
			TablaSimbolos.Entrada ent = local.busc(fun_aux_id);
			ent.parametros = parametros_aux;
			idFun = false;
			sinArg = false;
		}
		pil_aux.get(ntope-4).tip= pil_aux.get(ntope-2).tip;
		
		param.clear();
		paso();
	}
	
	public static void fun026() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		System.out.print("EL VALOR DE NTOPE-1 (en la en 026)ES " + pil_aux.get(ntope-1).tip+ "\n");//J
		System.out.print("EL VALOR DE NTOPE-2 (en la en 026)ES " + pil_aux.get(ntope-2).tip+ "\n"); //id
		System.out.print("EL VALOR DE NTOPE-3 (en la 026)ES " + pil_aux.get(ntope-3).tip+ "\n");//V
		System.out.print("EL VALOR DE NTOPE-4 (en la 026)ES " + pil_aux.get(ntope-4).tip+ "\n");//M
		System.out.print("EN EL J DE LAMDA EL TOKEN QUE SE METE ES " + pil_aux.get(ntope-1).tok+ "\n");
	    pil_aux.get(ntope-1).tip= "void";
	    if( pil_aux.get(ntope-3).tip == null) 
		{
			if(zon_fun) 
			{
				TablaSimbolos.Entrada ent = auxiliar.busc(posToken);
				ent.tipo= "int";
				ent.desplazamiento = auxiliar.desplazamiento;
				auxiliar.desplazamiento += valdesp(ent.tipo);
			}
			else 
			{
				TablaSimbolos.Entrada ent = local.busc(posToken);
				ent.tipo= "int";
				ent.desplazamiento = local.desplazamiento;
				local.desplazamiento += valdesp(ent.tipo);
			}
			pil_aux.get(ntope-3).tip = "int";
			}
		paso();
	}
	
	public static void fun027() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		
		pila.remove(pila.size() -1);
		
		
		
		if (!pil_aux.get(ntope-2).tip.equals("tipo_error")){
			pil_aux.get(ntope-3).tip = pil_aux.get(ntope-1).tip;
				
			if(eunafun) {
				param.add(pil_aux.get(ntope-2).tip);
				}
			
		} else {
			pil_aux.get(ntope-3).tip = "tipo_novalidoerr";
		}

		
		paso();
	}
	
	public static void fun028() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
	    pil_aux.get(ntope-1).tip= "void";
		
		paso();
	}
	
	
	public static void fun029() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		//	Q -> , E Q’ {29}
		//	{Q.tipo= If E.tipo != tipo_error ) then E.tipo x Q’.tipo else  tipo_novalidoerr}
		pila.remove(pila.size() -1);

	

		if (!pil_aux.get(ntope-2).tip.equals("tipo_error")){
			pil_aux.get(ntope-4).tip = pil_aux.get(ntope-2).tip; //REVISAR
			
			if(eunafun) {
				
			param.add(pil_aux.get(ntope-4).tip);
			}
		} else {
			pil_aux.get(ntope-4).tip = "tipo_novalidoerr";
		}

		paso();
	}
	
	public static void fun030() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
	    pil_aux.get(ntope-1).tip= "void";
		
		paso();
	}
	
	public static void fun0312() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{

		
		pila.remove(pila.size() -1);
	
		idddd = posToken;
		boolean p = false;
		if(zon_fun) 
		{
			TablaSimbolos.Entrada ent = local.busc(posToken);
			 for (Entry<Integer, TablaSimbolos.Entrada> entrada : auxiliar.tabla.entrySet()) 
			 {
				 if(entrada.getValue().lexema.equals(ent.lexema)) 
				 {
					 pil_aux.get(ntope-1).tip =entrada.getValue().tipo;
					 if(pil_aux.get(ntope-2).tip.equals("funcion"))
						 p=false;
					 else 
					 {
						 local.eliminar(posToken);
						 p = true;
					 }
				 }
			 }
		}
		
		if(!p)
		   pil_aux.get(ntope-1).tip = local.busc(posToken).gettipo();
		if(zon_fun &&!p) 
		{
			if(idFun == false) {
			String tipo_retorno = "";
			String etiqueta = "";
			boolean encontrada = false;
			TablaSimbolos.Entrada ent = local.busc(posToken);
			 for (Entry<Integer, TablaSimbolos.Entrada> entrada : auxiliar.tabla.entrySet()) 
			 {
				 if(entrada.getValue().lexema.equals(ent.lexema)) 
				 {
					 if(entrada.getValue().tipo.equals("funcion")) 
					 {
						 encontrada = true;
						 tipo_retorno = entrada.getValue().retorno;
						 etiqueta = entrada.getValue().etiq;					 
					 }
					 
				 }
			 }
			if(encontrada)
			{
				ent.tipo = "funcion";
				parametros_aux =  new ArrayList<String>();
				idFun = true;
				indice_atributos_funcion = 0;
				fun_aux_id = posToken;
				ent.retorno = tipo_retorno;
				ent.etiq = etiqueta;
				
			}
				
			if(!ent.tipo.equals(retfun))
				System.out.print(" \n UN ERROR EN EL TIPO DE RETURN DE LA FUNCION EN LA LINEA: " + Main.nlinea);
			}
			else 
			{
				TablaSimbolos.Entrada ent = local.busc(posToken);
				parametros_aux.add(ent.tipo);
				
			}
		}
		else 
		{
			
		}
		p = false;
		paso();
	}
	
	public static void fun0311() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		System.out.print("EL VALOR DE NTOPE-1 (en la 311)ES " + pil_aux.get(ntope-1).tip+ "\n");//N
		System.out.print("EL VALOR DE NTOPE-2 (en la 311)ES " + pil_aux.get(ntope-2).tip+ "\n");//id
		System.out.print("EL VALOR DE NTOPE-3 (en la 311)ES " + pil_aux.get(ntope-3).tip+ "\n");//S
		System.out.print("EL VALOR DE NTOPE-4 (en la 311)ES " + pil_aux.get(ntope-4).tip+ "\n");//B
		
		//pil_aux.get(ntope-1).tip = local.busc(posToken).tipo;
		paso();
	}
	
	public static void fun032() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		
		pila.remove(pila.size() -1);
		

		
		if(zon_fun) 
		{
			esperaRet = false;
			if(!pil_aux.get(ntope-1).tip.equals(tipRet)) 
				GestorErrores(2,0,null,null);
		}
		if (!pil_aux.get(ntope-1).tip.equals("tipo_novalidoerr")){
			pil_aux.get(ntope-3).tip = "tipo_okey";
		} else {
			pil_aux.get(ntope-3).tip = "tipo_novalidoerr";
		}
	    pil_aux.get(ntope-3).ret = pil_aux.get(ntope-1).tip;

		paso();
	}
	
	public static void fun033() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		
		pila.remove(pila.size() -1);
		

		if (pil_aux.get(ntope-1).tip.equals("int") || pil_aux.get(ntope-1).tip.equals("st") ){
			pil_aux.get(ntope-3).tip = pil_aux.get(ntope-1).tip;
		} else {
			pil_aux.get(ntope-3).tip = "tipo_error";
		}

		paso();
	}
	
	public static void fun034() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		
		if(zon_fun) 
		{
			boolean c = false;
			if(local.busc(posToken).tipo == null)
			{
				TablaSimbolos.Entrada ent = local.busc(posToken);
				 for (Entry<Integer, TablaSimbolos.Entrada> entrada : auxiliar.tabla.entrySet()) 
				 {
					 if(entrada.getValue().lexema.equals(ent.lexema)) 
					 {   local.eliminar(posToken);
					 	 c = true;
						 pil_aux.get(ntope-3).tip = entrada.getValue().tipo;
					 }
				 }
			}
			if( !c)
			{
				if(local.busc(posToken).tipo == null ) {
				int n = auxiliar.insertarEntrada(local.busc(posToken).lexema);
				 auxiliar.busc(n).tipo = "int";
				 auxiliar.busc(n).desplazamiento = auxiliar.desplazamiento;
				 auxiliar.desplazamiento +=valdesp(auxiliar.busc(n).tipo);
				pil_aux.get(ntope-3).tip = "int";
				local.eliminar(posToken);}
			}
		}
		else 
		{
			if(local.busc(posToken).tipo == null) 
			{
				local.busc(posToken).tipo = "int";
				local.busc(posToken).desplazamiento = local.desplazamiento;
				local.desplazamiento += valdesp(local.busc(posToken).tipo);
				pil_aux.get(ntope-3).tip = "int";
			}
			else
			pil_aux.get(ntope-3).tip = local.busc(posToken).tipo;
		}
		paso();
	}
	
	public static void fun035() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		if(pil_aux.get(ntope-4).tip == null) 
		{
				int ultimod= local.obtenerultimaEntrada();
				if(ultimod==-1) {
					ultimod=0;
				}
				
				local.busc(idddd).tipo = "int";
				local.busc(posToken).setDesplaz(local.desplazamiento);
				local.desplazamiento += valdesp(local.busc(posToken).tipo);
				pil_aux.get(ntope-4).tip = "int";
				
		}
		if(pil_aux.get(ntope-1).tip.equals(pil_aux.get(ntope-4).tip))
		{
		pil_aux.get(ntope-3).tip= pil_aux.get(ntope-1).tip;
		}
		else 
		{
			if(asignacion)
			 GestorErrores(3,0,null,null);
			else
			 GestorErrores(4,0,null,null);
		}
		paso();
	}
	
	public static void fun036() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		
		if(pil_aux.get(ntope-5).tip == null) 
		{
			GestorErrores(7,0,null,null);
			pil_aux.get(ntope-5).tip = "error";
			local.eliminar(idddd);
		}
		pil_aux.get(ntope-4).tip= pil_aux.get(ntope-2).tip;
		paso();
	}
	
	public static void fun037() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		
		
		
		
		pil_aux.get(ntope-2).tip= pil_aux.get(ntope-1).tip;
		paso();
	}
	
	public static void fun038() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		
	    pil_aux.get(ntope-1).tip= "void";
		
		paso();
	}
	
	public static void fun0391() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		
		
		pil_aux.get(ntope -1).tip = "funcion";
	    zona_declaracion = true;
	    zon_fun = true;
		paso();
	}
	
	public static void fun0392() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		TablaSimbolos.Entrada ent = local.busc(posToken);
	     funcion_id = posToken;
	     parametros = new ArrayList<String>();
		if(ent.tipo == null) 
		{	
			ent.tipo = "funcion";
			ent.setDesplaz(local.desplazamiento);
			local.desplazamiento += valdesp(ent.tipo);
			zona_declaracion = false;
			ent.parametros = parametros;
			ent.retorno= pil_aux.get(ntope-1).tip;
			tipRet=retfun;
			ent.etiq = ent.lexema + indiceFun; 
			if(ent.retorno.equals("int")||ent.retorno.equals("bool")||ent.retorno.equals("st")) {
				esperaRet = true;
			}
			
		}	
		CrearTs();
		paso();
	}
	
	public static void fun0393() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
	    zona_declaracion = false;
		paso();
	}
	
	public static void fun0394() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		
		paso();
	}
	
	public static void fun0395() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() - 1);

		
		
		if (!pil_aux.get(ntope-1).ret.equals(pil_aux.get(ntope-7).tip)){
			// error 
		}

		paso();
	}
	
	public static void fun0396() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		CerrarTs();
		zon_fun= false;
		if(esperaRet)
			GestorErrores(1,0,null,null);
		paso();
	}
	
	
	public static void fun0401() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		TablaSimbolos.Entrada ent = local.busc(posToken);
		if(ent.tipo == null) 
		{
			
			ent.setTipo(pil_aux.get(ntope-3).tip);
			ent.setDesplaz(local.desplazamiento);
			local.desplazamiento += valdesp(ent.tipo);
			zona_declaracion = false;
			parametros.add(pil_aux.get(ntope-3).tip);
		}
		else 
		{
			
		}	
		paso();
	}
	
	public static void fun0402() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		
		paso();
	}
	
	public static void fun041() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
	    pil_aux.get(ntope-1).tip= "void";
		
		paso();
	}
	
	public static void fun042() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		paso();
	}
	

	
	public static void fun043() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		
		pila.remove(pila.size() - 1);
		

		

		pil_aux.get(ntope-5).tip = pil_aux.get(ntope-7).tip;
		
		TablaSimbolos.Entrada ent = local.busc(posToken);
		if(ent.tipo == null) 
		{
			
			
			ent.setTipo(pil_aux.get(ntope-3).tip);
			ent.setDesplaz(local.desplazamiento);
			local.desplazamiento += valdesp(ent.tipo);
			parametros.add(pil_aux.get(ntope-3).tip);
			zona_declaracion = false;

			
		}
		else 
		{
			
		}
		paso();
	}
	
	public static void fun044() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
	    pil_aux.get(ntope-1).tip= "void";
		
		paso();
	}
	
	public static void fun045() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		

		if (!pil_aux.get(ntope-2).tip.equals("tipo_novalidoerr") && !pil_aux.get(ntope-1).tip.equals("tipo_novalidoerr")){
			if (pil_aux.get(ntope-2).tip.equals(pil_aux.get(ntope-1).tip)|| pil_aux.get(ntope-1).tip.equals("tipo_okey")){
				pil_aux.get(ntope-3).ret = pil_aux.get(ntope-1).ret;
			} else {
				pil_aux.get(ntope-3).ret = "tipo_novalidoerr";
			}
		} else {
			pil_aux.get(ntope-3).tip = "tipo_novalidoerr";
		}

		pila.remove(pila.size() -1);
		paso();
	}
	
	public static void fun046() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);

		

		pil_aux.get(ntope-1).tip = "tipo_okey";
	    pil_aux.get(ntope-1).ret= "void";
		
		paso();
	}
	
	public static void fun047() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		
		retfun = pil_aux.get(ntope-2).tip;
		pil_aux.get(ntope-1).tip= pil_aux.get(ntope-2).tip;
		paso();
	}
	
	public static void fun048() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		
		
	    pil_aux.get(ntope-2).tip= "void";
		
		paso();
	}
	
	public static void Pop1 () throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		ntope --;
		paso();
	}
	public static void Pop2 () throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		ntope --;
		ntope --;
		paso();
	}
	
	public static void Pop3 () throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		ntope --;
		ntope --;
		ntope --;
		paso();
	}
	
	public static void Pop4 () throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		
		pila.remove(pila.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		
		paso();
	}
	
	public static void Pop5 () throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		paso();
	}
	public static void Pop6 () throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		
	
		paso();
	}
	public static void Pop7 () throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		paso();
	}
	public static void Pop8 () throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		paso();
	}
	public static void Pop9 () throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		paso();
	}

	public static void Pop10 () throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException 
	{
		pila.remove(pila.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		pil_aux.remove(pil_aux.size() -1);
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		ntope --;
		paso();
	}
	
	public static void fin() throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException 
	{
		entrada c = pila.get(pila.size() -1);
		
		if(!c.tok.equals("$")) {
			gestionErrores(3);
		} else{
			
		}
	}
	public static int valdesp(String str) {
	    
		if(str.equals("int") ) {
			return 2;
		}else if( str.equals("bool")) {
			return 1;			
		}else if(str.equals("st") ) {
			return 64;
		}else {
		return 0;
		}
	}
	 public static boolean esNumerico(String str) {
	        // Utilizar una expresión regular para verificar si el string es numérico
	        return str.matches("-?\\d+(\\.\\d+)?"); // Acepta enteros y decimales (positivos y negativos)
	    }

	 public static void GestorErrores(int error,int indice,String param1, String param2) throws IOException 
	 {
		 estado = 1;
		 Main.fallaSemantico = true;
		switch (error) 
		{
		case 1:
			bufferErrores.write("Error semantico: Falta el return en la funcion. Linea " +Main.nlinea+ "\n");
			bufferErrores.write("\n");
			break;
		case 2:
			bufferErrores.write("Error semantico: El return no devuelve el tipo que deberia la funcion. Linea " +Main.nlinea + "\n");
			bufferErrores.write("\n");
			break;
		case 3:
			bufferErrores.write("Error semantico: La asignacion no se puede hacer entre dos tipos distintos. Linea " +Main.nlinea + "\n");
			bufferErrores.write("\n");
			break;
		case 4:
			bufferErrores.write("Error semantico: La operacion no se puede hacer sobre ese tipo de datos. Linea " +Main.nlinea + "\n");
			bufferErrores.write("\n");
			break;
		case 5:
			bufferErrores.write("Error semantico: No existe ninguna funcion con esos argumentos, revisar si son del tipo correcto o si falta por implementar dicha funcion. Linea " +Main.nlinea + "\n");
			bufferErrores.write("En concreto: Diferencia en el índice :\n");
			bufferErrores.write("Tu llamada a la funcion definida previamente[" + indice + "] = " + param1+ "\n");
			bufferErrores.write("Paramteros de la funcion definidos previamente[" + indice + "] = " + param2 + "\n");
			bufferErrores.write("\n");
			break;
		case 6:
			bufferErrores.write("Error semantico: Dentro de los parentesis de un if o un while debe de haber un booleano. Linea " +Main.nlinea +"\n");
			bufferErrores.write("\n");
			break;
		case 7:
			bufferErrores.write("Error semantico: No existe ninguna funcion con ese nombre. Linea " + Main.nlinea + "\n");
			bufferErrores.write("\n");
			break;
		case 8: 
			bufferErrores.write("Error semantico: Distinto numero de parametros. Linea " +Main.nlinea +"\n");
			bufferErrores.write("\n");
			break;
		case 9: 
			bufferErrores.write("Error semantico: Ya existe una variable con ese nombre. Linea " +Main.nlinea +"\n");
			bufferErrores.write("\n");
			break;
		case 10: 
			bufferErrores.write("Error semantico: El operador ! solo se puede emplear con booleanos. Linea " +Main.nlinea +"\n");
			bufferErrores.write("\n");
			break;
		}
		return;
	 }
	}

class entrada
{
	 String tok;
	 String ret;
	 String tip;
	 

	    public entrada(String tok, String ret, String tip){
	        this.tok = tok;                                        
	        this.ret = ret;                                         
	        this.tip = tip;       
			                                 
	    }	
}