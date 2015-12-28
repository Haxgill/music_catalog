package app.view.albumList;

import app.Main;
import app.model.AlbumItem;
import app.model.TrackItemForAlbumTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by alex on 10.11.2015.
 */
public class AlbumListController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox vBox;

    private Main main;

    public AlbumListController() {
    }

    @FXML
    private void initialize() {
    }

    public void setMainApp(Main main) {
        this.main = main;
    }

    public void initializeAlbumObjects(ObservableList<AlbumItem> albums, Double coverImageViewSize) {
        int i = 0;
        while (i < albums.size()) {
           try {
               FXMLLoader loaderAlbumObject = new FXMLLoader();
               loaderAlbumObject.setLocation(Main.class.getResource("/app/view/albumList/AlbumObject.fxml"));
               Parent albumObjectLayout = (Parent) loaderAlbumObject.load();
               AlbumObjectController albumObjectController = loaderAlbumObject.getController();
               albumObjectController.initializeData(albums.get(i));
               albumObjectController.getCoverImageView().setFitHeight(coverImageViewSize);
               albumObjectController.getCoverImageView().setFitWidth(coverImageViewSize);
               vBox.getChildren().add(albumObjectLayout);

               i++;
           } catch (IOException e) {
           }
        }

    }


}


