/*
 * Copyright (c) 2018. Alikhan Mussabekov
 * Gmail: alikhanmussabekov@gmail.com
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CitizenButton extends JButton {

    public Citizens citizens;
    private int x = (int) (Math.random() * 525), y = (int) (Math.random() * 600);

    CitizenButton(Citizens citizens){
        this.citizens = citizens;
        setBackground(citizens.getColor());
        setOpaque(true);
        setBorderPainted(false);
        setSize(120,120);
        setToolTipText(citizens.getName());
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
}
