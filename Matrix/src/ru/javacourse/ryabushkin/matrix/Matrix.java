package ru.javacourse.ryabushkin.matrix;

import ru.javacourse.ryabushkin.vector.Vector;

public class Matrix {
    private final Vector[] matrixRows;

    public Matrix(int rowsCount, int columnsCount) {
        if (rowsCount <= 0 || columnsCount <= 0) {
            throw new IllegalArgumentException("Matrix sizes must be >= 0. N = " + rowsCount + ". M = " + columnsCount);
        }

        matrixRows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            matrixRows[i] = new Vector(columnsCount);
        }
    }

    public Matrix(Matrix matrix) {
        int rowsCount = matrix.matrixRows.length;
        int columnsCount = matrix.matrixRows[0].getSize();

        matrixRows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            matrixRows[i] = new Vector(columnsCount);

            for (int j = 0; j < columnsCount; j++) {
                matrixRows[i].setComponent(j, matrix.matrixRows[i].getComponent(j));
            }
        }
    }

    public Matrix(double[][] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Empty array.");
        }

        int rowsCount = array.length;
        int columnsCount = 0;

        matrixRows = new Vector[rowsCount];

        for (double[] doubles : array) {
            columnsCount = Math.max(columnsCount, doubles.length);
        }

        for (int i = 0; i < rowsCount; i++) {
            Vector vector = new Vector(columnsCount);

            for (int j = 0; j < array[i].length; j++) {
                vector.setComponent(j, array[i][j]);
            }

            matrixRows[i] = vector;
        }
    }

    public Matrix(Vector[] vectors) {
        if (vectors.length == 0) {
            throw new IllegalArgumentException("Empty vector.");
        }

        int rowsCount = vectors.length;
        int columnsCount = 0;

        this.matrixRows = new Vector[rowsCount];

        for (Vector vector : vectors) {
            columnsCount = Math.max(columnsCount, vector.getSize());
        }

        for (int i = 0; i < rowsCount; i++) {
            Vector vector = new Vector(columnsCount);

            for (int j = 0; j < vectors[i].getSize(); j++) {
                vector.setComponent(j, vectors[i].getComponent(j));
            }

            this.matrixRows[i] = vector;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(("{"));

        for (Vector vector : matrixRows) {
            stringBuilder.append(vector).append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    public int getRowsCount() {
        return matrixRows.length;
    }

    public int getColumnsCount() {
        return matrixRows[0].getSize();
    }

    public Vector getRow(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index must be > 0. Index = " + index);
        }

        Vector resultVector = new Vector(matrixRows[0].getSize());

        for (int i = 0; i < matrixRows[index].getSize(); i++) {
            resultVector.setComponent(i, matrixRows[index].getComponent(i));
        }

        return resultVector;
    }

    public void setRow(int index, Vector vector) {
        if (index < 0) {
            throw new IllegalArgumentException("Index must be > 0. Index = " + index);
        }

        Vector resultVector = new Vector(vector.getSize());

        for (int i = 0; i < vector.getSize(); i++) {
            resultVector.setComponent(i, vector.getComponent(i));
        }

        matrixRows[index] = resultVector;
    }

    public Vector getColumn(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index must be > 0. Index = " + index);
        }

        Vector vector = new Vector(matrixRows.length);

        for (int i = 0; i < matrixRows.length; i++) {
            vector.setComponent(i, matrixRows[i].getComponent(index));
        }

        return vector;
    }

    public void transpose() {
        if (matrixRows.length != matrixRows[0].getSize()) {
            throw new UnsupportedOperationException("Matrix sizes must be equal");
        }

        for (int i = 0; i < matrixRows.length; i++) {
            for (int j = i + 1; j < matrixRows[0].getSize(); j++) {
                double temp = matrixRows[i].getComponent(j);
                matrixRows[i].setComponent(j, matrixRows[j].getComponent(i));
                matrixRows[j].setComponent(i, temp);
            }
        }
    }

    public void multiplyByScalar(double scalar) {
        for (Vector vector : matrixRows) {
            vector.multiplyByScalar(scalar);
        }
    }

    public double getDeterminant() {
        if (getRowsCount() != getColumnsCount()) {
            throw new UnsupportedOperationException("Matrix sizes must be equal.");
        }

        double determinant = 0;

        return getDeterminant(this, 1, determinant);
    }

    private double getDeterminant(Matrix matrix, double minor, double determinant) {
        if (matrix.getRowsCount() > 1) {
            Matrix tempMinor = new Matrix(matrix.matrixRows.length - 1, matrix.matrixRows.length - 1);

            for (int i = 0; i < matrix.matrixRows[0].getSize(); i++) {
                for (int j = 1; j < matrix.matrixRows.length; j++) {
                    for (int k = 0; k < matrix.matrixRows[0].getSize(); k++) {
                        if (k < i)
                            tempMinor.matrixRows[j - 1].setComponent(k, matrix.matrixRows[j].getComponent(k));
                        else if (k > i) {
                            tempMinor.matrixRows[j - 1].setComponent(k - 1, matrix.matrixRows[j].getComponent(k));
                        }
                    }
                }

                determinant = getDeterminant(tempMinor, Math.pow(-1, i + 2) * matrix.matrixRows[0].getComponent(i) * minor,
                        determinant);
            }
        } else {
            determinant += minor * matrix.matrixRows[0].getComponent(0);
        }

        return determinant;
    }

    public Vector getMultiplyByVector(Vector vector) {
        if (matrixRows.length != vector.getSize()) {
            throw new IllegalArgumentException("Vector-column size must be equal matrix column size. Vector size = "
                    + vector.getSize() + ", Matrix column size = " + matrixRows.length);
        }

        Vector resultVector = new Vector(matrixRows.length);

        for (int i = 0; i < matrixRows.length; i++) {
            double productRow = 0;

            for (int j = 0; j < matrixRows[0].getSize(); j++) {
                productRow += matrixRows[i].getComponent(j) * vector.getComponent(i);
            }

            resultVector.setComponent(i, productRow);
        }

        return resultVector;
    }

    private static boolean isSizesEqual(Matrix matrix1, Matrix matrix2) {
        return matrix1.matrixRows.length == matrix2.matrixRows.length &&
                matrix1.matrixRows[0].getSize() == matrix2.matrixRows[0].getSize();
    }

    public void add(Matrix matrix) {
        if (!isSizesEqual(this, matrix)) {
            throw new IllegalArgumentException("Matrix sizes must be equal. "
                    + "This matrix size = " + matrixRows.length + "x" + matrixRows[0].getLength()
                    + ". Argument matrix size = " + matrix.matrixRows.length + "x" + matrix.matrixRows[0].getSize() + ".");
        }

        for (int i = 0; i < matrix.matrixRows.length; i++) {
            this.matrixRows[i].add(matrix.matrixRows[i]);
        }
    }

    public void subtract(Matrix matrix) {
        if (!isSizesEqual(this, matrix)) {
            throw new IllegalArgumentException("Matrix sizes must be equal. "
                    + "This matrix size = " + matrixRows.length + "x" + matrixRows[0].getLength()
                    + ". Argument matrix size = " + matrix.matrixRows.length + "x" + matrix.matrixRows[0].getSize() + ".");
        }

        for (int i = 0; i < matrix.matrixRows.length; i++) {
            this.matrixRows[i].subtract(matrix.matrixRows[i]);
        }
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        if (!isSizesEqual(matrix1, matrix2)) {
            throw new IllegalArgumentException("Matrix sizes must be equal. "
                    + "Matrix1 size = " + matrix1.matrixRows.length + "x" + matrix1.matrixRows[0].getLength()
                    + ". Matrix2 size = " + matrix2.matrixRows.length + "x" + matrix2.matrixRows[0].getSize() + ".");
        }

        Matrix resultMatrix = new Matrix(matrix1);

        resultMatrix.add(matrix2);

        return resultMatrix;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        if (!isSizesEqual(matrix1, matrix2)) {
            throw new IllegalArgumentException("Matrix sizes must be equal. "
                    + "Matrix1 size = " + matrix1.matrixRows.length + "x" + matrix1.matrixRows[0].getLength()
                    + ". Matrix2 size = " + matrix2.matrixRows.length + "x" + matrix2.matrixRows[0].getSize() + ".");
        }

        Matrix resultMatrix = new Matrix(matrix1);

        resultMatrix.subtract(matrix2);

        return resultMatrix;
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (!isSizesEqual(matrix1, matrix2)) {
            throw new IllegalArgumentException("Matrix sizes must be equal. "
                    + "Matrix1 size = " + matrix1.matrixRows.length + "x" + matrix1.matrixRows[0].getLength()
                    + ". Matrix2 size = " + matrix2.matrixRows.length + "x" + matrix2.matrixRows[0].getSize() + ".");
        }

        Matrix resultMatrix = new Matrix(matrix1);

        for (int i = 0; i < matrix1.matrixRows.length; i++) {
            for (int j = 0; j < matrix2.matrixRows.length; j++) {
                resultMatrix.matrixRows[i].setComponent(j, matrix1.matrixRows[i].getComponent(j) * matrix2.matrixRows[i].getComponent(j));
            }
        }

        return resultMatrix;
    }
}