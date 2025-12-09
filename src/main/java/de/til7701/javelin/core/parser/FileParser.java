package de.til7701.javelin.core.parser;

import de.holube.javelin.parser.JavelinLexer;
import de.holube.javelin.parser.JavelinParser;
import de.holube.javelin.parser.JavelinParserBaseVisitor;
import de.til7701.javelin.core.ast.Ast;
import de.til7701.javelin.core.ast.Node;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ConsoleErrorListener;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.IOException;

public class FileParser {

    public Ast parse(File file) throws IOException {
        CharStream charStream = CharStreams.fromFileName(file.getAbsolutePath());
        JavelinLexer lexer = new JavelinLexer(charStream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);

        JavelinParser parser = new JavelinParser(commonTokenStream);
        parser.removeErrorListener(ConsoleErrorListener.INSTANCE);
        parser.addErrorListener(new ErrorListener());

        ParseTree cst = parser.compilationUnit();
        JavelinParserBaseVisitor<Node> listener = new Walker();
        return (Ast) listener.visit(cst);
    }

}
