package Laptenkov;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс для тестирования public методов класса {@link CustomTreeMapImpl<Object>}.
 *
 * @author habatoo
 */
class CustomTreeMapImplTest {


    CustomTreeMap<Integer, String> customEmptyTreeMap;
    CustomTreeMap<Integer, String> customNotEmptyTreeMap;

    /**
     * Инициализация экземпляров тестируемого класса {@link CustomTreeMapImpl<Object>}.
     */
    @BeforeEach
    void setUp() {
        customEmptyTreeMap = new CustomTreeMapImpl<Integer, String>(Integer::compareTo);

        customNotEmptyTreeMap = new CustomTreeMapImpl<Integer, String>(Integer::compareTo);
        customNotEmptyTreeMap.put(1, "first");
        customNotEmptyTreeMap.put(99, "last");
        customNotEmptyTreeMap.put(4, "fourth");
        customNotEmptyTreeMap.put(2, "second");
        customNotEmptyTreeMap.put(3, "third");
    }

    /**
     * Очистка экземпляров тестируемого класса {@link CustomTreeMapImpl<Object>}..
     */
    @AfterEach
    void tearDown() {
        customEmptyTreeMap = null;
        customNotEmptyTreeMap = null;
    }

    /**
     * Проверяет создание пустого экземпляра {@link CustomTreeMapImpl}.
     * Сценарий, при котором конструктор отрабатывает пустую коллекцию,
     * проверяет размер коллекции
     * равный 0 и отображение коллекции.
     */
    @Test
    public void customHashSetImpl_Test() {
        customEmptyTreeMap = new CustomTreeMapImpl<Integer, String>(Integer::compareTo);
        Assertions.assertEquals(0, customEmptyTreeMap.size());
        Assertions.assertEquals(
                "[ ]", customEmptyTreeMap.toString());
    }

    /**
     * Метод {@link CustomTreeMapImplTest#size_Test()}
     * проверяет метод {@link CustomTreeMapImpl#size()}.
     * Проверяет размер не пустого экземпляра {@link CustomTreeMapImpl}.
     * Сценарий, при котором пустой экземпляр возвращает величину
     * объекта равную 0, не пустой экземпляр возвращает 5.
     */
    @Test
    void size_Test() {
        Assertions.assertEquals(0, customEmptyTreeMap.size());
        Assertions.assertEquals(5, customNotEmptyTreeMap.size());
    }

    /**
     * Метод  {@link CustomTreeMapImplTest#isEmpty_Test()}
     * проверяет метод {@link CustomTreeMapImpl#isEmpty()}.
     * Проверяет на пустоту экземпляр объекта {@link CustomTreeMapImpl}.
     * Сценарий, при котором пустой экземпляр возвращает <code>true</code>,
     * не пустой экземпляр возвращает <code>false</code>.
     */
    @Test
    void isEmpty_Test() {
        Assertions.assertEquals(true, customEmptyTreeMap.isEmpty());
        Assertions.assertEquals(false, customNotEmptyTreeMap.isEmpty());
    }

    /**
     * Метод {@link CustomTreeMapImplTest#getSuccess_Test()}
     * Проверяет проверяет метод {@link CustomTreeMapImpl#get(Object)}.
     * Сценарий, при котором проверяется наличие существующего ключа и
     * возвращает значение V соответсвующее этому ключу.
     */
    @Test
    void getSuccess_Test() {
        Assertions.assertEquals("first", customNotEmptyTreeMap.get(1));
        Assertions.assertEquals("last", customNotEmptyTreeMap.get(99));
    }

    /**
     * Метод {@link CustomTreeMapImplTest#getFail_Test()}
     * Проверяет проверяет метод {@link CustomTreeMapImpl#get(Object)}.
     * Сценарий, при котором проверяется наличие не существующего ключа и
     * возвращает null.
     */
    @Test
    void getFail_Test() {
        Assertions.assertEquals(null, customEmptyTreeMap.get(999));
        Assertions.assertEquals(null, customNotEmptyTreeMap.get(999));
    }

    /**
     * Метод {@link CustomTreeMapImplTest#putSuccess_Test()}
     * Проверяет проверяет метод {@link CustomTreeMapImpl#put(Object, Object)}.
     * Сценарий, при котором объект успешно отрабатывает добавление
     * сушествующего объекта и возвращает его старое значение.
     */
    @Test
    void putSuccess_Test() {
        Assertions.assertEquals(5, customNotEmptyTreeMap.size());
        Assertions.assertEquals("first", customNotEmptyTreeMap.put(1,"notFirst"));
        Assertions.assertEquals(5, customNotEmptyTreeMap.size());
    }

    /**
     * Метод {@link CustomTreeMapImplTest#putNull_Test()}
     * Проверяет проверяет метод {@link CustomTreeMapImpl#put(Object, Object)}.
     * Сценарий, при котором объект успешно отрабатывает добавление
     * не пустого объекта типа Т и возвращает <code>null</code>.
     */
    @Test
    void putNull_Test() {
        Assertions.assertEquals(0, customEmptyTreeMap.size());
        Assertions.assertEquals(null, customEmptyTreeMap.put(15,"first"));
        Assertions.assertEquals(null, customEmptyTreeMap.put(99, "null"));
    }

    /**
     * Метод {@link CustomTreeMapImplTest#putNull_Test()}
     * Проверяет проверяет метод {@link CustomTreeMapImpl#remove(Object)}.
     * Сценарий, при котором объект успешно отрабатывает удаление
     * не пустого существующего объекта по ключу К и возвращает старое значение V.
     */
    @Test
    void removeSuccess_Test() {
        Assertions.assertEquals(5, customNotEmptyTreeMap.size());
        Assertions.assertEquals("first", customNotEmptyTreeMap.remove(1));
        Assertions.assertEquals(4, customNotEmptyTreeMap.size());
    }

    /**
     * Метод {@link CustomTreeMapImplTest#putNull_Test()}
     * Проверяет проверяет метод {@link CustomTreeMapImpl#remove(Object)}.
     * Сценарий, при котором объект успешно отрабатывает удаление
     * не пустого не существующего объекта по ключу К и возвращает null.
     */
    @Test
    void removeFail_Test() {
        Assertions.assertEquals(null, customNotEmptyTreeMap.remove(999));
        Assertions.assertEquals(null, customEmptyTreeMap.remove(999));
    }

    /**
     * Метод {@link CustomTreeMapImplTest#containsKeySuccess_Test()}
     * Проверяет проверяет метод {@link CustomTreeMapImpl#containsKey(Object)}.
     * Сценарий, при котором проверяется наличие существующего ключа и
     * возвращает <code>true</code>.
     */
    @Test
    void containsKeySuccess_Test() {
        Assertions.assertEquals(true, customNotEmptyTreeMap.containsKey(1));
        Assertions.assertEquals(true, customNotEmptyTreeMap.containsKey(99));
    }

    /**
     * Метод {@link CustomTreeMapImplTest#containsKeyFail_Test()}
     * Проверяет проверяет метод {@link CustomTreeMapImpl#containsKey(Object)}.
     * Сценарий, при котором проверяется наличие не существующего ключа и
     * возвращает <code></code>.
     */
    @Test
    void containsKeyFail_Test() {
        Assertions.assertEquals(false, customEmptyTreeMap.containsKey(999));
        Assertions.assertEquals(false, customNotEmptyTreeMap.containsKey(999));
    }

    /**
     * Метод {@link CustomTreeMapImplTest#containsValueSuccess_Test()}
     * Проверяет проверяет метод {@link CustomTreeMapImpl#containsValue(Object)}.
     * Сценарий, при котором проверяется наличие существующего значения и
     * возвращает <code>true</code>.
     */
    @Test
    void containsValueSuccess_Test() {
        Assertions.assertEquals(true, customNotEmptyTreeMap.containsValue("first"));
        Assertions.assertEquals(true, customNotEmptyTreeMap.containsValue("last"));
    }

    /**
     * Метод {@link CustomTreeMapImplTest#containsValueFail_Test()}
     * Проверяет проверяет метод {@link CustomTreeMapImpl#containsValue(Object)}.
     * Сценарий, при котором проверяется наличие не существующего значения и
     * возвращает <code></code>.
     */
    @Test
    void containsValueFail_Test() {
        Assertions.assertEquals(false, customEmptyTreeMap.containsValue("none"));
        Assertions.assertEquals(false, customNotEmptyTreeMap.containsValue("none"));
    }

    /**
     * Метод {@link CustomTreeMapImplTest#keys_Test()}
     * Проверяет проверяет метод {@link CustomTreeMapImpl#keys()}.
     * Сценарий, при котором проверяется отображение массива всех ключей.
     */
    @Test
    void keys_Test() {
        Assert.assertEquals(Arrays.asList(1, 2, 3, 4, 99), Arrays.asList(customNotEmptyTreeMap.keys()));
    }

    /**
     * Метод {@link CustomTreeMapImplTest#containsValueFail_Test()}
     * Проверяет проверяет метод {@link CustomTreeMapImpl#containsValue(Object)}.
     * Сценарий, при котором проверяется отображение массива всех значений.
     */
    @Test
    void values_Test() {
        Assert.assertEquals(Arrays.asList(
                "first", "second", "third", "fourth", "last"), Arrays.asList(customNotEmptyTreeMap.values()));
    }

    /**
     * Метод {@link CustomTreeMapImplTest#testToStringEmpty_Test()}
     * Проверяет отображение экземпляр объекта {@link CustomTreeMapImpl}
     * методом {@link CustomTreeMapImpl#toString()}.
     * Сценарий, при котором пустой экземпляр проверяется на отображение
     * тестовых строк.
     */
    @Test
    void testToStringEmpty_Test() {
        Assertions.assertEquals("[ ]", customEmptyTreeMap.toString());
    }

    /**
     * Метод {@link CustomTreeMapImplTest#testToStringNotEmpty_Test()}
     * Проверяет отображение экземпляр объекта {@link CustomTreeMapImpl}
     * методом {@link CustomTreeMapImpl#toString()}.
     * Сценарий, при котором не пустой экземпляр проверяется на отображение
     * тестовых строк.
     */
    @Test
    void testToStringNotEmpty_Test() {
        Assertions.assertEquals(
                "[  {key=1;value=first}  {key=2;value=second}  {key=3;value=third}  {key=4;value=fourth}  {key=99;value=last} ]",
                customNotEmptyTreeMap.toString());
    }

}