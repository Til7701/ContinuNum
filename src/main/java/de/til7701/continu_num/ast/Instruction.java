package de.til7701.continu_num.ast;

public sealed interface Instruction extends Node permits
        MethodCall,
        SymbolInitialization {
}
