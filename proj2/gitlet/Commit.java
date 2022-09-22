package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.TreeMap;

import static gitlet.Repository.GITLET_DIR;
import static gitlet.Repository.add;
import static gitlet.Utils.*;


/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable,IOinterface{
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    private Date timestamp;
    public String parent1;
    public String parent2;
    private String CommitID;
    private TreeMap BlobIDMap;
    public Commit  (String msg, Commit parent, TreeMap parentBlobIDMap){
        if (parent == null) {
            this.timestamp = new Date(0);
        }else{
            this.timestamp = new Date();
        }
        this.message = msg;
        this.parent1 = parent.CommitID;
        this.parent2 = null;
        this.helpBuildBlob(parentBlobIDMap);
        this.helpBuild();
    }

    public void helpBuild(){
        this.CommitID =sha1(this);
        File CommitFile = join(GITLET_DIR,"OBJECT","Commit", this.CommitID);
        writeContents(CommitFile,this);
    }

    public void helpBuildBlob(TreeMap<String,String> parentBlobIDMap){
        if (parentBlobIDMap != null){
            this.BlobIDMap =  parentBlobIDMap;
            //结合暂存区的add与remove，操作
            TreeMap addStage = Stage.getAddStage();
            for (Object file :addStage.keySet()){
                if (this.BlobIDMap.containsKey(file)){
                    Object addVersion = addStage.get(file);
                    if (this.BlobIDMap.get(file).equals(addVersion)){
                        ;
                    }else{
                        this.BlobIDMap.remove(file);
                        this.BlobIDMap.put(file,addVersion);
                    }
                }
            }
            Stage.clearAD();
            //处理remove缓冲区
            TreeMap rmStage = Stage.getRemoveStage();
            rmStage.keySet();
            for (Object file :rmStage.keySet()){
                if (this.BlobIDMap.containsKey(file)){
                    Object rmVersion = rmStage.get(file);
                    if (this.BlobIDMap.get(file).equals(rmVersion)){
                        this.BlobIDMap.remove(file);
                    }
                }
            }
            Stage.clearRM();
        }
    }



//    static void add(String msg){
//        Branch HeadPointer = readObject(join(GITLET_DIR,"HeadPointer"),Branch.class);
//        Commit tmp = new Commit(msg, HeadPointer.getPointer());
//        writeContents(join(GITLET_DIR,"HeadPointer"),HeadPointer);
//    }


    @Override
    public int attribute() {
        return 1;
    }

    /* TODO: fill in the rest of this class. */
}
