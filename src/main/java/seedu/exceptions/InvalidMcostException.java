package seedu.exceptions;

public class InvalidMcostException extends EZMealPlanException {
    @Override
    public String getMessage() {
        return "The /mcost input must be parsable into a double and the resulting double value " +
                "must be more than or equals to 0.\n";
    }
}
