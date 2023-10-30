package org.west2.clusterio.jsr.config;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Haechi
 */
@Getter
public class CodingConfig implements Serializable {
    @Serial
    private static final long serialVersionUID = -6541180061782004705L;

    private int dataShards= 6;

    private int parityShards= 3;

    public CodingConfig setDataShards(int dataShards) {
        this.dataShards = dataShards;
        return this;
    }

    public CodingConfig setParityShards(int parityShards) {
        this.parityShards = parityShards;
        return this;
    }

}
