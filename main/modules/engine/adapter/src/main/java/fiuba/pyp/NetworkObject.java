package fiuba.pyp;

import com.sun.accessibility.internal.resources.accessibility_ko;
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

    //private int id;
    //private int ack;

    public NetworkObject(Operation operation, NodeHandle from,int seq) {
        this.operation = operation;
        this.from = from;
        this.seq = seq;

        //this.id = id;
        //this.ack = 0;

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
