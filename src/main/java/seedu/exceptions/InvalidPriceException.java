package seedu.exceptions;

public class InvalidPriceException extends EZMealPlanException {
    String productName;

    public InvalidPriceException(String productName) {
        this.productName = productName;
    }

    public String getMessage() {
        return "The price of the " + this.productName + " must be between 0 and " + Double.MAX_VALUE +" (both sides" +
               " inclusive).\n";
    }
}
