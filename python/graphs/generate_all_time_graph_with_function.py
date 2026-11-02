from argparse import ArgumentParser
from pathlib import Path

from graphs.generate_time_graph_with_function import generate_time_graph
from util import algorithms


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
        try:
            generate_time_graph(results_dir, graphs_dir, algorithm)
        except Exception as err:
            print(f"Error while generating to algorithm {algorithm}: {err}")

    pass


if __name__ == '__main__':
    main()
