package seedu.exceptions;

public class DuplicateMealException extends EZMealPlanException {
    String mealName;
    String listName;

    public DuplicateMealException(String mealName, String listName) {
        this.mealName = mealName;
        this.listName = listName;
    }

    @Override
    public String getMessage() {
        String message = "This meal: " + mealName + " with the matching ingredient(s) already exists in the " +
                         listName + ".\n";
        String messageForCreatingDuplicateMeal = """
                To avoid confusion, please use either a different meal name or add a new ingredient into the meal.
                For example: Let A, B and C be ingredients.
                      The existing Meal 1 (hamburger) has A($1.50) and B($1.50).
                      Meal 2 (hamburger) has A($1.50) and B($1.50). (Duplicate!)
                      Meal 3 (hamburger) has A($1.00) and B($1.00). (Duplicate!)
                      Meal 4 (mini hamburger) has A($1.00) and B($1.00). (Accepted)
                      Meal 5 (normal hamburger) has A($1.50) and B($1.50). (Accepted)
                      Meal 6 (deluxe hamburger) has A($2.00) and B($2.00). (Accepted)
                      Meal 7 (hamburger) has A($1.50), B($0.50) and C($1.50).(Accepted)
                """;
        return listName.equals("main meal list") ? message + messageForCreatingDuplicateMeal : message;
    }
}
