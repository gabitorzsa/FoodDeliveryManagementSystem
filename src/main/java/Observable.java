import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Observable {

    protected PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void addListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }
}
