package nemo.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import nemo.exception.IncorrectDateTimeException;

public class EventTest {
    @Test
    public void eventConstructor_hasMainArg_incorrectDateTimeExceptionThrown() {
        HashMap<String, String> dummyArgs = new HashMap<>();
        dummyArgs.put("mainArg", "test");

        try {
            new Event(dummyArgs);
        } catch (ParseException e) {
            assertEquals(e.getMessage(), "Task argument requires a priority level");
        } catch (IncorrectDateTimeException e) {
            // @@author CrunchyBiscuit19-reused
            // Reused from https://www.baeldung.com/junit-fail
            // with minor modifications
            fail("IncorrectDateTimeException should not be thrown");
            // @@author
        }
    }

    @Test
    public void eventConstructor_noFromDate_parseExceptionThrown() {
        HashMap<String, String> dummyArgs = new HashMap<>();
        dummyArgs.put("mainArg", "test");
        dummyArgs.put("priority", "low");

        try {
            new Event(dummyArgs);
        } catch (ParseException e) {
            assertEquals(e.getMessage(), "Task argument requires a from date");
        } catch (IncorrectDateTimeException e) {
            fail("IncorrectDateTimeException should not be thrown");
        }
    }

    @Test
    public void eventConstructor_noToDate_incorrectDateTimeExceptionThrown() {
        HashMap<String, String> dummyArgs = new HashMap<>();
        dummyArgs.put("mainArg", "test");
        dummyArgs.put("priority", "low");
        dummyArgs.put("from", "a");

        try {
            new Event(dummyArgs);
        } catch (ParseException e) {
            assertEquals(e.getMessage(), "Task argument requires a to date");
        } catch (IncorrectDateTimeException e) {
            fail("IncorrectDateTimeException should not be thrown");
        }
    }

    @Test
    public void eventConstructor_invalidDateFormats_dateTimeParseExceptionThrown() {
        HashMap<String, String> dummyArgs = new HashMap<>();
        dummyArgs.put("mainArg", "test");
        dummyArgs.put("priority", "low");
        dummyArgs.put("from", "a");
        dummyArgs.put("to", "b");

        try {
            new Event(dummyArgs);
        } catch (DateTimeParseException e) {
            assertEquals(e.getMessage(), "a is not a valid date for format dd-MM-yyyy HHmm.");
        } catch (ParseException e) {
            assertEquals(e.getMessage().contains("is not a valid date for format"), true);
        } catch (IncorrectDateTimeException e) {
            fail("IncorrectDateTimeException should not be thrown");
        }
    }

    @Test
    public void eventConstructor_fromIsAfterTo_incorrectDateTimeExceptionThrown() {
        HashMap<String, String> dummyArgs = new HashMap<>();
        dummyArgs.put("mainArg", "test");
        dummyArgs.put("priority", "low");
        dummyArgs.put("from", "03-03-2026 0000");
        dummyArgs.put("to", "02-02-2026 0000");

        try {
            new Event(dummyArgs);
        } catch (DateTimeParseException e) {
            fail("DateTimeParseException should not be thrown");
        } catch (ParseException e) {
            fail("ParseException should not be thrown");
        } catch (IncorrectDateTimeException e) {
            assertEquals(e.getMessage(), "From datetime must come strictly before to datetime.");
        }
    }

    @Test
    public void eventConstructor_validArguments_noExceptionsThrown() {
        HashMap<String, String> dummyArgs = new HashMap<>();
        dummyArgs.put("mainArg", "test");
        dummyArgs.put("priority", "low");
        dummyArgs.put("from", "01-01-2026 0000");
        dummyArgs.put("to", "02-02-2026 0000");

        try {
            new Event(dummyArgs);
        } catch (DateTimeParseException e) {
            fail("DateTimeParseException should not be thrown");
        } catch (ParseException e) {
            fail("ParseException should not be thrown");
        } catch (IncorrectDateTimeException e) {
            fail("IncorrectDateTimeException should not be thrown");
        }
    }
}
