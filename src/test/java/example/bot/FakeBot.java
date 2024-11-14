package example.bot;

import java.util.ArrayList;
import java.util.List;

/**
 * Бот, для тестирования BotLogic
 */
public class FakeBot implements Bot {

    /**
     * Сообщения, отправленные ботом
     */
    private final List<String> messages = new ArrayList<>();

    @Override
    public void sendMessage(Long chatId, String message) {
        messages.add(message);
    }

    /**
     * Возвращение сообщений, отправленных ботом
     */
    public List<String> getMessages() {
        return messages;
    }

    /**
     * Возвращение сообщения по индексу
     */
    public String getMessage(int index) {
        return messages.get(index);
    }
}
