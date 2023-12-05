package org.west2.clusterio.datanode.protocol;

import org.west2.clusterio.common.protocolPB.DatanodeProtocol;

public class RegisterDatanodeRequest {
    private DatanodeRegistration registration;

    public RegisterDatanodeRequest(DatanodeRegistration registration) {
        this.registration = registration;
    }

    public DatanodeProtocol.RegisterDatanodeRequestProto parse(){
        DatanodeProtocol.DatanodeRegistrationProto registrationProto = registration.parseDatanodeReg();
        return DatanodeProtocol.RegisterDatanodeRequestProto.newBuilder()
                .setRegistration(registrationProto).build();
    }
}
