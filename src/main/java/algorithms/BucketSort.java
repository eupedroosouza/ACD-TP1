package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BucketSort {


    public static <T extends Number & Comparable<T>> void sort(T[] array) {
        int length = array.length;

        if (length <= 1) {
            return;
        }

        // Encontra o valor mínimo e máximo, utilizado para normalizar os valores entre o intervalo [0, 1)
        T min = array[0];
        T max = array[0];

        for (int i = 1; i < length; i++) {
            if (array[i].compareTo(min) < 0) {
                min = array[i];
            }
            if (array[i].compareTo(max) > 0) {
                max = array[i];
            }
        }

        double minVal = min.doubleValue();
        double maxVal = max.doubleValue();
        double range = maxVal - minVal;
        if (range == 0.0) {
            return;
        }

        List<List<T>> buckets = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            buckets.add(new ArrayList<>());
        }

        for (T value : array) {
            double normalizedValue = (value.doubleValue() - minVal) / range;
            int bucketIdx = (int) (normalizedValue * (length - 1));
            buckets.get(bucketIdx).add(value);
        }

        // Ordenar (utilizando método nativo do Java) e contatenar
        int index = 0;
        for (int i = 0; i < length; i++) {
            List<T> bucket = buckets.get(i);
            Collections.sort(bucket);
            for (T value : bucket) {
                array[index++] = value;
            }
        }
    }
}

