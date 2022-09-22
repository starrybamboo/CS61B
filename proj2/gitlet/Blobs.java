package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Utils.*;



public class Blobs implements IOinterface,Serializable{
    private String FileName;
    private File passCode;
    public Blobs(String name, File passcode){
        this.FileName = name;
        this.passCode = passcode;
        helpWrite(name,this.passCode);
    }

    public void helpWrite(String name, File passcode){
        String version = Calculate.Sha(name,passcode);
        File f = join(Repository.GITLET_DIR,"Blobs",version);
        writeContents(f,this);
    }

    @Override
    public int attribute() {
        return 0;
    }
}


