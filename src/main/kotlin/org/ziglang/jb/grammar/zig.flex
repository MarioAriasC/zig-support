package org.ziglang.jb.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static org.ziglang.jb.psi.ZigTypes.*;

%%

%{
    public ZigLexer() {
      this((java.io.Reader)null);
    }
%}

%public
%class ZigLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
ox80_oxBF=[\200-\277]
oxF4='\364'
ox80_ox8F=[\200-\217]
oxF1_oxF3=[\361-\363]
oxF0='\360'
ox90_0xBF=[\220-\277]
oxEE_oxEF=[\356-\357]
oxED='\355'
ox80_ox9F=[\200-\237]
oxE1_oxEC=[\341-\354]
oxE0='\340'
oxA0_oxBF=[\240-\277]
oxC2_oxDF=[\302-\337]
mb_utf8_literal=
       ({oxF4} {ox80_ox8F} {ox80_oxBF} {ox80_oxBF})
     | ({oxF1_oxF3} {ox80_oxBF} {ox80_oxBF} {ox80_oxBF})
     | ({oxF0} {ox90_0xBF} {ox80_oxBF} {ox80_oxBF})
     | ({oxEE_oxEF} {ox80_oxBF} {ox80_oxBF})
     | ({oxED} {ox80_ox9F} {ox80_oxBF})
     | ({oxE1_oxEC} {ox80_oxBF} {ox80_oxBF})
     | ({oxE0} {oxA0_oxBF} {ox80_oxBF})
     | ({oxC2_oxDF} {ox80_oxBF})

ascii_char_not_nl_slash_squote=[\000-\011\013-\046\050-\133\135-\177]

WHITE_SPACE=[\s]+
CONTAINER_DOC="//!".*
LINE_COMMENT="//" [^\n]* | "////" [^\n]*
COMMENT="///".*
hex = [0-9a-fA-F]

ID=[A-Za-z_][A-Za-z0-9_]* | "@\"" {STRING_CHAR}* \"
BUILTIN_IDENTIFIER="@"[A-Za-z_][A-Za-z0-9_]*
//CHAR_ESCAPE=\\x{hex}{hex}|\\u\{{hex}+}|\\[nr\t\'\\\"]
CHAR_ESCAPE=\\x{hex}{hex}|\\u\{{hex}+}|\\[nrt\'\\\"]
char_char=[a-zA-Z_\U0000A0-\U10ffff] | {CHAR_ESCAPE} | {ascii_char_not_nl_slash_squote}
CHAR_LITERAL=\' {char_char} \'
STRING_CHAR={CHAR_ESCAPE}|[^\"\n]
STRING_LITERAL_SINGLE=\"{STRING_CHAR}*\"
LINE_STRING=(\\\\ [^\n]* [ \n]*)+

bin= [01]
bin_= '_'? {bin}
oct = [0-7]
oct_ = '_'? {oct}
hex_ = '_'? {hex}
dec = [0-9]
dec_ = '_'? {dec}

bin_int = [01] [01_]*
oct_int = [0-7] [0-7_]*
dec_int = [0-9] [0-9_]*
hex_int = [0-9a-fA-F] [0-9a-fA-F_]*
FLOAT=
 "0x" {hex_int} "." {hex_int} ([pP] [-+]? {dec_int})?
 | {dec_int} "." {dec_int} ([eE] [-+]? {dec_int})?
 | "0x" {hex_int} [pP] [-+]? {dec_int}
 | {dec_int} [eE] [-+]? {dec_int}

INTEGER= "0b" {bin_int} | "0o" {oct_int} | "0x" {hex_int} | {dec_int}

%%
<YYINITIAL> {
  {WHITE_SPACE}               { return WHITE_SPACE; }

  "struct"                   {return STRUCT;}
  "opaque"                   {return OPAQUE;}
  "enum"                     {return ENUM;}
  "union"                    {return UNION;}
  "error"                    {return ERROR;}
  "unreachable"              {return UNREACHABLE;}
  "noalias"                  {return NOALIAS;}


  "packed"                   {return PACKED;}
  "pub"                      { return PUB; }
  "fn"                       { return FN; }
  "const"                    { return CONST; }
  "var"                      { return VAR; }
  "="                        { return EQUAL; }
  "or"                       { return OR; }
  "and"                      { return AND; }
  "export"                   { return EXPORT; }
  "extern"                   { return EXTERN; }
  "inline"                   { return INLINE; }
  "noinline"                 { return NOINLINE; }
  "threadlocal"              { return THREAD_LOCAL; }
  "usingnamespace"           { return USING_NAME_SPACE; }
  "align"                    { return ALIGN; }
  "linksection"              { return LINKSECTION; }
  "callconv"                 { return CALLCONV; }
  "comptime"                 { return COMPTIME; }
  "anytype"                  { return ANY_TYPE; }
  "test"                     { return TEST; }
  "nosuspend"                { return NOSUSPEND; }
  "suspend"                  { return SUSPEND; }
  "defer"                    { return DEFER; }
  "errdefer"                 { return ERRDEFER; }
  "for"                      { return FOR; }
  "while"                    { return WHILE; }
  "switch"                   { return SWITCH; }
  "break"                    { return BREAK; }
  "continue"                 { return CONTINUE; }
  "resume"                   { return RESUME; }
  "asm"                      { return ASM; }
  "volatile"                 { return VOLATILE; }
  "anyframe"                 { return ANYFRAME; }
  "allowzero"                { return ALLOWZERO; }
  "async"                    { return ASYNC; }
  "=="                       { return EQUALEQUAL; }
  "<"                        { return LARROW; }
  ">"                        { return RARROW; }
  "!"                        { return EXCLAMATIONMARK; }
  "!="                       { return EXCLAMATIONMARKEQUAL; }
  "<="                       { return LARROWEQUAL; }
  ">="                       { return RARROWEQUAL; }
  "&"                        { return AMPERSAND; }
  "^"                        { return CARET; }
  "|"                        { return PIPE; }
  "orelse"                   { return ORELSE; }
  "catch"                    { return CATCH; }
  "<<"                       { return LARROW2; }
  ">>"                       { return RARROW2; }
  "+"                        { return PLUS; }
  "-"                        { return MINUS; }
  "++"                       { return PLUS2; }
  "+%"                       { return PLUSPERCENT; }
  "-%"                       { return MINUSPERCENT; }
  "||"                       { return PIPE2; }
  "*"                        { return ASTERISK; }
  "/"                        { return SLASH; }
  "%"                        { return PERCENT; }
  "**"                       { return ASTERISK2; }
  "*%"                       { return ASTERISKPERCENT; }
  "if"                       { return IF; }
  "return"                   { return RETURN; }
  "else"                     { return ELSE; }
  "{"                        { return LBRACE; }
  "}"                        { return RBRACE; }
  "*="                       { return ASTERISKEQUAL; }
  "/="                       { return SLASHEQUAL; }
  "%="                       { return PERCENTEQUAL; }
  "+="                       { return PLUSEQUAL; }
  "-="                       { return MINUSEQUAL; }
  "<<="                      { return LARROW2EQUAL; }
  ">>="                      { return RARROW2EQUAL; }
  "&="                       { return AMPERSANDEQUAL; }
  "^="                       { return CARETEQUAL; }
  "=>"                       { return EQUALRARROW; }
  "->"                       { return MINUSRARROW; }
  "|="                       { return PIPEEQUAL; }
  "*%="                      { return ASTERISKPERCENTEQUAL; }
  "+%="                      { return PLUSPERCENTEQUAL; }
  "-%="                      { return MINUSPERCENTEQUAL; }
  "~"                        { return TILDE; }
  "."                        { return DOT; }
  ".."                       { return DOT2; }
  "..."                      { return DOT3; }
  "try"                      { return TRY; }
  "["                        { return LBRACKET; }
  "]"                        { return RBRACKET; }
  ".*"                       { return DOTASTERISK; }
  "?"                        { return QUESTIONMARK; }
  ".?"                       { return DOTQUESTIONMARK; }
  ";"                        { return SEMICOLON; }
  "("                        { return LPAREN; }
  ")"                        { return RPAREN; }
  ","                        { return COMMA; }
  ":"                        { return COLON; }
  "await"                    { return AWAIT; }
  "+|"                       { return PLUSPIPE; }
  "-|"                       { return MINUSPIPE; }
  "*|"                       { return ASTERISKPIPE; }
  "<<|"                      { return LARROW2PIPE; }
  "+|="                      { return PLUSPIPEEQUAL; }
  "-|="                      { return MINUSPIPEEQUAL; }
  "*|="                      { return ASTERISKPIPEEQUAL; }
  "<<|="                     { return LARROW2PIPEEQUAL; }
  {CHAR_LITERAL}             { return CHAR_LITERAL; }

  {CONTAINER_DOC}            { return CONTAINER_DOC; }
  {COMMENT}                  { return COMMENT; }
  {LINE_COMMENT}             {return COMMENT;}

  {FLOAT}                    {return FLOAT;}
  {INTEGER}                  { return INTEGER; }
  {BUILTIN_IDENTIFIER}        {return BUILTIN_IDENTIFIER;}
  {ID}                         { return ID; }
  {STRING_LITERAL_SINGLE}      { return STRING_LITERAL_SINGLE; }
  {LINE_STRING}                { return LINE_STRING; }

}

[^] { return BAD_CHARACTER; }