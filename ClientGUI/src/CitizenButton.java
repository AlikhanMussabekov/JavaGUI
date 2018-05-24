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
    private int x = 0 + (int) (Math.random() * 525), y = 0+ (int) (Math.random()* 600);

    CitizenButton(Citizens citizens){
        this.citizens = citizens;
        setBackground(citizens.getColor());
        setOpaque(true);
        setBorderPainted(false);
        addMouseListener(this);
        setSize(120,120);
    }

    public Citizens getCitizens() {
        return citizens;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
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
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JButton button = (JButton) e.getSource();
        button.setText("");
    }
}
