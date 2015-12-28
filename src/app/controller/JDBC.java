package app.controller;

import java.sql.*;
import java.lang.*;
import java.util.logging.*;

import app.model.AlbumItem;
import app.model.ArtistItem;
import app.model.TrackItemForAlbumTable;
import app.model.TrackItemForTrackTable;
import com.sun.istack.internal.NotNull;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by alex on 18.11.2015.
 */
public class JDBC {

    private static String urlDB = "D:\\Project\\Java\\Music catalog\\src\\app\\resources\\data\\mc.db";
    private static Connection connection = null;

    public static void registerDriver() {

        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Драйвер подключен");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ObservableList<TrackItemForTrackTable> getAllSongs() {
        ObservableList<TrackItemForTrackTable>  resultData = FXCollections.observableArrayList();

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+urlDB);
            System.out.println("Соединение установлено");

            Statement songsStatement = null;

            songsStatement = connection.createStatement();
            //Выполним запрос
            ResultSet songs = songsStatement.executeQuery(
                    "SELECT * FROM \"public\".\"songs\" OFFSET 0");

            while (songs.next()) {
                PreparedStatement albumPreparedStatement = null;
                albumPreparedStatement = connection.prepareStatement(
                        "SELECT * FROM albums where id = ? ");
                albumPreparedStatement.setInt(1, songs.getInt("album_id"));
                ResultSet album = albumPreparedStatement.executeQuery();

                PreparedStatement groupPreparedStatement = null;
                groupPreparedStatement = connection.prepareStatement(
                        "SELECT * FROM groups where id = ? ");
                album.next();
                groupPreparedStatement.setInt(1, album.getInt("group_id"));
                ResultSet group = groupPreparedStatement.executeQuery();
                group.next();

                resultData.add(
                        new TrackItemForTrackTable(
                        songs.getString("name"),
                        group.getString("name"),
                        album.getString("name"),
                        album.getString("year"),
                        songs.getTime("lenght").toString().substring(3),
                        songs.getString("source"))
                );
            }

        } catch (Exception ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    return resultData;
    }


    public static ObservableList<AlbumItem> getAllAlbums() {
        ObservableList<AlbumItem>  resultData = FXCollections.observableArrayList();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+urlDB);
            System.out.println("Соединение установлено");

            Statement albumsStatement = null;
            albumsStatement = connection.createStatement();
            ResultSet album = albumsStatement.executeQuery(
                    "SELECT * FROM \"public\".\"albums\" OFFSET 0");

            while (album.next()) {
                PreparedStatement groupPreparedStatement = null;
                groupPreparedStatement = connection.prepareStatement(
                        "SELECT * FROM groups where id = ? ");
                groupPreparedStatement.setInt(1, album.getInt("group_id"));
                ResultSet group = groupPreparedStatement.executeQuery();
                group.next();

                PreparedStatement songsPreparedStatement = null;
                songsPreparedStatement = connection.prepareStatement(
                        "SELECT * FROM songs where album_id = ? ");
                songsPreparedStatement.setInt(1, album.getInt("id"));
                ResultSet songs = songsPreparedStatement.executeQuery();

                ObservableList<TrackItemForAlbumTable> trackList = FXCollections.observableArrayList();
                int i = 1;
                while (songs.next()) {
                    trackList.add(
                            new TrackItemForAlbumTable(
                                    Integer.toString(i),
                                    songs.getString("name"),
                                    songs.getTime("lenght").toString().substring(3),
                                    songs.getString("source"))
                    );
                    i++;
                }

                resultData.add(
                        new AlbumItem(
                                album.getString("name"),
                                album.getInt("year"),
                                group.getString("name"),
                                album.getString("cover"),
                               trackList)
                );
            }

        } catch (Exception ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return resultData;
    }


    public static ObservableList<String> getAllGroups() {
        ObservableList<String>  resultData = FXCollections.observableArrayList();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+urlDB);
            System.out.println("Соединение установлено");
            Statement getGroupsStatement = null;
            getGroupsStatement = connection.createStatement();
            ResultSet group = getGroupsStatement.executeQuery(
                    "SELECT * FROM \"public\".\"groups\" OFFSET 0");
            while (group.next()) {
                resultData.add(group.getString("name"));
            }

        } catch (Exception ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return resultData;
    }




    public static ObservableList<AlbumItem> getAlbumsOfGroup(String groupName) {
        ObservableList<AlbumItem>  resultData = FXCollections.observableArrayList();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+urlDB);
            System.out.println("Соединение установлено");

            PreparedStatement groupPreparedStatement = null;
            groupPreparedStatement = connection.prepareStatement(
                    "SELECT * FROM groups where name = ? ");
            groupPreparedStatement.setString(1, groupName);
            ResultSet group = groupPreparedStatement.executeQuery();
            group.next();

            PreparedStatement albumsPreparedStatement = null;
            albumsPreparedStatement = connection.prepareStatement(
                    "SELECT * FROM albums WHERE group_id = ? ");
            albumsPreparedStatement.setInt(1, group.getInt("id"));
            ResultSet album = albumsPreparedStatement.executeQuery();
            while (album.next()) {
                PreparedStatement songsPreparedStatement = null;
                songsPreparedStatement = connection.prepareStatement(
                        "SELECT * FROM songs where album_id = ? ");
                songsPreparedStatement.setInt(1, album.getInt("id"));
                ResultSet songs = songsPreparedStatement.executeQuery();

                ObservableList<TrackItemForAlbumTable> trackList = FXCollections.observableArrayList();
                int i = 1;
                while (songs.next()) {
                    trackList.add(
                            new TrackItemForAlbumTable(
                                    Integer.toString(i),
                                    songs.getString("name"),
                                    songs.getTime("lenght").toString().substring(3),
                                    songs.getString("source"))
                    );
                    i++;
                }

                resultData.add(
                        new AlbumItem(
                                album.getString("name"),
                                album.getInt("year"),
                                group.getString("name"),
                                album.getString("cover"),
                                trackList)
                );

            }

        } catch (Exception ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return resultData;
    }




    public static ObservableList<TrackItemForAlbumTable> getSongsOfAlbum(String albumName) {
        ObservableList<TrackItemForAlbumTable>  resultData = FXCollections.observableArrayList();
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+urlDB);
            System.out.println("Соединение установлено");

            PreparedStatement albumsPreparedStatement = null;
            albumsPreparedStatement = connection.prepareStatement(
                    "SELECT * FROM albums WHERE name = ? ");
            albumsPreparedStatement.setString(1, albumName);
            ResultSet album = albumsPreparedStatement.executeQuery();
            album.next();

            PreparedStatement songsPreparedStatement = null;
            songsPreparedStatement = connection.prepareStatement(
                    "SELECT * FROM songs where album_id = ? ");
            songsPreparedStatement.setInt(1, album.getInt("id"));
            ResultSet songs = songsPreparedStatement.executeQuery();
             int i = 1;
                while (songs.next()) {
                    resultData.add(
                            new TrackItemForAlbumTable(
                                    Integer.toString(i),
                                    songs.getString("name"),
                                    songs.getTime("lenght").toString().substring(3),
                                    songs.getString("source"))
                    );
                    i++;
                }

        } catch (Exception ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return resultData;
    }

    public static boolean addGroup(String groupName) {
        Boolean result = false;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+urlDB);
            System.out.println("Соединение установлено");

            PreparedStatement checkPreparedStatement = null;
            checkPreparedStatement = connection.prepareStatement(
                    "SELECT * FROM groups where name = ?");
            checkPreparedStatement.setString(1, groupName);
            ResultSet check = checkPreparedStatement.executeQuery();

            if (check.next() == false) {
                PreparedStatement groupPreparedStatement = null;
                groupPreparedStatement = connection.prepareStatement(
                        "INSERT INTO groups VALUES (NEXTVAL('group_ids'), ?)");
                groupPreparedStatement.setString(1, groupName);
                groupPreparedStatement.executeUpdate();
                result = true;
            }
            else {
                result =  false;
            }


        } catch (Exception ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }


    public static boolean addAlbum(String albumName, Integer albumYear, String albumCoverPath,  String groupName) {
        Boolean result = false;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+urlDB);
            System.out.println("Соединение установлено");

            PreparedStatement groupPreparedStatement = null;
            groupPreparedStatement = connection.prepareStatement(
                    "SELECT * FROM groups where name = ? ");
            groupPreparedStatement.setString(1, groupName);
            ResultSet group = groupPreparedStatement.executeQuery();
            group.next();

            PreparedStatement checkPreparedStatement = null;
            checkPreparedStatement = connection.prepareStatement(
                    "SELECT * FROM albums where name = ? and group_id = ? ");
            checkPreparedStatement.setString(1, albumName);
            checkPreparedStatement.setInt(2, group.getInt("id"));
            ResultSet check = checkPreparedStatement.executeQuery();

            if (check.next() == false) {

                PreparedStatement albumPreparedStatement = null;
                albumPreparedStatement = connection.prepareStatement(
                        "INSERT INTO albums (ID, group_id, name, year, cover) VALUES (NEXTVAL('album_ids'),  ?,  ?,  ?,  ?)");
                albumPreparedStatement.setInt(1, group.getInt("id"));
                albumPreparedStatement.setString(2, albumName);
                albumPreparedStatement.setInt(3, albumYear);
                albumPreparedStatement.setString(4, albumCoverPath);
                albumPreparedStatement.executeUpdate();
                result = true;
            }
            else {
                result =  false;
            }


        } catch (Exception ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }

    public static boolean addSong(String songName, String songSourcePath, Time songLenght,  String albumName) {
        Boolean result = false;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+urlDB);
            System.out.println("Соединение установлено");

            PreparedStatement albumPreparedStatement = null;
            albumPreparedStatement = connection.prepareStatement(
                    "SELECT * FROM albums where name = ? ");
            albumPreparedStatement.setString(1, albumName);
            ResultSet album = albumPreparedStatement.executeQuery();
            album.next();

            PreparedStatement checkPreparedStatement = null;
            checkPreparedStatement = connection.prepareStatement(
                    "SELECT * FROM songs where name = ? and album_id = ?");
            checkPreparedStatement.setString(1, songName);
            checkPreparedStatement.setInt(2, album.getInt("id"));
            ResultSet check = checkPreparedStatement.executeQuery();

            if (check.next() == false) {

                PreparedStatement songPreparedStatement = null;
                songPreparedStatement = connection.prepareStatement(
                        "INSERT INTO songs (ID, album_id, name, source, lenght) VALUES (NEXTVAL('song_ids'), ? , ?,  ?, ?)");
                songPreparedStatement.setInt(1, album.getInt("id"));
                songPreparedStatement.setString(2, songName);
                songPreparedStatement.setString(3, songSourcePath);
                songPreparedStatement.setTime(4, songLenght);
                songPreparedStatement.executeUpdate();
                result = true;
            }
            else {
                result =  false;
            }


        } catch (Exception ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }

    public static void deleteGroup(String groupName) {
        try {

            connection = DriverManager.getConnection("jdbc:sqlite:"+urlDB);
            System.out.println("Соединение установлено");


            PreparedStatement groupPreparedStatement = null;
            groupPreparedStatement = connection.prepareStatement(
                    "SELECT * FROM groups where name = ? ");
            groupPreparedStatement.setString(1, groupName);
            ResultSet group = groupPreparedStatement.executeQuery();
            group.next();

            PreparedStatement albumsPreparedStatement = null;
            albumsPreparedStatement = connection.prepareStatement(
                    "SELECT * FROM albums WHERE group_id = ? ");
            albumsPreparedStatement.setInt(1, group.getInt("id"));
            ResultSet album = albumsPreparedStatement.executeQuery();
            while (album.next()) {
                PreparedStatement songsPreparedStatement = null;
                songsPreparedStatement = connection.prepareStatement(
                        "DELETE FROM songs WHERE album_id=?;\n" +
                                "DELETE FROM albums WHERE id= ?;");
                songsPreparedStatement.setInt(1, album.getInt("id"));
                songsPreparedStatement.setInt(2, album.getInt("id"));
                songsPreparedStatement.executeUpdate();
            }

            PreparedStatement deleteGroupPreparedStatement = null;
            deleteGroupPreparedStatement = connection.prepareStatement(
                    "DELETE FROM groups WHERE name=?");
            deleteGroupPreparedStatement.setString(1, groupName);
            deleteGroupPreparedStatement.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void deleteAlbum(String albumName) {
        try {

            connection = DriverManager.getConnection("jdbc:sqlite:"+urlDB);
            System.out.println("Соединение установлено");

            PreparedStatement albumsPreparedStatement = null;
            albumsPreparedStatement = connection.prepareStatement(
                    "SELECT * FROM albums WHERE name = ? ");
            albumsPreparedStatement.setString(1, albumName);
            ResultSet album = albumsPreparedStatement.executeQuery();
            album.next();

            PreparedStatement songsPreparedStatement = null;
            songsPreparedStatement = connection.prepareStatement(
                    "DELETE FROM songs WHERE album_id=?;\n" +
                            "DELETE FROM albums WHERE id= ?;");
            songsPreparedStatement.setInt(1, album.getInt("id"));
            songsPreparedStatement.setInt(2, album.getInt("id"));
            songsPreparedStatement.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void deleteSong(String songName) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+urlDB);
            System.out.println("Соединение установлено");

            PreparedStatement songsPreparedStatement = null;
            songsPreparedStatement = connection.prepareStatement(
                    "DELETE FROM songs WHERE name =?");
            songsPreparedStatement.setString(1, songName);
            songsPreparedStatement.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }


    public static boolean editGroup(String oldGroupName, String groupName) {
        Boolean result = false;
        try {

            connection = DriverManager.getConnection("jdbc:sqlite:"+urlDB);
            System.out.println("Соединение установлено");

            PreparedStatement checkPreparedStatement = null;
            checkPreparedStatement = connection.prepareStatement(
                    "SELECT * FROM groups where name = ?");
            checkPreparedStatement.setString(1, groupName);
            ResultSet check = checkPreparedStatement.executeQuery();

            if (check.next() == false) {
                PreparedStatement groupPreparedStatement = null;
                groupPreparedStatement = connection.prepareStatement(
                        "UPDATE groups SET name=? WHERE name=?");
                groupPreparedStatement.setString(1, groupName);
                groupPreparedStatement.setString(2, oldGroupName);
                groupPreparedStatement.executeUpdate();
                result = true;
            }
            else {
                result =  false;
            }


        } catch (Exception ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }


    public static boolean editAlbum(String oldAlbumName, String albumName, Integer albumYear, String albumCoverPath) {
        Boolean result = false;
        try {

            connection = DriverManager.getConnection("jdbc:sqlite:"+urlDB);
            System.out.println("Соединение установлено");

            PreparedStatement checkPreparedStatement = null;
            checkPreparedStatement = connection.prepareStatement(
                    "SELECT * FROM albums where name = ?");
            checkPreparedStatement.setString(1, albumName);
            ResultSet check = checkPreparedStatement.executeQuery();

            if (!check.next() || check.getString("name").equals(oldAlbumName)) {
                PreparedStatement albumPreparedStatement = null;
                albumPreparedStatement = connection.prepareStatement(
                        "UPDATE albums SET name=?, year=?, cover=? WHERE name=?");
                albumPreparedStatement.setString(1, albumName);
                albumPreparedStatement.setInt(2, albumYear);
                albumPreparedStatement.setString(3, albumCoverPath);
                albumPreparedStatement.setString(4, oldAlbumName);
                albumPreparedStatement.executeUpdate();
                result = true;
            }
            else {
                result =  false;
            }


        } catch (Exception ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }


    public static boolean editSong(String oldSongName, String songName, String songSourcePath,  Time songLenght) {
        Boolean result = false;
        try {

            connection = DriverManager.getConnection("jdbc:sqlite:"+urlDB);
            System.out.println("Соединение установлено");

            PreparedStatement checkPreparedStatement = null;
            checkPreparedStatement = connection.prepareStatement(
                    "SELECT * FROM songs where name = ?");
            checkPreparedStatement.setString(1, songName);
            ResultSet check = checkPreparedStatement.executeQuery();

            if (!check.next() || check.getString("name").equals(oldSongName)) {
                PreparedStatement songPreparedStatement = null;
                songPreparedStatement = connection.prepareStatement(
                        "UPDATE songs SET name=?, source=?, lenght=? WHERE name=?");
                songPreparedStatement.setString(1, songName);
                songPreparedStatement.setString(2, songSourcePath);
                songPreparedStatement.setTime(3, songLenght);
                songPreparedStatement.setString(4, oldSongName);
                songPreparedStatement.executeUpdate();
                result = true;
            }
            else {
                result =  false;
            }


        } catch (Exception ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }


    public static boolean checkUser(String userName, String password) {
        Boolean result = false;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+urlDB);
            System.out.println("Соединение установлено");

            PreparedStatement checkPreparedStatement = null;
            checkPreparedStatement = connection.prepareStatement(
                    "SELECT * FROM users where username = ? and password=?");
            checkPreparedStatement.setString(1, userName);
            checkPreparedStatement.setString(2, password);
            ResultSet check = checkPreparedStatement.executeQuery();

            if (check.next()) {
                result = true;
            }
            else {
                result =  false;
            }

        } catch (Exception ex) {
            Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(JDBC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return result;
    }

}

