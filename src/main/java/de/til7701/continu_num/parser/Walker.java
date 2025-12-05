package de.til7701.continu_num.parser;

import de.holube.continu_num.parser.ContinuNumParser;
import de.holube.continu_num.parser.ContinuNumParserBaseVisitor;
import de.til7701.continu_num.ast.*;
import org.antlr.v4.runtime.tree.ErrorNode;
import picocli.CommandLine;

import java.util.List;

public class Walker extends ContinuNumParserBaseVisitor<Node> {

    @Override
    public ContinuNumFile visitCompilationUnit(ContinuNumParser.CompilationUnitContext ctx) {
        CommandLine.tracer().debug("Visiting CompilationUnit %s", ctx.getText());
        List<Instruction> instructions;

        instructions = ctx.statement().stream()
                .map(instructionContext -> (Instruction) visit(instructionContext))
                .toList();

        return new ContinuNumFile(
                instructions
        );
    }

    @Override
    public Node visitSymbolDefinition(ContinuNumParser.SymbolDefinitionContext ctx) {
        CommandLine.tracer().debug("Visiting SymbolDefinition %s", ctx.getText());

        boolean mutable = ctx.MUT() != null;
        String type = ctx.TypeIdentifier().getText();
        String name = ctx.SymbolIdentifier().getText();

        Expression expression = (Expression) visit(ctx.expression());

        return new SymbolInitialization(
                mutable,
                type,
                name,
                expression
        );
    }

    @Override
    public Node visitLiteralExpression(ContinuNumParser.LiteralExpressionContext ctx) {
        CommandLine.tracer().debug("Visiting LiteralExpression %s", ctx.getText());

        if (ctx.StringLiteral() != null) {
            String value = ctx.StringLiteral().getText();
            value = value.substring(1, value.length() - 1);
            return new StringLiteralExpression(value);
        } else if (ctx.BooleanLiteral() != null) {
            String boolText = ctx.BooleanLiteral().getText();
            boolean value = switch (boolText) {
                case "true" -> true;
                case "false" -> false;
                default -> throw new ParserException("Invalid boolean literal: " + boolText);
            };
            return new BooleanLiteralExpression(value);
        }
        if (ctx.IntegerLiteral() != null) {
            String value = ctx.IntegerLiteral().getText();
            return new IntegerLiteralExpression(value);
        } else {
            throw new ParserException("Unknown literal type: " + ctx.getText());
        }
    }

    @Override
    public Node visitSymbolIdentifierExpression(ContinuNumParser.SymbolIdentifierExpressionContext ctx) {
        CommandLine.tracer().debug("Visiting SymbolIdentifierExpression %s", ctx.getText());

        String name = ctx.SymbolIdentifier().getText();
        return new SymbolExpression(name);
    }

    @Override
    public Node visitErrorNode(ErrorNode node) {
        CommandLine.tracer().debug("Visiting ErrorNode %s", node.getText());

        throw new ParserException(node.getText());
    }

}
