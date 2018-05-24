import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class LabClient {

    private static final int PORT = 1488;
    private static final String HOST = "localhost";
    private static final DateFormat df = new SimpleDateFormat("yyyy/mm/dd");
    private static ArrayList<Citizens> citizensArrayList;

    public static void main (String[] args){
        SwingUtilities.invokeLater(ClientGUI::new);
    }

    public static class ClientGUI implements ActionListener {



        private boolean check = false;
        private boolean animation = false;
        private AnimationPanel animationPanel;
        private JPanel mainPanel;
        JCheckBox stPtrsbr = new JCheckBox("Санкт - Петербург");
        JCheckBox mscw = new JCheckBox("Москва");
        JFormattedTextField formattedTextField = new JFormattedTextField(df);
        JSpinner spinner = new JSpinner();
        Timer timer;


        private ArrayList<CitizenButton> buttonsList = new ArrayList<>();

        ClientGUI(){

            update();
            for(Citizens citizens: citizensArrayList){
                CitizenButton curButton = new CitizenButton(citizens);
                //curButton.setLocation((int)Math.random()*10,(int)Math.random()*10);
                System.out.println(curButton.getX() + " " + curButton.getY());
                curButton.setBounds(curButton.getX(),curButton.getY(),curButton.getWidth(),curButton.getHeight());
                buttonsList.add(curButton);
            }

            init();
        }

        private void init(){

            JFrame frame = new JFrame("Клиент");
            frame.setMinimumSize(new Dimension(1050,600));
            mainPanel = new JPanel(new GridLayout(1,2));
            animationPanel = new AnimationPanel();
            JPanel functionPanel = new JPanel();

            JButton animate = new JButton("Анимация");
            JButton update = new JButton("Обновить");

            update.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().equals("Обновить")){
                        update();

                        for(CitizenButton button: buttonsList){
                            animationPanel.remove(button);
                        }

                        buttonsList.clear();

                        for(Citizens citizens: citizensArrayList){
                            CitizenButton curButton = new CitizenButton(citizens);
                            //curButton.setLocation((int)Math.random()*10,(int)Math.random()*10);
                            System.out.println(curButton.getX() + " " + curButton.getY());
                            curButton.setBounds(curButton.getX(),curButton.getY(),curButton.getWidth(),curButton.getHeight());
                            buttonsList.add(curButton);
                        }
                        animationPanel.repaint();
                        mainPanel.repaint();
                        animationPanel.revalidate();
                        mainPanel.revalidate();

                    }
                }
            });

            animate.setMinimumSize(new Dimension(100,20));
            animate.addActionListener(this);
            animate.setBackground(Color.GREEN);
            animate.setOpaque(true);
            animate.setBorderPainted(false);

            formattedTextField.setColumns(10);
            spinner.setMinimumSize(new Dimension(20,20));

            try {
                MaskFormatter dateMask = new MaskFormatter("####/##/##");
                dateMask.install(formattedTextField);
            } catch (ParseException ex) {
                System.out.println("date error");
            }

            functionPanel.add(animate);
            functionPanel.add(update);
            functionPanel.add(stPtrsbr);
            functionPanel.add(mscw);
            functionPanel.add(formattedTextField);
            functionPanel.add(spinner);

            mainPanel.add(animationPanel);
            mainPanel.add(functionPanel);


            frame.add(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        }
        public void setGreenColor(JButton button){
            button.setBackground(Color.GREEN);
            button.setOpaque(true);
            button.setBorderPainted(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Анимация") || e.getActionCommand().equals("Стоп")){

                JButton buttonSource = (JButton) e.getSource();

                if (check) {
                    buttonSource.setText("Анимация");
                    buttonSource.setBackground(Color.GREEN);
                    buttonSource.setOpaque(true);
                    buttonSource.setBorderPainted(false);
                    timer.stop();
                    check = !check;
                    for(CitizenButton button: buttonsList){
                        button.setBackground(button.getCitizens().getColor());
                        button.setOpaque(true);
                        button.setBorderPainted(false);
                    }
                }else{
                    buttonSource.setText("Стоп");
                    buttonSource.setBackground(Color.RED);
                    buttonSource.setOpaque(true);
                    buttonSource.setBorderPainted(false);
                    timer = new Timer(3000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            for(CitizenButton button: buttonsList) {
                                if (mscw.isSelected() && button.getCitizens().getCity().equals("Москва")) {
                                    setGreenColor(button);
                                }else if (stPtrsbr.isSelected() && button.getCitizens().getCity().equals("Санкт-Петербург")){
                                    setGreenColor(button);
                                }else if(formattedTextField.isEditValid()){
                                    //System.out.println(formattedTextField.getValue().getClass());
                                    if(button.getCitizens().creationDate().after((Date) formattedTextField.getValue())){
                                        //System.out.println("afterr");
                                        setGreenColor(button);
                                    }
                                }else if ( (Integer) spinner.getValue()!=0){
                                    if ((Integer) spinner.getValue() > button.getCitizens().getAge()){
                                        setGreenColor(button);
                                    }
                                }
                            }
                        }
                    });
                    timer.start();
                    check = !check;
                }
                animationPanel.repaint();


            }
        }

        class AnimationPanel extends JPanel implements ActionListener{

            public AnimationPanel() {
                //setLayout(new GridLayout(buttonsList.size()/3,3));
                setLayout(null);
            }

            @Override
            public void paintComponent(Graphics g) {

                Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                /*g2d.setColor(Color.white);
                g.fillRect(0, 0, getWidth(), getHeight());
                g2d.setColor(Color.black);
                g2d.drawRect(0, 0, getWidth(), getHeight());*/

                for(CitizenButton button: buttonsList){
                    this.add(button);
                }

            }

            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }



        }
    }

    public static void update(){

        Socket socket = null;

        Scanner consoleInput = new Scanner(System.in);


            try {
                socket = new Socket(HOST, PORT);

                if (socket.isConnected()) {

                    try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                        Object input = in.readObject();

                        try {

                            citizensArrayList = (ArrayList<Citizens>) input;

                            citizensArrayList.forEach(Citizens -> Citizens.printInfo());

                            //new ClientGUI(mainSet);

                        } catch (ClassCastException e) {

                            String error = (String) input;

                            if (error.equals("stop")) {
                                System.out.println(error);
                            } else
                                System.out.println(error);

                        }

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }catch (EOFException | SocketException ignored){

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
            }finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }
}
