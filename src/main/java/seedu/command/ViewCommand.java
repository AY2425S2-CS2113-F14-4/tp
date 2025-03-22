package seedu.command;

import seedu.checkers.ViewChecker;
import seedu.exceptions.EZMealPlanException;
import seedu.exceptions.InvalidViewKeywordException;
import seedu.exceptions.ViewIndexOutOfRangeException;
import seedu.food.Meal;
import seedu.logic.MealManager;
import seedu.meallist.Meals;
import seedu.ui.UserInterface;


import java.util.logging.Logger;

public class ViewCommand extends Command {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    String mainOrUser;
    final String main = "/m";
    final String user = "/u";

    public ViewCommand(String userInput) {
        this.validUserInput = userInput.trim();
        this.lowerCaseInput = this.validUserInput.toLowerCase();
    }

    @Override
    public void execute(MealManager mealManager, UserInterface ui) throws EZMealPlanException {
        setMainOrUser();
        boolean isValidUserInput = checkValidUserInput();
        if (!isValidUserInput) {
            logger.severe("Huge issue detected! The user input format remains invalid despite " +
                    "passing all the checks for input formatting error.");
        }
        assert isValidUserInput;
        viewMeal(mainOrUser, mealManager, ui);
    }

    private void viewMeal(String mainOrUser, MealManager mealManager, UserInterface ui) throws EZMealPlanException {
        Meals meals = mainOrUser.equals(main) ? mealManager.getMainMeals()
                : mealManager.getUserMeals();
        if (checkEmptyMealList(mainOrUser, meals)) {
            return;
        }
        int afterKeywordIndex = lowerCaseInput.indexOf(mainOrUser) + mainOrUser.length();
        String afterKeyword = lowerCaseInput.substring(afterKeywordIndex).trim();
        int mealListIndex = Integer.parseInt(afterKeyword);
        Meal meal = getMeal(meals, mealListIndex);
        ui.printIngredientList(meal);
    }

    private boolean checkEmptyMealList(String mainOrUser, Meals meals) {
        String mainMealListName = "main meal list";
        String userMealListName = "user meal list";
        if (meals.getList().isEmpty()) {
            String mealListString = mainOrUser.equals(main) ? mainMealListName : userMealListName;
            System.out.println("The " + mealListString + " is empty.\n");
            return true;
        }
        return false;
    }

    private static Meal getMeal(Meals meals, int mealListIndex) throws EZMealPlanException {
        try {
            int indexOffset = 1;
            int actualIndex = mealListIndex - indexOffset;
            return meals.getList().get(actualIndex);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new ViewIndexOutOfRangeException(mealListIndex, meals);
        }
    }


    public void setMainOrUser() throws EZMealPlanException {
        boolean isContainsMain = this.lowerCaseInput.contains(main) && !this.lowerCaseInput.contains(user);
        boolean isContainsUser = this.lowerCaseInput.contains(user) && !this.lowerCaseInput.contains(main);
        if (isContainsMain) {
            mainOrUser = main;
        } else if (isContainsUser) {
            mainOrUser = user;
        } else {
            throw new InvalidViewKeywordException();
        }
    }

    private boolean checkValidUserInput() throws EZMealPlanException {
        ViewChecker checker = new ViewChecker(validUserInput, mainOrUser);
        checker.check();
        return checker.isPassed();
    }
}

