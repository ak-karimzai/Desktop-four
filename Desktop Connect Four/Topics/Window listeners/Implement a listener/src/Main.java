import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class WindowOpeningAdapter extends WindowAdapter {
    // override an event called after window opening
    public void windowOpened(WindowEvent e) {
        System.out.println("Window is opened");
    }
}