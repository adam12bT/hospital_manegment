package hospital_assingma;

import javax.swing.*;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

public class HospitalPanel extends JPanel {

    String[] labelNames = { "Name:", "Age:", "Time:","comment:" };
    JTextField[] textFields = new JTextField[labelNames.length];
    String[] getText = new String[labelNames.length];
    Choice c = new Choice();
    private MyQueue patients = new MyQueue();
    private  MyStack patientsStack=new MyStack();
    private Container parentContainer;//next panel


    private boolean errorDisplayed = false; 
    public HospitalPanel(Container parentContainer) {

        this.parentContainer = parentContainer;


        tthetextfiledandlabels();
    }

    public void tthetextfiledandlabels() {
        setPreferredSize(new Dimension(400, 500));

        ImageIcon gifIcon = new ImageIcon("C:\\Users\\adamb\\eclipse-workspace\\hospital_assingma\\res\\Medable - Swipe.gif");
        Image gifImage = gifIcon.getImage();
        Image scaledGifImage = gifImage.getScaledInstance(600, 500, Image.SCALE_DEFAULT); // Scale the image to 500x400

        ImageIcon scaledGifIcon = new ImageIcon(scaledGifImage);
        JLabel gifLabel = new JLabel(scaledGifIcon);
        gifLabel.setBounds(0, 0, 400, 500); 

        add(gifLabel);

        Timer gifTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(gifLabel);

                revalidate();
                repaint();
                setPreferredSize(new Dimension(500, 400));
                parentContainer.revalidate();
                parentContainer.repaint();

            }
        });

        gifTimer.setRepeats(false); 
        gifTimer.start();
    	
    	
    	
    	
        JButton button = new JButton("Submit");
        button.setBounds(50,450, 80, 30);
        JButton next = new JButton("next");
       next.setBounds(275,450, 80, 30);
        
        
        Color customColor = new Color(131, 159, 166);
        setBackground(customColor);

        JLabel label2 = new JLabel("The hospital management");
        label2.setBounds(150, 10, 200, 30);

        setLayout(null); 

        int yCoordinate = 50;

        for (int i = 0; i < labelNames.length; i++) {
            JLabel label = new JLabel(labelNames[i]);
            label.setBounds(50, yCoordinate, 100, 30);
            add(label);

            textFields[i] = new JTextField();
            textFields[i].setBounds(150, yCoordinate, 200, 30);

           

            add(textFields[i]);
            yCoordinate += 80;
        }

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 errorDisplayed = false;

                getthedatafromthetext();
                if (!chekthetextfild()) {
                    addPatientToList();
                }
                chekthetextfild();
                settextempty();
            }

		
        });
        
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           	 removeAll(); 

                ImageIcon gifIcon = new ImageIcon("C:\\Users\\adamb\\eclipse-workspace\\hospital_assingma\\res\\Pass me my happy pills.gif");
                Image gifImage = gifIcon.getImage();
                Image scaledGifImage = gifImage.getScaledInstance(400, 500, Image.SCALE_DEFAULT);
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
                        parentContainer.remove(HospitalPanel.this);

                        parentContainer.repaint();
                    }
                });

                gifTimer.setRepeats(false); 
                gifTimer.start();

                // Create and switch to the new panel
                TablePanel anotherPanel = new TablePanel(patients, parentContainer, patientsStack);
                parentContainer.add(anotherPanel);
                parentContainer.revalidate();
                parentContainer.repaint();
                anotherPanel.addPatientsListToTable();
            }
        });


        
        
        add(label2);
        add(button);
        add(next);

        ChoiceExample1();
    }
  public void getthedatafromthetext() {
        if (!errorDisplayed) { 
            for (int i = 0; i < labelNames.length; i++) {
                getText[i] = textFields[i].getText();

            }
        }
    }




  public boolean chekthetextfild() {
	    boolean hasError = false;

	    if (!errorDisplayed) {
	        for (int i = 0; i < labelNames.length; i++) {
	            String input = getText[i];
	            if (input.isEmpty()) {
	                JOptionPane.showMessageDialog(this, labelNames[i] + " cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
	                hasError = true;
	                errorDisplayed = true; 
	            } else if (i == 1 && !isInteger(input)) {
	                JOptionPane.showMessageDialog(this, labelNames[i] + " must be an integer! ", "Error", JOptionPane.ERROR_MESSAGE);
	                hasError = true;
                    textFields[1].setText("");

	                errorDisplayed = true; 
	            } else if (i == 2 && !isValidTime(input)) {
	                JOptionPane.showMessageDialog(this, labelNames[i] + " must be a valid time (HH:mm)!", "Error", JOptionPane.ERROR_MESSAGE);
	                hasError = true;
	                errorDisplayed = true; 
                    textFields[2].setText("");

	            }
	        }
	        if (c.getSelectedItem().equals("----")) {
	            JOptionPane.showMessageDialog(this, "You have to choose a categorie", "Error", JOptionPane.ERROR_MESSAGE);
	            hasError = true;
	            errorDisplayed = true; 
	        }
	    }
	    return hasError;
	}

  private boolean isInteger(String str) {
	    if (str == null || str.isEmpty()  || str.length()>4 ) {
	        return false;
	    }

	    int length = str.length();
	    int startIndex = 0;



	    for (int i = startIndex; i < length; i++) {
	        char c = str.charAt(i);
	        if (!Character.isDigit(c)) {
	            return false; 
	        }
	    }

	    return true; 
	}

	private boolean isValidTime(String time) {
	    if (time.length() != 5) {
	        return false; 
	    }

	    String[] parts = time.split(":");
	    if (parts.length != 2) {
	        return false;
	    }

	    try {
	        int hours = Integer.parseInt(parts[0]);
	        int minutes = Integer.parseInt(parts[1]);

	        if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
	            return false; 
	        }
	    } catch (NumberFormatException e) {
	        return false; 
	    }

	    return true; 
	}






    public void settextempty() {
        if (!errorDisplayed) { 
            c.removeAll();
            unit();
            for (int i = 0; i < labelNames.length; i++) {
                textFields[i].setText("");
            }
        }
    }


    public void ChoiceExample1() {
        JLabel label5 = new JLabel("Category");
        label5.setBounds(50, 288+80, 80, 30);
        c.setBounds(150, 290+80, 200, 30);
        unit();
        add(c);
        add(label5);
    }

    public void unit() {
        c.add("----");
        c.add("Cardiology");
        c.add("Neurosurgery");
        c.add("Family Medicine");
        c.add("Ophthalmology");
        c.add("Orthodontics");
    }
    public void addPatientToList() {
        if (!errorDisplayed) { 
            int age = Integer.parseInt(getText[1]);
            String name = getText[0];
            String time = getText[2];
        	String categorie=c.getSelectedItem();
        	String commment=getText[3];

            patients.enqueue(new patient(age, name, categorie, time, commment));
            System.out.println("Patient added: " + name);
        }

    }
}