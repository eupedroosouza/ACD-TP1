package algorithms;

import java.lang.reflect.Array;

public class MergeSort {

    public static <T extends Comparable<T>> void sort(Class<T> clazz, T[] array) {

        int length = array.length;
        if (length <= 1) { // se o array for <= 1 não tem pq seguir dividindo ele
            return;
        }

        int middle = length / 2;
        T[] leftArray = (T[]) Array.newInstance(clazz, middle);
        T[] rightArray = (T[]) Array.newInstance(clazz, length - middle);

        int i = 0; // left array
        int j = 0; // right array

        for (; i < length; i++) { // percorre todo o array
            if (i < middle) {
                leftArray[i] = array[i];
            } else {
                rightArray[j] = array[i];
                j++;
            }
        }
        sort(clazz, leftArray);
        sort(clazz, rightArray);
        merge(leftArray, rightArray, array);

    }

    private static <T extends Comparable<T>> void merge(T[] leftArray, T[] rightArray, T[] array) {

        int leftSize = array.length / 2;
        int rightSize = array.length - leftSize;
        int i = 0, l = 0, r = 0;
        // i = array original
        // l = array esquerda
        // r = array direita

        // checando condições pra merging
        while (l < leftSize && r < rightSize) {
            if (leftArray[l].compareTo(rightArray[r]) < 0) {
                array[i] = leftArray[l];
                i++;
                l++;
            } else {
                array[i] = rightArray[r];
                i++;
                r++;
            }
        }
        while (l < leftSize) {
            array[i] = leftArray[l];
            i++;
            l++;
        }
        while (r < rightSize) {
            array[i] = rightArray[r];
            i++;
            r++;
        }

    }

}
