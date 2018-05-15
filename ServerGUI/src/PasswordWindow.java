import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

class PasswordWindow extends JFrame implements ActionListener {

    private static final String USERNAME = ".user.name";
    private static final String PATH = "/programming/Java/lab5/.password";

    private JTextField passwordTextField;
    private JLabel passwordCheckLabel;

    PasswordWindow(){

        super("Вход");

        setSize(300,150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(300,150));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        passwordTextField = new JPasswordField(10);
        passwordTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passwordTextField.setMaximumSize(new Dimension(150,20));

        passwordTextField.setActionCommand("textFieldAction");

        JButton enterButton = new JButton("Войти");

        passwordTextField.addActionListener(this);
        enterButton.addActionListener(this);

        JLabel greetingLabel = new JLabel("Добро пожаловать!");
        greetingLabel.setFont(new Font("Courier New", Font.ITALIC, 25));

        passwordCheckLabel = new JLabel();

        greetingLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        passwordTextField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        passwordCheckLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        enterButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        panel.add(greetingLabel);
        panel.add(passwordTextField);
        panel.add(passwordCheckLabel);
        panel.add(enterButton);

        add(panel);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals("Войти")){

            try {

                String enteredPassword = passwordTextField.getText();


                Scanner hashIn = new Scanner(new File(PATH));
                int hcodePassFile = hashIn.nextInt();


                if(enteredPassword.concat(USERNAME).hashCode() == hcodePassFile){
                    passwordCheckLabel.setText("Верный пароль");
                    passwordCheckLabel.setForeground(Color.GREEN);
                    passwordTextField.setBorder(BorderFactory.createLineBorder(Color.GREEN));
                    dispose();
                    new TreeSelectionModelTest();
                }else{
                    passwordTextField.setText("");
                    passwordCheckLabel.setText("Неверный пароль");
                    passwordCheckLabel.setForeground(Color.RED);
                    passwordTextField.setBorder(BorderFactory.createLineBorder(Color.RED));
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }else {
            //passwordCheckLabel.setText("Введите пароль");
            //passwordCheckLabel.;
        }
    }
}
