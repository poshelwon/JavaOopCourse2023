package ru.javacourse.ryabushkin.array_list_home_main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class ArrayListHome {
    public static void removeEvenNumbers(ArrayList<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) % 2 == 0) {
                list.remove(i);
                i--;
            }
        }
    }

    public static ArrayList<Integer> getUniqueValuesList(ArrayList<Integer> list) {
        ArrayList<Integer> resultList = new ArrayList<>();

        try {
            for (Integer item : list) {
                if (!resultList.contains(item)) {
                    resultList.add(item);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("List is null");
        }

        return resultList;
    }

    public static ArrayList<Integer> readFileNumbers(String path) throws IOException {
        ArrayList<Integer> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String string;

            while ((string = reader.readLine()) != null) {
                list.add(toInt(string));
            }

            return list;
        } catch (IOException e) {
            System.out.println("File not found");

            return null;
        }
    }

    private static int toInt(Object obj) {
        return Integer.parseInt(obj.toString());
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\poshelwon\\IdeaProjects\\JavaOopCourse2023\\ArrayListHome\\numbers";

        try {
            ArrayList<Integer> fileStringsList = readFileNumbers(filePath);

            System.out.println("Input list: " + fileStringsList);

            if (fileStringsList != null) {
                fileStringsList.replaceAll(ArrayListHome::toInt);

                removeEvenNumbers(fileStringsList);

                System.out.println("No-even list: " + fileStringsList);
            }
        } catch (IOException ignored) {}

        ArrayList<Integer> numbersList =  new ArrayList<>(Arrays.asList(5, 2, 2, 3, 4, 3, 4, 5, 4)); /* null; */

        System.out.println("List: " + numbersList);

        System.out.println("Unique values list: " + getUniqueValuesList(numbersList));
    }
}