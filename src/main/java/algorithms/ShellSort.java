package algorithms;

public class ShellSort {

    public static <T extends Comparable<T>> void sort(T[] A) {
        int n = A.length;
        int h = 1;
        do {
            h = (h * 3) + 1;
        } while (h < n);
        do {
            h = h / 3;

            for (int i = h; i < n; i++) {
                T x = A[i];
                int j = i;
                T b;
                while ((b = A[j - h]).compareTo(x) > 0) {
                    A[j] = b;
                    j = j - h;
                    if (j < h) {
                        break;
                    }
                }

                A[j] = x;
            }
        } while (h != 1);

    }

}
