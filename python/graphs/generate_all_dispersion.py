from argparse import ArgumentParser
from pathlib import Path

from graphs.generate_dispersion import generate_dispersion
from util import algorithms, sizes


def main():
    parser = ArgumentParser()

    parser.add_argument("--dir", required=True)
    parser.add_argument("--graphs", required=True)

    arguments = parser.parse_args()


    results_dir = Path(arguments.dir)
    if not results_dir.exists():
        raise Exception(f"Results dir '{dir}' not exists")
    graphs_dir = Path(arguments.graphs)

    for algorithm in algorithms:
        for size in sizes:
            current_result_dir = results_dir.joinpath(f"./{algorithm}/{size}/").absolute()
            if not current_result_dir.exists():
                continue
            generate_dispersion(results_dir, size, algorithm, graphs_dir)


    pass

if __name__ == '__main__':
    main()
