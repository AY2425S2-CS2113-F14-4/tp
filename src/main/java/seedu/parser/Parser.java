package seedu.parser;

import seedu.command.ByeCommand;
import seedu.command.ClearCommand;
import seedu.command.Command;
import seedu.command.CreateCommand;
import seedu.command.FilterCommand;
import seedu.command.HelpCommand;
import seedu.command.ListCommand;
import seedu.command.MealCommand;
import seedu.command.RemoveCommand;
import seedu.command.DeleteCommand;
import seedu.command.SelectCommand;
import seedu.command.UnknownCommand;
import seedu.command.ViewCommand;

public class Parser {
    static String bye = "bye";
    static String create = "create";
    static String filter = "filter";
    static String select = "select";
    static String meal = "meal";
    static String list = "list";
    static String clear = "clear";
    static String help = "help";
    static String remove = "remove";
    static String view = "view";
    static String delete = "delete";

    public static Command parse(String userInput) {
        String lowerCaseUserInput = userInput.toLowerCase().trim();
        userInput = userInput.trim();
        if (lowerCaseUserInput.startsWith(bye)) {
            return new ByeCommand();
        } else if (lowerCaseUserInput.startsWith(create)) {
            return new CreateCommand(userInput);
        } else if (lowerCaseUserInput.startsWith(filter)) {
            return new FilterCommand(userInput);
        } else if (lowerCaseUserInput.startsWith(select)) {
            return new SelectCommand(userInput);
        } else if (lowerCaseUserInput.startsWith(list)) {
            return new ListCommand();
        } else if (lowerCaseUserInput.startsWith(meal)) {
            return new MealCommand();
        } else if (lowerCaseUserInput.startsWith(clear)) {
            return new ClearCommand();
        } else if (lowerCaseUserInput.startsWith(help)) {
            return new HelpCommand(userInput);
        } else if (lowerCaseUserInput.startsWith(remove)) {
            return new RemoveCommand(userInput);
        } else if (lowerCaseUserInput.startsWith(view)) {
            return new ViewCommand(userInput);
        } else if (lowerCaseUserInput.startsWith(delete)) {
            return new DeleteCommand(userInput);
        } else {
            return new UnknownCommand(userInput);
        }
    }
}
