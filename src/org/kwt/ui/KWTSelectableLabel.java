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

/**
 * A selectable KLabel that can be focusable and receive ActionEvents. Intended to be sometimes
 * used in lieu of a KButton.
 * 
 * <br><br><font size="1">Example render: </font>
 * <img src="http://s3.amazonaws.com/kwt-dev/javadoc/KSelectableLabel_ExampleRender.png">
 * 
 * @author Adrian Petrescu
 *
 */
public class KWTSelectableLabel extends KLabel {
    private static final long serialVersionUID = 8118660222383683366L;
    
    private static final int BUTTON_DOWN_EVENT = 401;
    private static final int DEFAULT_UNDERLINE_WIDTH = 5;
    private static final int DEFAULT_UNDERLINE_GAP = 2;

    /** An empty style */
    public static final int STYLE_NONE = 0;
    /** A solid style in the foreground color */
    public static final int STYLE_SOLID = 1;
    /** A dashed line in the foreground color */
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
    
    /**
     * Constructs a new selectable label with no text.
     */
    public KWTSelectableLabel() {
        this(null);
    }
    
    /**
     * Constructs a new selectable label with the given text. The text will be clipped if it extends
     * past the label's maximum size.
     * 
     * @param text the label's text
     */
    public KWTSelectableLabel(String text) {
        super(text);
        enableEvents(AWTEvent.KEY_EVENT_MASK);
        setFocusable(true);
        actionListeners = new LinkedList();
    }
    
    /**
     * Returns the style of highlighting to be used when the label has the focus. Valid results are:
     * <ul>
     *   <li> <code>STYLE_NONE</code> - the label will not be highlighted at all
     *   <li> <code>STYLE_SOLID</code> - the label will have an inverted foreground and background color.
     * </ul>
     * 
     * @return the style used for the highlighting
     */
    public int getHighlightStyle() {
        return highlightStyle;
    }
    
    /**
     * Sets the style of highlighting to be used when the label has the focus. Valid styles are:
     * <ul>
     *   <li> <code>STYLE_NONE</code> - the label will not be highlighted at all
     *   <li> <code>STYLE_SOLID</code> - the label will have an inverted foreground and background color.
     * </ul>
     * 
     * @param style the style to use for the highlighting
     * @throws InvalidStyleException if <code>style</code> is not one of the allowed values.
     */
    public void setHighlightStyle(int style) throws InvalidStyleException {
        if (style < STYLE_NONE || style > STYLE_SOLID)
            throw new InvalidStyleException(this.getClass().getName() + 
                    " does not support the given style for highlighting.", style);
        highlightStyle = style;
    }
    
    /**
     * Returns the style of underlining to be used when the label has the focus. Valid results are:
     * <ul>
     *   <li> <code>STYLE_NONE</code> - the label will not be underlined at all
     *   <li> <code>STYLE_SOLID</code> - the label will be underlined by a solid line
     *   <li> <code>STYLE_DASHED</code> - the label will be underlined by a dashed line
     * </ul>
     * 
     * @return the style used for the underlining
     */
    public int getUnderlineStyle() {
        return underlineStyle;
    }

    /**
     * Sets the style of underlining to be used when the label has the focus. Valid styles are:
     * <ul>
     *   <li> <code>STYLE_NONE</code> - the label will not be underlined at all
     *   <li> <code>STYLE_SOLID</code> - the label will be underlined by a solid line
     *   <li> <code>STYLE_DASHED</code> - the label will be underlined by a dashed line
     * </ul>
     * 
     * @param style the style to use for the underlining
     */
    public void setUnderlineStyle(int style) throws InvalidStyleException {
        if (style < STYLE_NONE || style > STYLE_DASHED)
            throw new InvalidStyleException(this.getClass().getName() +
                    " does not support the given style for underlining.", style);
        underlineStyle = style;
    }
    
    /**
     * Sets the width of the underline, assuming the style provides one. Note that this may return
     * a non-zero value even if the underlining style is set to <code>STYLE_NONE</code>
     * 
     * @return the width of the underline
     */
    public int getUnderlineWidth() {
        return underlineWidth;
    }
    
    /**
     * Sets the width of the underline, assuming the style provides one.
     * 
     * @param width the width of the underline
     */
    public void setUnderlineWidth(int width) {
        underlineWidth = width;
    }

    /**
     * Sets the gap between the bottom of the label's text and the top of the underline, assuming the style
     * provides one. Note that the gap takes into account the maximum descent of the text's font.
     * 
     * @param gap the gap between the label and the underline
     */
    public void setUnderlineGap(int gap) {
        underlineGap = gap;
    }
    
    /**
     * Returns the gap between the bottom of the label's text and the top of the underline, assuming the style
     * provides one. Note that the gap takes into account hte maximum descent of the text's font.
     * 
     * @return the gap between the label and the underline
     */
    public int getUnderlineGap() {
        return underlineGap;
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
        // Use KLabel's preferred size for its minimum size, to work around a known bug in the KDK.
        Dimension d = super.getPreferredSize();
        if (spoofSize) return new Dimension(d.width, d.height - underlineGap - underlineWidth);
        return new Dimension(d.width, d.height + underlineGap + underlineWidth + 1);
    }
   
    /**
     * {@inheritDoc }
     */
    public Dimension getSize() {
        Dimension d = super.getSize();
        if (spoofSize) return new Dimension(d.width, d.height - underlineGap - underlineWidth);
        return new Dimension(d.width, d.height + underlineGap + underlineWidth + 1);
    }

    /**
     * {@inheritDoc }
     */
    public void paint(Graphics g) {
        spoofSize = true;
        super.paint(g);
        spoofSize = false;
        if (this.isFocusOwner()) {
            int y = super.getSize().height - (underlineGap + underlineWidth);
            g.setColor(Color.BLACK);
            switch (underlineStyle) {
            case STYLE_SOLID:
                g.fillRect(0, y + underlineGap, this.getWidth() - 1, underlineWidth - 1);
                break;
            case STYLE_DASHED:
                for (int i = 0; i <= (this.getWidth() - 1) / (underlineWidth - 1); i += 2) {
                    g.fillRect(i * (underlineWidth - 1), y + underlineGap, underlineWidth - 1, underlineWidth - 1);
                }
            }
        }
    }

    /**
     * Registers a listener who wishes to be notified whenever this label is clicked by the user.
     * 
     * @param listener a listener who wishes to be notified
     */
    public void addActionListener(ActionListener listener) {
        this.actionListeners.add(listener);
    }
    
    /**
     * {@inheritDoc }
     */
    protected void processEvent(AWTEvent e) {
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
