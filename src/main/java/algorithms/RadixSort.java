package algorithms;

public class RadixSort {

public static void radix(int[] A) {
    int n = A.length;
    if (n == 0) return;

    // Encontra o valor máximo para saber o número de dígitos
    int max = A[0];
    for (int i = 1; i < n; i++) {
        if (A[i] > max) {
            max = A[i];
        }
    }

    // Executa o Counting Sort para cada dígito
    for (int exp = 1; max / exp > 0; exp *= 10) {
        countingSortPorDigito(A, n, exp);
    }
}

private static void countingSortPorDigito(int[] A, int n, int exp) {
    int[] B = new int[n];
    int[] C = new int[10];

    for (int i = 0; i < n; i++) {
        int digito = (A[i] / exp) % 10;
        C[digito]++;
    }

    for (int i = 1; i < 10; i++) {
        C[i] += C[i - 1];
    }

    for (int i = n - 1; i >= 0; i--) {
        int digito = (A[i] / exp) % 10;
        B[C[digito] - 1] = A[i];
        C[digito]--;
    }

    // Copia os elementos de volta para o vetor original A
    for (int i = 0; i < n; i++) {
        A[i] = B[i];
    }
}
}