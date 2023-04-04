package ru.javacourse.ryabushkin.matrix_main;

import ru.javacourse.ryabushkin.matrix.Matrix;
import ru.javacourse.ryabushkin.vector.Vector;

public class Main {
    public static void main(String[] args){
        Matrix matrix1 = new Matrix(3, 4); // first constructor

        System.out.println("Matrix1 = " + matrix1);

        Matrix matrix2 = new Matrix(matrix1); // second constructor

        System.out.println("Matrix2 = " + matrix2);

        double[][] matrix3Array = {
                {0, 9, 8},
                {7},
                {6, 5},
                {4, 3, 2, 1}
        };

        Matrix matrix3 = new Matrix(matrix3Array);  // third constructor

        System.out.println("Matrix3 = " + matrix3);

        Vector[] matrix4Array = {
                new Vector(new double[]{1, 2, 3}),
                new Vector(new double[]{4, 5, 6, 7}),
                new Vector(new double[]{8, 9})
        };

        Matrix matrix4 = new Matrix(matrix4Array); // fourth constructor

        System.out.println("Matrix4 = " + matrix4 + ", sizes = [" + matrix4.getRowsCount() + ", "
                + matrix4.getColumnsCount() + "]");

        System.out.println();

        Vector vector1 = matrix4.getRow(2);
        vector1.multiplyByScalar(2);
        matrix4.setRow(2, vector1);

        System.out.println("Matrix4 = " + matrix4);

        Vector columnVector = matrix4.getColumn(2);

        System.out.println("Matrix4, column2 = " + columnVector);

        matrix3.transpose();

        System.out.println("Matrix3 transpose. Matrix3 = " + matrix3);

        matrix1.multiplyByScalar(-1);

        System.out.println("Matrix1 multiply by -1. Matrix1 = " + matrix1);

        Vector[] matrix5Array = {
                new Vector(new double[]{1, -20, 3, 11}),
                new Vector(new double[]{16, 5, 6, -8}),
                new Vector(new double[]{7, 8, 9, 1}),
                new Vector(new double[]{7, 8, 9, 14})
        };

        Matrix matrix5 = new Matrix(matrix5Array);

        System.out.println("Matrix5 determinant  = " + matrix5.getDeterminant());

        Vector[] matrix6Array = {
                new Vector(new double[]{1, 2, 3}),
                new Vector(new double[]{4, 5, 6}),
                new Vector(new double[]{7, 8, 9})
        };

        Matrix matrix6 = new Matrix(matrix6Array);

        Vector[] matrix7Array = {
                new Vector(new double[]{1, 2, 3}),
                new Vector(new double[]{4, 5, 6}),
                new Vector(new double[]{7, 8, 9})
        };

        Matrix matrix7 = new Matrix(matrix7Array);

        System.out.println("Matrix6 and matrix7 product = " + Matrix.getProduct(matrix6, matrix7));

        Vector[] matrix8array = {
                new Vector(new double[]{1, 2, 3}),
                new Vector(new double[]{4, 5, 6}),
                new Vector(new double[]{7, 8, 9})
        };

        Matrix matrix8 = new Matrix(matrix8array);

        Vector vector2 = matrix8.getRow(1);

        System.out.println("Matrix8 multiply by vector2 = " + matrix8.multiplyByVector(vector2));

        System.out.println("Matrix sum = " + Matrix.getSum(matrix4, matrix4));

        System.out.println("Matrix difference = " + Matrix.getDifference(matrix5, matrix5));

        System.out.println("Matrix product = " + Matrix.getProduct(matrix6, matrix6));
    }
}