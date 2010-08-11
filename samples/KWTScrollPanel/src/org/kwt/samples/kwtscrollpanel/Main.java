package org.kwt.samples.kwtscrollpanel;

import java.awt.Container;

import org.kwt.ui.KWTScrollPanel;
import org.kwt.ui.KWTSelectableLabel;

import com.amazon.kindle.kindlet.AbstractKindlet;
import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KBoxLayout;
import com.amazon.kindle.kindlet.ui.KLabel;
import com.amazon.kindle.kindlet.ui.KPanel;

/**
 * This sample Kindlet demonstrates the use of the KWTScrollPanel
 * 
 * @author Adrian Petrescu
 * 
 */
public class Main extends AbstractKindlet {
    
    public void create(final KindletContext context) {
        final Container rootContainer = context.getRootContainer();
        Container testContainer = new KWTScrollPanel(80);
        testContainer.setLayout(new KBoxLayout(testContainer, KBoxLayout.Y_AXIS));
        rootContainer.add(testContainer);
        
        KLabel[] labels = new KLabel[25];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new KLabel("This is Label #" + i);
            testContainer.add(labels[i]);
        }
    }
    
}
