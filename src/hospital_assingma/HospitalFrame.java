package hospital_assingma;

import javax.swing.JFrame;


	

	public class HospitalFrame extends JFrame {

	    public HospitalFrame() {
	        super("Hospital Assignment"); 

	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        setResizable(false);
	        getContentPane().add(new HospitalPanel(this));//add 

	        pack();
	        setLocationRelativeTo(null);

	        setVisible(true);
	    }

	
	
}
