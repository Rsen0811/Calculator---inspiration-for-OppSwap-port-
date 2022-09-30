/**
 * 
 * Use this class to handle the main features of your application.
 * You should add additional classes as appropriate to support good modularity and reduce redundancy.
 *
 */
//import java.utils.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.*;

import javax.swing.*;
import java.awt.*;


public class Calculator extends JFrame implements ActionListener {	
	static JFrame frame;
	
	private ArrayList<String> history;
	private int historyIndex;
	
	private static String otext = "";
	private JTextField o;
	private JTextField i;
	
	
	KeyListener listener = new KeyListener() {           
		@Override
        public void keyPressed(KeyEvent event) {
        	char k = event.getKeyChar();
        	if (event.getKeyCode() == 38) {
        		scrollHistory(true);
        	} else if (event.getKeyCode() == 40) {
        		scrollHistory(false);
        	}

        	otext = append(otext, k);

        	o.setText(otext.replace('~', '-').replaceAll("l", "ln(").replaceAll("T", "log("));
            event.consume();
        }
 
        @Override
        public void keyReleased(KeyEvent event) {
        	return;
        } 
 
        @Override
        public void keyTyped(KeyEvent event) {
             return;   
        }
	};{
		
	frame = new JFrame("calculator");
	
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
	
	Map<String, JButton> buttons = new HashMap<String, JButton>();		
	String[] btnNames = {"0 1", "1 1", "2 1", "3 1", "4 1", "5 1", "6 1", "7 1", "8 1", "9 1", 
					"+ 2", "- 2", "* 2", "/ 2", "= 2", "C 0", ". 1", "del 0", "(-) 1", 
					"rcl 0", "▲ 0", "▼ 0", "( 2", ") 2", "^ 2", "ln 2", "log 2", "! 2", "% 2"};
	
	Color[] cs = {Color.LIGHT_GRAY, Color.WHITE, Color.orange};
	for (String button : btnNames) {
		String[] btext = button.split(" ");
		
		JButton t = new JButton(btext[0]);
		t.setFocusable(false);
		t.setFont(new Font("Arial", Font.BOLD, 40));
		t.setPreferredSize(new Dimension(110,70));		
		t.setBackground(cs[Integer.parseInt(btext[1])]);
		
		if (btext[0].equals("▲") || btext[0].equals("▼")) {
			t.setPreferredSize(new Dimension(110,35));
			t.setFont(new Font("Arial", Font.BOLD, 28));
		}	
		
		t.addActionListener(this);
		buttons.put(btext[0], t);
	}
		
    JPanel feilds = new JPanel();
    feilds.setLayout(new BoxLayout(feilds, BoxLayout.Y_AXIS));
    feilds.add(i);
    
    JPanel buffer1 = new JPanel();
    buffer1.setBackground(new Color(220, 220, 220));
    feilds.add(buffer1);    
    feilds.add(o);
    
    JPanel buffer2 = new JPanel();
    buffer2.setBackground(Color.black);
    buffer2.setPreferredSize(new Dimension(500, 50));
    feilds.add(buffer2);
    feilds.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
    
    JPanel function = new JPanel();
    function.setBackground(Color.BLACK);
    
    JPanel scroll = new JPanel();
    scroll.setLayout(new GridLayout(2,1));
    scroll.add(buttons.get("▲"));
    scroll.add(buttons.get("▼"));
    
    function.add(buttons.get("C"));
    function.add(buttons.get("del"));
    function.add(buttons.get("rcl"));
    function.add(scroll);
    
    JPanel layer0 = new JPanel();
    layer0.setBackground(Color.BLACK);
    layer0.add(buttons.get("ln"));
    layer0.add(buttons.get("log"));
    layer0.add(buttons.get("^"));
    layer0.add(buttons.get("%"));
    
    JPanel layer1 = new JPanel();
    layer1.setBackground(Color.BLACK);
    layer1.add(buttons.get("("));
    layer1.add(buttons.get(")"));
    layer1.add(buttons.get("!"));
    layer1.add(buttons.get("*"));
    
    JPanel layer2 = new JPanel();
    layer2.setBackground(Color.BLACK);      
    layer2.add(buttons.get("7"));        
    layer2.add(buttons.get("8"));
    layer2.add(buttons.get("9"));
    layer2.add(buttons.get("/"));

    JPanel layer3 = new JPanel();
    layer3.setBackground(Color.BLACK);      
    layer3.add(buttons.get("4"));        
    layer3.add(buttons.get("5"));
    layer3.add(buttons.get("6"));
    layer3.add(buttons.get("+"));
    
    JPanel layer4 = new JPanel();
    layer4.setBackground(Color.BLACK);     
    layer4.add(buttons.get("1"));        
    layer4.add(buttons.get("2"));
    layer4.add(buttons.get("3"));
    layer4.add(buttons.get("-"));
        
    JPanel layer5 = new JPanel();  
    layer5.setBackground(Color.BLACK);    
    layer5.add(buttons.get("0"));        
    layer5.add(buttons.get("."));
    layer5.add(buttons.get("(-)"));
    layer5.add(buttons.get("="));
        
   
    JPanel main = new JPanel();
    main.setBackground(Color.BLACK);
    main.setLayout(new GridLayout(8,1));
    
    main.add(feilds);
    main.add(function);
    main.add(layer0);
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
    	if (b.equals("ln"))  b = "l";
    	if (b.equals("log"))  b = "T";
    	
    	
    	if (b.equals("▲") || b.equals("▼")) {
    		scrollHistory(b.equals("▲"));
    	}
    	
		otext = append(otext, (b.equals("del")) ? (char) 8 : b.toCharArray()[0]);
		o.setText(otext.replace('~', '-').replaceAll("l", "ln(").replaceAll("T", "log("));
	}
	
	private String append(String s, char k) {
		System.out.println(k);
		
		String nums = "~.lT()!0123456789";
		String semiNums = "~.lT()!";
		String ops = "-+*/^%";
		String specials = "cr";
		
		if (k == 't') k = 'T';// t is already used in Infinity
		
		if(k != 'c' && s.length() > 0 && s.charAt(0) == 'I') {
			s = append(s, 'c');
		}
		
		if (k == '=' || (int) k == 10) {			
			if (s.length() == 0) return "";		
			return doubleString(equals());		
		}
		
		if (k == 'c') return "";
		if (k == 'n') {
			if (s.length() == 0	|| ops.indexOf(s.charAt(s.length()-1)) != -1) {
				return s + " ~";
			}
			
			if (s.charAt(s.length()-1) == '(' || s.charAt(s.length()-1) == ')'
				|| s.charAt(s.length()-1) == 'T' || s.charAt(s.length()-1) == 'l') return s + "~";	
			
		} else if (k == '!') {
			if (s.length() != 0 && nums.indexOf(s.charAt(s.length()-1)) > 4) { // end parenthesis or number
				return s + k;
			} return s;
			
		} else if (k == 'l' || k == 'T') {
			if (s.length() == 0	|| ops.indexOf(s.charAt(s.length()-1)) != -1) {
				return s + " " + k;
			}
			
			if (s.charAt(s.length()-1) == '(' || s.charAt(s.length()-1) == ')'
			|| s.charAt(s.length()-1) == 'l' || s.charAt(s.length()-1) == 'T') return s + k;
			
		} else if (k == 'r') {
			if(s.length() == 0 || semiNums.indexOf((s.charAt(s.length()-1))) > 3) { // >3 -> ()
				return s + recall().trim(); // trimmed as parenthesis don't need spaces and neither 
											// does the start of a line
			}
			
			if (ops.indexOf(s.charAt(s.length()-1)) != -1) {
				return s + recall();
						
			}
			
		} else if ((int) k == 8) {
    		String t = s.substring(0, Math.max(0, s.length()-1));
    		return t.trim();
    		
    	} else if (nums.indexOf(k) != -1) {			
    		if (s.length() == 0 || nums.indexOf(s.charAt(s.length()-1)) != -1) {
				if (s.length() != 0 && k == '.' && s.charAt(s.length()-1) == '.') return s;
    			return s + k;
				
			} else if (ops.indexOf(s.charAt(s.length()-1)) != -1) {
				return s + " " + k;
			}
				
		} else if (ops.indexOf(k) != -1) {			
			if (s.length() != 0) {	
				if (ops.indexOf(s.charAt(s.length()-1)) != -1) { // opswap
					return s.substring(0, s.length()-1) + k;
					
				}
				if (nums.indexOf(s.charAt(s.length()-1)) < 5) { // open parenthesis, !, or nums 
					return s; //if non-complete nums +++++======+++=
				}
				if (nums.indexOf(s.charAt(s.length()-1)) != -1
						|| semiNums.indexOf((s.charAt(s.length()-1))) > 3) {
					return s + " " + k;
					
				}
			}
		}
    	
    	return s;
	}
	
	private void scrollHistory(Boolean up) {
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
	
	private double equals() {
		String ftext = otext.replace('~', '-').replaceAll("l", "ln(").replaceAll("T", "log(");
		double ans = Communicator.getNum(ftext);
		String eq = ftext + " = " + doubleString(ans); 
		i.setText(eq);
		history.add(eq);
		historyIndex = history.size()-1;
		return ans;
	}
	
	private String recall() {
		String recall = (history.size() > 1 && !history.get(historyIndex).contains("I")) ? 
				history.get(historyIndex).split("= ")[1] : "";
		
		return ((!recall.equals("")) ? " " + recall : "");
	}
	
	private static String doubleString(double d) {
		if (Math.abs(Math.round(d)-d) > 0.00000000000001 // maximum fltpt error
				|| Math.abs(d) > 9999999) { // after 7 sig figs, use scientific notation
			d = (Math.round(d*10000000000000.0)) / 10000000000000.0; // rounding fltpt error
			System.out.println("here");
			return String.format("%s", d);
		} else {
			return String.format("%.0f", d);
		}
	}
	
	public static void main(String[] args) {
		//System.out.println(Arrays.toString(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
		Calculator c = new Calculator();
	}
}
