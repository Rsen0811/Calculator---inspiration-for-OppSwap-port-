/**
 * 
 * Use this class to handle the main features of your application.
 * You should add additional classes as appropriate to support good modularity and reduce redundancy.
 *
 */
import java.util.*;
import java.io.*;

public class BackendComputation {
	
	public static void main(String[] args) {
		String eq = "1 + 2 * 7 / 2 * 3";//"8 + 3 * 5 * 1 * ( 3 - 4 ) * 5";
		ArrayList<String> equationList = new ArrayList<String>();
		String[] a = eq.split(" ");
		for (String s : a) {
			equationList.add(s);
		}
		System.out.println(computeArray(equationList));
	}

	public static double computeArray(List<String> elements) {
		System.out.println(elements.toString());
		if (elements.get(0).equals("(")) {
			int closedI = findCorrespondingParentheses(elements);
			List<String> sub = new ArrayList<String>(elements.subList(1, closedI));
			List<String> sub2 = new ArrayList<String>(elements.subList(closedI + 1, elements.size()));
			double d = computeArray(sub);
			sub2.add(0, Double.toString(d));
			System.out.println(sub2.toString());
			d = computeArray(sub2);
			System.out.println(d);
			return d;
				
		}

		if (elements.size() <= 1) {
			System.out.println(Double.parseDouble(elements.get(0)));
			return Double.parseDouble(elements.get(0));
		}

		if (elements.size() >= 3 && elements.get(2).equals("(")) {
			int iCloseP = findCorrespondingParentheses(elements.subList(2,  elements.size())) + 3;
			System.out.println(iCloseP + "val");
			List<String> sub1 = new ArrayList<String>(elements.subList(0, 2));
			System.out.println(sub1 + "1");
			List<String> sub2 = new ArrayList<String>(elements.subList(2, iCloseP));
			System.out.println(sub2 + "2");
			List<String> sub3 = new ArrayList<String>(elements.subList(iCloseP, elements.size()));
			System.out.println(sub3 + "3");
			double insideP = computeArray(sub2);
			sub1.add(Double.toString(insideP));
			sub1.addAll(sub3);
			double d = computeArray(sub1);
			System.out.println(d);
			return d;
		}
		
		if (elements.size() <= 3) {
			double d = performOperation(elements);
			System.out.println(d);
			return d;
		}
		if (opValue(elements.get(1)) >= opValue(elements.get(3))) {
			List<String> sub = new ArrayList<String>(elements.subList(3, elements.size()));
			sub.add(0, Double.toString(performOperation(elements.subList(0, 3))));
			double d = computeArray(sub);
			System.out.println(d);
			return d;
		}
		if (opValue(elements.get(1)) < opValue(elements.get(3))) {
			List<String> sub = new ArrayList<String>(elements.subList(2, elements.size()));
			List<String> sub2 = elements.subList(0, 2);
			sub2.add(Double.toString(computeArray(sub)));
			double d = performOperation(sub2);
			System.out.println(d);
			return d;
		}
		System.out.println("BROKEN");
		return 0;

	}
	
	private static int findCorrespondingParentheses(List<String> elements) {
		System.out.println(elements.toString() + "in");
		if (elements.get(0).equals("(")) {
			int countOpen = 0;
			int countClosed = 0;
			int closedI = -1;
			for (int i = 0; i < elements.size(); i++) {
				if (elements.get(i).equals("(")) {
					countOpen++;
				}
				if (elements.get(i).equals(")")) {
					countClosed++;
					closedI = i;
				}
				if (countOpen == countClosed) {
					return closedI;
				}
			}
		}
		
		System.out.println("BROKEN");
		return -2;
	}
	private static double performOperation(List<String> elements) {
		if (elements.get(1).equals("-")) {
			return Double.parseDouble(elements.get(0)) - Double.parseDouble(elements.get(2));
		} else if (elements.get(1).equals("+")) {
			return Double.parseDouble(elements.get(0)) + Double.parseDouble(elements.get(2));			
		} else if (elements.get(1).equals("/")) {
			return Double.parseDouble(elements.get(0)) / Double.parseDouble(elements.get(2));
		} else if (elements.get(1).equals("*")) {
			return Double.parseDouble(elements.get(0)) * Double.parseDouble(elements.get(2));
		} else if (elements.get(1).equals("^")) {
			return Math.pow(Double.parseDouble(elements.get(0)), Double.parseDouble(elements.get(2)));
		} else if (elements.get(1).equals("%")) {
			return Double.parseDouble(elements.get(0)) % Double.parseDouble(elements.get(2));
		}
		throw new NumberFormatException();
	}
	

	/*
	private static int computeStacks(List<String> elements, Stack<Integer> nums, Stack<Character> ops) {
		for (int i = 0; i < elements.size(); i++) {
			
		}
	}*/
	// 8*1*(6+2*7)+5 
	
	// 8 1 6 2 7 5
	//    
	//  
	// 
	
	
	private static int opValue(String op) {
		if (op.equals("+") || op.equals("-")) {
			return 0;
		} else if (op.equals("*") || op.equals("/") || op.equals("%")) {
			return 1;
		} else if (op.equals("^")) {
			return 2;
		}
		return -1;
	}
}

