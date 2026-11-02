import json
from collections import defaultdict
from pathlib import Path
from typing import Any, Dict

sizes = [1_000, 10_000, 100_000, 1_000_000]
input_types = ["RANDOM", "CRESCENT", "DECRESCENT"]
input_types_label = ["Aleatório", "Crescente", "Decrescente"]
input_types_to_label =  dict(zip(input_types, input_types_label))

algorithms = ["RADIXSORT", "COUNTINGSORT", "BUCKETSORT", "MERGESORT", "HEAPSORT", "QUICKSORT", "SHELLSORT",
              "INSERTIONSORT", "SELECTIONSORT", "BUBBLESORT"]
algorithms_label = ["RadixSort", "CountingSort", "BucketSort", "MergeSort", "HeapSort", "QuickSort", "ShellSort",
                    "InsertionSort", "SelectionSort", "BubbleSort"]

algorithms_to_label = dict(zip(algorithms, algorithms_label))


# input_type -> size -> result json
def load_algorithm_results(results_dir: Path, algorithm: str) -> Dict[str, Dict[int, Any]]:
    data: Dict[str, Dict[int, float]] = defaultdict(dict)
    for size in sizes:
        for input_type in input_types:

            result_path = results_dir.joinpath(f"./{algorithm}/{size}/{input_type}.json").absolute()

            if result_path.exists():
                try:

                    with open(result_path) as f:
                        loaded = json.load(f)
                    data[input_type][size] = loaded[0]
                except Exception as err:
                    print(f"Error loading algorithm: {algorithm}, size: {size} and input type: {input_type}: {err}")
    return data
