package org.west2.clusterio;

import org.west2.clusterio.datanode.protocol.DatanodeID;
import org.west2.clusterio.datanode.protocol.DatanodeInfo;
import org.west2.clusterio.datanode.protocol.DatanodeRegistration;
import org.west2.clusterio.datanode.protocol.StorageInfo;
import org.west2.clusterio.common.utils.NetworkUtil;
import org.west2.clusterio.datanode.DatanodeSystem;
import org.west2.clusterio.datanode.client.DatanodeClient;

public class DataNodeStartUp {
    public static void main(String[] args) {
        DatanodeClient client =DatanodeClient.getInstance();
        //For test
        DatanodeID idTest = new DatanodeID("1", NetworkUtil.getLocalAddress(), "DatanodeTest", 9097);
        StorageInfo storageInfo = new StorageInfo("1", 1, 1, "1", System.currentTimeMillis());
        DatanodeRegistration datanodeRegistration = new DatanodeRegistration(idTest, storageInfo);
        DatanodeSystem sys = DatanodeSystem.getSystem();
        DatanodeInfo info = new DatanodeInfo(idTest, 4096, 1024, 3072, 3072, System.currentTimeMillis(), 0, System.currentTimeMillis());
        sys.setInfo(info);
        sys.setReg(datanodeRegistration);
        client.initChannel("127.0.0.1",9096);
        client.register();

    }
}
