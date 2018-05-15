import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ButtonDemo implements ActionListener{
    JLabel label;

    ButtonDemo(){
        JFrame jframe = new JFrame("Button example");
        jframe.setLayout(new FlowLayout());
        jframe.setSize(275, 100);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton buttonUp = new JButton("Up");
        JButton buttonDown = new JButton("Down");

        buttonUp.addActionListener(this);
        buttonDown.addActionListener(this);

        jframe.add(buttonUp);
        jframe.add(buttonDown);

        label = new JLabel("Press button");

        jframe.add(label);

        jframe.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("U")){
            label.setText("You pressed Up.");
        }else {
            label.setText("You pressed Down.");
        }
    }
}
