import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class HippodromeTest {

    /*
    Проверить, что при передаче в конструктор null, будет выброшено
    IllegalArgumentException
     */
    @Test
    void nullInConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    /*
    Проверить, что при передаче в конструктор null, выброшенное исключение
    будет содержать сообщение "Horses cannot be null."
     */
    @Test
    void nullInConstructorText() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    /*
    Проверить, что при передаче в конструктор пустого списка, будет
    выброшено IllegalArgumentException
     */
    @Test
    void emptyListInConstructor() {
        List<Horse> horses = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
    }

    /*
    Проверить, что при передаче в конструктор пустого списка, выброшенное
    исключение будет содержать сообщение "Horses cannot be empty."
     */
    @Test
    void emptyListInConstructorText() {
        List<Horse> horses = new ArrayList<>();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    /*
    Проверить, что метод возвращает список, который содержит те же объекты
    и в той же последовательности, что и список который был передан в конструктор.
    При создании объекта Hippodrome передай в конструктор список из
    30 разных лошадей
     */
    @Test
    void getHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Name " + i, 10 + 1 * Math.random()));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    /*
    Проверить, что метод вызывает метод move у всех лошадей. При создании
    объекта Hippodrome передай в конструктор список из 50 моков лошадей и
    воспользуйся методом verify
     */
    @Test
    void move() {
        List<Horse> mockHorses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Horse mockHorse = Mockito.mock(Horse.class);
            mockHorses.add(mockHorse);
        }
        Hippodrome hippodrome = new Hippodrome(mockHorses);
        hippodrome.move();
        for (Horse mockHorse : mockHorses) {
            Mockito.verify(mockHorse, times(1)).move();
        }
    }

    @Test
    void getWinner() {
        List<Horse> horses = new ArrayList<>();
        Horse horse1 = new Horse("Horse1", 10, 100);
        Horse horse2 = new Horse("Horse2", 15, 150);
        Horse horse3 = new Horse("Horse3", 20, 200);
        horses.add(horse1);
        horses.add(horse2);
        horses.add(horse3);

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(horse3, hippodrome.getWinner());
    }
}