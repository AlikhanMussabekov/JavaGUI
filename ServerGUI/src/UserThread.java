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

            try(InputStream in = socket.getInputStream();
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())
            ){

                if(!socket.isClosed()) {

                    System.out.println("Server is running...");
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    try {

                        byte[] buf = new byte[32 * 1024];
                        int readBytes = in.read(buf);

                        try {
                            String line = new String(buf, 0, readBytes);
                            cmdScanner = new Scanner(line);
                            cmdScanner.useDelimiter(" ");
                        }catch (StringIndexOutOfBoundsException e){
                            //System.out.println("11111");
                            //continue;
                        }


                        String curCmd = cmdScanner.next();

                        try{

                            String stringJson = cmdScanner.next();

                            try {
                                parser.nextCommand(curCmd, curSet, stringJson);
                                //curSet.writeElements();


                                out.writeObject(curSet.returnObjects());
                                out.flush();

                            } catch (ParseException e) {
                                e.printStackTrace();
                            } catch (InputException e) {
                                out.writeObject(e.info());
                                out.flush();
                            }catch (CommandException e){
                                out.writeObject(e.info());
                                out.flush();
                            }

                        }catch(NoSuchElementException e){
                            if (curCmd.equals("stop_app")){
                                System.out.println("Application stopped...");
                                curSet.save();
                                out.writeObject("stop");
                                out.flush();
                                //break;
                            }else{
                                //System.out.println(22222);
                                out.writeObject("JSON error...");
                                out.flush();
                            }
                        }

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
