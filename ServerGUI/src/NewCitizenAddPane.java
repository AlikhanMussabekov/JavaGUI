import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class NewCitizenAddPane extends Thread implements ActionListener {

    private JFrame parentFrame;
    private String name;
    private int age;
    private boolean check = false;

    ConcurrentSkipListSetCollection curSet;

    JFrame addFrame;

    JLabel nameLabel = new JLabel("Название:");
    JLabel ageLabel = new JLabel("Возраст:");
    JLabel checkLabel = new JLabel(" ");

    JTextField nameTF = new JTextField(10);
    JTextField ageTF = new JTextField(10);

    NewCitizenAddPane(JFrame parentFrame, ConcurrentSkipListSetCollection curSet){
        this.parentFrame = parentFrame;
        this.curSet = curSet;
    }


    public Citizens getCitizen(){
        return  new Citizens(name,age);
    }

    @Override
    public void run() {
        while(!check) {
            addFrame = new JFrame("Элемент");
            addFrame.setSize(300, 150);
            addFrame.setResizable(false);
            addFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            addFrame.setLocationRelativeTo(parentFrame);

            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

            JPanel namePanel = new JPanel(new FlowLayout());
            JPanel agePanel = new JPanel(new FlowLayout());

            namePanel.setSize(250, 40);
            agePanel.setSize(250, 40);


            JButton submitButton = new JButton("Добавить");

            namePanel.add(nameLabel);
            namePanel.add(nameTF);

            agePanel.add(ageLabel);
            agePanel.add(ageTF);

            namePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            ageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            checkLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            submitButton.addActionListener(this);

            contentPanel.add(namePanel);
            contentPanel.add(agePanel);
            contentPanel.add(checkLabel);
            contentPanel.add(submitButton);

            addFrame.add(contentPanel);

            addFrame.setVisible(true);
            //addFrame.pack();
            addFrame.addWindowListener(new WindowAdapter() {

                @Override
                public void windowDeactivated(WindowEvent wEvt) {
                    ((JFrame) wEvt.getSource()).dispose();
                }

            });
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (nameTF.getText().equals("") && ageTF.getText().equals("")){
            checkLabel.setText("Заполните поля");
            checkLabel.setForeground(Color.RED);
            nameTF.setBorder(BorderFactory.createLineBorder(Color.RED));
            ageTF.setBorder(BorderFactory.createLineBorder(Color.RED));
        }else if (nameTF.getText().equals("")){
            checkLabel.setText("Заполните поле");
            checkLabel.setForeground(Color.RED);
            nameTF.setBorder(BorderFactory.createLineBorder(Color.RED));
            ageTF.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }else if (ageTF.getText().equals("")){
            checkLabel.setText("Заполните поле");
            checkLabel.setForeground(Color.RED);
            nameTF.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            ageTF.setBorder(BorderFactory.createLineBorder(Color.RED));
        }else {

            try {
                name = nameTF.getText();
                age = Integer.valueOf(ageTF.getText());
                check = true;
                curSet.add_element(new Citizens(name,age));
                new InformationPane("Элемент успешно добавлен", addFrame, "OK");
                check = true;

            }catch(NumberFormatException ex){
                checkLabel.setText("Введите число");
                checkLabel.setForeground(Color.RED);
                nameTF.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                ageTF.setBorder(BorderFactory.createLineBorder(Color.RED));
            }
        }
    }
}
