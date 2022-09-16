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
	static JTextField o;
	static JTextField i;
	
	public static void main(String[] args) {
		KeyListener listener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent event) {
            	System.out.println(event.getKeyChar());
                event.consume();
            }
 
            @Override
            public void keyReleased(KeyEvent event) {
 
            }
 
            @Override
            public void keyTyped(KeyEvent event) {
                
            }
		};
		frame = new JFrame("calculator");
		
		Calculator c = new Calculator();
		
		o = new JTextField("MEMES TEST", 18);
		o.setPreferredSize(new Dimension(250, 100));
		o.setHorizontalAlignment(JTextField.RIGHT);
		o.setFont(new Font("Monospace", Font.BOLD, 30));
		o.setEditable(false);
		
		i = new JTextField("HIGH TEST", 40);		
		i.setPreferredSize(new Dimension(250, 30));		
		i.setHorizontalAlignment(JTextField.RIGHT);		
		i.setFont(new Font("Monospace", Font.BOLD, 15));		
		i.setEditable(false);
		i.addKeyListener(listener);
		
        JPanel panel = new JPanel();
        panel.add(i);
        panel.add(o);
        
        
        frame.add(panel);
        
        frame.setSize(500, 700);
        frame.show();
        
	}
	
	public Calculator() {
		System.out.println("Calculator Running");
	}
	
	public void actionPerformed(ActionEvent e) {
		System.out.print("hit");
	}
}
