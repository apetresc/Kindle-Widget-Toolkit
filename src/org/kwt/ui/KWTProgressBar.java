package org.kwt.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import org.apache.log4j.Logger;

import com.amazon.kindle.kindlet.ui.KComponent;

/**
 * A progress bar.
 * 
 * @author Adrian Petrescu
 *
 */
public class KWTProgressBar extends KComponent {
    private static final long serialVersionUID = 5781953629278873008L;
    private static final Logger logger = Logger.getLogger(KWTProgressBar.class);
    
    private static final int VERTICAL_PADDING   = 3;
    private static final int HORIZONTAL_PADDING = 3;
    private static final int CORNER_ROUNDING = 10;
    
    private int width;
    private int totalTicks;
    private int currentTick;
    
    public KWTProgressBar() {
        this(100);
    }
    
    public KWTProgressBar(int totalTicks) {
        this.totalTicks = totalTicks;
        this.currentTick = 0;
    }
    
    public int getTotalTicks() {
        return totalTicks;
    }
    
    public int getCurrentTick() {
        return currentTick;
    }
    
    public void setCurrentTick(int currentTick) {
        this.currentTick = currentTick;
    }
    
    public Dimension getPreferredSize() {
        Dimension d = getMinimumSize();
        d.width = Math.max(width, d.width);
        return d;
    }
    
    public Dimension getMinimumSize() {
        return new Dimension(getFontMetrics(getFont()).stringWidth("100%") + 2 * HORIZONTAL_PADDING,
                getFontMetrics(getFont()).getHeight() + 2 * VERTICAL_PADDING);
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void paint(Graphics g) {
        double progress = (double) currentTick / totalTicks;
        logger.info("Progress: " + progress);
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, CORNER_ROUNDING, CORNER_ROUNDING);
        g.setColor(Color.BLACK);
        g.fillRoundRect(0, 0, (int) (progress * getWidth()) - 1, getHeight() - 1, CORNER_ROUNDING, CORNER_ROUNDING);
        String progressString = ((int) (progress * 100)) + "%";
        g.setXORMode(Color.WHITE);
        g.drawString(progressString,
                ((getWidth() - 1) / 2) - (getFontMetrics(getFont()).stringWidth(progressString) / 2),
                getFontMetrics(getFont()).getHeight());
    }
}
