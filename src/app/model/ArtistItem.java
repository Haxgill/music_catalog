package app.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 * Created by alex on 16.11.2015.
 */
public class ArtistItem {
    private String artistName;
    private ObservableList<AlbumItem> albumList;

    public ArtistItem(String artistName, ObservableList<AlbumItem> albumList) {
        this.artistName = artistName;
        this.albumList = albumList;
    }


    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }


    public ObservableList<AlbumItem> getAlbumList() {
        return albumList;
    }

    public void setAlbumList(ObservableList<AlbumItem> albumList ) {
        this.albumList = albumList;
    }

}
