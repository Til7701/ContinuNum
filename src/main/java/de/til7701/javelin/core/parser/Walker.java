package de.til7701.javelin.core.parser;

import de.holube.javelin.parser.JavelinParser;
import de.holube.javelin.parser.JavelinParserBaseVisitor;
import de.til7701.javelin.core.ast.*;
import de.til7701.javelin.core.reflect.Type;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import picocli.CommandLine;

import java.util.List;
import java.util.Optional;

public class Walker extends JavelinParserBaseVisitor<Node> {

    @Override
    public Ast visitCompilationUnit(JavelinParser.CompilationUnitContext ctx) {
        CommandLine.tracer().debug("Visiting CompilationUnit %s", ctx.getText());

        List<Instruction> instructions = ctx.statement().stream()
                .map(statementContext -> (Instruction) visit(statementContext))
                .toList();

        return new Ast(
                instructions
        );
    }

    @Override
    public Node visitStatementList(JavelinParser.StatementListContext ctx) {
        List<Instruction> instructions = ctx.statement().stream()
                .map(statementContext -> (Instruction) visit(statementContext))
                .toList();
        return new InstructionList(instructions);
    }

    @Override
    public Node visitExpressionStatement(JavelinParser.ExpressionStatementContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Node visitWhileStatement(JavelinParser.WhileStatementContext ctx) {
        Expression condition = (Expression) visit(ctx.expression());
        Instruction body = (Instruction) visit(ctx.statement());
        return new WhileStatement(
                condition,
                body
        );
    }

    @Override
    public Node visitCollectionAccess(JavelinParser.CollectionAccessContext ctx) {
        Expression collection = (Expression) visit(ctx.expression(0));
        Expression index = (Expression) visit(ctx.expression(1));
        return new BinaryExpression(
                collection,
                BinaryOperator.COLLECTION_ACCESS,
                index
        );
    }

    @Override
    public Node visitStaticMethodCall(JavelinParser.StaticMethodCallContext ctx) {
        Optional<String> typeName = Optional.ofNullable(ctx.typeIdentifier()).map(ParseTree::getText);
        String methodName = ctx.SymbolIdentifier().getText();
        List<Expression> arguments = ctx.expression().stream()
                .map(expressionContext -> (Expression) visit(expressionContext))
                .toList();

        return new StaticMethodCall(typeName, methodName, arguments);
    }

    @Override
    public Node visitParenExpression(JavelinParser.ParenExpressionContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Node visitBinaryOperationExpression(JavelinParser.BinaryOperationExpressionContext ctx) {
        Expression left = (Expression) visit(ctx.expression(0));
        Expression right = (Expression) visit(ctx.expression(1));
        String operator = ctx.binaryOperator().getText();
        return new BinaryExpression(
                left,
                BinaryOperator.fromString(operator),
                right
        );
    }

    @Override
    public Node visitCollectionCreation(JavelinParser.CollectionCreationContext ctx) {
        List<Expression> elements = ctx.expression().stream()
                .map(expressionContext -> (Expression) visit(expressionContext))
                .toList();
        return new CollectionCreationExpression(elements);
    }

    @Override
    public Node visitInstanceMethodCall(JavelinParser.InstanceMethodCallContext ctx) {
        Expression instance = (Expression) visit(ctx.expression(0));
        String methodName = ctx.SymbolIdentifier().getText();
        List<Expression> arguments = ctx.expression().subList(1, ctx.expression().size()).stream()
                .map(expressionContext -> (Expression) visit(expressionContext))
                .toList();

        return new InstanceMethodCall(
                instance,
                methodName,
                arguments
        );
    }

    @Override
    public Node visitSymbolDefinition(JavelinParser.SymbolDefinitionContext ctx) {
        CommandLine.tracer().debug("Visiting SymbolDefinition %s", ctx.getText());

        boolean mutable = ctx.MUT() != null;
        Type type = Type.fromName(ctx.typeIdentifier().getText());
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
    public Node visitLiteralExpression(JavelinParser.LiteralExpressionContext ctx) {
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
    public Node visitSymbolIdentifierExpression(JavelinParser.SymbolIdentifierExpressionContext ctx) {
        CommandLine.tracer().debug("Visiting SymbolIdentifierExpression %s", ctx.getText());

        String name = ctx.SymbolIdentifier().getText();
        return new SymbolExpression(name);
    }

    @Override
    public Node visitAssignment(JavelinParser.AssignmentContext ctx) {
        CommandLine.tracer().debug("Visiting Assignment %s", ctx.getText());

        String name = ctx.SymbolIdentifier().getText();
        Expression value = (Expression) visit(ctx.expression());

        return new Assignment(name, value);
    }

    @Override
    public Node visitErrorNode(ErrorNode node) {
        CommandLine.tracer().debug("Visiting ErrorNode %s", node.getText());

        throw new ParserException(node.getText());
    }

}
