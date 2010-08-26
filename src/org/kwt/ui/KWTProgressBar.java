/*
 * Copyright 2010 Adrian Petrescu.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located in the LICENSE file included with this
 * distribution.
 * 
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 * CONDITIONS OF ANY KIND, either express or implied. See the License
 * for the specific language governing permissions and limitations under the
 * License.
 */

package org.kwt.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import org.apache.log4j.Logger;

import com.amazon.kindle.kindlet.ui.KComponent;

/**
 * A progress bar. Represents the proportion of progress towards completing a particular task. Meant
 * to emulate the book progress bar at the bottom of the regular Kindle reader application.
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
    
    /** 
     * Constructs a new progress bar, with 100 total ticks. The current tick begins at 0.
     */
    public KWTProgressBar() {
        this(100);
    }
    
    /**
     * Constructs a new progress bar. The current tick begins at 0.
     * 
     * @param totalTicks The total number of ticks representing completion of the task.
     */
    public KWTProgressBar(int totalTicks) {
        this.totalTicks = totalTicks;
        this.currentTick = 0;
    }
    
    /**
     * Returns the total number of ticks.
     * 
     * @return the total number of ticks.
     */
    public int getTotalTicks() {
        return totalTicks;
    }
    
    /**
     * Returns the current tick.
     * 
     * @return the current tick.
     */
    public int getCurrentTick() {
        return currentTick;
    }
    
    /**
     * Sets the current tick. If <code>currentTick < 0</code> or 
     * <code>currentTick > totalTicks</code>, then <code>currentTick</code>
     * will be set to 0 or <code>totalTicks</code> respectively.
     * 
     * @param currentTick the current tick.
     */
    public void setCurrentTick(int currentTick) {
        this.currentTick = Math.min(totalTicks, Math.max(0, currentTick));
    }
    
    /**
     * {@inheritDoc }
     */
    public Dimension getPreferredSize() {
        Dimension d = getMinimumSize();
        d.width = Math.max(width, d.width);
        return d;
    }
    
    /**
     * {@inheritDoc }
     */
    public Dimension getMinimumSize() {
        return new Dimension(getFontMetrics(getFont()).stringWidth("100%") + 2 * HORIZONTAL_PADDING,
                getFontMetrics(getFont()).getHeight() + 2 * VERTICAL_PADDING);
    }
    
    /**
     * Sets the width (in pixels) of this component.
     * @param width the width (in pixels) of this component.
     */
    public void setWidth(int width) {
        this.width = width;
    }
    
    /**
     * {@inheritDoc }
     */
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
