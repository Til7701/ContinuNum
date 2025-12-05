package de.til7701.continu_num.cli;

import picocli.CommandLine;

public class VersionProvider implements CommandLine.IVersionProvider {

    @Override
    public String[] getVersion() {
        return new String[]{System.getProperty("continu_num.version")};
    }

}