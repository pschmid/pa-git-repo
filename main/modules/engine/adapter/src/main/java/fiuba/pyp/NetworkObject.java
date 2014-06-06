package fiuba.pyp;

import rice.p2p.commonapi.NodeHandle;
import rice.p2p.scribe.ScribeContent;

import java.io.Serializable;

/**
 * Created by pablo on 25/03/14.
 */
public class NetworkObject implements ScribeContent {

    private Operation operation;
    /**
     * The source of this content.
     */
    NodeHandle from;
    /**
     * The sequence number of the content.
     */
    int seq;


    public NetworkObject(Operation operation, NodeHandle from,int seq) {
        this.operation = operation;
        this.from = from;
        this.seq = seq;
    }

    public Operation getOperation() {
        return operation;
    }

    public NodeHandle getId() {
        return from;
    }


    public String toString() {
        return "MyScribeContent #"+seq+"contains "+operation+" from "+from;
    }

}
