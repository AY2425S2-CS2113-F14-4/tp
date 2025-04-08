package ezmealplan.parser;

import org.junit.jupiter.api.Test;
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
import ezmealplan.exceptions.EZMealPlanException;
import ezmealplan.exceptions.ParserException;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {

    /**
     * parseByeCommand_byeWithoutArguments_returnsByeCommand
     * Unit Being Tested: Parser.parse (bye command)
     * Description: Input "bye" (without extra arguments)
     * Expected Outcome: Returns an instance of ByeCommand.
     */
    @Test
    public void parseByeCommand_byeWithoutArguments_returnsByeCommand() throws ParserException, EZMealPlanException {
        Command command = Parser.parse("bye");
        assertInstanceOf(ByeCommand.class, command);
    }

    /**
     * parseCreateCommand_createWithArguments_returnsCreateCommand
     * Unit Being Tested: Parser.parse (create command)
     * Description: Input "create new meal plan"
     * Expected Outcome: Returns an instance of CreateCommand.
     */
    @Test
    public void parseCreateCommand_createWithArguments_returnsCreateCommand()
            throws ParserException, EZMealPlanException {
        Command command = Parser.parse("create new meal plan");
        assertInstanceOf(CreateCommand.class, command);
    }

    /**
     * parseHelpCommand_helpWithoutArguments_returnsHelpCommand
     * Unit Being Tested: Parser.parse (help command)
     * Description: Input "help" (without extra arguments)
     * Expected Outcome: Returns an instance of HelpCommand.
     */
    @Test
    public void parseHelpCommand_helpWithoutArguments_returnsHelpCommand() throws ParserException, EZMealPlanException {
        Command command = Parser.parse("help");
        assertInstanceOf(HelpCommand.class, command);
    }

    /**
     * parseHelpCommand_helpWithOneArgument_returnsHelpCommand
     * Unit Being Tested: Parser.parse (help command)
     * Description: Input "help wishlist"
     * Expected Outcome: Returns an instance of HelpCommand.
     */
    @Test
    public void parseHelpCommand_helpWithOneArgument_returnsHelpCommand() throws ParserException, EZMealPlanException {
        Command command = Parser.parse("help wishlist");
        assertInstanceOf(HelpCommand.class, command);
    }

    /**
     * parseFilterCommand_filterWithArgument_returnsFilterCommand
     * Unit Being Tested: Parser.parse (filter command)
     * Description: Input "filter ingredient"
     * Expected Outcome: Returns an instance of FilterCommand.
     */
    @Test
    public void parseFilterCommand_filterWithArgument_returnsFilterCommand()
            throws ParserException, EZMealPlanException {
        Command command = Parser.parse("filter ingredient");
        assertInstanceOf(FilterCommand.class, command);
    }

    /**
     * parseSelectCommand_selectWithOneArgument_returnsSelectCommand
     * Unit Being Tested: Parser.parse (select command)
     * Description: Input "select item"
     * Expected Outcome: Returns an instance of SelectCommand.
     */
    @Test
    public void parseSelectCommand_selectWithOneArgument_returnsSelectCommand()
            throws ParserException, EZMealPlanException {
        Command command = Parser.parse("select item");
        assertInstanceOf(SelectCommand.class, command);
    }

    /**
     * parseRecipesCommand_recipesWithoutArguments_returnsRecipesCommand
     * Unit Being Tested: Parser.parse (recipes command)
     * Description: Input "recipes" (without extra arguments)
     * Expected Outcome: Returns an instance of RecipesCommand.
     */
    @Test
    public void parseRecipesCommand_recipesWithoutArguments_returnsRecipesCommand()
            throws ParserException, EZMealPlanException {
        Command command = Parser.parse("recipes");
        assertInstanceOf(RecipesCommand.class, command);
    }

    /**
     * parseWishlistCommand_wishlistWithoutArguments_returnsWishlistCommand
     * Unit Being Tested: Parser.parse (wishlist command)
     * Description: Input "wishlist" (without extra arguments)
     * Expected Outcome: Returns an instance of WishlistCommand.
     */
    @Test
    public void parseWishlistCommand_wishlistWithoutArguments_returnsWishlistCommand()
            throws ParserException, EZMealPlanException {
        Command command = Parser.parse("wishlist");
        assertInstanceOf(WishlistCommand.class, command);
    }

    /**
     * parseClearCommand_clearWithoutArguments_returnsClearCommand
     * Unit Being Tested: Parser.parse (clear command)
     * Description: Input "clear" (without extra arguments)
     * Expected Outcome: Returns an instance of ClearCommand.
     */
    @Test
    public void parseClearCommand_clearWithoutArguments_returnsClearCommand()
            throws ParserException, EZMealPlanException {
        Command command = Parser.parse("clear");
        assertInstanceOf(ClearCommand.class, command);
    }

    /**
     * parseRemoveCommand_removeWithArgument_returnsRemoveCommand
     * Unit Being Tested: Parser.parse (remove command)
     * Description: Input "remove item"
     * Expected Outcome: Returns an instance of RemoveCommand.
     */
    @Test
    public void parseRemoveCommand_removeWithArgument_returnsRemoveCommand()
            throws ParserException, EZMealPlanException {
        Command command = Parser.parse("remove item");
        assertInstanceOf(RemoveCommand.class, command);
    }

    /**
     * parseViewCommand_viewWithArgument_returnsViewCommand
     * Unit Being Tested: Parser.parse (view command)
     * Description: Input "view item"
     * Expected Outcome: Returns an instance of ViewCommand.
     */
    @Test
    public void parseViewCommand_viewWithArgument_returnsViewCommand() throws ParserException, EZMealPlanException {
        Command command = Parser.parse("view item");
        assertInstanceOf(ViewCommand.class, command);
    }

    /**
     * parseDeleteCommand_deleteWithArgument_returnsDeleteCommand
     * Unit Being Tested: Parser.parse (delete command)
     * Description: Input "delete item"
     * Expected Outcome: Returns an instance of DeleteCommand.
     */
    @Test
    public void parseDeleteCommand_deleteWithArgument_returnsDeleteCommand()
            throws ParserException, EZMealPlanException {
        Command command = Parser.parse("delete item");
        assertInstanceOf(DeleteCommand.class, command);
    }

    /**
     * parseRecommendCommand_recommendWithArgument_returnsRecommendCommand
     * Unit Being Tested: Parser.parse (recommend command)
     * Description: Input "recommend meal"
     * Expected Outcome: Returns an instance of RecommendCommand.
     */
    @Test
    public void parseRecommendCommand_recommendWithArgument_returnsRecommendCommand()
            throws ParserException, EZMealPlanException {
        Command command = Parser.parse("recommend meal");
        assertInstanceOf(RecommendCommand.class, command);
    }

    /**
     * parseConsumeCommand_consumeWithArgument_returnsConsumeCommand
     * Unit Being Tested: Parser.parse (consume command)
     * Description: Input "consume food"
     * Expected Outcome: Returns an instance of ConsumeCommand.
     */
    @Test
    public void parseConsumeCommand_consumeWithArgument_returnsConsumeCommand()
            throws ParserException, EZMealPlanException {
        Command command = Parser.parse("consume food");
        assertInstanceOf(ConsumeCommand.class, command);
    }

    /**
     * parseBuyCommand_buyWithArgument_returnsBuyCommand
     * Unit Being Tested: Parser.parse (buy command)
     * Description: Input "buy ingredient"
     * Expected Outcome: Returns an instance of BuyCommand.
     */
    @Test
    public void parseBuyCommand_buyWithArgument_returnsBuyCommand() throws ParserException, EZMealPlanException {
        Command command = Parser.parse("buy ingredient");
        assertInstanceOf(BuyCommand.class, command);
    }

    /**
     * parseInventoryCommand_inventoryWithoutArguments_returnsInventoryCommand
     * Unit Being Tested: Parser.parse (inventory command)
     * Description: Input "inventory" (without extra arguments)
     * Expected Outcome: Returns an instance of InventoryCommand.
     */
    @Test
    public void parseInventoryCommand_inventoryWithoutArguments_returnsInventoryCommand()
            throws ParserException, EZMealPlanException {
        Command command = Parser.parse("inventory");
        assertInstanceOf(InventoryCommand.class, command);
    }

    @Test
    public void parseByeCommand_byeWithExtraArguments_exceptionThrown() {
        assertThrows(ParserException.class, () -> Parser.parse("bye extra"));
    }

    @Test
    public void parseCreateCommand_createWithoutArguments_exceptionThrown() {
        assertThrows(ParserException.class, () -> Parser.parse("create"));
    }

    @Test
    public void parseHelpCommand_helpWithTooManyArguments_exceptionThrown() {
        assertThrows(ParserException.class, () -> Parser.parse("help wishlist extra"));
    }

    @Test
    public void parseFilterCommand_filterWithoutArguments_exceptionThrown() {
        assertThrows(ParserException.class, () -> Parser.parse("filter"));
    }

    @Test
    public void parseSelectCommand_selectWithoutArguments_exceptionThrown() {
        assertThrows(ParserException.class, () -> Parser.parse("select"));
    }

    @Test
    public void parseRecipesCommand_recipesWithExtraArguments_exceptionThrown() {
        assertThrows(ParserException.class, () -> Parser.parse("recipes extra"));
    }

    @Test
    public void parseWishlistCommand_wishlistWithExtraArguments_exceptionThrown() {
        assertThrows(ParserException.class, () -> Parser.parse("wishlist extra"));
    }

    @Test
    public void parseClearCommand_clearWithExtraArguments_exceptionThrown() {
        assertThrows(ParserException.class, () -> Parser.parse("clear extra"));
    }

    @Test
    public void parseRemoveCommand_removeWithoutArguments_exceptionThrown() {
        assertThrows(ParserException.class, () -> Parser.parse("remove"));
    }

    @Test
    public void parseViewCommand_viewWithoutArguments_exceptionThrown() {
        assertThrows(ParserException.class, () -> Parser.parse("view"));
    }

    @Test
    public void parseDeleteCommand_deleteWithoutArguments_exceptionThrown() {
        assertThrows(ParserException.class, () -> Parser.parse("delete"));
    }

    @Test
    public void parseRecommendCommand_recommendWithoutArguments_exceptionThrown() {
        assertThrows(ParserException.class, () -> Parser.parse("recommend"));
    }

    @Test
    public void parseConsumeCommand_consumeWithoutArguments_exceptionThrown() {
        assertThrows(ParserException.class, () -> Parser.parse("consume"));
    }

    @Test
    public void parseBuyCommand_buyWithoutArguments_exceptionThrown() {
        assertThrows(ParserException.class, () -> Parser.parse("buy"));
    }

    @Test
    public void parseInventoryCommand_inventoryWithExtraArguments_exceptionThrown() {
        assertThrows(ParserException.class, () -> Parser.parse("inventory extra"));
    }

    /**
     * parseMistypedCommand_inputMistyped_returnsMistypedCommand
     * Unit Being Tested: Parser.parse -> parseUnknownInput
     * Description: Input "wishlists" (a slight misspelling of "wishlist")
     * Expected Outcome: Returns an instance of MistypedCommand.
     */
    @Test
    public void parseMistypedCommand_inputMistyped_returnsMistypedCommand()
            throws ParserException, EZMealPlanException {
        Command command = Parser.parse("wishlists");
        assertInstanceOf(MistypedCommand.class, command);
    }

    /**
     * parseUnknownCommand_inputUnknown_returnsUnknownCommand
     * Unit Being Tested: Parser.parse -> parseUnknownInput
     * Description: Input "wish list" (space splits into tokens; commandWord "wish")
     * Expected Outcome: Returns an instance of UnknownCommand.
     */
    @Test
    public void parseUnknownCommand_inputUnknown_returnsUnknownCommand() throws ParserException, EZMealPlanException {
        Command command = Parser.parse("wish list");
        assertInstanceOf(UnknownCommand.class, command);
    }

    /**
     * parseMistypedCommand_anotherExample_returnsMistypedCommand
     * Unit Being Tested: Parser.parse -> parseUnknownInput
     * Description: Input "filter/ing ingredient" (mistyped command starting with a valid command string)
     * Expected Outcome: Returns an instance of MistypedCommand.
     */
    @Test
    public void parseMistypedCommand_anotherExample_returnsMistypedCommand()
            throws ParserException, EZMealPlanException {
        Command command = Parser.parse("filter/ing ingredient");
        assertInstanceOf(MistypedCommand.class, command);
    }

    /**
     * parseUnknownCommand_anotherExample_returnsUnknownCommand
     * Unit Being Tested: Parser.parse -> parseUnknownInput
     * Description: Input "reccomend /ing 1" (unknown command spelling)
     * Expected Outcome: Returns an instance of UnknownCommand.
     */
    @Test
    public void parseUnknownCommand_anotherExample_returnsUnknownCommand() throws ParserException, EZMealPlanException {
        Command command = Parser.parse("reccomend /ing 1");
        assertInstanceOf(UnknownCommand.class, command);
    }

    /**
     * parseUnknownCommand_yetAnotherExample_returnsUnknownCommand
     * Unit Being Tested: Parser.parse -> parseUnknownInput
     * Description: Input "by e" (unknown command because "by" does not match "bye")
     * Expected Outcome: Returns an instance of UnknownCommand.
     */
    @Test
    public void parseUnknownCommand_yetAnotherExample_returnsUnknownCommand()
            throws ParserException, EZMealPlanException {
        Command command = Parser.parse("by e");
        assertInstanceOf(UnknownCommand.class, command);
    }

    /**
     * parseUnknownCommand_creatInput_returnsUnknownCommand
     * Unit Being Tested: Parser.parse -> parseUnknownInput
     * Description: Input "creat egg /ing raw egg (1)" (unknown command because "creat" doesn't match "create")
     * Expected Outcome: Returns an instance of UnknownCommand.
     */
    @Test
    public void parseUnknownCommand_creatInput_returnsUnknownCommand() throws ParserException, EZMealPlanException {
        Command command = Parser.parse("creat egg /ing raw egg (1)");
        assertInstanceOf(UnknownCommand.class, command);
    }
}
