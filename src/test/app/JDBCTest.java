import app.controller.JDBC;
import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by alex on 12.12.2015.
 */
public class JDBCTest extends Assert {

    @Test
    public void testCheckUser() {
        Boolean actual = JDBC.checkUser("123", "123");
        assertTrue(actual);
    }

    @Test
    public void testAddGroup() {
        Boolean actual = JDBC.addGroup("smile");
        assertFalse(actual);
    }

    @Test
    public void testAddAlbum() {
        Boolean actual = JDBC.addAlbum("album",2002, "", "smile");
        assertFalse(actual);
    }



}
