parser grammar ContinuNumParser;

options {
    tokenVocab=ContinuNumLexer;
}

compilationUnit : statement* EOF;

statement
    : symbolDefinition
    ;

symbolDefinition
    : MUT? TypeIdentifier SymbolIdentifier ASSIGN expression SEMI
    ;

expression
    : literalExpression
    | symbolIdentifierExpression
    ;

literalExpression
    : IntegerLiteral
    | StringLiteral
    | BooleanLiteral
    ;

symbolIdentifierExpression
    : SymbolIdentifier
    ;
