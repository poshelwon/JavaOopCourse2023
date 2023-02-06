package ru.javacourse.ryabushkin.matrix;

import ru.javacourse.ryabushkin.vector.Vector;

import javax.naming.LimitExceededException;
import java.util.Arrays;

public class Matrix {
    private double determinant;
    private Vector[] matrix;

    public Matrix(int n, int m) {  // +
        matrix = new Vector[n];

        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Sides must be >= 0. N = " + n + ". M = " + m);
        }

        for (int i = 0; i < n; i++) {
            matrix[i] = new Vector(m);
        }
    }

    public Matrix(Matrix matrix) { // +
        int columns = matrix.matrix.length;
        int rows = matrix.matrix[1].getSize();

        for (int i = 0; i < rows; i++) {
            this.matrix = Arrays.copyOf(matrix.matrix, columns);
        }

        this.determinant = 0; //I don`t sure about that.
    }

    public Matrix(double[][] matrix) { // +
        if (matrix.length == 0) {
            throw new IllegalArgumentException("Array is null.");
        }

        int columns = matrix.length;
        int rows = 0;

        this.matrix = new Vector[columns];

        for (double[] doubles : matrix) {
            rows = Math.max(rows, doubles.length);
        }

        for (int i = 0; i < columns; i++) {
            Vector vector = new Vector(rows);

            for (int j = 0; j < matrix[i].length; j++) {
                vector.setComponent(j, matrix[i][j]);
            }

            this.matrix[i] = vector;
        }
    }

    public Matrix(Vector[] vectors) { // +
        if (vectors.length == 0) {
            throw new IllegalArgumentException("Vectors is null");
        }

        int columns = vectors.length;
        int rows = 0;

        this.matrix = new Vector[columns];

        for (Vector vector : vectors) {
            rows = Math.max(rows, vector.getSize());
        }

        for (int i = 0; i < columns; i++) {
            Vector vector = new Vector(rows);

            for (int j = 0; j < vectors[i].getSize(); j++) {
                vector.setComponent(j, vectors[i].getComponent(j));
            }

            this.matrix[i] = vector;
        }
    }

    @Override
    public String toString() { // +
        StringBuilder stringBuilder = new StringBuilder(("{"));

        for (Vector vector : matrix) {
            stringBuilder.append("{");

            for (int j = 0; j < vector.getSize(); j++) {
                stringBuilder.append(vector.getComponent(j)).append(", ");
            }

            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
            stringBuilder.append("},");
        }

        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    public int[] getSize() {
        return new int[]{matrix.length, matrix[0].getSize()};
    }

    public Vector getVector(int index) {
        return matrix[index];
    }

    public void setVector(int index, Vector vector) {
        matrix[index] = vector;
    }

    public Vector getColumnVector(int index) {
        Vector vector = new Vector(matrix.length);

        for (int i = 0; i < matrix.length; i++) {
            vector.setComponent(i, matrix[i].getComponent(index));
        }

        return vector;
    }

    public void transposeMatrix() {
        Matrix resultMatrix = new Matrix(matrix[0].getSize(), matrix.length);

        for (int i = 0; i < matrix[0].getSize(); i++) {
            for (int j = 0; j < matrix.length; j++) {
                resultMatrix.matrix[i] = getColumnVector(i);
            }
        }

        matrix = resultMatrix.matrix;
    }

    public void multiplyByScalar(double scalar) {
        for (Vector vector : matrix) {
            vector.multiplyByScalar(scalar);
        }
    }

    public double getDeterminant() throws LimitExceededException {
        getReduction(this, 1);

        return determinant;
    }

    private void getReduction(Matrix subMinor, double minor) throws LimitExceededException {
        if (subMinor.getSize()[0] != subMinor.getSize()[1]) {
            throw new LimitExceededException("Matrix sides must be similar");
        }

        if (subMinor.getSize()[0] > 1) {
            Matrix tempMinor = new Matrix(subMinor.matrix.length - 1, subMinor.matrix.length - 1);

            for (int k = 0; k < subMinor.getSize()[0]; k++) {
                for (int i = 1; i < subMinor.matrix.length; i++) {
                    for (int j = 0; j < subMinor.matrix[i].getSize(); j++) {
                        if (j < k)
                            tempMinor.matrix[i - 1].setComponent(j, subMinor.matrix[i].getComponent(j));
                        else if (j > k) {
                            tempMinor.matrix[i - 1].setComponent(j - 1, subMinor.matrix[i].getComponent(j));
                        }
                    }
                }

                getReduction(tempMinor, Math.pow(-1, k + 2) * subMinor.matrix[0].getComponent(k) * minor);
            }
        } else {
            determinant += minor * subMinor.matrix[0].getComponent(0);
        }
    }

    public void multiplyByVector(Vector vector) {
        if (matrix[0].getSize() != vector.getSize()) {
            throw new IllegalArgumentException("Vector size must be = array row size. Vector size = "
                    + vector.getSize() + ", Matrix row size = " + matrix[0].getSize());
        }

        for (Vector thisVector : matrix) {
            for (int j = 0; j < thisVector.getSize(); j++) {
                thisVector.setComponent(j, thisVector.getComponent(j) * vector.getComponent(j));
            }
        }
    }

    public void add(Matrix matrix) {
        if (matrix.matrix.length != this.matrix.length || matrix.matrix[0].getSize() != this.matrix[0].getSize()) {
            throw new IllegalArgumentException("Matrix sizes must be similar. "
                    + "This matrix size = " + this.matrix.length + "x" + this.matrix[0].getLength()
                    + ". Argument matrix size = " + matrix.matrix.length + "x" + matrix.matrix[0].getSize() + ".");
        }

        for (int i = 0; i < matrix.matrix.length; i++) {
            this.matrix[i].add(matrix.matrix[i]);
        }
    }

    public void subtract(Matrix matrix) {
        if (matrix.matrix.length != this.matrix.length || matrix.matrix[0].getSize() != this.matrix[0].getSize()) {
            throw new IllegalArgumentException("Matrix sizes must be similar. "
                    + "This matrix size = " + this.matrix.length + "x" + this.matrix[0].getLength()
                    + ". Argument matrix size = " + matrix.matrix.length + "x" + matrix.matrix[0].getSize() + ".");
        }

        for (int i = 0; i < matrix.matrix.length; i++) {
            this.matrix[i].subtract(matrix.matrix[i]);
        }
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        Matrix resultMatrix = new Matrix(matrix1);

        resultMatrix.add(matrix2);

        return resultMatrix;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        Matrix resultMatrix = new Matrix(matrix1);

        resultMatrix.subtract(matrix2);

        return resultMatrix;
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.matrix.length != matrix2.matrix.length || matrix1.matrix[0].getSize() != matrix2.matrix[0].getSize()) {
            throw new IllegalArgumentException("Matrix sizes must be similar. "
                    + "This matrix size = " + matrix2.matrix.length + "x" + matrix2.matrix[0].getLength()
                    + ". Argument matrix size = " + matrix1.matrix.length + "x" + matrix1.matrix[0].getSize() + ".");
        }

        Matrix resultMatrix = new Matrix(matrix1);

        for (int i = 0; i < matrix1.matrix.length; i++) {
            for (int j = 0; j < matrix2.matrix.length; j++) {
                resultMatrix.matrix[i].setComponent(j, matrix1.matrix[i].getComponent(j) * matrix2.matrix[i].getComponent(j));
            }
        }

        return resultMatrix;
    }
}