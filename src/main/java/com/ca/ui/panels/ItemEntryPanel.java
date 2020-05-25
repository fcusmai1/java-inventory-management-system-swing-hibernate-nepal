package com.ca.ui.panels;

import com.ca.db.model.Category;
import com.ca.db.model.Item;
import com.ca.db.model.UnitsString;
import com.ca.db.model.Vendor;
import com.ca.db.service.DBUtils;
import com.gt.common.utils.UIUtils;
import com.gt.uilib.components.AppFrame;
import com.gt.uilib.components.GDialog;
import com.gt.uilib.components.input.DataComboBox;
import com.gt.uilib.components.table.BetterJTable;
import com.gt.uilib.components.table.EasyTableModel;
import com.gt.uilib.inputverifier.Validator;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.List;

public class ItemEntryPanel extends JPanel {
    private final String[] header = new String[]{"S.N.", "ID", "Purchase Order No.", "Khata No.", "Dakhila No.", "Name", "Pana No.", "Category", "Specification",
            "Parts No.", "Serial No.", "Rack Number", "Purchase date", "Added date", "Vendor", "Original Quantity", "Qty in Stock", "Rate", "Unit",
            "Total"};
    private JPanel formPanel = null;
    private JPanel buttonPanel;
    transient Validator v;
    private JDateChooser txtPurDate;
    private SpecificationPanel currentSpecificationPanel;
    private JTextField txtName;
    private JButton btnSave;
    private JPanel upperPane;
    private JPanel lowerPane;
    private BetterJTable table;
    private EasyTableModel dataModel;
    private DataComboBox cmbCategory;
    private JPanel specPanelHolder;
    private JTextField txtPartsnumber;
    private JTextField txtRacknumber;
    private DataComboBox cmbVendor;
    private JTextField txtTotal;
    private JTextField txtSerialnumber;
    static transient System.Logger logger;
    protected AppFrame mainApp;

    transient KeyListener priceCalcListener = new KeyListener() {
        private String getPrice() {
            BigDecimal amt;
            BigDecimal rate = new BigDecimal("0");
            BigDecimal qty = new BigDecimal("0");
            logger.log(System.Logger.Level.INFO, "Rate " + rate + " Qty " + qty);
            amt = rate.multiply(qty);
            return amt + "";
        }


        public void keyPressed(KeyEvent e) {
            txtTotal.setText(getPrice());
        }

        public void keyTyped(KeyEvent e) {
            txtTotal.setText(getPrice());
        }

        public void keyReleased(KeyEvent e) {
            txtTotal.setText(getPrice());
        }

    };
    private JTextField txtPananumber;
    private JTextField txtPurchaseordernumber;
    private JButton btnNewCategory;
    private JTextField txtKhatanumber;
    private JTextField txtDakhilanumber;
    private DataComboBox cmbUnitcombo;

    public ItemEntryPanel() {
        JSplitPane splitPane = new JSplitPane();
        splitPane.setContinuousLayout(true);
        splitPane.setResizeWeight(0.3);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        add(splitPane, BorderLayout.CENTER);
        splitPane.setLeftComponent(getUpperSplitPane());
        splitPane.setRightComponent(getLowerSplitPane());
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
                ItemEntryPanel panel = new ItemEntryPanel();
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
        UIUtils.decorateBorders(this);
        UIUtils.clearAllFields(upperPane);
        intCombo();
        txtTotal.setText("0");
    }

    private void intCombo() {
        try {
            initCmbCategory();

            initCmbVendor();

            initCmbUnits();
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, e);
        }
        /* Item listener on cmbCategory - to change specification panel */
        cmbCategory.addItemListener(e -> {
            specPanelHolder.removeAll();
            currentSpecificationPanel = null;
            specPanelHolder.repaint();
            specPanelHolder.revalidate();

        });

    }

    private void initCmbCategory() throws Exception {
        /* Category Combo */
        cmbCategory.init();
        List<Category> cl = DBUtils.readAll(Category.class);
        for (Category c : cl) {
            cmbCategory.addRow(new Object[]{c.getId(), c.getCategoryName()});
        }
    }

    private void initCmbUnits() throws Exception {
        /* Category Combo */
        cmbUnitcombo.init();
        List<UnitsString> cl = DBUtils.readAll(UnitsString.class);
        for (UnitsString c : cl) {
            cmbUnitcombo.addRow(new Object[]{c.getId(), c.getValue() + ""});
        }
    }

    private void initCmbVendor() throws Exception {
        /* Vendor Combo */
        cmbVendor.init();
        List<Vendor> vl = DBUtils.readAll(Vendor.class);
        for (Vendor vendor : vl) {
            cmbVendor.addRow(new Object[]{vendor.getId(), vendor.getName(), vendor.getAddress()});
        }
    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            JButton btnReadAll = new JButton("Read All");
            buttonPanel.add(btnReadAll);

            JButton btnNew = new JButton("New");
            buttonPanel.add(btnNew);

            JButton btnDeleteThis = new JButton("Delete This");

            JButton btnModify = new JButton("Modify");
            buttonPanel.add(btnModify);
            buttonPanel.add(btnDeleteThis);

            JButton btnCancel = new JButton("Cancel");
            buttonPanel.add(btnCancel);
        }
        return buttonPanel;
    }


    public final void enableDisableComponents() {
        v.resetErrors();
    }

    private void setModelIntoForm(Item bro) {
        txtPurchaseordernumber.setText(bro.getPurchaseOrderNo());
        txtName.setText(bro.getName());
        txtPananumber.setText(bro.getPanaNumber());
        cmbCategory.selectItem(bro.getCategory().getId());
        txtPartsnumber.setText(bro.getPartsNumber());
        txtSerialnumber.setText(bro.getSerialNumber());
        txtRacknumber.setText(bro.getRackNo());
        txtPurDate.setDate(bro.getPurchaseDate());
        cmbVendor.selectItem(bro.getVendor().getId());
        cmbUnitcombo.selectItem(bro.getUnitsString().getId());
        BigDecimal total = bro.getRate().multiply(new BigDecimal(bro.getQuantity()));
        txtTotal.setText(total.toString());
        txtKhatanumber.setText(bro.getKhataNumber());
        txtDakhilanumber.setText(bro.getDakhilaNumber());
    }


    private JPanel getUpperFormPanel() {
        if (formPanel == null) {
            formPanel = new JPanel();

            formPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "New Item Information Entry", TitledBorder.LEADING,
                    TitledBorder.TOP, null, null));
            formPanel.setBounds(10, 49, 474, 135);
            formPanel.setLayout(new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(90dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("left:max(137dlu;default)"),
                    FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("left:max(49dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("left:max(56dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(137dlu;default)"), FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("max(29dlu;default)"), FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("max(26dlu;default):grow"),},
                    new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(11dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("top:max(15dlu;default)"),
                            FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(55dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,}));

            JLabel lblPurchaseOrderNumber = new JLabel("Purchase Order Number");
            formPanel.add(lblPurchaseOrderNumber, "4, 2");

            txtPurchaseordernumber = new JTextField();
            formPanel.add(txtPurchaseordernumber, "8, 2, fill, default");
            txtPurchaseordernumber.setColumns(10);

            JLabel lblKhataNumber = new JLabel("Khata");
            formPanel.add(lblKhataNumber, "4, 4");

            txtKhatanumber = new JTextField();
            formPanel.add(txtKhatanumber, "8, 4, fill, default");
            txtKhatanumber.setColumns(10);

            JLabel lblDakhilaNumber = new JLabel("Dakhila Number");
            formPanel.add(lblDakhilaNumber, "4, 6");

            txtDakhilanumber = new JTextField();
            formPanel.add(txtDakhilanumber, "8, 6, fill, default");
            txtDakhilanumber.setColumns(10);

            JLabel lblN = new JLabel("Name");
            formPanel.add(lblN, "4, 8");

            txtName = new JTextField();
            formPanel.add(txtName, "8, 8, fill, default");
            txtName.setColumns(10);

            JLabel lblPanaNumber = new JLabel("Pana Number");
            formPanel.add(lblPanaNumber, "4, 10");

            txtPananumber = new JTextField();
            formPanel.add(txtPananumber, "8, 10, fill, default");
            txtPananumber.setColumns(10);

            JLabel lblCategory = new JLabel("Category");
            formPanel.add(lblCategory, "4, 12, default, top");

            cmbCategory = new DataComboBox();
            formPanel.add(cmbCategory, "8, 12, fill, default");

            btnNewCategory = new JButton("New");
            btnNewCategory.setEnabled(false);
            btnNewCategory.setVisible(false);
            btnNewCategory.addActionListener(e -> {

                GDialog cd = new GDialog(mainApp, "New Category Entry", true);
                CategoryPanel vp = new CategoryPanel();
                vp.changeStatusToCreate();
                cd.setAbstractFunctionPanel(vp, new Dimension(450, 600));
                cd.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        try {
                            initCmbCategory();
                        } catch (Exception ex) {
                            logger.log(System.Logger.Level.ERROR, ex.getMessage());
                        }
                    }

                    @Override
                    public void windowClosed(WindowEvent e) {
                        logger.log(System.Logger.Level.INFO, "ItemEntryPanel.getUpperFormPanel().new ActionListener() {...}.actionPerformed(...).new WindowAdapter() {...}.windowClosing()");
                    }
                });
            });
            formPanel.add(btnNewCategory, "10, 12");

            specPanelHolder = new JPanel();
            specPanelHolder.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
            formPanel.add(specPanelHolder, "4, 14, 17, 1, fill, fill");

            JLabel lblPartsNumber = new JLabel("Parts Number");
            formPanel.add(lblPartsNumber, "4, 16");

            txtPartsnumber = new JTextField();
            formPanel.add(txtPartsnumber, "8, 16, fill, default");
            txtPartsnumber.setColumns(10);

            JLabel lblQuantity = new JLabel("Quantity");
            formPanel.add(lblQuantity, "12, 16");




            JLabel lblSerialNumber = new JLabel("Serial Number");
            formPanel.add(lblSerialNumber, "4, 18");

            txtSerialnumber = new JTextField();
            formPanel.add(txtSerialnumber, "8, 18, fill, default");
            txtSerialnumber.setColumns(10);

            JLabel lblUnit = new JLabel("Unit");
            formPanel.add(lblUnit, "12, 18");

            cmbUnitcombo = new DataComboBox();
            formPanel.add(cmbUnitcombo, "16, 18, fill, default");

            JLabel lblRacknumber = new JLabel("Rack Number");
            formPanel.add(lblRacknumber, "4, 20");

            txtRacknumber = new JTextField();
            formPanel.add(txtRacknumber, "8, 20, fill, default");
            txtRacknumber.setColumns(10);

            JLabel lblRate = new JLabel("Rate");
            formPanel.add(lblRate, "12, 20");

            JLabel lblPurchaseDate = new JLabel("Purchase Date");
            formPanel.add(lblPurchaseDate, "4, 22");

            txtPurDate = new JDateChooser();
            txtPurDate.setEnabled(false);
            formPanel.add(txtPurDate, "8, 22, fill, default");

            JLabel lblTotal = new JLabel("Total");
            formPanel.add(lblTotal, "12, 22");

            txtTotal = new JTextField();
            txtTotal.setEditable(false);
            txtTotal.setFocusable(false);
            formPanel.add(txtTotal, "16, 22, fill, default");
            txtTotal.setColumns(10);

            JLabel lblPhoneNumber = new JLabel("Vendor");
            formPanel.add(lblPhoneNumber, "4, 24");

            cmbVendor = new DataComboBox();
            formPanel.add(cmbVendor, "8, 24, fill, default");

            JButton btnNewVendor = new JButton("New");
            btnNewVendor.addActionListener(e -> {
                GDialog cd = new GDialog(mainApp, "New Vendor Entry", true);
                VendorPanel vp = new VendorPanel();
                vp.changeStatusToCreate();
                cd.setAbstractFunctionPanel(vp, new Dimension(450, 600));
                cd.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        try {
                            initCmbVendor();
                            cmbVendor.setSelectedIndex(cmbVendor.getItemCount() - 1);
                        } catch (Exception ex) {
                            logger.log(System.Logger.Level.ERROR, ex);

                        }
                    }
                });
            });
            formPanel.add(btnNewVendor, "10, 24");

            btnSave = new JButton("Save");
            btnSave.addActionListener(e -> {
                btnSave.setEnabled(false);
                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() {
                        return null;
                    }

                };
                worker.addPropertyChangeListener(evt -> {
                    if ("DONE".equals(evt.getNewValue().toString())) {
                        btnSave.setEnabled(true);
                    }
                });

                worker.execute();
            });
            formPanel.add(btnSave, "18, 24, fill, default");
        }

        return formPanel;
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

    private JPanel getLowerSplitPane() {
        if (lowerPane == null) {
            lowerPane = new JPanel();
            lowerPane.setLayout(new BorderLayout());
            dataModel = new EasyTableModel(header);

            table = new BetterJTable(dataModel);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane sp = new JScrollPane(table, javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

            lowerPane.add(sp, BorderLayout.CENTER);
            table.getSelectionModel().addListSelectionListener(e -> {
                int selRow = table.getSelectedRow();
                if (selRow != -1) {
                    /**
                     * if second column doesnot have primary id info, then
                     */
                    int selectedId = (Integer) dataModel.getValueAt(selRow, 1);
                    populateSelectedRowInForm(selectedId);
                }
            });
        }
        return lowerPane;
    }

    private void populateSelectedRowInForm(int selectedId) {
        try {
            Item bro = (Item) DBUtils.getById(Item.class, selectedId);

            if (bro != null) {
                setModelIntoForm(bro);
                cmbCategory.selectItem(bro.getCategory().getId());
                cmbVendor.selectItem(bro.getVendor().getId());
                txtPurDate.setDate(bro.getPurchaseDate());
                currentSpecificationPanel.populateValues(bro.getSpecification());
            }
        } catch (Exception e) {
            logger.log(System.Logger.Level.INFO, "populateSelectedRowInForm");
        }
    }

}
