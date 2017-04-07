
public class Stack {
	
	private Queue q1;
	private Queue q2;
	private Thread thread;
	public int elements = 0;
	private boolean q1q2 = true;
	
	public Stack(int length) {
		
		q1 = new Queue(1, length);
		q2 = new Queue(2, length);
		
		thread = new Thread() { 
			public void run() { 
				pop(); 
			} 
		};
		
	}
	
	public void push(String item) {
		
		if(q1q2)
			q1.enqueue(item);
		else q2.enqueue(item);
		
		if(Converter.threadTime != 0) {
			Calculator.structures[0][elements].setBackground(Calculator.colors[elements%9]);
			Calculator.structures[4][elements].setBackground(Calculator.colors[elements%9]);
			Calculator.structureItems[0][elements].setText(item);
			Calculator.structureItems[3][elements].setText(item);
			Calculator.structureItems[4][elements].setText(item);
			Calculator.structureItems[4][++elements].setText("NULL");
		}
		
	}	
	
	public String pop() {
		
		String output = "";
		
		if(Converter.threadTime != 0) {			
			thread = new Thread();
			thread.start();
		}
		
		try{
			
			if(q1q2) {
				while(q1.rr() != 0) {
					q2.enqueue(q1.dequeue());
					//	Thread.sleep(500);
				}
				q1q2 = false;
				output = q1.dequeue();
				
			}else if(!q1q2) {
				while(q2.rr() != 0) {
					q1.enqueue(q2.dequeue());
					//Thread.sleep(500);
				}
				q1q2 = true;
				output = q2.dequeue();
			}
			Thread.sleep(500);
			
			elements--;
			
			if(Converter.threadTime != 0) {
				Calculator.structures[0][elements].setBackground(Calculator.colors[10]);
				Calculator.structureItems[0][elements].setText(" ");
				Thread.sleep(100);
			}
			
			int ctr = 0;
			while(ctr < elements) {
				Calculator.structureItems[4][ctr].setText(Calculator.structureItems[0][ctr].getText());
				Calculator.structureItems[3][ctr].setText(Calculator.structureItems[0][ctr].getText());
				Calculator.structures[4][ctr].setBackground(Calculator.colors[ctr%9]);
				Calculator.structures[3][ctr].setBackground(Calculator.colors[ctr%9]);
				ctr++;
			}
			Calculator.structureItems[4][elements].setText("NULL");
			
		}catch(Exception e){ e.printStackTrace(); }
		
		return output;
		
	}
	
	public void display() {
		
		if(q1q2)
			q1.display();
		else q2.display();
		
	}
	
}
