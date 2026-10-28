package benchmark;

import algorithms.Algorithm;
import org.openjdk.jmh.annotations.*;
import util.Util;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
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


    private Algorithm algorithm;
    private Integer[] input;

    @Setup(Level.Trial)
    public void setup() {
        this.algorithm = Algorithm.valueOf(algorithmName);
        Util.ArrayType type = Util.ArrayType.valueOf(inputType);

        this.input = Util.integerArray(type, seed, size, repeatedCoefficient, randomOrigin, randomBound);
    }

    @Benchmark
    public Integer[] bench() {
        Integer[] sort = input.clone();
        this.algorithm.getFunction().accept(sort);
        return sort;
    }

}
