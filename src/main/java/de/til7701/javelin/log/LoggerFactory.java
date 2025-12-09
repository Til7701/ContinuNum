package de.til7701.javelin.log;

public class LoggerFactory {

    public static Logger create(String name) {
        return new Logger();
    }

}