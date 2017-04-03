
public class Stack {
	
	private Queue q1;
	private Queue q2;
	private Thread thread;
	public int elements = 0;
	private boolean q1q2 = true;
	
	public Stack() {
		
		q1 = new Queue(1);
		q2 = new Queue(2);
		
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
		
		Calculator.structures[0][elements].setBackground(Calculator.colors[elements]);
		Calculator.structureItems[0][elements].setText(item);
		elements++;
		
	}
	
	
	
	public String pop() {
		
		String output = "";
		thread = new Thread();
		thread.start();
		
		try{
			
			if(q1q2) {
				while(q1.rr() != 0) {
					q2.enqueue(q1.dequeue());
					Thread.sleep(500);
				}
				q1q2 = false;
				output = q1.dequeue();
				
			}else if(!q1q2){
				while(q2.rr() != 0) {
					q1.enqueue(q2.dequeue());
					Thread.sleep(500);
				}
				q1q2 = true;
				output = q2.dequeue();
			}
			Thread.sleep(500);
			
		}catch(Exception e){ e.printStackTrace(); }
		
		elements--;
		Calculator.structures[0][elements].setBackground(Calculator.colors[10]);
		Calculator.structureItems[0][elements].setText(" ");
		return output;
		
	}
	
	public void display() {
		
		if(q1q2){
			q1.display();
		}else{
			q2.display();
		}
		
	}
	
}
