import java_cup.runtime.*;
import java.util.AbstractMap.SimpleEntry;
%%
%{
%}
%cup
%%
"TRUE"                            { return new Symbol(sym.TRUE);             }
"FALSE"                           { return new Symbol(sym.FALSE);            }
"print"                           { return new Symbol(sym.PRINT);            }
"paste"                           { return new Symbol(sym.PASTE);            }
"for"                             { return new Symbol(sym.FOR);              }
"in"                              { return new Symbol(sym.IN);              }
"as.double"                       { return new Symbol(sym.ASDOUBLE);         }
(0|([1-9][0-9]*))(\.[0-9]*)?      { return new Symbol(sym.NUM, 
                                    Double.parseDouble(yytext()));           }
[a-zA-Z]([\_\.a-zA-Z0-9])*|\.[\_\.a-zA-Z][\_\.a-zA-Z0-9]*
                                  { return new Symbol(sym.IDENT, yytext() ); }
([0-9][\_\.a-zA-Z0-9]*)|(\.[0-9][\_\.a-zA-Z0-9]*)
                                  { return new Symbol(sym.IDENTE, yytext()); }
","                               { return new Symbol(sym.COMA);             }
";"                               { return new Symbol(sym.PC);               }
"="		                          { return new Symbol(sym.IGUAL);            }
"<-"		                      { return new Symbol(sym.ASIGI);            }
"->"		                      { return new Symbol(sym.ASIGD);            }
"+"  	   	                      { return new Symbol(sym.MAS);              }
"-"	  	                          { return new Symbol(sym.MENOS);            }
"*"		                          { return new Symbol(sym.POR);              }
"/"		                          { return new Symbol(sym.DIV);              }
"^"		                          { return new Symbol(sym.EXP);              }
"=="		                      { return new Symbol(sym.EQ);               }
"!="		                      { return new Symbol(sym.NE);               }
"!"		                          { return new Symbol(sym.NOT);              }
"<"		                          { return new Symbol(sym.MENOR);            }
">"		                          { return new Symbol(sym.MAYOR);            }
"<="		                      { return new Symbol(sym.MENORIG);          }
">="		                      { return new Symbol(sym.MAYORIG);          }
"&"		                          { return new Symbol(sym.AND);              }
"|"		                          { return new Symbol(sym.OR);               }
"("		                          { return new Symbol(sym.AP);               }
"c("		                      { return new Symbol(sym.AA);               }
")"		                          { return new Symbol(sym.CP);               }
"{"		                          { return new Symbol(sym.AL);               }
"}"		                          { return new Symbol(sym.CL);               }
"["                               { return new Symbol(sym.AC);               }
"]"                               { return new Symbol(sym.CC);               }
":"                               { return new Symbol(sym.DP);               }
(\#[^\n]*\n)                      { return new Symbol(sym.COMENT);           }
\n                                { return new Symbol(sym.EOL);              }
[^]|\n    {  }
