parser grammar ContinuNumParser;

options {
    tokenVocab=ContinuNumLexer;
}

compilationUnit : statement* EOF;

statement
    : symbolDefinition SEMI
    | assignment SEMI
    | methodCall SEMI
    ;

symbolDefinition
    : MUT? TypeIdentifier SymbolIdentifier ASSIGN expression
    ;

expression
    : literalExpression
    | symbolIdentifierExpression
    | methodCall
    | expression binaryOperator expression
    | LPAREN expression RPAREN
    | expression LBRACK expression RBRACK // collection access
    ;

binaryOperator
    : ADD
    | SUB
    | MUL
    | DIV
    ;

literalExpression
    : IntegerLiteral
    | StringLiteral
    | BooleanLiteral
    ;

symbolIdentifierExpression
    : SymbolIdentifier
    ;

assignment
    : SymbolIdentifier ASSIGN expression
    ;

methodCall
    : (TypeIdentifier DOT)? SymbolIdentifier LPAREN (expression (COMMA expression)*)? RPAREN
    ;
