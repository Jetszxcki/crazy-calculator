
public class PseudoArray {
	
	private Link first;
	private Link last;
	private Link curr;
	private int count = 0;
	
	public PseudoArray(int size) {
		
		first = null;
		last = null;
		
		for(int i = 0; i < size; i++) {
			Link newLink = new Link();
			if(isEmpty()) 
				first = newLink;
			else last.next = newLink;
			
			last = newLink;
			Calculator.structures[3][i].setBackground(Calculator.colors[i]);
		}
		curr = first;
		
	}
	
	public boolean isEmpty() {
		return first == null;
	}
	
	public void add(String item) {
		
		Calculator.structureItems[3][count].setText(item);
		Calculator.structureItems[4][count].setText(item);
		curr.setItem(item);
		curr = curr.next;
		count++;
		
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
		Calculator.structureItems[3][count].setText(" ");
		Calculator.structureItems[4][count].setText(" ");
		count--;
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
