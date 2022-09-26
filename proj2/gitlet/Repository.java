package gitlet;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

import static gitlet.Utils.*;


/** Represents a gitlet repository.
 *  does at a high level.
 *
 *  @author starrybamboo
 */
public class Repository {
    /**
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

//    private Branch headBranch;
//    private Commit headCommit;
//    private TreeMap<String,String> headCommitMap;


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
        new Branch(initial.getID(),"HEAD").saveAndHead();
        new Branch(initial.getID(),"master").saveAndHead();
    }

    private static void FileExit(String fileName){
        // judge the file exit situation
        if (!join(CWD, fileName).exists()) {
            exitWithMessage("File does not exist.");
        }
    }
    private static void CommitExit(String commitName){
        // judge the file exit situation
        if (!join(COMMIT, commitName).exists()) {
            exitWithMessage("No commit with that id exists.");
        }
    }

    private static void BranchExit(String branchName){
        // judge the file exit situation
        if (!join(REF, branchName).exists()) {
            exitWithMessage("No such branch exists.");
        }
    }

    private static boolean stageRemove(File stageChange,String fileName){
        Stage removeStage = readObject(stageChange,Stage.class);
        if (removeStage.containsKey(fileName)){
            removeStage.removeElement(fileName);
            return true;
        }return false;
    }

    private static void stageAdd(File stage, String fileName, Blobs addFile){
        Stage addStage = readObject(stage,Stage.class);
        addStage.addElement(fileName,addFile.getVersion());
    }


    private static boolean versionSame(TreeMap<String,String> headCommitBlobIDMap, String fileName, Blobs addFileBlob){
        if (headCommitBlobIDMap.containsKey(fileName)){
            if (headCommitBlobIDMap.get(fileName).equals(addFileBlob.getVersion())){
                return true;
            }
        }return false;
    }

    static void add(String fileName){
        FileExit(fileName);
        //set up a blob
        Blobs addFileBlob = new Blobs(fileName,readContents(join(CWD,fileName)));
        //Remove it from removeStage
        if (stageRemove(STAGERM,fileName)){return;}
        //exit if commit is same as it.
        Branch Head = Branch.getHeadBranch();
        Commit headCommit = Head.getCommit();
        TreeMap<String,String> headCommitBlobIDMap = headCommit.getBlobIDMap();
        if (versionSame(headCommitBlobIDMap,fileName,addFileBlob)){return;}
        stageAdd(STAGEAD, fileName, addFileBlob);
//        if (headCommitBlobIDMap.containsKey(fileName)){
//            if (headCommitBlobIDMap.get(fileName).equals(addFileBlob.getVersion())){
//                return;
//            }
//        }
//        Stage addStage = readObject(STAGEAD,Stage.class);
//        addStage.addElement(fileName,addFileBlob.getVersion());

        //add it to the addStage
    }

    static void commit(String msg){
        if (msg.equals("")){exitWithMessage("Please enter a commit message.");}

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
        Branch Head = Branch.getHeadBranch();
        Commit currentCommit = Head.getCommit();
        checkout(currentCommit.getID(),FileName);
//        TreeMap<String,String> map = currentCommit.getBlobIDMap();
//        if (map.isEmpty() || !map.containsKey(FileName)){
//            exitWithMessage("File does not exist in that commit.");
//            return;
//        }
//        //should be packed above.
//        Blobs file = readObject(join(BLOBS,map.get(FileName)),Blobs.class);
//        writeContents(join(CWD,FileName),file.getPasscode());
    }

    static String helpFind(String CommitID){
        List<String> finds = plainFilenamesIn(COMMIT);
        for (String x : finds){
            if ((x.substring(0,8)).equals(CommitID)){
                return x;
            }
        }
        return CommitID;
    }

    public static void checkout(String CommitID,String FileName){
        //read the specific commitID get the commit
        if (CommitID.length() == 8 ){
            CommitID = helpFind(CommitID);
        }
        CommitExit(CommitID);
        Commit currentCommit = readObject(join(COMMIT,CommitID),Commit.class);
        TreeMap<String,String> map = currentCommit.getBlobIDMap();
        if (map.isEmpty() || !map.containsKey(FileName)){
            exitWithMessage("File does not exist in that commit.");
            return;
        }
        Blobs file = readObject(join(BLOBS, map.get(FileName)),Blobs.class);
        writeContents(join(CWD,FileName),file.getPasscode());
    }




    public static void checkoutBranch(String branchName){
        // read Head and next branch and their commit.
        BranchExit(branchName);

        Branch Head = Branch.getHeadBranch();
        Branch changeBranch = readObject(join(REF,branchName), Branch.class);

        if(Head.getBranchName().equals(branchName)
                && Head.currentCommitID().equals(changeBranch.currentCommitID())){
            //maybe you think condition above is redundancy ,but it make sense don't change it. @author starrybamboo
            exitWithMessage("No need to checkout the current branch.");
        }

        Commit currentCommit = Head.getCommit();
        Commit nextCommit = changeBranch.getCommit();
        // read the file name and judge if there is an untracked file
        List<String> workingDirectoryFileName = plainFilenamesIn(CWD);

        TreeMap<String,String> oldFileMap = currentCommit.getBlobIDMap();
        TreeMap<String,String> addFileMap = nextCommit.getBlobIDMap();
        //delete the file untracked by next commit
        uncommitJudge(oldFileMap);
//        Stage addStage = readObject(STAGEAD,Stage.class);
//        for (String file :workingDirectoryFileName){
//            if (!oldFileMap.containsKey(file) && !addStage.MAP.containsKey(file)){
//                exitWithMessage("There is an untracked file in the way; delete it, or add and commit it first.");
//            }
//        }
        //renew the file that not match next commit
        for (String file : workingDirectoryFileName ){
            if (!addFileMap.keySet().contains(file)){
                File deleteFile = join(CWD,file);
                restrictedDelete(deleteFile);
            }
        }

        for (String file : addFileMap.keySet()){
            checkout(nextCommit.getID(),file);
        }
//        // write file to it.
//        for (String file : addFileMap.keySet()){
//            // read the Blob name in branch that change to and compare it with prev branch
//            String BlobVersion = addFileMap.get(file);
//            if (oldFileMap.containsKey(file)){
//                if(BlobVersion.equals(oldFileMap.get(file))){
//                    // if same ignore
//                    continue;
//                }else{
//                    // if not same overwrite
//                    Blobs writeFile = readObject(join(BLOBS,BlobVersion),Blobs.class);
//                    writeContents(join(CWD,writeFile.getFileName()),writeFile.getPasscode());
//                }
//            }else{
//                // if not contain just write
//                Blobs writeFile = readObject(join(BLOBS,BlobVersion),Blobs.class);
//                writeContents(join(CWD,writeFile.getFileName()),writeFile.getPasscode());
//            }
//        }
        changeBranch.saveAndHead();
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
        Commit currentCommit = Head.getCommit();
        new Branch(currentCommit.getID(),branchName).saveNoHead();
        //below zhushi is old, no use .
        //save would chang the Head point branch so we should change it back
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

    static void uncommitJudge(TreeMap<String, String> oldFileMap){
        Stage addStage = readObject(STAGEAD,Stage.class);
        List<String> workingDirectoryFileName = plainFilenamesIn(CWD);
        //delete the file untracked by next commit
        for (String file :workingDirectoryFileName) {
            if (!oldFileMap.containsKey(file) && !addStage.MAP.containsKey(file)) {
                exitWithMessage("There is an untracked file in the way; delete it, or add and commit it first.");
            }
        }
    }

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
    static void merge(String mergeBranchName) {

        Boolean flag = false;
        Stage addStage = readObject(STAGEAD, Stage.class);
        Stage removeStage = readObject(STAGERM, Stage.class);
        if (!addStage.MAP.isEmpty() || !removeStage.MAP.isEmpty()) {
            exitWithMessage("You have uncommitted changes.");
        }
        BranchExit(mergeBranchName);
        Branch headBranch = Branch.getHeadBranch();
        Branch mergeBranch = readObject(join(REF, mergeBranchName), Branch.class);
        if (headBranch.getBranchName().equals(mergeBranch.getBranchName())) {
            exitWithMessage("cannot merge a branch with itself.");
        }
        Commit headCommit = headBranch.getCommit();
        Commit mergeCommit = mergeBranch.getCommit();
        TreeMap<String, String> currentFileMap = headCommit.getBlobIDMap();
        TreeMap<String, String> mergeFileMap = mergeCommit.getBlobIDMap();
        uncommitJudge(currentFileMap);
        //end error resolve
        //start to merge
        //find split point
        Commit splitCommit = findSplit(headCommit, mergeCommit);
        if (splitCommit.getID().equals(headCommit.getID())){
            checkout(mergeBranchName);
            exitWithMessage("Current branch fast-forwarded.");
        }
        if (splitCommit.getID().equals(mergeCommit.getID())){
            exitWithMessage("Given branch is an ancestor of the current branch.");
        }
        // end paticular situation go on
        Branch splitBranch = Branch.getBranch(mergeBranchName);
        splitBranch.changeCommitIDalone(splitCommit.getID());
        checkoutBranch(splitBranch.getBranchName());
        TreeMap<String,String> splitMap = splitCommit.getBlobIDMap();

        Set<String> headSet = headCommit.getBlobIDMap().keySet();
        Set<String> mergeSet = mergeCommit.getBlobIDMap().keySet();
        Set<String> splitSet = splitCommit.getBlobIDMap().keySet();

        Set<String> conflictSet = new HashSet<String>();

        for (String x : headSet){
            checkout(headCommit.getID(),x);
            add(x);
        }
        for (String x : mergeSet){
            String splitVersion = splitMap.get(x);
            String currentVersion = currentFileMap.get(x);
            String mergeVersion = mergeFileMap.get(x);
//            if (currentVersion == null){
//                checkout(mergeCommit.getID(), x);
//                add(x);
//            }else
            if (splitVersion == null){
                if (currentVersion == null){
                    checkout(mergeCommit.getID(), x);
                    add(x);
                }else if(!mergeVersion.equals(currentVersion)){
                    conflict(x, currentVersion, mergeVersion);
                    conflictSet.add(x);
                    flag = true;
                }else {
                    checkout(headCommit.getID(), x);
                    add(x);
                }
            }else{
                if (currentVersion == null){
                    checkout(mergeCommit.getID(), x);
                    add(x);
                } else if(!mergeVersion.equals(currentVersion) && !currentVersion.equals(splitVersion)){
                    conflict(x, currentVersion, mergeVersion);
                    conflictSet.add(x);
                    flag = true;
                }else {
                    checkout(mergeCommit.getID(), x);
                    add(x);
                }
            }
        }

        for (String x : splitSet){
            if ((!headSet.contains(x) || ! mergeSet.contains(x) )&& !conflictSet.contains(x)){
                remove(x);
                if (headSet.contains(x)){
                    remove(x);
                }
            }
        }

        commit("Merged " + mergeBranchName +" into " + headBranch.getBranchName() + ".");
        Commit newCommit = Branch.getHeadCommit();
        newCommit.mergeParent(headCommit.getID(),mergeCommit.getID());
        if (flag){
            System.out.println("Encountered a merge conflict.");
        }
        headBranch.changeCommitID(newCommit.getID());
        join(REF,mergeBranchName).delete();
    }

    static void conflict(String x,String currentVersion ,String mergeVersion){
        Blobs head = readObject(join(BLOBS,currentVersion),Blobs.class);
        Blobs merge = readObject(join(BLOBS,mergeVersion),Blobs.class);


        String newContent = "<<<<<<< HEAD\n" + new String(head.getPasscode()) + "=======\n" +
                new String(merge.getPasscode()) + ">>>>>>>\n";
        Blobs blobs = new Blobs(x,newContent.getBytes(StandardCharsets.UTF_8));
        blobs.save();
        writeContents(join(CWD,x),newContent);
    }



    static Commit findSplit(Commit headCommit, Commit mergeCommit){
        TreeMap<String,String> headMap = new TreeMap<String,String>();
        TreeMap<String,String> mergeMap = new TreeMap<String,String>();
        ArrayDeque<Commit> BST = new ArrayDeque<Commit>();

        BST.addFirst(headCommit);headMap.put(headCommit.getID(),"1");
        helpBST(BST,headMap);
        BST.addFirst(mergeCommit);mergeMap.put(mergeCommit.getID(),"1");
        helpBST(BST,mergeMap);


        String splitName = "";
        int min = 100000;
        for (String x: headMap.keySet()){
            if (mergeMap.containsKey(x) && headMap.containsKey(x)){
                int level = mergeMap.get(x).length();
                if (level < min){
                    splitName = x;
                    min = level;
                }
            }
        }
        return readObject(join(COMMIT,splitName),Commit.class);
    }

    static void helpBST(ArrayDeque<Commit> BST, TreeMap<String,String> map){
        String i = "1";
        while (!BST.isEmpty()){
            ArrayDeque<Commit> AST = new ArrayDeque<Commit>();
            while (!BST.isEmpty()){
                for (String x :BST.getFirst().getParentList()){
                    if (! map.containsKey(x)){
                         map.put(x,i);
                        AST.addFirst(readObject(join(COMMIT, x), Commit.class));
                    }
                }
                BST.removeFirst();
            }
            i = i + "1";
            BST = AST;
            if (BST == null){
                return;
            }
        }
    }







//        TreeMap<String,String> splitFileMap = splitCommit.getBlobIDMap();
//        TreeMap<String,String> headFileMap = headCommit.getBlobIDMap();
//        TreeMap<String,String> mergeFileMap = mergeCommit.getBlobIDMap();
//        // read all map
//        TreeMap<String,String> addMap1 = new TreeMap<String,String>();
//        TreeMap<String,String> removeMAP1 = new TreeMap<String,String>();
//        TreeMap<String,String> nameSameMap1 = new TreeMap<String,String>();
//        TreeMap<String,String> addMap2 = new TreeMap<String,String>();
//        TreeMap<String,String> removeMAP2 = new TreeMap<String,String>();
//        TreeMap<String,String> nameSameMap2 = new TreeMap<String,String>();
//
//        Repository.helpIterate(headFileMap,splitFileMap,nameSameMap1);
//        addMap1 = headFileMap;
//        removeMAP1 = splitFileMap;
//
//        // in order to distinguish the split map is different I use 1 and 2
//        TreeMap<String,String> splitFileMap2 = splitCommit.getBlobIDMap();
//        Repository.helpIterate(mergeFileMap,splitFileMap2,nameSameMap2);
//        addMap2 = mergeFileMap;
//        removeMAP2 = splitFileMap2;
//
//        TreeMap<String,String> resultTreeMap = judgeConflict(addMap1,removeMAP1,nameSameMap1,addMap2,removeMAP2,nameSameMap2);
//        new Commit();
//
//    }
//    static TreeMap<String,String> judgeConflict(TreeMap<String,String> addMap1,TreeMap<String,String> removeMAP1 ,TreeMap<String,String> nameSameMap1 ,
//                                                TreeMap<String,String> addMap2, TreeMap<String,String> removeMAP2,TreeMap<String,String> nameSameMap2 ){
//        TreeMap<String,String> resultTreeMap = new TreeMap<String,String>();
//        helpIterateADD(addMap1,addMap2,resultTreeMap);
//        helpIterateADD(nameSameMap1,nameSameMap2,resultTreeMap);
//
//        return resultTreeMap;
//    }
//
//    static void helpIterate( TreeMap<String,String> set1, TreeMap<String,String> set2, TreeMap<String,String> jiaoJI){
//        for (String x : set1.keySet()){
//            if (set2.containsKey(x)){
//                String version = readObject(join(BLOBS,set2.get(x)),Blobs.class).getVersion();
//                // if modify actually change it to the modified vesion
//                jiaoJI.put(x,version);
//                set1.remove(x);
//            }
//        }
//    }
//    static void helpIterateADD( TreeMap<String,String> set1, TreeMap<String,String> set2, TreeMap<String,String> jiaoJI){
//        for (String x : set1.keySet()){
//            if (set2.containsKey(x)){
//                String version1 = readObject(join(BLOBS,set1.get(x)),Blobs.class).getVersion();
//                String version2 = readObject(join(BLOBS,set2.get(x)),Blobs.class).getVersion();
//                // if version conflict print
//                if (!version1.equals(version2)){
//                    byte[] blobsContent = printConflictMessage(readObject(join(BLOBS,set1.get(x)),Blobs.class),
//                            readObject(join(BLOBS,set2.get(x)),Blobs.class));
//                    Blobs tmp= new Blobs(x, blobsContent);
//                    jiaoJI.put(x, tmp.getVersion());
//                    continue;
//                }
//                jiaoJI.put(x,version1);
//                set1.remove(x);
//            }
//        }
//    }
//    static byte[] printConflictMessage(Blobs tmp1, Blobs tmp2){
//        System.out.println("Encountered a merge conflict.");
//                return("<<<<<<< HEAD\n" +
//                tmp1.getPasscode().toString() +"\n" +
//                "=======\n" +
//                tmp2.getPasscode().toString() +"\n" +
//                ">>>>>>>").getBytes();
//    }
//
//    private static Commit findSplit (Commit headCommit ,Commit mergeCommit){
//        TreeMap<String,String> headCommitMap = new TreeMap<String,String>();
//        ArrayList<String> CommitList = new ArrayList<>();
//        CommitList.add(headCommit.getID());
//        String i = "";
//        headCommitMap.put(headCommit.getID(), "1");
//        // i is depth CommitList is the next level node and headCommit is recording.
//        Repository.help1(CommitList,headCommitMap,"1");
//        CommitList.add(mergeCommit.getID());
//        Repository.help2(CommitList,headCommitMap,"2");
//        int max = 10000;
//        String splitCommitName = "";
//        for (String x : headCommitMap.keySet()){
//            String level = headCommitMap.get(x);
//            int levelLayer = level.length();
//            if (!(level.contains("1") && level.contains("2"))){continue;}
//            if (levelLayer < max){
//                max = levelLayer;
//                splitCommitName = x;
//            }
//        }
//        return readObject(join(COMMIT, splitCommitName), Commit.class);
//    }
//
//    public static void help1(ArrayList<String> CommitList, TreeMap<String,String> headCommitMap, String i){
//        ArrayList<String> CommitListNext = new ArrayList<>();
//
//        while (!CommitList.isEmpty()){
//            Commit getCommit = readObject(join(COMMIT,CommitList.get(0)), Commit.class);
//            // add all parent in this list
//            //i += "1";
//            while (getCommit.getParent() != null){
//                String parentCommit = getCommit.getParent();
//                // if contain chose min
//                if (headCommitMap.containsKey(parentCommit)){
//                    if (headCommitMap.get(parentCommit).length() > i.length()){
//                        headCommitMap.remove(parentCommit);
//                        headCommitMap.put(parentCommit, i);
//                    }
//                }else {
//                    headCommitMap.put(parentCommit, i);
//                }
//                //get the next Array that should iterate.
//                CommitListNext.add(parentCommit);
//                getCommit.removeParent();
//            }
//        }
//        // iterate over
//        // next.
//        Repository.help1(CommitListNext, headCommitMap,i + "1");
//    }
//
//    public static void help2(ArrayList<String> CommitList, TreeMap<String,String> headCommitMap, String i){
//        ArrayList<String> CommitListNext = new ArrayList<>();
//
//        while (!CommitList.isEmpty()){
//            Commit getCommit = readObject(join(COMMIT,CommitList.get(0)), Commit.class);
//            // add all parent in this list
//            //i += "1";
//            while (getCommit.getParent() != null){
//                String parentCommit = getCommit.getParent();
//                // if contain chose min
//
//                if (headCommitMap.containsKey(parentCommit)){
//                    if (headCommitMap.get(parentCommit).contains("1")){
//                        headCommitMap.remove(parentCommit);
//                        headCommitMap.put(parentCommit,"1" + i);
//                    }else if (headCommitMap.get(parentCommit).length() > i.length()){
//                        headCommitMap.remove(parentCommit);
//                        headCommitMap.put(parentCommit, i);
//                    }
//                }else {
//                    headCommitMap.put(parentCommit, i);
//                }
//                //get the next Array that should iterate.
//                CommitListNext.add(parentCommit);
//                getCommit.removeParent();
//            }
//        }
//        // iterate over
//        // next.
//        Repository.help2(CommitListNext, headCommitMap,i + "2");
//    }
//


}
