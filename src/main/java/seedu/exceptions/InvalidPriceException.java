package seedu.exceptions;

public class InvalidPriceException extends EZMealPlanException {
    String productName;

    public InvalidPriceException(String productName) {
        this.productName = productName;
    }

    public String getMessage() {
        return "The price of the " + this.productName + " must be greater than or equals to 0.\n";
    }
}
