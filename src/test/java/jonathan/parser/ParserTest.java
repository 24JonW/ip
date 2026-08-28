package jonathan.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jonathan.JonathanException;
public class ParserTest {
    @Test
    public void isValidDate_correctFormat_returnsTrue() {
        assertTrue(Parser.isValidDate("2026-08-27"));
    }

    @Test
    public void isValidDate_wrongFormat_returnsFalse() {
        assertFalse(Parser.isValidDate("27-08-2026"));
        assertFalse(Parser.isValidDate("2026/08/27"));
        assertFalse(Parser.isValidDate("not a date"));
    }

    @Test
    public void parse_emptyTodoDescription_exceptionThrown() {
        assertThrows(JonathanException.class, () -> {
            Parser.parse("todo ");
        });
    }

    @Test
    public void parse_unknownCommand_exceptionThrown() {
        assertThrows(JonathanException.class, () -> {
            Parser.parse("blahblah");
        });
    }
}
