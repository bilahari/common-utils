/**
 * 
 */
package personnel.bilahari.learnings.queue.singlyLL;

/**
 * @author bilahari.th
 *
 */
public class SinglyLinkedList<E> {

	private Node<E> head;

	public void append(E data){
		if(head != null){
			Node<E> tempNode = head;
			while(tempNode.getNext() != null){
				tempNode = tempNode.getNext();
			}
			tempNode.setNext(new Node<E>(data));
		}else{
			head = new Node<E>(data);
		}
	}

	public E delete(int index){
		if(head == null){							//	When LL is null
			return null;
		}else{
			Node<E> deletedNode;
			E data;
			if(index == 0){							// Case 1 : deleting head
				deletedNode = head;
				data = head.getData();
				head = head.getNext();
				return data;
			}else{									//	Case 2 : deleting nodes other than head
				Node<E> tempNode = head;
				for(int i=0;i<index-1;i++){
					tempNode = tempNode.getNext();
				}
				deletedNode = tempNode.getNext();
				data = deletedNode.getData();
				tempNode.setNext(tempNode.getNext().getNext());
				return data;
			}
		}
	}

	@Override
	public String toString() {
		return "SinglyLinkedList [head=" + head + "]";
	}

}
