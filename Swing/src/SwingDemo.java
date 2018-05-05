import javax.swing.*;

public class SwingDemo {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SwingDemo();
            }
        });
    }

    SwingDemo(){
        JFrame jframe = new JFrame("First app");

        jframe.setSize(275, 100);

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel jlabel = new JLabel("Hello World!");

        jframe.add(jlabel);

        jframe.setVisible(true);
    }
}
