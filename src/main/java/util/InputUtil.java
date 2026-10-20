package util;

import input.InputDescription;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class InputUtil {

    public static void save(Integer[] arr, Util.ArrayType inputType, Path path) throws IOException {
        String arrStringed = String.join(";", Arrays.stream(arr).map(Object::toString).toList());
        String parsed = arr.length + ":" + inputType.name() + ":" + arrStringed;
        Files.createFile(path);
        Files.writeString(path, parsed);
    }

    public static InputDescription read(Path path) throws IOException {
        String parsed = Files.readString(path);
        String[] rootSplit = parsed.split(":");
        int size = Integer.parseUnsignedInt(rootSplit[0]);
        Util.ArrayType inputType = Util.ArrayType.valueOf(rootSplit[1]);
        String input = rootSplit[2];
        String[] inputSplit = input.split(";");
        Integer[] arr = new Integer[size];
        for (int i = 0; i < size; i++) {
            arr[i] = Integer.parseInt(inputSplit[i]);
        }
        return new InputDescription(inputType, size, arr);
    }

}
