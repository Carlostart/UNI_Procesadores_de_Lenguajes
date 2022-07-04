%%
%standalone
%xstate DEFVAR
%xstate COM
%xstate XCOM
%xstate PRINT
%xstate PCOM
%xstate XPCOM

%{
	public String nombreVar = new String();
	public String valorVar = new String();
%}
%%
<YYINITIAL>{
  class" "+[a-zA-Z_][a-zA-Z0-9_]*  {
    System.out.print(yytext()+"_rmj");
  }
  String" "+[a-zA-Z_][a-zA-Z0-9_]*" "*=	{
  	nombreVar=yytext().substring(7,yylength()-1);
    yybegin(DEFVAR);
  }
  System.out.print(ln)?" "*\(  {
    System.out.print(yytext()+'"');
    yybegin(PRINT);
  }
  [^]	{
  	System.out.print(yytext());
  }
}
<DEFVAR>{
  \"	{
    yybegin(COM);
  }
  [a-zA-Z_][a-zA-Z0-9_]* {
    valorVar=valorVar+TablaSimbolos.get(yytext());
    TablaSimbolos.put(nombreVar, valorVar);
		System.out.print(valorVar);
  }
  ;	{
    valorVar = "";
  	yybegin(YYINITIAL);
  }
  [^] {
  }
}
<COM>{
  [^(\"\\)]  {
    valorVar = valorVar + yytext();
    TablaSimbolos.put(nombreVar, valorVar);
		System.out.print(nombreVar+"->"+valorVar);
  }
  (\\.)+ {
    valorVar = valorVar + yytext();
    TablaSimbolos.put(nombreVar, valorVar);
  }
  \"  {
    yybegin(DEFVAR);
  }
}
<PRINT>{
  \"  {
    yybegin(PCOM);
  }
  [a-zA-Z_][a-zA-Z0-9_]*	{
  	System.out.print(TablaSimbolos.get(yytext()));
  }
  \);  {
  	System.out.print('"'+yytext());
  	yybegin(YYINITIAL);
  }
  [^] {
  }
}
<PCOM>{
  [^\"\\]*	{
    System.out.print(yytext());
  }
  \\.|\\\\  {
    System.out.print(yytext());
  }
  \"  {
    yybegin(PRINT);
  }
}
