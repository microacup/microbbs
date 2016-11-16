package me.micro.bbs.exception;

/**
 * MicroException
 *
 * Created by microacup on 2016/11/16.
 */
public class MicroException extends Exception {
    public MicroException() {
        super("服务器好像不太懂...");
    }

    public MicroException(String message) {
        super(message);
    }

    public MicroException(String message, Throwable cause) {
        super(message, cause);
    }

    public MicroException(Throwable cause) {
        super(cause);
    }
}
