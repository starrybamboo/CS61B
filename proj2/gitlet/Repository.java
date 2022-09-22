package gitlet;

import java.io.File;
import java.util.TreeMap;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");


    static void init(){
        if (!GITLET_DIR.exists()){
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }else{
            GITLET_DIR.mkdir();
            buildFolder();
            buildInitialThing();
        }
    }

    static void buildFolder(){
        join(GITLET_DIR,"OBJECT").mkdir();
        join(GITLET_DIR,"OBJECT","Blobs").mkdir();
        join(GITLET_DIR, "OBJECT","Commit").mkdir();
        join(GITLET_DIR,"Ref").mkdir();
        join(GITLET_DIR,"Stage").mkdir();
    }

    static void buildInitialThing(){
        Commit sentinel = new Commit("initial commit",null,null);
        writeContents(join(GITLET_DIR,"OBJECT","sentinel"),sentinel);
        Branch master = new Branch(sentinel,"master");
        writeContents(join(GITLET_DIR,"Ref","HEAD"),master);
        writeContents(join(GITLET_DIR,"Ref","HEAD"),master);
        writeContents(join(GITLET_DIR,"Stage","MAPad"),null);
        writeContents(join(GITLET_DIR,"Stage","MAPrm"),null);
    }

     static void add(String fileName){
//        File tmp = join(CWD,fileName);
//        if (!tmp.exists()){
//            System.out.println("File does not exist.");
//        }else{
//            Stage.checkAdd(tmp, fileName);
//        }
    }

    static void commit(String msg){
        Branch HEAD = readObject(join(GITLET_DIR,"Ref","HEAD"),Branch.class);
        TreeMap parentBlobIDMap = readObject(join(GITLET_DIR,"Stage","MAPad"),TreeMap.class);
        new Commit(msg, HEAD.getPointer(), parentBlobIDMap);
    }

    public void rm(){

    }

    public void log(){

    }

//    public void global-log(){
//
//    }

    public void find(){

    }

    public void status(){};

    public void checkout(){}

    public void branch(){}

//    public void rm-branch(){}

    public void reset(){
    }

    public void merge(){}

    /* TODO: fill in the rest of this class. */
}
