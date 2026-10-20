package util;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

public class Arguments {

    public static HashMap<String, String> parseArguments(String[] args) {
        if ((args.length % 2) > 0) {
            System.err.println("Invalid arguments: " + String.join(" ", args));
            System.exit(1);
            return null;
        }
        HashMap<String, String> arguments = new HashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            arguments.put(args[i], args[i + 1]);
        }
        return arguments;
    }

    public static <T> T argument(HashMap<String, String> arguments, Function<String, T> parser, T defaultValue, String... args) {
        for (String arg : args) {
            if (arguments.containsKey(arg)) {
                try {
                    return (Optional.ofNullable(arguments.get(arg)).map(parser).orElse(defaultValue));
                } catch (Exception ex) {
                    System.err.println("Invalid argument: " + arg);
                    ex.printStackTrace(System.err);
                }
            }
        }
        return defaultValue;
    }

    public static <T> T requiredArgument(HashMap<String, String> arguments, Function<String, T> parser, String... args) {
        for (String arg : args) {
            String value = arguments.get(arg);
            if (value != null) {
                try {
                    return parser.apply(value);
                } catch (Exception ex) {
                    System.err.println("Invalid argument: " + arg);
                    ex.printStackTrace(System.err);
                }
            }
        }
        System.err.println("Required argument: [" + String.join(", ", args) + "]");
        System.exit(1);
        return null;
    }

}
