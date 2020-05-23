package com.gt.uilib.components;

import javax.swing.*;
import java.awt.*;

public class GDialog extends Dialog {
    JPanel funcPane;

    public GDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
    }

    public final void setAbstractFunctionPanel(JPanel jPanel, Dimension dm) {
        this.funcPane = jPanel;

        add(funcPane);
        setSize(dm);
        setLocation(100, 50);
        setVisible(true);
    }

}
