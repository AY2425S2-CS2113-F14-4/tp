package seedu.command;

import seedu.logic.MealManager;
import seedu.ui.UserInterface;

public class UnknownCommand extends Command {
    public UnknownCommand(String userInputText) {
        validUserInput = userInputText;
    }
    @Override
    public void execute(MealManager mealManager, UserInterface ui) {
        ui.printUnknownCommand(validUserInput);
    }
}
