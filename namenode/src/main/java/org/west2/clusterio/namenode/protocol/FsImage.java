package org.west2.clusterio.namenode.protocol;

import java.io.*;
import java.util.List;

public class FsImage implements Serializable {
    private final long createdTime;
    private int transactionID = 0;
    private List<FsFile> files;

    public FsImage() {
        createdTime = System.currentTimeMillis();
    }

    public void serialize(File file) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        ObjectOutputStream stream = new ObjectOutputStream(out);
        stream.writeObject(this);
        stream.close();
        out.close();
    }

    public static FsImage deserialization(File file) throws IOException, ClassNotFoundException {
        FsImage image = null;
        FileInputStream input = new FileInputStream(file);
        ObjectInputStream stream = new ObjectInputStream(input);
        image = (FsImage) stream.readObject();
        stream.close();
        input.close();
        return image;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public void setFiles(List<FsFile> files) {
        this.files = files;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public List<FsFile> getFiles() {
        return files;
    }
}
