package app.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by alex on 22.11.2015.
 */
public class TrackItemForAlbumTable {
    private final SimpleStringProperty ordinalNumberTrack;
    private final SimpleStringProperty trackName;
    private final SimpleStringProperty trackTime;
    private String source;


    public TrackItemForAlbumTable(String ordinalNumberTrack, String trackName, String trackTime, String source) {
        this.ordinalNumberTrack = new SimpleStringProperty(ordinalNumberTrack);
        this.trackName = new SimpleStringProperty(trackName);
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


    public String getOrdinalNumberTrack() {
        return ordinalNumberTrack.get();
    }

    public StringProperty ordinalNumberTrackProperty() {
        return ordinalNumberTrack;
    }

    public void setOrdinalNumberTrack(String OrdinalNumberTrack) {
        this.ordinalNumberTrack.set(OrdinalNumberTrack);
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

    public void setSource(String source) {
        this.source = source;
    }
}
