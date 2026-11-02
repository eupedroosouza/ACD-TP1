import locale
from argparse import ArgumentParser
from pathlib import Path
from typing import Dict, Any

import matplotlib.pyplot as plt
import numpy as np
from matplotlib.ticker import FuncFormatter
from scipy.optimize import curve_fit

from util import load_algorithm_results, input_types_to_label, algorithms_to_label, input_types

locale.setlocale(locale.LC_ALL, "pt_BR.UTF-8")

sizes = [1_000, 10_000, 100_000, 1_000_000]


def func_linear(x, a, b):
    return a * x + b


def func_nlogn(x, a, b):
    # np.maximum prevents log of 0 or negative numbers
    x_safe = np.maximum(x, 1e-10)
    return a * (x_safe * np.log2(x_safe)) + b


def func_quadratic(x, a, b):
    return a * (x ** 2) + b


def discover_best_fit(x: np.ndarray, y: np.ndarray):
    models = [func_linear, func_nlogn, func_quadratic]

    best_error = float('inf')
    best_params = None
    best_func = None

    for func in models:
        try:
            # curve_fit finds the best 'a' and 'b' for the current function
            popt, pcov = curve_fit(func, x, y)

            # Calculate the error (Sum of Squared Residuals)
            y_pred = func(x, *popt)
            error = np.sum((y - y_pred) ** 2)

            if error < best_error:
                best_error = error
                best_params = popt
                best_func = func

        except RuntimeError:
            continue

    return best_func, best_params


def generate_time_graph(results_dir: Path, graphs_dir: Path, algorithm: str):
    data: Dict[str, Dict[int, Any]] = load_algorithm_results(results_dir, algorithm)


    fig, axes = plt.subplots(1, 3, figsize=(15, 5))
    for i, ax in enumerate(axes):
        input_type = input_types[i]

        current_data = data[input_type]
        x = np.array(list(current_data.keys()))
        y = np.array([v["primaryMetric"]["scorePercentiles"]["50.0"] for v in current_data.values()])

        ax.scatter(x, y, edgecolors='black', zorder=5)

        fit_func, params = discover_best_fit(x, y)
        x_smooth = np.linspace(min(x), max(x), 100)
        y_smooth = fit_func(x_smooth, *params)
        ax.plot(x_smooth, y_smooth, linestyle='--', color='red', linewidth=2)

        ax.set_title(f"{input_types_to_label[input_type]}")
        ax.set_xlabel("Tamanho de Entrada (N)")
        ax.set_ylabel("Tempo de Execução (ms)")
        ax.set_xticks(sizes)
        ax.set_xticklabels([locale.format_string("%d", s, grouping=True) for s in sizes])
        ax.tick_params(axis='x', rotation=45)
        ax.yaxis.set_major_formatter(
            FuncFormatter(lambda y_val, pos: locale.format_string("%.5f", y_val, grouping=True)))
        ax.grid(True, linestyle='--', alpha=0.6)

        ax_theo = ax.twinx()
        x_theo = np.linspace(min(x), max(x), 100)
        y_theo_raw = np.array([0 for _ in x_theo])
        ax_theo.plot(x_theo, y_theo_raw, alpha=0)
        ax_theo.set_yticks([])

    fig.suptitle(
        f"Tempo de Execução {algorithms_to_label[algorithm]}",
        fontsize=16, fontweight='bold')
    plt.tight_layout()
    graph_path = graphs_dir.joinpath(f"./{algorithm}.svg").absolute()
    graph_path.parent.mkdir(parents=True, exist_ok=True)
    plt.savefig(graph_path, format="svg")
    plt.close()


def main():
    parser = ArgumentParser()

    parser.add_argument("--resultsDir", required=True)
    parser.add_argument("--graphs", required=True)
    parser.add_argument("--algorithm", required=True)
    arguments = parser.parse_args()

    results_dir = Path(arguments.resultsDir)
    graphs_dir = Path(arguments.graphs)
    algorithm = arguments.algorithm

    generate_time_graph(results_dir, graphs_dir, algorithm)


if __name__ == '__main__':
    main()
