package Laptenkov;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

/**
 * Класс {@link CustomTreeMapImpl<K,V>} отображение на основе бинарного дерева поиска.
 * Класс {@link CustomTreeMapImpl<K,V>} реализует интерфейс {@link CustomTreeMap<K,V>}.
 * Класс {@link CustomTreeMapImpl<K,V>} может хранить объекты любого типа.
 */
public class CustomTreeMapImpl<K,V> implements CustomTreeMap {

    /**
     * Контейнер для храниения объекта Node.
     * @param <K> ключ.
     * @param <V> значение.
     */
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> left, right, parent;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        void setLeftChild(Node<K, V> node) {
            this.left = node;
            if (this.left != null) {
                this.left.parent = this;
            }
        }

        void setRightChild(Node<K, V> node) {
            this.right = node;
            if (this.right != null) {
                this.right.parent = this;
            }
        }
    }

    private Comparator<K> comparator;
    private int size;
    private Node<K, V> root;

    /**
     * Конструктор объекта {@link CustomTreeMapImpl<K,V>}
     */
    public CustomTreeMapImpl(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    /**
     * Метод {@link CustomTreeMapImpl<K,V>#size()} возвращает размер связанного списка
     * объекта {@link CustomTreeMapImpl<K,V>}.
     *
     * @return целое число типа {@link int}
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Метод {@link CustomTreeMapImpl<K,V>#isEmpty()} возвращает булево значение
     * при проверке объекта {@link CustomTreeMapImpl<K,V>} на пустоту.
     *
     * @return <code>true</code> если размер не нулевой,
     * <code>false</code> если размер нулевой.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Метод {@link CustomTreeMapImpl#get(Object)} возвращает V - значение
     * по переданному ключу K
     *
     * @param key ключ К.
     * @return V - значение если ключу К соотвествует значение,
     * null если такого ключа нет.
     */
    @Override
    public Object get(Object key) {
        if (size == 0) {
            return null;
        }

        Node<K, V> result = search(root, (K) key);

        return result == null ? null : result.value;
    }

    /**
     * Вспомогательный метод {@link CustomTreeMapImpl#get(Object)} возвращает Node<K, V>
     * по переданному ключу K
     *
     * @param key ключ К.
     * @return Node<K, V> если ключу К соотвествует значение,
     * null если такого ключа нет.
     */
    private Node<K, V> search(Node<K, V> node, K key) {

        int cmp = comparator.compare(key, node.key);
        if (cmp == 0) {
            return node;
        }

        if (cmp < 0) {
            if (node.left == null) {
                return null;
            } else {
                return search(node.left, key);
            }
        } else {
            if (node.right == null) {
                return null;
            } else {
                return search(node.right, key);
            }
        }
    }

    /**
     * Метод {@link CustomTreeMapImpl<K,V>#add(Object)} возвращает булево значение
     * при попытке добавления объекта в {@link CustomTreeMapImpl<K,V>}.
     *
     * объект типа Т для добавления.
     * @return возвращает <code>true</code> если добавление успешно,
     * возвращает <code>false</code> если добавление не удалось.
     *
     * @param key
     * @param value
     * @return previous value or null
     */
    @Override
    public Object put(Object key, Object value) {

        if (root == null) {
            root = new Node<K, V>((K) key, (V) value);
            root.parent = null;
            size++;
            return null;
        }

        return append(root, (K) key, (V) value);
    }

    /**
     * Вспомогателььный метод {@link CustomTreeMapImpl#get(Object)} возвращает V - значение
     * по переданному ключу K
     *
     * @param key ключ К.
     * @return V - значение если ключу К соотвествует значение,
     * null если такого ключа нет.
     */
    private V append(Node<K, V> node, K key, V value) {

        int cmp = comparator.compare(key, node.key);
        if (cmp == 0) {
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }

        if (cmp < 0) {
            if (node.left == null) {
                node.left = new Node<K, V>(key, value);
                node.left.parent = node;
                size++;
                return null;
            } else {
                return append(node.left, key, value);
            }
        } else {
            if (node.right == null) {
                node.right = new Node<K, V>(key, value);
                node.right.parent = node;
                size++;
                return null;
            } else {
                return append(node.right, key, value);
            }
        }
    }

    /**
     * Метод {@link CustomTreeMapImpl#remove(Object)} удаляет объект по переданному
     * значению ключа К при наличия ключа K объекта в {@link CustomTreeMapImpl} или
     * возвращает null.
     *
     * @param key передаваемый ключ.
     * @return удаленное значение или null если объект не существует.
     */
    @Override
    public Object remove(Object key) {
        if (root == null) {
            return null;
        }

        if (root.key == key) {
            Node<K, V> result = root;
            siftAfter(root);
            return result.value;
        }

        Object result = get(key);
        if (!removeBefore(root, (K) key)) {
            result = null;
        }
        return  result;
    }

    /**
     * Метод удаляет объект из {@link CustomTreeMapImpl} с упорядочиванием их
     * по порядку - используя comparator.
     * @param node узел в котором производится попытка удаления.
     * @param key объекта для удаления.
     * @return возвращает <code>true</code> если удаление успешно,
     * возвращает <code>false</code> если удаление не удалось.
     */
    private boolean removeBefore(Node<K, V> node, K key) {

        int result = comparator.compare(key, node.key);
        if (result == 0) {
            siftAfter(node);
            return true;
        }

        if (result < 0) {
            if (node.left == null) {
                return false;
            } else {
                return removeBefore(node.left, key);
            }
        } else {
            if (node.right == null) {
                return false;
            } else {
                return removeBefore(node.right, key);
            }
        }
    }

    /**
     * Вспомогательный метод для метода {@link CustomTreeMapImpl<K, V>#remove(Object)}
     * прохохжления по дереву и удаления
     * @param node узел для дальнейшего рекурсивного обхода.
     */
    private void siftAfter(Node<K, V> node) {

        if (node.right == null && node.left == null) {
            if (node.parent == null) {
                root = null;
                size = 0;
                return;
            }

            if (node.parent.left == node) {
                node.parent.left = null;
                size--;
                return;
            }
            if (node.parent.right == node) {
                node.parent.right = null;
                size--;
                return;
            }
        }

        // X не имеет правого потомка
        if (node.right == null) {
            exchange(node, node.left);
            return;
        }

        // X имеет правого потомка Y
        if (node.right != null) {
            if (node.right.left == null) { // Y не имеет левого потомка
                node.right.setLeftChild(node.left);
                exchange(node, node.right);
                return;
            }

            if (node.right.left != null) { // Y имеет левого потомка
                Node<K, V> tmpLeft = node.right;
                Node<K, V> tmpPrev = null;
                while (tmpLeft.left != null) {
                    tmpPrev = tmpLeft;
                    tmpLeft = tmpLeft.left;
                }

                if (tmpLeft.right != null) {
                    tmpPrev.left = tmpLeft.right;
                } else {
                    tmpPrev.left = null;
                }
                tmpLeft.setLeftChild(node.left);
                tmpLeft.setRightChild(node.right);
                exchange(node, tmpLeft);
                return;
            }
        }
    }

    /**
     * Вспомогательный метод для метода {@link CustomTreeMapImpl<K, V>#remove(Object)}
     * замены двух нод.
     * @param node узел который меняют.
     * @param changeNode узел для замены.
     */
    private void exchange(Node<K, V> node, Node<K, V> changeNode) {
        if (node.parent == null) {
            changeNode.parent = null;
            root = changeNode;
        } else if (node.parent.left == node) {
            changeNode.parent = node.parent;
            node.parent.left = changeNode;
        } else if (node.parent.right == node) {
            changeNode.parent = node.parent;
            node.parent.right = changeNode;
        }
        node = changeNode;
        size--;
        return;
    }

    /**
     * Метод {@link CustomTreeMapImpl#containsKey(Object)} возвращает булево значение
     * при проверке наличия ключа K объекта в {@link CustomTreeMapImpl}.
     *
     * @param key ключ типа К для проверки.
     * @return возвращает <code>true</code> если ключ присутствует,
     * возвращает <code>false</code> если ключ отсутствует.
     */
    @Override
    public boolean containsKey(Object key) {
        if (root == null) {
            return false;
        }

        Node<K, V> result = search(root, (K) key);

        return result == null ? false : true;
    }

    /**
     * Метод {@link CustomTreeMapImpl#containsValue(Object)} возвращает булево значение
     * при проверке наличия значения V объекта в {@link CustomTreeMapImpl}.
     *
     * @param value - значение V для проверки.
     * @return возвращает <code>true</code> если значение присутствует,
     * возвращает <code>false</code> если значение отсутствует.
     */
    @Override
    public boolean containsValue(Object value) {
        if (root == null) {
            return false;
        }

        Collection<Node<K, V>> array = new ArrayList<>(size);
        if (root != null) {
            fillRecursive(root, array);
        }

        for (Node<K, V> node : array) {
            if (Objects.equals(node.value, value)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Метод {@link CustomTreeMapImpl#keys()} возвращает массив всех ключей К
     * объекта {@link CustomTreeMapImpl}.
     *
     * @return массив ключей Object[]
     */
    @Override
    public Object[] keys() {
        Collection<Node<K, V>> array = new ArrayList<>(size);
        if (root != null) {
            fillRecursive(root, array);
        }

        Object[] objects = new Object[size];
        int j = 0;
        for (Node<K, V> node : array) {
            objects[j++] = node.key;
        }

        return objects;
    }

    /**
     * Метод {@link CustomTreeMapImpl#keys()} возвращает массив всех значений К
     * объекта {@link CustomTreeMapImpl}.
     *
     * @return массив значений Object[]
     */
    @Override
    public Object[] values() {

        Collection<Node<K, V>> array = new ArrayList<>(size);
        if (root != null) {
            fillRecursive(root, array);
        }

        Object[] objects = new Object[size];
        int j = 0;
        for (Node<K, V> node : array) {
            objects[j++] = node.value;
        }

        return objects;
    }

    /**
     * Метод {@link CustomTreeMapImpl<K,V>#toString()}
     * возвращает строковое представление дерева {@link CustomTreeMapImpl<K,V>}
     *
     * @return объект типа String в формате '[ value1, ..., valueN ]
     * или [ ] если дерево пустое.
     */
    @Override
    public String toString() {

        Collection<Node<K, V>> array = new ArrayList<>(size);

        if (root != null) {
            fillRecursive(root, array);
        }

        StringBuilder cb = new StringBuilder();

        cb.append("[ ");
        for (Node<K, V> node : array) {
            cb.append(" {key=" + node.key + ";value=" + node.value + "} ");
        }
        cb.append("]");
        return cb.toString();
    }

    /**
     * Вспомогательный метод {@link CustomTreeMapImpl<K,V>#fillRecursive()}
     * осуществляет заполнение массива знаачениячми дерева.
     */
    private void fillRecursive(Node<K, V> node, Collection<Node<K, V>> array) {
        if (node.left != null) {
            fillRecursive(node.left, array);
        }
        array.add(node);
        if (node.right != null) {
            fillRecursive(node.right, array);
        }
    }
}