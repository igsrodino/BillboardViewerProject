package Server.Controllers;

import Server.Models.ScheduleModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleControllerTest {
    ScheduleModel model;
    ScheduleController SC;

    @BeforeEach
    void setupMain() {
        SC = new ScheduleController(model);
    }

    @Test
    void getBillboardSchedule() {
        assertEquals("Response", SC.getBillboardSchedule(1));
    }

    @Test
    void getBillboardScheduleErrors() {
        assertThrows(AssertionError.class, () -> {
            assertEquals(1, SC.getBillboardSchedule(1));
            assertEquals(1.0, SC.getBillboardSchedule(1));
            assertEquals("", SC.getBillboardSchedule(1));
        });
    }

    @Test
    void getCurrentBillboard() {
        assertEquals(1, SC.getCurrentBillboard());
    }

    @Test
    void getCurrentBillboardErrors() {
        assertThrows(AssertionError.class, () -> {
            assertEquals("d", SC.getCurrentBillboard());
            assertEquals(1.0, SC.getCurrentBillboard());
            assertEquals(-20, SC.getCurrentBillboard());
        });
    }

    @Test
    void setBillboardSchedule() {
        assertThrows(NullPointerException.class, () -> {
            assertEquals("", SC.setBillboardSchedule(1, 1000, 0100, 0010, 4));
            assertEquals(20, SC.setBillboardSchedule(1, 1000, 0100, 0010, 6));
            assertEquals(2.0, SC.setBillboardSchedule(1, 1000, 0100, 0010, 5));
        });
    }
}