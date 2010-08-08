package org.kwt.ui;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.kwt.InvalidStyleException;

import com.amazon.kindle.kindlet.ui.KLabel;

public class KWTSelectableLabel extends KLabel {
    private static final long serialVersionUID = 8118660222383683366L;
    
    private static final int BUTTON_DOWN_EVENT = 401;
    private static final int DEFAULT_UNDERLINE_WIDTH = 5;
    private static final int DEFAULT_UNDERLINE_GAP = 2;

    public static final int STYLE_NONE = 0;
    public static final int STYLE_SOLID = 1;
    public static final int STYLE_DASHED = 2;
    
    private List actionListeners;
    private int highlightStyle = STYLE_NONE;
    private int underlineStyle = STYLE_SOLID;
    private int underlineWidth = DEFAULT_UNDERLINE_WIDTH;
    private int underlineGap = DEFAULT_UNDERLINE_GAP;
    
    /* This is a dirty hack to get around the fact that KLabels do not respond to setPosition().
     * Instead, when painting the superclass, we have to trick it into thinking its size is smaller
     * than it actually is. However, we don't want this faulty size to be read at any other time.
     * Hence this flag for when to spoof the size.
     */
    private boolean spoofSize = false;
    
    public KWTSelectableLabel() {
        this(null);
    }
    
    public KWTSelectableLabel(String text) {
        super(text);
        enableEvents(AWTEvent.KEY_EVENT_MASK);
        setFocusable(true);
        actionListeners = new LinkedList();
    }
    
    public int getHighlightStyle() {
        return highlightStyle;
    }
    
    public void setHighlightStyle(int style) throws InvalidStyleException {
        if (style < STYLE_NONE || style > STYLE_SOLID)
            throw new InvalidStyleException(this.getClass().getName() + 
                    " does not support the given style for highlighting.", style);
        highlightStyle = style;
    }
    
    public int getUnderlineStyle() {
        return underlineStyle;
    }
    
    public void setUnderlineWidth(int width) {
        underlineWidth = width;
    }
    
    public int getUnderlineWidth() {
        return underlineWidth;
    }
    
    public void setUnderlineGap(int gap) {
        underlineGap = gap;
    }
    
    public int getUnderlineGap() {
        return underlineGap;
    }
    
    public void setUnderlineStyle(int style) throws InvalidStyleException {
        if (style < STYLE_NONE || style > STYLE_DASHED)
            throw new InvalidStyleException(this.getClass().getName() +
                    " does not support the given style for underlining.", style);
        underlineStyle = style;
    }
    
    public Dimension getPreferredSize() {
        return getMinimumSize();
    }
    
    public Dimension getMinimumSize() {
        // Use KLabel's preferred size for its minimum size, to work around a known bug in the KDK.
        Dimension d = super.getPreferredSize();
        if (spoofSize) return new Dimension(d.width, d.height - underlineGap - underlineWidth);
        return new Dimension(d.width, d.height + underlineGap + underlineWidth + 1);
    }
    
    public Dimension getSize() {
        Dimension d = super.getSize();
        if (spoofSize) return new Dimension(d.width, d.height - underlineGap - underlineWidth);
        return new Dimension(d.width, d.height + underlineGap + underlineWidth + 1);
    }

    public void paint(Graphics g) {
        spoofSize = true;
        super.paint(g);
        spoofSize = false;
        if (this.isFocusOwner()) {
            switch (underlineStyle) {
            case STYLE_SOLID:
                int y = super.getSize().height - (underlineGap + underlineWidth);
                g.setColor(Color.BLACK);
                g.fillRect(0, y + underlineGap, this.getWidth() - 1, underlineWidth - 1);
            }
        }
    }

    public void addActionListener(ActionListener listener) {
        this.actionListeners.add(listener);
    }
    
    public void processEvent(AWTEvent e) {
        switch(e.getID()) {
        case BUTTON_DOWN_EVENT:
            Iterator it = actionListeners.iterator();
            while (it.hasNext()) {
                ActionListener listener = (ActionListener) it.next();
                listener.actionPerformed(new ActionEvent(this, BUTTON_DOWN_EVENT, null));
            }
            break;
        default:
            break;
        }
    }
}
