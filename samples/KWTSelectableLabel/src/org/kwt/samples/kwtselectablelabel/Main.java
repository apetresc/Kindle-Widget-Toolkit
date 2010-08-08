package org.kwt.samples.kwtselectablelabel;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.kwt.ui.KWTSelectableLabel;

import com.amazon.kindle.kindlet.AbstractKindlet;
import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KBoxLayout;
import com.amazon.kindle.kindlet.ui.KLabel;

/**
 * This sample Kindlet demonstrates the use of the KWTSelectableLabel.
 * 
 * @author Adrian Petrescu
 *
 */
public class Main extends AbstractKindlet {

    public void create(final KindletContext context) {
        final Container rootContainer = context.getRootContainer();
        rootContainer.setLayout(new KBoxLayout(rootContainer, KBoxLayout.Y_AXIS));
        
        final KLabel description = new KLabel("No label has been selected yet.");
        
        KWTSelectableLabel[] labels = new KWTSelectableLabel[5];
        for (int i = 0; i < labels.length; i++) {
            final int selectedLabel = i;
            labels[i] = new KWTSelectableLabel("This is Label #" + i);
            labels[i].setUnderlineWidth((i+1) * 2);
            labels[i].setUnderlineGap(i * 2);
            labels[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    description.setText("Label #" + selectedLabel + " was selected.");
                    rootContainer.repaint();
                }
            });
            rootContainer.add(labels[i]);
        }
        labels[0].requestFocus();
        rootContainer.add(description);
    }
}
