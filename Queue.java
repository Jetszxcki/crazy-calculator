
public class Queue {
	
	private PseudoArray arr;
	private int rear = -1;
	
	public Queue() {
		arr = new PseudoArray(20);
	}
	
	public void enqueue(String item) {
		arr.add(item);
		rear++;
	}
	
	public String dequeue() {
		rear--;
		return arr.delete();
	}
	
	public int rr() {
		return rear;
	}
	
	public void setRear(int rear) {
		this.rear = rear;
	}
	
	public boolean isEmpty() {
		return arr.isEmpty();
	}
	
	public void display() {
		arr.display();
	}
	
}
