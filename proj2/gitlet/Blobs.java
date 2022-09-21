package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import static gitlet.Utils.*;



public class Blobs implements IOinterface,Serializable{
    public Blobs(String name, File passcode){
        String x = Calculate.Sha(name, passcode);
        File f = join(Repository.GITLET_DIR,"Blobs",x);
        writeContents(f,this);
    }

    @Override
    public int attribute() {
        return 0;
    }
}


