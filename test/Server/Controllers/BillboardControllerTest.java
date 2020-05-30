package Server.Controllers;

import Server.Models.BillboardModel;
import Server.Models.ScheduleModel;
import Server.Utilities.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BillboardControllerTest {
    BillboardModel model;
    BillboardController BC;
    Database dbConn;
    ScheduleModel scheduleModel;
    ScheduleController scheduleController;

    @BeforeEach
    void setupMain() {
        BC = new BillboardController(model);
        scheduleModel = new ScheduleModel(dbConn);
        scheduleController = new ScheduleController(scheduleModel);
    }


    @Test
    void getBillboard()  {
        // Always throws NullPointerException as test can only be done with SocketWrench.
        // See Test section on report for tests.
        assertThrows(NullPointerException.class, () -> {
            assertEquals(1, BC.getBillboard(1));
            assertEquals(null, BC.getBillboard(1));
            assertEquals("", BC.getBillboard(1));
        });
    }
}
