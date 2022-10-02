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
		
		if (elements.get(0).equals("ln")) {
			List<String> sub1 = new ArrayList<String>(elements.subList(1, elements.size()));
			int n = findCorrespondingParentheses(sub1);
			List<String> sub2 = new ArrayList<String>(elements.subList(2, n + 1));
			if (n + 1 < elements.size() - 1) {
				List<String> sub3 = new ArrayList<String>(elements.subList(n + 2, elements.size()));
				sub3.add(0, Double.toString(Math.log(computeArray(sub2))));
				return  computeArray(sub3);
			} else {
				return Math.log(computeArray(sub2));
			}
						
		}
		
		if (elements.get(0).equals("log")) {
			List<String> sub1 = new ArrayList<String>(elements.subList(1, elements.size()));
			int n = findCorrespondingParentheses(sub1);
			List<String> sub2 = new ArrayList<String>(elements.subList(2, n + 1));
			if (n + 1 < elements.size() - 1) {
				List<String> sub3 = new ArrayList<String>(elements.subList(n + 2, elements.size()));
				sub3.add(0, Double.toString(Math.log10(computeArray(sub2))));
				return  computeArray(sub3);
			} else {
				return Math.log10(computeArray(sub2));
			}
						
		}
		
		if (elements.get(1).equals("!")) {
			List<String> sub = new ArrayList<String>(elements.subList(2, elements.size()));
			sub.add(0, Double.toString(gamma(Double.parseDouble(elements.get(0)))));
			return computeArray(sub);
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
		
		if (elements.get(2).equals("ln")) {
			List<String> sub1 = new ArrayList<String>(elements.subList(3, elements.size()));
			int n = findCorrespondingParentheses(sub1);
			List<String> sub2 = new ArrayList<String>(elements.subList(4, n + 3));
			if (n + 3 < elements.size() - 1) {
				List<String> sub3 = new ArrayList<String>(elements.subList(n + 4, elements.size()));
				sub3.add(0, Double.toString(Math.log(computeArray(sub2))));
				sub3.add(0, elements.get(1));
				sub3.add(0, elements.get(0));
				return  computeArray(sub3);
			} else {
				List<String> sub3 = new ArrayList<String>(elements.subList(0, 2));
				sub3.add(Double.toString(Math.log(computeArray(sub2))));
				return computeArray(sub3);
			}
		}
		
		if (elements.get(2).equals("log")) {
			List<String> sub1 = new ArrayList<String>(elements.subList(3, elements.size()));
			int n = findCorrespondingParentheses(sub1);
			List<String> sub2 = new ArrayList<String>(elements.subList(4, n + 3));
			if (n + 3 < elements.size() - 1) {
				List<String> sub3 = new ArrayList<String>(elements.subList(n + 4, elements.size()));
				sub3.add(0, Double.toString(Math.log10(computeArray(sub2))));
				sub3.add(0, elements.get(1));
				sub3.add(0, elements.get(0));
				return  computeArray(sub3);
			} else {
				List<String> sub3 = new ArrayList<String>(elements.subList(0, 2));
				sub3.add(Double.toString(Math.log10(computeArray(sub2))));
				return computeArray(sub3);
			}
		}
		
		if (elements.get(3).equals("!")) {
			List<String> sub = new ArrayList<String>(elements.subList(0, elements.size()));
			double num = gamma(Double.parseDouble(elements.get(2)));
			sub.remove(3);
			sub.set(2, Double.toString(num));
			return computeArray(sub);
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
		throw new RuntimeException();

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
		
		throw new RuntimeException();
	}
	private static double performOperation(List<String> elements) {
		if (elements.get(1).equals("-")) {
			return Double.parseDouble(elements.get(0)) - Double.parseDouble(elements.get(2));
		} else if (elements.get(1).equals("+")) {
			return Double.parseDouble(elements.get(0)) + Double.parseDouble(elements.get(2));			
		} else if (elements.get(1).equals("/")) {
			if (Double.parseDouble(elements.get(2)) == 0) {
				return Double.NaN;
			}
			return Double.parseDouble(elements.get(0)) / Double.parseDouble(elements.get(2));
		} else if (elements.get(1).equals("*")) {
			return Double.parseDouble(elements.get(0)) * Double.parseDouble(elements.get(2));
		} else if (elements.get(1).equals("^")) {

			return Math.pow(Double.parseDouble(elements.get(0)), Double.parseDouble(elements.get(2)));
		} else if (elements.get(1).equals("%")) {
			return Double.parseDouble(elements.get(0)) % Double.parseDouble(elements.get(2));
		} else if (elements.get(1).equals("!")) {
			return gamma(Double.parseDouble(elements.get(0)));
		}
		throw new RuntimeException();
	}
	
	private static double gamma(double x) {
		if (Math.abs(((int) x) - x) <= 0.0001 && x >= 0) {
			double product = 1;
			for (int i = 1; i <= (int) x; i++) {
				product *= (double) i;
				System.out.println(product);
			}
			System.out.println("WORKING");
			return product;
		}
		if (!(x > 0)) {
			throw new IllegalArgumentException();
		}
		x = x+1;
		double outsideTerm = Math.pow(Math.E, -x) * Math.pow(x, x);
		double sqrtInvX = Math.sqrt(1 / x);
		double term1 = Math.sqrt(2 * Math.PI) * sqrtInvX;
		double sqrtHalfPi = Math.sqrt(Math.PI / 2);
		double term2 = (1.0/6.0) * sqrtHalfPi * Math.pow(sqrtInvX, 3);
		double term3 = (1.0/144.0) * sqrtHalfPi * Math.pow(sqrtInvX, 5);
		return outsideTerm * (term1 + term2 + term3);
	}
	
	
	
	private static int opValue(String op) {
		if (op.equals("+") || op.equals("-")) {
			return 0;
		} else if (op.equals("*") || op.equals("/") || op.equals("%")) {
			return 1;
		} else if (op.equals("^")) {
			return 2;
		} else if (op.equals("!")) {
			return 3;
		}
		return -1;
	}
}

