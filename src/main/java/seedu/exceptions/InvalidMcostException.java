package seedu.exceptions;

public class InvalidMcostException extends EZMealPlanException {
    @Override
    public String getMessage() {
        return "The /mcost input must be in 2 decimal places, parsable into a double and the resulting double value " +
                "must be between 0 to "+ Double.MAX_VALUE + ".\n" + "Please enter a valid string price input " +
               "as such 0.00, 0.50, 2.00, 1.55 etc.\n";
    }
}
