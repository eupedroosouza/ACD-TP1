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


        Integer[] arr = Util.integerArray(inputType, size, repeatedCoefficient, randomOrigin, randomBound);


        HashMap<Algorithm, Long> timeMeans = new HashMap<>();
        for (Algorithm algorithm : Algorithm.values()) {
            timeMeans.put(algorithm, 0L);
        }
        for (Algorithm algorithm : Algorithm.values()) {
            for (int i = 1; i <= samples; i++) {
                long startTime = System.nanoTime();
                algorithm.getFunction().accept(Arrays.copyOf(arr, arr.length));
                long endTime = System.nanoTime();
                long time = (endTime - startTime);
                timeMeans.put(algorithm, timeMeans.get(algorithm) + time);
            }
            timeMeans.put(algorithm, timeMeans.get(algorithm) / samples);
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
        System.out.println("Input type: " + inputType);
        System.out.println("Samples: " + samples);
        System.out.println("Algorithms: " + Arrays.toString(Algorithm.values()));
        System.out.println("Time mean: ");
        for (Map.Entry<Algorithm, Long> entry : timeMeans.entrySet()) {
            Algorithm algorithm = entry.getKey();
            long timeMean = entry.getValue();
            System.out.println(" " + algorithm + ": " + timeMean + "ns");
        }
        System.out.println("======================================");


    }


}
