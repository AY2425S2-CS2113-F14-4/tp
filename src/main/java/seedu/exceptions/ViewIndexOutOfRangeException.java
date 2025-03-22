package seedu.exceptions;

import seedu.meallist.MainMeals;
import seedu.meallist.Meals;

public class ViewIndexOutOfRangeException extends EZMealPlanException {
    int inputIndex;
    int listSize;
    String listName;
    String mainMealListName = "main meal list";
    String userMealListName = "user meal list";

    public ViewIndexOutOfRangeException(int inputIndex, Meals meals) {
        this.inputIndex = inputIndex;
        this.listSize = meals.size();
        this.listName = meals instanceof MainMeals ? mainMealListName : userMealListName;
    }

    @Override
    public String getMessage() {
        return "The index provided for the " + listName + " (" + inputIndex + ") is out of range.\n" +
                "It must be between 1 and " + listSize + ".\n";
    }
}
