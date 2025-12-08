parser grammar ContinuNumParser;

options {
    tokenVocab=ContinuNumLexer;
}

compilationUnit : statement* EOF;

statement
    : LBRACE statement* RBRACE #statementList
    | MUT? typeIdentifier SymbolIdentifier ASSIGN expression SEMI #symbolDefinition
    | SymbolIdentifier ASSIGN expression SEMI #assignment
    | expression SEMI #expressionStatement
    | WHILE expression statement #whileStatement
    ;

expression
    : (IntegerLiteral | StringLiteral | BooleanLiteral) #literalExpression
    | SymbolIdentifier #symbolIdentifierExpression
    | typeIdentifier DOT SymbolIdentifier LPAREN (expression (COMMA expression)*)? RPAREN #staticMethodCall
    | expression DOT SymbolIdentifier LPAREN (expression (COMMA expression)*)? RPAREN #instanceMethodCall
    | expression binaryOperator expression #binaryOperationExpression
    | LPAREN expression RPAREN #parenExpression
    | expression LBRACK expression RBRACK #collectionAccess
    ;

binaryOperator
    : ADD
    | SUB
    | MUL
    | DIV
    | LT
    | GT
    | LE
    | GE
    | EQUAL
    | NOTEQUAL
    ;

typeIdentifier
    : TypeIdentifier
    | typeIdentifier LBRACK RBRACK
    ;
