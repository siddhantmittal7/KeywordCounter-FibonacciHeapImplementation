public class Node {
    //Pointers to left right,child and parent
    Node left;
    Node right;
    Node child;
    Node parent;
    int degree = 0;
    boolean mark = false;
    String word = "";
    int key;

    Node(String word, int key)
    {
        this.left = this;
        this.right = this;
        this.parent = null;
        this.degree = 0;
        this.word = word;
        this.key = key;

    }
}
