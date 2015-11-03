package kn.tcg.model.exceptions;

public class ActionException extends Exception{
    public ActionException(String message){
        super(message);
    }

    public ActionException(String message, Throwable throwable){
        super(message, throwable);
    }
}
