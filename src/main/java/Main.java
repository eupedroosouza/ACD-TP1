import algorithms.Algorithm;
import util.Arguments;
import util.ResultUtil;
import util.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {

    static void main(String[] args) {
        if (!testAlgorithms()) {
            return;
        }

        HashMap<String, String> arguments = Arguments.parseArguments(args);

        Util.ArrayType inputType = Arguments.requiredArgument(arguments, Util.ArrayType::valueOf, "--inputType");
        int size = Arguments.requiredArgument(arguments, Integer::parseUnsignedInt, "--size");
        int samples = Arguments.requiredArgument(arguments, Integer::parseInt, "--samples");
        int repeatedCoefficient = Arguments.argument(arguments, Integer::parseInt, 10, "--repeatedCoefficient", "-rc");
        int randomOrigin = Arguments.argument(arguments, Integer::parseInt, 0, "--randomOrigin", "-ro");
        int randomBound = Arguments.argument(arguments, Integer::parseInt, 500, "--randomBound", "-rb");
        String resultsFile = Arguments.requiredArgument(arguments, i -> i, "--resultsFile");

        Path path = Paths.get(resultsFile).normalize().toAbsolutePath();
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException ex) {
            System.err.println("Error while creating parent directories of: " + path);
            ex.printStackTrace(System.err);
            System.exit(1);
            return;
        }


        System.out.println("======================================");
        System.out.println("Input type: " + inputType);
        System.out.println("Samples: " + samples);
        System.out.println("Algorithms: " + Arrays.toString(Algorithm.values()));
        System.out.println("Time mean: ");
        Integer[] arr = Util.integerArray(inputType, size, repeatedCoefficient, randomOrigin, randomBound);


        HashMap<Algorithm, Long> timeMeans = new HashMap<>();
        for (Algorithm algorithm : Algorithm.values()) {
            timeMeans.put(algorithm, 0L);
        }
        for (Algorithm algorithm : Algorithm.values()) {
            for (int i = 1; i <= samples; i++) {
                long startTime = System.nanoTime();
                try {
                    algorithm.getFunction().accept(Arrays.copyOf(arr, arr.length));
                } catch (Exception ex) {
                    System.err.println("Fail running " + algorithm + ", skipping, but continue the others algorithms. See console...");
                    ex.printStackTrace(System.err);
                    timeMeans.put(algorithm, -1L);
                    continue;
                }
                long endTime = System.nanoTime();
                long time = (endTime - startTime);
                timeMeans.put(algorithm, timeMeans.get(algorithm) + time);

            }
            timeMeans.put(algorithm, timeMeans.get(algorithm) / samples);
            System.out.println(" " + algorithm + ": " + timeMeans.get(algorithm) + "ns");

            try {
                // Save parcial results to preserve results
                ResultUtil.save(path, timeMeans);
            } catch (IOException ex) {
                System.err.println("Fail save results file at: " + path);
                ex.printStackTrace(System.err);
                System.exit(1);
                return;
            }

        }
        System.out.println("======================================");


    }

    static boolean testAlgorithms() {
        System.out.println("======= Testings Algorithms =======");

        Map<Util.ArrayType, Integer[]> arrays = Map.of(
                Util.ArrayType.RANDOM, Util.integerArray(Util.ArrayType.RANDOM, 500, 10, 0, 500),
                Util.ArrayType.REPEATED, Util.integerArray(Util.ArrayType.REPEATED, 500, 10, 0, 500),
                Util.ArrayType.CRESCENT, Util.integerArray(Util.ArrayType.CRESCENT, 500, 10, 0, 500),
                Util.ArrayType.DECRESCENT, Util.integerArray(Util.ArrayType.DECRESCENT, 500, 10, 0, 500)
        );

        for (Algorithm algorithm : Algorithm.values()) {
            for (Util.ArrayType arrayType : Util.ArrayType.values()) {
                Integer[] originalArray = arrays.get(arrayType);
                Integer[] array = Arrays.copyOf(originalArray, originalArray.length);
                algorithm.getFunction().accept(array);
                boolean status = Util.checkOrderedByArrays(originalArray, array);
                if (!status) {
                    System.out.println(algorithm + " FAILED");
                    System.exit(1);
                    return false;
                }
            }
            System.out.println(algorithm.toString() + " OK!");
        }
        System.out.println("===================================");
        System.out.println("All algorithms is OK!");
        return true;
    }

}
