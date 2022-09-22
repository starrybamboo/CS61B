package gitlet;

import java.io.File;

import static gitlet.Utils.readContents;
import static gitlet.Utils.sha1;

public class Calculate {
    public static String Sha(String fileName, byte[] tmp){
        return sha1(fileName + tmp);
    }
}
