package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.TreeMap;

import static gitlet.Repository.GITLET_DIR;
import static gitlet.Utils.*;
import static gitlet.Utils.readContents;

public class Stage implements Serializable {
    public String name;
    public TreeMap<String,String> MAP;
    Stage(String name){
        this.name = name;
        this.MAP = new TreeMap<String,String>();
    }
    // name present the stage type
    public void addElement(String name, String currentVersion){
        //if no previous version add
        if (!this.MAP.containsKey(name)){
                this.MAP.put(name,currentVersion);
                this.save();
        }else {
            //if version is not equal
            String stageVersion = this.MAP.get(name);
            if (!currentVersion.equals(stageVersion)) {
                this.MAP.remove(name);
                this.MAP.put(name, currentVersion);
                this.save();
            }
        }
    }

    public void removeElement(String name){
        // if has name remove regardless of version
        if (this.MAP.get(name) != null){
            this.MAP.remove(name);
            this.save();
        }
    }

    public void clear(){
        this.MAP = new TreeMap<String,String>();
        this.save();
    }

    public Boolean containsKey(String FileName){
        return this.MAP.containsKey(FileName);
    }

    public void save(){
        File saveFile = join(Repository.STAGE,this.name);
        writeObject(saveFile,this);
    }
}




//    public static TreeMap getAddStage(){
//        return readObject(join(GITLET_DIR,"Stage","MAPad"), TreeMap.class);
//    }
//
//    public static TreeMap getRemoveStage(){
//        return readObject(join(GITLET_DIR,"Stage","MAPrm"), TreeMap.class);
//    }

//    public static void clearAD(){
//        writeContents(join(GITLET_DIR,"Stage","MAPad"),"");
//    }
//
//    public static void clearRM(){
//        writeContents(join(GITLET_DIR,"Stage","MAPrm"),"");
//    }


//    private TreeMap<String,String> AdditionMap = new TreeMap<String,String>();
//    private TreeMap<String,String> RemoveMap = new TreeMap<String,String>();
//
//    public Stage(){
//        File tmp = join(GITLET_DIR,REMOVE);
//        writeObject(tmp,RemoveMap);
//        File tmp2 = join(GITLET_DIR,ADD);
//        writeObject(tmp2,AdditionMap);
//    }
//
//    static void checkAdd(File tmp,String fileName){
//
//        TreeMap AdditionMap = AdditionMap();
//        TreeMap RemoveMap = RemoveMap();
//
//        String key = Calculate.Sha(fileName,tmp);
//
//        RemoveMap.remove(fileName);
//        String value =(String) AdditionMap.get(fileName);
//        restrictedDelete(join(GITLET_DIR,"StageRemove",value));
//
//        if (value == null){
//            File f = join(GITLET_DIR,"StageAdd",key);
//            AdditionMap.put(fileName,key);
//            writeContents(f,tmp);
//        }else if(value == key) {
//            ;
//        }else {
//            File old = join(GITLET_DIR,"StageRemove",value);
//            restrictedDelete(old);
//            File f = join(GITLET_DIR,"StageAdd",key);
//            writeContents(f,tmp);
//            AdditionMap.remove(fileName);
//            AdditionMap.put(fileName,key);
//        }
//
//        writeObject(join(GITLET_DIR,ADD),AdditionMap);
//        writeObject(join(GITLET_DIR,REMOVE),RemoveMap);
//    }
//
//    static TreeMap AdditionMap(){
//        TreeMap AdditionMap = readObject(join(GITLET_DIR,ADD),TreeMap.class);
//        return  AdditionMap;
//    }
//
//    static Object searchAdd(String m){
//        TreeMap AdditionMap = AdditionMap();
//        return AdditionMap.get(m);
//    }
//    static TreeMap RemoveMap (){
//        TreeMap RemoveMap = readObject(join(GITLET_DIR,REMOVE),TreeMap.class);
//        return RemoveMap;
//    }
//
//    static void clearAdd(){
//        writeObject(join(GITLET_DIR,ADD),null);
//    }
//
//    static void clearRemove(){
//        writeObject(join(GITLET_DIR,REMOVE),null);
//    }