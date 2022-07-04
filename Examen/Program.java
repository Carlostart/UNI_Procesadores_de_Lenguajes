
// Copyright (c) 2010 Gavin Harrison
// See LICENSE for GPLv3 Terms

import java.util.*;
import java.text.DecimalFormat;

interface Expr {
	ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm);

	String get();
}

interface Statement {
	void run(HashMap<String, ArrayList<Double>> hm);
}

class Num implements Expr {
	ArrayList<Double> val = new ArrayList<Double>();

	public Num(ArrayList<Double> i) {
		val.addAll(i);
	}

	public String get() {
		return val.get(0).toString();
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		return val;
	}
}

class Arr implements Expr {
	ArrayList<Expr> exprs;

	public Arr(ArrayList<Expr> i) {
		exprs = i;
	}

	public String get() {
		return exprs.toString();
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> ld = new ArrayList<>();
		for (Expr e : exprs) {
			if (e.run(hm) != null)
				ld.addAll(e.run(hm));
		}
		return ld;
	}
}

class ID implements Expr {
	String name;

	public ID(String s) {
		name = s;
	}

	public String get() {
		return name;
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		if (hm.containsKey(name))
			return (hm.get(name));
		else {
			ArrayList<Double> d = new ArrayList<>();
			d.add(0.0);
			hm.put(name, d);
			return (hm.get(name));
		
		}
	}
}

class IDArr implements Expr {
	String name;
	Expr index;

	public IDArr(String s, Expr i) {
		name = s;
		index = i;
	}

	public String get() {
		return name;
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		if (hm.containsKey(name)) {
			ArrayList<Double> r = new ArrayList<>();
			ArrayList<Double> idxar = index.run(hm);
			for(double idx : idxar){
				if(idx<0 || idx >= hm.get(name).size()) {
					PPerlL.out.println("error();");
					return null;
				}
				else{
					r.add(hm.get(name).get((int) idx));
				}
			} 
			return r;
		} else {
			PPerlL.out.println("error();");
			return null;
		}
	}
	
}

class Len implements Expr {
	String name;
	Expr expr;

	public Len(Expr e) {
		expr = e;
	}

	public String get() {
		return name;
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> r = new ArrayList<>();
		r.add((double)expr.run(hm).size()-1);
		return r;
	}
	
}

class Push implements Expr {
	Expr arr;
	Expr add;

	public Push(Expr ar, Expr e) {
		arr = ar;
		add = e;
	}

	public String get() {
		return "push";
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> r = (ArrayList<Double>)arr.run(hm).clone();
		r.addAll(add.run(hm));
		return r;
	}
	
}

class Pop implements Expr {
	Expr arr;

	public Pop(Expr ar) {
		arr = ar;
	}

	public String get() {
		return "pop";
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> aux = arr.run(hm);
		ArrayList<Double> r = new ArrayList<>();
		r.add(aux.get(aux.size()-1));

		aux.remove(aux.size()-1);
		return r;
	}
	
}

class Shift implements Expr {
	Expr arr;

	public Shift(Expr ar) {
		arr = ar;
	}

	public String get() {
		return "shift";
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> aux = arr.run(hm);
		ArrayList<Double> r = new ArrayList<>();
		r.add(aux.get(0));

		aux.remove(0);
		return r;
	}
	
}

class DivExpr implements Expr {
	Expr left, right;

	public DivExpr(Expr l, Expr r) {
		left = l;
		right = r;
	}

	public String get() {
		return left.get() + " / " + right.get();
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> rval = right.run(hm);
		ArrayList<Double> lval = left.run(hm);
		if (lval.size() == rval.size()) {
			ArrayList<Double> r = new ArrayList<Double>();
			for (int i = 0; i < lval.size(); i++) {
				if (rval.get(i) == 0) {
					System.out.println("Error: division by zero");
					System.exit(1);
				}
				r.add(lval.get(i) / rval.get(i));
			}
			return r;
		}
		PPerlL.out.println("Error: longitud de objetos diferentes");
		return null;
	}
}

class MultExpr implements Expr {
	Expr left, right;

	public MultExpr(Expr l, Expr r) {
		left = l;
		right = r;
	}

	public String get() {
		return left.get() + " * " + right.get();
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> rval = right.run(hm);
		ArrayList<Double> lval = left.run(hm);
		if (lval.size() == rval.size()) {
			ArrayList<Double> r = new ArrayList<Double>();
			for (int i = 0; i < rval.size(); i++) {
				r.add(lval.get(i) * rval.get(i));
			}
			return r;
		}
		PPerlL.out.println("Error: longitud de objetos diferentes");
		return null;
	}
}

class ExpExpr implements Expr {
	Expr left, right;

	public ExpExpr(Expr l, Expr r) {
		left = l;
		right = r;
	}

	public String get() {
		return left.get() + " ^ " + right.get();
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> r = new ArrayList<Double>();
		ArrayList<Double> rval = right.run(hm);
		ArrayList<Double> lval = left.run(hm);
		if (lval.size() == rval.size()) {
			for (int i = 0; i < lval.size(); i++) {
				r.add(Math.pow(lval.get(i), rval.get(i)));
			}
			return r;
		}
		PPerlL.out.println("Error: longitud de objetos diferentes");
		return null;
	}
}

class MinusExpr implements Expr {
	Expr left, right;

	public MinusExpr(Expr l, Expr r) {
		left = l;
		right = r;
	}

	public String get() {
		return left.get() + " - " + right.get();
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> r = new ArrayList<Double>();
		ArrayList<Double> rval = right.run(hm);
		ArrayList<Double> lval = left.run(hm);
		if (lval.size() == rval.size()) {
			for (int i = 0; i < lval.size(); i++) {
				r.add(lval.get(i) - rval.get(i));
			}
			return r;
		}
		PPerlL.out.println("Error: longitud de objetos diferentes");
		return null;
	}
}

class PlusExpr implements Expr {
	Expr left, right;

	public PlusExpr(Expr l, Expr r) {
		left = l;
		right = r;
	}

	public String get() {
		return left.get() + " + " + right.get();
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> r = new ArrayList<Double>();
		ArrayList<Double> rval = right.run(hm);
		ArrayList<Double> lval = left.run(hm);
		if (lval.size() == rval.size()) {
			for (int i = 0; i < lval.size(); i++) {
				r.add(lval.get(i) + rval.get(i));
			}
			return r;
		}
		PPerlL.out.println("Error: longitud de objetos diferentes");
		return null;

	}
}

class InvExpr implements Expr {
	Expr expr;

	public InvExpr(Expr e) {
		expr = e;
	}

	public String get() {
		return "-" + expr.get();
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> r = new ArrayList<Double>();
		ArrayList<Double> e = expr.run(hm);
		for (int i = 0; i < e.size(); i++) {
			r.add(-1 * e.get(i));
		}
		return r;
	}
}

class ExprErr implements Expr {
	String err;

	public ExprErr(String sl) {
		err = sl;
	}

	public String get() {
		return err;
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		PPerlL.out.println(err);
		return null;
	}
}

class Cond implements Expr {
	ArrayList<Boolean> bool = new ArrayList<>();

	public Cond(boolean b) {
		bool.add(b);
	}

	public String get() {
		return null;
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> r = new ArrayList<Double>();
		for (boolean b : bool)
			r.add(b ? 1.0 : 0.0);
		return r;
	}
}

class NotCond implements Expr {
	Expr bool;

	public NotCond(Expr b) {
		bool = b;
	}

	public String get() {
		return "";
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> r = new ArrayList<Double>();
		for (double b : bool.run(hm))
			r.add(b == 0 ? 1.0 : 0.0);
		return r;
	}
}

class GteCond implements Expr {
	Expr left, right;

	public GteCond(Expr l, Expr r) {
		left = l;
		right = r;
	}

	public String get() {
		return left.get() + " >= " + right.get();
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> r = new ArrayList<Double>();
		ArrayList<Double> rval = right.run(hm);
		ArrayList<Double> lval = left.run(hm);
		if (lval.size() == rval.size()) {
			for (int i = 0; i < lval.size(); i++) {
				r.add(lval.get(i) >= rval.get(i) ? 1.0 : 0.0);
			}
			return r;
		}
		PPerlL.out.println("Error: longitud de objetos diferentes");
		return null;
	}
}

class AndCond implements Expr {
	Expr left, right;

	public AndCond(Expr l, Expr r) {
		left = l;
		right = r;
	}

	public String get() {
		return left.get() + " && " + right.get();
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> r = new ArrayList<Double>();
		ArrayList<Double> rval = right.run(hm);
		ArrayList<Double> lval = left.run(hm);
		if (lval.size() == rval.size()) {
			for (int i = 0; i < lval.size(); i++) {
				r.add(lval.get(i) == 1 && rval.get(i) == 1 ? 1.0 : 0.0);
			}
			return r;
		}
		PPerlL.out.println("Error: longitud de objetos diferentes");
		return null;
	}
}

class OrCond implements Expr {
	Expr left, right;

	public OrCond(Expr l, Expr r) {
		left = l;
		right = r;
	}

	public String get() {
		return left.get() + " || " + right.get();
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> r = new ArrayList<Double>();
		ArrayList<Double> rval = right.run(hm);
		ArrayList<Double> lval = left.run(hm);
		if (lval.size() == rval.size()) {
			for (int i = 0; i < lval.size(); i++) {
				r.add(lval.get(i) == 1 || rval.get(i) == 1 ? 1.0 : 0.0);
			}
			return r;
		}
		PPerlL.out.println("Error: longitud de objetos diferentes");
		return null;
	}
}

class GtCond implements Expr {
	Expr left, right;

	public GtCond(Expr l, Expr r) {
		left = l;
		right = r;
	}

	public String get() {
		return left.get() + " > " + right.get();
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> r = new ArrayList<Double>();
		ArrayList<Double> rval = right.run(hm);
		ArrayList<Double> lval = left.run(hm);
		if (lval.size() == rval.size()) {
			for (int i = 0; i < lval.size(); i++) {
				r.add(lval.get(i) > rval.get(i) ? 1.0 : 0.0);
			}
			return r;
		}
		PPerlL.out.println("Error: longitud de objetos diferentes");
		return null;
	}
}

class LteCond implements Expr {
	Expr left, right;

	public LteCond(Expr l, Expr r) {
		left = l;
		right = r;
	}

	public String get() {
		return left.get() + " <= " + right.get();
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> r = new ArrayList<Double>();
		ArrayList<Double> rval = right.run(hm);
		ArrayList<Double> lval = left.run(hm);
		if (lval.size() == rval.size()) {
			for (int i = 0; i < lval.size(); i++) {
				r.add(lval.get(i) <= rval.get(i) ? 1.0 : 0.0);
			}
			return r;
		}
		PPerlL.out.println("Error: longitud de objetos diferentes");
		return null;
	}
}

class LtCond implements Expr {
	Expr left, right;

	public String get() {
		return left.get() + " < " + right.get();
	}

	public LtCond(Expr l, Expr r) {
		left = l;
		right = r;
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> r = new ArrayList<Double>();
		ArrayList<Double> rval = right.run(hm);
		ArrayList<Double> lval = left.run(hm);
		if (lval.size() == rval.size()) {
			for (int i = 0; i < lval.size(); i++) {
				r.add(lval.get(i) < rval.get(i) ? 1.0 : 0.0);
			}
			return r;
		}
		PPerlL.out.println("Error: longitud de objetos diferentes");
		return null;
	}
}

class NeqCond implements Expr {
	Expr left, right;

	public NeqCond(Expr l, Expr r) {
		left = l;
		right = r;
	}

	public String get() {
		return left.get() + " != " + right.get();
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> r = new ArrayList<Double>();
		ArrayList<Double> rval = right.run(hm);
		ArrayList<Double> lval = left.run(hm);
		if (lval.size() == rval.size()) {
			for (int i = 0; i < lval.size(); i++) {
				r.add(lval.get(i) != rval.get(i) ? 1.0 : 0.0);
			}
			return r;
		}
		PPerlL.out.println("Error: longitud de objetos diferentes");
		return null;
	}
}

class EqCond implements Expr {
	Expr left, right;

	public EqCond(Expr l, Expr r) {
		left = l;
		right = r;
	}

	public String get() {
		return left.get() + " == " + right.get();
	}

	public ArrayList<Double> run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> r = new ArrayList<Double>();
		ArrayList<Double> rval = right.run(hm);
		ArrayList<Double> lval = left.run(hm);
		if (lval.size() == rval.size()) {
			for (int i = 0; i < lval.size(); i++) {
				r.add(lval.get(i) == rval.get(i) ? 1.0 : 0.0);
			}
			return r;
		}
		PPerlL.out.println("Error: longitud de objetos diferentes");
		return null;
	}
}

class PrintStmt implements Statement {
	ArrayList<Expr> exprs = new ArrayList<Expr>();

	public PrintStmt(Expr e) {
		exprs.add(e);
	}

	public void run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> rs = new ArrayList<>();
		ArrayList<Double> r;
		String f = "";
			for (Expr e : exprs) {
			r = e.run(hm);
			if (r != null) {
				rs.addAll(r);
				DecimalFormat df = new DecimalFormat("#.##");
				for (double d : r) {
					String formatted = df.format(d);
					f += formatted + ", ";
				}
			}
		}
		if(f.length()>1){
			f = f.substring(0,f.length()-2);
		}
		PPerlL.out.println(f);
	}
}

class PasteStmt implements Statement {
	ArrayList<Expr> exprs;

	public PasteStmt(ArrayList<Expr> e) {
		exprs = e;
	}

	public void run(HashMap<String, ArrayList<Double>> hm) {
		ArrayList<Double> rs = new ArrayList<>();
		ArrayList<Double> r;
		String f = "";
		f += "\"";
		for (Expr e : exprs) {
			r = e.run(hm);
			if (r != null) {
				rs.addAll(r);
				DecimalFormat df = new DecimalFormat("#.##");
				for (double d : r) {
					String formatted = df.format(d);
					f += formatted + " ";
				}
			}
		}
		PPerlL.out.println(f.substring(0, f.length() - 1) + "\"");
	}
}

class ForStmt implements Statement {
	String id;
	Expr arr;
	StmtLst stmt;

	public ForStmt(String i, Expr a, Statement s) {
		id = i;
		arr = a;
		stmt = new StmtLst(s);
	}

	public ForStmt(String i, Expr a, StmtLst s) {
		id = i;
		arr = a;
		stmt = s;
	}

	public void run(HashMap<String, ArrayList<Double>> hm) {
		for (double d : arr.run(hm)) {
			ArrayList<Double> aux = new ArrayList<>();
			aux.add(d);
			hm.put(id, aux);
			stmt.run(hm);
		}
	}
}

/*
 * class IfStmt implements Statement { Condition cond; Statement stmt;
 * 
 * public IfStmt(Condition c, Statement s) { cond = c; stmt = s; }
 * 
 * public void run(HashMap<String, Integer> hm) { if (cond.test(hm))
 * stmt.run(hm); } }
 */

class StmtErr implements Statement {
	String err;

	public StmtErr(String sl) {
		err = sl;
	}

	public void run(HashMap<String, ArrayList<Double>> hm) {
		PPerlL.out.println(err);
	}
}

class AssignStmt implements Statement {
	HashMap<String, Expr> vals;

	public AssignStmt(HashMap<String, Expr> v) {
		vals = v;
	}

	

	public void run(HashMap<String, ArrayList<Double>> hm) {
		for (String k : vals.keySet()) {
			if(vals.get(k)!=null){
				if(k.charAt(0) == '$' && vals.get(k).get().charAt(0) == '@'){
					ArrayList<Double> r = new ArrayList();
					r.add((double)vals.get(k).run(hm).size());
					hm.put(k,r);
				}
				else
					hm.put(k,vals.get(k).run(hm));
			}
		}
	}
}

	class StmtLst {
	ArrayList<Statement> stmts;

	public StmtLst(Statement s) {
		stmts = new ArrayList<Statement>();
		stmts.add(s);
	}

	public StmtLst() {
		stmts = new ArrayList<Statement>();
	}

	public void add(Statement s) {
		stmts.add(s);
	}

	public void run(HashMap<String, ArrayList<Double>> hm) {
		for (int i = 0; i < stmts.size(); i++)
			stmts.get(i).run(hm);
	}
}

class Program {
	HashMap<String, ArrayList<Double>> vars;
	StmtLst stmt_ls;

	public Program(StmtLst ls) {
		vars = new HashMap<String, ArrayList<Double>>();
		stmt_ls = ls;
	}

	public void exec() {
		stmt_ls.run(vars);
	}
}
