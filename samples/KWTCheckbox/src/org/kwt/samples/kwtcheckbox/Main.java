package org.kwt.samples.kwtcheckbox;

import java.awt.Container;
import java.awt.FlowLayout;

import org.kwt.ui.KWTCheckbox;
import org.kwt.ui.KWTCheckboxGroup;

import com.amazon.kindle.kindlet.AbstractKindlet;
import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KLabel;

/**
 * This sample Kindlet demonstrates the use of the KWTCheckbox.
 * 
 * @author Adrian Petrescu
 *
 */
public class Main extends AbstractKindlet {

    public void create(final KindletContext context) {
        Container rootContainer = context.getRootContainer();
        rootContainer.setLayout(new FlowLayout());

        KWTCheckboxGroup group = new KWTCheckboxGroup();

        rootContainer.add(new KWTCheckbox(group));
        rootContainer.add(new KWTCheckbox(group));
        rootContainer.add(new KWTCheckbox(group));
        rootContainer.add(new KWTCheckbox(group));
        rootContainer.add(new KWTCheckbox(group));

        group = null;

        rootContainer.add(new KWTCheckbox(group));
        rootContainer.add(new KWTCheckbox(group));
        rootContainer.add(new KWTCheckbox(group));
        rootContainer.add(new KWTCheckbox(group));
        rootContainer.add(new KWTCheckbox(group));

        KWTCheckbox button = new KWTCheckbox(group);
        rootContainer.add(button);

        button.requestFocus();
        rootContainer.add(new KLabel("Click this option!"));
    }
}
