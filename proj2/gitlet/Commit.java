package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.List;

import static gitlet.Repository.GITLET_DIR;
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
    private Commit parent1;
    private Commit parent2;
    private String name;
    private List<Blobs> BlobArray;
    public Commit  (String msg, Commit parent){
        if (parent == null) {
            this.timestamp = new Date(0);
        }else{
            this.timestamp = new Date();
        }
        this.message = msg;
        this.parent1 = parent;
        this.parent2 = null;
        String x = sha1(this);
        this.name = x;
        File f = join(GITLET_DIR,"Commit",x);
        for (Object m : Stage.AdditionMap().keySet()){
            Object w = Stage.searchAdd((String) m);
            File tmp = join(GITLET_DIR,"Addition",(String) w);
            Blobs addBlob =new Blobs((String) w,tmp);
            this.BlobArray.add(addBlob);
        }
        Stage.clearAdd();
        writeContents(f,this);
    }

    static void add(String msg){
        Branch HeadPointer = readObject(join(GITLET_DIR,"HeadPointer"),Branch.class);
        Commit tmp = new Commit(msg, HeadPointer.get());
        writeContents(join(GITLET_DIR,"HeadPointer"),HeadPointer);
    }



    public void addBlobs(Blobs tmp){
        BlobArray.add(tmp);
    }

    @Override
    public int attribute() {
        return 1;
    }

    /* TODO: fill in the rest of this class. */
}
