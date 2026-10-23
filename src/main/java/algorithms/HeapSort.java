package algorithms;

public class HeapSort {

public static void heap(int[] array) {
    int n = array.length;

    // Constrói o heap (reorganiza o array)
    for (int i = n / 2 - 1; i >= 0; i--) {
        heapify(array, n, i);
    }

    // Extrai os elementos do heap um por um
    for (int i = n - 1; i > 0; i--) {
        // Move a raiz atual para o final
        int temp = array[0];
        array[0] = array[i];
        array[i] = temp;

        heapify(array, i, 0);
    }
}

public static void heapify(int[] array, int n, int i) {
    int largest = i; // Inicializa o maior como raiz
    int left = 2 * i + 1; // Filho à esquerda
    int right = 2 * i + 2; // Filho à direita

    // Se o filho da esquerda for maior que a raiz
    if (left < n && array[left] > array[largest]) {
        largest = left;
    }

    // Se o filho da direita for maior que o maior até agora
    if (right < n && array[right] > array[largest]) {
        largest = right;
    }

    // Se o maior não for a raiz, troca e continua o heapify
    if (largest != i) {
        int swap = array[i];
        array[i] = array[largest];
        array[largest] = swap;

        heapify(array, n, largest);
    }
    }
}