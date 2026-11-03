from argparse import ArgumentParser
from pathlib import Path

import numpy as np
import pandas as pd

import util
from util import load_algorithm_results


def format_median_quartiles(jmh_result: dict) -> str:
    """Extracts rawData and returns a string in the format: Median [Q1, Q3]"""
    if not jmh_result:
        print(f"Not exists: {jmh_result}")
        return "-"

    try:
        raw_data = jmh_result["primaryMetric"]["rawData"][0]
        q1 = np.percentile(raw_data, 25)
        median = np.percentile(raw_data, 50)
        q3 = np.percentile(raw_data, 75)

        return f"{median:.4f} [{q1:.4f}, {q3:.4f}]"
    except (KeyError, IndexError) as err:
        print(f"{jmh_result}: {err}")
        return "-"


# --- Main Execution Block ---
if __name__ == "__main__":
    parser = ArgumentParser()

    parser.add_argument("--dir", required=True)
    parser.add_argument("--file", required=True)

    arguments = parser.parse_args()

    results_dir = Path(arguments.dir)
    if not results_dir.exists():
        raise Exception(f"Results dir '{dir}' not exists")

    # Load all algorithm results using your existing function
    all_results_data = {}
    for algorithm in util.algorithms:
        all_results_data[algorithm] = load_algorithm_results(results_dir, algorithm)

    # Build the MultiIndex columns to match the CSV structure
    columns_tuples = [("Tamanho da Entrada", "Tipo de Entrada / Algoritmo")]

    for size in util.sizes:
        formatted_size = f"{size:,}".replace(',', '.')
        for input_type in util.input_types:
            columns_tuples.append((formatted_size, util.input_types_to_label[input_type]))

    columns = pd.MultiIndex.from_tuples(columns_tuples)

    # Build the rows data
    table_rows = []
    for algorithm in util.algorithms:
        current_row = [util.algorithms_to_label[algorithm]]

        for size in util.sizes:
            for input_type in util.input_types:
                # Safely get the result from the nested dictionary
                result = all_results_data[algorithm].get(input_type, {}).get(size)
                if not result:
                    formatted_string = "-"
                else:
                    median = result["primaryMetric"]["scorePercentiles"]["50.0"]
                    formatted_string = f"{median:.4f}"
                current_row.append(formatted_string)
        table_rows.append(current_row)

    #  Create the DataFrame and export to CSV
    df = pd.DataFrame(table_rows, columns=columns)

    output_filename = arguments.file
    df.to_csv(output_filename, index=False, encoding="utf-8")

    print(f"Success! The table was exported to '{output_filename}'.")
