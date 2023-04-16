package se2203.assignment1;

import javafx.application.Platform;

public class SelectionSort implements  SortingStrategy{
    private int[] list;
    private SortingHubController controller;

    public SelectionSort(int[] list, SortingHubController controller){
        this.list = list;
        this.controller = controller;
    }
    @Override
    public void sort(int[] numbers) {
        // Implementation of the selection sort algorithm
        for (int i = 0; i < numbers.length - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < numbers.length; j++){
                if (numbers[j]<numbers[index]){
                    index = j;//searching for lowest index
                }
            }
            int smaller = numbers[index];
            numbers[index] = numbers[i];
            numbers[i] = smaller;
            try {
                // Makes the thread sleep for 25 ms
                Thread.sleep(25);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // Calls the controller updateGraph() method later to create the animation
            Platform.runLater(()->{
                controller.updateGraph(numbers);

            });
        }

    }

    @Override
    public void run() {
        // Sorts the list and calls the controller sortFinished method when completed
        sort(list);
        controller.sortFinished();
    }
}
