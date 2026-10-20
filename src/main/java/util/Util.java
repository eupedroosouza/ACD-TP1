package util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Util {


    public enum ArrayType {
        RANDOM("Aleatório"), REPEATED("Valores repetidos"), CRESCENT("Crescente"), DECRESCENT("Decrescente");

        private final String name;

        ArrayType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static Integer[] integerArray(ArrayType type, int size, int repeatedCoefficient, int randomOrigin, int randomBound) {
        Integer[] arr = (Integer[]) Array.newInstance(Integer.class, size);
        switch (type) {
            case RANDOM -> {
                for (int i = 0; i < size; i++) {
                    arr[i] = ThreadLocalRandom.current().nextInt(randomOrigin, randomBound);
                }
            }
            case REPEATED -> {
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < size; i += repeatedCoefficient) {
                    for (int j = 0; j < repeatedCoefficient; j++) {
                        list.set(i + j, ThreadLocalRandom.current().nextInt(randomOrigin, randomBound));
                    }
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
