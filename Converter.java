
public class Converter extends Thread {
	
	public static int threadTime = 500;
	private String errMessage = "SYNTAX ERROR";
	private String mathErr = "MATH ERROR";
	private String finalAnswer = "";
	private String postfix = "";
	private String inputString;
	private String[] output;
	private int right = 0;
	private int left = 0; 
	private Stack stack;
  
	public Converter() {}
  
	public Converter(String inputString) {
		
		this.inputString = inputString;
		stack = new Stack(inputString.length());
		
	}	
	
	public static boolean isOperator(String s) {
		return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
	}
	
	public static boolean isOperand(String s) {
		return !s.equals("(") && !s.equals(")") && !isOperator(s) && !s.equals("");
	}
	
	public boolean isError() {
		return postfix.equals(errMessage) || postfix.equals(mathErr) || left != right;
	}

	public void run() {
	
		try{
			String temp = "(", parsed = "", previousChar = "", previousOperator = "";

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
					previousOperator = current;
					
				}else if(current.equals("(")) {
					if(x == inputString.length()-1 || isOperand(previousChar) || previousChar.equals(")")) {
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
				
				int ht1;
				ht1 = (int)Calculator.structureHolder[0].getPreferredSize().getHeight();
				
				if(ht1 != 0) {
					Calculator.structureHolder[4].revalidate();
					Calculator.scrollAnim[4].getVerticalScrollBar().setValue(ht1);
				}
				
				previousChar = current;
				Thread.sleep(500);
			}
					
			if(!isError()) {
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
			
				int stringCtr = 0;
				double first, second, out = 0.0;
				boolean isParenthesis = false;
				output = new String[postfix.length()];
				
				for(int i = 0; i < postfix.length(); i++) {
					output[i] = "";
					if(postfix.charAt(i) == '(') {
						isParenthesis = true;
						output[stringCtr] += String.valueOf(postfix.charAt(i));
					}else if(postfix.charAt(i) == ')') {
						isParenthesis = false;
						output[stringCtr++] += String.valueOf(postfix.charAt(i));
					}else{
						output[stringCtr] += String.valueOf(postfix.charAt(i));
						if(!isParenthesis)
							stringCtr++;
					}
				}

				for(int j = 0; j < stringCtr; j++) {
					if(output[j].equals(null))
						break;
					else if(output[j].charAt(0) == '(')
						output[j] = output[j].substring(1, output[j].length()-1);
				}
				
				for(int j = 0; j < stringCtr; j++) {
					if(output[j].equals(null))
						break;
					else{
						if(isOperator(output[j])) {
							second = Double.parseDouble(stack.pop());
							Thread.sleep(500);
							first = Double.parseDouble(stack.pop());
							Thread.sleep(500);
							
							if(output[j].equals("+")) 
								out = first + second;
							else if(output[j].equals("-")) 
								out = first - second;
							else if(output[j].equals("*")) 
								out = first * second;
							else if(output[j].equals("/")) {
								out = 0.0;
								if(second != 0) {
									out = first / second;
								}else{
									postfix = mathErr;
									break;
								}
							}
							stack.push(Double.toString(out));
							Thread.sleep(500);
						}else stack.push(output[j]);
					}
					
					if(output[j].length() == 1)
						parsed += output[j];
					else parsed += "(" + output[j] + ")";
					
					displaySnapshot(output[j], parsed, String.valueOf(out), "    ");
					Thread.sleep(500);
				}				
				displaySnapshot("END", parsed, String.valueOf(out), "    ");
				finalAnswer = roundOff(Double.parseDouble(stack.pop()));
			}
			
		}catch(Exception e) { e.printStackTrace(); }
		
		String output;
		
		if(isError())
			output = postfix + "\n\n=> " + inputString;
		else output = inputString + "\n\n\nPF: " + postfix + "\nA:  " + finalAnswer;
		
		Calculator.screen.setText(output);	
		Calculator.snapshotScreens[0].requestFocusInWindow();
		Calculator.enableButtons(true);
		Calculator.isRunning = false;
		
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
		stack.display();
		
	}
}
