public class FibonacciHeap {

    private Node maxNode;
    private int numNodes;


    /*
        The following procedure inserts node x into Fibonacci heap H ,
        assuming that the node has already been allocated and that x:key has already been filled in.
     */
    public void insert(Node node){

        if(maxNode == null){
            maxNode = node;
        }else{
            node.right = maxNode.right;
            maxNode.right = node;
            node.left = maxNode;
            node.right.left = node;
        }

        if(node.key > maxNode.key){
            maxNode = node;
        }

        numNodes = numNodes +1;
    }

    /*
        remove max works by first making a root out of each of the maximum node’s
        children and removing the maximum node from the root list. It then consolidates
        the root list by linking roots of equal degree until at most one root
        remains of each degree
    */
    public Node removeMax(){
        Node z = maxNode;
        if(z != null){
            int degree = maxNode.degree;

            Node child = z.child;
            Node nextNode;

            //add all childern to the root list of H
            while(degree > 0){
                nextNode = child.right;

                //removing child from children list
                child.right.left = child.left;
                child.left.right = child.right;

                //following the insert steps into root list
                child.right = maxNode.right;
                maxNode.right = child;
                child.left = maxNode;
                child.right.left = child;
                child.parent = null;

                child = nextNode;
                degree--;
            }

            z.left.right = z.right;
            z.right.left = z.left;
            if(z == z.right)
                maxNode = null;
            else{
                maxNode = z.right;
                consolidate();
            }
        }
        numNodes--;
        return z;
    }

    /*
        The following procedure unites Fibonacci heaps H1 and H2,
        destroying H1 and H2 in the process. It simply concatenates
        the root lists of H1 and H2 and then deter- mines the new maximum node.
        Afterward, the objects representing H1 and H2 will never be used again.
     */

    public void joinTrees(Node y, Node x){

        y.right.left = y.left;
        y.left.right = y.right;
        y.parent = x;
        //If no child exist
        if (x.child == null) {
            x.child = y;
            y.right = y;
            y.left = y;
        } else {
            y.left = x.child;
            y.right = x.child.right;
            x.child.right = y;
            y.right.left = y;
        }
        x.degree = x.degree + 1;
        y.mark = false;
    }

    /*
        The next step, in which we reduce the number of trees in the Fibonacci heap,
        is consolidating the root list of H, which the call CONSOLIDATE.H/ accomplishes.
        Consolidating the root list consists of repeatedly executing the following steps until
        every root in the root list has a distinct degree value:
        1. Find two roots x and y in the root list with the same degree. Without loss of generality, let x:key 􏰎 y:key.
        2. joinTrees procedure. This procedure increments the attribute x:degree and clears the mark on y.
     */

    public void consolidate(){

        //maxium bound on the degree
        double fi = (1 + Math.sqrt(5))/2;

        double s = Math.log(numNodes)/Math.log(fi);
        int sizeofDegreeTable = 2*(int) s + 1;

        //auxiliary array storing degrees
        Node[] degreeArray = new Node[sizeofDegreeTable];


        int numRoots = 0;
        Node x = maxNode;

        //calculating the number of nodes in the root list
        do {
            numRoots++;
            x = x.right;
        }while(x !=maxNode);

        //degree wise combine
        while(numRoots>0){
            int degree = x.degree;
            Node next = x.right;

            while(degreeArray[degree] != null){
                Node y = degreeArray[degree];
                if(x.key < y.key){
                    Node temp = y;
                    y = x;
                    x = temp;
                }
                joinTrees(y,x);
                degreeArray[degree] = null;
                degree = degree + 1;
            }
            degreeArray[degree] = x;
            numRoots--;
            x = next;
        }

        maxNode = null;

        //finding the max node and creating the root list
        for(int i=0;i<degreeArray.length;i++){
            Node z = degreeArray[i];
            if(z != null){
                //create a root list for H containing just AŒi􏰀
                if(maxNode == null){
                    maxNode = z;
                    maxNode.left = z;
                    maxNode.right = z;
                }else{// insert AŒi 􏰀 into H ’s root list
                    z.left = maxNode;
                    z.right = maxNode.right;
                    maxNode.right = z;
                    z.right.left = z;
                    if(z.key > maxNode.key){
                        maxNode = z;
                    }
                }
            }
        }
    }

    /*
        In the following procedure for the operation FIB-HEAP-INCREASE-KEY,
        we assume as before that removing a node from a linked list does not
        change any of the structural attributes in the removed node.
     */

    public void increaseKey(Node z, int k){
        if(k < z.key){
            System.out.println("attempted to decrease key");
            return;
        }
        z.key = k;
        Node p = z.parent;

        if(p != null && z.key > p.key){
            cut(z,p);
            cascadingCut(p);
        }

        if(z.key > maxNode.key){
            maxNode = z;
        }
    }

    /*
        Removing the node if its key increases more than its root
     */

    public void cut(Node child, Node parent){
        //remove child from the child list of parent, decrementing parent:degree
        //add child to the root list of H
        // removes child from child pointer of parent and decreases the degree
        child.left.right = child.right;
        child.right.left = child.left;
        parent.degree--;

        // reset parents child pointer to it's other child if necessary
        if (parent.child == child) {
            parent.child = child.right;
        }

        if (parent.degree == 0) {
            parent.child = null;
        }

        // add child to root list of heap
        child.left = maxNode;
        child.right = maxNode.right;
        maxNode.right = child;
        child.right.left = child;
        child.parent = null;
        child.mark = false;
    }

    /*
        The reason for the cascading cut is to keep D(n) low. It turns out that
        if you allow any number of nodes to be cut from a tree, then D(n) can
        grow to be linear, which makes delete and delete-min take linear time.
        Intuitively, you want the number of nodes in a tree of order k to be exponential in k.
        That way, you can have only logarithmically many trees in a consolidated heap.
        If you can cut an arbitrary number of nodes from a tree, you lose this guarantee.
        Specifically, you could take a tree of order k, then cut all of its grandchildren.
        This leaves a tree with k children, each of which are leaves. Consequently, you can
        create trees of order k with just k + 1 total nodes in them. This means that in the
        worst case you would need a tree of order n - 1 to hold all the nodes, so
        D(n) becomes n - 1 rather than O(log n).
     */

    public void cascadingCut(Node z){
        Node x = z.parent;

        if (x != null) {
            if (!z.mark) {
                z.mark = true;
            } else {
                cut(z, x);
                cascadingCut(x);
            }
        }
    }

    /*
        Following procedure print the root list of the tree
     */

    public void printRootList(){

        int numRoots = 0;
        Node x = maxNode;

        do {
            numRoots++;
            x = x.right;
        }while(x !=maxNode);

        while(numRoots > 0){
            Node next = x.right;
            System.out.print(x.key + "--");
            numRoots--;
            x = next;
        }
        System.out.println();
    }


}
