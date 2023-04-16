package se2203.assignment1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class SortingHubController implements Initializable {
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private AnchorPane rectPane;
    @FXML
    private Button resetButton;
    @FXML
    private Label sizeLabel;
    @FXML
    private Slider slider;
    @FXML
    private Button sortButton;
    private int[] intArray;
    private SortingStrategy sortingMethod;
    private boolean sorting;
    private boolean sorted;
    @FXML
    void comboSelected(ActionEvent event) {
        // When the comboBox event is raised, check to see if selected is Merge Sort
        if(comboBox.getSelectionModel().getSelectedItem()=="Merge Sort"){
            // If so call setSortStrategy with a new MergeSort object
            setSortStrategy(0);
        } else{
            // If not then means selectionSort must be selected
            setSortStrategy(1);
        }
    }
    @FXML
    void onResetClicked(ActionEvent event) {
        // When reset button is clicked sets sorted boolean to false
        sorted = false;
        // Ensures that merge sort is selected
        comboBox.getSelectionModel().selectFirst();
        // Sets slider to 64 and label as well
        slider.setValue(64);
        sizeLabel.setText("64");
        // Reshuffles the array
        intArray = new int[64];
        setSortStrategy(0);
        generateData(intArray);
        updateGraph(intArray);
    }

    @FXML
    void onSortClicked(ActionEvent event) throws InterruptedException {
        // When sort button is clicked, it will create a new thread of the sortingMethod object
        // and start that thread calling its run() method
        if(sorted == false && sorting == false){
            Thread thread2 = new Thread(sortingMethod);
            // Sets the boolean sorting to true to avoid clicking sort multiple times
            sorting = true;
            // Temporarily disables the slider so that it cant be messed with during sort
            slider.setDisable(true);
            // Starts the thread
            thread2.start();
        }
    }
    public void sortFinished(){
        // When sorting is finish it will re-enable the slider
        // and set the boolean values correctly to avoid glitches when trying
        // to sort an already sorted array
        slider.setDisable(false);
        sorting = false;
        sorted = true;
    }

    @FXML
    void sliderMoved(MouseEvent event) {
        // Changes sorted to false once the slider is moved
        sorted = false;
        // Rounds the double value to an integer
        int size = (int) Math.round(slider.getValue());
        // Sets the label to display the size
        sizeLabel.setText("" + size);
        // Creates a new array with the correct size and generates new data to shuffle
        intArray = new int[size];
        generateData(intArray);
        // Sets the sorting strategy based on the combobox selected item
        if(comboBox.getSelectionModel().getSelectedItem()=="Merge Sort"){
            setSortStrategy(0);
        }else{
            setSortStrategy(1);
        }
        updateGraph(intArray);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Some initial values that the fields will hold when the program
        // is initialized as described in the assignment instructions
        slider.setValue(64);
        comboBox.getItems().setAll("Merge Sort", "Selection Sort");
        comboBox.getSelectionModel().selectFirst();
        intArray = new int[64];
        generateData(intArray);
        updateGraph(intArray);
        setSortStrategy(0);
    }
    public void setSortStrategy(int i){
        // sets the sorting strategy to a new merge or selection sort object
        // depending on what is chosen in the combo box.
        // Passes the array and this controller object into the constructor
        if(i==0){
            sortingMethod = new MergeSort(intArray, this);
        } else{
            sortingMethod = new SelectionSort(intArray, this);
        }
    }
    public void updateGraph(int[] data){
        // Clears all children in the rectangle pane
        rectPane.getChildren().clear();
        double placer = 0;
        double heightMultiplier = (double) 310 / data.length;
        double width = (double)  (750 - data.length) / data.length;
        //System.out.println(width);
        for(int i = 0; i<data.length; i++){
            // Adjusts the height to fit in the pane
            double height = data[i]*heightMultiplier;
            // New rectangle object created
            Rectangle rect = new Rectangle();
            // Set its height and width, and colour to red
            rect.setHeight(height);
            rect.setWidth(width);
            rect.setFill(Color.RED);
            // Sets the X layout of the bar to the placer variable
            rect.setLayoutX(placer);
            // Positions the rectangle Y layout, so it is at the bottom of the pane
            rect.setLayoutY(310 - height);
            // Increases the value of the placer variable
            placer = (placer + width + 1);
            // Adds the current rectangle
            rectPane.getChildren().add(rect);
        }
    }
    public void generateData(int[] intArray){
        // Makes an arraylist object to hold the integers to allow for easy shuffling
        ArrayList<Integer> integerArrayList = new ArrayList<>(intArray.length);
        // Adds all integers in range 1 to arraysize selected by slider into the arraylist
        for(int i = 0; i< intArray.length; i++){
            integerArrayList.add(i+1);
        }
        // Shuffles list
        Collections.shuffle(integerArrayList);
        // Puts shuffled list into the array
        for(int i = 0; i< intArray.length; i++){
            intArray[i] = integerArrayList.get(i);
        }
    }
}
