package algorithms;

import java.util.function.Consumer;

public enum Algorithm {

    // O(n)
    RADIXSORT("RadixSort", array -> RadixSort.sort(Integer.class, array)),
    COUNTINGSORT("CoutingSort", array -> CountingSort.sort(Integer.class, array)),
    BUCKETSORT("BucketSort", BucketSort::sort),
    // O(n.log(n)) - most cases
    MERGESORT("MergeSort", array -> MergeSort.sort(Integer.class, array)),
    HEAPSORT("HeapSort", HeapSort::sort),
    QUICKSORT("QuickSort", QuickSort::sort),
    SHELLSORT("ShellSort", ShellSort::sort),
    // O(n^2)
    INSERTIONSORT("InsertionSort", InsertionSort::sort),
    SELECTIONSORT("SelectionSort", SelectionSort::sort),
    BUBBLESORT("BubbleSort", BubbleSort::sort);

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
