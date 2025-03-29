package seedu.checkers;

import seedu.exceptions.EZMealPlanException;
import seedu.exceptions.InvalidIngIndexException;
import seedu.exceptions.InvalidMcostIndexException;
import seedu.exceptions.InvalidMnameIndexException;
import seedu.exceptions.MissingIngredientException;
import seedu.exceptions.MissingMealCostException;
import seedu.exceptions.MissingMealIndexException;
import seedu.exceptions.MissingMealNameException;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class SelectCheckerTest {
    private static final Logger logger = Logger.getLogger(SelectCheckerTest.class.getName());
    final String select = "select";

    public SelectCheckerTest() {
        String fileName = "SelectCheckerTest.log";
        setupLogger(fileName);
    }

    private static void setupLogger(String fileName) {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.ALL);
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);
        logger.addHandler(consoleHandler);
        createLogFile(fileName);
    }

    private static void createLogFile(String fileName) {
        try {
            FileHandler fileHandler = new FileHandler(fileName, true);
            fileHandler.setLevel(Level.FINE);
            logger.addHandler(fileHandler);
        } catch (IOException ioException) {
            logger.log(Level.SEVERE, "File logger is not working.", ioException);
        }
    }

    @Test
    public void selectChecker_success() throws EZMealPlanException {
        logger.info("running selectChecker_success()");
        String[] validSelectStrings = {"select 1 /mname a", "select 2 /mname a,b", "select 3 /ing c", "select 4/ing c,d"
                , "select5/mcost 1"};
        for (String selectString : validSelectStrings) {
            String filterMethod = getFilterMethod(selectString);
            SelectChecker checker = new SelectChecker(selectString, filterMethod);
            checker.check();
            assertTrue(checker.isPassed());
            logger.info("Valid select command input.");
        }
        logger.info("selectChecker_success() passed");
    }

    private static String getFilterMethod(String filterString) {
        String filterMethod = "";
        if (filterString.contains("/mname")) {
            filterMethod = "byMname";
        } else if (filterString.contains("/mcost")) {
            filterMethod = "byMcost";
        } else if (filterString.contains("/ing")) {
            filterMethod = "byIng";
        }
        return filterMethod;
    }

    @Test
    public void selectChecker_fail() {
        checkInvalidMcostIndex();
        checkInvalidMcostFormat();
        checkInvalidMnameIndex();
        checkInvalidMnameFormat();
        checkInvalidIngIndex();
        checkInvalidIngFormat();
        checkMissingMealIndex();
    }

    private void checkInvalidIngFormat() {
        String testName = "checkInvalidIngFormat()";
        String[] invalidIngFormatStrings = {"select 1 /ing", "select2/ing   "};
        String expectedMessage = new MissingIngredientException(select).getMessage();
        checkInvalidFilterInput(testName, invalidIngFormatStrings, expectedMessage);
    }

    private void checkInvalidIngIndex() {
        String testName = "checkInvalidIngIndex()";
        String[] invalidIngIndexStrings = {"/ing select 1", "/ing a,b select 1", "a,b /ing 1 select"
                , " 1 /ing a,b select"};
        String expectedMessage = new InvalidIngIndexException(select).getMessage();
        checkInvalidFilterInput(testName, invalidIngIndexStrings, expectedMessage);
    }

    private void checkInvalidMnameFormat() {
        String testName = "checkInvalidMnameFormat()";
        String[] invalidMnameFormatStrings = {"select 1 /mname", "select2/mname   "};
        String expectedMessage = new MissingMealNameException(select).getMessage();
        checkInvalidFilterInput(testName, invalidMnameFormatStrings, expectedMessage);
    }

    private void checkInvalidMnameIndex() {
        String testName = "checkInvalidMnameIndex()";
        String[] invalidMnameIndexStrings = {"/mname select 2", "/mname a,b  2 select", "a,b 2 /mname select"
                , "/mname2 select a,b"};
        String expectedMessage = new InvalidMnameIndexException(select).getMessage();
        checkInvalidFilterInput(testName, invalidMnameIndexStrings, expectedMessage);
    }

    private void checkInvalidMcostFormat() {
        String testName = "checkInvalidMcostFormat()";
        String[] invalidMnameIndexStrings = {"select 1 /mcost", "select1/mcost  "};
        String expectedMessage = new MissingMealCostException(select).getMessage();
        checkInvalidFilterInput(testName, invalidMnameIndexStrings, expectedMessage);
    }

    private void checkInvalidMcostIndex() {
        String testName = "checkInvalidMcostIndex()";
        String[] invalidMnameIndexStrings = {"/mcost select2", "/mcost 1 2 select", "2 /mcost 5 select"
                , "2/mcost 0.5select"};
        String expectedMessage = new InvalidMcostIndexException(select).getMessage();
        checkInvalidFilterInput(testName, invalidMnameIndexStrings, expectedMessage);
    }

    private void checkMissingMealIndex() {
        String testName = "checkMissingMealIndex()";
        String[] invalidStringIndexStrings = {"select /ing a", "select/mcost 1", "select/mname b,c", "select"};
        String expectedMessage = new MissingMealIndexException(select).getMessage();
        checkInvalidFilterInput(testName, invalidStringIndexStrings, expectedMessage);
    }

    private static void checkInvalidFilterInput(String testName, String[] testStrings, String expectedMessage) {
        logger.fine("running " + testName);
        for (String invalidIngIndexString : testStrings) {
            String filterMethod = getFilterMethod(invalidIngIndexString);
            SelectChecker checker = new SelectChecker(invalidIngIndexString, filterMethod);
            try {
                checker.check();
                fail();
            } catch (EZMealPlanException ezMealPlanException) {
                assertEquals(expectedMessage, ezMealPlanException.getMessage());
                logger.info("Matching exception caught!");
                logger.info(testName + " passed");
            } finally {
                assertFalse(checker.isPassed());
                logger.info("Input is expected to fail.");
            }
        }
    }
}
