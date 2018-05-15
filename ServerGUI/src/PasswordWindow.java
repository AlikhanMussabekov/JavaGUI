import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PasswordWindow extends JFrame implements ActionListener {

    JTextField passwordTextField;
    JButton enterButton;
    JLabel greetingLabel,passwordCheckLabel;

    JFrame passwordWindowfFrame = new JFrame("Вход");

    PasswordWindow(){

        passwordWindowfFrame.setSize(300,150);
        passwordWindowfFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        passwordWindowfFrame.setMinimumSize(new Dimension(300,150));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        passwordTextField = new JPasswordField(10);
        passwordTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passwordTextField.setMaximumSize(new Dimension(150,20));

        passwordTextField.setActionCommand("textFieldAction");

        enterButton = new JButton("Войти");

        passwordTextField.addActionListener(this);
        enterButton.addActionListener(this);

        greetingLabel = new JLabel("Добро пожаловать!");
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

        passwordWindowfFrame.add(panel);

        passwordWindowfFrame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals("Войти")){

            String enteredPassword = passwordTextField.getText();
            if(enteredPassword.equals("newpass")){
                passwordCheckLabel.setText("Верный пароль");
                passwordCheckLabel.setForeground(Color.GREEN);
                passwordTextField.setBorder(BorderFactory.createLineBorder(Color.GREEN));
                passwordWindowfFrame.dispose();
            }else{
                passwordTextField.setText("");
                passwordCheckLabel.setText("Неверный пароль");
                passwordCheckLabel.setForeground(Color.RED);
                passwordTextField.setBorder(BorderFactory.createLineBorder(Color.RED));
            }
        }else {
            //passwordCheckLabel.setText("Введите пароль");
            //passwordCheckLabel.;
        }
    }
}
