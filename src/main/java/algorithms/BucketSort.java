package algorithms;

public class BucketSort {


        static public void Bucket(int array[]){

            int length = array.length;

            if (length <= 1) {
                return;
            }

            // encontra valor maximo
            int max = array[0];

            for (int i = 1; i < length; i++) {

                if (array[i] > max) {
                    max = array[i];
                }
            }

            // estrutura de baldes
            @SuppressWarnings("")
            List<Integer>[] buckets = new ArrayList[length];

            for (int i = 0; i < length; i++) {
                buckets[i] = new ArrayList<>();
            }

            // Distribui os elementos nos baldes com base no valor maximo
            for (int i = 0; i < length; i++) {

                int bucketIndex = (int) ((long) array[i] * length / (max + 1));
                buckets[bucketIndex].add(array[i]);

            }

            for (int i = 0; i < length; i++) {
                Collections.sort(buckets[i]);
            }

            // Junta todos os baldes de volta no array
            int index = 0;

            for (int i = 0; i < length; i++) {

                for (int j = 0; j < buckets[i].size(); j++) {

                    array[index] = buckets[i].get(j);
                    index++;

                }
            }

        }

    }

