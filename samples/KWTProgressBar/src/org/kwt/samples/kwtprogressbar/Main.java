package org.kwt.samples.kwtprogressbar;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.kwt.ui.KWTProgressBar;

import com.amazon.kindle.kindlet.AbstractKindlet;
import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KButton;
import com.amazon.kindle.kindlet.ui.KPanel;

public class Main extends AbstractKindlet {

    public void create(final KindletContext context) {
        final Container rootContainer = context.getRootContainer();
        final KPanel panel = new KPanel();
        rootContainer.add(panel);
        
        final KWTProgressBar progressBar1 = new KWTProgressBar();
        progressBar1.setWidth(800);
        progressBar1.setLabelStyle(KWTProgressBar.STYLE_NONE);
        
        final KWTProgressBar progressBar2 = new KWTProgressBar();
        progressBar2.setWidth(800);
        progressBar2.setLabelStyle(KWTProgressBar.STYLE_PERCENTAGE);
        
        final KWTProgressBar progressBar3 = new KWTProgressBar();
        progressBar3.setWidth(800);
        progressBar3.setLabelStyle(KWTProgressBar.STYLE_TOTAL);
        
        KButton increaseTicks = new KButton("Increase ticks");
        KButton decreaseTicks = new KButton("Decrease ticks");
        increaseTicks.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                progressBar1.setCurrentTick(progressBar1.getCurrentTick() + 1);
                progressBar2.setCurrentTick(progressBar2.getCurrentTick() + 1);
                progressBar3.setCurrentTick(progressBar3.getCurrentTick() + 1);
                panel.repaint();
            }
        });
        decreaseTicks.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                progressBar1.setCurrentTick(progressBar1.getCurrentTick() - 1);
                progressBar2.setCurrentTick(progressBar2.getCurrentTick() - 1);
                progressBar3.setCurrentTick(progressBar3.getCurrentTick() - 1);
                panel.repaint();
            }
        });
        increaseTicks.requestFocus();
        
        panel.add(progressBar1);
        panel.add(progressBar2);
        panel.add(progressBar3);
        panel.add(increaseTicks);
        panel.add(decreaseTicks);
    }
}
