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
        // Head is a branch and its name is current branch name so
        // we just read it when it save it would save in that branch
    }

    public String currentCommitID(){
        return this.pointer;
    }

    public void save(){
        File saveFile = join(Repository.GITLET_DIR,"REF",this.branchName);
        writeObject(saveFile,this);
        writeObject(Repository.HEAD,this);
    }

    public void changeBranchName(String branchName){
        // overwrite the branch name
        this.branchName = branchName;
        writeObject(Repository.HEAD,this);
    }

    public void changeCommitID(String commitID){
        // overwrite the branch name
        this.pointer = commitID;
        File saveFile = join(Repository.GITLET_DIR,"REF",branchName);
        writeObject(saveFile,this);
        writeObject(Repository.HEAD,this);
    }
    public void changeCommitIDalone(String commitID){
        // overwrite the branch name
        this.pointer = commitID;
        File saveFile = join(Repository.GITLET_DIR,"REF",branchName);
        writeObject(saveFile,this);
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
