package app.view.artistList;

import app.Main;
import app.controller.JDBC;
import app.model.AlbumItem;
import app.model.ArtistItem;
import app.view.albumList.AlbumListController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by alex on 10.11.2015.
 */
public class ArtistListController {

    @FXML
    private AnchorPane albumsAnchorPane;

    @FXML
     private ListView<String> artistListView;


    private Main main;

    public ArtistListController() {
    }

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.;
    }

    public void setMainApp(Main main) {
        this.main = main;
    }

    public void initializeData(ObservableList<String> groups) {
        artistListView.setItems(groups);
        initializeAlbumsData(groups.get(0));
        artistListView.getSelectionModel().selectFirst();
    }

    private void initializeAlbumsData(String groupName) {
        try {
            FXMLLoader loaderAlbumList = new FXMLLoader();
            loaderAlbumList.setLocation(Main.class.getResource("/app/view/albumList/AlbumList.fxml"));
            Parent AlbumList = (Parent) loaderAlbumList.load();
            AlbumListController AlbumListCr = loaderAlbumList.getController();
            AlbumListCr.initializeAlbumObjects(JDBC.getAlbumsOfGroup(groupName), 150.0);
            albumsAnchorPane.getChildren().add(AlbumList);
            albumsAnchorPane.setTopAnchor(AlbumList, 0.0);
            albumsAnchorPane.setLeftAnchor(AlbumList, 0.0);
            albumsAnchorPane.setRightAnchor(AlbumList, 0.0);
            albumsAnchorPane.setBottomAnchor(AlbumList, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void changeGroupHandler() {
        initializeAlbumsData(artistListView.getSelectionModel().getSelectedItem());
    }

}

