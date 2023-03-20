package ru.javacourse.ryabushkin.list;

public class List<T> {
    /* private int size;
    private ListItem<T> head;

    public List(ListItem<T> item) {
        this.head = item;
        size = 1;
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
        T prevValue = null;
        ListItem<T> item = head;

        int i = 0;

        while (i != index && item != null) {
            prevValue = item.getValue();
            item = item.getNext();

            i++;
        }

        if (item != null) {
            item.setValue(value);

            return prevValue;
        }

        return null;
    }

    public T remove(int index) {
        ListItem<T> prevItem = null;
        T delitedValue = null;
        ListItem<T> item = head;

        int i = 0;

        while (i != index && item != null) { //TODO попробовать подобные while сделать через for с двойным условием.
            prevItem = item.getNext();
            item = item.getNext();

            i++;
        }

        if (item != null && prevItem != null) {
            delitedValue = item.getValue();
            prevItem.setNext(item.getNext());

            size--;

            return delitedValue;
        }

        return null;
    }

    public void beginAdd(ListItem<T> listItem) {
        listItem.setNext(head.getNext());
        head = listItem;

        size++;
    }

    public void indexAdd(int index, ListItem<T> listItem) {
        ListItem<T> item = head;
        ListItem<T> prevItem = null;

        int i = 0;

        while (i != index && item != null) {
            prevItem = item.getNext();
            item = item.getNext();

            i++;
        }

        if (item != null && prevItem != null) {
            prevItem.setNext(listItem);
            listItem.setNext(item);
        }




        prevItem = item;
        item = item.getNext();


    size++;
}

    public boolean removeValue(T value) {
        for (ListItem<T> item = head, prevItem = item; item != null; prevItem = item, item = item.getNext()) {
            if (item.getValue().equals(value)) {
                prevItem.setNext(item.getNext().getNext());

                return true;
            }
        }

        return false;
    }

    public T removeFirst() {
        T value = head.getValue();
        head = head.getNext();

        return value;
    }

*/
}