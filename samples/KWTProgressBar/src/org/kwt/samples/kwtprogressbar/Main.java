package org.kwt.samples.kwtprogressbar;

import java.awt.Container;

import org.kwt.ui.KWTProgressBar;

import com.amazon.kindle.kindlet.AbstractKindlet;
import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KLabel;
import com.amazon.kindle.kindlet.ui.KPanel;

public class Main extends AbstractKindlet {

    public void create(final KindletContext context) {
        final Container rootContainer = context.getRootContainer();
        KPanel panel = new KPanel();
        rootContainer.add(panel);
        KWTProgressBar progressBar1 = new KWTProgressBar();
        progressBar1.setWidth(600);
        progressBar1.setCurrentTick(50);
        KWTProgressBar progressBar2 = new KWTProgressBar();
        progressBar2.setCurrentTick(100);
        KLabel label1 = new KLabel("Hello there");
        KLabel label2 = new KLabel("Bye there");
        panel.add(progressBar1);
        panel.add(progressBar2);
        panel.add(label1);
        panel.add(label2);
    }
}
