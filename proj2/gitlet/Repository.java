package gitlet;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;
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
    public static final File COMMIT = join(GITLET_DIR,"OBJECT","COMMIT");
    public static final File BLOBS = join(GITLET_DIR,"OBJECT","BLOBS");
    public static final File HEAD = join(GITLET_DIR,"REF","HEAD");
    public static final File STAGE = join(GITLET_DIR,"STAGE");
    public static final File REF = join(GITLET_DIR,"REF");
    public static final File STAGEAD = join(GITLET_DIR,"STAGE","STAGEAD");
    public static final File STAGERM = join(GITLET_DIR,"STAGE","STAGERM");


    static void init(){
        if (GITLET_DIR.exists()){
            message("A Gitlet version-control system already exists in the current directory.");
        }else{
            GITLET_DIR.mkdir();
            COMMIT.mkdirs();
            BLOBS.mkdirs();
            REF.mkdirs();
            STAGE.mkdirs();
            initialize();
        }
    }

    static void initialize(){
        Commit initial = new Commit();
        new Stage("STAGEAD").save();
        new Stage("STAGERM").save();
        new Branch(initial.getID(),"HEAD").save();
        new Branch(initial.getID(),"master").save();
    }

    static void add(String fileName){
        //initial
        Blobs currentFile = new Blobs(fileName,readContents(join(CWD,fileName)));
        Stage MAPad = readObject(STAGEAD,Stage.class);
        Stage MAPrm = readObject(STAGERM,Stage.class);
        MAPad.addElement(fileName,currentFile.getVersion());
        MAPrm.removeElement(fileName);
    }

    static void commit(String msg){
        Stage MAPad = readObject(STAGEAD,Stage.class);
        Stage MAPrm = readObject(STAGERM,Stage.class);
        Branch Head = readObject(HEAD,Branch.class);
        Commit parents = readObject(join(COMMIT,Head.getPointer()),Commit.class);
        parents.updateMap(MAPad.MAP,MAPrm.MAP);

        Commit newCommit = new Commit(msg,parents.getID(),parents.getBlobIDMap());

        newCommit.save();
        Head.changePointer(newCommit.getID());
        MAPrm.clear();
        MAPad.clear();
    }

    public static void checkout(String FileName){
        //such things should be packed
        Branch Head = readObject(HEAD,Branch.class);
        Commit currentCommit = readObject(join(COMMIT,Head.getPointer()),Commit.class);
        TreeMap<String,String> map = currentCommit.getBlobIDMap();
        //should be packed above.
        String a = map.get(FileName);
        if (a == null){return;}
        Blobs file = readObject(join(BLOBS,a),Blobs.class);
        writeContents(join(CWD,FileName),file.getPasscode());
    }

    public static void checkout(String CommitID,String FileName){
        Commit currentCommit = readObject(join(COMMIT,CommitID),Commit.class);
        TreeMap<String,String> map = currentCommit.getBlobIDMap();
        Blobs file = readObject(join(BLOBS, map.get(FileName)),Blobs.class);
        writeContents(join(CWD,file.getFileName()),file.getPasscode());
    }

    public static void log() {
        Branch Head = readObject(HEAD, Branch.class);
        Commit currentCommit = readObject(join(COMMIT, Head.getPointer()), Commit.class);

        while (currentCommit != null) {
            System.out.println("===");
            System.out.println("commit " + currentCommit.getID());
            System.out.println("Date: " + currentCommit.getDate());
            System.out.println(currentCommit.getMessage());
            System.out.println();
            if (currentCommit.getParent() == null) {
                break;
            }
            currentCommit = readObject(join(COMMIT, currentCommit.getParent()), Commit.class);
        }
    }
        //===
        //commit a0da1ea5a15ab613bf9961fd86f010cf74c7ee48
        //Date: Thu Nov 9 20:00:05 2017 -0800
        //A commit message.
        //the result of printout


    //    static void remove(String FileName){
//        //remove addStage
//        Stage MAPad = readObject(STAGEAD,Stage.class);
//        MAPad.removeElement(FileName);
//        //put in removeStage
//        Branch Head = readObject(HEAD,Branch.class);
//        Commit currentFile = Head.getCommit();
//        Stage MAPrm = readObject(STAGERM,Stage.class);
//        MAPrm.addElement(FileName,(String) currentFile.getBlobIDMap().get(FileName));
//        //remove working directory file
//        File deleteFile = join(CWD,FileName);
//        if (deleteFile.exists()) {
//            Utils.restrictedDelete(deleteFile);
//        }
//    }



    public void rm(){

    }



    public void find(){

    }

    public void status(){};



    public void branch(){}

//    public void rm-branch(){}

    public void reset(){
    }

    public void merge(){}

    /* TODO: fill in the rest of this class. */
}
