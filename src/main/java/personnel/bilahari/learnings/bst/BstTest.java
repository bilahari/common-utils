package personnel.bilahari.learnings.bst;

public class BstTest {

	public static void main(String[] args){
		Bst bst =new Bst();

		bst.insert(10);
		bst.insert(5);
		bst.insert(20);
		bst.insert(3);
		bst.insert(7);
		bst.insert(15);
		bst.insert(25);
		bst.insert(8);
		
		System.out.println(bst.search(11));
		System.out.println(bst.search(7));
		
		System.out.println(bst.getPreorderTraversal());
		
		System.out.println("Height : "+bst.getHeight());
	}
}
