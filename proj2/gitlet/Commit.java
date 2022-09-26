package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static gitlet.Repository.COMMIT;
import static gitlet.Repository.GITLET_DIR;
import static gitlet.Utils.*;


/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /**
     * The message of this Commit.
     */
    private String message;
    private Date timestamp;
    public ArrayList<String> parent;
    private String CommitID;
    private TreeMap<String,String> BlobIDMap;

    public Commit() {
        this.timestamp = new Date(0);
        this.message = "initial commit";
        this.parent = new ArrayList<>(2);
        this.BlobIDMap = new TreeMap<String,String>();
        this.CommitID = sha1(dateToTimeStamp(timestamp),message);
        File saveFile = join(COMMIT, this.CommitID);
        writeObject(saveFile,this);
    }
    //parents prev commit node
    public Commit(String msg,String parents, TreeMap parentBlobIDMap){
        this.timestamp = new Date();
        this.message = msg;
        this.parent = new ArrayList(2);
        this.parent.add(parents);
        this.BlobIDMap = parentBlobIDMap;
        this.CommitID = generateID();
    }


    private static String dateToTimeStamp(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.US);
        return dateFormat.format(date);
    }

    // it not only could use in MAPad and MAPrm but also could make it in conflict
    public void updateMap(TreeMap<String,String> MAPad,TreeMap<String,String> MAPrm){
        for(String x: MAPad.keySet() ){
            // if there is no file add
            if (this.BlobIDMap.get(x) == null){
                this.BlobIDMap.put(x, MAPad.get(x));
                continue;
            }
            // if version is not eaqula change
            if (!this.BlobIDMap.get(x).equals(MAPad.get(x))) {
                this.BlobIDMap.remove(x);
                this.BlobIDMap.put(x, MAPad.get(x));
            }
        }
        for(String x: MAPrm.keySet() ){
            //if removeStage's remove map has something to remove ,remove.
            if (this.BlobIDMap.get(x) != null){
                this.BlobIDMap.remove(x);
                continue;
            }
//            if (!this.BlobIDMap.get(x).equals(MAPrm.get(x))){
//                this.BlobIDMap.remove(x);
//            }
        }
    }

//    public TreeMap<String,String> getBoldID(){
//        Commit currentCommit = readObject(join(COMMIT,Head.currentCommit()),Commit.class);
//        TreeMap<String,String> map = currentCommit.getBlobIDMap();
//    }

    public void addParent(String parentName){
        this.parent.add(parentName);
    }



    public void logPrint(){
        System.out.println("===");
        System.out.println("commit " + this.getID());
        if (this.parent.size() == 2 && this.parent.get(0) != null && this.parent.get(1) != null){
            System.out.println("Merge: " + this.parent.get(0).substring(0,7) + " " + this.parent.get(1).substring(0,7));
        }
        System.out.println("Date: " + this.getDate());
        System.out.println(this.getMessage());
        System.out.println();
    }


    public void save() {
        File saveFile = join(COMMIT, this.CommitID);
        writeObject(saveFile,this);
    }


    public String getID(){
        return this.CommitID;
    }
    public TreeMap<String,String> getBlobIDMap(){
        return this.BlobIDMap;
    }
    private String generateID() {
        return Utils.sha1(dateToTimeStamp(timestamp), message, parent.toString(), BlobIDMap.toString());
    }
    public String getParent(){
        if (this.parent.isEmpty()){return null;}
        return this.parent.get(0);
    }
    public String getDate(){
        return dateToTimeStamp(this.timestamp);
    }

    public String getMessage(){
        return this.message;
    }

    public ArrayList<String> getParentList(){
        return this.parent;
    }
    public void removeParent(){
        this.parent.remove(0);
    }

    public void mergeParent(String parent1 ,String parent2){
        this.parent.remove(0);
        this.parent.add(parent1);
        this.parent.add(parent2);
        this.save();
    }

}

//
//    public void helpBuild(){
//        this.CommitID =sha1(this);
//        File CommitFile = join(GITLET_DIR,"OBJECT","Commit", this.CommitID);
//        writeContents(CommitFile,this);

//    public void helpBuildBlob(TreeMap<String,String> parentBlobIDMap){
//        if (parentBlobIDMap != null){
//            this.BlobIDMap =  parentBlobIDMap;
//            TreeMap addStage = Stage.getAddStage();
//            for (Object file :addStage.keySet()){
//                if (this.BlobIDMap.containsKey(file)){
//                    Object addVersion = addStage.get(file);
//                    if (this.BlobIDMap.get(file).equals(addVersion)){
//                        ;
//                    }else{
//                        this.BlobIDMap.remove(file);
//                        this.BlobIDMap.put(file,addVersion);
//                    }
//                }
//            }
//            Stage.clearAD();
//            TreeMap rmStage = Stage.getRemoveStage();
//            rmStage.keySet();
//            for (Object file :rmStage.keySet()){
//                if (this.BlobIDMap.containsKey(file)){
//                    Object rmVersion = rmStage.get(file);
//                    if (this.BlobIDMap.get(file).equals(rmVersion)){
//                        this.BlobIDMap.remove(file);
//                    }
//                }
//            }
//            Stage.clearRM();
//        }
//    }



//    static void add(String msg){
//        Branch HeadPointer = readObject(join(GITLET_DIR,"HeadPointer"),Branch.class);
//        Commit tmp = new Commit(msg, HeadPointer.getPointer());
//        writeContents(join(GITLET_DIR,"HeadPointer"),HeadPointer);
//    }


