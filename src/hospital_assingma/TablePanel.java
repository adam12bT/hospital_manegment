package hospital_assingma;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;


public class TablePanel extends JPanel {
    private JTable table;
    private Container parentContainer;

    private int id = 1;
    private DefaultTableModel tableModel;
    private MyQueue patients;
    private MyStack patientsStack; 

    public TablePanel(MyQueue patients, Container parentContainer,MyStack patientsStack) {
        this.patients = patients;
        this.patientsStack =patientsStack ; 
        this.parentContainer = parentContainer; 
        initializeTable();
        setPanelBackground();
        addMouseListenerToTable();
    }








    private void initializeTable() {
        setLayout(null);
        setPreferredSize(new Dimension(500, 500));
        
        JLabel label2 = new JLabel("The task to do");
        label2.setBounds(160, -5, 200, 30);
add(label2);

        String[] columns = {"id", "Name", "Age", "Category", "Time", "todo"};
        tableModel = new DefaultTableModel(new Object[0][], columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
              return false;
            }
        };

        table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JTextField()));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 20, 400, 300);

        add(scrollPane);
        

        addPatientsListToTable();
        JButton next = new JButton("next");
       next.setBounds(275,450, 80, 30);
       JButton order = new JButton("order");
       order.setBounds(50,450, 80, 30);
       add(order);
       order.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
        	   promptAndReorder();
           }
       });
       add(next);


next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           	 removeAll(); 

                ImageIcon gifIcon = new ImageIcon("C:\\Users\\adamb\\eclipse-workspace\\hospital_assingma\\res\\Pass me my happy pills.gif");
                Image gifImage = gifIcon.getImage();
                Image scaledGifImage = gifImage.getScaledInstance(600, 500, Image.SCALE_DEFAULT);
                ImageIcon scaledGifIcon = new ImageIcon(scaledGifImage);
                JLabel gifLabel = new JLabel(scaledGifIcon);
                gifLabel.setBounds(0, 0, 400, 500);
                add(gifLabel);

                Timer gifTimer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	 remove(gifLabel);
                        
                        revalidate();
                        repaint();

                        parentContainer.revalidate();
                        parentContainer.remove(TablePanel.this);

                        parentContainer.repaint();
                    }
                });

                gifTimer.setRepeats(false); 
                gifTimer.start();

    	        tabelpanel2 panel2 = new tabelpanel2(  patientsStack, parentContainer,patients);
    	        parentContainer.add(panel2);

    	        parentContainer.revalidate();
    	        parentContainer.repaint();
            }
        });

    }

    private void setPanelBackground() {
        Color customColor = new Color(131, 159, 166);
        setBackground(customColor);
    }

    public void addPatientsListToTable() {
        for (patient p : patients.getQueue()) {
            if (!isPatientInTable(p.getName())) {
                addPatientToTable(p.getName(), p.getAge(), p.getCategorie(), p.getTime(), p.getComment());
            }
        }
    }

    private boolean isPatientInTable(String name) {
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            if (name.equals(tableModel.getValueAt(row, 1))) {
                return true;
            }
        }
        return false;
    }

    private void addPatientToTable(String name, int age, String category, String time, String comment) {
        Object[] rowData = {id, name, age, category, time, "done"};
        tableModel.addRow(rowData);
        id++;
    }



    private void addMouseListenerToTable() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                if (row >= 0 && col == 5) {
                    String name = (String) table.getValueAt(row, 1);
                    int age = (int) table.getValueAt(row, 2);
                    String category = (String) table.getValueAt(row, 3);
                    String time = (String) table.getValueAt(row, 4);
                    String comment = getCommentForName(name);

                    patient patientToMove = new patient(age,name, category,time,comment);

                    patientsStack.push(patientToMove);
                	
                	
                	
                	removeRow(row);
                    
                    
                } else if (row >= 0 && col == 1) {
                    String name = (String) table.getValueAt(row, col);
                    String comment = getCommentForName(name);
                    showCommentPopup(comment);
                }
            }
        });
    }

    private void removeRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < tableModel.getRowCount()) {
            String name = (String) tableModel.getValueAt(rowIndex, 1);
            patients.removePatient(name);
            tableModel.removeRow(rowIndex);
        }
    }
    private String getCommentForName(String name) {
        for (patient p : patients.getQueue()) {
            if (p.getName().equals(name)) {
                return p.getComment();
            }
        }
        return "Comment not found for " + name;
    }

    private void showCommentPopup(String comment) {
        JOptionPane.showMessageDialog(TablePanel.this, "Comment: " + comment, "Patient Comment", JOptionPane.INFORMATION_MESSAGE);
    }
    private void promptAndReorder() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int age = (int) tableModel.getValueAt(selectedRow, 2);
            String name = (String) tableModel.getValueAt(selectedRow, 1);
            String category = (String) tableModel.getValueAt(selectedRow, 3);
            String time = (String) tableModel.getValueAt(selectedRow, 4);
            String comment = getCommentForName(name);

            String newPositionStr = JOptionPane.showInputDialog(TablePanel.this, "Enter the new position for the task '" + name + "':");
            if (newPositionStr != null && !newPositionStr.isEmpty()) {
                try {
                    int newPosition = Integer.parseInt(newPositionStr);
                    if (newPosition >= 0 && newPosition < patients.getQueue().size()) {
                        patients.removePatient(name);
                        id=1;
                        patients.addPatient(newPosition, new patient(age, name, category, time, comment));
                        refreshTable();
                    } else {
                        JOptionPane.showMessageDialog(TablePanel.this, "Invalid position. Please enter a position between 0 and " + (patients.getQueue().size() - 1) + ".", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(TablePanel.this, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(TablePanel.this, "Please select a task to reorder.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        addPatientsListToTable();
    }





}

