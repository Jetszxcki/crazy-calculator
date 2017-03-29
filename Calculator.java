import javax.swing.border.CompoundBorder;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.text.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")

public class Calculator extends JFrame implements ActionListener {
	
	private String[] operatorImgs = {"images/divide.png", "images/multiply.png", "images/subtract.png", "images/add.png", "images/equals.png"};
	private String[] otherImgs = {"images/off.png", "images/clear.png", "images/backspace.png", "images/openParenthesis.png", "images/closeParenthesis.png"};
	private String[] numImgs = {"images/0.png", "images/1.png", "images/2.png", "images/3.png", "images/4.png", "images/5.png", 
								"images/6.png", "images/7.png", "images/8.png", "images/9.png"};
	
	private JLabel heading = new JLabel("CRAZY CALCULATOR", SwingConstants.CENTER);
	public static JTextPane[] snapshotScreens = new JTextPane[4];
	private JButton[] otherButtons = new JButton[5];
	private JButton[] operators = new JButton[5];
	private JButton[] digits = new JButton[10];
	public static JTextArea screen;
	private Converter convert;
	
	private String ss = "READ|    PARSED   |   WRITTEN   |     STACK\n";
	private String string = "0";
	
	public Calculator() {
		
		setLayout(new GridLayout(1,2));
		setCalculatorComponents();
		
	}
		
	private void setCalculatorComponents() {
			
		heading.setFont(new Font("Eras Bold ITC", Font.BOLD, 20));
		heading.setForeground(Color.WHITE);
		
		screen = new JTextArea(5,25);
		screen.setForeground(Color.WHITE);
		screen.setBackground(new Color(40,40,40));
		screen.setFont(new Font("Consolas", Font.PLAIN, 50));
		screen.setEditable(false);
		screen.setText("0");
	
		JPanel operatorPanel = new JPanel();
		JPanel topNumPanel = new JPanel();
		JPanel screenPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel mainPanel = new JPanel();
		JPanel snapshot = new JPanel();
		JPanel numPanel = new JPanel();
		JPanel right = new JPanel();
		JPanel[] panels = { operatorPanel, topNumPanel, centerPanel, screenPanel, mainPanel, numPanel, snapshot, right };	
		
		operatorPanel.setBorder(myBorder());
		centerPanel.setBorder(myBorder());
		screenPanel.setBorder(myBorder());
		
		operatorPanel.setPreferredSize(new Dimension(200,100));
		topNumPanel.setPreferredSize(new Dimension(50,80));
		snapshot.setPreferredSize(new Dimension(550,200));
		right.setPreferredSize(new Dimension(50,200));
		
		operatorPanel.setLayout(new GridLayout(5,1,7,7));
		topNumPanel.setLayout(new GridLayout(1,3,7,7));
		centerPanel.setLayout(new BorderLayout(5,5));
		screenPanel.setLayout(new BorderLayout(5,5));
		snapshot.setLayout(new GridLayout(1,4,5,5));
		mainPanel.setLayout(new BorderLayout(5,10));
		numPanel.setLayout(new GridLayout(4,3,7,7));
										
		Color c = Color.BLACK; 		
		for(int x = 0; x < panels.length; x++)
			panels[x].setBackground(c);
		
		int x = 7;		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				digits[x] = new JButton(new ImageIcon(numImgs[x]));
				numPanel.add(digits[x]);
				x++;
			}
			x -= 6;				
		}
		digits[0] = new JButton(new ImageIcon(numImgs[0]));
		numPanel.add(digits[0]);
		
		for(int k = 0; k < 10; k++) {
			digits[k].addActionListener(this);
			digits[k].addMouseListener(new MouseHandler());
			
			if(k < 4) {
				snapshotScreens[k] = new JTextPane();
				snapshotScreens[k].setFont(new Font("Consolas", Font.PLAIN, 20));
				snapshotScreens[k].setBackground(new Color(40,40,40));
				snapshotScreens[k].setForeground(Color.WHITE);
				snapshotScreens[k].setEditable(false);
				snapshot.add(snapshotScreens[k]);
				
				StyledDocument doc = snapshotScreens[k].getStyledDocument();
				SimpleAttributeSet center = new SimpleAttributeSet();
				StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
				doc.setParagraphAttributes(0, doc.getLength(), center, false);
			}				
				
			if(k < 5) {
				operators[k] = new JButton(new ImageIcon(operatorImgs[k]));
				otherButtons[k] = new JButton(new ImageIcon(otherImgs[k]));
				otherButtons[k].addMouseListener(new MouseHandler());
				operators[k].addMouseListener(new MouseHandler());
				otherButtons[k].addActionListener(this);
				operators[k].addActionListener(this);
				operatorPanel.add(operators[k]);
				
				if(k < 3) 
					topNumPanel.add(otherButtons[k]);
				else numPanel.add(otherButtons[k]);
			}
		}			
		resetTextPane();
		
		centerPanel.add(topNumPanel, BorderLayout.NORTH);
		centerPanel.add(numPanel, BorderLayout.CENTER);
		screenPanel.add(screen, BorderLayout.CENTER);
		screenPanel.add(heading, BorderLayout.NORTH);

		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(screenPanel, BorderLayout.NORTH);
		mainPanel.add(operatorPanel, BorderLayout.EAST);
		add(snapshot);
		add(mainPanel);
		
	}	
	
	private Border myBorder() {
		Border line1, line2, compound;
		line1 = BorderFactory.createLineBorder(new Color(80,80,80), 5, true);
		line2 = BorderFactory.createLineBorder(Color.BLACK, 10, true);
		compound = BorderFactory.createCompoundBorder(line1, line2);
		return compound;
	}
	
	private void resetTextPane() {
		String[] s = {"READ\n","PARSED\n","WRITTEN\n","STACK\n"};
		for(int x = 0; x < 4; x++) {
			snapshotScreens[x].setText(null);
			snapshotScreens[x].setText(s[x]);
		}
	}
	
	private void input(String s) {
		if(string.equals("0")) {
			screen.setText(s);
			string = s;
		}else{
			screen.append(s);
			string += s;
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == otherButtons[0]) 
			System.exit(0);
			
		else if(e.getSource() == otherButtons[1]) {
			convert = new Converter();
			screen.setText("0");
			resetTextPane();
			string = "0";
			
		}else if(e.getSource() == otherButtons[2]) {	
			try {			
				resetTextPane();
				screen.setText("");
				StringBuilder sb = new StringBuilder(string);
				sb.deleteCharAt(string.length()-1);
				string = sb.toString();
				screen.setText(string);
				
				if(string.length() == 0) {
					screen.setText("0");
					string = "0";
				}
			}catch(StringIndexOutOfBoundsException s) {}
		}
		
		else if(e.getSource() == otherButtons[3]) input("(");
		else if(e.getSource() == otherButtons[4]) input(")");
		
		else if(e.getSource() == operators[4]) {
			System.out.print(ss);
			try{
				resetTextPane();
				convert = new Converter(string);
				String result = convert.toPostFix();
				screen.setText(result);	
				System.out.println("\n\n" + result);
			}catch(NullPointerException np) {
				screen.setText(string + "\n\nPF:(" + string + ")\nA: " + string);
			}
		}
		
		for(int x = 0; x < 10; x++) {
			if(e.getSource() == digits[x]) 
				input(String.valueOf(x));
			else if(x < 4 && e.getSource() == operators[x]) {
				String[] symbols = {"/", "x", "-", "+"};
				input(symbols[x]);
			}
		}
		
	}
	
	
	private class MouseHandler extends MouseAdapter {
		
		public void mousePressed(MouseEvent m) {
			String[] nums = {"0(2).png", "1(2).png", "2(2).png", "3(2).png", "4(2).png", "5(2).png", "6(2).png", "7(2).png", "8(2).png", "9(2).png"};
			String[] operations = {"divide(2).png", "multiply(2).png", "subtract(2).png", "add(2).png", "equals(2).png"};
			String[] others = {"off(2).png", "clear(2).png", "backspace(2).png", "openP(2).png", "closeP(2).png"};
			
			for(int x = 0; x < digits.length; x++) {
				if(m.getSource() == digits[x]) 
					digits[x].setIcon(new ImageIcon("images/" + nums[x]));
				else if(x < 5 && m.getSource() == operators[x])
					operators[x].setIcon(new ImageIcon("images/" + operations[x]));
				else if(x < 5 && m.getSource() == otherButtons[x])
					otherButtons[x].setIcon(new ImageIcon("images/" + others[x]));
			}
		}
		
		public void mouseReleased(MouseEvent m) {
			for(int y = 0; y < digits.length; y++) {
				if(m.getSource() == digits[y])
					digits[y].setIcon(new ImageIcon(numImgs[y]));
				else if(y < 5 && m.getSource() == operators[y])
					operators[y].setIcon(new ImageIcon(operatorImgs[y]));
				else if(y < 5 && m.getSource() == otherButtons[y])
					otherButtons[y].setIcon(new ImageIcon(otherImgs[y]));
			}
		}
		
	}
	
	
}
