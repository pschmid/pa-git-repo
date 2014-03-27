package fiuba.pyp;

import java.util.TreeSet;

/**
 * Created by pyp on 20/03/14.
 *
 * Esta Clase implementa el modelo XOTDM que extiende el modelo linear de direccionameinto
 * de datos a una jerarquia de dominios, un arbol
 *
 */
public class AddressDomain {

    /** Feature No Implementado
    *  Deberia haber un arbol de Dominios donde cada hoja y nodo intermedio tiene
    *  su propio History Buffer
    *
    *    private TreeMap<K,V> tree;
    **/
    private ConcurrencyControl concurrencyControl;

    private int levels;

    private static AddressDomain INSTANCE = null;
    // Private constructor suppresses
    private AddressDomain(){
        initialize();
    }

    // creador sincronizado para protegerse de posibles problemas  multi-hilo
    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AddressDomain();
        }
    }

    public static AddressDomain getInstance() {
        createInstance();
        return INSTANCE;
    }

    private void initialize() {
        this.concurrencyControl = ConcurrencyControl.getInstance();
        this.levels = 1;
    }

    public int getLevels() {
        return levels;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    private ConcurrencyControl getConcurrencyControl() {
        return concurrencyControl;
    }

    public Operation getPrimitiveOperation(Operation newOp){
        return this.getConcurrencyControl().runOperation(newOp);
    }



}
