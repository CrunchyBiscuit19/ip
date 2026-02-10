package nemo.command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nemo.exception.NotCommandException;

public class CommandTest {
    @Test
    public void commandFromString_validStringConversion_convertValidStringsOnly() {
        try {
            assertEquals(Command.fromString("bye"), Command.BYE);
            assertEquals(Command.fromString("list"), Command.LIST);
            assertEquals(Command.fromString("mark"), Command.MARK);
            assertEquals(Command.fromString("unmark"), Command.UNMARK);
            assertEquals(Command.fromString("todo"), Command.TODO);
            assertEquals(Command.fromString("deadline"), Command.DEADLINE);
            assertEquals(Command.fromString("event"), Command.EVENT);
            assertEquals(Command.fromString("delete"), Command.DELETE);
            Command.fromString("wrong");
        } catch (NotCommandException e) {
            assertEquals(e.getMessage(), "Not valid command.");
        }
    }
}
