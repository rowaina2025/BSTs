package Data_Structures.Tree;

import Data_Structures.Node.AVLNode;
import Data_Structures.Node.Node;
import Data_Structures.Node.RBNode;

public class AVL<T extends Comparable<T>> extends BST<T> {
    /**
     * Each tree has its own root, so it is removed from the parent class
     * We can just add it for normal node, but it's preferred to make each tree has its own root!
     * */

    public AVL(T data) {
        this.root = new AVLNode<>(data);
        this.root.setParent(null);
        this.root.setLeft(null);
        this.root.setRight(null);
    }
    public AVL(AVLNode<T> root) {
        this.root = root;
        if(this.root.getParent() == null)
            this.root.setParent(null);
        if(this.root.getRight() == null)
            this.root.setRight(null);
        if(this.root.getLeft() == null)
            this.root.setLeft(null);
    }
    public AVL() { }

    public AVLNode<T> getRoot(){
        return (AVLNode<T>) this.root;
    }
    public int getHeight() {
        return ((AVLNode<T>) this.root).getHeight();
    }

    public boolean insert(T data) {
        AVLNode<T> newNode = new AVLNode<>(data);
        if(node_isNull(this.root)){
            this.root = newNode;
            return true;
        }
        if(node_isNull(super.insert(this.root, null, newNode)))
            return false;
        update(newNode.getParent());
        return true;
    }

    public boolean delete(T key) {
        AVLNode<T> node = (AVLNode<T>) super.search(this.root, key);
        RBNode<T> deletedNode = (RBNode<T>) super.delete(node);
//        fixDelete(deletedNode);
        if(deletedNode == this.root)
            this.root = null;
        else if(deletedNode.isLeftChild())
            deletedNode.getParent().setLeft(null);
        else
            deletedNode.getParent().setRight(null);
        if(node != null)
            size--;
        return node != null;
    }

//    public AVLNode<T> insert(AVLNode<T> node,T data) {
//        //insert data in its place in bst
//        AVLNode<T> tmp = new AVLNode<T>(data);
//        if(node == null){
//            node = tmp;
//            return node;
//        }
//        int compare = node.getData().compareTo(data);
//        if(node.getLeft() == null && compare > 0) {
//            node.setLeft(tmp);
//            tmp.setParent(node);
//            System.out.println("Parent " + node.getData() + " left child " + node.getLeft().getData());
//            return tmp;
//        } else if(node.getRight() == null && compare < 0) {
//            node.setRight(tmp);
//            tmp.setParent(node);
//            System.out.println("Parent " + node.getData() + " left child " + node.getLeft().getData());
//            return tmp;
//        } else if(node.left != null && compare > 0) {
//            insert(node.left, data);
//        } else if(node.right != null && compare < 0) {
//            insert(node.right, data);
//        } else if((node.left != null && node.left.getData().compareTo(data) == 0) || (node.right != null && node.right.getData().compareTo(data) == 0)) {
//            System.out.println("Found!!\n");
//            return null;
//        }
//        return tmp;
//    }

    public void update(AVLNode<T> Y) {
        if (Y == null) return;
        int bf = Y.setBalance();
        // Test for imbalance and fix heights
        if (bf == -2){ // Left imbalance
            if (Y.getLeft().getBalance() == 1)
                leftRightFix(Y); // Caused by right subtree of left child
            else
                leftLeftFix(Y); // Caused by left subtree of left child
        }else if (bf == 2){ // Right imbalance
            if (Y.getRight().getBalance() == -1)
                rightLeftFix(Y); // Caused by left subtree of right child
            else
                rightRightFix(Y); // Caused by right subtree of right child
        }else{ // Node has no imbalance
            Y.setHeight();
        }
        update(Y.getParent());
    }
    private void leftLeftFix(AVLNode<T> x){
        AVLNode<T> y = x.getLeft();
        rotateRight(x);
        x.setHeight(); x.setBalance();
        y.setHeight(); y.setBalance();
    }
    private void leftRightFix(AVLNode<T> x){
        AVLNode<T> y = x.getLeft(), z = y.getRight();
        rotateLeft(y); rotateRight(x);
        y.setHeight(); y.setBalance();
        x.setHeight(); x.setBalance();
        z.setHeight(); z.setBalance();
    }
    private void rightLeftFix(AVLNode<T> x){
        AVLNode<T> y = x.getRight(), z = y.getLeft();
        rotateRight(y); rotateLeft(x);
        y.setHeight(); y.setBalance();
        x.setHeight(); x.setBalance();
        z.setHeight(); z.setBalance();
    }
    private void rightRightFix(AVLNode<T> x) {
        AVLNode<T> y = x.getRight();
        rotateLeft(x);
        x.setHeight(); x.setBalance();
        y.setHeight(); y.setBalance();
    }

    public void printTree(){
        printPreorder(root);
        System.out.println();
    }

    public void batchInsert(T[] data){
        for (int i=0 ; i < data.length ; i++) insert(data[i]);
    }

    private void printPreorder(Node n){
        if (n == null) return;
        System.out.print(n.getData() + " ");
        printPreorder(n.getLeft());
        printPreorder(n.getRight());
    }

}