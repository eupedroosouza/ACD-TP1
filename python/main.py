#  python.exe .\main.py -d ../results/ -e ../build/libs/ACD-TP1.jar

import subprocess
from argparse import ArgumentParser
from pathlib import Path

input_types = ["RANDOM", "REPEATED", "CRESCENT", "DECRESCENT"]
algorithms = ["RADIXSORT", "COUNTINGSORT", "BUCKETSORT", "MERGESORT", "HEAPSORT", "QUICKSORT", "SHELLSORT",
              "INSERTIONSORT", "SELECTIONSORT", "BUBBLESORT"]
sizes = [100, 1_000, 10_000, 100_000, 1_000_000]
samples = 10  # can be changed to 30


def main():
    parser = ArgumentParser()

    parser.add_argument("--dir", "-d", required=True)
    parser.add_argument("--executable", "--exec", "-e", required=True)

    arguments = parser.parse_args()

    results_dir = Path(arguments.dir)
    if not results_dir.exists():
        results_dir.mkdir()
    elif not results_dir.is_dir():
        raise Exception(f"{results_dir} not is a directory.")

    executable: str = arguments.executable
    if not executable.endswith(".jar"):
        executable += ".jar"

    if not Path(executable).exists():
        raise Exception(f"File {exec} not exists.")

    print("===================================")
    for size in sizes:

        print(f"Size: {size}")
        print("Types:")
        for input_type in input_types:
            print(f" {input_type}")
            print("  Algorithms:")
            for algorithm in algorithms:
                current_result_dir = results_dir.joinpath(f"./{algorithm}/{size}/").absolute()
                current_result_dir.mkdir(parents=True, exist_ok=True)

                result_file = current_result_dir.joinpath(f"{input_type}.json").absolute()
                result_log_file = current_result_dir.joinpath(f"{input_type}_log.txt").absolute()

                command = [
                    "java",
                    "-jar", str(executable),
                    "--algorithm", str(algorithm),
                    "--inputType", str(input_type),
                    "--size", str(size),
                    "--samples", str(samples),
                    "--result", str(result_file)
                ]
                try:
                    result = subprocess.run(
                        command,
                        capture_output=True,
                        text=True,
                        check=True
                    )
                    stdout = result.stdout.strip()
                    with open(result_log_file, "w") as f:
                        f.write(stdout)

                    print(f"   {algorithm} OK!")
                except subprocess.CalledProcessError as e:
                    print(f"   {algorithm} FAILED: {e}")

    print("===================================")


if __name__ == '__main__':
    main()
