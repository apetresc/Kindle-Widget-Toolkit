/*
 * Copyright 2010 Dan Fabulich.
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.amazon.kindle.kindlet.ui.KButton;

/**
 * A custom checkbox. If the KWTCheckbox belongs to an instance of a KWTCheckboxGroup, it will behave
 * and look like a radio button; otherwise, it will be a simple toggleable checkbox.
 * 
 * <br><br><font size="1">Example render: </font>
 * <img src="http://s3.amazonaws.com/kwt-dev/javadoc_images/KWTCheckbox_ExampleRender.png">
 * 
 * @author Dan Fabulich
 *
 */
public class KWTCheckbox extends KButton implements ActionListener {
    private static final long serialVersionUID = 8105922331821759692L;
    
    private boolean selected = false;
    final KWTCheckboxGroup group;

    // TODO: Make these values styleable.
    private static final int padding = 1, border = 2;

    private boolean isRadioButton() {
        return group != null;
    }

    /** 
     * Returns whether the box is checked.
     * 
     * @return whether the box is checked
     */
    public boolean isSelected() {
        return selected;
    }

    /** Toggle the state of this box. If it belongs to a KWTCheckboxGroup, it will deselect the currently
     *  selected box in that group.
     *  
     *  @param selected whether to select or deselect this box.
     */
    public void setSelected(boolean selected) {
        if (this.selected == selected) return;
        if (selected && group != null) group.setSelected(this);
        this.selected = selected;
        repaint();
    }

    /** 
     * Constructs a new checkbox. It will be a toggleable checkbox not belonging to a group. 
     */
    public KWTCheckbox() {
        this(null);
    }

    /** Constructs a radio button belonging to a group.
     *
     *  @param group the group this radio button will belong to
     */
    public KWTCheckbox(KWTCheckboxGroup group) {
        this.group = group;
        addActionListener(this);
    }

    /**
     * {@inheritDoc }
     */
    public Dimension getPreferredSize() {
        return getMinimumSize();
    }

    /**
     * {@inheritDoc }
     */
    public Dimension getMinimumSize() {
        int d = getFontMetrics(getFont()).getMaxAscent();
        d += (padding + border) * 2;
        return new Dimension(d, d);
    }

    /**
     * {@inheritDoc }
     */
    public void paint(Graphics g) {
        // Max Ascent ~ maximum height of a letter from the baseline
        // We'll use maxAscent to size our checkbox
        int maxAscent = g.getFontMetrics().getMaxAscent();
        int diameter = maxAscent - border;

        int x = padding + border;
        int y = padding + border;

        g.setColor(Color.black);
        if (isRadioButton()) {
            if (selected) g.fillOval(x, y, diameter, diameter);
            g.drawOval(x, y, diameter, diameter);
        } else {
            // checkbox
            if (selected) {
                // draw X
                g.drawLine(x, y, x+diameter, y+diameter);
                g.drawLine(x, y+diameter, x+diameter, y);
            }
            g.drawRect(x, y, diameter, diameter);
        }
        
        if (!isFocusOwner()) {
            g.setColor(Color.white);
        }
        for (int i = 0; i < border; i++) {
            // Draw the border as a sequence of self-contained rectangles
            // The first border is the full size, the next border is 1px smaller, and so on
            int rectWidth = diameter + 2 * (padding + border - i);
            g.drawRect(i, i, rectWidth, rectWidth);
        }
    }

    /** 
     * Called when the checkbox is toggled by the user. This method should never be called by
     * client code.
     * 
     * @param event the event that toggled this action
     */
    public void actionPerformed(ActionEvent event) {
        if (isRadioButton() && selected) return; // can't deselect last radio button
        setSelected(!selected);
    }


}
