import org.jspecify.annotations.NullMarked;

@NullMarked
module de.til7701.javelin.interpreter {
    requires de.til7701.javelin.common;
    requires de.til7701.javelin.ast;

    requires static lombok;
    requires org.jspecify;
    requires org.slf4j;

    exports de.til7701.javelin.interpreter;
}
