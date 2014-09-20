/**
 * 
 */
package personnel.bilahari.learnings.queue.singlyLL;

/**
 * @author bilahari.th
 *
 */
public class TestSll {

	public static void main(String [] args){
		
		SinglyLinkedList<Integer> sll = new SinglyLinkedList<Integer>();
		
		sll.append(0);
		sll.append(1);
		sll.append(2);
		sll.append(3);
		sll.append(4);
		sll.append(5);
		
		System.out.println(sll);

		System.out.println(sll.delete(1));
		
		System.out.println(sll);
	}
}
