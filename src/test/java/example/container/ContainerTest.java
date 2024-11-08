package example.container;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тестирование класса Container
 */
public class ContainerTest {

    /**
     * Экземпляр класса Container, тестируемый в этом классе
     */
    private Container container;

    /**
     * Инициализация полей
     */
    @BeforeEach
    public void init() {
        container = new Container();
    }

    /**
     * Тестирование методов add, size и get
     */
    @Test
    void testAdd() {
        Assertions.assertEquals(0, container.size());

        Item item = new Item(1);

        Assertions.assertTrue(container.add(item));
        Assertions.assertEquals(1, container.size());
        Assertions.assertEquals(item, container.get(0));
    }

    /**
     * Тестирование метода remove
     */
    @Test
    void testRemove() {
        Item item = new Item(1);
        container.add(item);

        Assertions.assertTrue(container.remove(item));
        Assertions.assertEquals(0, container.size());
    }
}
