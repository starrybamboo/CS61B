package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Utils.*;



public class Blobs implements Serializable{
    private String FileName;
    private byte[] passCode;
    private String Version;
    public Blobs(String name, byte[] passcode){
        this.FileName = name;
        this.passCode = passcode;
        this.save();
    }

    public void save(){
        this.Version = sha1(this.FileName,this.passCode);
        File saveFile = join(Repository.BLOBS,this.Version);
        writeObject(saveFile,this);
    }

//    public void helpWrite(String name, byte[] passcode){
//        String version = Utils.sha1(name,passcode);
//        File f = join(Repository.GITLET_DIR,"Blobs",version);
//        this.Version = version;
//        writeContents(f,this);
//    }

    public String getVersion(){
        return this.Version;
    }
    public String getFileName(){return this.FileName;}
    public byte[] getPasscode(){return this.passCode;}
}


