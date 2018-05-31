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
        private AnimationPanel animationPanel;
        private JPanel mainPanel;
        JCheckBox stPtrsbr = new JCheckBox("Санкт - Петербург");
        JCheckBox mscw = new JCheckBox("Москва");
        JFormattedTextField formattedTextField = new JFormattedTextField(df);


        SpinnerModel value =
                new SpinnerNumberModel(0, //initial value
                        0, //minimum value
                        100, //maximum value
                        1); //step

        JSpinner spinner = new JSpinner(value);
        Timer timer;
        int counter = 0;


        private ArrayList<CitizenButton> buttonsList = new ArrayList<>();
        private ArrayList<CitizenButton> filteredList = new ArrayList<>();

        ClientGUI(){

            update();
            for(Citizens citizens: citizensArrayList){
                CitizenButton curButton = new CitizenButton(citizens);
                System.out.println(curButton.getX() + " " + curButton.getY());
                curButton.setBounds(curButton.getX(),curButton.getY(),curButton.getWidth(),curButton.getHeight());
                buttonsList.add(curButton);
            }

            init();
        }

        private void init(){

            JFrame frame = new JFrame("Мусабеков Алихан, Мацкевич Игорь, Группа P3111");
            frame.setMinimumSize(new Dimension(1050,600));
            mainPanel = new JPanel(new GridLayout(1,2));
            animationPanel = new AnimationPanel();
            JPanel functionPanel = new JPanel();

            functionPanel.setLayout(new BoxLayout(functionPanel, BoxLayout.Y_AXIS));


            JButton animate = new JButton("Анимация");
            JButton update = new JButton("Обновить");

            animate.setAlignmentX(Component.CENTER_ALIGNMENT);
            update.setAlignmentX(Component.CENTER_ALIGNMENT);
            formattedTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
            spinner.setAlignmentX(Component.CENTER_ALIGNMENT);
            mscw.setAlignmentX(Component.CENTER_ALIGNMENT);
            stPtrsbr.setAlignmentX(Component.CENTER_ALIGNMENT);

            update.addActionListener(e -> {
                if (e.getActionCommand().equals("Обновить")){
                    update();

                    for(CitizenButton button: buttonsList){
                        animationPanel.remove(button);
                    }

                    buttonsList.clear();

                    for(Citizens citizens: citizensArrayList){
                        CitizenButton curButton = new CitizenButton(citizens);
                        System.out.println(curButton.getX() + " " + curButton.getY());
                        curButton.setBounds(curButton.getX(),curButton.getY(),curButton.getWidth(),curButton.getHeight());
                        buttonsList.add(curButton);
                    }

                    animate.setText("Анимация");
                    animate.setBackground(Color.GREEN);
                    animate.setOpaque(true);
                    animate.setBorderPainted(false);

                    timer.stop();

                    animationPanel.repaint();

                }
            });

            animate.setMinimumSize(new Dimension(100,20));
            animate.addActionListener(this);
            animate.setBackground(Color.GREEN);
            animate.setOpaque(true);
            animate.setBorderPainted(false);


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

            timer = new Timer(500, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if(counter < 3000){
                        for(CitizenButton button: filteredList){
                            setNewColor(button);
                        }
                        animationPanel.repaint();
                        counter += timer.getDelay();
                    }else if (counter < 7000){
                        for (CitizenButton button: filteredList){
                            resetColor(button);
                        }
                        System.out.println("7000");
                        animationPanel.repaint();
                        counter += timer.getDelay();
                    }else {
                        timer.stop();
                        counter = 0;
                        for (CitizenButton button: filteredList){
                            button.getCitizens().resetNewColor();
                        }
                        animate.setText("Анимация");
                        animate.setBackground(Color.GREEN);
                        animate.setOpaque(true);
                        animate.setBorderPainted(false);
                        animationPanel.repaint();
                    }

                }
            });

            frame.add(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        }
        public void setNewColor(CitizenButton button){
            button.setBackground(button.getCitizens().getNewColor());
            button.setOpaque(true);
            button.setBorderPainted(false);
        }

        public void resetColor(CitizenButton button){
            button.setBackground(button.getCitizens().getOldColor());
            button.setOpaque(true);
            button.setBorderPainted(false);
        }


    @Override
    public void actionPerformed (ActionEvent e){
        if (e.getActionCommand().equals("Анимация") || e.getActionCommand().equals("Стоп")) {

            JButton buttonSource = (JButton) e.getSource();

            if (check) {
                buttonSource.setText("Анимация");
                buttonSource.setBackground(Color.GREEN);
                buttonSource.setOpaque(true);
                buttonSource.setBorderPainted(false);
                timer.stop();
                check = !check;
                for (CitizenButton button : buttonsList) {
                    button.setBackground(button.getCitizens().getColor());
                    button.setOpaque(true);
                    button.setBorderPainted(false);
                }
            } else {

                counter = 0;

                buttonSource.setText("Стоп");
                buttonSource.setBackground(Color.RED);
                buttonSource.setOpaque(true);
                buttonSource.setBorderPainted(false);

                filteredList.clear();

                //filteredList = buttonsList;

                if (mscw.isSelected()) {
                    for (CitizenButton button : buttonsList) {
                        if (button.getCitizens().getCity().equals("Москва") &&
                                button.getCitizens().creationDate().before((Date) formattedTextField.getValue()) &&
                                (Integer)spinner.getValue() < button.getCitizens().getAge()) {
                            filteredList.add(button);
                        }
                    }
                }

                if (stPtrsbr.isSelected()) {
                    for (CitizenButton button : buttonsList) {
                        if (button.getCitizens().getCity().equals("Санкт-Петербург") &&
                                button.getCitizens().creationDate().before((Date) formattedTextField.getValue()) &&
                                (Integer)spinner.getValue() < button.getCitizens().getAge()) {
                            filteredList.add(button);
                        }
                    }
                }

                /*if (formattedTextField.isEditValid()) {
                    for (CitizenButton button : filteredList) {
                        if (button.getCitizens().creationDate().before((Date) formattedTextField.getValue())) {
                            filteredList.remove(button);
                        }
                    }
                }
*/
                    /*for (CitizenButton button : filteredList) {
                        if ((Integer)spinner.getValue() < button.getCitizens().getAge()) {
                            filteredList.remove(button);
                        }
                    }*/


                    /*for(CitizenButton button: buttonsList) {
                        if (mscw.isSelected() && button.getCitizens().getCity().equals("Москва")) {
                            filteredList.add(button);
                        } else if (stPtrsbr.isSelected() && button.getCitizens().getCity().equals("Санкт-Петербург")) {
                            filteredList.add(button);
                        } else if (formattedTextField.isEditValid()) {
                            if (button.getCitizens().creationDate().after((Date) formattedTextField.getValue())) {
                                filteredList.add(button);
                            }
                        } else if ((Integer) spinner.getValue() != 0) {
                            if ((Integer) spinner.getValue() > button.getCitizens().getAge()) {
                                filteredList.add(button);
                            }
                        }
                    }*/

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
                g2d.setColor(Color.white);
                g.fillRect(0, 0, getWidth(), getHeight());
                g2d.setColor(Color.black);
                g2d.drawRect(0, 0, getWidth(), getHeight());

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

                //new InformationPane("Сервер не отвечает, подождите", null, "Error" );

                JOptionPane.showMessageDialog(null, "Сервер не отвечает, попробуйте позже");

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
