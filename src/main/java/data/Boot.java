package data;

import helper.Clock;
import helper.StateManager;
import org.lwjgl.opengl.Display;

import static helper.Artist.BeginSection;

public class Boot {

    private Boot() {

        //initialize openGL calls
        BeginSection();

        // main game loop
        while (!Display.isCloseRequested()) {
            Clock.update();
            StateManager.update();
            Display.update();
//            Display.sync(60);
        }
        Display.destroy();
    }

    public static void main(String[] args) {
        new Boot();
    }
}