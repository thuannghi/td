package UI;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static helper.Artist.*;

public class UI {

    private List<Button> buttonList;
    private List<Menu> menuList;
    private TrueTypeFont font;
    private Font awtFont;

    public UI() {
        buttonList = new ArrayList<Button>();
        menuList = new ArrayList<Menu>();
        awtFont = new Font("Times New Roman", Font.BOLD, 24);
        font = new TrueTypeFont(awtFont, false);
    }

    public void drawString (int x, int y, String text) {
        font.drawString(x, y, text);
    }

    public void addButton(String name, String textureName, int x, int y) {
        buttonList.add(new Button(name, quickLoad(textureName), x, y));
    }

    public boolean isBouttonClicked(String buttonName) {
        Button button = getButton(buttonName);
        float mouseY = HEIGHT - Mouse.getY() - 1;
        return Mouse.getX() > button.getX() && Mouse.getX() < button.getX() + button.getWidth() &&
                mouseY > button.getY() && mouseY < button.getY() + button.getHeight();
    }

    public Button getButton(String buttonName) {
        for (Button b : buttonList) {
            if (b.getName().equals(buttonName)) {
                return b;
            }
        }
        return null;
    }

    public void draw() {
        for (Button b : buttonList) {
            drawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
        }
        for (Menu m : menuList) {
            m.draw();
        }
    }

    public void createMenu(String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight) {
        menuList.add(new Menu(name, x, y, width, height, optionsWidth, optionsHeight));
    }

    public Menu getMenu(String name) {
        for (Menu m : menuList) {
            if (name.equals(m.getName())) {
                return m;
            }
        }
        return null;
    }

    public class Menu {

        private String name;
        private List<Button> menuButtons;
        private int x, y, width, height,  buttonAmount, optionsWidth, optionsHeght, padding;

        public Menu(String name, int x, int y, int width, int height, int optionsWidth, int optionsHeight) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.optionsWidth = optionsWidth;
            this.optionsHeght = optionsHeight;
            this.padding = (width - (optionsWidth * TILE_SIZE)) / (optionsWidth + 1);
            this.buttonAmount = 0;
            this.menuButtons = new ArrayList<Button>();
        }

        public void addButton(Button b) {
            setButton(b);
        }

        public void quickAdd (String name, String buttonTextureName) {
            Button b = new Button(name, quickLoad(buttonTextureName), 0, 0);
            setButton(b);
        }

        private void setButton (Button b) {
            if (optionsWidth != 0) {
                b.setY (y + (buttonAmount / optionsWidth) * TILE_SIZE);
            }
            b.setX(x + (buttonAmount % 2) * (padding + TILE_SIZE) + padding);
            buttonAmount++;
            menuButtons.add(b);
        }

        public void draw() {
            for (Button b : menuButtons) {
                drawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
            }
        }

        public boolean isBouttonClicked(String buttonName) {
            Button button = getButton(buttonName);
            float mouseY = HEIGHT - Mouse.getY() - 1;
            return Mouse.getX() > button.getX() && Mouse.getX() < button.getX() + button.getWidth() &&
                    mouseY > button.getY() && mouseY < button.getY() + button.getHeight();
        }

        public Button getButton(String buttonName) {
            for (Button b : menuButtons) {
                if (b.getName().equals(buttonName)) {
                    return b;
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
