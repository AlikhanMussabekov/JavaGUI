/*
 * Copyright (c) 2018. Alikhan Mussabekov
 * Gmail: alikhanmussabekov@gmail.com
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CitizenButton extends JButton implements MouseListener {

    public Citizens citizens;

    CitizenButton(Citizens citizens){
        this.citizens = citizens;
        setBackground(citizens.getColor());
        setOpaque(true);
        setBorderPainted(false);
        addMouseListener(this);
        setSize(60,60);
        repaint();
    }

    public Citizens getCitizens() {
        return citizens;
    }

    @Override

    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        button.setText(citizens.getName());
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        button.setText("");
    }
}
