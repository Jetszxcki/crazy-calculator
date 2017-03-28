
public class PseudoArray {
	
	private Link first;
	private Link last;
	private Link curr;
	
	public PseudoArray(int size) {
		
		first = null;
		last = null;
		
		for(int i = 0; i < size; i++) {
			Link newLink = new Link();
			
			if(isEmpty()) 
				first = newLink;
			else last.next = newLink;
			
			last = newLink;
		}
		curr = first;
	
	}
	
	public boolean isEmpty() {
		return first == null;
	}
	
	public void add(String item) {
		curr.setItem(item);
		curr = curr.next;
	}
	
	public String delete() {
		
		String temp = first.getItem();
		Link newLink = new Link();
		
		if(first.next == null)
			last = null;
		
		first = first.next;
		
		if(isEmpty())
			first = newLink;
		else last.next = newLink;
		
		last = newLink;
		return temp;
		
	}
	
	public void display() {
		
		Link current = first;
		
		while(current != null) {
			Calculator.snapshotScreens[3].setText(Calculator.snapshotScreens[3].getText() + current.getItem());
			System.out.print(current.getItem());
			current = current.next;
		}
		Calculator.snapshotScreens[3].setText(Calculator.snapshotScreens[3].getText() + "\n");
		System.out.println();
		
	}
	
}
