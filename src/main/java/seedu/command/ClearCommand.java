package seedu.command;

import seedu.logic.MealManager;
import seedu.ui.UserInterface;
import seedu.meallist.Meals;
import seedu.food.Meal;
import java.util.List;


public class ClearCommand extends Command {

    @Override
    public void execute(MealManager mealManager, UserInterface ui) {
        Meals userMeals = mealManager.getUserMeals();
        List<Meal> userMealList = userMeals.getList();
        userMealList.clear();
        ui.printClearedList();
    }
}
