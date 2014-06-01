package fiuba.pyp;

import com.sun.istack.internal.Nullable;
import rice.p2p.commonapi.*;
import rice.p2p.scribe.*;
import rice.pastry.commonapi.PastryIdFactory;

import javax.swing.event.EventListenerList;
import java.awt.*;
import java.util.EventListener;
import java.util.LinkedList;


/**
 * Created by pablo on 28/03/14.
 */
public class Client implements ScribeClient, Application {

    private static final String END_POINT_NAME = "gotoEndPoint";
    private static final String SCRIBE_NAME = "scribeInstance";
    private static final String TOPIC_NAME = "genericTopic";

    EventListenerList listeners = new EventListenerList();
    /**
     * The message sequence number.  Will be incremented after each send.
     */
    int seqNum = 0;

    /**
     * This task kicks off publishing and anycasting.
     * We hold it around in case we ever want to cancel the publishTask.
     */
    CancellableTask publishTask;

    /**
     * My handle to a scribe impl.
     */
    Scribe myScribe;

    /**
     * The only topic this appl is subscribing to.
     */
    Topic myTopic;

    /**
     * The Endpoint represents the underlieing node.  By making calls on the
     * Endpoint, it assures that the message will be delivered to a MyApp on whichever
     * node the message is intended for.
     */
    protected Endpoint endpoint;
    private LinkedList<NetworkObject> remoteOperations;

    /**
     * The constructor for this scribe client.  It will construct the ScribeApplication.
     *
     * @param node the PastryNode
     */
    public Client(Node node) {
        this.remoteOperations = new LinkedList<NetworkObject>();

        // you should recognize this from lesson 3
        this.endpoint = node.buildEndpoint(this, END_POINT_NAME);

        // construct Scribe
        this.myScribe = new ScribeImpl(node,SCRIBE_NAME);

        // construct the topic
        this.myTopic = new Topic(new PastryIdFactory(node.getEnvironment()), TOPIC_NAME);
        //System.out.println("myTopic = "+myTopic);

        // now we can receive messages
        endpoint.register();
    }

    /**
     * Subscribes to myTopic.
     */
    public void subscribe() {
        myScribe.subscribe(myTopic, this);
    }

    /**
     * Starts the publish task.
     */
    public void startPublishTask() {
        publishTask = endpoint.scheduleMessage(new PublishContent(), 5000, 5000);
    }

    @Nullable
    public NetworkObject getNextRemoteOperation() {
        return remoteOperations.pollFirst();
    }


    /**
     * Part of the Application interface.  Will receive PublishContent every so often.
     */
    public void deliver(Id id, Message message) {
        if (message instanceof PublishContent) {
            //sendMulticast();
            //sendAnycast();
        }
    }

    /**
     * Sends the multicast message.
     */
    public void sendMulticast(Operation operation) {

        NetworkObject myMessage = new NetworkObject(operation,endpoint.getLocalNodeHandle(), seqNum);
        //System.out.println("Node "+endpoint.getLocalNodeHandle()+" broadcasting "+seqNum + "mensaje"+ myMessage);
        myScribe.publish(myTopic, myMessage);
        seqNum++;
    }

    /**
     * Called whenever we receive a published message.
     */
    public void deliver(Topic topic, ScribeContent content) {
        if (((NetworkObject)content).from == null) {
          new Exception("Stack Trace").printStackTrace();
        }else if(((NetworkObject)content).from != endpoint.getLocalNodeHandle()){
            //System.out.println("MyScribeClient.deliver("+topic+","+content+")");
            remoteOperations.addLast((NetworkObject)content);
            fireRemoteOperation(new Event(null, 0, null));
        }
    }

    /**
     * Called when we receive an anycast.  If we return
     * false, it will be delivered elsewhere.  Returning true
     * stops the message here.
     */
    public boolean anycast(Topic topic, ScribeContent content) {
        boolean returnValue = myScribe.getEnvironment().getRandomSource().nextInt(3) == 0;
        //System.out.println("MyScribeClient.anycast("+topic+","+content+"):"+returnValue);
        return returnValue;
    }

    public void childAdded(Topic topic, NodeHandle child) {
//    System.out.println("MyScribeClient.childAdded("+topic+","+child+")");
    }

    public void childRemoved(Topic topic, NodeHandle child) {
//    System.out.println("MyScribeClient.childRemoved("+topic+","+child+")");
    }

    public void subscribeFailed(Topic topic) {
//    System.out.println("MyScribeClient.childFailed("+topic+")");
    }

    public boolean forward(RouteMessage message) {
        return true;
    }


    public void update(NodeHandle handle, boolean joined) {

    }

    class PublishContent implements Message {
        public int getPriority() {
            return MAX_PRIORITY;
        }
    }


    /************ Some passthrough accessors for the myScribe *************/
    public boolean isRoot() {
        return myScribe.isRoot(myTopic);
    }

    public NodeHandle getParent() {
        // NOTE: Was just added to the Scribe interface.  May need to cast myScribe to a
        // ScribeImpl if using 1.4.1_01 or older.
        return ((ScribeImpl)myScribe).getParent(myTopic);
        //return myScribe.getParent(myTopic);
    }

    public NodeHandle[] getChildren() {
        return myScribe.getChildren(myTopic);
    }

    public void addRemoteOperationListener(EventListener listener)
    {
        listeners.add(RemoteOperationListener.class, (RemoteOperationListener) listener);
    }
    public void removeRemoteOperationListener(RemoteOperationListener listener)
    {
        listeners.remove(RemoteOperationListener.class, listener);
    }
    protected void fireRemoteOperation(Event event)
    {
        Object[] listeners = this.listeners.getListenerList();
        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;
        for (int i = 0; i<numListeners; i+=2)
        {
            if (listeners[i]==RemoteOperationListener.class)
            {
                // pass the event to the listeners event dispatch method
                ((RemoteOperationListener)listeners[i+1]).dispatchNewOperationArrive(event);
            }
        }
    }

}

