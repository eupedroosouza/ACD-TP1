package algorithms;

public class CountingSort {

public static void Counting(int array[]){

    int n = array.length;;
    if (n == 0) return;

    int k = array[0];
    for (int i = 1; i < n ; i++){
        if (array[i] > k){
            k = array[i];
        }
    }

    int[] C = new int[k + 1];
    int[] B = new int[n];

    for (int j = 0 ; j < n; j ++){
        C[array[j]]++;
    }

    for (int i = 1; i <= k ; i ++){
        C[i] += C[i - 1];
    }

    for (int j = n - 1; j >= 0; j--){
        B[C[array[j]] - 1] = array[j];
        C[array[j]] --;
    }

    for (int i = 0 ; i < n ; i ++){
        array[i] = B[i];
    }

}


}
