/*
 * Copyright (c) 2018. Alikhan Mussabekov
 * Gmail: alikhanmussabekov@gmail.com
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RectangleComponent extends Rectangle.Double implements MouseListener{

    int x = 0, y = 0;
    Color color;
    Rectangle.Double rect = new Rectangle.Double(x,y,50,50);

    RectangleComponent(double x, double y, double w, double h) {
        super(x, y, w, h);
    }

    public void setRect(int x, int y, Color color){
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setColor(color);
        g2d.fill(rect);
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
        System.out.println(11);
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
