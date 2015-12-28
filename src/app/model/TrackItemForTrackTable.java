package app.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by alex on 22.11.2015.
 */
public class TrackItemForTrackTable {
    private final SimpleStringProperty trackName;
    private final SimpleStringProperty artistName;
    private final SimpleStringProperty albumName;
    private final SimpleStringProperty albumYear;
    private final SimpleStringProperty trackTime;
    private String source;

    public TrackItemForTrackTable(String trackName, String artistName, String albumName, String albumYear, String trackTime, String source) {
        this.trackName = new SimpleStringProperty(trackName);
        this.artistName = new SimpleStringProperty(artistName);
        this.albumName = new SimpleStringProperty(albumName);
        this.albumYear = new SimpleStringProperty(albumYear);
        this.trackTime = new SimpleStringProperty(trackTime);
        this.source = source;
    }

    public String getTrackName() {
        return trackName.get();
    }

    public StringProperty trackNameProperty() {
        return trackName;
    }

    public void setTrackName(String tName) {
        trackName.set(tName);
    }


    public String getArtistName() {
        return artistName.get();
    }

    public StringProperty artistNameProperty() {
        return artistName;
    }

    public void setArtistName(String arName) {
        artistName.set(arName);
    }


    public String getAlbumName() {
        return albumName.get();
    }

    public StringProperty albumNameProperty() {
        return albumName;
    }

    public void setAlbumName(String alName) {
        albumName.set(alName);
    }


    public String getAlbumYear() {
        return albumYear.get();
    }

    public StringProperty albumYearProperty() {
        return albumYear;
    }

    public void setAlbumYear(String alYear) {
        albumYear.set(alYear);
    }


    public String getTrackTime() {
        return trackTime.get();
    }

    public StringProperty trackTimeProperty() {
        return trackTime;
    }

    public void setTrackTime(String tTime) {
        trackTime.set(tTime);
    }

    public String getSource() {
        return source;
    }

    public void setЫщ(String source) {
        this.source = source;
    }
}
