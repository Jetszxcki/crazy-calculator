import javax.swing.border.Border;
import javax.swing.text.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")

public class Calculator extends JFrame implements ActionListener {

	
	public static Color[] colors = {new Color(238,0,0), new Color(255,69,0), new Color(255,97,3), new Color(255,128,0), new Color(238,201,0), new Color(255,255,0),
									new Color(127,255,0), new Color(0,255,0), new Color(0,238,118), new Color(0,238,238), Color.DARK_GRAY};
	private String[] otherImgs = {"images/off.png", "images/clear.png", "images/backspace.png", "images/openParenthesis.png", "images/closeParenthesis.png"};
	private String[] operatorImgs = {"images/divide.png", "images/multiply.png", "images/subtract.png", "images/add.png", "images/equals.png"};
	private String[] numImgs = {"images/0.png", "images/1.png", "images/2.png", "images/3.png", "images/4.png", "images/5.png", 
								"images/6.png", "images/7.png", "images/8.png", "images/9.png"};
	
	public static JTextPane[] snapshotScreens = new JTextPane[4];
	public static JLabel[][] structureItems = new JLabel[5][10];
	public static JPanel[][] structures = new JPanel[5][10];
	private static JButton[] otherButtons = new JButton[5];
	private static JButton[] operators = new JButton[5];
	private static JButton[] digits = new JButton[10];
	
	public static JTextArea screen;
	private JPanel animationPanel;
	private JPanel structurePanel;
	private JPanel snapshotPanel;
	private JPanel labelsPanel;
	private Converter convert;
	
	private String ss = "READ|    PARSED   |   WRITTEN   |     STACK\n";
	private String string = "0";
	
	public Calculator() {
		
		setLayout(new GridLayout(1,2));
		setCalculatorComponents();
		
	}
		
	private void setCalculatorComponents() {
		
		JLabel heading = new JLabel("CRAZY CALCULATOR", SwingConstants.CENTER);
		heading.setFont(new Font("Eras Bold ITC", Font.BOLD, 20));
		heading.setForeground(Color.WHITE);
		
		screen = new JTextArea(5,25);
		screen.setForeground(Color.WHITE);
		screen.setBackground(new Color(40,40,40));
		screen.setFont(new Font("Consolas", Font.PLAIN, 40));
		screen.setEditable(false);
		screen.setText("0");
		
		JPanel operatorPanel = new JPanel();
		JPanel topNumPanel = new JPanel();
		JPanel screenPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel mainPanel = new JPanel();
		JPanel leftPanel = new JPanel();
		JPanel numPanel = new JPanel();
		animationPanel = new JPanel();
		structurePanel = new JPanel();
		snapshotPanel = new JPanel();
		labelsPanel = new JPanel();
		
		JPanel[] panels = { operatorPanel, topNumPanel, centerPanel, screenPanel, mainPanel, numPanel, leftPanel, snapshotPanel, animationPanel, labelsPanel, structurePanel };	
				
		operatorPanel.setPreferredSize(new Dimension(200,100));
		screenPanel.setPreferredSize(new Dimension(100,350));
		topNumPanel.setPreferredSize(new Dimension(50,70));
		leftPanel.setPreferredSize(new Dimension(550,200));
		
		structurePanel.setLayout(new GridLayout(1,4,3,3));
		operatorPanel.setLayout(new GridLayout(5,1,7,7));
		snapshotPanel.setLayout(new GridLayout(1,4,3,3));
		animationPanel.setLayout(new BorderLayout(0,0));
		topNumPanel.setLayout(new GridLayout(1,3,7,7));
		labelsPanel.setLayout(new GridLayout(1,4,3,3));
		centerPanel.setLayout(new BorderLayout(5,5));
		screenPanel.setLayout(new BorderLayout(5,5));
		leftPanel.setLayout(new GridLayout(2,1,5,5));
		mainPanel.setLayout(new BorderLayout(5,10));
		numPanel.setLayout(new GridLayout(4,3,7,7));
		
		snapshotPanel.setBorder(myBorder());
		operatorPanel.setBorder(myBorder());
		centerPanel.setBorder(myBorder());
		screenPanel.setBorder(myBorder());
										
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
				snapshotScreens[k].setFont(new Font("Consolas", Font.PLAIN, 15));
				snapshotScreens[k].setBackground(new Color(40,40,40));
				snapshotScreens[k].addKeyListener(new KeyHandler());
				snapshotScreens[k].setForeground(Color.WHITE);
				snapshotScreens[k].setEditable(false);
				snapshotPanel.add(snapshotScreens[k]);
				
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
				
		JScrollPane scroll;
		for(int ctr = 0; ctr < 4; ctr++) {
			scroll = new JScrollPane(snapshotScreens[ctr]);
			snapshotPanel.add(scroll);
		}
		
		setAnimationPanel();
		leftPanel.add(snapshotPanel);
		leftPanel.add(animationPanel);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(screenPanel, BorderLayout.NORTH);
		mainPanel.add(operatorPanel, BorderLayout.EAST);
		add(leftPanel);
		add(mainPanel);
		
	}	
	
	private void setAnimationPanel() {
		
		JLabel[] labels = new JLabel[5];
		JPanel[] structureHolder = new JPanel[5];
		Font font = new Font("Consolas", Font.PLAIN, 15);
		String[] strLabels = {"STACK", "QUEUE 1", "QUEUE 2", "PSEUDOARRAY", "LINKED LIST"};
		
		for(int x = 0; x < 5; x++) {
			structureHolder[x] = new JPanel();
			structureHolder[x].setBackground(Color.BLACK);
			structureHolder[x].setLayout(new GridLayout(10,1));
			structurePanel.add(structureHolder[x]);	
			
			for(int y = 9; y >= 0; y--) {
				structures[x][y] = new JPanel();
				structureItems[x][y] = new JLabel(" ", SwingConstants.CENTER);
				structureItems[x][y].setPreferredSize(new Dimension(130,28));
				structureItems[x][y].setForeground(Color.WHITE);
				structureItems[x][y].setBackground(Color.BLACK);
				structureItems[x][y].setOpaque(true);
				structureItems[x][y].setFont(font);
				structures[x][y].setBackground(Color.DARK_GRAY);
				structures[x][y].add(structureItems[x][y]);
				structureHolder[x].add(structures[x][y]);
			}
			
			labels[x] = new JLabel(strLabels[x], SwingConstants.CENTER);
			labels[x].setForeground(Color.WHITE);
			labelsPanel.add(labels[x]);
			labels[x].setFont(font);
		}
		animationPanel.add(structurePanel, BorderLayout.CENTER);
		animationPanel.add(labelsPanel, BorderLayout.SOUTH);
		
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
		for(int x = 0; x < 4; x++)
			snapshotScreens[x].setText(s[x]);
		
	}
	
	public static void enableButtons(boolean bool) {
		
		for(int x = 0; x < 10; x++) {
			digits[x].setEnabled(bool);
			if(x < 5) {
				otherButtons[x].setEnabled(bool);
				operators[x].setEnabled(bool);
			}
		}
		
	}
	
	private void input(String s) {
		
		if(string.equals("0") && !Converter.isOperator(s)) {
			screen.setText(s);
			string = s;
		}else{
			screen.append(s);
			string += s;
		}
		
	}
	
	public void clearAll() {
		
		convert = new Converter();
		screen.setText("0");
		resetTextPane();
		string = "0";
		
	}
	
	public void clear() {
		
		try{			
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
		}catch(StringIndexOutOfBoundsException s) { s.printStackTrace(); }
		
	}
	
	public void compute() {
		
		System.out.print(ss);
		resetTextPane();
		convert = new Converter(string);
		convert.start();
		enableButtons(false);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == otherButtons[0]) System.exit(0);
		else if(e.getSource() == otherButtons[1]) clearAll();
		else if(e.getSource() == otherButtons[2]) clear();
		else if(e.getSource() == otherButtons[3]) input("(");
		else if(e.getSource() == otherButtons[4]) input(")");
		else if(e.getSource() == operators[4]) compute();
		
		for(int x = 0; x < 10; x++) {
			if(e.getSource() == digits[x]) 
				input(String.valueOf(x));
			else if(x < 4 && e.getSource() == operators[x]) {
				String[] symbols = {"/", "*", "-", "+"};
				input(symbols[x]);
			}
		}
		
	}
	
	String[] nums = {"0(2).png", "1(2).png", "2(2).png", "3(2).png", "4(2).png", "5(2).png", "6(2).png", "7(2).png", "8(2).png", "9(2).png"};
	String[] operations = {"divide(2).png", "multiply(2).png", "subtract(2).png", "add(2).png", "equals(2).png"};
	String[] others = {"off(2).png", "clear(2).png", "backspace(2).png", "openP(2).png", "closeP(2).png"};
			
	private class MouseHandler extends MouseAdapter {
		
		public void mousePressed(MouseEvent m) {		
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
	
	private class KeyHandler extends KeyAdapter {
		
		char[] symbols = {'/', '*', '-', '+'};
		int[] numKeys = { KeyEvent.VK_0, KeyEvent.VK_1, KeyEvent.VK_2, 
						  KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5, 
						  KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9 };
		
		public void keyPressed(KeyEvent k) {
			for(int x = 0; x < numKeys.length; x++) {
				if(k.getKeyCode() == numKeys[x])
					digits[x].setIcon(new ImageIcon("images/" + nums[x]));
				else if(x < 4 && k.getKeyChar() == symbols[x])
					operators[x].setIcon(new ImageIcon("images/" + operations[x]));
			}
			
			if(k.getKeyChar() == '(') 
				otherButtons[3].setIcon(new ImageIcon("images/" + others[3]));
			else if(k.getKeyChar() == ')')
				otherButtons[4].setIcon(new ImageIcon("images/" + others[4]));
			else if(k.getKeyCode() == KeyEvent.VK_BACK_SPACE)
				otherButtons[2].setIcon(new ImageIcon("images/" + others[2]));
			else if(k.getKeyCode() == KeyEvent.VK_DELETE)
				otherButtons[1].setIcon(new ImageIcon("images/" + others[1]));
			else if(k.getKeyCode() == KeyEvent.VK_ENTER)
				operators[4].setIcon(new ImageIcon("images/" + operations[4]));
			else if(k.getKeyCode() == KeyEvent.VK_ESCAPE) 
				otherButtons[0].setIcon(new ImageIcon("images/" + others[0]));
		}
		
		public void keyReleased(KeyEvent k) {
			for(int y = 0; y < numKeys.length; y++) {
				if(k.getKeyCode() == numKeys[y]) {
					digits[y].setIcon(new ImageIcon(numImgs[y]));
					input(String.valueOf(y));
				}else if(y < 4 && k.getKeyChar() == symbols[y]) {
					operators[y].setIcon(new ImageIcon(operatorImgs[y]));
					input(String.valueOf(symbols[y]));
				}
			}
			
			if(k.getKeyChar() == '(') {
				otherButtons[3].setIcon(new ImageIcon(otherImgs[3]));
				input("(");
			}else if(k.getKeyChar() == ')') {
				otherButtons[4].setIcon(new ImageIcon(otherImgs[4]));
				input(")");	
			}else if(k.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				otherButtons[2].setIcon(new ImageIcon(otherImgs[2]));
				clear();
			}else if(k.getKeyCode() == KeyEvent.VK_DELETE) {
				otherButtons[1].setIcon(new ImageIcon(otherImgs[1]));
				clearAll();
			}else if(k.getKeyCode() == KeyEvent.VK_ENTER) {
				operators[4].setIcon(new ImageIcon(operatorImgs[4]));
				compute();
			}else if(k.getKeyCode() == KeyEvent.VK_ESCAPE)
				System.exit(0);
		}
		
	}	
	
	
}
