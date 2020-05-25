package com.ca.ui.panels;

import com.gt.common.ResourceManager;
import com.gt.common.constants.StrConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.*;
import java.awt.*;

public class HomeScreenPanel extends JPanel {

    private static final String TAHOMA = "Tahoma";

    public HomeScreenPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JPanel panel4 = new JPanel();
        add(panel4);
        panel4.setLayout(new FormLayout(new ColumnSpec[]{
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("max(59dlu;default)"),
                FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("default:grow"),
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("default:grow"),},
                new RowSpec[]{
                        FormFactory.RELATED_GAP_ROWSPEC,
                        RowSpec.decode("max(65dlu;default):grow"),
                        FormFactory.RELATED_GAP_ROWSPEC,
                        RowSpec.decode("max(16dlu;default)"),
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        RowSpec.decode("max(17dlu;default)"),
                        FormFactory.RELATED_GAP_ROWSPEC,
                        RowSpec.decode("max(11dlu;default)"),
                        FormFactory.RELATED_GAP_ROWSPEC,
                        RowSpec.decode("max(15dlu;default)"),
                        FormFactory.RELATED_GAP_ROWSPEC,
                        RowSpec.decode("max(8dlu;default)"),
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,}));

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(ResourceManager.getImageIcon("logo2.png"));
        panel4.add(lblNewLabel, "8, 8, 1, 5");

        JLabel lblCompany = new JLabel(ResourceManager.getString(StrConstants.COMPANY_NAME));
        lblCompany.setFont(new Font(TAHOMA, Font.BOLD, 16));
        panel4.add(lblCompany, "10, 8, default, top");

        JLabel lblComm = new JLabel(ResourceManager.getString(StrConstants.DEPARTMENT));
        lblComm.setFont(new Font(TAHOMA, Font.BOLD, 13));
        panel4.add(lblComm, "10, 10");

        JLabel lblInventory = new JLabel(ResourceManager.getString(StrConstants.APP_TITLE));
        lblInventory.setFont(new Font(TAHOMA, Font.BOLD, 14));
        panel4.add(lblInventory, "10, 12");

        JLabel lblWelcome = new JLabel("Welcome, Please use toolbar and menus to proceed.");
        lblWelcome.setFont(new Font(TAHOMA, Font.PLAIN, 13));
        panel4.setFont(new Font(TAHOMA, Font.BOLD, 12));
        panel4.add(lblWelcome, "10, 16, left, default");

    }


}
