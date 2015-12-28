package app.view.editLayout;

import app.Main;
import app.controller.JDBC;
import app.model.AlbumItem;
import app.model.ArtistItem;
import app.model.TrackItemForAlbumTable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.postgresql.util.PGTime;
import javafx.stage.FileChooser;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.sound.sampled.*;
import javax.sound.sampled.spi.AudioFileReader;
import javax.sound.sampled.spi.AudioFileWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Time;
import java.util.Optional;

/**
 * Created by alex on 23.11.2015.
 */
public class EditLayoutController {
    @FXML
    private TableView<ArtistsTableItem> artistsTable;

    @FXML
    private TableColumn<ArtistsTableItem, String> artistNameColumn;

    @FXML
    private TextField artistNameTextField;

    @FXML
    private Button artistButton;

    @FXML
    private Button artistButtonSave;

    @FXML
    private Button artistButtonCancel;

    @FXML
    private Pane artistPane;

    @FXML
    private TableView<AlbumsTableItem> albumsTable;

    @FXML
    private TableColumn<AlbumsTableItem, String> albumNameColumn;

    @FXML
    private TableColumn<AlbumsTableItem, String> albumCoverPathColumn;

    @FXML
    private TableColumn<AlbumsTableItem, String> albumYearColumn;

    @FXML
    private TextField albumNameTextField;

    @FXML
    private TextField albumYearTextField;

    @FXML
    private TextField albumCoverPathTextField;

    @FXML
    private Button albumCoverPathButton;

    @FXML
    private Button albumButton;

    @FXML
    private Button albumButtonSave;

    @FXML
    private Button albumButtonCancel;

    @FXML
    private Pane albumPane;

    @FXML
    private TableView<SongsTableItem> songsTable;

    @FXML
    private TableColumn<SongsTableItem, String> songNameColumn;

    @FXML
    private TableColumn<SongsTableItem, String> songSourcePathColumn;

    @FXML
    private TextField songNameTextField;

    @FXML
    private TextField songSourcePathTextField;

    @FXML
    private Button songSourcePathButton;

    @FXML
    private Button songButton;

    @FXML
    private Button songButtonSave;

    @FXML
    private Button songButtonCancel;


    @FXML
    private Pane songPane;

    @FXML
    private Label infoLabel;

    @FXML Button logOutButton;


    private Main main;

    private String oldArtistNameEditable;
    private String oldAlbumNameEditable;
    private String oldSongNameEditable;

    public EditLayoutController() {
    }

    @FXML
    private void initialize() {
        artistNameColumn.setCellValueFactory(cellData -> cellData.getValue().artistNameProperty());
        albumNameColumn.setCellValueFactory(cellData -> cellData.getValue().albumNameProperty());
        albumYearColumn.setCellValueFactory(cellData -> cellData.getValue().albumYearProperty());
        albumCoverPathColumn.setCellValueFactory(cellData -> cellData.getValue().albumCoverPathProperty());
        songNameColumn.setCellValueFactory(cellData -> cellData.getValue().songNameProperty());
        songSourcePathColumn.setCellValueFactory(cellData -> cellData.getValue().songSourcePathProperty());
        initializeArtistsList();
        initializeArtistsTableContextMenu();
        initializeAlbumsTableContextMenu();
        initializeSongsTableContextMenu();
    }

    public void setMainApp(Main main) {
        this.main = main;
    }


    private void initializeArtistsTableContextMenu() {

        final ContextMenu menu = new ContextMenu();
        final MenuItem editSelectedItem = new MenuItem("Редактировать группу");
        editSelectedItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                artistNameTextField.setText(artistsTable.getSelectionModel().getSelectedItem().getArtistName());
                oldArtistNameEditable = artistsTable.getSelectionModel().getSelectedItem().getArtistName();
                artistButtonSave.setVisible(true);
                artistButtonCancel.setVisible(true);
                artistButton.setVisible(false);
            }
        });
        final MenuItem deleteSelectedItem = new MenuItem("Удалить группу");
        deleteSelectedItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showDeleteDialog(artistsTable.getSelectionModel().getSelectedItem());
            }
        });
        deleteSelectedItem.disableProperty().bind(
                Bindings.isEmpty(artistsTable.getSelectionModel().getSelectedItems()));
        menu.getItems().addAll(editSelectedItem, deleteSelectedItem);
        editSelectedItem.disableProperty().bind(
                Bindings.isEmpty(artistsTable.getSelectionModel().getSelectedItems()));
        artistsTable.setContextMenu(menu);
    }

    private void initializeAlbumsTableContextMenu() {

        final ContextMenu menu = new ContextMenu();
        final MenuItem editSelectedItem = new MenuItem("Редактировать альбом");
        editSelectedItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                albumNameTextField.setText(albumsTable.getSelectionModel().getSelectedItem().getAlbumName());
                oldAlbumNameEditable = albumsTable.getSelectionModel().getSelectedItem().getAlbumName();
                albumYearTextField.setText(albumsTable.getSelectionModel().getSelectedItem().getAlbumYear());
                albumCoverPathTextField.setText(albumsTable.getSelectionModel().getSelectedItem().getAlbumCoverPath());
                albumButtonSave.setVisible(true);
                albumButtonCancel.setVisible(true);
                albumButton.setVisible(false);
            }
        });
        final MenuItem deleteSelectedItem = new MenuItem("Удалить албом");
        deleteSelectedItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showDeleteDialog(albumsTable.getSelectionModel().getSelectedItem());
            }
        });
        deleteSelectedItem.disableProperty().bind(
                Bindings.isEmpty(albumsTable.getSelectionModel().getSelectedItems()));
        menu.getItems().addAll(editSelectedItem, deleteSelectedItem);
        editSelectedItem.disableProperty().bind(
                Bindings.isEmpty(albumsTable.getSelectionModel().getSelectedItems()));
        albumsTable.setContextMenu(menu);
    }

    private void initializeSongsTableContextMenu() {

        final ContextMenu menu = new ContextMenu();
        final MenuItem editSelectedItem = new MenuItem("Редактировать песню");
        editSelectedItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                songNameTextField.setText(songsTable.getSelectionModel().getSelectedItem().getSongName());
                oldSongNameEditable = songsTable.getSelectionModel().getSelectedItem().getSongName();
                songSourcePathTextField.setText(songsTable.getSelectionModel().getSelectedItem().getSongSourcePath());
                songButtonSave.setVisible(true);
                songButtonCancel.setVisible(true);
                songButton.setVisible(false);
            }
        });
        final MenuItem deleteSelectedItem = new MenuItem("Удалить песню");
        deleteSelectedItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showDeleteDialog(songsTable.getSelectionModel().getSelectedItem());
            }
        });
        deleteSelectedItem.disableProperty().bind(
                Bindings.isEmpty(songsTable.getSelectionModel().getSelectedItems()));
        menu.getItems().addAll(editSelectedItem, deleteSelectedItem);
        editSelectedItem.disableProperty().bind(
                Bindings.isEmpty(songsTable.getSelectionModel().getSelectedItems()));
        songsTable.setContextMenu(menu);
    }

    private void showDeleteDialog( Object o) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Предупреждение об удалении");
        alert.setHeaderText(null);
        ButtonType buttonTypeDelete = new ButtonType("Да", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeDelete, buttonTypeCancel);
        String artistName = null;
        String albumName = null;
        String songName = null;
        if (o.getClass() == ArtistsTableItem.class) {
            ArtistsTableItem item = (ArtistsTableItem) o;
            artistName = item.getArtistName();
            alert.setContentText("Вместе с группой будут удалены все включающие в неё альбомы и песни. Вы точно хотите удалить группу "+artistName+"?");
        }
        else if(o.getClass() == AlbumsTableItem.class) {
            AlbumsTableItem item = (AlbumsTableItem) o;
            albumName = item.getAlbumName();
            alert.setContentText("Вместе с альбомом будут удалены все включающие в него песни. Вы точно хотите удалить альбом "+albumName+"?");
        }
        else if (o.getClass() == SongsTableItem.class) {
            SongsTableItem item = (SongsTableItem) o;
            songName = item.getSongName();
            alert.setContentText("Вы точно хотите удалить песню "+songName+"?");
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeDelete) {
            if (artistName != null) {
                JDBC.deleteGroup(artistName);
                artistsTable.getItems().remove(
                        artistsTable.getSelectionModel().getSelectedIndex());
            } else if (albumName != null) {
                System.out.print(albumName + " deleted");
                JDBC.deleteAlbum(albumName);
                albumsTable.getItems().remove(
                        albumsTable.getSelectionModel().getSelectedIndex());
            } else if (songName != null) {
                JDBC.deleteSong(songName);
                System.out.print(songName + " deleted");
                songsTable.getItems().remove(
                        songsTable.getSelectionModel().getSelectedIndex());
            }
        }
    }

    @FXML
    private void initializeArtistsList() {
        ObservableList<ArtistsTableItem> artists = FXCollections.observableArrayList();
        ObservableList<String> artistsList = JDBC.getAllGroups();
        int i = 0;
        while (i < artistsList.size()) {
            artists.add(new ArtistsTableItem(artistsList.get(i)));
            i++;
        }
        artistsTable.setItems(artists);

    }

    private void initializeAlbumsList(String groupName) {
        ObservableList<AlbumsTableItem> albumsData = FXCollections.observableArrayList();
        ObservableList<AlbumItem> albums = JDBC.getAlbumsOfGroup(groupName);
        int i = 0;
        while (i < albums.size()) {
            albumsData.add(
                    new AlbumsTableItem(
                            albums.get(i).getAlbumName(),
                            albums.get(i).getAlbumYear().toString(),
                            albums.get(i).getCover())
            );
            i++;
        }
        albumsTable.setItems(albumsData);

    }

    private void initializeSongsList(String albumName) {
        ObservableList<SongsTableItem> songsData = FXCollections.observableArrayList();
        ObservableList<TrackItemForAlbumTable> songs = JDBC.getSongsOfAlbum(albumName);
        int i = 0;
        while (i < songs.size()) {
            songsData.add(
                    new SongsTableItem(
                            songs.get(i).getTrackName(),
                            songs.get(i).getSource())
            );
            i++;
        }
        songsTable.setItems(songsData);

    }

    @FXML
    private void logOutButtonAction() {
        main.generateScene();
        main.stage.setTitle("Music catalog");
        ((AnchorPane) main.scene.getRoot()).getChildren().remove(0);
        main.stage.setResizable(true);
    }

    @FXML
    private void changeArtistHandler() {
        initializeAlbumsList(artistsTable.getSelectionModel().getSelectedItem().getArtistName());
        songsTable.getItems().clear();
        songPane.setDisable(true);
        albumPane.setDisable(false);
    }


    @FXML
    private void changeAlbumHandler() {
        if(!albumsTable.getSelectionModel().isEmpty()) {
            initializeSongsList(albumsTable.getSelectionModel().getSelectedItem().getAlbumName());
            songPane.setDisable(false);
        }

    }

    @FXML
    private void artistButtonAction() {
        infoLabel.setText("");
        String groupName = artistNameTextField.getText();
        if (groupName.length() < 2  || groupName.length() > 20) {
            infoLabel.setText("Название группы должно содержать  1..20 символов!");
        }
        else {
            if (JDBC.addGroup(groupName)) {
                artistsTable.getItems().add(new ArtistsTableItem(groupName));
                artistNameTextField.clear();
            }
            else {
                infoLabel.setText("Группа с таким названием уже есть в каталоге!");
            }
        }


    }

    @FXML
    private void albumButtonAction() {
        infoLabel.setText("");
        String groupName = artistsTable.getSelectionModel().getSelectedItem().getArtistName();
        String albumName = albumNameTextField.getText();
        String albumYear = albumYearTextField.getText();
        String albumCoverPath = albumCoverPathTextField.getText();
        if (albumName.length() < 1  || albumName.length() > 20) {
            infoLabel.setText("Название группы должно содержать  1..20 символов!");
        }
        else if (!albumYear.matches("[-+]?\\d+") || albumYear.length() != 4) {
            infoLabel.setText("Год альбома должен содержать 4 цифры!");
        }
        else if(!new File(albumCoverPath).exists()) {
            infoLabel.setText("Указанный файл для обложки альбома не существует!");
        }
        else {
            if (JDBC.addAlbum(albumName, Integer.parseInt(albumYear), albumCoverPath, groupName)) {
                albumsTable.getItems().add(new AlbumsTableItem(albumName, albumYear, albumCoverPath));
                albumNameTextField.clear();
                albumYearTextField.clear();
                albumCoverPathTextField.clear();
            } else {
                infoLabel.setText("У группы " + groupName + " уже есть альбом с таким названием!");
            }
        }
    }



    @FXML
    private void songButtonAction() {
        infoLabel.setText("");
        String albumName = albumsTable.getSelectionModel().getSelectedItem().getAlbumName();
        String songName = songNameTextField.getText();
        String songSourcePath = songSourcePathTextField.getText();
        Time songLenght = new Time(12345670);
        if (songName.length() < 1  || songName.length() > 20) {
            infoLabel.setText("Название песни должно содержать  1..20 символов!");
        }
        else if(!new File(songSourcePath).exists()) {
            infoLabel.setText("Указанный исходный файл песни не существует!");
        }
        else {
            if (JDBC.addSong(songName, songSourcePath, songLenght, albumName)) {
                songsTable.getItems().add(new SongsTableItem(songName, songSourcePath));
                songNameTextField.clear();
                songSourcePathTextField.clear();
            } else {
                infoLabel.setText("В альбоме " + albumName + " уже есть песня с таким названием!");
            }
        }
    }


    @FXML
    private void artistButtonSaveAction() {
        infoLabel.setText("");
        String groupName = artistNameTextField.getText();
        if (groupName.length() < 2  || groupName.length() > 20) {
            infoLabel.setText("Название группы должно содержать  1..20 символов!");
        }
        else {
            if (JDBC.editGroup(oldArtistNameEditable, groupName)) {
                initializeArtistsList();
                initializeAlbumsList(groupName);
                songsTable.getItems().clear();
                songPane.setDisable(true);
                albumPane.setDisable(false);
                artistButtonCancelAction();
            }
            else {
                infoLabel.setText("Группа с таким названием уже есть в каталоге!");
            }
        }

    }

    @FXML
    private void artistButtonCancelAction() {
        oldArtistNameEditable = "";
        artistNameTextField.clear();
        artistButtonCancel.setVisible(false);
        artistButtonSave.setVisible(false);
        artistButton.setVisible(true);
    }

    @FXML
    private void albumButtonSaveAction() {
        infoLabel.setText("");
        String groupName = artistsTable.getSelectionModel().getSelectedItem().getArtistName();
        String albumName = albumNameTextField.getText();
        String albumYear = albumYearTextField.getText();
        String albumCoverPath = albumCoverPathTextField.getText();
        if (albumName.length() < 1  || albumName.length() > 20) {
            infoLabel.setText("Название группы должно содержать  1..20 символов!");
        }
        else if (!albumYear.matches("[-+]?\\d+") || albumYear.length() != 4) {
            infoLabel.setText("Год альбома должен содержать 4 цифры!");
        }
        else if(!new File(albumCoverPath).exists()) {
            infoLabel.setText("Указанный файл для обложки альбома не существует!");
        }
        else {
            if (JDBC.editAlbum(oldAlbumNameEditable,albumName, Integer.parseInt(albumYear), albumCoverPath)) {
                initializeAlbumsList(groupName);
                initializeSongsList(albumName);
                songPane.setDisable(false);
                albumButtonCancelAction();
            } else {
                infoLabel.setText("У группы " + groupName + " уже есть альбом с таким названием!");
            }
        }
    }

    @FXML
    private void albumButtonCancelAction() {
        oldAlbumNameEditable = "";
        albumNameTextField.clear();
        albumYearTextField.clear();
        albumCoverPathTextField.clear();
        albumButtonCancel.setVisible(false);
        albumButtonSave.setVisible(false);
        albumButton.setVisible(true);
    }


    @FXML
    private void songButtonSaveAction() {
        infoLabel.setText("");
        String albumName = albumsTable.getSelectionModel().getSelectedItem().getAlbumName();
        String songName = songNameTextField.getText();
        String songSourcePath = songSourcePathTextField.getText();
        Path path = new File(songSourcePath).toPath();
        Time songLenght = new Time(12345670);
        try {
            Time time = (Time) Files.getAttribute(path, "duration");
        }
        catch (Exception ex) {}
        if (songName.length() < 1  || songName.length() > 20) {
            infoLabel.setText("Название песни должно содержать  1..20 символов!");
        }
        else if(!new File(songSourcePath).exists()) {
            infoLabel.setText("Указанный исходный файл песни не существует!");
        }
        else {
            if (JDBC.editSong(oldSongNameEditable, songName, songSourcePath, songLenght)) {
                initializeSongsList(albumName);
                songButtonCancelAction();
            } else {
                infoLabel.setText("В альбоме " + albumName + " уже есть песня с таким названием!");
            }
        }
    }

    @FXML
    private void songButtonCancelAction() {
        oldSongNameEditable = "";
        songNameTextField.clear();
        songSourcePathTextField.clear();
        songButtonCancel.setVisible(false);
        songButtonSave.setVisible(false);
        songButton.setVisible(true);
    }

    @FXML
    private void albumCoverPathButtonAction() {
        FileChooser coverChooser = new FileChooser();
        coverChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        File source = new File(albumCoverPathTextField.getText());
        if (source.exists()) {
            coverChooser.setInitialDirectory(new File(source.getParent()));
        }
        File selectedFile =
                coverChooser.showOpenDialog(this.main.stage);
        albumCoverPathTextField.setText(selectedFile.getPath());
    }

    @FXML
    private void songSourcePathButtonAction() {
        FileChooser songChooser = new FileChooser();
        songChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
                );
        File source = new File(songSourcePathTextField.getText());
        if (source.exists()) {
            songChooser.setInitialDirectory(new File(source.getParent()));
        }
        File selectedFile =
                songChooser.showOpenDialog(this.main.stage);
        songNameTextField.setText(selectedFile.getName());
        songSourcePathTextField.setText(selectedFile.getPath());
    }
}









class ArtistsTableItem {

    private final SimpleStringProperty artistName;

    public ArtistsTableItem(String artistName) {this.artistName = new SimpleStringProperty(artistName);}

    public String getArtistName() {
        return artistName.get();
    }

    public StringProperty artistNameProperty() {
        return artistName;
    }

    public void setArtistName(String arName) {
        artistName.set(arName);
    }
}

class AlbumsTableItem {

    private final SimpleStringProperty albumName;
    private final SimpleStringProperty albumYear;
    private final SimpleStringProperty albumCoverPath;

    public AlbumsTableItem(String albumName, String albumYear, String albumCoverPath) {

        this.albumName = new SimpleStringProperty(albumName);
        this.albumYear = new SimpleStringProperty(albumYear);
        this.albumCoverPath = new SimpleStringProperty(albumCoverPath);

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


    public String getAlbumCoverPath() {
        return albumCoverPath.get();
    }

    public StringProperty albumCoverPathProperty() {
        return albumCoverPath;
    }

    public void setAlbumCoverPath(String albCoverPath) {
        albumCoverPath.set(albCoverPath);
    }


}


class SongsTableItem {

    private final SimpleStringProperty songName;
    private final SimpleStringProperty songSourcePath;

    public SongsTableItem(String songName, String albumCoverPath) {

        this.songName = new SimpleStringProperty(songName);
        this.songSourcePath = new SimpleStringProperty(albumCoverPath);

    }


    public String getSongName() {
        return songName.get();
    }

    public StringProperty songNameProperty() {
        return songName;
    }

    public void setSongName(String alName) {
        songName.set(alName);
    }


    public String getSongSourcePath() {
        return songSourcePath.get();
    }

    public StringProperty songSourcePathProperty() {
        return songSourcePath;
    }

    public void setSongSourcePath(String sgSourcePath) {
        songSourcePath.set(sgSourcePath);
    }


}



