import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class PasswordWindow implements ActionListener {

    JTextField passwordTextField;
    JButton enterButton;
    JLabel greetingLabel,passwordCheckLabel;

    PasswordWindow(){
        JFrame passwordWindowfFrame = new JFrame("Entrance");


        passwordWindowfFrame.setLayout(new FlowLayout());

        passwordWindowfFrame.setSize(300,150);

        passwordWindowfFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        passwordTextField = new JPasswordField(10);
        passwordTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        passwordTextField.setActionCommand("textFieldAction");

        enterButton = new JButton("Enter");

        passwordTextField.addActionListener(this);
        enterButton.addActionListener(this);

        greetingLabel = new JLabel("Добро пожаловать!");
        passwordCheckLabel = new JLabel();

        greetingLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        passwordWindowfFrame.add(greetingLabel);
        passwordWindowfFrame.add(passwordTextField);
        passwordWindowfFrame.add(passwordCheckLabel);
        passwordWindowfFrame.add(enterButton);

        passwordWindowfFrame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals("Enter")){

            String enteredPassword = passwordTextField.getText();
            if(enteredPassword.equals("newpass")){
                passwordCheckLabel.setText("Верный пароль");
                passwordTextField.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            }else{
                passwordTextField.setText("");
                passwordCheckLabel.setText("Неверный пароль");
                passwordCheckLabel.setForeground(Color.RED);
                passwordTextField.setBorder(BorderFactory.createLineBorder(Color.RED));
            }
        }else {
            passwordCheckLabel.setText("Введите пароль");
        }
    }
}
