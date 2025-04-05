package seedu.exceptions;

public class EmptyListException extends EZMealPlanException {
    String recipeOrWishlistName;

    public EmptyListException(String recipeOrWishlistName) {
        this.recipeOrWishlistName = recipeOrWishlistName;
    }
    @Override
    public String getMessage() {
        return "The " + recipeOrWishlistName + " is empty.\n";
    }
}
