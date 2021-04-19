package smat.meal.exception;

public class SmatException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public SmatException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SmatException(String exMessage) {
        super(exMessage);
    }
}
