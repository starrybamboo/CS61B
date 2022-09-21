package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Utils.join;
import static gitlet.Utils.writeContents;

public class Branch implements Serializable {
    private Commit pointer;
    public Branch(Commit tmp,String name){
        this.pointer = tmp;
        File f = join(Repository.GITLET_DIR,name);
        writeContents(f,tmp);
    }

    public Commit get(){
        return this.pointer;
    }
}
