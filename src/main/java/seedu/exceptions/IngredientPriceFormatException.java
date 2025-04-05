package seedu.exceptions;

public class IngredientPriceFormatException extends EZMealPlanException {
    String ingredientName;

    public IngredientPriceFormatException(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    @Override
    public String getMessage() {
        return "Price input for " + ingredientName + " must have 2 decimal places and be convertable to double. " +
                "Please enter a valid string price input in 2 decimal places such as 0.00, 0.50, 2.00, 1.55 etc.\n";
    }
}
