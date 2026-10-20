package algorithms;

import java.util.function.Consumer;

public enum Algorithm {

    BUBBLESORT("BubbleSort", BubbleSort::sort),
    INSERTIONSORT("InsertionSort", InsertionSort::sort),
    QUICKSORT("QuickSort", QuickSort::sort),
    SELECTIONSORT("SelectionSort", SelectionSort::sort),
    SHELLSORT("ShellSort", ShellSort::sort);

    private final String name;
    private final Consumer<Integer[]> function;

    Algorithm(String name, Consumer<Integer[]> function) {
        this.name = name;
        this.function = function;
    }

    public Consumer<Integer[]> getFunction() {
        return function;
    }

    @Override
    public String toString() {
        return name;
    }
}
