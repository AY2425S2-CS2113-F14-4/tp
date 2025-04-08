package ezmealplan.parser;

import ezmealplan.command.BuyCommand;
import ezmealplan.command.ByeCommand;
import ezmealplan.command.ClearCommand;
import ezmealplan.command.Command;
import ezmealplan.command.ConsumeCommand;
import ezmealplan.command.CreateCommand;
import ezmealplan.command.DeleteCommand;
import ezmealplan.command.FilterCommand;
import ezmealplan.command.HelpCommand;
import ezmealplan.command.InventoryCommand;
import ezmealplan.command.MistypedCommand;
import ezmealplan.command.RecipesCommand;
import ezmealplan.command.RecommendCommand;
import ezmealplan.command.RemoveCommand;
import ezmealplan.command.SelectCommand;
import ezmealplan.command.UnknownCommand;
import ezmealplan.command.ViewCommand;
import ezmealplan.command.WishlistCommand;
import ezmealplan.exceptions.ParserException;

public class Parser {
    private static final String BYE = "bye";
    private static final String CREATE = "create";
    private static final String FILTER = "filter";
    private static final String SELECT = "select";
    private static final String WISHLIST = "wishlist";
    private static final String RECIPES = "recipes";
    private static final String CLEAR = "clear";
    private static final String HELP = "help";
    private static final String REMOVE = "remove";
    private static final String VIEW = "view";
    private static final String DELETE = "delete";
    private static final String RECOMMEND = "recommend";
    private static final String CONSUME = "consume";
    private static final String BUY = "buy";
    private static final String INVENTORY = "inventory";
    private static final String[] allCommandStrings = {BYE, CREATE, FILTER, SELECT, WISHLIST, RECIPES, CLEAR, HELP,
        REMOVE, VIEW, DELETE, RECOMMEND, CONSUME, BUY, INVENTORY};


    /**
     * Parses a string input and returns the corresponding command as a Command object.
     *
     * @throws ParserException If an unexpected number of arguments is detected after the command keyword.
     */
    public static Command parse(String userInput) throws ParserException {
        String trimmedInput = userInput.trim();
        String[] tokens = tokenise(trimmedInput);
        String commandWord = tokens[0].toLowerCase();

        return switch(commandWord) {
        case BYE -> parseBye(tokens, trimmedInput);
        case CREATE -> parseCreate(tokens, trimmedInput);
        case HELP -> parseHelp(tokens, trimmedInput);
        case FILTER -> parseFilter(tokens, trimmedInput);
        case SELECT -> parseSelect(tokens, trimmedInput);
        case RECIPES -> parseRecipes(tokens);
        case WISHLIST -> parseWishlist(tokens);
        case CLEAR -> parseClear(tokens);
        case REMOVE -> parseRemove(tokens, trimmedInput);
        case VIEW -> parseView(tokens, trimmedInput);
        case DELETE -> parseDelete(tokens, trimmedInput);
        case RECOMMEND -> parseRecommend(tokens, trimmedInput);
        case CONSUME -> parseConsume(tokens, trimmedInput);
        case BUY -> parseBuy(tokens, trimmedInput);
        case INVENTORY -> parseInventory(tokens);
        default -> parseUnknownInput(commandWord);
        };
    }

    /**
     * Splits the input string by one or more whitespace characters.
     */
    private static String[] tokenise(String input) {
        return input.split("\\s+");
    }

    // Helper method to enforce exact token count.
    private static void assertExactTokenCount(String[] tokens, int expected, String errorMessage)
            throws ParserException {
        if (tokens.length != expected) {
            throw new ParserException(errorMessage);
        }
    }

    // Helper method to enforce a minimum token count.
    private static void assertMinTokenCount(String[] tokens, int min, String errorMessage) throws ParserException {
        if (tokens.length < min) {
            throw new ParserException(errorMessage);
        }
    }

    private static Command parseBye(String[] tokens, String input) throws ParserException {
        assertExactTokenCount(tokens, 1, "The bye command does not accept any arguments.");
        return new ByeCommand();
    }

    private static Command parseCreate(String[] tokens, String input) throws ParserException {
        assertMinTokenCount(tokens, 2, "Missing arguments for create command.");
        return new CreateCommand(input);
    }

    private static Command parseHelp(String[] tokens, String input) throws ParserException {
        if (tokens.length > 2) {
            throw new ParserException("Invalid syntax for help command: too many arguments.");
        }
        return new HelpCommand(input);
    }

    private static Command parseFilter(String[] tokens, String input) throws ParserException {
        assertMinTokenCount(tokens, 2, "Missing arguments for filter command.");
        return new FilterCommand(input);
    }

    private static Command parseSelect(String[] tokens, String input) throws ParserException {
        assertMinTokenCount(tokens, 2, "Select command should have exactly one argument.");
        return new SelectCommand(input);
    }

    private static Command parseRecipes(String[] tokens) throws ParserException {
        assertExactTokenCount(tokens, 1, "The recipes command does not take any arguments.");
        return new RecipesCommand();

    }

    private static Command parseWishlist(String[] tokens) throws ParserException {
        assertExactTokenCount(tokens, 1, "The wishlist command does not take any arguments.");
        return new WishlistCommand();
    }

    private static Command parseClear(String[] tokens) throws ParserException {
        assertExactTokenCount(tokens, 1, "The clear command does not take any arguments.");
        return new ClearCommand();
    }

    private static Command parseRemove(String[] tokens, String input) throws ParserException {
        assertMinTokenCount(tokens, 2, "Missing arguments for remove command.");
        return new RemoveCommand(input);
    }

    private static Command parseView(String[] tokens, String input) throws ParserException {
        assertMinTokenCount(tokens, 2, "Missing arguments for view command.");
        return new ViewCommand(input);
    }

    private static Command parseDelete(String[] tokens, String input) throws ParserException {
        assertMinTokenCount(tokens, 2, "Missing arguments for delete command.");
        return new DeleteCommand(input);
    }

    private static Command parseRecommend(String[] tokens, String input) throws ParserException {
        assertMinTokenCount(tokens, 2, "Missing arguments for recommend command.");
        return new RecommendCommand(input);
    }

    private static Command parseConsume(String[] tokens, String input) throws ParserException {
        assertMinTokenCount(tokens, 2, "Missing arguments for consume command.");
        return new ConsumeCommand(input);
    }

    private static Command parseBuy(String[] tokens, String input) throws ParserException {
        assertMinTokenCount(tokens, 2, "Missing arguments for buy command.");
        return new BuyCommand(input);
    }

    private static Command parseInventory(String[] tokens) throws ParserException {
        assertExactTokenCount(tokens, 1, "The inventory command does not take any arguments.");
        return new InventoryCommand();
    }

    private static Command parseUnknownInput(String commandWord) {
        for (String actualCommandString : allCommandStrings) {
            if (commandWord.startsWith(actualCommandString)) {
                return new MistypedCommand(commandWord, actualCommandString);
            }
        }
        return new UnknownCommand(commandWord);
    }
}
