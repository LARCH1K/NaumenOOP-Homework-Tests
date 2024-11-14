package example.bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тестирование логики бота
 */
public class BotLogicTest {

    /**
     * Экземпляр класса FakeBot, созданного для тестирования класса BotLogic
     */
    private FakeBot bot;

    /**
     * Экземпляр класса BotLogic, тестируемый в этом классе
     */
    private BotLogic botLogic;

    /**
     * Экземпляр класса User, используется для взаимодействия с botLogic
     */
    private User user;

    /**
     * Инициализация полей
     */
    @BeforeEach
    public void init() {
        bot = new FakeBot();
        botLogic = new BotLogic(bot);
        user = new User(1L);
    }

    /**
     * Тестирование команды /test на обработку корректного и некорректного ответов
     */
    @Test
    void testProcessCommandTestCommandCorrectAndIncorrectAnswer() {
        botLogic.processCommand(user, "/test");

        Assertions.assertEquals("Вычислите степень: 10^2", bot.getMessage(0));
        botLogic.processCommand(user, "100");
        Assertions.assertEquals("Правильный ответ!", bot.getMessage(1));

        Assertions.assertEquals("Сколько будет 2 + 2 * 2", bot.getMessage(2));
        botLogic.processCommand(user, "8");
        Assertions.assertEquals("Вы ошиблись, верный ответ: 6", bot.getMessage(3));
    }

    /**
     * Тестирование команды /test на обработку корректных ответов
     */
    @Test
    void testProcessCommandTestCommandCorrectAnswers() {
        botLogic.processCommand(user, "/test");

        Assertions.assertEquals("Вычислите степень: 10^2", bot.getMessage(0));
        botLogic.processCommand(user, "100");
        Assertions.assertEquals("Правильный ответ!", bot.getMessage(1));

        Assertions.assertEquals("Сколько будет 2 + 2 * 2", bot.getMessage(2));
        botLogic.processCommand(user, "6");
        Assertions.assertEquals("Правильный ответ!", bot.getMessage(3));
    }

    /**
     * Тестирование команды /test на обработку некорректных ответов
     */
    @Test
    void testProcessCommandTestCommandIncorrectAnswers() {
        botLogic.processCommand(user, "/test");

        Assertions.assertEquals("Вычислите степень: 10^2", bot.getMessage(0));
        botLogic.processCommand(user, "99");
        Assertions.assertEquals("Вы ошиблись, верный ответ: 100", bot.getMessage(1));

        Assertions.assertEquals("Сколько будет 2 + 2 * 2", bot.getMessage(2));
        botLogic.processCommand(user, "8");
        Assertions.assertEquals("Вы ошиблись, верный ответ: 6", bot.getMessage(3));
    }

    /**
     * Тестирование команды /notify с ожиданием в 1 секунду
     */
    @Test
    void testProcessCommandNotifyCommandNotifyWorked() throws InterruptedException {
        botLogic.processCommand(user, "/notify");
        botLogic.processCommand(user, "Notify text");
        botLogic.processCommand(user, "1");

        Thread.sleep(900);
        Assertions.assertEquals(3, bot.getMessages().size());

        Thread.sleep(110);
        Assertions.assertEquals(4, bot.getMessages().size());

        Assertions.assertEquals("Сработало напоминание: 'Notify text'", bot.getMessage(3));
    }

    /**
     * Тестирование команды /notify с отрицательным временем ожидания
     */
    @Test
    void testProcessCommandNotifyCommandNotifyNegativeWaitTime() {
        botLogic.processCommand(user, "/notify");
        botLogic.processCommand(user, "Notify text");

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> botLogic.processCommand(user, "-1"));
        Assertions.assertEquals("Negative delay.", exception.getMessage());
    }

    /**
     * Тестирование команды /notify с некорректным временем ожидания
     */
    @Test
    void testProcessCommandNotifyCommandNotifyInvalidWaitTime() {
        botLogic.processCommand(user, "/notify");
        botLogic.processCommand(user, "Notify text");
        botLogic.processCommand(user, "qwe");

        Assertions.assertEquals("Пожалуйста, введите целое число", bot.getMessage(2));
    }

    /**
     * Тестирование команды /repeat при отсутствии вопросов
     */
    @Test
    void testProcessCommandRepeatCommandWithoutQuestions() {
        botLogic.processCommand(user, "/repeat");
        Assertions.assertEquals("Нет вопросов для повторения", bot.getMessage(0));
    }

    /**
     * Тестирование команды /repeat при наличии вопросов
     */
    @Test
    void testProcessCommandRepeatCommandWithQuestions() {
        botLogic.processCommand(user, "/test");
        botLogic.processCommand(user, "101");

        botLogic.processCommand(user, "/repeat");
        Assertions.assertEquals("Вычислите степень: 10^2", bot.getMessage(3));

        botLogic.processCommand(user, "100");
        Assertions.assertEquals("Правильный ответ!", bot.getMessage(4));
        Assertions.assertEquals("Тест завершен", bot.getMessage(5));

        botLogic.processCommand(user, "/repeat");
        Assertions.assertEquals("Нет вопросов для повторения", bot.getMessage(6));
    }

}
