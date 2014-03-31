package fiuba.pyp;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import rice.environment.Environment;
import rice.p2p.commonapi.NodeHandle;
import rice.pastry.NodeIdFactory;
import rice.pastry.PastryNode;
import rice.pastry.PastryNodeFactory;
import rice.pastry.socket.SocketPastryNodeFactory;
import rice.pastry.standard.RandomNodeIdFactory;

import javax.swing.event.EventListenerList;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Created by pablo on 09/03/14.
 */
public class RemoteOperationHandler{

    private final Client app;

    @Nullable
    public Operation getNextRemoteOperation() {
        //hacer la logica de administrar si llegan paquetes
        //de un mismo user desordenados y devolver el correcto
        NetworkObject networkObject = app.getNextRemoteOperation();
        if(networkObject != null)
            return networkObject.getOperation();
        else return null;

    }

    public void publishOperation(@NotNull Operation operation){
        app.sendMulticast(operation);
    }

    public RemoteOperationHandler(int bindport, InetSocketAddress bootaddress,
                       Environment env) throws Exception {

        // Generate the NodeIds Randomly
        NodeIdFactory nidFactory = new RandomNodeIdFactory(env);

        // construct the PastryNodeFactory, this is how we use rice.pastry.socket
        PastryNodeFactory factory = new SocketPastryNodeFactory(nidFactory, bindport, env);

        // loop to construct the nodes/apps
        //for (int curNode = 0; curNode < numNodes; curNode++) {
        // construct a new node
        PastryNode node = factory.newNode();

        // construct a new scribe application
        app = new Client(node);
        //apps.add(app);

        node.boot(bootaddress);

        // the node may require sending several messages to fully boot into the ring
        synchronized(node) {
            while(!node.isReady() && !node.joinFailed()) {
                // delay so we don't busy-wait
                node.wait(500);

                // abort if can't join
                if (node.joinFailed()) {
                    throw new IOException("Could not join the FreePastry ring.  Reason:"+node.joinFailedReason());
                }
            }
        }

        System.out.println("Finished creating new node: " + node);

        app.subscribe();
        //app.startPublishTask();

    }

    public void addRemoteOperationListener(EventListener event){
        this.app.addRemoteOperation(event);
    }

}

