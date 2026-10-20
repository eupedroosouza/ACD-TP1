package algorithms;

public class SelectionSort {

    public static <T extends Comparable<T>> void sort(T[] A) {
        for (int i = 0; i < A.length; i++) {
            int min = i;
            for (int j = i + 1; j < A.length; j++) {
                T a = A[j];
                T b = A[min];
                if (a.compareTo(b) < 0) {
                    min = j;
                }
            }
            T a = A[i];
            T b = A[min];
            A[min] = a;
            A[i] = b;
        }
    }

}
