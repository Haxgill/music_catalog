package app.test;
import app.controller.JDBC;
import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by alex on 12.12.2015.
 */
public class JDBCTest extends Assert {



    @Test
    public void testAddGroup() {
        //a-b-d
        Boolean actual1 = JDBC.addGroup("Notwist");
        assertFalse(actual1);
        //a-c-d
        Boolean actual2 = JDBC.addGroup("Abcd");
        assertTrue(actual2);
    }


    @Test
    public void testCheckUser() {
        //a-b-c-e
        Boolean actual1 = JDBC.checkUser("admin", "admin");
        assertTrue(actual1);
        //a-b-d-e
        Boolean actual2 = JDBC.checkUser("user", "123");
        assertFalse(actual2);
    }


}
