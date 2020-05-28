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
        assertThrows(NullPointerException.class, () -> {
            assertEquals(1, BC.getBillboard(1));
            assertEquals(null, BC.getBillboard(1));
            assertEquals("", BC.getBillboard(1));
        });
    }

    @Test
    void convertDocumentToString() {

    }

    @Test
    void getBillboardList() {
           // assertEquals("Response", BC.getBillboardList());
    }

    @Test
    void deleteBillboard() {
        //assertEquals("Response", BC.deleteBillboard(2));

    }

    @Test
    void createBillboard() {
        assertEquals("Response", BC.createBillboard("#FF00FF", "This is a test",
                "#00FF00", "https://placebear.com/512/512", "url",
                "#FF00FF", "Testing", 3));
    }
}
