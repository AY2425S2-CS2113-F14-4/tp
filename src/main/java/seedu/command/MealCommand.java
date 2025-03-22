package seedu.command;

import seedu.exceptions.EZMealPlanException;
import seedu.food.Meal;
import seedu.logic.MealManager;
import seedu.ui.UserInterface;

import java.util.List;
import java.util.logging.Logger;

public class MealCommand extends Command {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    public void execute(MealManager mealManager, UserInterface ui) throws EZMealPlanException {
        logger.fine("Executing MealCommand");
        List<Meal> userMealList = mealManager.getUserMeals().getList();
        ui.printMealList(userMealList, "user chosen meals");
    }
}
