package ru.javacourse.ryabushkin.vector_main;

import ru.javacourse.ryabushkin.vector.Vector;

public class Main {
    public static void main(String[] args) {
        Vector vector1 = new Vector(5);

        double[] vector2Array = {1, 2, 3};

        Vector vector2 = new Vector(vector2Array);

        Vector vector3 = new Vector(vector2);

        int size = 5;

        double[] vector4Array = {1, 2, 3, 4};

        Vector vector4 = new Vector(size, vector4Array);

        System.out.println("vector1 = " + vector1);
        System.out.println("vector2 = " + vector2);
        System.out.println("vector3 = " + vector3);
        System.out.println("vector4 = " + vector4);

        System.out.println("Vector1 and 2 sum = " + Vector.getSum(vector1, vector2));

        System.out.println("Vector1 and 2 difference = " + Vector.getDifference(vector1, vector2));

        System.out.println("Vector2 and 3 scalar product = " + Vector.getScalarProduct(vector2, vector3));

        System.out.println("Vector2 and 3 compare = " + vector3.equals(vector2));

        System.out.println("Vector2 and 3 hash-code compare = " + (vector3.hashCode() == vector2.hashCode()));

        vector1.add(vector4);
        System.out.println("Vector1 add vector4. Vector1 = " + vector1);

        vector2.subtract(vector1);
        System.out.println("Vector2 subtract vector1. vector2 = " + vector2);

        vector1.multiplyByScalar(2);
        System.out.println("Vector1 multiply by 2. vector1 = " + vector1);

        vector2.reverse();
        System.out.println("Vector2 reverse . Vector2 = " + vector2);

        double component = vector2.getComponent(0);

        vector1.setComponent(1, component);
        System.out.println("Vector2 get component 0 and set component 1 to vector1. vector1 = "
                + vector1);

        System.out.println("Vector1 length = " + vector1.getLength());
    }
}