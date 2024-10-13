package hospital_assingma;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class tabelpanel2 extends JPanel {  
    private Container parentContainer;
    private MyStack patientsStack;
    private MyQueue patients ; 

    public tabelpanel2(MyStack patientsStack,Container parentContainer,MyQueue patients) {
    	 this.parentContainer = parentContainer;
    	 
        this.patientsStack = patientsStack;
        this.patients=patients;
        createTable();  
        displayDataRecursive(patientsStack);
        //displayData();  
        setPanelBackground();
        addMouseListenerToTable(); 
        JLabel label2 = new JLabel("The completed tasks");
        label2.setBounds(150, -5, 200, 30);
add(label2);
        JButton back = new JButton("back");
        back.setBounds(275, 450, 80, 30);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           	 removeAll(); 

                // Load and display the new GIF
                ImageIcon gifIcon = new ImageIcon("C:\\Users\\adamb\\eclipse-workspace\\hospital_assingma\\res\\Pass me my happy pills.gif");
                Image gifImage = gifIcon.getImage();
                Image scaledGifImage = gifImage.getScaledInstance(600, 500, Image.SCALE_DEFAULT);
                ImageIcon scaledGifIcon = new ImageIcon(scaledGifImage);
                JLabel gifLabel = new JLabel(scaledGifIcon);
                gifLabel.setBounds(0, 0, 400, 500);
                add(gifLabel);

                // Timer to remove the GIF after a specific duration (e.g., 3000 milliseconds)
                Timer gifTimer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    	 remove(gifLabel);
                        
                        revalidate();
                        repaint();

                        parentContainer.revalidate();
                        parentContainer.remove(tabelpanel2.this);

                        parentContainer.repaint();
                    }
                });

                gifTimer.setRepeats(false); // Stop the timer after the first execution
                gifTimer.start();

                // Create and switch to the new panel
                TablePanel panel2 = new TablePanel( patients, parentContainer,patientsStack);
                parentContainer.add(panel2);
                parentContainer.revalidate();
                parentContainer.repaint();
            }
        });

    

        
        JButton viewstat = new JButton("viewstat");
        viewstat.setBounds(50, 450, 150, 30);
        viewstat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				showPopup();
				
				
				
				
			}
        	
        });
        add(back);
        add(viewstat);

    }
    private void setPanelBackground() {
        Color customColor = new Color(131, 159, 166);
        setBackground(customColor);
    }

    private JTable table;
    private DefaultTableModel tableModel = new DefaultTableModel();  // Initialize the table model

    private void createTable() {
        setLayout(null);
        setPreferredSize(new Dimension(500, 500));

        tableModel = new DefaultTableModel(new Object[0][], new String[]{ "Name", "Age", "Category", "Time","comment","done" }) {
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
    }

/*
    private void displayData() {
        // Create a temporary stack to hold the elements without removing them from the original stack
        MyStack tempStack = new MyStack();

        // Transfer elements from the original stack to the temporary stack
        while (!patientsStack.isEmpty()) {
            patient currentPatient = patientsStack.pop();
            tempStack.push(currentPatient);
        }

        // Display data in the table using the temporary stack
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        while (!tempStack.isEmpty()) {
            patient currentPatient = tempStack.pop();
            model.addRow(new Object[]{
                    currentPatient.getName(),
                    currentPatient.getAge(),
                    currentPatient.getCategorie(),
                    currentPatient.getTime(),
                    currentPatient.getComment(),
                    "undo"
            });

            // Restore the elements back to the original stack
            patientsStack.push(currentPatient);
        }
    }
    */

private void displayDataRecursive(MyStack stack) {
    if (stack.isEmpty()) {
        return; // Base case: stop when the stack is empty
    }

    patient currentPatient = stack.pop(); // Remove the top element from the original stack

    // Recursively call the method with the updated stack
    displayDataRecursive(stack);

    // Display data in the table
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.addRow(new Object[]{
            currentPatient.getName(),
            currentPatient.getAge(),
            currentPatient.getCategorie(),
            currentPatient.getTime(),
            currentPatient.getComment(),
            "undo"
    });
    displayDataRecursive(stack);


    patientsStack.push(currentPatient);
}
    
    
    

    
    private void addMouseListenerToTable() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                if (row >= 0 && col == 5) { 
                  
                    String name = (String) table.getValueAt(row, 0);
                    int age = (int) table.getValueAt(row, 1);
                    String category = (String) table.getValueAt(row, 2);
                    String time = (String) table.getValueAt(row, 3);
                    String comment = (String) table.getValueAt(row, 4);
                    System.out.println(comment);
                    patient patientToMove = new patient(age,name, category,time,comment);

                    patients.enqueue(patientToMove);                	
                    patientsStack.pop();
                	
                	removeRow(row);
                    
                    
                
            }
        };
    });
    }
    
    private void removeRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < tableModel.getRowCount()) {
            tableModel.removeRow(rowIndex);
        }
    }
 
    private void showPopup() {
        int totalTasks = patients.size() + patientsStack.size();
        int doneTasks = patientsStack.size();
        int theundonetask=patients.size();

        double percentageDone = (double) doneTasks / totalTasks * 100;

        String message = "Total tasks: " + totalTasks +"\n unDone tasks: "+theundonetask + "\nDone tasks: " + doneTasks + "\nPercentage Done: " + percentageDone + "%";
        JOptionPane.showMessageDialog(this, message, "Task Statistics", JOptionPane.INFORMATION_MESSAGE);
    }
}
