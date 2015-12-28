package app.view.trackList;

import app.Main;
import app.model.TrackItemForTrackTable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by alex on 01.11.2015.
 */
public class TrackListController {

    @FXML
    private TableView<TrackItemForTrackTable> trackListTable;
    @FXML
    private TableColumn<TrackItemForTrackTable, String> trackNameColumn;
    @FXML
    private TableColumn<TrackItemForTrackTable, String> artistNameColumn;
    @FXML
    private TableColumn<TrackItemForTrackTable, String> albumNameColumn;
    @FXML
    private TableColumn<TrackItemForTrackTable, String> albumYearColumn;
    @FXML
    private TableColumn<TrackItemForTrackTable, String> trackTimeColumn;

//    public final ObservableList<TrackItemForTrackTable> data =
//            FXCollections.observableArrayList(
//                    new TrackItemForTrackTable("Wormwood", "Michale Graves", "The Arkansas Sessions", "2008", "2:52", ""),
//                    new TrackItemForTrackTable("Silent Partner", "Michale Graves", "The Arkansas Sessions", "2008", "1:57", ""),
//                    new TrackItemForTrackTable("Ophelia", "Michale Graves", "The Arkansas Sessions", "2008", "5:42", ""),
//                    new TrackItemForTrackTable("Take me home", "Sophie Ellis Bextor", "Read My Lips", "2010", "32:11", ""),
//                    new TrackItemForTrackTable("Viretta Park", "Michale Graves", "The Arkansas Sessions", "2008", "2:13", "")
//            );


    private Main main;

    public TrackListController() {
    }

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        trackNameColumn.setCellValueFactory(cellData -> cellData.getValue().trackNameProperty());
        artistNameColumn.setCellValueFactory(cellData -> cellData.getValue().artistNameProperty());
        albumNameColumn.setCellValueFactory(cellData -> cellData.getValue().albumNameProperty());
        albumYearColumn.setCellValueFactory(cellData -> cellData.getValue().albumYearProperty());
        trackTimeColumn.setCellValueFactory(cellData -> cellData.getValue().trackTimeProperty());
        albumYearColumn.getStyleClass().add("centered-col");
        trackTimeColumn.getStyleClass().add("centered-col");
    }

    public void setMainApp(Main main) {
        this.main = main;

    }

    public void initializeData(ObservableList<TrackItemForTrackTable> trackItemForTrackTables) {
        trackListTable.setItems(trackItemForTrackTables);
    }
}

