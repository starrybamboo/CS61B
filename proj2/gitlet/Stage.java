package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.TreeMap;

import static gitlet.Repository.GITLET_DIR;
import static gitlet.Utils.*;
import static gitlet.Utils.readContents;

public class Stage implements Serializable {
    public static TreeMap getAddStage(){
        return readObject(join(GITLET_DIR,"Stage","MAPad"), TreeMap.class);
    }

    public static TreeMap getRemoveStage(){
        return readObject(join(GITLET_DIR,"Stage","MAPrm"), TreeMap.class);
    }

    public static void clearAD(){
        writeContents(join(GITLET_DIR,"Stage","MAPad"),null);
    }

    public static void clearRM(){
        writeContents(join(GITLET_DIR,"Stage","MAPrm"),null);
    }


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

}
