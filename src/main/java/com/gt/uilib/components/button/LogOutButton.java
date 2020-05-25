package com.gt.uilib.components.button;

import com.gt.common.ResourceManager;
import com.gt.uilib.components.AppFrame;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LogOutButton extends ActionButton {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public LogOutButton(String Text, ImageIcon on, ImageIcon off, String panelQualifiedClassName) {
        super(Text, on, off, panelQualifiedClassName);
    }

    public static LogOutButton create(String text, String fileName, String panelQualifiedClassName) {
        String onFile = fileName + "-on.png";
        String offFile = fileName + "-off.png";
        return new LogOutButton(text, ResourceManager.getImageIcon(onFile), ResourceManager.getImageIcon(offFile), panelQualifiedClassName);
    }

    public static void handleLogout() {

        AppFrame.getInstance().handleLogOut();

    }

    @Override
    protected final void initListner() {
        addMouseListener(getLogOutMouseListener());
    }

    protected final MouseListener getLogOutMouseListener() {
        MouseListener ml = new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                handleLogout();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(new EtchedBorder(EtchedBorder.LOWERED));
                highlight();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(null);
                unhighlight();
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                setBorder(null);
                unhighlight();
            }
        };
        return ml;
    }
}
