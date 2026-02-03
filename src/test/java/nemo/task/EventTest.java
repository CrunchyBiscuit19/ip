package nemo.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class EventTest {
    @Test
    public void eventConstructorTest() {
        HashMap<String, String> dummyArgs = new HashMap<>();

        dummyArgs.put("mainArg", "test");
        try {
            new Event(dummyArgs);
        } catch (ParseException e) {
            assertEquals(e.getMessage(), "Task argument requires a priority level");
        }

        dummyArgs.put("priority", "low");
        try {
            new Event(dummyArgs);
        } catch (ParseException e) {
            assertEquals(e.getMessage(), "Task argument requires a from date");
        }

        dummyArgs.put("from", "a");
        try {
            new Event(dummyArgs);
        } catch (ParseException e) {
            assertEquals(e.getMessage(), "Task argument requires a to date");
        }

        dummyArgs.put("to", "b");
        try {
            new Event(dummyArgs);
        } catch (DateTimeParseException e) {
            assertEquals(e.getMessage(), "a is not a valid date for format dd-MM-yyyy HHmm.");
        } catch (ParseException e) {
            assertEquals(e.getMessage().contains("is not a valid date for format"), true);
        }

        dummyArgs.put("from", "01-01-2026 0000");
        dummyArgs.put("to", "01-01-2026 0000");
        try {
            new Event(dummyArgs);
        } catch (Exception e) {
            assertEquals(true, false); // Should never happen
        }
    }
}
