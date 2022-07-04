%%
%standalone
%xstate DEFVAR
%xstate COM
%xstate XCOM
%{
	public String nombreVar = new String();
	public String valorVar = new String();
%}
%%
<YYINITIAL>{
[a-zA-Z_][a-zA-Z0-9_]*"="	{
								nombreVar=yytext().substring(0,yylength()-1);
								yybegin(DEFVAR);
							}
\$[a-zA-Z_][a-zA-Z0-9_]*	{
								System.out.print(TablaSimbolos.get(yytext()));
							}
\"[^\$]+					{
								System.out.print(yytext());
							}
.							{
								System.out.print(yytext());
							}
}
<DEFVAR>{
(\$[a-zA-Z_][a-zA-Z0-9_])*	{
								valorVar=valorVar+TablaSimbolos.get(yytext());
								TablaSimbolos.put(nombreVar, valorVar);
							}
\\\;						{
								valorVar=valorVar+yytext();
								TablaSimbolos.put(nombreVar, valorVar);
							}
[^\n\$\""\;"\\]+			{
								valorVar=valorVar+yytext();
								TablaSimbolos.put(nombreVar, yytext());
							}
\"[^\$\"]*					{
								valorVar=yytext().substring(1);
								yybegin(COM);
							}
[^]|\n						{
								yybegin(YYINITIAL);
							}
}
<COM>{

\"							{
								TablaSimbolos.put(nombreVar, valorVar);
								yybegin(DEFVAR);
							}
\$[a-zA-Z_][a-zA-Z0-9_]*	{
								valorVar=valorVar+(TablaSimbolos.get(yytext()));
							}
[^\$\n\"]					{
								valorVar=valorVar+yytext();
							}
\\\"						{
								valorVar=valorVar+yytext();
								yybegin(XCOM);
							}

}
<XCOM>{
[^\"]						{
								valorVar=valorVar+yytext();
							}
\"							{
								valorVar=valorVar+yytext();
								yybegin(COM);

							}
}
