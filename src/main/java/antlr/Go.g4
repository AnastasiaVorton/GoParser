/**
 * The grammal for Go language is defined (and optimized for our purposes) referencing the official Go documentation
 * https://golang.org/ref/spec
 */
grammar Go;

@parser::members {
    /**
     * Returns {@code true} iff on the current index of the parser's
     * token stream a token exists on the {@code HIDDEN} channel which
     * either is a line terminator, or is a multi line comment that
     * contains a line terminator.
     *
     * @return {@code true} iff on the current index of the parser's
     * token stream a token exists on the {@code HIDDEN} channel which
     * either is a line terminator, or is a multi line comment that
     * contains a line terminator.
     */
    private boolean lineTerminatorAhead() {
        // Get the token ahead of the current index.
        int possibleIndexEosToken = this.getCurrentToken().getTokenIndex() - 1;
        Token ahead = _input.get(possibleIndexEosToken);
        if (ahead.getChannel() != Lexer.HIDDEN) {
            // We're only interested in tokens on the HIDDEN channel.
            return false;
        }

        if (ahead.getType() == TERMINATOR) {
            // There is definitely a line terminator ahead.
            return true;
        }

        if (ahead.getType() == WS) {
            // Get the token ahead of the current whitespaces.
            possibleIndexEosToken = this.getCurrentToken().getTokenIndex() - 2;
            ahead = _input.get(possibleIndexEosToken);
        }

        // Get the token's text and type.
        String text = ahead.getText();
        int type = ahead.getType();

        // Check if the token is, or contains a line terminator.
        return (type == COMMENT && (text.contains("\r") || text.contains("\n"))) ||
                (type == TERMINATOR);
    }

     /**
     * Returns {@code true} if no line terminator exists between the specified
     * token offset and the prior one on the {@code HIDDEN} channel.
     *
     * @return {@code true} if no line terminator exists between the specified
     * token offset and the prior one on the {@code HIDDEN} channel.
     */
    private boolean noTerminatorBetween(int tokenOffset) {
        BufferedTokenStream stream = (BufferedTokenStream)_input;
        List<Token> tokens = stream.getHiddenTokensToLeft(stream.LT(tokenOffset).getTokenIndex());

        if (tokens == null) {
            return true;
        }

        for (Token token : tokens) {
            if (token.getText().contains("\n"))
                return false;
        }

        return true;
    }

     /**
     * Returns {@code true} if no line terminator exists after any encounterd
     * parameters beyond the specified token offset and the next on the
     * {@code HIDDEN} channel.
     *
     * @return {@code true} if no line terminator exists after any encounterd
     * parameters beyond the specified token offset and the next on the
     * {@code HIDDEN} channel.
     */
    private boolean noTerminatorAfterParams(int tokenOffset) {
        BufferedTokenStream stream = (BufferedTokenStream)_input;
        int leftParams = 1;
        int rightParams = 0;
        String value;
        if (stream.LT(tokenOffset).getText().equals("(")) {
            // Scan past parameters
            while (leftParams != rightParams) {
                tokenOffset++;
                value = stream.LT(tokenOffset).getText();
                if (value.equals("(")) {
                    leftParams++;
                }
                else if (value.equals(")")) {
                    rightParams++;
                }
            }

            tokenOffset++;
            return noTerminatorBetween(tokenOffset);
        }

        return true;
    }
}

@lexer::members {
    // The most recently produced token.
    private Token lastToken = null;
    /**
     * Return the next token from the character stream and records this last
     * token in case it resides on the default channel. This recorded token
     * is used to determine when the lexer could possibly match a regex
     * literal.
     *
     * @return the next token from the character stream.
     */
    @Override
    public Token nextToken() {
        // Get the next token.
        Token next = super.nextToken();
        if (next.getChannel() == Token.DEFAULT_CHANNEL) {
            // Keep track of the last token on the default channel.
            this.lastToken = next;
        }

        return next;
    }
}

//SourceFile       = PackageClause ";" { ImportDecl ";" } { TopLevelDecl ";" } .
sourceFile
    : packageClause eos ( importDecl eos )* ( topLevelDecl eos )*
    ;

//PackageClause  = "package" PackageName .
//PackageName    = identifier .
packageClause
    : 'package' IDENTIFIER
    ;

importDecl
    : 'import' ( importSpec | '(' ( importSpec eos )* ')' )
    ;

importSpec
    : ( '.' | IDENTIFIER )? importPath
    ;

importPath
    : STRING_LIT
    ;

//TopLevelDecl  = Declaration | FunctionDecl | MethodDecl .
topLevelDecl
    : declaration
    | functionDecl
    | methodDecl
    ;

//Declaration   = ConstDecl | TypeDecl | VarDecl .
declaration
    : constDecl
    | typeDecl
    | varDecl
    ;


//ConstDecl      = "const" ( ConstSpec | "(" { ConstSpec ";" } ")" ) .
constDecl
    : 'const' ( constSpec | '(' ( constSpec eos )* ')' )
    ;

//ConstSpec      = IdentifierList [ [ Type ] "=" ExpressionList ] .
constSpec
    : identifierList ( type? '=' expressionList )?
    ;

//
//IdentifierList = identifier { "," identifier } .
identifierList
    : IDENTIFIER ( ',' IDENTIFIER )*
    ;

//ExpressionList = Expression { "," Expression } .
expressionList
    : expression ( ',' expression )*
    ;

//TypeDecl     = "type" ( TypeSpec | "(" { TypeSpec ";" } ")" ) .
typeDecl
    : 'type' ( typeSpec | '(' ( typeSpec eos )* ')' )
    ;

//TypeSpec     = identifier Type .
typeSpec
    : IDENTIFIER type
    ;


// Function declarations

//FunctionDecl = "func" FunctionName ( Function | Signature ) .
//FunctionName = identifier .
//Function     = Signature FunctionBody .
//FunctionBody = Block .
functionDecl
    : 'func' IDENTIFIER ( function | signature )
    ;

function
    : signature block
    ;

//MethodDecl   = "func" Receiver MethodName ( Function | Signature ) .
//Receiver     = Parameters .
methodDecl
    : 'func' receiver IDENTIFIER ( function | signature )
    ;

receiver
    : parameters
    ;

//VarDecl     = "var" ( VarSpec | "(" { VarSpec ";" } ")" ) .
//VarSpec     = IdentifierList ( Type [ "=" ExpressionList ] | "=" ExpressionList ) .
varDecl
    : 'var' ( varSpec | '(' ( varSpec eos )* ')' )
    ;

varSpec
    : identifierList ( type ( '=' expressionList )? | '=' expressionList )
    ;


//Block = "{" StatementList "}" .
block
    : '{' statementList '}'
    ;

//StatementList = { Statement ";" } .
statementList
    : ( statement eos )*
    ;

statement
    : declaration
    | labeledStmt
    | simpleStmt
    | goStmt
    | returnStmt
    | breakStmt
    | continueStmt
    | gotoStmt
    | fallthroughStmt
    | block
    | ifStmt
    | switchStmt
    | selectStmt
    | forStmt
    | deferStmt
	;

//SimpleStmt = EmptyStmt | ExpressionStmt | SendStmt | IncDecStmt | Assignment | ShortVarDecl .
simpleStmt
    : sendStmt
    | expressionStmt
    | incDecStmt
    | assignment
    | shortVarDecl
    | emptyStmt
    ;

//ExpressionStmt = Expression .
expressionStmt
    : expression
    ;

//SendStmt = Channel "<-" Expression .
//Channel  = Expression .
sendStmt
    : expression '<-' expression
    ;

//IncDecStmt = Expression ( "++" | "--" ) .
incDecStmt
    : expression ( '++' | '--' )
    ;

//Assignment = ExpressionList assign_op ExpressionList .
assignment
    : expressionList assign_op expressionList
    ;

//assign_op = [ add_op | mul_op ] "=" .
assign_op
    : ('+' | '-' | '|' | '^' | '*' | '/' | '%' | '<<' | '>>' | '&' | '&^')? '='
    ;


//ShortVarDecl = IdentifierList ":=" ExpressionList .
shortVarDecl
    : identifierList ':=' expressionList
    ;

emptyStmt
    : ';'
    ;

//LabeledStmt = Label ":" Statement .
//Label       = identifier .
labeledStmt
    : IDENTIFIER ':' statement
    ;

//ReturnStmt = "return" [ ExpressionList ] .
returnStmt
    : 'return' expressionList?
    ;

//BreakStmt = "break" [ Label ] .
breakStmt
    : 'break' IDENTIFIER?
    ;

//ContinueStmt = "continue" [ Label ] .
continueStmt
    : 'continue' IDENTIFIER?
    ;

//GotoStmt = "goto" Label .
gotoStmt
    : 'goto' IDENTIFIER
    ;

//FallthroughStmt = "fallthrough" .
fallthroughStmt
    : 'fallthrough'
    ;

//DeferStmt = "defer" Expression .
deferStmt
    : 'defer' expression
    ;

//IfStmt = "if" [ SimpleStmt ";" ] Expression Block [ "else" ( IfStmt | Block ) ] .
ifStmt
    : 'if' (simpleStmt ';')? expression block ( 'else' ( ifStmt | block ) )?
    ;

//SwitchStmt = ExprSwitchStmt | TypeSwitchStmt .
switchStmt
    : exprSwitchStmt | typeSwitchStmt
    ;

//ExprSwitchStmt = "switch" [ SimpleStmt ";" ] [ Expression ] "{" { ExprCaseClause } "}" .
//ExprCaseClause = ExprSwitchCase ":" StatementList .
//ExprSwitchCase = "case" ExpressionList | "default" .
exprSwitchStmt
    : 'switch' ( simpleStmt ';' )? expression? '{' exprCaseClause* '}'
    ;

exprCaseClause
    : exprSwitchCase ':' statementList
    ;

exprSwitchCase
    : 'case' expressionList | 'default'
    ;

//TypeSwitchStmt  = "switch" [ SimpleStmt ";" ] TypeSwitchGuard "{" { TypeCaseClause } "}" .
//TypeSwitchGuard = [ identifier ":=" ] PrimaryExpr "." "(" "type" ")" .
//TypeCaseClause  = TypeSwitchCase ":" StatementList .
//TypeSwitchCase  = "case" TypeList | "default" .
//TypeList        = Type { "," Type } .
typeSwitchStmt
    : 'switch' ( simpleStmt ';' )? typeSwitchGuard '{' typeCaseClause* '}'
    ;
typeSwitchGuard
    : ( IDENTIFIER ':=' )? primaryExpr '.' '(' 'type' ')'
    ;
typeCaseClause
    : typeSwitchCase ':' statementList
    ;
typeSwitchCase
    : 'case' typeList | 'default'
    ;
typeList
    : type ( ',' type )*
    ;


//SelectStmt = "select" "{" { CommClause } "}" .
//CommClause = CommCase ":" StatementList .
//CommCase   = "case" ( SendStmt | RecvStmt ) | "default" .
//RecvStmt   = [ ExpressionList "=" | IdentifierList ":=" ] RecvExpr .
//RecvExpr   = Expression .
selectStmt
    : 'select' '{' commClause* '}'
    ;
commClause
    : commCase ':' statementList
    ;
commCase
    : 'case' ( sendStmt | recvStmt ) | 'default'
    ;
recvStmt
    : ( expressionList '=' | identifierList ':=' )? expression
    ;

//ForStmt = "for" [ Condition | ForClause | RangeClause ] Block .
//Condition = Expression .
forStmt
    : 'for' ( expression | forClause | rangeClause )? block
    ;

//ForClause = [ InitStmt ] ";" [ Condition ] ";" [ PostStmt ] .
//InitStmt = SimpleStmt .
//PostStmt = SimpleStmt .
forClause
    : simpleStmt? ';' expression? ';' simpleStmt?
    ;


//RangeClause = [ ExpressionList "=" | IdentifierList ":=" ] "range" Expression .
rangeClause
    : (expressionList '=' | identifierList ':=' )? 'range' expression
    ;

//GoStmt = "go" Expression .
goStmt
    : 'go' expression
    ;

//Type      = TypeName | TypeLit | "(" Type ")" .
type
    : typeName
    | typeLit
    | '(' type ')'
    ;

//TypeName  = identifier | QualifiedIdent .
typeName
    : IDENTIFIER
    | qualifiedIdent
    ;

//TypeLit   = ArrayType | StructType | PointerType | FunctionType | InterfaceType |
//	    SliceType | MapType | ChannelType .
typeLit
    : arrayType
    | structType
    | pointerType
    | functionType
    | interfaceType
    | sliceType
    | mapType
    | channelType
    ;


arrayType
    : '[' arrayLength ']' elementType
    ;

arrayLength
    : expression
    ;

elementType
    : type
    ;

//PointerType = "*" BaseType .
//BaseType    = Type .
pointerType
    : '*' type
    ;

//InterfaceType      = "interface" "{" { MethodSpec ";" } "}" .
//MethodSpec         = MethodName Signature | InterfaceTypeName .
//MethodName         = identifier .
//InterfaceTypeName  = TypeName .
interfaceType
    : 'interface' '{' ( methodSpec eos )* '}'
    ;

//SliceType = "[" "]" ElementType .
sliceType
    : '[' ']' elementType
    ;

//MapType     = "map" "[" KeyType "]" ElementType .
//KeyType     = Type .
mapType
    : 'map' '[' type ']' elementType
    ;

//ChannelType = ( "chan" | "chan" "<-" | "<-" "chan" ) ElementType .
channelType
    : ( 'chan' | 'chan' '<-' | '<-' 'chan' ) elementType
    ;

methodSpec
    : {noTerminatorAfterParams(2)}? IDENTIFIER parameters result
    | typeName
    | IDENTIFIER parameters
    ;


//FunctionType   = "func" Signature .
//Signature      = Parameters [ Result ] .
//Result         = Parameters | Type .
//Parameters     = "(" [ ParameterList [ "," ] ] ")" .
//ParameterList  = ParameterDecl { "," ParameterDecl } .
//ParameterDecl  = [ IdentifierList ] [ "..." ] Type .
functionType
    : 'func' signature
    ;

signature
    : {noTerminatorAfterParams(1)}? parameters result
    | parameters
    ;

result
    : parameters
    | type
    ;

parameters
    : '(' ( parameterList ','? )? ')'
    ;

parameterList
    : parameterDecl ( ',' parameterDecl )*
    ;

parameterDecl
    : identifierList? '...'? type
    ;

// TODO current pointer
// OPERANDS

// Operand
operand
    : literal
    | operandName
    | methodExpr
    | '(' expression ')'
    ;

// Literal
literal
    : basicLit
    | compositeLit
    | functionLit
    ;

// Basic Literal
basicLit
    : INT_LIT
    | FLOAT_LIT
    | IMAGINARY_LIT
    | RUNE_LIT
    | STRING_LIT
    ;

// Operand Name
operandName
    : IDENTIFIER
    | qualifiedIdent
    ;


// Qualified identifiers - identifierы qualified with a package name prefix.
qualifiedIdent
    : IDENTIFIER '.' IDENTIFIER
    ;

compositeLit
    : literalType literalValue
    ;

literalType
    : structType
    | arrayType
    | '[' '...' ']' elementType
    | sliceType
    | mapType
    | typeName
    ;

literalValue
    : '{' ( elementList ','? )? '}'
    ;

elementList
    : keyedElement (',' keyedElement)*
    ;

keyedElement
    : (key ':')? element
    ;

key
    : IDENTIFIER
    | expression
    | literalValue
    ;

element
    : expression
    | literalValue
    ;


// Struct types
structType
    : 'struct' '{' ( fieldDecl eos )* '}'
    ;

fieldDecl
    : ({noTerminatorBetween(2)}? identifierList type | anonymousField) STRING_LIT?
    ;

anonymousField
    : '*'? typeName
    ;


//Function Literals
functionLit
    : 'func' function
    ;

// Primary expression
primaryExpr
    : operand
    | conversion
    | primaryExpr selector
    | primaryExpr index
    | primaryExpr slice
    | primaryExpr typeAssertion
	| primaryExpr arguments
    ;

// Selector
selector
    : '.' IDENTIFIER
    ;

//Index
index
    : '[' expression ']'
    ;

//Slice
slice
    : '[' (( expression? ':' expression? ) | ( expression? ':' expression ':' expression )) ']'
    ;

typeAssertion
    : '.' '(' type ')'
    ;

arguments
    : '(' ( ( expressionList | type ( ',' expressionList )? ) '...'? ','? )? ')'
    ;

// Method Expression
methodExpr
    : receiverType '.' IDENTIFIER
    ;

// Receiver type
receiverType
    : typeName
    | '(' '*' typeName ')'
    | '(' receiverType ')'
    ;


// Expressions
// Expression - unary or with binary operator.
expression
    : unaryExpr
    | expression ('||' | '&&' | '==' | '!=' | '<' | '<=' | '>' | '>=' | '+' | '-' | '|' | '^' | '*' | '/' | '%' | '<<' | '>>' | '&' | '&^') expression
    ;

// Unary Expression
unaryExpr
    : primaryExpr
    | ('+'|'-'|'!'|'^'|'*'|'&'|'<-') unaryExpr
    ;

// Conversion = casting to some type.
conversion
    : type '(' expression ','? ')'
    ;

eos
    : ';'
    | EOF
    | {lineTerminatorAhead()}?
    | {_input.LT(1).getText().equals("}") }?
    ;

// TOKENS FOR LEXER

// Identifiers
IDENTIFIER
    : LETTER ( LETTER | DECIMAL_DIGIT )*
    ;

// Keywords
KEYWORD
    : 'break'
    | 'default'
    | 'func'
    | 'interface'
    | 'select'
    | 'case'
    | 'defer'
    | 'go'
    | 'map'
    | 'struct'
    | 'chan'
    | 'else'
    | 'goto'
    | 'package'
    | 'switch'
    | 'const'
    | 'fallthrough'
    | 'if'
    | 'range'
    | 'type'
    | 'continue'
    | 'for'
    | 'import'
    | 'return'
    | 'var'
    ;


// Operators

// Binary operators - "||" | "&&" | rel_op | add_op | mul_op.
BINARY_OP
    : '||' | '&&' | REL_OP | ADD_OP | MUL_OP
    ;

// Rel operators -  "==" | "!=" | "<" | "<=" | ">" | ">=".
fragment REL_OP
    : '=='
    | '!='
    | '<'
    | '<='
    | '>'
    | '>='
    ;

// Add operators - "+" | "-" | "|" | "^".
fragment ADD_OP
    : '+'
    | '-'
    | '|'
    | '^'
    ;

// Mul operators - "*" | "/" | "%" | "<<" | ">>" | "&" | "&^".
fragment MUL_OP
    : '*'
    | '/'
    | '%'
    | '<<'
    | '>>'
    | '&'
    | '&^'
    ;

// Unary operators - "+" | "-" | "!" | "^" | "*" | "&" | "<-".
fragment UNARY_OP
    : '+'
    | '-'
    | '!'
    | '^'
    | '*'
    | '&'
    | '<-'
    ;


// Integer literals
INT_LIT
    : DECIMAL_LIT
    | OCTAL_LIT
    | HEX_LIT
    ;

// Decimal literal
fragment DECIMAL_LIT
    : [1-9] DECIMAL_DIGIT*
    ;

// Octal literal
fragment OCTAL_LIT
    : '0' OCTAL_DIGIT*
    ;

// Hexadecimal literal
fragment HEX_LIT
    : '0' ( 'x' | 'X' ) HEX_DIGIT+
    ;


// Floating-point literals
FLOAT_LIT
    : DECIMALS '.' DECIMALS? EXPONENT?
    | DECIMALS EXPONENT
    | '.' DECIMALS EXPONENT?
    ;

// Decimals
fragment DECIMALS
    : DECIMAL_DIGIT+
    ;

// Exponent
fragment EXPONENT
    : ( 'e' | 'E' ) ( '+' | '-' )? DECIMALS
    ;

// Imaginary literals
IMAGINARY_LIT
    : (DECIMALS | FLOAT_LIT) 'i'
    ;


// Rune literals - rune constants, integer values identifying a Unicode code point.

// Rune literal - encapsulated in single backquotes.
RUNE_LIT
    : '\'' ( UNICODE_VALUE | BYTE_VALUE ) '\''
    ;

// Unicode value
fragment UNICODE_VALUE
    : UNICODE_CHAR
    | LITTLE_U_VALUE
    | BIG_U_VALUE
    | ESCAPED_CHAR
    ;

// Byte value - octal or hex byte value.
fragment BYTE_VALUE
    : OCTAL_BYTE_VALUE | HEX_BYTE_VALUE
    ;

// Octal byte value
fragment OCTAL_BYTE_VALUE
    : '\\' OCTAL_DIGIT OCTAL_DIGIT OCTAL_DIGIT
    ;

// Hex byte value
fragment HEX_BYTE_VALUE
    : '\\' 'x' HEX_DIGIT HEX_DIGIT
    ;

// Little u value - starts with u
LITTLE_U_VALUE
    : '\\u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    ;

// Big u value - starts with U
BIG_U_VALUE
    : '\\U' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    ;

// Escaped char - certain single-character escapes that represent special values.
fragment ESCAPED_CHAR
    : '\\' ( 'a' | 'b' | 'f' | 'n' | 'r' | 't' | 'v' | '\\' | '\'' | '"' )
    ;


// String literals
STRING_LIT
    : RAW_STRING_LIT
    | INTERPRETED_STRING_LIT
    ;

// Raw string literal - a string encapsulated within single backquotes.
fragment RAW_STRING_LIT
    : '`' ( UNICODE_CHAR | NEWLINE | [~`] )*? '`'
    ;

// Interpreted string literal - a string encapsulated within double backquotes.
fragment INTERPRETED_STRING_LIT
    : '"' ( '\\"' | UNICODE_VALUE | BYTE_VALUE )*? '"'
    ;

// Latin letter (both upper case and regular)
fragment LETTER
    : [A-Za-z]
    | '_'
    ;

// Decimal digit
fragment DECIMAL_DIGIT
    : [0-9]
    ;

// Octal digit
fragment OCTAL_DIGIT
    : [0-7]
    ;

// Hexadecimal digit
fragment HEX_DIGIT
    : [0-9a-fA-F]
    ;

// Newline
fragment NEWLINE
    : [\n]
    ;

// TODO decide to use or to discard
// Unicode_char = /* an arbitrary Unicode code point except newline */ .
fragment UNICODE_CHAR   : ~[\u000A] ;

// Whitespace and comments -> tokens to be ignored and not passed to the parser
WHITE_SPACE  :  [ \t]+ -> channel(HIDDEN)
    ;

COMMENT // Multiline
    :   '/*' .*? '*/' -> channel(HIDDEN)
    ;

// TODO use/delete?
TERMINATOR
	: [\r\n]+ -> channel(HIDDEN)
	;


LINE_COMMENT // Single line
    :   '//' ~[\r\n]* -> skip
    ;