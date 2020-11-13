package edu.birzeit.swms.exceptions;

public class DatabaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    Exception exception;

    public DatabaseException() {
    }

    public DatabaseException(Exception exception) {
        super(exception.getMessage());
        this.exception = exception;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "DatabaseException{" +
                "exception=" + exception +
                '}';
    }

}
