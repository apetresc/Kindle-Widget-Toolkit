package org.kwt.samples.kcheckbox;

import java.awt.Container;
import java.awt.FlowLayout;

import org.kwt.ui.KCheckbox;
import org.kwt.ui.KCheckboxGroup;

import com.amazon.kindle.kindlet.AbstractKindlet;
import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KLabel;

/**
 * This sample Kindlet demonstrates the use of the KCheckbox.
 * 
 * @author Adrian Petrescu
 *
 */
public class Main extends AbstractKindlet {

    public void create(final KindletContext context) {
        Container rootContainer = context.getRootContainer();
        rootContainer.setLayout(new FlowLayout());

        KCheckboxGroup group = new KCheckboxGroup();

        rootContainer.add(new KCheckbox(group));
        rootContainer.add(new KCheckbox(group));
        rootContainer.add(new KCheckbox(group));
        rootContainer.add(new KCheckbox(group));
        rootContainer.add(new KCheckbox(group));

        group = null;

        rootContainer.add(new KCheckbox(group));
        rootContainer.add(new KCheckbox(group));
        rootContainer.add(new KCheckbox(group));
        rootContainer.add(new KCheckbox(group));
        rootContainer.add(new KCheckbox(group));

        KCheckbox button = new KCheckbox(group);
        rootContainer.add(button);

        button.requestFocus();
        rootContainer.add(new KLabel("fClick this option! g f I"));
    }
}
