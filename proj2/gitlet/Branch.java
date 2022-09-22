package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Utils.join;
import static gitlet.Utils.writeContents;

public class Branch implements Serializable {
    private Commit pointer;
    private String name;
    public Branch(Commit buildPlace,String name){
        this.pointer = buildPlace;
        this.name = name;
        File f = join(Repository.GITLET_DIR,"Ref",name);
        writeContents(f,buildPlace);
    }

    public Commit getPointer(){
        return this.pointer;
    }

    public String getName(){return this.name;}
}
