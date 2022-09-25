package gitlet;

import java.io.File;
import java.util.List;
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
            initialize();
        }
    }
    static void initialize(){
        GITLET_DIR.mkdir();
        COMMIT.mkdirs();
        BLOBS.mkdirs();
        REF.mkdirs();
        STAGE.mkdirs();
        Commit initial = new Commit();
        new Stage("STAGEAD").save();
        new Stage("STAGERM").save();
        new Branch(initial.getID(),"HEAD").save();
        new Branch(initial.getID(),"master").save();
    }

    static void add(String fileName){
        if (!join(CWD,fileName).exists()){
            exitWithMessage("File does not exist.");
            return;
        }
        //Remove it from removeStage
        Blobs currentFile = new Blobs(fileName,readContents(join(CWD,fileName)));
        Stage removeStage = readObject(STAGERM,Stage.class);
        if (removeStage.containsKey(fileName)){
            removeStage.removeElement(fileName);
            return;
        }
        //exit if commit is same as it.
        Branch Head = readObject(HEAD,Branch.class);
        Commit parents = Head.getCommit();

        if (parents.getBlobIDMap().containsKey(fileName)){
            if (parents.getBlobIDMap().get(fileName).equals(currentFile.getVersion())){
                return;
            }
        }
        //read file
        Stage addStage = readObject(STAGEAD,Stage.class);
        addStage.addElement(fileName,currentFile.getVersion());
        //add it to the addStage
    }

    static void commit(String msg){
        Branch Head = readObject(HEAD,Branch.class);
        Commit parents = Head.getCommit();
        //get parent commit
        Stage addStage = readObject(STAGEAD,Stage.class);
        Stage removeStage = readObject(STAGERM,Stage.class);
        if (addStage.MAP.isEmpty() && removeStage.MAP.isEmpty()){
            exitWithMessage("No changes added to the commit.");
        }
        parents.updateMap(addStage.MAP,removeStage.MAP);
        Commit newCommit = new Commit(msg,parents.getID(),parents.getBlobIDMap());
        //renew parent blobIDMap with stage thing and then it become the current commit BlobIDMap.
        // (it would not change the parent commit)
        //the commit is immutable
        newCommit.save();
        Head.changePointer(newCommit.getID());
        //save it and change the Head
        removeStage.clear();
        addStage.clear();
        //clean the stage
    }

    public static void checkout(String FileName){
        //such things should be packed
        //read head and then read the current commit
        Branch Head = readObject(HEAD,Branch.class);
        Commit currentCommit = Head.getCommit();
        TreeMap<String,String> map = currentCommit.getBlobIDMap();
        if (map.isEmpty() || !map.containsKey(FileName)){
            exitWithMessage("File does not exist in that commit.");
            return;
        }
        //should be packed above.
        Blobs file = readObject(join(BLOBS,map.get(FileName)),Blobs.class);
        writeContents(join(CWD,FileName),file.getPasscode());
    }

    public static void checkout(String CommitID,String FileName){
        //read the specific commitID get the commit
        if (!join(COMMIT,CommitID).exists()){
            exitWithMessage("No commit with that id exists.");
            return;
        }
        Commit currentCommit = readObject(join(COMMIT,CommitID),Commit.class);
        TreeMap<String,String> map = currentCommit.getBlobIDMap();
//        if (map == null ||map.isEmpty() || !join(BLOBS, map.get(FileName)).exists()){
//            exitWithMessage("File does not exist in that commit.");
//            return;
//        }
        if (map.isEmpty() || !map.containsKey(FileName)){
            exitWithMessage("File does not exist in that commit.");
            return;
        }
        Blobs file = readObject(join(BLOBS, map.get(FileName)),Blobs.class);
        writeContents(join(CWD,FileName),file.getPasscode());
    }

    public static void checkoutBranch(String branchName){
        // read Head and next branch and their commit.
        if (!join(REF,branchName).exists()){exitWithMessage("No such branch exists.");return;}

        Branch Head = readObject(HEAD, Branch.class);
        Branch changeBranch = readObject(join(REF,branchName), Branch.class);
        if(Head.getBranchName().equals(branchName) &&
                Head.currentCommitID().equals(changeBranch.currentCommitID())){
            exitWithMessage("No need to checkout the current branch.");
            return;
        }
        Commit currentCommit = Head.getCommit();
        Commit nextCommit = changeBranch.getCommit();

        // read the file name and judge if there is an untracked file
        List<String> workingDirectoryFileName = plainFilenamesIn(CWD);

        TreeMap<String,String> oldFileMap = currentCommit.getBlobIDMap();
        TreeMap<String,String> addFileMap = nextCommit.getBlobIDMap();
        //delete the file untracked by next commit

        for (String file :workingDirectoryFileName){
            if (!oldFileMap.containsKey(file)){
                exitWithMessage("There is an untracked file in the way; delete it, or add and commit it first.");
            }
//            if (oldFileMap.get(file) != null && addFileMap.get(file) != null){
//                String oldBlobID = oldFileMap.get(file);
//                String newBlobID = addFileMap.get(file);
//                if (!oldBlobID.equals(newBlobID)){
//                    exitWithMessage("There is an untracked file in the way; delete it, or add and commit it first.");
//                }
//            }
        }
        //renew the file that not match next commit
        for (String file : workingDirectoryFileName ){
            if (!addFileMap.keySet().contains(file)){
                File deleteFile = join(CWD,file);
                restrictedDelete(deleteFile);
            }
        }

        // write file to it.
        for (String file : addFileMap.keySet()){
            // read the Blob name in branch that change to and compare it with prev branch
            String BlobVersion = addFileMap.get(file);
            if (oldFileMap.containsKey(file)){
                if(BlobVersion.equals(oldFileMap.get(file))){
                    // if same ignore
                    continue;
                }else{
                    // if not same overwrite
                    Blobs writeFile = readObject(join(BLOBS,BlobVersion),Blobs.class);
                    writeContents(join(CWD,writeFile.getFileName()),writeFile.getPasscode());
                }
            }else{
                // if not contain just write
                Blobs writeFile = readObject(join(BLOBS,BlobVersion),Blobs.class);
                writeContents(join(CWD,writeFile.getFileName()),writeFile.getPasscode());
            }
        }
        changeBranch.save();
    }


    public static void log() {
        // read the current branch and then get the point commit.
        Branch Head = readObject(HEAD, Branch.class);
        Commit currentCommit = readObject(join(COMMIT, Head.currentCommitID()), Commit.class);
        while (currentCommit != null) {
            //print out the information
            currentCommit.logPrint();
            if (currentCommit.getParent() == null) {
                break;
            }
            currentCommit = readObject(join(COMMIT, currentCommit.getParent()), Commit.class);
        }
        //===
        //commit a0da1ea5a15ab613bf9961fd86f010cf74c7ee48
        //Date: Thu Nov 9 20:00:05 2017 -0800
        //A commit message.
        //the result of printout
    }



    static void remove(String FileName){
        //read the current
        Branch Head = readObject(HEAD,Branch.class);
        Commit currentCommit = Head.getCommit();
        //remove the file if in addStage
        Stage addStage = readObject(STAGEAD,Stage.class);
        if (!addStage.MAP.containsKey(FileName) && !currentCommit.getBlobIDMap().containsKey(FileName)){
            exitWithMessage("No reason to remove the file.");
        }
        if (addStage.MAP.containsKey(FileName)) {
            addStage.removeElement(FileName);
            return;
        }
        Stage removeStage = readObject(STAGERM, Stage.class);
        removeStage.addElement(FileName,currentCommit.getBlobIDMap().get(FileName));
        //remove working directory file
        File deleteFile = join(CWD,FileName);
        if (deleteFile.exists()) {
            Utils.restrictedDelete(deleteFile);
        }
    }

    static void globalLog(){
        //read every Commit in commit directory
        List<String> commitList = Utils.plainFilenamesIn(COMMIT);
        for (String commitName : commitList){
            //iterate it.
            Commit currentCommit = readObject(join(COMMIT,commitName), Commit.class);
            currentCommit.logPrint();
        }
    }

    static void find(String message){
        //read every Commit in commit directory
        boolean flag = false;
        List<String> commitList = Utils.plainFilenamesIn(COMMIT);
        for (String commitName : commitList) {
            //iterate it.
            Commit currentCommit = readObject(join(COMMIT, commitName), Commit.class);
            if (currentCommit.getMessage().equals(message)) {
                System.out.println(currentCommit.getID());
                flag = true;
            }
        }
        if (flag){
            System.out.println();
        }else{
            System.out.println("Found no commit with that message.");
        }
    }

    static void status(){
        // first part print the commit
        List<String> branchList = plainFilenamesIn(REF);
        System.out.println("=== Branches ===");
        Branch Head = readObject(join(HEAD),Branch.class);
        System.out.println("*" + Head.getBranchName());
        for (String branchName : branchList){
            //Head point branch we stayed in, but itself is not a true branch so
            //we should avoid print again.
            Branch currentBranch = readObject(join(REF,branchName), Branch.class);
            if (currentBranch.getBranchName().equals(Head.getBranchName())) {
                continue;
            }
            System.out.println(currentBranch.getBranchName());
        }
        System.out.println();
        // next part print the addStage
        System.out.println("=== Staged Files ===");
        Stage addStage = readObject(STAGEAD,Stage.class);
        for (String fileName : addStage.MAP.keySet()){
            System.out.println(fileName);
        }
        System.out.println();
        //next part print the removeStage
        System.out.println("=== Removed Files ===");
        Stage removeStage = readObject(STAGERM,Stage.class);
        for (String fileName : removeStage.MAP.keySet()){
            System.out.println(fileName);
        }
        System.out.println();
        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println();
        System.out.println("=== Untracked Files ===");
        System.out.println();
    }

    public static void branch(String branchName){
        //get the head
        if (join(REF,branchName).exists()){exitWithMessage("A branch with that name already exists.");}
        Branch Head = readObject(HEAD,Branch.class);
        String headBranch = Head.getBranchName();
        Commit currentCommit = Head.getCommit();
        new Branch(currentCommit.getID(),branchName).save();
        //save would chang the Head point branch so we should change it back
        Head.changeBranchName(headBranch);
        //this change would save.
    }

    public static void rmBranch(String branchName){
        File deleteBranch = join(REF,branchName);
        if (deleteBranch.exists()) {
            Branch Head = readObject(HEAD,Branch.class);
            String headBranch = Head.getBranchName();
            if (headBranch.equals(branchName)){
                exitWithMessage("Cannot remove the current branch.");
            }else{
                deleteBranch.delete();
            }
        }else{
            exitWithMessage("A branch with that name does not exist.");
        }
    }

    public static void reset(String commitID){
        //judge if there is a commit.
        if (join(COMMIT,commitID).exists()) {
            //git the commit and change the head
            Commit pointCommit = readObject(join(COMMIT, commitID), Commit.class);
            Branch Head = readObject(HEAD, Branch.class);
            Head.changeCommitIDalone(commitID);
            // the change would not save so though we read it again it doesn't matter
            Repository.checkoutBranch(Head.getBranchName());

            Head.changeCommitIDalone(commitID);
            Stage addStage = readObject(STAGEAD,Stage.class);
            Stage removeStage = readObject(STAGERM,Stage.class);
            removeStage.clear();
            addStage.clear();
        }else {
            exitWithMessage("No commit with that id exists.");
        }
    }

    static void merge(String mergeBranchName){
        //TODO: if the split point is the given branch's point commit,ends with the message
        // Given branch is an ancestor of the current branch.
        //TODO: and if is the current branch's point ,check out given branch,print message
        // Current branch fast-forwarded.

        // I think the implementation should be divided into two part
        // one is search the split point
        //and then judge if the situation above
        //thus we could follow next step

        // add the branch file current file doesn't exit, use check , add it to addStage
        // nothing change stay
        //if they do same thing(remove or add the same file) ,stay that


    }
}
