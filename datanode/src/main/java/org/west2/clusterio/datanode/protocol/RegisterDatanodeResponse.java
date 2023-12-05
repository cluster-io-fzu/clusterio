package org.west2.clusterio.datanode.protocol;

import org.west2.clusterio.common.protocolPB.DatanodeProtocol;

public class RegisterDatanodeResponse {
    private DatanodeRegistration registration;

    public RegisterDatanodeResponse(DatanodeRegistration registration) {
        this.registration = registration;
    }

    public DatanodeProtocol.RegisterDatanodeResponseProto parse(){
        DatanodeProtocol.DatanodeRegistrationProto registrationProto = registration.parseDatanodeReg();
        return DatanodeProtocol.RegisterDatanodeResponseProto.newBuilder()
                .setRegistration(registrationProto).build();
    }
}
