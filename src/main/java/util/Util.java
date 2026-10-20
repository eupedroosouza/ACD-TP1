package util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

    private static final int RANDOM_ORIGIN = 0;
    private static final int RANDOM_BOUND = 500;
    private static final int REPEATED_COEFFICIENT = 10; // how many times should each random element be repeated

    public enum ArrayType {
        RANDOM, REPEATED, CRESCENT, DECRESCENT
    }

    public static Integer[] integerArray(ArrayType type, int size) {
        Integer[] arr = (Integer[]) Array.newInstance(Integer.class, size);
        switch (type) {
            case RANDOM -> {
                for (int i = 0; i < size; i++) {
                    arr[i] = ThreadLocalRandom.current().nextInt(RANDOM_ORIGIN, RANDOM_BOUND);
                }
            }
            case REPEATED -> {
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < size; i += REPEATED_COEFFICIENT) {
                    list.set(i, ThreadLocalRandom.current().nextInt(RANDOM_ORIGIN, RANDOM_BOUND));
                }
                Collections.shuffle(list);
                arr = list.toArray(Integer[]::new);
            }
            case CRESCENT -> {
                for (int i = 0; i < size; i++) {
                    arr[i] = i + 1;
                }
            }
            case DECRESCENT -> {
                for (int i = size - 1; i >= 0; i--) {
                    arr[i] = i + 1;
                }
            }
        }
        return arr;
    }


}
