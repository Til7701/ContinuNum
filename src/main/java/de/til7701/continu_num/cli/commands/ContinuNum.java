package de.til7701.continu_num.cli.commands;

import de.til7701.continu_num.ast.ContinuNumFile;
import de.til7701.continu_num.cli.VersionProvider;
import de.til7701.continu_num.cli.mixins.DebugMixin;
import de.til7701.continu_num.parser.FileParser;
import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "conum",
        mixinStandardHelpOptions = true,
        versionProvider = VersionProvider.class,
        description = "The ContinuNum programming language."
)
public class ContinuNum implements Callable<Integer> {

    @CommandLine.Mixin
    private DebugMixin debugMixin;

    @CommandLine.Parameters(
            index = "0",
            description = "The source file to execute.",
            arity = "1"
    )
    private File source;

    @Override
    public Integer call() throws Exception {
        FileParser fileParser = new FileParser();
        ContinuNumFile ast = fileParser.parse(source);
        System.out.println(ast);
        return 0;
    }

}
