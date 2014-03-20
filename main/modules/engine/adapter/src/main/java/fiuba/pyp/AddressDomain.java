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

    public AddressDomain(int levels) {
        this.concurrencyControl = ConcurrencyControl.getInstance();
        this.levels = levels;
    }

    public ConcurrencyControl getConcurrencyControl() {
        return concurrencyControl;
    }

    public Operation getPrimitiveOperation(Operation newOp){
        this.getConcurrencyControl().run(newOp);
        return this.getConcurrencyControl().getLastOperationExecuted();
    }



}
