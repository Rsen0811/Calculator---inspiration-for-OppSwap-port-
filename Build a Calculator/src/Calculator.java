/**
 * 
 * Use this class to handle the main features of your application.
 * You should add additional classes as appropriate to support good modularity and reduce redundancy.
 *
 */
//import java.utils.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import java.awt.*;


public class Calculator extends JFrame implements ActionListener {	
	static JFrame frame;
	
	private static String otext = "";
	static JTextField o;
	static JTextField i;
	
	public static void main(String[] args) {
		KeyListener listener = new KeyListener() {
            
			private final String valids = "0123456789 -+*/()";
            
            @Override
            public void keyPressed(KeyEvent event) {
            	char k = event.getKeyChar();
            	System.out.println(k);
            	if ((int) k == 8) {
            		otext = otext.substring(0, Math.max(0, otext.length()-1));            		
            	} else if (valids.contains((k + ""))) {
            		otext += k;       
            	}

            	o.setText(otext);
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
