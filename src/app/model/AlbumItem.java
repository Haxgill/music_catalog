package app.model;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 * Created by alex on 16.11.2015.
 */
public class AlbumItem {
    private String albumName;
    private Integer albumYear;
    private String artistName;
    private String cover;
    private ObservableList<TrackItemForAlbumTable> trackList;

    public AlbumItem(String albumName, Integer albumYear, String artistName, String cover, ObservableList<TrackItemForAlbumTable> trackList) {
        this.albumName = albumName;
        this.albumYear = albumYear;
        this.artistName = artistName;
        this.cover = cover;
        this.trackList = trackList;
    }


    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }


    public Integer getAlbumYear() {
        return albumYear;
    }

    public void setAlbumYear(Integer albumYear) {
        this.albumYear = albumYear;
    }


    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }


    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }


    public ObservableList<TrackItemForAlbumTable> getTrackList() {
        return trackList;
    }

    public void setTrackList(ObservableList<TrackItemForAlbumTable> trackList ) {
        this.trackList = trackList;
    }

}
