import app.view.authorizationLayout.AuthorizationLayoutController;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by alex on 12.12.2015.
 */
public class AuthorizationLayoutControllerTest extends Assert {
    AuthorizationLayoutController authorizationLayoutController = new AuthorizationLayoutController();

    @Test
    public void testCheckOnSymbol() {
        //Boolean expected = true;
        Boolean actual =  authorizationLayoutController.checkOnSymbol("xfgchbj");
        assertFalse(actual);
    }

    @Test
    public void testIsEmpty() {

        Boolean actual =  authorizationLayoutController.isEmpty("");
        assertTrue(actual);
    }

    @Test
    public void testIsСorrectLength() {
        Boolean actual =  authorizationLayoutController.isСorrectLength("asdf");
        assertTrue(actual);
    }
}
