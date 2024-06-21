package View;

import Network.Client.ClientController;
import View.GUI.Gui;
import model.client.ClientBoard;
import org.junit.jupiter.api.Test;

public class TestStationGUI {

    @Test
    public void startGUI() {
        Gui gui = new Gui();
        gui.launchGui(new ClientBoard(null, null, null, null, null, null, null), new ClientController(null, null));
    }
}
