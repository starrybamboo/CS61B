package byow.Core;

public class Main {
    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.interactWithInputString(args[0]);
        System.out.println(engine.toString());
    }

}
