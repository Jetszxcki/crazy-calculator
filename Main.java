import javax.swing.JFrame;

public class Main {

	public static void main(String args[]) {
		
		Calculator c = new Calculator();
		c.setUndecorated(true);
		c.setVisible(true);
		c.setLocationRelativeTo(null);
		c.setExtendedState(JFrame.MAXIMIZED_BOTH);
		c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
}