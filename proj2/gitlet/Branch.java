package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Repository.COMMIT;
import static gitlet.Utils.*;

public class Branch implements Serializable {
    private String pointer;
    private String branchName;

    public Branch(String currentCommit,String branchName){
        this.pointer = currentCommit;
        this.branchName = branchName;
    }

    public String getPointer(){
        return this.pointer;
    }

    public void save(){
        File saveFile = join(Repository.GITLET_DIR,"REF",this.branchName);
        writeObject(saveFile,this);
        writeObject(Repository.HEAD,this);
    }

    public Commit getCommit(){
        Commit currentCommit = readObject(join(COMMIT,this.pointer), Commit.class);
        return currentCommit;
    }
    public String getBranchName(){return this.branchName;}
    public void changePointer(String CommitID){
        this.pointer = CommitID;
        this.save();
    }

}
