import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CommandThread extends Thread{

    ArrayList<Citizens> citizens;
    ObjectOutputStream out;

    public CommandThread(ArrayList<Citizens> citizens,ObjectOutputStream out) {
        this.citizens = citizens;
        this.out = out;
    }

    @Override
    public void run() {
        System.out.println("new request");
        try {
            out.writeObject(citizens);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
