import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserTest extends User {

    public UserTest(String email, String password, String userName, system system, List<Role> roles) {
        super(email, password, userName, system, roles);
    }

    @Test
    public void testGetEmail() {
        Assert.assertTrue(true);

    }
    @Test
    public void testGetEmail2() {
        Assert.assertTrue(false);
    }
    @Test
    public void testGetEmail3() {
        Assert.assertTrue(false);
    }
}