package ru.javacourse.ryabushkin.list_main;

import ru.javacourse.ryabushkin.list.List;
import ru.javacourse.ryabushkin.list.ListItem;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = new List<>(new ListItem<>(5)); // constructor
        System.out.println("Created list = " + list); // test toString

        System.out.println("List`s size = " + list.getSize()); // test getSize
        System.out.println("List`s head = " + list.getHead()); // test getHead

        list.setHead(new ListItem<>(3));
        System.out.println("New list`s head = " + list.getHead()); // test setHead

        list.beginAdd(new ListItem<>(7));
        System.out.println("New item add to begin List. List = " + list); // test beginAdd

        System.out.println("Old item`s value  = " + list.setValue(0, 10) + ". New items`s value = "
                + list.getValue(0)); // test setValue and getValue

        list.add(2, new ListItem<>(5));
        System.out.println("Deleted item = " + list.remove(1)); // test remove

        list.add(2, new ListItem<>(6));
        System.out.println("List = " + list); // test add

        System.out.println("Value 6 removed?  " + list.removeValue(6)); // test removeValue

        System.out.println("First item = " + list.removeFirst() + " removed. List = " + list); // test removeFirst

        List<Integer> list2 = list.copy(list);
        System.out.println("List copy to List2. List2 = " + list2); // test copy

        list.add(1, new ListItem<>(0));
        list.reversal();

        System.out.println("Reserval list = " + list); // test reversal
    }
}