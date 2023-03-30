package ru.javacourse.ryabushkin.list;

public class List<T> {
    private int size;
    private ListItem<T> head;

    public List(ListItem<T> item) {
        this.head = item;
        size = 1;
    }

    @Override
    public String toString() {
        ListItem<T> item = head;
        StringBuilder stringBuilder = new StringBuilder();

        while (item != null) {
            stringBuilder.append(item).append(", ");
            item = item.getNext();
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());

        return stringBuilder.toString();
    }

    public int getSize() {
        return size;
    }

    public ListItem<T> getHead() {
        return head;
    }

    public void setHead(ListItem<T> item) {
        head = item;
    }

    public T getValue(int index) {
        ListItem<T> item = head;

        int i = 0;

        while (i != index && item != null) {
            item = item.getNext();
            i++;
        }

        if (item != null) {
            return item.getValue();
        }

        return null;
    }

    public T setValue(int index, T value) {
        if (index > size - 1) {
            System.out.println("Index must be < list size");
            return null;
        }

        ListItem<T> item = head;

        int i = 0;

        while (i != index && item != null) {
            item = item.getNext();
            i++;
        }

        if (item != null) {
            T prevValue = item.getValue();
            item.setValue(value);

            return prevValue;
        }

        return null;
    }

    public T remove(int index) {
        if (index >= size) {
            throw new IllegalArgumentException("Index must be < size. Index = " + index + ", size = " + size);
        }

        ListItem<T> item = head;
        ListItem<T> prevItem = null;

        for (int i = 0; item != null && i < index; i++) {
            prevItem = item;
            item = item.getNext();
        }

        if (item != null && prevItem != null) {
            prevItem.setNext(item.getNext());
            size--;

            return item.getValue();
        }

        return null;
    }

    public void beginAdd(ListItem<T> listItem) {
        listItem.setNext(getHead());
        head = listItem;

        size++;
    }

    public void add(int index, ListItem<T> listItem) {
        if (index > size) {
            throw new IllegalArgumentException("Index must be <= size. Index = " + index + ", size = " + size);
        }

        if (listItem == null) {
            throw new IllegalArgumentException("List must not be = null");
        }

        ListItem<T> prevItem = head;

        for (int i = 1; i <= size; i++) {
            if (i == index || i == size) {
                listItem.setNext(prevItem.getNext());
                prevItem.setNext(listItem);
                size++;

                break;
            }

            prevItem = prevItem.getNext();
        }
    }

    public boolean removeValue(T value) {
        for (ListItem<T> item = head, prevItem = item; item != null; prevItem = item, item = item.getNext()) {
            if (item.getValue().equals(value)) {
                prevItem.setNext(item.getNext());
                size--;

                return true;
            }
        }

        return false;
    }

    public T removeFirst() {
        T value = head.getValue();
        head = head.getNext();
        size--;

        return value;
    }

    public List<T> copy(List<T> list) {
        List<T> copy = new List<>(new ListItem<>(list.getValue(0)));

        for (ListItem<T> item = list.getHead().getNext(); item != null; item = item.getNext()) {
            copy.add(copy.size, item);
        }

        return copy;
    }

    public void reversal() {
        if (size < 2) {
            return;
        }

        for (ListItem<T> item = head, prevItem = null, tempItem; item != null; prevItem = item, head = item, item = tempItem) {
            tempItem = item.getNext();
            item.setNext(prevItem);
        }
    }
}