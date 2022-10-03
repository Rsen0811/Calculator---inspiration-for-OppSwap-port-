import java.util.*;
import java.io.*;

public class BackendComputation {
	
	public static void main(String[] args) {
		// testing code
		String eq = "1 + 2 * 7 / 2 * 3";//"8 + 3 * 5 * 1 * ( 3 - 4 ) * 5";
		ArrayList<String> equationList = new ArrayList<String>();
		String[] a = eq.split(" ");
		for (String s : a) {
			equationList.add(s);
		}
		System.out.println(computeArray(equationList));
	}

	public static double computeArray(List<String> elements) {
		// if entering parenthesis...
		if (elements.get(0).equals("(") || elements.get(0).equals("-(")) {
			// keep track of if the parenthesis value is being negated
			int pos = 1;
			if (elements.get(0).equals("-(")) {
				pos = -1;
			}
			
			// split up array into inside/outside parenthesis
			int closedI = findCorrespondingParentheses(elements);
			List<String> sub = new ArrayList<String>(elements.subList(1, closedI));
			List<String> sub2 = new ArrayList<String>(elements.subList(closedI + 1, elements.size()));
			
			// calculate value inside parenthesis and add it to before the outside values
			// multiply by negative one if negated
			sub2.add(0, Double.toString(computeArray(sub) * pos));
			return computeArray(sub2);
				
		}
		
		// if there is one value left, return it as the answer
		if (elements.size() <= 1) {
			return Double.parseDouble(elements.get(0));
		}
		
		// if the next operation is ln...
		if (elements.get(0).equals("ln") || elements.get(0).equals("-ln")) {
			// keep track of if the ln was negated or not
			int pos = 1;
			if (elements.get(0).equals("-ln")) {
				pos = -1;
			}
			// find and calculate the inside of parenthesis of the ln
			List<String> sub1 = new ArrayList<String>(elements.subList(1, elements.size()));
			int n = findCorrespondingParentheses(sub1);
			// inside parenthesis value
			List<String> sub2 = new ArrayList<String>(elements.subList(2, n + 1));
			
			// if there are values outside ln (...), consider them, otherwise return ln(value)
			if (n + 1 < elements.size() - 1) {
				// store values after parenthesis
				List<String> sub3 = new ArrayList<String>(elements.subList(n + 2, elements.size()));
				// add value of stuff inside ln(...) to before post parenthesis elements
				sub3.add(0, Double.toString(Math.log(computeArray(sub2) * pos)));
				// calculate and return
				return  computeArray(sub3);
			} else {
				return Math.log(computeArray(sub2)) * pos;
			}
						
		}
		
		// if next element is log...
		if (elements.get(0).equals("log") || elements.get(0).equals("-log")) {
			// keep track of if the log is negated
			int pos = 1;
			if (elements.get(0).equals("-log")) {
				pos = -1;
			}
			
			// find and calculate the inside of parenthesis of the log
			List<String> sub1 = new ArrayList<String>(elements.subList(1, elements.size()));
			int n = findCorrespondingParentheses(sub1);
			// inside parenthesis value
			List<String> sub2 = new ArrayList<String>(elements.subList(2, n + 1));
			// if there are values outside log (...), consider them, otherwise return log(value)
			if (n + 1 < elements.size() - 1) {
				// separate values outside log
				List<String> sub3 = new ArrayList<String>(elements.subList(n + 2, elements.size()));
				// calculate values inside log, log them, add to outside values and calc the whole
				sub3.add(0, Double.toString(Math.log10(computeArray(sub2)) * pos));
				return  computeArray(sub3);
			} else {
				return Math.log10(computeArray(sub2)) * pos;
			}
						
		}
		
		// if there is a factorial after 1st number
		if (elements.get(1).equals("!")) {
			// separate nums after factorial
			List<String> sub = new ArrayList<String>(elements.subList(2, elements.size()));
			// add factorial of first num to before outside nums
			sub.add(0, Double.toString(gamma(Double.parseDouble(elements.get(0)))));
			// calculate and return whole
			return computeArray(sub);
		}
		
		// if there is (x, [op], "("), perform PEMDAS appropriately
		if (elements.size() >= 3 && (elements.get(2).equals("(") || elements.get(2).equals("-("))) {
			// keep track of if parenthesis value is negated
			int pos = 1;
			if (elements.get(0).equals("-(")) {
				pos = -1;
			}
			// find bounds of parenthsis values
			int iCloseP = findCorrespondingParentheses(elements.subList(2,  elements.size())) + 3;
			// separate array into vals before parenthesis, vals after, and vals in
			List<String> sub1 = new ArrayList<String>(elements.subList(0, 2));
			List<String> sub2 = new ArrayList<String>(elements.subList(2, iCloseP));
			List<String> sub3 = new ArrayList<String>(elements.subList(iCloseP, elements.size()));
			
			// add result of computing parenthesis to first vals
			sub1.add(Double.toString(computeArray(sub2) * pos));
			// add all post parenthesis values after parenthesis result
			sub1.addAll(sub3);
			// compute result of everything
			return computeArray(sub1);
		}
		
		// if there is (x, [op], y), calculate using operator for result and return
		if (elements.size() <= 3) {
			return performOperation(elements);
		}
		
		// if there is (x, [op], ln)...
		if (elements.get(2).equals("ln") || elements.get(2).equals("-ln")) {
			// keep track of if ln is negated
			int pos = 1;
			if (elements.get(2).equals("-ln")) {
				pos = -1;
			}
			// separate ln stuff from pre-ln stuff and post ln stuff
			List<String> sub1 = new ArrayList<String>(elements.subList(3, elements.size()));
			int n = findCorrespondingParentheses(sub1);
			List<String> sub2 = new ArrayList<String>(elements.subList(4, n + 3));
			// consider if there is post ln stuff
			if (n + 3 < elements.size() - 1) {
				// if post ln stuff, then add result of ln, then the pre ln stuff
				List<String> sub3 = new ArrayList<String>(elements.subList(n + 4, elements.size()));
				sub3.add(0, Double.toString(Math.log(computeArray(sub2)) * pos));
				sub3.add(0, elements.get(1));
				sub3.add(0, elements.get(0));
				// compute whole
				return  computeArray(sub3);
			} else {
				// if no post ln stuff, add result of ln(...) to after  pre ln stuff
				List<String> sub3 = new ArrayList<String>(elements.subList(0, 2));
				sub3.add(Double.toString(Math.log(computeArray(sub2)) * pos));
				// compute whole
				return computeArray(sub3);
			}
		}
		
		// if there is (x, [op], log)...
		if (elements.get(2).equals("log") || elements.get(2).equals("-log")) {
			// keep track of if log is negated
			int pos = 1;
			if (elements.get(2).equals("-log")) {
				pos = -1;
			}
			// separate log stuff from pre-log stuff and post log stuff
			List<String> sub1 = new ArrayList<String>(elements.subList(3, elements.size()));
			int n = findCorrespondingParentheses(sub1);
			List<String> sub2 = new ArrayList<String>(elements.subList(4, n + 3));
			// consider if there is post log stuff
			if (n + 3 < elements.size() - 1) {
				// if post log stuff, then add result of log, then the pre log stuff
				List<String> sub3 = new ArrayList<String>(elements.subList(n + 4, elements.size()));
				sub3.add(0, Double.toString(Math.log10(computeArray(sub2)) * pos));
				sub3.add(0, elements.get(1));
				sub3.add(0, elements.get(0));
				// compute whole
				return  computeArray(sub3);
			} else {
				// if no post log stuff, add result of log(...) to after  pre log stuff
				List<String> sub3 = new ArrayList<String>(elements.subList(0, 2));
				sub3.add(Double.toString(Math.log10(computeArray(sub2)) * pos));
				// compute whole
				return computeArray(sub3);
			}
		}
		
		// if (x, [op], y, !)
		if (elements.get(3).equals("!")) {
			// duplicate array
			List<String> sub = new ArrayList<String>(elements.subList(0, elements.size()));
			// calculate factorial
			double num = gamma(Double.parseDouble(elements.get(2)));
			sub.remove(3); // remove "!"
			sub.set(2, Double.toString(num)); // replace y with factorial result
			return computeArray(sub); // compute whole
		}
		
		// if first op is higher or equal precedence than second in (x, [op], y, [op], z)
		if (opValue(elements.get(1)) >= opValue(elements.get(3))) {
			// save elements after and including 2nd op
			List<String> sub = new ArrayList<String>(elements.subList(3, elements.size()));
			// calculate first two nums with first op and add add it before later elements
			sub.add(0, Double.toString(performOperation(elements.subList(0, 3))));
			// compute whole
			return computeArray(sub);
		}
		
		// if first op is lower precedence than second in (x, [op], y, [op], z)
		if (opValue(elements.get(1)) < opValue(elements.get(3))) {
			// separate stuff after and including y from stuff before y
			List<String> sub = new ArrayList<String>(elements.subList(2, elements.size()));
			List<String> sub2 = elements.subList(0, 2);
			// calculate stuff after/including y and attach it to elements before y
			sub2.add(Double.toString(computeArray(sub)));
			// perform the 1st operation in string between x and result of rest of the array
			return performOperation(sub2);
		}
		// something went wrong - syntax error in input
		throw new RuntimeException();

	}
	
	// locate index of matching pair of parenthesis
	private static int findCorrespondingParentheses(List<String> elements) {
		// verify that input list starts with parenthesis
		if (elements.get(0).equals("(") || elements.get(0).equals("-(")) {
			int countOpen = 0; // counts # of "("
			int countClosed = 0; // counts # of ")"
			int closedI = -1; // tracks the matching ")" to initial "("
			for (int i = 0; i < elements.size(); i++) {
				// every time "(" is encountered, increment count
				if (elements.get(i).equals("(") || elements.get(i).equals("-(")) {
					countOpen++;
				}
				// every time ")" is encountered, increment count
				if (elements.get(i).equals(")")) {
					countClosed++;
					// this could be the correct ")" - mark it
					closedI = i;
				}
				// if there are an equal amount of pairs counted, every "(" encountered
				// has been closed - last ")" encountered must match with first "(", and
				// so the current tracked index of the previous ")" is correct
				if (countOpen == countClosed) {
					return closedI;
				}
			}
		}
		// something went wrong - syntax error
		throw new RuntimeException();
	}
	
	// perform an operation according to a string - takes a list of 3 elements, outputs double
	// format: x, [op], y
	private static double performOperation(List<String> elements) {
		if (elements.get(1).equals("-")) { // subtraction
			return Double.parseDouble(elements.get(0)) - Double.parseDouble(elements.get(2));
		
		} else if (elements.get(1).equals("+")) { // addition
			return Double.parseDouble(elements.get(0)) + Double.parseDouble(elements.get(2));			
		
		} else if (elements.get(1).equals("/")) { // division
			if (Double.parseDouble(elements.get(2)) == 0) { // divide by 0 - return NaN
				return Double.NaN;
			}
			return Double.parseDouble(elements.get(0)) / Double.parseDouble(elements.get(2));
		
		} else if (elements.get(1).equals("*")) { // multiplication
			return Double.parseDouble(elements.get(0)) * Double.parseDouble(elements.get(2));
		
		} else if (elements.get(1).equals("^")) { // power
			return Math.pow(Double.parseDouble(elements.get(0)), Double.parseDouble(elements.get(2)));
		
		} else if (elements.get(1).equals("%")) { // mod
			return Double.parseDouble(elements.get(0)) % Double.parseDouble(elements.get(2));
		
		} else if (elements.get(1).equals("!")) { // factorial
			return gamma(Double.parseDouble(elements.get(0)));
		}
		// bad input - syntax error
		throw new RuntimeException();
	}
	
	// factorial calculation - formula allows decimal values
	private static double gamma(double x) {
		// if the input is close to an integer, use integer formula
		// the formula is an approximation, and will unnecessarily lose precision
		// for int inputs
		if (Math.abs(((int) x) - x) <= 0.0001 && x >= 0) {
			// multiply until input is exceeded - then stop, return product
			double product = 1;
			for (int i = 1; i <= (int) x; i++) {
				product *= (double) i;

			}

			return product;
		}
		// cannot evaluate negatives - thows error that indicates NaN
		if (!(x > 0)) {
			throw new IllegalArgumentException();
		}
		// shift function - gamma function is actually (x - 1)! - shift so it matches ! function
		x = x+1;
		// calculate gamma function - utilizes taylor series for approximation
		// true gamma function uses an improper integral which would be unrealistic
		// to evaluate in java on this project
		// calculations are separated into terms and variables for ease of reading/error checking
		double outsideTerm = Math.pow(Math.E, -x) * Math.pow(x, x);
		double sqrtInvX = Math.sqrt(1 / x);
		double term1 = Math.sqrt(2 * Math.PI) * sqrtInvX;
		double sqrtHalfPi = Math.sqrt(Math.PI / 2);
		double term2 = (1.0/6.0) * sqrtHalfPi * Math.pow(sqrtInvX, 3);
		double term3 = (1.0/144.0) * sqrtHalfPi * Math.pow(sqrtInvX, 5);
		return outsideTerm * (term1 + term2 + term3);
	}
		
	// returns precedence of given operator
	private static int opValue(String op) {
		// addition/subtraction - precedence 0
		if (op.equals("+") || op.equals("-")) {
			return 0;
		// multiplication, division, mod, precedence 1	
		} else if (op.equals("*") || op.equals("/") || op.equals("%")) {
			return 1;
		// exponent, precedence 2
		} else if (op.equals("^")) {
			return 2;
		// factorial, precedence 3
		} else if (op.equals("!")) {
			return 3;
		}
		// bad input - syntax error
		throw new RuntimeException();
	}
}

