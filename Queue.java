
public class Queue {
	
	private PseudoArray arr;
	private Thread thread;
	private int rear = -1;
	private int num;
	
	public Queue(int num) {
		
		arr = new PseudoArray(10);
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
		Calculator.structures[num][rear].setBackground(Calculator.colors[rear]);
		Calculator.structureItems[num][rear].setText(item);
		
	}
	
	public String dequeue() {
		
		thread = new Thread();
		thread.start();
		int ctr = 1;
		
		try{
			
			Calculator.structures[num][0].setBackground(Calculator.colors[10]);
			Calculator.structureItems[num][0].setText(" ");
			Thread.sleep(500);
			
			if(rear != 0) {
				while(ctr <= rear) {
					Calculator.structureItems[num][ctr-1].setText(Calculator.structureItems[num][ctr].getText());
					Calculator.structures[num][ctr-1].setBackground(Calculator.colors[ctr-1]);
					Calculator.structures[num][ctr].setBackground(Calculator.colors[10]);
					Calculator.structureItems[num][ctr].setText(" ");
					Thread.sleep(500);
					ctr++;
				}
				Calculator.structures[num][rear].setBackground(Calculator.colors[10]);
				Calculator.structureItems[num][rear].setText(" ");	
			}
			Thread.sleep(500);
			rear--;
			
			if(rear == -1)
				Calculator.structures[num][0].setBackground(Calculator.colors[10]);
			
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
