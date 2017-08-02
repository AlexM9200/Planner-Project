/*
 * 
 * Alex Matthews
 * Software Development Student @ Institute of Technology Carlow, Ireland
 * 2017
 *
 */

public class DivideByZeroException extends ArithmeticException {

	private static final long serialVersionUID = 1L;

	public DivideByZeroException() {
		super("Attempted to Divide by Zero");
	}

	public DivideByZeroException(String message) {
		super(message);
	}
}
