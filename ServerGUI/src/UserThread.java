import org.json.simple.parser.ParseException;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserThread extends Thread {

    public static final int PORT = 1488;

    ConcurrentSkipListSetCollection curSet = null;
    JsonCommandParser parser = new JsonCommandParser();
    Scanner cmdScanner;

    Socket socket = null;

    public UserThread(ConcurrentSkipListSetCollection curSet, Socket socket) {
        this.curSet = curSet;
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("new Thread");

        try {

            try(ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())
            ){

                if(!socket.isClosed()) {

                    System.out.println("Server is running...");

                    try {

                        out.writeObject(curSet.returnObjects());
                        out.flush();

                    }catch(EOFException e) {
                        System.out.println("EOFException");
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
