package manager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void getDefaultHistory() {
        assertNotNull(Managers.getDefaultHistory(), "ManagerHistory не найден");
    }

    @Test
    void getDefault() {
        assertNotNull(Managers.getDefault(), "Manager не найден");
    }
}