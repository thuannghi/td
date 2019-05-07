package data;

import helper.StateManager;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;
import UI.UI;

import static helper.Artist.*;

public class MainMenu {

    private Texture background;
    private UI menuUI;

    public MainMenu () {
        background = quickLoad("sun");
        menuUI = new UI();
        menuUI.addButton("Play", "playbutton", WIDTH / 2 - 128, (int) (HEIGHT * 0.45f));
//        menuUI.addButton("Editor", "editorbutton", WIDTH / 2 - 128, (int) (HEIGHT * 0.55f));
        menuUI.addButton("Quit", "quitbutton", WIDTH / 2 - 128, (int) (HEIGHT * 0.55f));
    }

    public void update () {
        drawQuadTex(background, 0, 0, 2048, 1024);
        menuUI.draw();
        updateButtons();
    }

    private void updateButtons () {
        if (Mouse.isButtonDown(0)) {
            if (menuUI.isBouttonClicked("Play")) {
                StateManager.setGameState(StateManager.GameState.GAME);
            }
//            if (menuUI.isBouttonClicked("Editor")) {
//                StateManager.setGameState(StateManager.GameState.EDITOR);
//            }
            if (menuUI.isBouttonClicked("Quit")) {
                System.exit(0);
            }
        }
    }
}
