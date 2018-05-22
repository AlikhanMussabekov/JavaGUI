import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ClientGUI{

    private ArrayList<Citizens> citizensArrayList;

    ClientGUI(ArrayList<Citizens> citizensArrayList){
        this.citizensArrayList = citizensArrayList;
        init();
    }

    private void init(){
        JFrame frame = new JFrame("Клиент");

        JPanel panel = new JPanel(new GridLayout());

        frame.setVisible(true);
    }
}
