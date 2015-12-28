package app.view.playerMenu;

import app.Main;
import app.controller.JDBC;
import app.view.albumList.AlbumListController;
import app.view.artistList.ArtistListController;
import app.view.authorizationLayout.AuthorizationLayoutController;
import app.view.trackList.TrackListController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.beans.EventHandler;
import java.io.IOException;

/**
 * Created by alex on 13.11.2015.
 */
public class PlayerMenuController {


    @FXML
    private Button playButton;
    @FXML
    private ImageView playButtonImageView;
    @FXML
    private Slider volumeSlider;
    @FXML
    private MenuButton menuButton;
    @FXML
    private ImageView menuButtonImageView;
    @FXML
    private MenuItem editCatalogMenuItem;
    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private MenuButton catalogMenuButton;
    @FXML
    private MenuItem TrackMenuItem;
    @FXML
    private MenuItem ArtistMenuItem;
    @FXML
    private MenuItem AlbumMenuItem;


    //Image sample for play/stop button
    private Image playButtonImageSampleUnFocused = new Image("/app/resources/images/playUnFocused.png");
    private Image playButtonImageSampleFocused = new Image("/app/resources/images/playFocused.png");
    private Image stopButtonImageSampleUnFocused = new Image("/app/resources/images/stopUnFocused.png");
    private Image stopButtonImageSampleFocused = new Image("/app/resources/images/stopFocused.png");

    //Image sample for menu button
    private Image menuButtonImageSampleUnFocused = new Image("/app/resources/images/menuUnFocused.png");
    private Image menuButtonImageSampleFocused = new Image("/app/resources/images/menuFocused.png");


    private Main main;

    public PlayerMenuController() {
    }

    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        playButtonImageView.setImage(playButtonImageSampleUnFocused);
        menuButtonImageView.setImage(menuButtonImageSampleUnFocused);
    }

    public void setMainApp(Main main) {
        this.main = main;

    }


    @FXML
    private void playButtonClickHandler() {
        if (playButtonImageView.getImage() == playButtonImageSampleFocused) {
            playButtonImageView.setImage(stopButtonImageSampleFocused);
        }
        else {
            playButtonImageView.setImage(playButtonImageSampleFocused);
        }
    }

    @FXML
    private void playButtonMouseEnteredHandler() {
        if (playButtonImageView.getImage() == playButtonImageSampleUnFocused) {
            playButtonImageView.setImage(playButtonImageSampleFocused);
        }
        else if(playButtonImageView.getImage() == stopButtonImageSampleUnFocused){
            playButtonImageView.setImage(stopButtonImageSampleFocused);
        }
    }

    @FXML
    private void playButtonMouseExitHandler() {
        if (playButtonImageView.getImage() == playButtonImageSampleFocused) {
            playButtonImageView.setImage(playButtonImageSampleUnFocused);
        }
        else if(playButtonImageView.getImage() == stopButtonImageSampleFocused){
            playButtonImageView.setImage(stopButtonImageSampleUnFocused);
        }
    }


    @FXML
    private void menuButtonClickHandler() {
        menuButtonImageView.setImage(menuButtonImageSampleFocused);
    }

    @FXML
    private void menuButtonMouseEnteredHandler() {
        menuButtonImageView.setImage(menuButtonImageSampleFocused);
    }

    @FXML
    private void menuButtonMouseExitHandler() {
        menuButtonImageView.setImage(menuButtonImageSampleUnFocused);
    }


    @FXML
    private void TrackMenuItemAction() {
        try {
            FXMLLoader loaderTrackList = new FXMLLoader();
            loaderTrackList.setLocation(Main.class.getResource("/app/view/trackList/TrackList.fxml"));
            Parent TrackList = (Parent) loaderTrackList.load();
            TrackListController TrackListCr = loaderTrackList.getController();
            TrackListCr.initializeData(JDBC.getAllSongs());
            catalogMenuButton.setText(TrackMenuItem.getText());
            ((AnchorPane) main.scene.getRoot()).getChildren().remove(1);
            ((AnchorPane) main.scene.getRoot()).getChildren().add(TrackList);
            ((AnchorPane) main.scene.getRoot()).setTopAnchor(TrackList, 51.0);
            ((AnchorPane) main.scene.getRoot()).setLeftAnchor(TrackList, 0.0);
            ((AnchorPane) main.scene.getRoot()).setRightAnchor(TrackList, 0.0);
            ((AnchorPane) main.scene.getRoot()).setBottomAnchor(TrackList, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void AlbumMenuItemAction() {
        try {
            FXMLLoader loaderAlbumList = new FXMLLoader();
            loaderAlbumList.setLocation(Main.class.getResource("/app/view/albumList/AlbumList.fxml"));
            Parent AlbumList = (Parent) loaderAlbumList.load();
            AlbumListController AlbumListCr = loaderAlbumList.getController();
            AlbumListCr.initializeAlbumObjects(JDBC.getAllAlbums(), 200.0);
            catalogMenuButton.setText(AlbumMenuItem.getText());
            ((AnchorPane) main.scene.getRoot()).getChildren().remove(1);
            ((AnchorPane) main.scene.getRoot()).getChildren().add(AlbumList);
            ((AnchorPane) main.scene.getRoot()).setTopAnchor(AlbumList, 51.0);
            ((AnchorPane) main.scene.getRoot()).setLeftAnchor(AlbumList, 0.0);
            ((AnchorPane) main.scene.getRoot()).setRightAnchor(AlbumList, 0.0);
            ((AnchorPane) main.scene.getRoot()).setBottomAnchor(AlbumList, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void ArtistMenuItemAction() {
        try {
            FXMLLoader loaderArtistList = new FXMLLoader();
            loaderArtistList.setLocation(Main.class.getResource("/app/view/artistList/ArtistList.fxml"));
            Parent ArtistList = (Parent) loaderArtistList.load();
            ArtistListController ArtistListCr = loaderArtistList.getController();
            ArtistListCr.initializeData(JDBC.getAllGroups());
            catalogMenuButton.setText(ArtistMenuItem.getText());
            ((AnchorPane) main.scene.getRoot()).getChildren().remove(1);
            ((AnchorPane) main.scene.getRoot()).getChildren().add(ArtistList);
            ((AnchorPane) main.scene.getRoot()).setTopAnchor(ArtistList, 51.0);
            ((AnchorPane) main.scene.getRoot()).setLeftAnchor(ArtistList, 0.0);
            ((AnchorPane) main.scene.getRoot()).setRightAnchor(ArtistList, 0.0);
            ((AnchorPane) main.scene.getRoot()).setBottomAnchor(ArtistList, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void editCatalogMenuItemAction() {
        Parent root;
        try {
            FXMLLoader loaderAuthLayout = new FXMLLoader();
            loaderAuthLayout.setLocation(Main.class.getResource("/app/view/authorizationLayout/AuthorizationLayout.fxml"));
            root = loaderAuthLayout.load();
            AuthorizationLayoutController authLayoutController = loaderAuthLayout.getController();
            authLayoutController.setMainApp(main);
            Stage stage = new Stage();
            stage.setTitle("Authentication");
            stage.setScene(new Scene(root));
            stage.show();
            authLayoutController.setAuthLayoutStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void exitMenuItemAction() {
        System.exit(0);
    }

}
