package seedu.command;

import seedu.exceptions.EZMealPlanException;
import seedu.food.Meal;
import seedu.logic.MealManager;
import seedu.ui.UserInterface;

import java.util.List;
import java.util.logging.Logger;

public class ListCommand extends Command {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Override
    public void execute(MealManager mealManager, UserInterface ui) throws EZMealPlanException {
        logger.fine("Executing 'list' command");
        List<Meal> mainMealList = mealManager.getMainMeals().getList();
        ui.printMealList(mainMealList, "main list");
    }
}
