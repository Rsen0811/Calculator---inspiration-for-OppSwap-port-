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
		
		Map<String, JButton> buttons = new HashMap<String, JButton>();		
		String[] btnNames = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", 
								"+", "-", "*", "/", "=", "C", ".", "del", "(-)", "rcl"};
		for (String name : btnNames) {
			JButton t = new JButton(name);
			t.setFocusable(false);
			t.setFont(new Font("Arial", Font.BOLD, 40));
			t.setPreferredSize(new Dimension(100,75));
			t.addActionListener(c);
			buttons.put(name, t);
		}
		
        JPanel feilds = new JPanel();
        feilds.setLayout(new BoxLayout(feilds, BoxLayout.Y_AXIS));
        feilds.add(i);
        feilds.add(o);
        
        JPanel layer1 = new JPanel(); 
        layer1.add(buttons.get("C"));
        layer1.add(buttons.get("del"));
        layer1.add(buttons.get("rcl"));
        layer1.add(buttons.get("="));
        
        JPanel layer2 = new JPanel();      
        layer2.add(buttons.get("7"));        
        layer2.add(buttons.get("8"));
        layer2.add(buttons.get("9"));
        layer2.add(buttons.get("/"));

        JPanel layer3 = new JPanel();      
        layer3.add(buttons.get("4"));        
        layer3.add(buttons.get("5"));
        layer3.add(buttons.get("6"));
        layer3.add(buttons.get("*"));
        
        JPanel layer4 = new JPanel();      
        layer4.add(buttons.get("1"));        
        layer4.add(buttons.get("2"));
        layer4.add(buttons.get("3"));
        layer4.add(buttons.get("-"));
        
        JPanel layer5 = new JPanel();      
        layer5.add(buttons.get("0"));        
        layer5.add(buttons.get("."));
        layer5.add(buttons.get("(-)"));
        layer5.add(buttons.get("+"));
        
   
        JPanel main = new JPanel();
        main.add(feilds);
        main.add(layer1);
        main.add(layer2);
        main.add(layer3);
        main.add(layer4);
        main.add(layer5);

        
        frame.add(main);        
        frame.setSize(500, 700);
        frame.show();
        
	}
	
	public Calculator() {
		System.out.println("Calculator Running");
	}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("hit");
	}
}
