package fiuba.pyp;

import java.awt.*;
import java.util.EventListener;

public interface RemoteOperationListener
        extends EventListener
{
    // event dispatch methods
    public void dispatchNewOperationArrive(Event event);
}