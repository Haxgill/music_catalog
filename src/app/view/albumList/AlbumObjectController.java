package app.view.albumList;

import app.model.AlbumItem;
import app.model.TrackItemForAlbumTable;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.*;
import javafx.scene.image.Image;

import java.awt.*;

/**
 * Created by alex on 14.11.2015.
 */
public class AlbumObjectController {

    @FXML
    private TableView AlbumListTable;
    @FXML
    private TableColumn<TrackItemForAlbumTable, String> ordinalNumberTrackColumn;
    @FXML
    private TableColumn<TrackItemForAlbumTable, String> trackNameColumn;
    @FXML
    private TableColumn<TrackItemForAlbumTable, String> trackTimeColumn;
    @FXML
    private Label albumNameLabel;
    @FXML
    private Label albumYearLabel;
    @FXML
    private ImageView coverImageView;
    @FXML
    private Label groupNameLabel;

    public AlbumObjectController() {
    }

    @FXML
    private void initialize() {
        ordinalNumberTrackColumn.setCellValueFactory(cellData -> cellData.getValue().ordinalNumberTrackProperty());
        trackNameColumn.setCellValueFactory(cellData -> cellData.getValue().trackNameProperty());
        trackTimeColumn.setCellValueFactory(cellData -> cellData.getValue().trackTimeProperty());
        ordinalNumberTrackColumn.getStyleClass().add("centered-col");
        trackTimeColumn.getStyleClass().add("centered-col");
        trackTimeColumn.getStyleClass().add("remove-column-border");
    }

    public void initializeData(AlbumItem album) {
        albumNameLabel.setText(album.getAlbumName());
        groupNameLabel.setText(album.getArtistName());
        albumYearLabel.setText(Integer.toString(album.getAlbumYear()));
        coverImageView.setImage(new Image("file:"+album.getCover()));
        AlbumListTable.setItems(album.getTrackList());
        int albumListTableSize = album.getTrackList().size()*24+2;
        AlbumListTable.setPrefHeight(albumListTableSize);
        AlbumListTable.setMinHeight(albumListTableSize);
        AlbumListTable.setMaxHeight(albumListTableSize);

    }

    public ImageView getCoverImageView() { return coverImageView;}

    public void setCoverImageView(ImageView coverImageView) { this.coverImageView = coverImageView;}

}

