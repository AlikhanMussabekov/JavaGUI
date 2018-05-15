import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MultipleUserServer {
    public static final int PORT = 1488;

    public static void main(String[] args) throws ClassNotFoundException {

        ServerSocket serverSocket = null;
        Socket socket = null;

        ConcurrentSkipListSetCollection curSet = new ConcurrentSkipListSetCollection();
        JsonCommandParser parser = new JsonCommandParser();
        Scanner cmdScanner;

        String pathStr = null;
        String check = null;

        Scanner path = new Scanner(System.getenv("PATH"));

        path.useDelimiter(":");

        while(path.hasNext()){

            pathStr = path.next();

            if (pathStr.substring(pathStr.length()-4,pathStr.length()).equals("lab5"))
                check = pathStr;

        }
        path.close();

/*
        Scanner path = new Scanner(System.getenv("LAB"));
        check = path.next();
*/

        try {
            curSet.setPath(check+"/str.csv");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        curSet.readElements();
        curSet.writeElements();

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true){
            try {
                System.out.println("Waiting for connection...");

                socket = serverSocket.accept();

                System.out.println("Connected: " + socket.getInetAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }

            new Thread(new UserThread(curSet,socket)).start();
        }

    }
}