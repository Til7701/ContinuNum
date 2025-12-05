parser grammar ContinuNumParser;

options {
    tokenVocab=ContinuNumLexer;
}

compilationUnit : statement* EOF;

statement
    : symbolDefinition SEMI
    | methodCall SEMI
    ;

symbolDefinition
    : MUT? TypeIdentifier SymbolIdentifier ASSIGN expression
    ;

expression
    : literalExpression
    | symbolIdentifierExpression
    | methodCall
    ;

literalExpression
    : IntegerLiteral
    | StringLiteral
    | BooleanLiteral
    ;

symbolIdentifierExpression
    : SymbolIdentifier
    ;

methodCall
    : (TypeIdentifier DOT)? SymbolIdentifier LPAREN (expression (COMMA expression)*)? RPAREN
    ;
