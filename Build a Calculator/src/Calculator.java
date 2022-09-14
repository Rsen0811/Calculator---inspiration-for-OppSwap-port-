/**
 * 
 * Use this class to handle the main features of your application.
 * You should add additional classes as appropriate to support good modularity and reduce redundancy.
 *
 */
//import java.utils.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class Calculator extends JFrame implements ActionListener {
	static JFrame frame;
	private String sTextFeild = "";
	
	public static void main() {
		frame = new JFrame("calculator");
		
		Calculator c = new Calculator();		
        
        JPanel panel = new JPanel();     
        frame.add(panel);
        
        frame.setSize(200, 220);
        frame.show();
	}
	
	public Calculator() {
		System.out.println("Calculator Running");
	}
	
	public void actionPerformed(ActionEvent e) {
		System.out.print("hit");
	}
}
