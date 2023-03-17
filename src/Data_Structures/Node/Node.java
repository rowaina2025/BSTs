package Data_Structures.Node;

public class Node<T extends Comparable<T>>{
    protected T data;
    public Node<T> parent = null;
    public Node<T> left = null;
    public Node<T> right = null;

    public Node(T data) {
        this.data = data;
    }
    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public T getData() {
            return data;
    }

    public void setData(T data){
        this.data = data;
    }

    public int compareTo(Node<T> o) {
        return this.data.compareTo(o.data);
    }

    public boolean isLeftChild(){
        return this == this.parent.left;
    }

    public boolean isRightChild(){
        return this == this.parent.right;
    }

    public Node<T> getSibling(){
        if(this.parent == null)
            return null;
        return (isLeftChild())? this.parent.right : this.parent.left;
    }

}
