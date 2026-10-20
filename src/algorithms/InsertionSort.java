package algorithms;

public class InsertionSort {

    private static <T extends Comparable<T>> void sort(T[] A) {
        for (int i = 1; i < A.length; i++) {
            T key = A[i];
            int j = i - 1;
            while (j >= 0 && A[j].compareTo(key) > 0) {

                A[j + 1] = A[j];
                j = j - 1;
            }
            A[j + 1] = key;
        }
    }

}
