import org.junit.BeforeClass;
import ServiceLayer.*;
import LogicLayer.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ControllerTests {
    static Controller testController;

    @BeforeClass
    public static void init(){
        Administrator admin = new Administrator("A", "B", "C");
        Representitive rep = new Representitive(new User("A", "B", "C", null), "TEST");
        testController = new Controller(rep, admin);
    }

    @Test
    public void testBuildingObject() {
        Controller testController = new Controller();
        Administrator testAdmin = testController.getAdministrator();
        Representitive testRep = testController.getRepresentitive();
        assertEquals(testAdmin.getPassword(),"B");
    }
}
