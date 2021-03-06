package fiuba.pyp;
import org.apache.log4j.Logger;
import rice.p2p.commonapi.Id;

import java.net.InetSocketAddress;

import static java.lang.Thread.sleep;

/**
 * Created by pablo on 24/03/14.
 */
public class AdaptedOperationManeger {

    LocalOperationHandler localOperationHandler;
    RemoteOperationHandler remoteOperationHandler;
    AddressDomain addressDomain;
    int localCounter=1;
    //Este contador hace que cuando hay inactividad en la red por cierto tiempo se limpie el history buffer.
    //Cuando esta en 0 no empieza a contar todavia hasta que se capture un evento de la App o se reciba por la red (sincronia)
    //Si ya habia comenzado a contar y se recibe algo nuevamente vuelve a comenzar desde 1 (evita conflictos)
    //Al llegar a 1000 limpia el buffer en cada nodo de la red ya que estan sincronizados.
    //Despues de limpiar el buffer vuelve a 0 y no vuelve a empezar hasta que reciba algo de la App o la red, y asi sucesivamente
    private static int timeCount = 0;

    //Constructor que inicializa los parametros de Red, los handlers y la comunicacion con la capa inferior
    public AdaptedOperationManeger(InetSocketAddress localIP, InetSocketAddress bootaddress) {
        this.localCounter = 1; initializeCount();
        this.localOperationHandler = new LocalOperationHandler();
        this.addressDomain = AddressDomain.getInstance();
        try {
            startRemoteHandler(localIP,bootaddress);
        } catch (Exception e) {
            Logger.getLogger(App.class);
            return;
        }
        ConcurrencyControl concurrencyControl = addressDomain.getConcurrencyControl();
        concurrencyControl.clearHistoryBuffer();


        //TODO this might be setted by application
        //concurrencyControl.setDoc(new DocumentText());

    }

    //Caputa los eventos de interes de la capa superior de la arquitectura y los convierte en operaciones
    public synchronized void captureEventFromApp(String type, String character, int position){
        Id localId = remoteOperationHandler.getId();
        Operation newOp = new Operation(new DocumentCharacter(character), position, type, localId, addressDomain.getConcurrencyControl().getTimeStamp());
        newOp.setLocalTimeStamp(localCounter);
        localCounter++;
        newOp.setStateVector(remoteOperationHandler.getStateVector());
        addressDomain.getConcurrencyControl().setOtherSitesOperations(newOp);
        newOp = addressDomain.getConcurrencyControl().runOperation(newOp);
        if (!(newOp.isIdentity())){
            newOp.setStateVector(remoteOperationHandler.getStateVector());
            remoteOperationHandler.publishOperation(newOp);
        }
        initializeCount(); incrementCount();
    }

    public static synchronized void incrementCount() { timeCount++; }
    public static synchronized void initializeCount() { timeCount=0; }

        //Envia una operacion a la capa superior de la arquitectura
    public Operation sendOperationToApp(){
        if (localOperationHandler.getOperationEvents().isEmpty()){
            return null;
        }
        else{
            Operation operation = localOperationHandler.getOperationEvents().get(0);
            localOperationHandler.getOperationEvents().remove(operation);
            return operation;
        }
    }

    //Inicializa el Handler para operaciones remotas
    private void startRemoteHandler(InetSocketAddress localIp, InetSocketAddress bootaddress) throws Exception {

        remoteOperationHandler = new RemoteOperationHandler(localIp,bootaddress);

        start();

    }

    //Este Hilo se ocupara de obtener las Operaciones Remotas de la Red
    public void start(){
        Runnable remotePublisherHandler = new Runnable() {
            @Override
            public void run() {
                try {
                    while(true){
                        //Intenta obtener la proxima operacion remota, si no encuentra espera (simula latencia)
                        Operation op = remoteOperationHandler.getNextRemoteOperation();
                        if (op != null){
                            op = addressDomain.getConcurrencyControl().runOperation(op);
                            //if (!op.isIdentity()){
                            localOperationHandler.addOperationEvent(op);
                            //}
                            initializeCount(); incrementCount();
                        }
                        else{
                            sleep(4); //Con esto simulamos el tiempo de sincronia
                            if (timeCount > 0 && timeCount < 1000){
                                incrementCount();
                            }
                            else if (timeCount >= 1000){
                                //No hay conflictos porque si justo mientras se borra el buffer llega una operacion remota
                                //queda esperando en la red y al salir de este else luego lo captura
                                addressDomain.getConcurrencyControl().clearHistoryBuffer();
                                //System.out.println(timeCount);
                                initializeCount();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
        Thread threadWriter2 = new Thread(remotePublisherHandler);
        threadWriter2.start();
    }

}
