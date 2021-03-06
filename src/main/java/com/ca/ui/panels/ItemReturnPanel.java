package com.ca.ui.panels;

import com.ca.db.model.Category;
import com.ca.db.model.Transfer;
import com.ca.db.service.ItemReturnServiceImpl;
import com.ca.db.service.TransferServiceImpl;
import com.gt.common.utils.DateTimeUtils;
import com.gt.common.utils.ExcelUtils;
import com.gt.common.utils.UIUtils;
import com.gt.uilib.components.input.DataComboBox;
import com.gt.uilib.components.table.BetterJTable;
import com.gt.uilib.components.table.EasyTableModel;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ItemReturnPanel extends JPanel {
    public static final String CATEGORY = "Category";

    private JPanel formPanel = null;
    private JPanel buttonPanel;
    private JDateChooser txtFromDate;
    private JDateChooser txtToDate;
    private JButton btnSave;
    private JPanel upperPane;
    private BetterJTable table;
    private EasyTableModel dataModel;
    private DataComboBox cmbCategory;
    private JTextField txtPanaNumber;
    private JTextField txtItemname;
    static transient System.Logger logger;

    private ItemReceiverPanel itemReceiverPanel;


    public ItemReturnPanel() {

        JSplitPane splitPane = new JSplitPane();
        splitPane.setContinuousLayout(true);
        splitPane.setResizeWeight(0.1);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        add(splitPane, BorderLayout.CENTER);
        splitPane.setLeftComponent(getUpperSplitPane());

        init();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, e);
        }
        EventQueue.invokeLater(() -> {
            try {
                JFrame jf = new JFrame();
                ItemReturnPanel panel = new ItemReturnPanel();
                jf.setBounds(panel.getBounds());
                jf.getContentPane().add(panel);
                jf.setVisible(true);
                jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            } catch (Exception e) {
                logger.log(System.Logger.Level.ERROR, e);
            }
        });
    }


    public final void init() {
        /* never forget to call super.init() */
        UIUtils.decorateBorders(this);
        UIUtils.clearAllFields(upperPane);
        intCombo();
    }

    private void intCombo() {
        try {
            /* Category Combo */
            cmbCategory.init();
            List<Category> cl = ItemReturnServiceImpl.getNonReturnableCategory();
            for (Category c : cl) {
                cmbCategory.addRow(new Object[]{c.getId(), c.getCategoryName()});
            }
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, e);
        }
    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();

            JButton btnSaveToExcel = new JButton("Save to Excel");
            btnSaveToExcel.addActionListener(e -> {
                JFileChooser jf = new JFileChooser();
                jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jf.showDialog(ItemReturnPanel.this, "Select Save location");
                String fileName = jf.getSelectedFile().getAbsolutePath();
                try {
                    ExcelUtils.writeExcelFromJTable(table, fileName + ".xls");
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Could not save" + e1.getMessage());
                }
            });

            JButton btnPrev = new JButton("<");
            buttonPanel.add(btnPrev);

            JButton btnNext = new JButton(">");
            buttonPanel.add(btnNext);
            buttonPanel.add(btnSaveToExcel);
        }
        return buttonPanel;
    }


    public void handleSaveAction() {
        throw new UnsupportedOperationException();
    }


    private JPanel getUpperFormPanel() {
        if (formPanel == null) {
            formPanel = new JPanel();

            formPanel.setBorder(new TitledBorder(null, "Transfer Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            formPanel.setBounds(10, 49, 474, 135);
            formPanel.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("left:max(128dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("left:max(26dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("max(125dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,}, new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
                    FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));

            JLabel lblItemName = new JLabel("Item Name");
            formPanel.add(lblItemName, "4, 2");

            txtItemname = new JTextField();
            formPanel.add(txtItemname, "8, 2, fill, default");
            txtItemname.setColumns(10);
            JLabel lblPanaNumber = new JLabel("Transfer Number");
            formPanel.add(lblPanaNumber, "12, 2");

            txtPanaNumber = new JTextField();
            formPanel.add(txtPanaNumber, "16, 2, fill, default");
            txtPanaNumber.setColumns(10);

            JLabel lblN = new JLabel(CATEGORY);
            formPanel.add(lblN, "4, 4");

            cmbCategory = new DataComboBox();
            formPanel.add(cmbCategory, "8, 4, fill, default");

            JLabel lblVendor = new JLabel("Item Request No.");
            formPanel.add(lblVendor, "12, 4, default, top");

            JTextField txtItemRequestNumber = new JTextField();
            formPanel.add(txtItemRequestNumber, "16, 4, fill, default");
            txtItemRequestNumber.setColumns(10);

            JLabel lblFrom = new JLabel("From");
            formPanel.add(lblFrom, "4, 6");

            txtFromDate = new JDateChooser();
            formPanel.add(txtFromDate, "8, 6, fill, default");

            JLabel lblTo = new JLabel("To");
            formPanel.add(lblTo, "12, 6");

            txtToDate = new JDateChooser();
            txtToDate.setDate(new Date());
            formPanel.add(txtToDate, "16, 6, fill, default");

            btnSave = new JButton("Search");
            btnSave.addActionListener(e -> handleSearchQuery());

            JLabel lblReceiver = new JLabel("Receiver :");
            formPanel.add(lblReceiver, "4, 8, default, center");

            JPanel receiverHolder = new JPanel();
            itemReceiverPanel = new ItemReceiverPanel();
            receiverHolder.add(itemReceiverPanel);
            itemReceiverPanel.hideLilam();
            formPanel.add(receiverHolder, "8, 8, fill, fill");

            formPanel.add(btnSave, "18, 8, default, bottom");

            JButton btnReset = new JButton("Reset");
            formPanel.add(btnReset, "20, 8, default, bottom");
            btnReset.addActionListener(e -> {
                UIUtils.clearAllFields(formPanel);
                cmbCategory.selectDefaultItem();
                itemReceiverPanel.clearAll();
            });
        }
        return formPanel;
    }

    private void handleSearchQuery() {
        readAndShowAll();
    }

    private void readAndShowAll() {
        try {
            List<Transfer> brsL;
            int returnStatus = -1;

            brsL = TransferServiceImpl.notReturnedTransferItemQuery(txtItemname.getText(), cmbCategory.getSelectedId(), itemReceiverPanel.getCurrentReceiverConstant(),
                    itemReceiverPanel.getSelectedId(), returnStatus, -1, txtPanaNumber.getText().trim(), "", txtFromDate.getDate(),
                    txtToDate.getDate());

            if (brsL == null || brsL.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No Records Found");
                dataModel.resetModel();
                dataModel.fireTableDataChanged();
                table.adjustColumns();
                return;
            }
            showListInGrid(brsL);
        } catch (Exception ee) {
            logger.log(System.Logger.Level.ERROR, ee);
        }
    }

    private void showListInGrid(List<Transfer> brsL) {
        dataModel.resetModel();
        int sn = 0;
        String transferTYpe;
        String sentTo ;
        for (Transfer bo : brsL) {
            transferTYpe = "";
            sentTo = "";

            if (bo.getTransferType() == Transfer.OFFICIAL) {
                transferTYpe = "Official";
                sentTo = bo.getBranchOffice().getName() + "  " + bo.getBranchOffice().getAddress();
            } else if (bo.getTransferType() == Transfer.PERSONNAL) {
                transferTYpe = "Personnal";
                sentTo = bo.getPerson().getFirstName() + "  " + bo.getPerson().getLastName();
            }
            dataModel.addRow(new Object[]{++sn, bo.getId(), bo.getItem().getName(), bo.getItem().getCategory().getCategoryName(),
                    bo.getItem().getSpeciifcationString(), DateTimeUtils.getCvDateMMMddyyyy(bo.getTransferDate()), transferTYpe, sentTo,
                    bo.getTransferPanaNumber(), bo.getTransferRequestNumber(), bo.getRemainingQtyToReturn(), bo.getItem().getUnitsString().getValue()});
        }
        table.setModel(dataModel);
        dataModel.fireTableDataChanged();
        table.adjustColumns();
    }


    private JPanel getUpperSplitPane() {
        if (upperPane == null) {
            upperPane = new JPanel();
            upperPane.setLayout(new BorderLayout(0, 0));
            upperPane.add(getUpperFormPanel(), BorderLayout.CENTER);
            upperPane.add(getButtonPanel(), BorderLayout.SOUTH);
        }
        return upperPane;
    }
}
