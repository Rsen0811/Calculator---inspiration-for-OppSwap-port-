/**
 * 
 * Use this class to handle the main features of your application.
 * You should add additional classes as appropriate to support good modularity and reduce redundancy.
 *
 */
import java.util.*;
import java.io.*;

public class BackendComputation {
	/*
	public static void main(String[] args) {
		String eq = "8 + 3 * 5 * 1 + ( 4 + 6 ) * 3 - 4 * 5";
		ArrayList<String> equationList = new ArrayList<String>();
		String[] a = eq.split(" ");
		for (String s : a) {
			equationList.add(s);
		}
		System.out.println(computeArray(equationList));
	}*/

	public static double computeArray(List<String> elements) {
		if (elements.get(0).equals("(")) {
			List<String> sub = new ArrayList<String>(elements.subList(1, elements.lastIndexOf(")")));
			return computeArray(sub);
		}
		if (elements.size() == 1) {
			return Integer.parseInt(elements.get(0));
		}
		if (elements.size() <= 3) {
			return performOperation(elements);
		}
		if (opValue(elements.get(1)) >= opValue(elements.get(3)) || elements.get(3).equals("(")) {
			List<String> sub = new ArrayList<String>(elements.subList(3, elements.size()));
			sub.add(0, Double.toString(performOperation(elements.subList(0, 3))));
			return computeArray(sub);
		}
		if (opValue(elements.get(1)) < opValue(elements.get(3))) {
			List<String> sub = new ArrayList<String>(elements.subList(2, elements.size()));
			List<String> sub2 = elements.subList(0, 2);
			sub2.add(Double.toString(computeArray(sub)));
			
			return performOperation(sub2);
		}
		return 0;

	}
	
	private static double performOperation(List<String> elements) {
		if (elements.get(1).equals("-")) {
			return Integer.parseInt(elements.get(0)) - Integer.parseInt(elements.get(2));
		} else if (elements.get(1).equals("+")) {
			return Integer.parseInt(elements.get(0)) + Integer.parseInt(elements.get(2));			
		} else if (elements.get(1).equals("/")) {
			return Double.parseDouble(elements.get(0)) / Integer.parseInt(elements.get(2));
		} else if (elements.get(1).equals("*")) {
			return Integer.parseInt(elements.get(0)) * Integer.parseInt(elements.get(2));
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
		} else if (op.equals("*") || op.equals("/")) {
			return 1;
		}
		return -1;
	}
}

