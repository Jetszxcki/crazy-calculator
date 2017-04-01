
public class Queue {
	
	private PseudoArray arr;
	private int rear = -1;
	private int num;
	
	public Queue(int num) {
		
		arr = new PseudoArray(10);
		this.num = num;
		
	}
	
	public void enqueue(String item) {
		
		arr.add(item);
		rear++;
		Calculator.structures[num][rear].setBackground(Calculator.colors[rear]);
		Calculator.structureItems[num][rear].setText(item);
		
	}
	
	public String dequeue() {
		
		int ctr = 1;
		Calculator.structureItems[num][0].setText(" ");
		
		if(rear != 0) {
			while(ctr < rear) {
				Calculator.structureItems[num][ctr-1].setText(Calculator.structureItems[num][ctr].getText());
				Calculator.structures[num][ctr-1].setBackground(Calculator.colors[ctr-1]);
				ctr++;
			}
			Calculator.structureItems[num][rear].setText(" ");		
			Calculator.structures[num][rear].setBackground(Calculator.colors[10]);
		}
		rear--;
		if(rear == -1)
			Calculator.structures[num][0].setBackground(Calculator.colors[10]);
		
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
