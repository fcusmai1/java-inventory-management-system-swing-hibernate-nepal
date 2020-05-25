package com.gt.uilib.components.table;

import javax.swing.table.DefaultTableModel;

/**
 * IMP: by default- second column contains key, but it is adjustable by setKeyColumn()
 *
 * @author gtiwari333@gmail.com
 */
public class EasyTableModel extends DefaultTableModel {
    private static final long serialVersionUID = 8952987986047661236L;
    protected String[] header;
    private int keyIdColumn = 1;

    protected EasyTableModel() {
    }

    public EasyTableModel(String[] header) {
        this.header = header;
        for (String aHeader : header) {
            addColumn(aHeader);
        }
    }

    public final Integer getKeyAtRow(int row) {
        return (Integer) getValueAt(row, keyIdColumn);
    }

    public final void setKeyColumn(int keyCol) {
        this.keyIdColumn = keyCol;
    }

    /**
     * on the assumption that second column contains key
     *
     * @param key
     * @return
     */
    public final boolean containsKey(Integer key) {

        int rowC = getRowCount();
        for (int i = 0; i < rowC; i++) {
            Integer keyAtRowI = getKeyAtRow(i);
            if (keyAtRowI.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public final void removeRowWithKey(Integer key) {
        if (key == null || key < 0) {
            return;
        }
        int index = 0;

        int rowC = getRowCount();
        for (int i = 0; i < rowC; i++) {
            Integer keyAtRowI = getKeyAtRow(i);
            if (keyAtRowI.equals(key)) {
                removeRow(index);
                return;
            }
            index++;
        }

    }
    @Override
    public final void addRow(Object[] values) {
        super.addRow(values);
    }

    public final void resetModel() {
        super.setRowCount(0);
    }

}
