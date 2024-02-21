import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class HorseTest {

    // Проверить, что при передаче в конструктор первым параметром null,
    // будет выброшено IllegalArgumentException.
    // Для этого нужно воспользоваться методом assertThrows
    @Test
    void constructorWithNullFirstArgument() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1));
    }

    // Проверить, что при передаче в конструктор первым параметром null,
    // выброшенное исключение будет содержать сообщение "Name cannot be null.".
    // Для этого нужно получить сообщение из перехваченного исключения и
    // воспользоваться методом assertEquals
    @Test
    void constructorNullCheckingExceptionsText() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null,1));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    /*
    Проверить, что при передаче в конструктор первым параметром пустой
    строки или строки содержащей только пробельные символы (пробел,
    табуляция и т.д.), будет выброшено IllegalArgumentException.
    Чтобы выполнить проверку с разными вариантами пробельных символов,
    нужно сделать тест параметризованным;
     */
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\r", "\t\n\r"})
    void wrongSymbols(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1));
    }

    /*
    Проверить, что при передаче в конструктор первым параметром пустой строки
    или строки содержащей только пробельные символы (пробел, табуляция и т.д.),
    выброшенное исключение будет содержать сообщение "Name cannot be blank."
     */
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\r", "\t\n\r"})
    void nameNotBlank(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    /*
    Проверить, что при передаче в конструктор вторым параметром
    отрицательного числа, будет выброшено IllegalArgumentException
     */
    @Test
    void constructorNegativeSpeed() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Horse", -3));
    }

    /*
    Проверить, что при передаче в конструктор вторым параметром отрицательного
    числа, выброшенное исключение будет содержать сообщение "Speed cannot be negative."
     */
    @Test
    void constructorNegativeSpeedText() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Horse",-3));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    /*
    Проверить, что при передаче в конструктор третьим параметром отрицательного числа,
    будет выброшено IllegalArgumentException;
     */
    @Test
    void constructorNegativeDistance() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Horse", 3, -5));
    }

    /*
    Проверить, что при передаче в конструктор третьим параметром отрицательного числа,
    выброшенное исключение будет содержать сообщение "Distance cannot be negative."
     */
    @Test
    void constructorNegativeDistanceText() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Horse",3, -5));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    /*
    Проверить, что метод возвращает строку, которая была передана
    первым параметром в конструктор
     */
    @Test
    void getName() {
        String expectedName = "Black Beauty";
        Horse horse = new Horse(expectedName, 3);
        assertEquals(expectedName, horse.getName());
    }

    /*
    Проверить, что метод возвращает число, которое было передано
    вторым параметром в конструктор
     */
    @Test
    void getSpeed() {
        double initialSpeed = 3;
        Horse horse = new Horse("Horse", initialSpeed);
        assertEquals(initialSpeed, horse.getSpeed());
    }

    /*
    Проверить, что метод возвращает число, которое было передано
    третьим параметром в конструктор
     */
    @Test
    void getDistance() {
        double initialDistance = 5;
        Horse horse = new Horse("Horse", 3, initialDistance);
        assertEquals(initialDistance, horse.getDistance());
    }

    /*
    Проверить, что метод возвращает ноль, если объект был создан с помощью
    конструктора с двумя параметрами;
     */
    @Test
    void getDistanceTwoParams() {
        double initialDistance = 0;
        Horse horse = new Horse("Horse", 3);
        assertEquals(initialDistance, horse.getDistance());
    }

    /*
    Проверить, что метод вызывает внутри метод getRandomDouble с параметрами
    0.2 и 0.9. Для этого нужно использовать MockedStatic и его метод verify
     */
    @Test
    void useGetRandomDouble() {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("Horse", 6);
            horse.move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    /*
    Проверить, что метод присваивает дистанции значение высчитанное по формуле:
    distance + speed * getRandomDouble(0.2, 0.9). Для этого нужно замокать
    getRandomDouble, чтобы он возвращал определенные значения, которые нужно
    задать параметризовав тест
     */
    @Test
    void checkingRandomDoubleMethode() {
        double speed = 10.0;
        double min = 0.2;
        double max = 0.9;
        double randomValue = 0.5;

        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)){
            mockedStatic.when(() -> Horse.getRandomDouble(min, max)).thenReturn(randomValue);

            Horse horse = new Horse("Horse", speed, 20);

            horse.move();

            double expectedDistance = 20.0 + speed * randomValue;
            assertEquals(expectedDistance, horse.getDistance(), 0.0001);
        }
    }
}