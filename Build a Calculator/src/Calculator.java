/**
 * 
 * Use this class to handle the main features of your application.
 * You should add additional classes as appropriate to support good modularity and reduce redundancy.
 *
 */
//import java.utils.*;
import java.awt.event.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

import javax.swing.*;
import java.awt.*;


public class Calculator extends JFrame implements ActionListener {	
	static JFrame frame; // calculator window
	
	private ArrayList<String> history;
	private int historyIndex;
	
	private static String otext = "";
	private JTextField o; // answer text
	private JTextField i; // input text
	
	
	private KeyListener listener = new KeyListener() {           
		@Override
        public void keyPressed(KeyEvent event) {
        	char k = event.getKeyChar();
        	if (event.getKeyCode() == 38) { // up
        		scrollHistory(true);
        	} else if (event.getKeyCode() == 40) { // down
        		scrollHistory(false);
        	}

        	otext = append(otext, k); // Modify input text with keyboard
        	
        	// format placeholders for ln, log, and negative nums
        	o.setText(otext.replace('~', '-')
    				.replaceAll("l", "ln(").replaceAll("T", "log(")
    				.replaceAll("U", "Undefined").replaceAll("S", "Syntax Error")
    				.replaceAll("Infinity", "Overflow"));
        	event.consume();
        }
		
		// unused overrides
        @Override
        public void keyReleased(KeyEvent event) {
        	return;
        } 
 
        @Override
        public void keyTyped(KeyEvent event) {
             return;   
        }
	};{ // the class that is attached to the keylistener
		
	frame = new JFrame("calculator"); // Instantiates the frame as part of the calculator
	
	// adds and formats output and intput textfeilds
	o = new JTextField("MEMES TEST", 18);
	o.setPreferredSize(new Dimension(250, 100));
	o.setHorizontalAlignment(JTextField.RIGHT);
	o.setFont(new Font("Monospace", Font.BOLD, 30));
	o.setEditable(false);
	o.setBorder(null);
	
	i = new JTextField("HIGH TEST", 40);		
	i.setPreferredSize(new Dimension(250, 30));		
	i.setHorizontalAlignment(JTextField.RIGHT);		
	i.setFont(new Font("Monospace", Font.BOLD, 15));		
	i.setEditable(false);
	i.addKeyListener(listener);
	i.setBorder(null);
	
	// used hashmap to store all references to buttons
	// each string contains button label, and button type (Function, operation, number)
	Map<String, JButton> buttons = new HashMap<String, JButton>();		
	String[] btnNames = {"0 1", "1 1", "2 1", "3 1", "4 1", "5 1", "6 1", "7 1", "8 1", "9 1", 
					"+ 2", "- 2", "* 2", "/ 2", "= 2", "C 0", ". 1", "del 0", "(-) 1", 
					"rcl 0", "▲ 0", "▼ 0", "( 2", ") 2", "^ 2", "ln 2", "log 2", "! 2", "% 2"};
	
	// automated the formatting of all buttons
	Color[] cs = {Color.LIGHT_GRAY, Color.WHITE, Color.orange};
	for (String button : btnNames) {
		String[] btext = button.split(" ");
		
		JButton t = new JButton(btext[0]);
		t.setFocusable(false); // to not unfocus from keyboard
		t.setFont(new Font("Arial", Font.BOLD, 40));
		t.setPreferredSize(new Dimension(110,70));		
		t.setBackground(cs[Integer.parseInt(btext[1])]); // set color dependent on button type
		
		// special formatting for compound scroll button
		if (btext[0].equals("▲") || btext[0].equals("▼")) {
			t.setPreferredSize(new Dimension(110,35));
			t.setFont(new Font("Arial", Font.BOLD, 28));
		}	
		
		// adds to action listener and stores reference in hashmap
		t.addActionListener(this);
		buttons.put(btext[0], t);
	}
	
	//separate sub-widget for displays
    JPanel feilds = new JPanel();
    feilds.setLayout(new BoxLayout(feilds, BoxLayout.Y_AXIS));
    feilds.add(i);
    
    // interfeild buffer
    JPanel buffer1 = new JPanel();
    buffer1.setBackground(new Color(220, 220, 220));
    feilds.add(buffer1);    
    feilds.add(o);
    
    // buffer at bottom of feilds
    JPanel buffer2 = new JPanel();
    buffer2.setBackground(Color.black);
    buffer2.setPreferredSize(new Dimension(500, 50));
    feilds.add(buffer2);
    feilds.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
    
    // sub-widget for function buttons (top row)
    JPanel function = new JPanel();
    function.setBackground(Color.BLACK);
    
    // compound scroll button
    JPanel scroll = new JPanel();
    scroll.setLayout(new GridLayout(2,1));
    scroll.add(buttons.get("▲"));
    scroll.add(buttons.get("▼"));
    
    // add function buttons and scroll compound button to first layer
    function.add(buttons.get("C"));
    function.add(buttons.get("del"));
    function.add(buttons.get("rcl"));
    function.add(scroll);
    
    // first layer of buttons
    JPanel layer0 = new JPanel();
    layer0.setBackground(Color.BLACK);
    layer0.add(buttons.get("ln"));
    layer0.add(buttons.get("log"));
    layer0.add(buttons.get("^"));
    layer0.add(buttons.get("%"));
    
    // second layer of buttons
    JPanel layer1 = new JPanel();
    layer1.setBackground(Color.BLACK);
    layer1.add(buttons.get("("));
    layer1.add(buttons.get(")"));
    layer1.add(buttons.get("!"));
    layer1.add(buttons.get("*"));
    
    // third layer of buttons
    JPanel layer2 = new JPanel();
    layer2.setBackground(Color.BLACK);      
    layer2.add(buttons.get("7"));        
    layer2.add(buttons.get("8"));
    layer2.add(buttons.get("9"));
    layer2.add(buttons.get("/"));

    // fourth layer of buttons
    JPanel layer3 = new JPanel();
    layer3.setBackground(Color.BLACK);      
    layer3.add(buttons.get("4"));        
    layer3.add(buttons.get("5"));
    layer3.add(buttons.get("6"));
    layer3.add(buttons.get("+"));
    
    // fifth layer of buttons
    JPanel layer4 = new JPanel();
    layer4.setBackground(Color.BLACK);     
    layer4.add(buttons.get("1"));        
    layer4.add(buttons.get("2"));
    layer4.add(buttons.get("3"));
    layer4.add(buttons.get("-"));
    
    // sixth layer of buttons
    JPanel layer5 = new JPanel();  
    layer5.setBackground(Color.BLACK);    
    layer5.add(buttons.get("0"));        
    layer5.add(buttons.get("."));
    layer5.add(buttons.get("(-)"));
    layer5.add(buttons.get("="));
        
    // main panel to fit all buttons together
    JPanel main = new JPanel();
    main.setBackground(Color.BLACK);
    main.setLayout(new GridLayout(8,1)); // fits all sub-widgets to a grid format
    
    main.add(feilds);
    main.add(function);
    main.add(layer0);
    main.add(layer1);
    main.add(layer2);
    main.add(layer3);
    main.add(layer4);
    main.add(layer5);
    
    // adds the main panel to frame to be displayed
    frame.add(main);        
    frame.setSize(500, 700); // pixels
    frame.show();
	}

	// calculator constructor
	public Calculator() {
		System.out.println("Calculator Running");
		history = new ArrayList<>();
		history.add("No Prior History");
		historyIndex = 0;
	}
	
	// code to handle gui button presses
	public void actionPerformed(ActionEvent event) {
		String b = event.getActionCommand();
    	// reformats special buttons to one-char codes
		if (b.equals("(-)"))  b = "n";
    	if (b.equals("rcl"))  b = "r";
    	if (b.equals("C"))  b = "c";
    	if (b.equals("ln"))  b = "l";
    	if (b.equals("log"))  b = "T";
    	
    	// handles scrolling
    	if (b.equals("▲") || b.equals("▼")) {
    		scrollHistory(b.equals("▲"));
    	}
    	
    	// changes input depending on button press
		otext = append(otext, (b.equals("del")) ? (char) 8 : b.toCharArray()[0]);
		o.setText(otext.replace('~', '-')
				.replaceAll("l", "ln(").replaceAll("T", "log(")
				.replaceAll("U", "Undefined").replaceAll("S", "Syntax Error")
				.replaceAll("Infinity", "Overflow"));
	}
	
	private String append(String s, char k) {
		System.out.println(k);
		// special types of buttons classified (with exceptions)
		String nums = "~.lT()!0123456789"; // can all be grouped as numbers
		String semiNums = "~.lT()!"; // can only proceed numbers, not follow (exceptions)
		String ops = "-+*/^%"; // operations cant follow each other, and separeted by space
		
		if (k == 't') k = 'T';// t is already used in Infinity
		
		// if infinity/overflow, SyntaxError, or Undefined, always clear before next keypress
		if(k != 'c' && s.length() > 0 && (s.charAt(0) == 'I' 
				|| s.charAt(0) == 'U' || s.charAt(0) == 'S')) {
			s = append(s, 'c');
		}
		
		// Calculate and display answer
		if (k == '=' || (int) k == 10) {			
			if (s.length() == 0) return "";		
			try { // catch syntax error or NaN error
				return doubleString(equals());	
			} catch (Exception e) {
				return e.getMessage().substring(0, 1);
			}
		}
		
		if (k == 'c') return ""; // clear case
		if (k == 'n') { // negative case
			// for no terms, or after an operator
			if (s.length() == 0	|| ops.indexOf(s.charAt(s.length()-1)) != -1) {
				return s + " ~";
			}
			
			// after parenthesis or logs
			if (s.charAt(s.length()-1) == '(' || s.charAt(s.length()-1) == ')'
				|| s.charAt(s.length()-1) == 'T' || s.charAt(s.length()-1) == 'l') return s + "~";	
			
		} else if (k == '!') {
			if (s.length() != 0 && nums.indexOf(s.charAt(s.length()-1)) > 4) { // end parenthesis or number
				return s + k;
			} return s; // else don't update
			
		} else if (k == 'l' || k == 'T') { // logs case
			// can be negative
			if (s.length() != 0	&& s.charAt(s.length()-1) == '~') return s + k;
			// else if after operator or first character in input
			if (s.length() == 0	|| ops.indexOf(s.charAt(s.length()-1)) != -1) {
				return s + " " + k;
			}
			// or after open parenthesis, including other logs
			if (s.charAt(s.length()-1) == '(' || s.charAt(s.length()-1) == 'l' 
					|| s.charAt(s.length()-1) == 'T') return s + k;
			
		} else if (k == 'r') { // recall button
			if(s.length() == 0 || semiNums.indexOf((s.charAt(s.length()-1))) > 3) { // >3 -> ()
				return s + recall().trim(); // trimmed as parenthesis don't need spaces and neither 
											// does the start of a line
			}
			
			// only works otherwise if following an operator
			if (ops.indexOf(s.charAt(s.length()-1)) != -1) {
				return s + recall();
						
			}
			
		} else if ((int) k == 8) {
			// deletes last character
    		String t = s.substring(0, Math.max(0, s.length()-1));
    		return t.trim(); // removes extra spaces
    		
    	} else if (nums.indexOf(k) != -1) {	// if its a number
    		if (s.length() == 0 || nums.indexOf(s.charAt(s.length()-1)) != -1) {
    			// no double decimals
				if (s.length() != 0 && k == '.' && s.charAt(s.length()-1) == '.') return s; 
    			return s + k; // when space not needed
				
			} else if (ops.indexOf(s.charAt(s.length()-1)) != -1) {
				return s + " " + k; // when space needed
			}
				
		} else if (ops.indexOf(k) != -1) {			
			if (s.length() != 0) {	
				if (ops.indexOf(s.charAt(s.length()-1)) != -1) { // opswap (swaps operators)
					return s.substring(0, s.length()-1) + k;
					
				}
				
				if (nums.indexOf(s.charAt(s.length()-1)) < 5) { // open parenthesis, !, or nums 
					return s; // cant operate on an open parenthesis
				}
				
				// if preceded by num or seminums: (, ), and !
				if (nums.indexOf(s.charAt(s.length()-1)) != -1
						|| semiNums.indexOf((s.charAt(s.length()-1))) > 3) {
					return s + " " + k;
					
				}
			}
		}
    	
    	return s; // else don't update if invalid key/button
	}
	
	// scrolls up if true, down if false
	private void scrollHistory(Boolean up) {
		// makes sure it doesn't exceed bounds
		if (up) {
			if (historyIndex > 0) {
				historyIndex --;
				i.setText(history.get(historyIndex).replaceAll("Infinity", "Overflow"));
			}				
		} else {
			if (historyIndex < history.size()-1) {
				historyIndex ++;
				i.setText(history.get(historyIndex).replaceAll("Infinity", "Overflow"));
			}
		}
	}
	
	// communicates with math part of the calculator
	private double equals() throws Exception {
		// formats placeholders for logs and negatives, and special keywords
		String ftext = otext.replace('~', '-')
				.replaceAll("l", "ln(").replaceAll("T", "log(")
				.replaceAll("U", "Undefined").replaceAll("S", "Syntax Error")
				.replaceAll("Infinity", "Overflow");
		double ans = Double.NaN; // starts with NaN
		
		try { 
			ans = Communicator.getNum(ftext); // send string to get output
		} catch (Exception e) { // only breaks if syntax was incorrect
			throw new Exception("Syntax Error"); // sends special exception
		}
		
		if (Double.isNaN(ans)) throw new Exception("Undefined"); // for undefined values
		
		// updates input/history textbox
		String eq = ftext + " = " + doubleString(ans); 
		i.setText(eq.replaceAll("Infinity", "Overflow"));
		history.add(eq);
		historyIndex = history.size()-1;
		return ans;
	}
	
	// recalls from history
	private String recall() {
		// only recalls answers that are real numbers
		String recall = (history.size() > 1 && !history.get(historyIndex).contains("I")) ? 
				history.get(historyIndex).split("= ")[1] : "";
		
		return ((!recall.equals("")) ? " " + recall : "");
	}
	
	private static String doubleString(double d) {
		// if floating point
		if (Math.abs(Math.round(d)-d) > 0.0000000000001 // maximum fltpt error
				|| Math.abs(d) > 9999999) { // after 7 sig figs, use scientific notation
			DecimalFormat r = new DecimalFormat("#.############");  // rounding fltpt error
			r.setRoundingMode(RoundingMode.HALF_UP);
			d = Double.parseDouble(r.format(d).replaceAll("∞", "Infinity"));
			
			return String.format("%s", d);
		} else { // if integer
			return String.format("%.0f", d);
		}
	}
	
	// main to instantiate calculator
	public static void main(String[] args) {
		Calculator c = new Calculator();
	}
}
