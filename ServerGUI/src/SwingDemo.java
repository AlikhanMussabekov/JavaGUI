import javax.swing.*;
import java.awt.*;

public class SwingDemo {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ButtonDemo();
            }
        });
    }

}
