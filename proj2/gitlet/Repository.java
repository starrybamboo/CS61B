package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
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
    public Stage MAP;
    private Branch HeadPointer;


    static void init(){
        if (!GITLET_DIR.exists()){
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }else{
            GITLET_DIR.mkdir();
            buildFolder();
            Commit sentinel = new Commit("initial commit",null);
            Branch master = new Branch(sentinel,"master");
            writeObject(join(GITLET_DIR,"HeadPointer"),master);
            Stage MAP = new Stage();
        }
    }

    static void buildFolder(){
        File tmp1 = join(GITLET_DIR,"StageRemove");
        tmp1.mkdir();
        File tmp2 = join(GITLET_DIR,"StageAdd");
        tmp2.mkdir();
        File tmp3 = join(GITLET_DIR,"Blobs");
        tmp3.mkdir();
        File tmp4 = join(GITLET_DIR,"Commit");
        tmp4.mkdir();
        File tmp5 = join(GITLET_DIR,"Repo");
        tmp5.mkdir();
    }

     static void add(String fileName){
        File tmp = join(CWD,fileName);
        if (!tmp.exists()){
            System.out.println("File does not exist.");
        }else{
            Stage.checkAdd(tmp, fileName);
        }
    }

    static void commit(String msg){
        Commit.add(msg);
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
