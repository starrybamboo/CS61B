package capers;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import static capers.Utils.*;

/** A repository for Capers
 * @author
 * The structure of a Capers Repository is as follows:
 *
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 *    - dogs/ -- folder containing all of the persistent data for dogs
 *    - story -- file containing the current story
 *
 * TODO: change the above structure if you do something different.
 */
public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** Main metadata folder. */
    static final File CAPERS_FOLDER = join(".capers");

        //TODO Hint: look at the `join`
        // function in Utils

    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     */
    public static void setupPersistence() {
        File story = Utils.join(CAPERS_FOLDER,"story");
        try{
            if (CAPERS_FOLDER.exists() == false) {
                CAPERS_FOLDER.mkdir();
            }
            if (!Dog.DOG_FOLDER.exists()){
                Dog.DOG_FOLDER.mkdir();
            }
            if (!story.exists()){
                story.createNewFile();
            }
        }catch (IOException excp){
            System.out.println(excp);
        }
    }


    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        File story = Utils.join(CAPERS_FOLDER, "story");
        String tmp =readContentsAsString(story) + text + "\n";
        writeContents(story, tmp);
        System.out.println(tmp);
    }
//    public static void writeStory(String text) {
//        // TODO
//        File story = Utils.join(CAPERS_FOLDER, "story");
//        String oldContent = readContentsAsString(story);
//        String newContent = oldContent + text + "\n";
//        Utils.writeContents(story, newContent);
//        System.out.println(newContent);
//    }


    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        // TODO
        Dog tmp = new Dog(name, breed, age);
        tmp.saveDog();
        System.out.println(tmp.toString());
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        Dog dog = Dog.fromFile(name);
        dog.haveBirthday();
        dog.saveDog();
    }
}
