package org.kwt.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;

import com.amazon.kindle.kindlet.ui.KPanel;

public class KWTScrollPanel extends KPanel {
    private static final long serialVersionUID = 323032064088045342L;
    
    private static final int LEFT_GAP = 10;
    private static final int SCROLLBAR_WIDTH = 20;
    private static final int SCROLLBAR_LEFT_GAP = 10;
    private static final int SCROLLBAR_RIGHT_GAP = 10;
    private static final int RIGHT_GAP = SCROLLBAR_LEFT_GAP + SCROLLBAR_WIDTH + SCROLLBAR_RIGHT_GAP;
    private static final int TOP_GAP = 10;
    private static final int BOTTOM_GAP = 10;
    
    /**
     * The height of the viewport displaying the underlying KPanel. Since KWTScrollPanels only
     * support vertical scrolling, only a viewportHeight is needed, and not a viewportWidth.
     */
    private int viewportHeight;
    
    /** 
     * How far down the component the scroll bar is. 0 represents the very top,
     * 1 represents the very bottom. 
     */
    private double scrollLevel;

    public KWTScrollPanel(int height) {
        scrollLevel = 0.0;
        viewportHeight = height;
    }
    
    public void setSize(Dimension d) {
        setSize(d.width, d.height);
    }
    
    public void setSize(int width, int height) {
        viewportHeight = height;
        
        super.setSize(width - (LEFT_GAP + RIGHT_GAP), super.getSize().height);
    }
    
    public Dimension getSize() {
        Dimension d = super.getSize();
        d.width += (LEFT_GAP + RIGHT_GAP);
        d.height =  viewportHeight + (TOP_GAP + BOTTOM_GAP);
        return d;
    }
    
    public Dimension getSize(Dimension rv) {
        super.getSize(rv);
        rv.width += (LEFT_GAP + RIGHT_GAP);
        rv.height =  viewportHeight + (TOP_GAP + BOTTOM_GAP);
        return rv;
    }
    
    public void paint(Graphics g) {
        Shape clip = g.getClip();
        
        // Draw the outer border
        g.drawRect(0, 0, super.getMinimumSize().width + (LEFT_GAP + RIGHT_GAP), viewportHeight + (TOP_GAP + BOTTOM_GAP));
        
        // Clip g to viewport
        g.clipRect(LEFT_GAP, TOP_GAP, super.getMinimumSize().width, viewportHeight);
        // Translate g to the current vertical scroll level
        g.translate(LEFT_GAP, -180);
        // Paint the underlying KPanel onto the viewport
        super.paint(g);
        
        // Restore the original clip
        Rectangle r = g.getClipBounds();
        g.setClip(clip);
        
        // Draw the viewport border.
        g.drawRect(r.x, r.y, r.width, r.height);
    }
}
