package algorithms;

import java.lang.reflect.Array;

public class CountingSort {

    public static <T extends Number & Comparable<T>> void sort(Class<T> clazz, T[] array) {

        int n = array.length;
        if (n == 0) return;

        T k = array[0];
        for (int i = 1; i < n; i++) {
            if (array[i].compareTo(k) > 0) {
                k = array[i];
            }
        }

        int maxVal = k.intValue();

        int[] C = new int[maxVal + 1];
        T[] B = (T[]) Array.newInstance(clazz, n);

        for (int j = 0; j < n; j++) {
            C[array[j].intValue()]++;
        }


        for (int i = 1; i <= maxVal; i++) {
            C[i] += C[i - 1];
        }

        for (int j = n - 1; j >= 0; j--) {
            int val = array[j].intValue();
            B[C[val] - 1] = array[j];
            C[val]--;
        }


        for (int i = 0; i < n; i++) {
            array[i] = B[i];
        }

    }


}
