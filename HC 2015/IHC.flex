import java_cup.runtime.*;
%%
%cup
%%
"["		                    { return new Symbol(sym.AC); }
"]"		                    { return new Symbol(sym.CC); }
[A-Z][1-9]		            { return new Symbol(sym.IDENT, yytext() ); }
0|[1-9][0-9]*             { return new Symbol(sym.INT, Integer.parseInt(yytext()) ); }
":"		                    { return new Symbol(sym.DP); }
"="		                    { return new Symbol(sym.EQ); }
"+"		                    { return new Symbol(sym.MAS); }
"-"		                    { return new Symbol(sym.MENOS); }
"*"		                    { return new Symbol(sym.POR); }
"/"		                    { return new Symbol(sym.DIV); }
"^"                       { return new Symbol(sym.EXP); }
PRINT		                  { return new Symbol(sym.PRINT); }
"("		                    { return new Symbol(sym.AP); }
")"		                    { return new Symbol(sym.CP); }
SUMA                      { return new Symbol(sym.SUM); }
PROMEDIO	                { return new Symbol(sym.PROM); }
";"		                    { return new Symbol(sym.PC); }
VERDADERO                 { return new Symbol(sym.V); }
FALSO                     { return new Symbol(sym.F); }
"<"		                    { return new Symbol(sym.MEN); }
">"		                    { return new Symbol(sym.MAY); }
"Y"                       { return new Symbol(sym.Y); }
"O"                       { return new Symbol(sym.O); }
"NO"                       { return new Symbol(sym.NO); }
.|\n						          {  }
