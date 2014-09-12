package personnel.bilahari.learnings.bst;

public class Bst {

	private Node root;

	public Bst() {
	}
	public Bst(Node root) {
		this.root = root;
	}

	public void insert(int data){
		Node newNode = new Node(data);
		if(root == null){
			root = newNode;
		}else{
			Node tempNode = root;
			boolean isInserted = false;
			while(!isInserted){
				if(data < tempNode.getData()){
					if(tempNode.getLeft() != null){
						tempNode = tempNode.getLeft();
					}else{
						tempNode.setLeft(newNode);
						isInserted = true;
					}
				}else{
					if(tempNode.getRight() != null){
						tempNode = tempNode.getRight();
					}else{
						tempNode.setRight(newNode);
						isInserted = true;
					}
				}
			}
		}
	}

	public boolean search(int data){
		Node tempNode = root;
		while(tempNode != null){
			if(tempNode.getData() == data){
				return true;
			}else if(data < tempNode.getData()){
				tempNode = tempNode.getLeft();
			}else{
				tempNode = tempNode.getRight();
			}
		}
		return false;
	}

	public String getPreorderTraversal(){
		String inorderTraversalStr = null;
		Node tempNode =root;
		if(tempNode != null){
			String leftSubtreeTraversal = new Bst(tempNode.getLeft()).getPreorderTraversal(); 
			String rightSubtreeTraversal = new Bst(tempNode.getRight()).getPreorderTraversal();
			inorderTraversalStr = tempNode.getData()+"";
			if(leftSubtreeTraversal != null && rightSubtreeTraversal != null){
				inorderTraversalStr += ","+leftSubtreeTraversal+","+rightSubtreeTraversal;
			}else if(rightSubtreeTraversal != null){
				inorderTraversalStr += ","+rightSubtreeTraversal;
			}else if(leftSubtreeTraversal != null){
				inorderTraversalStr += ","+leftSubtreeTraversal;
			}
		}
		return inorderTraversalStr;
	}
}
