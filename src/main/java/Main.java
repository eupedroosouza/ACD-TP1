import algorithms.Algorithm;
import benchmark.SortBenchmark;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import util.Arguments;
import util.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {

    static void main(String[] args) {
        HashMap<String, String> arguments = Arguments.parseArguments(args);

        Algorithm algorithm = Arguments.requiredArgument(arguments, Algorithm::valueOf, "--algorithm");
        Util.ArrayType inputType = Arguments.requiredArgument(arguments, Util.ArrayType::valueOf, "--inputType");
        int size = Arguments.requiredArgument(arguments, Integer::parseUnsignedInt, "--size");
        int samples = Arguments.requiredArgument(arguments, Integer::parseInt, "--samples");
        long seed = Arguments.argument(arguments, Long::parseLong, Util.SEED, "--seed");
        int repeatedCoefficient = Arguments.argument(arguments, Integer::parseInt, 10, "--repeatedCoefficient");
        int randomOrigin = Arguments.argument(arguments, Integer::parseInt, 0, "--randomOrigin");
        int randomBound = Arguments.argument(arguments, Integer::parseInt, 500, "--randomBound");
        String resultFile = Arguments.requiredArgument(arguments, i -> i, "--result");


        if (!testAlgorithms()) {
            return;
        }

        // Dynamic Warmup to save time
        int warmupCount;
        if (size <= 1000) {
            warmupCount = 256;
        } else if (size <= 10000) {
            warmupCount = 128;
        } else if (size <= 100000) {
            warmupCount = 64;
        } else {
            warmupCount = 32;
        }

        Options opt = new OptionsBuilder()
                .include(SortBenchmark.class.getSimpleName())
                .param("algorithmName", algorithm.name())
                .param("inputType", inputType.name())
                .param("size", String.valueOf(size))
                .param("seed", String.valueOf(seed))
                .param("repeatedCoefficient", String.valueOf(repeatedCoefficient))
                .param("randomOrigin", String.valueOf(randomOrigin))
                .param("randomBound", String.valueOf(randomBound))
                .warmupIterations(warmupCount)
                .warmupBatchSize(1)
                .measurementIterations(samples)
                .measurementBatchSize(1)
                .forks(1)
                .resultFormat(ResultFormatType.JSON)
                .result(resultFile)
                .build();

        try {
            new Runner(opt).run();
        } catch (RunnerException ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }

    static boolean testAlgorithms() {
        Map<Util.ArrayType, Integer[]> arrays = Map.of(
                Util.ArrayType.RANDOM, Util.integerArray(Util.ArrayType.RANDOM, Util.SEED, 500, 10, 0, 500),
                Util.ArrayType.REPEATED, Util.integerArray(Util.ArrayType.REPEATED, Util.SEED, 500, 10, 0, 500),
                Util.ArrayType.CRESCENT, Util.integerArray(Util.ArrayType.CRESCENT, Util.SEED, 500, 10, 0, 500),
                Util.ArrayType.DECRESCENT, Util.integerArray(Util.ArrayType.DECRESCENT, Util.SEED, 500, 10, 0, 500)
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
        }
        return true;
    }

}
