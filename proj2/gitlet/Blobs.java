package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Utils.*;



public class Blobs implements IOinterface,Serializable{
    private String FileName;
    private byte[] passCode;
    private String Version;
    public Blobs(String name, byte[] passcode){
        this.FileName = name;
        this.passCode = passcode;
        helpWrite(name,this.passCode);
    }

    public void helpWrite(String name, byte[] passcode){
        String version = Calculate.Sha(name,passcode);
        File f = join(Repository.GITLET_DIR,"Blobs",version);
        this.Version = version;
        writeContents(f,this);
    }

    public String getVersion(){
        return this.Version;
    }

    @Override
    public int attribute() {
        return 0;
    }
}


