import java.awt.Font;

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
		Calculator.structureItems[4][0].setText("NULL");
		curr = first;
		
	}
	
	public boolean isEmpty() {
		return first == null;
	}
	
	public void add(String item) {
		
		Calculator.structureItems[4][count].setFont(new Font("Consolas", Font.BOLD, 10));
		Calculator.structures[4][count].setBackground(Calculator.colors[count]);
		Calculator.structureItems[4][count].setText(item + "(" + curr + ")");
		Calculator.structureItems[4][count+1].setText("NULL");
		/*
		Calculator.structureItems[3][count].setText(item);
		*/
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
		
		int counter = count;
		//Calculator.structureItems[3][count].setText(" ");
		Calculator.structureItems[4][0].setText(" ");
		
		if(count != 0) {			
			while(counter <= count) {
				Calculator.structureItems[4][counter-1].setText(Calculator.structureItems[4][counter].getText());
				Calculator.structures[4][counter-1].setBackground(Calculator.colors[counter-1]);
				counter++;
			}
			Calculator.structureItems[4][count].setText(" ");	
			Calculator.structures[4][count].setBackground(Calculator.colors[10]);
		}
		
		count--;
		if(count == 0)  {
			Calculator.structures[4][0].setBackground(Calculator.colors[10]);
			//Calculator.structureItems[4][counter].setText("NULL");
		}
		
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
