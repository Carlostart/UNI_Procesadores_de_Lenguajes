import java_cup.runtime.*;
%%
%cup
%%
\![^\n]*\n                    { return new Symbol(sym.COMENT);           }
ARG[1-9]([0-9])?          	  { return new Symbol(sym.IDENTR, yytext()); }
ABS                           { return new Symbol(sym.ABS);              }
SIGN                          { return new Symbol(sym.SIGN);             }
EQ                            { return new Symbol(sym.EQ);               }
NE                            { return new Symbol(sym.NE);               }
LT                            { return new Symbol(sym.LT);               }
GT                            { return new Symbol(sym.GT);               }
LE                            { return new Symbol(sym.LE);               }
GE                            { return new Symbol(sym.GE);               }
ABLT                          { return new Symbol(sym.ABLT);             }
ABGT                          { return new Symbol(sym.ABGT);             }
THEN                          { return new Symbol(sym.THEN);             }
\'[^\']{0,8}\'                { return new Symbol(sym.STR, yytext() );   }
\'[^\']{9,999}\'              { return new Symbol(sym.STRL);             }
\*SET                         { return new Symbol(sym.SET);              }
\*STATUS                      { return new Symbol(sym.STATUS);           }
\*IF                          { return new Symbol(sym.IF);               }
\*ELSE                        { return new Symbol(sym.ELSE);             }
\*ELSEIF                      { return new Symbol(sym.ELSEIF);           }
\*ENDIF                       { return new Symbol(sym.ENDIF);            }
","                           { return new Symbol(sym.COMA);             }
"="		                      { return new Symbol(sym.IGUAL);            }
"+"  	  	                  { return new Symbol(sym.MAS);              }
"-"	  	                      { return new Symbol(sym.MENOS);            }
"*"		                      { return new Symbol(sym.POR);              }
"**"		                  { return new Symbol(sym.EXP);              }
"/"		                      { return new Symbol(sym.DIV);              }
"("		                      { return new Symbol(sym.AP);               }
")"		                      { return new Symbol(sym.CP);               }
[a-zA-Z]([\_a-zA-Z0-9]){0,31}	                               { return new Symbol(sym.IDENT, yytext() ); }
(0|(\-?[1-9][0-9]*))(\.[0-9]*)?(E(\-)?[1-9][0-9]*(.[0-9]*)?)?  { return new Symbol(sym.NUM, yytext());    }
[a-zA-Z][\_a-zA-Z0-9]{32,999}                                  { return new Symbol(sym.IDENTL);           }
\_[\_a-zA-Z0-9]{0,31}	                                       { return new Symbol(sym.IDENTB, yytext()); }
[0-9][\_a-zA-Z0-9]{0,31}	                                   { return new Symbol(sym.IDENTN);           }
[a-zA-Z]([\_a-zA-Z0-9]|[^\_a-zA-Z0-9\!,=\n" "\(\)+-/*]){0,31}  { return new Symbol(sym.IDENTC,yytext());  }
.|\n    {  }
