package benchmark;

import algorithms.Algorithm;
import org.openjdk.jmh.annotations.*;
import util.Util;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)
public class SortBenchmark {

    @Param({"BUBBLE_SORT"})
    private String algorithmName;
    @Param({"RANDOM"})
    private String inputType;
    @Param({"1000"})
    private int size;
    @Param({"12345"})
    private long seed;
    @Param({"10"})
    private int repeatedCoefficient;
    @Param({"0"})
    private int randomOrigin;
    @Param({"500"})
    private int randomBound;
    // Here to add that information on result
    @Param({"5"})
    private int warmupSize;
    @Param({"30"})
    private int samples;


    private Algorithm algorithm;
    private Integer[] input;
    private Integer[] currentInput;


    @Setup(Level.Trial)
    public void setup() {
        this.algorithm = Algorithm.valueOf(algorithmName);
        Util.ArrayType type = Util.ArrayType.valueOf(inputType);

        this.input = Util.integerArray(type, seed, size, repeatedCoefficient, randomOrigin, randomBound);
    }

    @Setup(Level.Iteration)
    public void prepare() {
        this.currentInput = Arrays.copyOf(input, input.length);
    }

    @TearDown(Level.Iteration)
    public void end() {
        this.currentInput = null;
    }

    @Benchmark
    public Integer[] bench() {
        this.algorithm.getFunction().accept(currentInput);
        return currentInput;
    }

}
