package algorithms;

public class BubbleSort {

    public static <T extends Comparable<T>> void sort(T[] A) {
        boolean change = true;
        final int lastIndex = A.length - 1;
        for (int i = 0; i < lastIndex && change; i++) {
            change = false;
            for (int j = lastIndex; j > i; j--) {
                int neighborIdx = j - 1;
                if (A[j].compareTo(A[neighborIdx]) < 0) {
                    change = true;
                    final T tmp = A[j];
                    A[j] = A[neighborIdx];
                    A[neighborIdx] = tmp;
                }
            }
        }
    }

}
