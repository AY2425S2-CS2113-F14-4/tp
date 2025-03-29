package seedu.checkers;

import org.junit.jupiter.api.Test;

import seedu.exceptions.EZMealPlanException;
import seedu.exceptions.RemoveFormatException;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.FileHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class RemoveDeleteCheckerTest {
    private static final Logger logger = Logger.getLogger(RemoveDeleteCheckerTest.class.getName());

    public RemoveDeleteCheckerTest() {
        String fileName = "RemoveDeleteCheckerTest.log";
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
    public void removeDeleteChecker_expectedInputs_success() throws EZMealPlanException {
        logger.fine("Running removeDeleteChecker_expectedInputs_success()");
        String validUserInput = "remove 1";
        RemoveDeleteChecker checker = new RemoveDeleteChecker(validUserInput);
        checker.check();
        assertTrue(checker.isPassed());
        logger.info("removeDeleteChecker_expectedInputs_success() passed");
    }

    @Test
    public void parseIndex_nonIntegerIndex_exceptionThrown() {
        logger.fine("Running parseIndex_nonIntegerIndex_exceptionThrown()");
        String validUserInput = "remove t";
        RemoveDeleteChecker checker = new RemoveDeleteChecker(validUserInput);
        try {
            checker.check();
            fail();
        } catch (EZMealPlanException ezMealPlanException) {
            assertEquals(new RemoveFormatException(validUserInput).getMessage(), ezMealPlanException.getMessage());
            logger.info("parseIndex_nonIntegerIndex_exceptionThrown() passed");
        }
    }

    @Test
    public void extractIndex_noIndex_exceptionThrown() {
        logger.fine("Running extractIndex_noIndex_exceptionThrown()");
        String validUserInput = "remove";
        RemoveDeleteChecker checker = new RemoveDeleteChecker(validUserInput);
        try {
            checker.check();
            fail();
        } catch (EZMealPlanException ezMealPlanException) {
            assertEquals(new RemoveFormatException(validUserInput).getMessage(), ezMealPlanException.getMessage());
            logger.info("extractIndex_noIndex_exceptionThrown() passed");
        }
    }
}
