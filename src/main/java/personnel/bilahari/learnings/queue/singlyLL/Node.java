/**
 * 
 */
package personnel.bilahari.learnings.queue.singlyLL;

/**
 * @author bilahari.th
 *
 */
public class Node<E> {

	private E data;
	private Node next;

	public Node(E data) {
		this.data = data;
	}

	public Node(E data, Node next) {
		this.data = data;
		this.next = next;
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	@Override
	public String toString() {
		if(next ==null ){
			return "Node [data=" + data + "] = > NULL";
		}else{
			return "Node [data=" + data + "] = > "+next.toString();
		}
	}
}
