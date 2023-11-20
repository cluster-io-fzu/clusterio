package org.west2.clusterio.namenode.common;

public class NameNodeException extends RuntimeException {
    private final NameNodeExceptionCode code;

    public NameNodeException(NameNodeExceptionCode code, String message) {
        super(message);
        this.code = code;
    }

    public NameNodeException(NameNodeExceptionCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public NameNodeExceptionCode getCode(){
        return code;
    }
}
