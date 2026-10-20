package input;

import util.Arguments;
import util.InputUtil;
import util.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class InputGenerator {

    static void main(String[] args) {
        HashMap<String, String> arguments = Arguments.parseArguments(args);

        Util.ArrayType inputType = Arguments.requiredArgument(arguments, Util.ArrayType::valueOf, "--inputType");
        int size = Arguments.requiredArgument(arguments, Integer::parseUnsignedInt, "--size");
        String file = Arguments.requiredArgument(arguments, i -> i, "--file");

        int repeatedCoefficient = Arguments.argument(arguments, Integer::parseInt, 10, "--repeatedCoefficient", "-rc");
        int randomOrigin = Arguments.argument(arguments, Integer::parseInt, 0, "--randomOrigin", "-ro");
        int randomBound = Arguments.argument(arguments, Integer::parseInt, 500, "--randomBound", "-rb");

        Path path = Paths.get(file).normalize().toAbsolutePath();
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException ex) {
            System.err.println("Error while creating parent directories of: " + path);
            ex.printStackTrace(System.err);
            System.exit(1);
            return;
        }

        if (Files.exists(path)) {
            System.err.println("Already exists file: " + path);
            System.exit(1);
            return;
        }

        System.out.println("================================");
        System.out.println("Input type:  " + inputType);
        System.out.println("Size: " + size);
        System.out.println("File: " + file);
        System.out.println("================================");

        Integer[] arr = Util.integerArray(inputType, size, repeatedCoefficient, randomOrigin, randomBound);
        try {
            InputUtil.save(arr, inputType, path);
            System.out.println("Saved input file at " + path);
        } catch (IOException ex) {
            System.err.println("Error saving input file:");
            ex.printStackTrace(System.err);
        }

    }

}
