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

    public static ArrayList<Integer> getUniqueValuesList(ArrayList<String> list) {
        ArrayList<Integer> resultList = new ArrayList<>(list.size());

        for (String item : list) {
            if (!resultList.contains(convertToInt(item))) {
                resultList.add(convertToInt(item));
            }
        }

        return resultList;
    }

    public static ArrayList<String> readFileStrings(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            ArrayList<String> list = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                list.add(line);
            }

            return list;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("[File not found]: " + path);
        }
    }

    private static int convertToInt(Object obj) throws NumberFormatException {
        return Integer.parseInt(obj.toString());
    }

    public static void main(String[] args) {
        String filePath = "C:\\Users\\poshelwon\\IdeaProjects\\JavaOopCourse2023\\ArrayListHome\\numbers";

        try {
            ArrayList<String> fileLinesList = readFileStrings(filePath);

            System.out.println("Input list: " + fileLinesList);

            ArrayList<Integer> numbersList = new ArrayList<>(fileLinesList.size());

            for (String item : fileLinesList) {
                numbersList.add(convertToInt(item));
            }

            removeEvenNumbers(numbersList);

            System.out.println("Not even numbers list: " + numbersList);
        } catch (FileNotFoundException | IllegalStateException e) {
            System.err.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("[File does not contain numbers]: " + filePath);
        } catch (IOException e) {
            System.err.println("[Input/output exception]");
        }

        filePath = "C:\\Users\\poshelwon\\IdeaProjects\\JavaOopCourse2023\\ArrayListHome\\numbers2";

        try {
            ArrayList<String> fileLinesList = readFileStrings(filePath);

            System.out.println("Input list2: " + fileLinesList);

            System.out.println("Unique values list2: " + getUniqueValuesList(fileLinesList));
        } catch (FileNotFoundException | IllegalStateException e) {
            System.err.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("[File does not contain numbers]: " + filePath);
        } catch (IOException e) {
            System.err.println("[Input/output exception]");
        }
    }
}