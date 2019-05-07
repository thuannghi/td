package helper;

import data.Tile;
import data.TileGrid;
import data.TileType;

import java.io.*;

public class Leveler {

    public static void saveMap (String mapName, TileGrid grid) {
        String mapData = "";
        for (int i = 0; i < grid.getTilesWide(); i ++) {
            for (int j = 0; j < grid.getTilesHigh(); j++) {
                mapData += getTileID(grid.getTile(i, j));
            }
        }

        File file = new File(mapName);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(mapData);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TileType getTileType(String ID) {
        TileType tileType = TileType.NULL;
        if ("0".equals(ID)) {
            tileType = TileType.Grass;
        } else if ("1".equals(ID)) {
            tileType = TileType.Dirt;
        } else if ("2".equals(ID)) {
            tileType = TileType.Water;
        }

        return tileType;
    }

    public static TileGrid loadMap (String mapName) {
        TileGrid grid = new TileGrid();
        try {
            BufferedReader br = new BufferedReader(new FileReader(mapName));
            String data = br.readLine();
            for (int i = 0; i < grid.getTilesWide(); i++) {
                for (int j = 0; j < grid.getTilesHigh(); j++) {
                    grid.setTile(i, j, getTileType(data.substring(i * grid.getTilesHigh() + j, i * grid.getTilesHigh() + j + 1)));
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return grid;
    }

    public static String getTileID (Tile t) {
        String ID = "E";
        switch (t.getTileType()) {
            case Grass:
                ID = "0";
                break;
            case Dirt:
                ID = "1";
                break;
            case Water:
                ID = "2";
                break;
            case NULL:
                ID = "3";
                break;
        }
        return ID;
    }
}
