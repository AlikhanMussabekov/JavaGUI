import com.sun.xml.internal.bind.v2.TODO;

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

public class LabClient {

    private static final int PORT = 1488;
    private static final String HOST = "localhost";
    private static ArrayList<Citizens> citizensArrayList;
    private static ResourceManager resourceManager = new ResourceManager("data","ru");
    private static DateFormat df = new SimpleDateFormat(resourceManager.getString("filter.year.format"));


    public static void main (String[] args){
        SwingUtilities.invokeLater(ClientGUI::new);
    }

    public static class ClientGUI implements ActionListener {



        private boolean check = false;
        private AnimationPanel animationPanel;
        private JPanel mainPanel;
        JFormattedTextField formattedTextField = new JFormattedTextField(df);
        MaskFormatter dateMask;

        JCheckBox stPtrsbr = new JCheckBox(resourceManager.getString("filter.city.stptrsbrg"));
        JCheckBox mscw = new JCheckBox(resourceManager.getString("filter.city.moscow"));

        JMenuItem estonian;
        JMenuItem russian;
        JMenuItem spanish;
        JMenuItem ukranian;

        JButton animate;
        JButton update;

        JMenu menu;

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

            JMenuBar menuBar = new JMenuBar();
            menu = new JMenu(resourceManager.getString("system.language"));

            estonian = new JMenuItem(resourceManager.getString("system.language.estonian"));
            russian = new JMenuItem(resourceManager.getString("system.language.russian"));
            spanish = new JMenuItem(resourceManager.getString("system.language.spanish"));
            ukranian = new JMenuItem(resourceManager.getString("system.language.ukranian"));

            estonian.addActionListener(this);
            russian.addActionListener(this);
            spanish.addActionListener(this);
            ukranian.addActionListener(this);

            menu.add(estonian);
            menu.add(russian);
            menu.add(spanish);
            menu.add(ukranian);

            menuBar.add(menu);

            functionPanel.setLayout(new BoxLayout(functionPanel, BoxLayout.Y_AXIS));


            animate = new JButton(resourceManager.getString("filter.animation.start"));
            update = new JButton(resourceManager.getString("filter.update.collection"));

            JPanel functionButtonsPanel = new JPanel(new FlowLayout());
            functionButtonsPanel.add(update);
            functionButtonsPanel.add(animate);

            update.addActionListener(e -> {
                if (e.getActionCommand().equals(resourceManager.getString("filter.update.collection"))){
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

                    animate.setText(resourceManager.getString("filter.animation.start"));
                    animate.setBackground(Color.GREEN);
                    animate.setOpaque(true);
                    animate.setBorderPainted(false);

                    timer.stop();

                    animationPanel.repaint();

                }
            });

            //update.setMinimumSize(new Dimension(100,20));

            animate.setMinimumSize(new Dimension(100,20));
            animate.addActionListener(this);
            animate.setBackground(Color.GREEN);
            animate.setOpaque(true);
            animate.setBorderPainted(false);

            formattedTextField.setColumns(10);
            formattedTextField.setMaximumSize(new Dimension(300,150));

            spinner.setMaximumSize(new Dimension(300,150));

            try {
                dateMask = new MaskFormatter(resourceManager.getString("filter.year.mask"));
                dateMask.install(formattedTextField);
            } catch (ParseException ex) {
                System.out.println("date error");
            }

            JPanel checkboxPanel = new JPanel(new FlowLayout());
            checkboxPanel.add(mscw);
            checkboxPanel.add(stPtrsbr);

            functionPanel.add(checkboxPanel);
            functionPanel.add(formattedTextField);
            functionPanel.add(spinner);
            functionPanel.add(functionButtonsPanel);

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
                        animate.setText(resourceManager.getString("filter.animation.start"));
                        animate.setBackground(Color.GREEN);
                        animate.setOpaque(true);
                        animate.setBorderPainted(false);
                        animationPanel.repaint();
                    }

                }
            });

            frame.setJMenuBar(menuBar);
            frame.add(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        }
        void setNewColor(CitizenButton button){
            button.setBackground(button.getCitizens().getNewColor());
            button.setOpaque(true);
            button.setBorderPainted(false);
        }

        void resetColor(CitizenButton button){
            button.setBackground(button.getCitizens().getOldColor());
            button.setOpaque(true);
            button.setBorderPainted(false);
        }


    @Override
    public void actionPerformed (ActionEvent e){
        if (e.getActionCommand().equals(resourceManager.getString("filter.animation.start")) || e.getActionCommand().equals(resourceManager.getString("filter.animation.stop"))) {

            JButton buttonSource = (JButton) e.getSource();

            if (check) {
                buttonSource.setText(resourceManager.getString("filter.animation.start"));
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

                filteredList.clear();

                if (formattedTextField.getValue()==null ){
                    JOptionPane.showMessageDialog(null, resourceManager.getString("filter.error.year"));
                }else{

                    if (mscw.isSelected()) {
                        for (CitizenButton button : buttonsList) {
                            if (button.getCitizens().getCity().equals(resourceManager.getString("filter.city.moscow")) &&
                                    button.getCitizens().citizensDate().before((Date) formattedTextField.getValue()) &&
                                    (Integer)spinner.getValue() < button.getCitizens().getAge()) {
                                filteredList.add(button);
                            }
                        }
                    }

                    if (stPtrsbr.isSelected()) {
                        for (CitizenButton button : buttonsList) {
                            if (button.getCitizens().getCity().equals(resourceManager.getString("filter.city.stptrsbrg")) &&
                                    button.getCitizens().citizensDate().before((Date) formattedTextField.getValue()) &&
                                    (Integer)spinner.getValue() < button.getCitizens().getAge()) {
                                filteredList.add(button);
                            }
                        }
                    }

                    timer.start();
                    check = !check;
                    buttonSource.setText(resourceManager.getString("filter.animation.stop"));
                    buttonSource.setBackground(Color.RED);
                    buttonSource.setOpaque(true);
                    buttonSource.setBorderPainted(false);
                }





            }
            animationPanel.repaint();


        }

        else if (e.getActionCommand().equals(resourceManager.getString("system.language.russian"))){
            resourceManager.changeLocale("ru");
            changeLanguage();
        }else if (e.getActionCommand().equals(resourceManager.getString("system.language.estonian"))){
            resourceManager.changeLocale("et");
            changeLanguage();
        }else if (e.getActionCommand().equals(resourceManager.getString("system.language.ukranian"))){
            resourceManager.changeLocale("uk");
            changeLanguage();
        } else if (e.getActionCommand().equals(resourceManager.getString("system.language.spanish"))){
            resourceManager.changeLocale("es");
            changeLanguage();
        }
    }

        private void changeLanguage() {
            mscw.setText(resourceManager.getString("filter.city.moscow"));
            stPtrsbr.setText(resourceManager.getString("filter.city.stptrsbrg"));
            update.setText(resourceManager.getString("filter.update.collection"));
            animate.setText(resourceManager.getString("filter.animation.start"));
            menu.setText(resourceManager.getString("system.language"));
            russian.setText(resourceManager.getString("system.language.russian"));
            estonian.setText(resourceManager.getString("system.language.estonian"));
            ukranian.setText(resourceManager.getString("system.language.ukranian"));
            spanish.setText(resourceManager.getString("system.language.spanish"));
            df = new SimpleDateFormat(resourceManager.getString("filter.year.format"));
            try {
                dateMask.setMask(resourceManager.getString("filter.year.mask"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            formattedTextField.revalidate();
            formattedTextField.repaint();
        }

        class AnimationPanel extends JPanel implements ActionListener{

            AnimationPanel() {
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

    static void update(){

        Socket socket = null;

            try {
                socket = new Socket(HOST, PORT);

                if (socket.isConnected()) {

                    try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                        Object input = in.readObject();

                        try {

                            citizensArrayList = (ArrayList<Citizens>) input;

                            citizensArrayList.forEach(c -> c.printInfo());

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

                JOptionPane.showMessageDialog(null, resourceManager.getString("server.notresponding"));

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
