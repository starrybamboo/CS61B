package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    static final int WIDTH = 60;
    static final int HEIGHT = 40;
    static final int LEFT = 1;
    static final int RIGHT = 2;

    static final int DOWN = 3;
    static final int SIZE_2 = 2;
    static final int SIZE_3 = 3;
    static final int SIZE_4 = 4;


    public static void main(String[] args){
        int size = 4;
        TERenderer hexRender = new TERenderer();
        hexRender.initialize(WIDTH,HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        int start_x = WIDTH / 2;
        int start_y = 0;
        for (int i = 0; i < 5; i++) {
            if (i < 3) {
                addHexagon(world, size, start_x, start_y);
                connectHexagon(world, start_x, start_y, LEFT, size,2);
                connectHexagon(world, start_x, start_y, RIGHT, size,2);
            } else {
                addHexagon(world, size, start_x, start_y);
                if (i == 3) {
                    connectHexagon(world, start_x, start_y, LEFT, size,1);
                    connectHexagon(world, start_x, start_y, RIGHT, size,1);
                }
            }
            start_y = start_y + size * 2;
        }

        hexRender.renderFrame(world);
    }

    private static void addHexagon(TETile[][] world, int size, int position_x, int position_y) {
        int tmpSize = size;
        TETile fillBlank = randomTile();
        for (int i = 0; i < size; i++){
            fillBlanket(world, tmpSize, position_x, position_y, fillBlank);
            position_y += 1;
            tmpSize += 2;
            position_x -= 1;
        }
        for (int i = 0; i < size; i++){
            tmpSize -= 2;
            position_x += 1;
            fillBlanket(world, tmpSize, position_x, position_y, fillBlank);
            position_y += 1;
        }

    }

    private static void fillBlanket(TETile[][] world, int size, int position_x, int position_y, TETile fillBlank){
        for (int i = position_x; i < position_x + size; i++){
            world[i][position_y] = fillBlank;
        }
    }

    private static TETile randomTile() {
        int tileNum = new Random().nextInt(3);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            default: return Tileset.SAND;
        }
    }

    private static void connectHexagon(TETile[][] world,int position_x, int position_y, int orientation, int size, int times){
        switch (orientation){
            case LEFT :
                for (int i = 0; i < times; i++){
                    position_y = position_y + size;
                    position_x = position_x - 2 * size + 1;
                    addHexagon(world, size, position_x, position_y);
                }
                break;
            case RIGHT:
                for (int i = 0; i < times; i++){
                    position_y = position_y + size;
                    position_x = position_x + 2 * size - 1;
                    addHexagon(world, size, position_x, position_y);
                }
                break;
        }
    }
}
