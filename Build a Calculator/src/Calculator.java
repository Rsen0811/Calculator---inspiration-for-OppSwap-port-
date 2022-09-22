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
	
	private static ArrayList<String> history;
	private static int historyIndex;
	
	private static String otext = "";
	static JTextField o;
	static JTextField i;
	
	public static void main(String[] args) {
		KeyListener listener = new KeyListener() {
            
            @Override
            public void keyPressed(KeyEvent event) {
            	char k = event.getKeyChar();
            	if (event.getKeyCode() == 38) {
            		history(true);
            	} else if (event.getKeyCode() == 40) {
            		history(false);
            	}

            	otext = append(otext, k);

            	o.setText(otext.replace('~', '-'));
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
		history = new ArrayList<>();
		history.add("No Prior History");
		historyIndex = 0;
	}
	
	public void actionPerformed(ActionEvent event) {
		String b = event.getActionCommand();
    	if (b.equals("(-)"))  b = "n";
    	if (b.equals("rcl"))  b = "r";
    	if (b.equals("C"))  b = "c";
    	
		otext = append(otext, (b.equals("del")) ? (char) 8 : b.toCharArray()[0]);
		o.setText(otext.replace('~', '-'));
	}
	
	private static String append(String s, char k) {
		System.out.println(k);
		
		String nums = "~0123456789.r";//()";
		String ops = "-+*/";
		String specials = "cr";
		
		if (k == '=' || (int) k == 10) {			
			if(s.length() > 0 && s.charAt(0) == 'I') {
				otext = "";
				o.setText("");
			}
			return doubleString(equals());		
		}
		
		if (k == 'c' || k == 'C') return "";
		if (k == 'n' || k == 'N') {
			if (s.length() == 0 || ops.indexOf(s.charAt(s.length()-1)) != -1) {
				return s + " ~";
			}
				
		} else if ((int) k == 8) {
    		String t = s.substring(0, Math.max(0, s.length()-1));
    		return t.trim();
    		
    	} else if (nums.indexOf(k) != -1) {
			String recall = (history.size() > 1) ? history.get(historyIndex).split("= ")[1] : "";
			
    		if (s.length() == 0 || nums.indexOf(s.charAt(s.length()-1)) != -1) {
				return s + ((k == 'r') ? recall : k);
				
			} else if (ops.indexOf(s.charAt(s.length()-1)) != -1) {
				return s + " " + ((k == 'r') ? recall : k);
			}
				
		} else if (ops.indexOf(k) != -1) {
			if (s.length() != 0) {
				if (nums.indexOf(s.charAt(s.length()-1)) != -1) {
					return s + " " + k;
				} else if (ops.indexOf(s.charAt(s.length()-1)) != -1) {
					return s.substring(0, s.length()-1) + k;
				}
			}
		}
    	
    	return s;
	}
	
	private static void history(Boolean up) {
		if (up) {
			if (historyIndex > 0) {
				historyIndex --;
				i.setText(history.get(historyIndex));
			}				
		} else {
			if (historyIndex < history.size()-1) {
				historyIndex ++;
				i.setText(history.get(historyIndex));
			}
		}
	}
	
	private static double equals() {
		String ftext = otext.replace('~', '-');
		double ans = Communicator.getNum(ftext);
		String eq = ftext + " = " + doubleString(ans); 
		i.setText(eq);
		history.add(eq);
		historyIndex = history.size()-1;
		return ans;
	}
	
	private static String doubleString(double d) {
		if (d % 1.0 != 0)
		    return String.format("%s", d);
		else
		    return String.format("%.0f", d);
	}
}
