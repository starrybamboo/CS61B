package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.Random;

import static byow.Core.Utils.*;

public class Engine implements Serializable {

    TETile[][] finalWorldFrame;
    TERenderer ter = new TERenderer();
    int avatar_x;
    int avatar_y;
    Random rand;

    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public String userInput;
    public static final File CWD = new File(System.getProperty("user.dir"));

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        startUI();
        StdDraw.show();
        this.userInput = solicitNCharsInput();
        generateUser();
        StartGame();
    }

    public void StartGame(){
        boolean flag = false;
        while (true) {
            if (StdDraw.hasNextKeyTyped()){
                char input = StdDraw.nextKeyTyped();
                this.userInput += input;
                if (flag){
                    if (input =='Q' || input == 'q'){
                        this.save();
                    }
                }
                if (input == ':'){
                    flag = true;
                    continue;
                }
                switch (input){
                    case 'W':
                        judge(avatar_x, avatar_y + 1);
                        break;
                    case 'A':
                        judge(avatar_x - 1, avatar_y);
                        break;
                    case 'S':
                        judge(avatar_x, avatar_y - 1);
                        break;
                    case 'D':
                        judge(avatar_x + 1, avatar_y);
                        break;
                    case 'L':
                        load();
                        break;
                }
            }
            if (StdDraw.mouseX() >= WIDTH || StdDraw.mouseX() < 0){continue;}
            if (StdDraw.mouseY() >= HEIGHT || StdDraw.mouseY() < 0){continue;}
            ter.renderFrame(finalWorldFrame,finalWorldFrame[(int)StdDraw.mouseX()][(int)StdDraw.mouseY()].description());
            StdDraw.pause(10);
        }
    }

//
//    private void save() {
//        File f = new File(CWD,"save.txt");
//        try {
//            if (!f.exists()) {
//                f.createNewFile();
//            }
//            FileOutputStream fs = new FileOutputStream(f);
//            ObjectOutputStream os = new ObjectOutputStream(fs);
//            os.writeObject(this);
//        }  catch (FileNotFoundException e) {
//            System.out.println("file not found");
//            System.exit(0);
//        } catch (IOException e) {
//            System.out.println(e);
//            System.exit(0);
//        }
//    }
//
//    private void Load() {
//        File f = new File(CWD,"save.txt");
//        if (f.exists()) {
//            try {
//                FileInputStream fs = new FileInputStream(f);
//                ObjectInputStream os = new ObjectInputStream(fs);
//                Engine newEngine = (Engine)os.readObject();
//                newEngine.StartGame();
//            } catch (FileNotFoundException e) {
//                System.out.println("file not found");
//                System.exit(0);
//            } catch (IOException e) {
//                System.out.println("there is an error");
//            } catch (ClassNotFoundException e) {
//                System.out.println("class not found");
//                System.exit(0);
//            }
//        }
//    }

    public boolean judge(int x,int y){
        if (!finalWorldFrame[x][y].description().equals(Tileset.WALL.description())){
            move(x,y);
            ter.renderFrame(finalWorldFrame);
            return true;
        }return false;
    }

    public void move(int x, int y){
        finalWorldFrame[avatar_x][avatar_y] = Tileset.FLOOR;
        finalWorldFrame[x][y] = Tileset.AVATAR;
        this.avatar_x = x;
        this.avatar_y = y;
    }

    public void generateUser(){
        for (int i = 1; i < WIDTH ; i ++){
            for (int j = 1; j < HEIGHT ; j ++){
                if (finalWorldFrame[i][j] == Tileset.FLOOR) {
                    finalWorldFrame[i][j] = Tileset.AVATAR;
                    ter.renderFrame(finalWorldFrame);
                    this.avatar_x = i;
                    this.avatar_y = j;
                    return ;
                }
            }
        }
    }

    public void startUI(){
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);

        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.WHITE);

        StdDraw.text(WIDTH/2, HEIGHT/2 + 10, "CS61B");
        StdDraw.text(WIDTH/2, HEIGHT/2, "Press L to load a game!(if you save.)");
        StdDraw.text(WIDTH/2, HEIGHT/2 - 10, "Type N###S to create a new world!");

    }

    public String solicitNCharsInput() {
        //TODO: Read n letters of player input
        int i = 0;
        String tmp = "";
        boolean flag = false;
        while (true){
            if (StdDraw.hasNextKeyTyped()){
                char s = StdDraw.nextKeyTyped();
                if (s == 'n' || s == 'N'){
                    flag = true;
                }
                if (s == 'l' || s == 'L'){
                    load();
                }
                if (flag){
                    tmp += s;
                    if (s == 's' || s == 'S'){
                        StdDraw.clear(StdDraw.BLACK);
                        interactWithInputString(tmp);
                        return tmp;
                    }
                    StdDraw.clear(StdDraw.BLACK);
                    StdDraw.text(WIDTH/2, HEIGHT/2 - 5, tmp);
                    StdDraw.text(WIDTH/2, HEIGHT/2 + 10, "CS61B");
                    StdDraw.text(WIDTH/2, HEIGHT/2, "Press L to load a game!(if you save.)");
                    StdDraw.text(WIDTH/2, HEIGHT/2 - 10, "Type N###S to create a new world!");
                    StdDraw.show();
                }
            }
        }
    }

    public void save(){
        if (this.userInput.equals(":q")|| this.userInput.equals(";Q") || this.userInput == null){
            return;
        }
        this.userInput = this.userInput.substring(0,this.userInput.length()-2);
        File saveFile = join(CWD,"saveworld.txt");
        if (saveFile.exists()) {
            writeObject(saveFile, this);
        }else {
            try{
                saveFile.createNewFile();
            }catch (IOException e){

            }
            writeObject(saveFile,this);
        }
    }

    public void load(){
        File saveFile = join(CWD,"saveworld.txt");
        if (!saveFile.exists()){
            return;
        }
        Engine a = readObject(saveFile,Engine.class);

        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);

        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.WHITE);
        interactWithInputString(a.userInput);
        this.ter = a.ter;
        this.userInput = a.userInput;
        this.avatar_y = a.avatar_y;
        this.avatar_x = a.avatar_x;
        this.finalWorldFrame = a.finalWorldFrame;
        this.StartGame();
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and . If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.
        input = input.toUpperCase();
        File saveFile = join(CWD,"saveworld.txt");
        if (input.contains("L") && saveFile.exists()){
            load();
            generateUser();
            boolean flag = false;
            for (int i = input.indexOf("S") + 1; i < input.length();i++){
                char s = input.charAt(i);
                if (flag){
                    if (s =='Q' || s == 'q'){
                        this.save();
                    }
                }
                if (s == ':'){
                    flag = true;
                    continue;
                }
                flag = false;
                switch (s){
                    case 'W':
                        judge(avatar_x, avatar_y + 1);
                        break;
                    case 'A':
                        judge(avatar_x - 1, avatar_y);
                        break;
                    case 'S':
                        judge(avatar_x, avatar_y - 1);
                        break;
                    case 'D':
                        judge(avatar_x + 1, avatar_y);
                        break;
//                    case 'L':
//                        load();
//                        break;
                }
            }
            return this.finalWorldFrame;
        }
        if (!input.contains("N") || !input.contains("S")){
            return null;
        }
        final long SEED = analyse(input);
        ter.initialize(WIDTH, HEIGHT);
        this.finalWorldFrame = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                this.finalWorldFrame[x][y] = Tileset.NOTHING;
            }
        }
        this.rand = new Random(SEED);
        int roomNumber = rand.nextInt(20) + 10;
        Room[] room = new Room[roomNumber];
        generateRoom(room, SEED);
        // get room
        sortRoom(room);
        eliminateRoom(room);
        buildRoom(room,finalWorldFrame);
        connectRoom(room,finalWorldFrame);

        ter.renderFrame(finalWorldFrame);
        return this.finalWorldFrame;
    }

    private long analyse(String input){
        return input.substring(input.indexOf("N"), input.indexOf("S")).hashCode();
    }

    private void generateRoom(Room[] room, long SEED){
        for (int i = 0; i < room.length; i++) {
            room[i] = new Room(SEED);
            SEED = SEED / 10;
        }
    }

    public void sortRoom(Room[] room){
        for (int i = 0; i < room.length; i++) {
            for (int j = i; j < room.length; j++) {
                if (room[i].get_x() > room[j].get_x()){
                    Room tmp = room[i];
                    room[i] = room[j];
                    room[j] = tmp;
                }
            }
        }
        // an ineffective way to sort, determined by x
    }

    public void eliminateRoom(Room[] room){
        Room prev = room[0];
        for (int i = 1; i < room.length; i++) {
            if (prev.positionConflict(room[i])){
                room[i] = null;
            } else {
                prev = room[i];
            }
        }
    }

    public void buildRoom(Room[] room,TETile[][] world) {
        for (int i = 0; i < room.length; i++){
            if (room[i] == null) {
                continue;
            } else {
                room[i].paveRoom(world);
            }
        }
    }

    public void connectRoom(Room[] room,TETile[][] world) {
        Room prev = null;
        for (int i = room.length - 1; i >= 0; i--){
            if (room[i] == null) {
                continue;
            } else {
                if (prev != null){
                    room[i].buildRoad(prev, world);
                }
                prev = room[i];
            }
        }
    }

    private class Room{
        int start_x;
        int start_y;
        int end_x;
        int end_y;
        public Room(long SEED){
            int start_x = new Random(SEED/12).nextInt(WIDTH - 10) + 2;
            int start_y = new Random(SEED/13).nextInt(HEIGHT - 10) + 2;
            int end_x = new Random(SEED/14).nextInt(4) + start_x + 3;
            int end_y = new Random(SEED/15).nextInt(4) + start_y + 3;

            this.start_x = start_x;
            this.start_y = start_y;
            this.end_x = end_x;
            this.end_y = end_y;
        }

        private int get_x(){
            return this.start_x;
        }
        private int get_y(){
            return this.start_y;
        }

        private int getEnd_x(){
            return this.end_x;
        }

        private int getEnd_y(){
            return this.end_y;
        }

        public boolean positionConflict(Room other){
//            if (this.end_x > other.get_x() && this.end_y > this.get_y()){
//                return true;
//            }
//            return false;
            if(this.end_x + 2 > other.get_x() && this.start_x - 2 < other.get_x() &&
                            this.start_y - 2 < other.getEnd_y() && this.end_y + 2 > other.getEnd_y()){
                return true;
            }
//            if (this.end_y + 2 > this.get_y() && this.start_y - 2 < other.get_y()){
//                return true;
//            }
            return false;
        }

        public void paveRoom(TETile[][] world){
            for (int i = this.start_x; i <= this.end_x; i++){
                for (int j = this.start_y; j <= this.end_y; j++){
                    world[i][j] = Tileset.FLOOR;
                }
            }
            for (int i = this.start_x - 1; i <= this.end_x + 1; i++){
                world[i][start_y - 1] = Tileset.WALL;
            }
            for (int i = this.start_x - 1; i <= this.end_x + 1; i++){
                world[i][end_y + 1] = Tileset.WALL;
            }
            for (int i = this.start_y - 1; i <= this.end_y + 1; i++){
                world[start_x - 1][i] = Tileset.WALL;
            }
            for (int i = this.start_y - 1; i <= this.end_y + 1; i++){
                world[end_x + 1][i] = Tileset.WALL;
            }

        }

        public void buildRoad(Room room, TETile[][] world){
            int x = this.get_x();
            int y = this.get_y();
            while (x <= room.get_x() + 1) {
                paveRoad(x, y, world);
                x += 1;
            }
            if(world[x][y] != Tileset.FLOOR){
                world[x][y] = Tileset.WALL;
                world[x][y + 1] = Tileset.WALL;
                world[x][y - 1] = Tileset.WALL;
            }
            x -= 2;
            if (y > room.get_y()){
                while (y >= room.get_y()){
                    paveRoad_y(x,y,world);
                    y -= 1;
                }
            } else {
                while (y <= room.get_y()) {
                    paveRoad_y(x,y,world);
                    y += 1;
                }
            }

        }

        public void paveRoad(int x, int y, TETile[][] world) {
            if (world[x][y] == Tileset.WALL) {
                world[x][y] = Tileset.FLOOR;
                world[x][y + 1] =Tileset.WALL;
                world[x][y - 1] =Tileset.WALL;
            } else if (world[x][y] == Tileset.NOTHING){
                world[x][y] = Tileset.FLOOR;
                world[x][y + 1] =Tileset.WALL;
                world[x][y - 1] =Tileset.WALL;
            }
        }

        public void paveRoad_y(int x, int y, TETile[][] world) {
            if (world[x][y] == Tileset.WALL) {
                world[x][y] = Tileset.FLOOR;
                world[x + 1][y] =Tileset.WALL;
                world[x - 1][y] =Tileset.WALL;
            } else if (world[x][y] == Tileset.NOTHING){
                world[x][y] = Tileset.FLOOR;
                world[x + 1][y] =Tileset.WALL;
                world[x - 1][y] =Tileset.WALL;
            }
        }
    }
}
