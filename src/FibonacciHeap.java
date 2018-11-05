public class FibonacciHeap {

    private Node maxNode;
    private int numberOfNodes;


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

        numberOfNodes = numberOfNodes +1;
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

                //following the insert steps
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
        return z;
    }

    public void joinTrees(Node y, Node x){

        y.right.left = y.left;
        y.left.right = y.right;
        y.parent = x;
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

    public void consolidate(){

        double fi = (1 + Math.sqrt(5))/2;

        double s = Math.log(numberOfNodes)/Math.log(fi);
        int sizeofDegreeTable = 2*(int) s + 1;

        Node[] degreeArray = new Node[sizeofDegreeTable+2];


        int numRoots = 0;
        Node x = maxNode;

        if (x != null) {
            numRoots++;
            x = x.right;

            while (x != maxNode) {
                numRoots++;
                x = x.right;
            }
        }

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

        for(int i=0;i<degreeArray.length;i++){
            Node z = degreeArray[i];
            if(z != null){
                //create a root list for H containing just AŒi􏰀
                if(maxNode == null){
                    maxNode = z;
                    maxNode.left = z;
                    maxNode.right = z;
                }else{// insert AŒi 􏰀 into H ’s root list
                    maxNode.right.left = z;
                    z.right = maxNode.right;
                    maxNode.right = z;
                    z.left = maxNode;
                    if(z.key > maxNode.key){
                        maxNode = z;
                    }
                }
            }
        }
    }


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

    public void cut(Node child, Node parent){
        //remove child from the child list of parent, decrementing parent:degree
        //add child to the root list of H
        // removes x from child of y and decreases the degree of y
        child.left.right = child.right;
        child.right.left = child.left;
        parent.degree--;

        // reset y.child if necessary
        if (parent.child == child) {
            parent.child = child.right;
        }

        if (parent.degree == 0) {
            parent.child = null;
        }

        // add x to root list of heap
        child.left = maxNode;
        child.right = maxNode.right;
        maxNode.right = child;
        child.right.left = child;

        // set parent of x to nil
        child.parent = null;

        // set mark to false
        child.mark = false;
    }

    public void cascadingCut(Node z){
        Node x = z.parent;

        //if there is a parent
        if (x != null) {
            // if y is unmarked, set it marked
            if (!z.mark) {
                z.mark = true;
            } else {
                // it's marked, cut it from parent
                cut(z, x);

                // cut its parent as well
                cascadingCut(x);
            }
        }
    }

    public void printRootList(){

        int numRoots = 0;
        Node x = maxNode;

        if (x != null) {
            numRoots++;
            x = x.right;

            while (x != maxNode) {
                numRoots++;
                x = x.right;
            }
        }

        while(numRoots > 0){
            Node next = x.right;
            System.out.print(x.key + "--");
            numRoots--;
            x = next;
        }
        System.out.println();
    }


}
