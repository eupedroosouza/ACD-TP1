package algorithms;

public class QuickSort {

    private static <T extends Comparable<T>> int partition(T[] A, int p, int r) {
        T pi = A[r];
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (A[j].compareTo(pi) <= 0) {
                i = i + 1;
                T tmp = A[j];
                A[j] = A[i];
                A[i] = tmp;
            }
        }

        T tmp = A[i + 1];
        A[i + 1] = A[r];
        A[r] = tmp;

        return i + 1;
    }

    private static <T extends Comparable<T>> void sort0(T[] A, int p, int r) {
        if (p >= r) {
            return;
        }
        int q = partition(A, p, r);
        sort0(A, p, q - 1);
        sort0(A, q + 1, r);
    }

    public static <T extends Comparable<T>> void sort(T[] A) {
        sort0(A, 0, A.length - 1);
    }

}