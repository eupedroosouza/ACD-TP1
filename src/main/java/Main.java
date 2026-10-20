import algorithms.Algorithm;
import input.InputDescription;
import util.Arguments;
import util.InputUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

public class Main {

    static void main(String[] args) {
        HashMap<String, String> arguments = Arguments.parseArguments(args);

        String inputFile = Arguments.requiredArgument(arguments, i -> i, "--inputFile");
        Algorithm algorithm = Arguments.requiredArgument(arguments, Algorithm::valueOf, "--algorithm");
        int samples = Arguments.requiredArgument(arguments, Integer::parseInt, "--samples");

        Path path = Paths.get(inputFile).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            System.err.println("Input file not found at: " + path);
            System.exit(1);
            return;
        }

        InputDescription input;
        try {
            input = InputUtil.read(path);
        } catch (IOException ex) {
            System.err.println("Error on read input file: " + path);
            ex.printStackTrace(System.err);
            System.exit(1);
            return;
        }


        long timeMean = 0;
        for (int i = 1; i <= samples; i++) {
            long startTime = System.nanoTime();
            algorithm.getFunction().accept(input.arr());
            long endTime = System.nanoTime();
            timeMean += (endTime - startTime);
        }
        timeMean /= samples;

        System.out.println("======================================");
        System.out.println("Input file: " + path);
        System.out.println("Input type: " + input.inputType());
        System.out.println("Samples: " + samples);
        System.out.println("Algorithm: " + algorithm);
        System.out.println("Time: " + timeMean + "ns");
        System.out.println("======================================");

    }


}
