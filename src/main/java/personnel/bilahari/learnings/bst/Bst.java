package personnel.bilahari.learnings.bst;

import personnel.bilahari.common.utils.NumberUtils;

public class Bst {

	private Node root;

	public Bst() {
	}

	public Bst(Node root) {
		this.root = root;
	}

	public int getHeight(){
		if(root == null){
			return 0;
		}else{
			return 1 + NumberUtils.max(new Bst(root.getLeft()).getHeight(), new Bst(root.getRight()).getHeight());
		}
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
		String preOrderTraversalStr = null;
		Node tempNode =root;
		if(tempNode != null){
			String leftSubtreeTraversal = new Bst(tempNode.getLeft()).getPreorderTraversal(); 
			String rightSubtreeTraversal = new Bst(tempNode.getRight()).getPreorderTraversal();
			preOrderTraversalStr = tempNode.getData()+"";
			if(leftSubtreeTraversal != null && rightSubtreeTraversal != null){
				preOrderTraversalStr += ","+leftSubtreeTraversal+","+rightSubtreeTraversal;
			}else if(rightSubtreeTraversal != null){
				preOrderTraversalStr += ","+rightSubtreeTraversal;
			}else if(leftSubtreeTraversal != null){
				preOrderTraversalStr += ","+leftSubtreeTraversal;
			}
		}
		return preOrderTraversalStr;
	}
}
