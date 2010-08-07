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
 * Custom checkbox for use in Kindle KDK.
 * @author dfabulich
 *
 */
public class KCheckbox extends KButton implements ActionListener {

    private boolean selected = false;
    final KCheckboxGroup group;

    private boolean isRadioButton() {
        return group != null;
    }

    /** Is this box checked? */
    public boolean isSelected() {
        return selected;
    }

    /** Check/uncheck this box */
    public void setSelected(boolean selected) {
        if (this.selected == selected) return;
        if (selected && group != null) group.setSelected(this);
        this.selected = selected;
        repaint();
    }

    private static final long serialVersionUID = 8105922331821759692L;
    // Should these be configurable?  Probably.  Just steal the code if you want to tweak these numbers.
    private static final int padding = 1, border = 2;

    /** Create a checkbox */
    public KCheckbox() {
        this(null);
    }

    /** Create a radio button belonging to a group */
    public KCheckbox(KCheckboxGroup group) {
        this.group = group;
        addActionListener(this);
    }

    public Dimension getPreferredSize() {
        return getMinimumSize();
    }

    public Dimension getMinimumSize() {
        int d = getFontMetrics(getFont()).getMaxAscent();
        d += (padding + border) * 2;
        return new Dimension(d, d);
    }

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

    /** Select this box */
    public void actionPerformed(ActionEvent e) {
        if (isRadioButton() && selected) return; // can't deselect last radio button
        setSelected(!selected);
    }


}
