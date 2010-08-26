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
import org.kwt.InvalidStyleException;

import com.amazon.kindle.kindlet.ui.KComponent;

/**
 * A progress bar. Represents the proportion of progress towards completing a particular task. Meant
 * to emulate the book progress bar at the bottom of the regular Kindle reader application.
 * 
 * <br><br><font size="1">Example render: </font>
 * <img src="http://s3.amazonaws.com/kwt-dev/javadoc_images/KWTProgressBar_ExampleRender.png">
 * 
 * @author Adrian Petrescu
 *
 */
public class KWTProgressBar extends KComponent {
    private static final long serialVersionUID = 5781953629278873008L;
    private static final Logger logger = Logger.getLogger(KWTProgressBar.class);
    
    private static final int VERTICAL_PADDING   = 3;
    private static final int HORIZONTAL_PADDING = 10;
    private static final int CORNER_ROUNDING = 10;
    
    /** An empty style. */
    public static final int STYLE_NONE = 0;
    /** A percentage description of progress */
    public static final int STYLE_PERCENTAGE = 1;
    /** A fractional description of progress */
    public static final int STYLE_TOTAL = 2;
    
    private int width;
    private int labelStyle = STYLE_PERCENTAGE;
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
        
        this.setFocusable(false);
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
     * Returns the style of label to be used. Valid options are:
     * <ul>
     *   <li> <code>STYLE_NONE</code> - no label will be shown.
     *   <li> <code>STYLE_PERCENTAGE</code> - the label will display a percentage of completion.
     *   <li> <code>STYLE_TOTAL</code> - the label will display a fraction of <code>totalTicks</code>
     * </ul>
     * 
     * @return the style of label to be used.
     */
    public int getLabelStyle() {
        return labelStyle;
    }
    
    /**
     * Sets the style of label to be used. Valid styles are:
     * <ul>
     *   <li> <code>STYLE_NONE</code> - no label will be shown.
     *   <li> <code>STYLE_PERCENTAGE</code> - the label will display a percentage of completion.
     *   <li> <code>STYLE_TOTAL</code> - the label will display a fraction of <code>totalTicks</code>
     * </ul>
     * 
     * @param style the style of label to be used.
     * @throws InvalidStyleException if <code>style</code> is not one of the allowed values.
     */
    public void setLabelStyle(int style) throws InvalidStyleException {
        if (style < STYLE_NONE || style > STYLE_TOTAL)
            throw new InvalidStyleException(this.getClass().getName() + 
                " does not support the given style for highlighting.", style);
        labelStyle = style;
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
        return new Dimension(getMinimumWidth() + 2 * HORIZONTAL_PADDING,
                getMinimumHeight() + 2 * VERTICAL_PADDING);
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
        
        if (labelStyle == STYLE_PERCENTAGE || labelStyle == STYLE_TOTAL) {
            String progressString = "";
            switch (labelStyle) {
            case STYLE_PERCENTAGE:
                progressString = ((int) (progress * 100)) + "%";
                break;
            case STYLE_TOTAL:
                progressString = currentTick + "/" + totalTicks;
            }
            g.setXORMode(Color.WHITE);
            g.drawString(progressString,
                    ((getWidth() - 1) / 2) - (getFontMetrics(getFont()).stringWidth(progressString) / 2),
                    getFontMetrics(getFont()).getHeight());
        }
    }
    
    private int getMinimumHeight() {
        switch (labelStyle) {
        case STYLE_NONE: return 0;
        case STYLE_PERCENTAGE:
        case STYLE_TOTAL:
        default:
            return getFontMetrics(getFont()).getHeight();
        }
    }
    
    private int getMinimumWidth() {
        switch (labelStyle) {
        case STYLE_NONE: return 0;
        case STYLE_PERCENTAGE:
            return getFontMetrics(getFont()).stringWidth("100%");
        case STYLE_TOTAL:
            String longest = String.valueOf(totalTicks);
            longest = longest + "/" + longest;
            return getFontMetrics(getFont()).stringWidth(longest);
        }
        
        return 0;
    }
}
