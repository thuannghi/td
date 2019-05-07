package data;

import UI.UI;
import UI.UI.Menu;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import static helper.Artist.*;
import static helper.Artist.quickLoad;
import static helper.Leveler.loadMap;
import static helper.Leveler.saveMap;

public class Editor {

    private final TileType[] tileTypes;
    private TileGrid tileGrid;
    private int index;
    private UI editorUI;
    private Menu tilePickerMenu;
    private Texture menuBackground;

    public Editor() {
        tileGrid = loadMap("newMap1");
        this.index = 0;
        tileTypes = new TileType[3];
        tileTypes[0] = TileType.Grass;
        tileTypes[1] = TileType.Dirt;
        tileTypes[2] = TileType.Water;
        this.menuBackground = quickLoad("menu_background2");
        setupUI();
    }

    private void setupUI() {
        editorUI = new UI();
        editorUI.createMenu("TilePicker", 1280, 100, 192, 960, 2, 0);
        tilePickerMenu = editorUI.getMenu("TilePicker");
        tilePickerMenu.quickAdd("Grass", "grass");
        tilePickerMenu.quickAdd("Dirt", "dirt");
        tilePickerMenu.quickAdd("Water", "water");
    }

    public void update() {
        draw();
        // handle mouse input
        if (Mouse.next()) {
            boolean mouseClicked = Mouse.isButtonDown(0);
            if (mouseClicked) {
                if (tilePickerMenu.isBouttonClicked("Grass")) {
                    index = 0;
                } else if (tilePickerMenu.isBouttonClicked("Dirt")) {
                    index = 1;
                } else if (tilePickerMenu.isBouttonClicked("Water")) {
                    index = 2;
                } else {
                    setTile();
                }
            }

            // handle keyboard input
            while (Keyboard.next()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState()) {
                    saveMap("newMap1", tileGrid);
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_A && Keyboard.getEventKeyState()) {
                    moveIndex();
                }
            }

        }

    }

    private void moveIndex() {
        index++;
        if (index > tileTypes.length - 1) {
            index = 0;
        }
    }

    private void draw() {
        drawQuadTex(menuBackground, 1280, 0, 192, 960);
        tileGrid.draw();
        editorUI.draw();
    }

    private void setTile() {
//        tileGrid.setTile((int) Math.floor(Mouse.getX() / TILE_SIZE), (int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE), TileType.Dirt);
        tileGrid.setTile((int) Math.floor(Mouse.getX() / TILE_SIZE), (int) Math.floor((HEIGHT - Mouse.getY() - 1) / TILE_SIZE), tileTypes[index]);
    }


}
