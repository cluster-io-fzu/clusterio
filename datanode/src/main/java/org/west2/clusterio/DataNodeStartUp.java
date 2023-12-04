package org.west2.clusterio;

import org.west2.clusterio.common.protocol.DatanodeID;
import org.west2.clusterio.common.protocol.DatanodeRegistration;
import org.west2.clusterio.common.protocol.StorageInfo;
import org.west2.clusterio.common.utils.NetworkUtil;
import org.west2.clusterio.datanode.DatanodeSystem;
import org.west2.clusterio.datanode.client.DatanodeClient;

public class DataNodeStartUp {
    public static void main(String[] args) {
        DatanodeClient client = new DatanodeClient();
        DatanodeID idTest = new DatanodeID("1", NetworkUtil.getLocalAddress(), "DatanodeTest", 9097);
        StorageInfo storageInfo = new StorageInfo("1", 1, 1, "1", System.currentTimeMillis());
        DatanodeRegistration datanodeRegistration = new DatanodeRegistration(idTest, storageInfo);
        DatanodeSystem sys = DatanodeSystem.getSystem();
        sys.setReg(datanodeRegistration);
        client.initChannel("127.0.0.1",9096);
        client.register();
    }
}
