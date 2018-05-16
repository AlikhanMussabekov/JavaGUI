import javax.swing.*;
import java.awt.*;

public class NewCitizenAddPane {

    private JFrame parentFrame;

    NewCitizenAddPane(){
        init();
    }

    NewCitizenAddPane(JFrame parentFrame){
        this.parentFrame = parentFrame;
        init();
    }

    public void init (){
        JFrame mainFrame = new JFrame("Добавить");
        mainFrame.setSize(300,150);
        //mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel(new FlowLayout());
        JPanel agePanel = new JPanel(new FlowLayout());

        namePanel.setSize(250,50);
        agePanel.setSize(2500,50);

        JLabel nameLabel = new JLabel("Название:");
        JLabel ageLabel = new JLabel("Возраст:");

        JTextField nameTF = new JTextField(10);
        JTextField ageTF = new JTextField(10);


        JButton submitButton = new JButton("Добавить");

        namePanel.add(nameLabel);
        namePanel.add(nameTF);

        agePanel.add(ageLabel);
        agePanel.add(ageTF);

        namePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        ageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(namePanel);
        contentPanel.add(agePanel);
        contentPanel.add(submitButton);

        mainFrame.add(contentPanel);

        mainFrame.setVisible(true);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(NewCitizenAddPane::new);
    }
}
