package algorithms;

public class MergeSort {

public static void Merge(int array[]){

    int length = array.length;
    if (length <= 1){ // se o array for <= 1 não tem pq seguir dividindo ele
        return;
    }

    int middle = length/2;
    int[] leftArray = new int[middle];
    int[] rightArray = new int[length - middle];

    int i = 0; // left array
    int j = 0; // right array

    for (; i < length; i ++){ // percorre todo o array
        if (i < middle){
            leftArray[i] = array[i];
        } else {
            rightArray[j] = array[i];
            j++;
        }
    }
    MergeSort(leftArray);
    MergeSort(rightArray);
    merge(leftArray, rightArray, array);

}

private static void merge(int[] leftArray, int[] rightArray, int[] array){

    int leftSize = array.length / 2;
    int rightSize = array.length - leftSize;
    int i = 0, l = 0, r = 0;
    // i = array original
    // l = array esquerda
    // r = array direita

    // checando condições pra merging
    while (l < leftSize && r < rightSize){
        if (leftArray[l] < rightArray[r]){
            array[i] = leftArray[l];
            i ++;
            l ++;
        } else {
            array[i] = rightArray[r];
            i ++;
            r ++;
        }
    }
    while (l < leftSize){
        array[i] = leftArray[l];
        i ++;
        l ++;
    }
    while (r < rightSize){
        array[i] = rightArray[r];
        i ++;
        r ++;
    }

}

}
