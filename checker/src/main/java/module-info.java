import org.jspecify.annotations.NullMarked;

@NullMarked
module de.til7701.javelin.checker {
    requires de.til7701.javelin.ast;
    requires de.til7701.javelin.common;
    requires org.jspecify;

    exports de.til7701.javelin.checker;
}
