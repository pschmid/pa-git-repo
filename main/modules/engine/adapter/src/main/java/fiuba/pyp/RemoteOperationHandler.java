package fiuba.pyp;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import rice.environment.Environment;
import rice.p2p.commonapi.Id;
import rice.pastry.NodeIdFactory;
import rice.pastry.PastryNode;
import rice.pastry.PastryNodeFactory;
import rice.pastry.socket.SocketPastryNodeFactory;
import rice.pastry.standard.RandomNodeIdFactory;

import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;

/**
 * Created by pablo on 09/03/14.
 */
public class RemoteOperationHandler{

    private Client app;
    private OperationManager operationManager;

    public RemoteOperationHandler(InetSocketAddress localIp, InetSocketAddress bootaddress) throws Exception{
        // Loads pastry configurations
        Environment env = new Environment();

        // disable the UPnP setting (in case you are testing this on a NATted LAN)
        env.getParameters().setString("nat_search_policy","never");

        // Generate the NodeIds Randomly
        NodeIdFactory nidFactory = new RandomNodeIdFactory(env);

        // construct the PastryNodeFactory, this is how we use rice.pastry.socket
        PastryNodeFactory factory = new SocketPastryNodeFactory(nidFactory, localIp.getAddress(),localIp.getPort(), env);

        // create a node
        PastryNode node = factory.newNode();

        // construct a new scribe application
        app = new Client(node);

        //boot to the ring
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
        operationManager = new OperationManager(getId());
        //app.startPublishTask();
    }

    public Id getId() {
        return app.endpoint.getLocalNodeHandle().getId();
    }

    @Nullable
    private Operation getNextNetworkOperation() {
        NetworkObject networkObject = app.getNextRemoteOperation();
        if (networkObject != null)
            return networkObject.getOperation();
        else return null;
    }
    //get next executable operation
    @Nullable
    public synchronized Operation getNextRemoteOperation(){
        return  operationManager.getNextOperation();
    }

    public void publishOperation(@NotNull Operation operation){
        app.sendMulticast(operation);
    }

    public void addRemoteOperationListener(EventListener event){
        RemoteOperationListener arrivalEvent = new RemoteOperationListener() {
            @Override
            public void dispatchNewOperationArrive(Event event) {

                Operation nextOp = getNextNetworkOperation();
                operationManager.addOperation(nextOp);
                operationManager.printQueue();

            }
        };
        this.app.addRemoteOperationListener(arrivalEvent);
    }

}

