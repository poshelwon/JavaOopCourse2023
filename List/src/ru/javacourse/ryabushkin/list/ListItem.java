package ru.javacourse.ryabushkin.list;

public class ListItem<T> {
    private T value;
    private ListItem<T> next;

    public ListItem(T value) {
        this.value = value;
        next = null;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public ListItem<T> getNext() {
        return next;
    }

    public void setNext(ListItem<T> next) {
        this.next = next;
    }
}