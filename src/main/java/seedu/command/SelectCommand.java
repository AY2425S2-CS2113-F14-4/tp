package seedu.command;

import seedu.exceptions.EZMealPlanException;
import seedu.exceptions.InvalidSelectIndexException;
import seedu.food.Meal;
import seedu.logic.MealManager;
import seedu.meallist.MealList;
import seedu.ui.UserInterface;

import java.util.List;
import java.util.logging.Logger;

public class SelectCommand extends FilterSelectCommand {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public SelectCommand(String userInput) {
        validUserInput = userInput.trim();
        this.lowerCaseInput = validUserInput.toLowerCase();
        this.filterOrSelect = "select";
    }

    @Override
    public void execute(MealManager mealManager, UserInterface ui) throws EZMealPlanException {
        boolean isValidUserInput = checkValidUserInput(filterOrSelect);
        if (!isValidUserInput) {
            logger.severe("Huge issue detected! The user input format remains invalid despite " +
                    "passing all the checks for input formatting error.");
        }
        assert isValidUserInput;
        List<Meal> filteredMealList = getFilteredMealList(mealManager);
        if (filteredMealList.isEmpty()) {
            System.out.println("The filtered meal list is empty.");
            return;
        }
        String indexSubstring = getIndexSubstring();
        int inputIndex = checkValidParse(indexSubstring);
        Meal selectedMeal = checkValidInputIndex(inputIndex, filteredMealList);
        MealList wishList = mealManager.getWishList();
        mealManager.addMeal(selectedMeal, wishList);
        ui.printAddMealMessage(selectedMeal, wishList);
    }

    private String getIndexSubstring() {
        int afterSelectIndex = this.lowerCaseInput.indexOf(filterOrSelect) + filterOrSelect.length();
        int inputMethodIndex;
        String inputMethod = getString(MCOST, ING, MNAME);
        if (inputMethod.isEmpty()) {
            return validUserInput.substring(afterSelectIndex).trim();
        }
        inputMethodIndex = this.lowerCaseInput.indexOf(inputMethod);
        return validUserInput.substring(afterSelectIndex, inputMethodIndex).trim();
    }

    private Meal checkValidInputIndex(int inputIndex, List<Meal> mealList) throws EZMealPlanException {
        Meal selectedMeal;
        int indexAdjustment = 1;
        int actualIndex = inputIndex - indexAdjustment;
        try {
            selectedMeal = mealList.get(actualIndex);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new InvalidSelectIndexException();
        }
        return selectedMeal;
    }

    private int checkValidParse(String indexSubstring) throws EZMealPlanException {
        int inputIndex;
        try {
            inputIndex = Integer.parseInt(indexSubstring);
        } catch (NumberFormatException numberFormatException) {
            throw new InvalidSelectIndexException();
        }
        return inputIndex;
    }
}
