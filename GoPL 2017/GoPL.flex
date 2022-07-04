import java_cup.runtime.*;
import java.util.AbstractMap.SimpleEntry;
%%
%{
%}
%cup
%%
("//"[^\n]*\n)|("/*"[^"*/"]*"*/") { return new Symbol(sym.COMENT);           }
"*/"                              { return new Symbol(sym.COMENTE);          }
"package main"                    { return new Symbol(sym.PACKAGE);          }
"import \"fmt\""                  { return new Symbol(sym.IMPORTS);          }
"main()"                          { return new Symbol(sym.MAIN);             }
"main()"\n                        { return new Symbol(sym.MAINE);            }
"func"                            { return new Symbol(sym.FUNC);             }
"int"                             { return new Symbol(sym.INT);              }
"bool"                            { return new Symbol(sym.BOOL);             }
"string"                          { return new Symbol(sym.STRING);           }
"var"                             { return new Symbol(sym.VAR);              }
"const"                           { return new Symbol(sym.CONST);            }
"true"                            { return new Symbol(sym.TRUE);             }
"false"                           { return new Symbol(sym.FALSE);            }
"fmt.Print"                       { return new Symbol(sym.PRINT);            }
"fmt.Println"                     { return new Symbol(sym.PRINTLN);          }
"fmt.Printf"                      { return new Symbol(sym.PRINTF);           }
"for"                             { return new Symbol(sym.FOR);              }
"while"                           { return new Symbol(sym.WHILE);            }
0|([1-9][0-9]*)                   { return new Symbol(sym.NUM,
                                    new SimpleEntry<Integer, String>(0,
                                    yytext()));                              }
\"[^\"]*\"                        { return new Symbol(sym.STR,
                                    new SimpleEntry<Integer, String>(1,
                                    yytext().substring(1, 
                                    yytext().length()-1)));                  }
[a-zA-Z]([\_a-zA-Z0-9])*          { return new Symbol(sym.IDENT, yytext() ); }
[0-9]([\_a-zA-Z0-9])*             { return new Symbol(sym.IDENTE);           }
","                               { return new Symbol(sym.COMA);             }
";"                               { return new Symbol(sym.PC);               }
"="		                          { return new Symbol(sym.IGUAL);            }
":="		                      { return new Symbol(sym.DPIG);             }
"+"  	   	                      { return new Symbol(sym.MAS);              }
"-"	  	                          { return new Symbol(sym.MENOS);            }
"*"		                          { return new Symbol(sym.POR);              }
"/"		                          { return new Symbol(sym.DIV);              }
"%"		                          { return new Symbol(sym.MOD);              }
"=="		                      { return new Symbol(sym.EQ);               }
"!="		                      { return new Symbol(sym.NE);               }
"<"		                          { return new Symbol(sym.MENOR);            }
">"		                          { return new Symbol(sym.MAYOR);            }
"<="		                      { return new Symbol(sym.MENORIG);          }
">="		                      { return new Symbol(sym.MAYORIG);          }
"("		                          { return new Symbol(sym.AP);               }
")"		                          { return new Symbol(sym.CP);               }
"{"		                          { return new Symbol(sym.AC);               }
"}"		                          { return new Symbol(sym.CC);               }
.|\n    {  }
