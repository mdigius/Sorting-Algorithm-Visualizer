package se2203.assignment1;

import javafx.application.Platform;

public class MergeSort implements SortingStrategy{
    private int[] list;
    private SortingHubController controller;
    public MergeSort(int[] list, SortingHubController controller){
        this.list = list;
        this.controller = controller;
    }
    @Override
    public void sort(int[] S) throws InterruptedException {
        mergeSort(S, 0, S.length-1);
    }
    public void mergeSort(int arr[], int l, int r) {
        if (l < r) {
            // Find the middle point
            int m = l + (r - l) / 2;

            // Sort first and second halves
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);

            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }
    public void merge(int arr[], int l, int m, int r) {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        int L[] = new int[n1];
        int R[] = new int[n2];

        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray array
        int k = l;
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
            // Calls the controller updateGraph() method later to create the animation
            Platform.runLater(()->{
                controller.updateGraph(arr);
            });
            try {
                // Makes thread sleep for 10ms
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        /* Copy remaining elements of L[] if any */
        while(i < n1){
            arr[k] = L[i];
            i++;
            k++;
            Platform.runLater(()->{
                controller.updateGraph(arr);
            });
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        /* Copy remaining elements of R[] if any */
        while(j < n2){
            arr[k] = R[j];
            j++;
            k++;
            Platform.runLater(()->{
                controller.updateGraph(arr);
            });
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void run() {
        try {
            sort(list);
            controller.sortFinished();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
