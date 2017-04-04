
public class Converter extends Thread {
	
	private String errMessage = "SYNTAX ERROR";
	private String mathErr = "MATH ERROR";
	private String finalAnswer = "";
	private String postfix = "";
	private String inputString;
	private int right = 0;
	private int left = 0; 
	private Stack stack;
  
	public Converter() { stack = new Stack(); }
  
	public Converter(String inputString) {
		
		this.inputString = inputString;
		stack = new Stack();
		
	}	
	
	public static boolean isOperator(String s) {
		return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
	}
	
	public static boolean isOperand(String s) {
		return !s.equals("(") && !s.equals(")") && !isOperator(s) && !s.equals("");
	}

	public void run() {
	
		try{
			String temp = "(", parsed = "", previousChar = "", previousOperator = "",  previousOperator2 = "";
			double answer = 0.0;
			
			for(int x = 0; x < inputString.length(); x++) {
				String current = String.valueOf(inputString.charAt(x));

				if(isOperator(current)) {
					if(x == 0 || x == inputString.length()-1 || isOperator(previousChar) || previousChar.equals("(")) {
						postfix = errMessage;
						break;
					}else if(current.equals("+") || current.equals("-")) {
						if(previousOperator != "" && stack.elements != 0)
							postfix += stack.pop();
						Thread.sleep(500);
						stack.push(current);
					}else if(current.equals("/") || current.equals("*")) {
						if(previousOperator.equals("/") || previousOperator.equals("*"))
							postfix += stack.pop();
						Thread.sleep(500);
						stack.push(current);
					}
					previousOperator2 = previousOperator;
					previousOperator = current;
					
				}else if(current.equals("(")) {
					if(x == inputString.length()-1 || isOperand(previousChar) || previousChar.equals(")")) {
						System.out.println("ENTERED");
						postfix = errMessage;
						break;
					}
					
					left++;
					previousOperator = "";
					stack.push(current);
					
				}else if(current.equals(")")) {
					right++;
					if(x == 0 || left < right || previousChar.equals("(") || isOperator(previousChar)) {
						postfix = errMessage;
						break;
					}else{
						while(true) {
							String popped = stack.pop();
							Thread.sleep(500);
							if(!popped.equals("("))
								postfix += popped;
							else break;
						}
					}
					previousOperator = "";
					
				}else{
					if(current.equals("0") && previousChar.equals("/")) {
						postfix = mathErr;
						break;
					}else if(previousChar.equals(")")) {
						postfix = errMessage;
						break;
					}else{
						String next;
						try{
							next = String.valueOf(inputString.charAt(x+1));
						}catch(Exception ex){ next = null; }
						
						temp += current;
						if(next == null || !isOperand(next)) {
							if(temp.length() > 2) {
								postfix += temp + ")";
								temp = "(";
							}else{
								postfix += current;
								temp = "(";
							}
						}
					}
				}	
				
				parsed += current;
				displaySnapshot(current, parsed, postfix, "    ");
				previousChar = current;
				Thread.sleep(500);
			}
			
			if(left != right) postfix = errMessage;			
			if(!postfix.equals(errMessage) && !postfix.equals(mathErr)) {
				displaySnapshot("END", parsed, postfix, "    ");
				if(stack.elements != 0) {		
					for(int k = 0; k <= stack.elements; k++) {
						postfix += stack.pop();
						displaySnapshot("", parsed, postfix, "    ");
						Thread.sleep(500);
					}
					stack.elements = 0;	
				}
				parsed = "";
				   
	// +++++++++++++++++++++++++++++++++++++  EVALUATION  ++++++++++++++++++++++++++++++++++++++++++++++
			
				int addedElements = 0;
				String number = "";
				double temp2 = 0.0;
				
				for(int x = 0; x < postfix.length(); x++) {
					String current = String.valueOf(postfix.charAt(x));
					
					if(isOperand(current)) {
						/*
						String next = String.valueOf(postfix.charAt(x+1));
						if(x != postfix.length()-1 && isOperand(next) || next.equals(")"))
							number += current;
						else */
						stack.push(current);
						addedElements++;
					}else if(current.equals("(") && addedElements != 0) {
						addedElements--;
						
					}else if(current.equals(")")) {
						/*
						stack.push(number);
						number = "";
						*/
						int exponent = 0, limit = 0;
						while(addedElements > limit++) {
							temp2 += Math.pow(10,exponent)*(Double.parseDouble(stack.pop()));
							Thread.sleep(500);
							exponent++;
						}
						
						stack.push(String.valueOf(temp2));	
						addedElements = 0;
						temp2 = 0.0;	
						
					}else if(isOperator(current)) {
						double num1 = Double.parseDouble(stack.pop());
						Thread.sleep(500);
						double num2 = Double.parseDouble(stack.pop());
						Thread.sleep(500);
						
						if(current.equals("+")) 
							answer = num1 + num2;
						else if(current.equals("-")) 
							answer = num2 - num1;
						else if(current.equals("*")) 
							answer = num1 * num2;
						else if(current.equals("/")) {
							if(num1 == 0) {
								postfix = mathErr;
								break;
							}else answer = num2 / num1;
						}
						stack.push(String.valueOf(answer));
						addedElements = 0;
					} 
					parsed += current;
					displaySnapshot(current, parsed, String.valueOf(answer), "    ");
					Thread.sleep(500);
				}	
				displaySnapshot("END", parsed, postfix, "    ");
				finalAnswer = roundOff(Double.parseDouble(stack.pop()));
			}
		}catch(Exception e){ e.printStackTrace(); }
		
		String output;
		
		if(postfix.equals(errMessage) || postfix.equals(mathErr))
			output = postfix + "\n\n=> " + inputString;
		else output = inputString + "\n\n\nPF: " + postfix + "\nA:  " + finalAnswer;
		
		Calculator.screen.setText(output);	
		System.out.println("\n\n" + output);
		Calculator.enableButtons(true);
		
    }
	
	private String roundOff(double evaluatedValue) {
		
		int integerAns = (int)evaluatedValue;
		double doubleAns = evaluatedValue - integerAns;
		
		if(doubleAns == 0.0)
			return String.valueOf(integerAns);
		else{
			String postfix = String.format("%1$.5f", doubleAns);
			return String.valueOf(Double.parseDouble(postfix) + integerAns);
		}
		
	}
		
	private void displaySnapshot(String current, String parsed, String postfix, String space) {
		
		Calculator.snapshotScreens[0].setText(Calculator.snapshotScreens[0].getText() + current + "\n");
		Calculator.snapshotScreens[1].setText(Calculator.snapshotScreens[1].getText() + parsed + "\n");
		Calculator.snapshotScreens[2].setText(Calculator.snapshotScreens[2].getText() + postfix + "\n");
		System.out.print("  " + current + space + parsed + space + postfix + space);
		stack.display();
		
	}
}
