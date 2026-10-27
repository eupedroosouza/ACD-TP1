package util;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Util {

    public static final long SEED = 8472910482910L;

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

    public static Integer[] integerArray(ArrayType type, long seed, int size, int repeatedCoefficient, int origin, int bound) {
        Integer[] arr = (Integer[]) Array.newInstance(Integer.class, size);
        switch (type) {
            case RANDOM -> {
                Random random = new Random(seed);
                for (int i = 0; i < size; i++) {
                    arr[i] = random.nextInt(origin, bound);
                }
            }
            case REPEATED -> {
                Random random = new Random(seed);
                List<Integer> list = new ArrayList<>(size);
                for (int i = 0; i < size; i++) { // initialize list
                    list.add(0);
                }
                for (int i = 0; i < size; i += repeatedCoefficient) {
                    int repeatedValue = random.nextInt(origin, bound);
                    for (int j = 0; j < repeatedCoefficient; j++) {
                        int idx = i + j;
                        if (idx < size) {
                            list.set(i + j, repeatedValue);
                        }
                    }
                }
                Collections.shuffle(list, random);
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

    public static <T extends Comparable<T>> String arrayToString(T[] vec) {
        List<String> entries = new ArrayList<>();
        for (int i = 0; i < vec.length; i++) {
            T entry = vec[i];
            entries.add(i + ": " + entry.toString());
        }
        return "[" + String.join(", ", entries) + "]";
    }

    public static String listToString(List<?> list) {
        List<String> entries = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Object entry = list.get(i);
            entries.add(i + ": " + entry.toString());
        }

        return "[" + String.join(", ", entries) + "]";
    }

    // Check if was ordered correctly with comparing with Java Streams native sort
    public static <T extends Comparable<T>> boolean checkOrderedByArrays(T[] orig, T[] ordered) {
        List<T> orderedByStreams = Arrays.stream(orig).sorted().toList();
        boolean ord = true;
        for (int i = 0; i < orig.length; i++) {
            if (orderedByStreams.get(i).compareTo(ordered[i]) != 0) {
                ord = false;
                System.out.println(" Position: " + i);
                System.out.println(" Original: " + arrayToString(orig));
                System.out.println(" Ordered by Streams: " + listToString(orderedByStreams));
                System.out.println(" Ordered by implemente algorithm: " + arrayToString(ordered));
                break;
            }
        }
        return ord;

    }

}
