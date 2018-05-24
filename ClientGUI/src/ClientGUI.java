import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

public class ClientGUI implements ActionListener {

    private static final DateFormat df = new SimpleDateFormat("yyyy/mm/dd");


    private boolean check = false;
    private boolean animation = false;
    private AnimationPanel animationPanel;
    JCheckBox stPtrsbr = new JCheckBox("Санкт - Петербург");
    JCheckBox mscw = new JCheckBox("Москва");
    JFormattedTextField formattedTextField = new JFormattedTextField(df);
    JSpinner spinner = new JSpinner();
    Timer timer;


    private ArrayList<Citizens> citizensArrayList;
    private ArrayList<CitizenButton> buttonsList = new ArrayList<>();

    ClientGUI(ArrayList<Citizens> citizensArrayList){
        this.citizensArrayList = citizensArrayList;

        for(Citizens citizens: this.citizensArrayList){
            buttonsList.add(new CitizenButton(citizens));
        }
        init();
    }

    private void init(){
        JFrame frame = new JFrame("Client");
        frame.setMinimumSize(new Dimension(370,600));
        JPanel mainPanel = new JPanel(new GridLayout(1,2));
        animationPanel = new AnimationPanel();
        JPanel functionPanel = new JPanel();

        JButton animate = new JButton("Анимация");

        animate.addActionListener(this);

        formattedTextField.setColumns(20);

        try {
            MaskFormatter dateMask = new MaskFormatter("####/##/##");
            dateMask.install(formattedTextField);
        } catch (ParseException ex) {
            System.out.println("errrrrrrrrrr");
        }

        functionPanel.add(animate);
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
                timer.stop();
                check = !check;
                for(CitizenButton button: buttonsList){
                    button.setBackground(button.getCitizens().getColor());
                    button.setOpaque(true);
                    button.setBorderPainted(false);
                }
            }else{
                buttonSource.setText("Стоп");
                timer = new Timer(3000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for(CitizenButton button: buttonsList) {
                            if (mscw.isSelected() && button.getCitizens().getCity().equals("Москва")) {
                                setGreenColor(button);
                            }else if (stPtrsbr.isSelected() && button.getCitizens().getCity().equals("Санкт-Петербург")){
                                setGreenColor(button);
                            }else if(formattedTextField.isEditValid()){
                                System.out.println(formattedTextField.getValue().getClass());
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

        private int y = 20;
        private int x = 20;

        public AnimationPanel() {

            setLayout(new GridLayout(buttonsList.size()/3,3));
        }

        @Override
        public void paintComponent(Graphics g) {
            int widthCount = getWidth()/90 - 1 ;
            int counter = 0;

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


        }



    }
}
