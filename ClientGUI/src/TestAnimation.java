/*
 * Copyright (c) 2018. Alikhan Mussabekov
 * Gmail: alikhanmussabekov@gmail.com
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class TestAnimation extends JComponent implements ActionListener {
    private double scale;
    private Color color;
    private Timer timer;
    public double x =10;
    public double y =10;


    public TestAnimation(Color color, int delay) {
        scale = 1.0;
        timer = new Timer(delay, this);
        this.color = color;
        setPreferredSize(new Dimension(500, 500));
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        int width = 500;
        int height = 500;
        g.fillRect(0, 0, width, height);
        g2d.setColor(Color.black);
        g2d.drawRect(0, 0, width - 1, height - 1);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.scale(scale, scale);
        x++;
        y++;
        Ellipse2D el = new Ellipse2D.Double(x, y, 20, 20);
        g2d.fill(el);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Moving Circle");
                JPanel panel = new JPanel();

                final TestAnimation MovingCircle = new TestAnimation(Color.RED, 20);

                panel.add(MovingCircle);

                frame.getContentPane().add(panel);
                final JButton button = new JButton("Start");
                button.addActionListener(new ActionListener() {
                    private boolean pulsing = false;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (pulsing) {
                            pulsing = false;
                            MovingCircle.stop();
                            button.setText("Start");
                        } else {
                            pulsing = true;
                            MovingCircle.start();
                            button.setText("Stop");
                        }
                    }
                });
                panel.add(button);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 550);
                frame.setVisible(true);
            }
        });
    }
}
