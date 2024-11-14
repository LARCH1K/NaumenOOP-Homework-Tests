package example.note;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тестирование логики по работе с заметками
 */
public class NoteLogicTest {

    /**
     * Экземпляр класса NoteLogic, тестируемый в этом классе
     */
    private NoteLogic noteLogic;

    /**
     * Инициализация полей
     */
    @BeforeEach
    public void init() {
        noteLogic = new NoteLogic();
    }

    /**
     * Тестирование команд /notes и /add
     */
    @Test
    void testHandleMessageNotesCommandAndAddCommand() {
        Assertions.assertEquals("Your notes:", noteLogic.handleMessage("/notes"));
        Assertions.assertEquals("Note added!", noteLogic.handleMessage("/add New note"));

        String expectedAnswer = """
                Your notes:
                1. New note
                """;
        Assertions.assertEquals(expectedAnswer, noteLogic.handleMessage("/notes"));
    }

    /**
     * Тестирование команды /edit
     */
    @Test
    void testHandleMessageEditCommand() {
        noteLogic.handleMessage("/add New note");
        Assertions.assertEquals("Note edited!", noteLogic.handleMessage("/edit 1 First note"));

        String expectedAnswer = """
                Your notes:
                1. First note
                """;
        Assertions.assertEquals(expectedAnswer, noteLogic.handleMessage("/notes"));
    }

    /**
     * Тестирование команды /del
     */
    @Test
    void testHandleMessageDelCommand() {
        noteLogic.handleMessage("/add First note");
        noteLogic.handleMessage("/add Second note");
        Assertions.assertEquals("Note deleted!", noteLogic.handleMessage("/del 1"));

        String expectedAnswer = """
                Your notes:
                1. Second note
                """;
        Assertions.assertEquals(expectedAnswer, noteLogic.handleMessage("/notes"));
    }
}
