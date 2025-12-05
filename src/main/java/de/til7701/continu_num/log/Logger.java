package de.til7701.continu_num.log;

import java.text.MessageFormat;

public class Logger {

    public void info(String message) {
        print(message);
    }

    public void info(Object o) {
        print(o.toString());
    }

    public void info(String message, Object... args) {
        print(MessageFormat.format(message, args));
    }

    public void info(String message, Throwable t) {
        print(message);
        printStackTrace(t);
    }

    public void warn(String message) {
        print(message);
    }

    public void warn(Object o) {
        print(o.toString());
    }

    public void warn(String message, Object... args) {
        print(MessageFormat.format(message, args));
    }

    public void warn(String message, Throwable t) {
        print(message);
        printStackTrace(t);
    }

    public void error(String message) {
        print(message);
    }

    public void error(Object o) {
        print(o.toString());
    }

    public void error(String message, Object... args) {
        print(MessageFormat.format(message, args));
    }

    public void error(String message, Throwable t) {
        print(message);
        printStackTrace(t);
    }

    public void debug(String message) {
        print(message);
    }

    public void debug(Object o) {
        print(o.toString());
    }

    public void debug(String message, Object... args) {
        print(MessageFormat.format(message, args));
    }

    public void debug(String message, Throwable t) {
        print(message);
        printStackTrace(t);
    }

    private void print(String message) {
        System.out.println(message); // NOSONAR
    }

    private void printStackTrace(Throwable t) {
        //noinspection CallToPrintStackTrace
        t.printStackTrace();
    }

}