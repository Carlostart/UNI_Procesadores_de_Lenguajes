import java.util.*;

public class Generator {

	public static final int PRINT = 0;
	public static final int MAS = 1;
	public static final int MENOS = 2;
	public static final int POR = 3;
	public static final int DIV = 4;
	public static final int ASIG = 5;
	public static final int LABEL = 6;
	public static final int GOTO = 7;
	public static final int PRINTC = 8;

	public static List<String> chars = new ArrayList<>();
	public static List<String> ints = new ArrayList<>();

	public static int indiceTemp = 0;
	public static int indiceEtiq = 0;

	public static String expresion (String e, int p){
		if (p == 1) {
			if (ints.contains(e)) salida(PRINT, e, null, null);
			else if(chars.contains(e)) salida(PRINTC, e, null, null);
			else if (e.charAt(0) == 'c') salida(PRINTC, e.substring(1), null, null);
			else if (e.charAt(0) == 'i') salida(PRINT, e.substring(1), null, null);
			else salida(PRINT, e, null, null);
		} else {
			if (ints.contains(e) || chars.contains(e) || e.charAt(0) == '$') return e;
			else return e.substring(1);
		}
		return "";
	}

	public static String tipo (String e) {
		if (ints.contains(e)) return "i";
		else if(chars.contains(e)) return "c";
		else if (e.charAt(0) == 'c') return "c";
		else if (e.charAt(0) == 'i') return "i";
		return "i";
	}

	public static String nuevaEtiqueta(){
		return "L"+(indiceEtiq++);
	}

	public static String nuevaTemp(){
		return "$t"+indiceTemp++;
	}

	public static void salida (int inst, String arg1, String arg2, String res){
		switch (inst){
		case MAS:
			PLXC.out.println(res+" = "+arg1+" + "+arg2+";");
			// System.out.println(res+" = "+arg1+" + "+arg2+";");
			break;
		case MENOS:
			PLXC.out.println(res+" = "+arg1+" - "+arg2+";");
			// System.out.println(res+" = "+arg1+" - "+arg2+";");
			break;
		case POR:
			PLXC.out.println(res+" = "+arg1+" * "+arg2+";");
			// System.out.println(res+" = "+arg1+" * "+arg2+";");
			break;
		case DIV:
			PLXC.out.println(res+" = "+arg1+" / "+arg2+";");
			// System.out.println(res+" = "+arg1+" / "+arg2+";");
		break;
		case PRINT:
			PLXC.out.println("print "+arg1+";");
			// System.out.println("print "+arg1+";");
		break;
		case PRINTC:
			PLXC.out.println("printc "+arg1+";");
			// System.out.println("print "+arg1+";");
			break;
		case ASIG:
			PLXC.out.println(arg1 + " = " + arg2+";");
			// System.out.println(arg1 + " = " + arg2+";");
			break;
		case LABEL:
			PLXC.out.println(res+":");
			// System.out.println(res+":");
			break;
		case GOTO:
			PLXC.out.println("goto "+res+";");
			// System.out.println("goto "+res+";");
			break;
		}
	}
}
