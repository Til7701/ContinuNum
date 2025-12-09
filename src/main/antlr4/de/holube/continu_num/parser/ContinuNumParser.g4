parser grammar ContinuNumParser;

options {
    tokenVocab=ContinuNumLexer;
}

compilationUnit : (statement* | typeDefinition) EOF;

statement
    : LBRACE statement* RBRACE #statementList
    | MUT? typeIdentifier SymbolIdentifier ASSIGN expression SEMI #symbolDefinition
    | expression ASSIGN expression SEMI #assignment
    | expression SEMI #expressionStatement
    | WHILE expression statement #whileStatement
    | FOR (MUT? TypeIdentifier SymbolIdentifier ASSIGN expression)? SEMI expression? SEMI expression? LBRACE statement* RBRACE #forStatement
    | RETURN expression? SEMI #returnStatement
    ;

expression
    : (IntegerLiteral | StringLiteral | BooleanLiteral) #literalExpression
    | SymbolIdentifier #symbolIdentifierExpression
    | typeIdentifier DOT SymbolIdentifier LPAREN (expression (COMMA expression)*)? RPAREN #staticMethodCall
    | expression DOT SymbolIdentifier LPAREN (expression (COMMA expression)*)? RPAREN #instanceMethodCall
    | TypeIdentifier LPAREN (expression (COMMA expression)*)? RPAREN #constructorCall
    | leftUnaryOperator expression #leftUnaryOperationExpression
    | expression rightUnaryOperator #rightUnaryOperationExpression
    | expression binaryOperator expression #binaryOperationExpression
    | LPAREN expression RPAREN #parenExpression
    | expression LBRACK expression RBRACK #collectionAccess
    | TypeIdentifier LBRACK (expression (COMMA expression)*)? RBRACK #collectionCreation
    | TypeIdentifier DOT EnumValueIdentifier #enumValueExpression
    ;

leftUnaryOperator
    : INC
    | DEC
    | SUB
    | HASH
    ;

rightUnaryOperator
    : INC
    | DEC
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

typeDefinition
    : typeModifier annotationTypeDefinition
    | typeModifier enumTypeDefinition
    | typeModifier classTypeDefinition
    ;

typeModifier
    : NATIVE
    ;

annotationTypeDefinition
    : ANNOTATION SEMI annotationFieldDefinition*
    ;

annotationFieldDefinition
    : typeIdentifier SymbolIdentifier SEMI
    ;

enumTypeDefinition
    : ENUM SEMI EnumValueIdentifier (COMMA EnumValueIdentifier)* COMMA?
    ;

classTypeDefinition
    : CLASS SEMI (EXTENDS TypeIdentifier (COMMA TypeIdentifier))? fieldDefinition* constructorDefinition* methodDefinition*
    ;

fieldDefinition
    : fieldModifier* typeIdentifier SymbolIdentifier SEMI #uninitializedFieldDefinition
    | fieldModifier* typeIdentifier SymbolIdentifier ASSIGN expression SEMI #initializedFieldDefinition
    ;

fieldModifier
    : STATIC
    | MUT
    | PUBLIC
    | PRIVATE
    | GET
    | SET
    ;

constructorDefinition
    : methodModifier* TypeIdentifier LPAREN (parameter (COMMA parameter)*)? RPAREN statement
    ;

methodDefinition
    : annotation* methodModifier* TypeIdentifier? SymbolIdentifier LPAREN (parameter (COMMA parameter)*)? RPAREN (ARROW typeIdentifier)? statement
    ;

methodModifier
    : STATIC
    | NATIVE
    | PUBLIC
    | PRIVATE
    ;

parameter
    : MUT? typeIdentifier SymbolIdentifier
    ;

annotation
    : AT TypeIdentifier (LPAREN (elementValuePair (COMMA elementValuePair)*)? RPAREN)?
    ;

elementValuePair
    : SymbolIdentifier ASSIGN expression
    ;
