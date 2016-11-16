package me.micro.bbs.exception;

/**
 * MicroException
 *
 * Created by microacup on 2016/11/16.
 */
public class MicroRuntimeException extends Exception {
    public MicroRuntimeException() {
        super("服务器正在思考人生...");
    }

    public MicroRuntimeException(String message) {
        super(message);
    }

    public MicroRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MicroRuntimeException(Throwable cause) {
        super(cause);
    }
}
