package ru.javacourse.ryabushkin.matrix;

import ru.javacourse.ryabushkin.vector.Vector;

public class Matrix {
    private Vector[] rows;

    public Matrix(int rowsCount, int columnsCount) {
        if (rowsCount <= 0 || columnsCount <= 0) {
            throw new IllegalArgumentException("Matrix sizes must be > 0. Rows = " + rowsCount + ". Columns = " + columnsCount);
        }

        rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            rows[i] = new Vector(columnsCount);
        }
    }

    public Matrix(Matrix matrix) { // todo + здесь удобнее использовать конструктор копирования вектора
        int rowsCount = matrix.rows.length;

        rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            rows[i] = new Vector(matrix.rows[i]);
        }
    }

    public Matrix(double[][] array) { // todo + здесь удобнее использовать конструктор вектора с двумя аргументами
        if (array.length == 0 || array[0].length == 0) {
            throw new IllegalArgumentException("Empty array.");
        }

        int rowsCount = array.length;
        int columnsCount = 0;

        rows = new Vector[rowsCount];

        for (double[] line : array) {
            columnsCount = Math.max(columnsCount, line.length);
        }

        for (int i = 0; i < rowsCount; i++) {
            rows[i] = new Vector(columnsCount, array[i]);
        }
    }

    public Matrix(Vector[] vectors) { // todo + можно использовать метод прибавления вектора
        if (vectors.length == 0) {
            throw new IllegalArgumentException("Empty array.");
        }

        int rowsCount = vectors.length;
        int columnsCount = 0;

        rows = new Vector[rowsCount];

        for (Vector vector : vectors) {
            columnsCount = Math.max(columnsCount, vector.getSize());
        }

        for (int i = 0; i < rowsCount; i++) {
            rows[i] = new Vector(Vector.getSum(vectors[i], new Vector(columnsCount)));
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (Vector line : rows) {
            stringBuilder.append(line).append(", ");
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    public int getRowsCount() {
        return rows.length;
    }

    public int getColumnsCount() {
        return getRowsCount();
    }

    // todo getRow + нужно использовать конструктор копирования вектора
    public Vector getRow(int index) { // todo + при проверке индекса нужно проверять выход индекса и за верхнюю границу допустимых значений
        if (index < 0 || index > getRowsCount()) {
            throw new IndexOutOfBoundsException("Index must be > 0 and <= rows count. Index = " + index
                    + ", rows count = " + getRowsCount()); // todo + для выхода индекса за границы нужно использовать более подходящий тип исключения
        }

        return new Vector(rows[index]);
    }

    // todo + setRow нужно использовать конструктор копирования вектора
    public void setRow(int index, Vector vector) { // todo + должна быть проверка что переданный вектор ровно нужного размера
        if (index < 0 || index > getRowsCount()) {
            throw new IndexOutOfBoundsException("Index must be > 0 and <= rows count. Index = " + index
                    + ", rows count = " + getRowsCount()); // todo + для выхода индекса за границы нужно использовать более подходящий тип исключения
        }

        if (vector.getSize() != getColumnsCount()) {
            throw new IllegalArgumentException("Vector`s size must be equal column`s size. Vector`s size = " + vector.getSize()
                    + ", column`s size = " + getColumnsCount());
        }

        Vector resultVector = new Vector(vector);

        rows[index] = resultVector;
    }

    public Vector getColumn(int index) {
        if (index < 0 || index > getRowsCount()) {
            throw new IndexOutOfBoundsException("Index must be > 0 and <= rows count. Index = " + index
                    + ", rows count = " + getRowsCount()); // todo + для выхода индекса за границы нужно использовать более подходящий тип исключения
        }

        Vector vector = new Vector(rows.length);

        for (int i = 0; i < rows.length; i++) {
            vector.setComponent(i, rows[i].getComponent(index));
        }

        return vector;
    }

    public void transpose() { //todo должен работать для неквадратных матриц. Поэтому должен создавать новый массив строк.
        for (int i = 0; i < rows.length; i++) {
            for (int j = i + 1; j < getColumnsCount(); j++) {
                double temp = rows[i].getComponent(j);
                rows[i].setComponent(j, rows[j].getComponent(i));
                rows[j].setComponent(i, temp);
            }
        }
    }

    public void multiplyByScalar(double scalar) {
        for (Vector vector : rows) {
            vector.multiplyByScalar(scalar);
        }
    }

    public double getDeterminant() {
        if (getRowsCount() != getColumnsCount()) {
            throw new UnsupportedOperationException("Matrix sizes must be equal. Rows = " + getRowsCount() + ", columns = " + getColumnsCount());
        }

        return getDeterminant(this, 1, 0);
    }

    private static double getDeterminant(Matrix matrix, double minor, double determinant) { //todo много всего (см письмо)
        if (matrix.getRowsCount() <= 1) {
            determinant += minor * matrix.rows[0].getComponent(0);
        } else {
            Matrix tempMinor = new Matrix(matrix.rows.length - 1, matrix.rows.length - 1); // todo убрать слово temp

            for (int i = 0; i < matrix.getColumnsCount(); i++) {
                for (int j = 1; j < matrix.rows.length; j++) {
                    for (int k = 0; k < matrix.getColumnsCount(); k++) {
                        if (k < i)
                            tempMinor.rows[j - 1].setComponent(k, matrix.rows[j].getComponent(k));
                        else if (k > i) {
                            tempMinor.rows[j - 1].setComponent(k - 1, matrix.rows[j].getComponent(k));
                        }
                    }
                }

                determinant = getDeterminant(tempMinor, Math.pow(-1, i + 2) * matrix.rows[0].getComponent(i) * minor,
                        determinant);
            }
        }

        return determinant;
    }

    public Vector multiplyByVector(Vector vector) { // todo + перименовать в "умножить на вектор" или "получить произведение"
        if (rows.length != vector.getSize()) { // todo проверка не правильная (см письмо)
            throw new IllegalArgumentException("Vector-column size must be equal matrix column size. Vector size = "
                    + vector.getSize() + ", Matrix column size = " + rows.length);
        }
        // todo есть ошибка!!!
        // todo можно оспользовать скалярное произведение векторов
        Vector resultVector = new Vector(rows.length);

        for (int i = 0; i < rows.length; i++) {
            double productRow = 0; //todo не правильное имя т.к. в векторе нет строк

            for (int j = 0; j < getColumnsCount(); j++) {
                productRow += rows[i].getComponent(j) * vector.getComponent(i);
            }

            resultVector.setComponent(i, productRow);
        }

        return resultVector;
    }

    private static boolean areSizesEqual(Matrix matrix1, Matrix matrix2) { // todo пусть сам просает исключение чтобы не дублировать код
        return matrix1.rows.length == matrix2.rows.length
                && matrix1.getColumnsCount() == matrix2.getColumnsCount();
    }

    public void add(Matrix matrix) {
        if (!areSizesEqual(this, matrix)) {
            throw new IllegalArgumentException("Matrix sizes must be equal. "
                    + "This matrix size = " + rows.length + "x" + rows[0].getLength() // todo rows[0].getLength() - ошибка
                    + ". Argument matrix size = " + matrix.rows.length + "x" + matrix.getColumnsCount() + ".");
        }

        for (int i = 0; i < matrix.rows.length; i++) {
            rows[i].add(matrix.rows[i]);
        }
    }

    public void subtract(Matrix matrix) {
        if (!areSizesEqual(this, matrix)) {
            throw new IllegalArgumentException("Matrix sizes must be equal. "
                    + "This matrix size = " + rows.length + "x" + rows[0].getLength() // todo rows[0].getLength() - ошибка
                    + ". Argument matrix size = " + matrix.rows.length + "x" + matrix.getColumnsCount() + ".");
        }

        for (int i = 0; i < matrix.rows.length; i++) {
            rows[i].subtract(matrix.rows[i]);
        }
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        if (!areSizesEqual(matrix1, matrix2)) {
            throw new IllegalArgumentException("Matrix sizes must be equal. "
                    + "Matrix1 size = " + matrix1.rows.length + "x" + matrix1.rows[0].getLength() // todo rows[0].getLength() - ошибка
                    + ". Matrix2 size = " + matrix2.rows.length + "x" + matrix2.getColumnsCount() + ".");
        }

        Matrix resultMatrix = new Matrix(matrix1);

        resultMatrix.add(matrix2);

        return resultMatrix;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        if (!areSizesEqual(matrix1, matrix2)) {
            throw new IllegalArgumentException("Matrix sizes must be equal. "
                    + "Matrix1 size = " + matrix1.rows.length + "x" + matrix1.rows[0].getLength() // todo rows[0].getLength() - ошибка
                    + ". Matrix2 size = " + matrix2.rows.length + "x" + matrix2.getColumnsCount() + ".");
        }

        Matrix resultMatrix = new Matrix(matrix1);

        resultMatrix.subtract(matrix2);

        return resultMatrix;
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) { // todo есть ошибки
        if (!areSizesEqual(matrix1, matrix2)) { // todo не правильная проверка размеров матрицы. совместимость размеров нужно проверять согласно правилам умножения матриц
            throw new IllegalArgumentException("Matrix sizes must be equal. "
                    + "Matrix1 size = " + matrix1.rows.length + "x" + matrix1.rows[0].getLength()
                    + ". Matrix2 size = " + matrix2.rows.length + "x" + matrix2.getColumnsCount() + ".");
        }

        Matrix resultMatrix = new Matrix(matrix1);

        for (int i = 0; i < matrix1.rows.length; i++) {
            for (int j = 0; j < matrix2.rows.length; j++) {
                resultMatrix.rows[i].setComponent(j, matrix1.rows[i].getComponent(j)
                        * matrix2.rows[i].getComponent(j));
            }
        }

        return resultMatrix;
    }
}