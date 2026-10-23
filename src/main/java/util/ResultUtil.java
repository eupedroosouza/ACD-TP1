package util;

import algorithms.Algorithm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultUtil {

    public static void save(Path resultsFile, HashMap<Algorithm, Long> results) throws IOException {
        if (!Files.exists(resultsFile)) {
            Files.createFile(resultsFile);
        }

        List<String> lines = new ArrayList<>();
        for (Map.Entry<Algorithm, Long> entry : results.entrySet()) {
            Algorithm algorithm = entry.getKey();
            long timeMean = entry.getValue();
            String line = algorithm.name() + "," + timeMean;
            lines.add(line);
        }

        String flat = String.join("\n", lines);
        Files.writeString(resultsFile, flat);
    }

}
