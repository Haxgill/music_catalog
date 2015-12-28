package app;

        import app.controller.JDBC;
        import app.test.AuthorizationLayoutControllerTest;
        import app.test.JDBCTest;
        import app.view.albumList.AlbumListController;
        import app.view.albumList.AlbumObjectController;
        import app.view.artistList.ArtistListController;
        import app.view.authorizationLayout.AuthorizationLayoutController;
        import app.view.playerMenu.PlayerMenuController;
        import app.view.trackList.TrackListController;
        import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.layout.AnchorPane;
        import javafx.stage.Stage;
        import java.io.IOException;



public class Main extends Application {

    public Scene scene = new Scene(new AnchorPane());
    public Stage stage;
    public Parent TrackList;
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("Music Catalog");
        generateScene();
        com.automatedqa.testcomplete.UnitTesting.AddClasses(new Class[]{JDBCTest.class, AuthorizationLayoutControllerTest.class});
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(getClass().getResource("view/Main.css").toExternalForm());
        stage.show();
        JDBC.registerDriver();
    }

    public void generateScene() {
        try {
            FXMLLoader loaderPlayerMenu = new FXMLLoader();
            loaderPlayerMenu.setLocation(Main.class.getResource("/app/view/playerMenu/PlayerMenu.fxml"));
            Parent PlayerMenu = (Parent) loaderPlayerMenu.load();
            PlayerMenuController PlayerMenuCr = loaderPlayerMenu.getController();
            PlayerMenuCr.setMainApp(this);


            FXMLLoader loaderTrackList = new FXMLLoader();
            loaderTrackList.setLocation(Main.class.getResource("/app/view/trackList/TrackList.fxml"));
            TrackList = (Parent) loaderTrackList.load();
            TrackListController TrackListCr = loaderTrackList.getController();
            TrackListCr.initializeData(JDBC.getAllSongs());

            ((AnchorPane) scene.getRoot()).getChildren().add(PlayerMenu);
            ((AnchorPane) scene.getRoot()).setTopAnchor(PlayerMenu, 0.0);
            ((AnchorPane) scene.getRoot()).setLeftAnchor(PlayerMenu, 0.0);
            ((AnchorPane) scene.getRoot()).setRightAnchor(PlayerMenu, 0.0);

            ((AnchorPane) scene.getRoot()).getChildren().add(TrackList);
            ((AnchorPane) scene.getRoot()).setTopAnchor(TrackList, 51.0);
            ((AnchorPane) scene.getRoot()).setLeftAnchor(TrackList, 0.0);
            ((AnchorPane) scene.getRoot()).setRightAnchor(TrackList, 0.0);
            ((AnchorPane) scene.getRoot()).setBottomAnchor(TrackList, 0.0);



        } catch (IOException e) {
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
