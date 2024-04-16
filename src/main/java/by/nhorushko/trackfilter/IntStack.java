package by.nhorushko.trackfilter;

/**
 * Класс IntStack реализует стек целых чисел на базе массива.
 * не удаляет элементы а просто сдвигает индекс на актуальный элемент
 * Это простая реализация стека, которая предоставляет основные операции стека,
 * такие как push, pop, peek и проверку на пустоту.
 */
public class IntStack {

    // Массив для хранения элементов стека
    private final int[] stack;

    // Индекс верхнего элемента в стеке
    private int top;

    /**
     * Конструктор создает стек с заданной емкостью.
     * @param capacity Максимальное количество элементов, которое может хранить стек.
     */
    public IntStack(int capacity) {
        stack = new int[capacity];
        top = -1;  // Стек пуст
    }

    /**
     * Метод для добавления элемента в стек.
     * @param value Элемент, который будет добавлен в стек.
     */
    public void push(int value) {
        stack[++top] = value;
    }

    /**
     * Метод для извлечения и удаления верхнего элемента из стека.
     * @return Верхний элемент стека.
     */
    public int pop() {
        return stack[top--];
    }

    /**
     * Метод для проверки, пуст ли стек.
     * @return true, если стек пуст, иначе false.
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * Метод для получения верхнего элемента стека без его удаления.
     * @return Верхний элемент стека.
     */
    public int peek() {
        return stack[top];
    }

    /**
     * Метод для получения текущего размера стека.
     * @return Количество элементов в стеке.
     */
    public int size() {
        return top + 1;
    }
}
