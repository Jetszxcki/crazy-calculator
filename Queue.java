
public class Queue {
	
	private PseudoArray arr;
	private Thread thread;
	private int rear = -1;
	private int num;
	
	public Queue(int num, int length) {
		
		arr = new PseudoArray(length);
		this.num = num;
		
		thread = new Thread() {
			public void run() {
				dequeue();
			}
		};
		
	}
	
	public void enqueue(String item) {
		
		rear++;
		arr.add(item);
		
		if(Converter.threadTime != 0) {
			Calculator.structures[num][rear].setBackground(Calculator.colors[rear%9]);
			Calculator.structureItems[num][rear].setText(item);			
		}
		
	}
	
	public String dequeue() {
				
		if(Converter.threadTime != 0) {
			thread = new Thread();
			thread.start();
		}
		
		try{
			if(Converter.threadTime != 0) {
				int ctr = 1;
				Thread.sleep(100);
				Calculator.structureItems[3][0].setText(" ");
				Calculator.structureItems[4][0].setText(" ");
				Calculator.structures[3][0].setBackground(Calculator.colors[10]);
				Calculator.structures[4][0].setBackground(Calculator.colors[10]);
				Calculator.structures[num][0].setBackground(Calculator.colors[10]);
				Calculator.structureItems[num][0].setText(" ");
				
				if(rear != 0) {
					while(ctr <= rear) {
						Calculator.structureItems[3][ctr-1].setText(Calculator.structureItems[3][ctr].getText());
						Calculator.structureItems[4][ctr-1].setText(Calculator.structureItems[4][ctr].getText());
						Calculator.structures[3][ctr-1].setBackground(Calculator.colors[(ctr-1)%9]);
						Calculator.structures[4][ctr-1].setBackground(Calculator.colors[(ctr-1)%9]);
						Calculator.structures[4][ctr].setBackground(Calculator.colors[10]);
						Calculator.structureItems[3][ctr].setText((ctr < rear) ? " " : "NULL");
						Calculator.structureItems[4][ctr].setText((ctr < rear) ? " " : "NULL");
						if(ctr == rear) Calculator.structureItems[4][ctr+1].setText(" ");
						
						Calculator.structureItems[num][ctr-1].setText(Calculator.structureItems[num][ctr].getText());
						Calculator.structures[num][ctr-1].setBackground(Calculator.colors[(ctr-1)%9]);
						Calculator.structures[num][ctr].setBackground(Calculator.colors[10]);
						Calculator.structureItems[num][ctr].setText(" ");
						ctr++;
					}
					Calculator.structures[num][rear].setBackground(Calculator.colors[10]);
					Calculator.structures[4][rear].setBackground(Calculator.colors[10]);
					Calculator.structureItems[4][rear].setText("NULL");
					Calculator.structureItems[num][rear].setText(" ");	
				}
			}
			
			if(rear == 0)
				Calculator.structureItems[4][1].setText(" ");
			rear--;
			
			if(rear == -1 && Converter.threadTime != 0) {
				Calculator.structures[num][0].setBackground(Calculator.colors[10]);
				Calculator.structures[4][0].setBackground(Calculator.colors[10]);
				Calculator.structures[3][0].setBackground(Calculator.colors[0]);
				Calculator.structureItems[3][0].setText("NULL");
				Calculator.structureItems[4][0].setText("NULL");
			}
						
		}catch(Exception e) { e.printStackTrace(); }
		
		return arr.delete();
		
	}	
	
	public int rr() {
		return rear;
	}
	
	public boolean isEmpty() {
		return arr.isEmpty();
	}
	
	public void display() {
		arr.display();
	}
	
}
