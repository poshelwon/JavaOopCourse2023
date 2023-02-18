package ru.javacourse.ryabushkin.array_list_home_main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
        if (list.isEmpty()) {
            throw new IllegalStateException();
        }

        ArrayList<Integer> resultList = new ArrayList<>();

        for (Integer item : list) {
            if (!resultList.contains(item)) {
                resultList.add(item);
            }
        }

        return resultList;
    }

    public static ArrayList<Integer> readStringsFileNumbers(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String string;

        ArrayList<Integer> list = new ArrayList<>();
        while ((string = reader.readLine()) != null) {
            list.add(toInt(string));
        }

        if (list.isEmpty()) {
            throw new IllegalStateException();
        }

        return list;
    }

    private static int toInt(Object obj) throws NumberFormatException {
        return Integer.parseInt(obj.toString());
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\poshelwon\\IdeaProjects\\JavaOopCourse2023\\ArrayListHome\\numbers";

        try {
            ArrayList<Integer> fileLinesList = readStringsFileNumbers(filePath);

            System.out.println("Input list: " + fileLinesList);

            fileLinesList.replaceAll(ArrayListHome::toInt);

            removeEvenNumbers(fileLinesList);

            System.out.println("Not even numbers list: " + fileLinesList);
        } catch (FileNotFoundException e) {
            System.err.println("[File not found]: " + filePath);
        } catch (IllegalStateException e) {
            System.err.println("[Empty file]: " + filePath);
        } catch (NumberFormatException e) {
            System.err.println("[File does not contain numbers]: " + filePath);
        } catch (IOException e) {
            System.err.println("[Input/output exception]");
        }

        ArrayList<Integer> numbersList = new ArrayList<>(Arrays.asList(5, 2, 2, 3, 4, 3, 4, 5, 4));

        System.out.println("List: " + numbersList);

        System.out.print("Unique values list: ");

        try {
            System.out.println(getUniqueValuesList(numbersList));
        } catch (IllegalStateException e) {
            System.err.println("[Empty list]");
        }
    }
}