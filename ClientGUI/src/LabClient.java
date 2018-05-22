import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LabClient {

    private static final int PORT = 1488;
    private static final String HOST = "localhost";

    public static void main(String[] args){

        ArrayList<Citizens> mainSet;

        Socket socket;

        Scanner consoleInput = new Scanner(System.in);

        while(true) {

            try {
                socket = new Socket(HOST, PORT);

                if (socket.isConnected()) {

                    try (OutputStream out = socket.getOutputStream();
                         ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                        String line;

                        //System.out.println(1);

                        line = consoleInput.nextLine();

                        //System.out.println(2);

                        out.write(line.getBytes());
                        out.flush();

                        Object input = in.readObject();

                        try {

                            mainSet = (ArrayList<Citizens>) input;

                            mainSet.forEach(Citizens -> Citizens.printInfo());

                        } catch (ClassCastException e) {
                            //e.printStackTrace();
                            //System.out.println("Ошибка");

                            String error = (String) input;

                            if (error.equals("stop")) {
                                System.out.println(error);
                                break;
                            } else
                                System.out.println(error);

                        }

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }catch (EOFException e){

                    }catch (SocketException e){

                    }
                }
            } catch (ConnectException exc){

                System.out.println("Server is not responding...\n"
                + "Please wait...");

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
