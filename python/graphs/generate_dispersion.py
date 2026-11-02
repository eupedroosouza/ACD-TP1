import locale
from argparse import ArgumentParser
from pathlib import Path
from typing import Dict, Any

import matplotlib.pyplot as plt

from util import load_algorithm_results, input_types, input_types_label, algorithms_to_label

locale.setlocale(locale.LC_ALL, "pt_BR.UTF-8")


def generate_dispersion(results_dir: Path, size: int, algorithm: str, graphs_dir: Path):
    graph_path = graphs_dir.joinpath(f"./{size}/{algorithm}.svg")
    graph_path.parent.mkdir(parents=True, exist_ok=True)

    data: Dict[str, Dict[int, Any]] = load_algorithm_results(results_dir, algorithm)

    fig, axes = plt.subplots(1, 3, figsize=(15, 5))
    for i, ax in enumerate(axes):
        input_type = input_types[i]

        try:
            current_data = data[input_type][size]
        except Exception as err:
            print(f"Error loading data to: {algorithm}, {input_type} and {size}: {err}")
            continue

        samples = []
        for j in range(1, int(current_data["measurementIterations"]) + 1):
            samples.append(j)
        times = current_data["primaryMetric"]["rawData"][0]

        ax.scatter(samples, times, color='blue', edgecolors='black')

        mean = current_data["primaryMetric"]["score"]
        ax.axhline(y=mean, color='red', linestyle='-', linewidth=1, label=f'Média ({mean:.5f} ms)')

        median = current_data["primaryMetric"]["scorePercentiles"]["50.0"]
        ax.axhline(y=median, color='green', linestyle='-', linewidth=1, label=f'Mediana ({median:.5f} ms)')

        ax.set_xlabel("Amostras")
        ax.set_ylabel("Tempo de Execução (ms)")
        ax.set_title(f"{input_types_label[i]}")

        ax.legend()
        ax.grid(True, linestyle='--', alpha=0.6)

        pass

    fig.suptitle(
        f"Dispersão {algorithms_to_label[algorithm]} (tamanho = {locale.format_string("%i", size, grouping=True)})",
        fontsize=16, fontweight='bold')
    plt.tight_layout()
    plt.savefig(graph_path, format="svg")
    plt.close()


def main():
    parser = ArgumentParser()

    parser.add_argument("--dir", required=True)
    parser.add_argument("--algorithm", required=True)
    parser.add_argument("--size", required=True)
    parser.add_argument("--graphs", required=True)

    arguments = parser.parse_args()

    algorithm = arguments.algorithm
    size = int(arguments.size)

    results_dir = Path(arguments.dir)
    if not results_dir.exists():
        raise Exception(f"Results dir '{dir}' not exists")
    graphs_dir = Path(arguments.graphs)

    generate_dispersion(results_dir, size, algorithm, graphs_dir)


if __name__ == '__main__':
    main()
