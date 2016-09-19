package app.exception;

public class BBNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -2367708878516550328L;

  public BBNotFoundException(String message) {
    super(message);
  }

}
