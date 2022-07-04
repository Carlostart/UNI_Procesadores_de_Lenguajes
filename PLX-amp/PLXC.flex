import java_cup.runtime.*;
%%
%cup
%%
"+"                   { return new Symbol(sym.MAS); }
"++"                  { return new Symbol(sym.MASMAS); }
"-"                   { return new Symbol(sym.MENOS); }
"--"                  { return new Symbol(sym.MENOSMENOS); }
"*"                   { return new Symbol(sym.POR); }
"/"                   { return new Symbol(sym.DIV); }
"%"                   { return new Symbol(sym.MOD); }
"("                   { return new Symbol(sym.AP); }
")"                   { return new Symbol(sym.CP); }
"{"                   { return new Symbol(sym.AC); }
"}"                   { return new Symbol(sym.CC); }
";"                   { return new Symbol(sym.PYC); }
"!"                   { return new Symbol(sym.NOT); }
"~"                   { return new Symbol(sym.MIN); }
[!~][!~]+             { return new Symbol(sym.MINN); }
"="                   { return new Symbol(sym.EQ); }
"!="                  { return new Symbol(sym.NOTEQ); }
"=="                  { return new Symbol(sym.EQEQ); }
"<"                   { return new Symbol(sym.MENOR); }
"<="                  { return new Symbol(sym.MENOREQ); }
">"                   { return new Symbol(sym.MAYOR); }
">="                  { return new Symbol(sym.MAYOREQ); }
"&&"                  { return new Symbol(sym.AND); }
"||"                  { return new Symbol(sym.OR); }
"if"                  { return new Symbol(sym.IF); }
"else"                { return new Symbol(sym.ELSE); }
"for"                 { return new Symbol(sym.FOR); }
"while"               { return new Symbol(sym.WHILE); }
"do"                  { return new Symbol(sym.DO); }
"print"               { return new Symbol(sym.PRINT); }
"int"                 { return new Symbol(sym.INT); }
"char"                { return new Symbol(sym.CHAR); }
".length"             { return new Symbol(sym.LEN); }
[a-zA-Z][a-zA-Z0-9]*"["[a-zA-Z0-9]+"]"     { return new Symbol(sym.MAT, yytext() ); }
[a-zA-Z][a-zA-Z0-9]*	{ return new Symbol(sym.IDENT, yytext() ); }
0|[1-9][0-9]*				  { return new Symbol(sym.ENTERO, yytext() ); }
"'"[^\']*"'"          { return new Symbol(sym.CARACTER, yytext()); }
.|\n			            {}
