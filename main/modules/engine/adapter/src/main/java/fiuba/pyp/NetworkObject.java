package fiuba.pyp;

import com.sun.accessibility.internal.resources.accessibility_ko;

import java.io.Serializable;

/**
 * Created by pablo on 25/03/14.
 */
public class NetworkObject implements Serializable {

    private Operation operation;
    private int id;
    private int ack;

    public NetworkObject(Operation operation, int id) {
        this.operation = operation;
        this.id = id;
        this.ack = 0;

    }
    public NetworkObject(int id) {
        this.operation = null;
        this.id = id;
        this.ack = 1;

    }

    public int getAck() {
        return ack;
    }

    public Operation getOperation() {
        return operation;
    }

    public int getId() {
        return id;
    }
}
