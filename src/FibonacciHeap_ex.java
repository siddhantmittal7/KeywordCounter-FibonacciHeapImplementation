import java.util.*;

    public class FibonacciHeap_ex {

        private Node maxNode;
        private int numberOfNodes;

        /*
        The following procedure inserts node x into Fibonacci heap H ,
        assuming that the node has already been allocated and that
        x:key has already been filled in.
         */
        public void insert(Node node) {


            if (maxNode != null) {

                node.left = maxNode;
                node.right = maxNode.right;
                maxNode.right = node;
                node.right.left = node;

                if (node.key > maxNode.key) {
                    maxNode = node;
                }
            } else {
                maxNode = node;
            }

            numberOfNodes++;
        }

        /*
        remove max works by first making a root out of each of the maximum node’s
        children and removing the maximum node from the root list. It then consolidates
        the root list by linking roots of equal degree until at most one root
        remains of each degree
        */
        public Node removeMax() {
            Node z = maxNode;
            if (z != null) {
                int numberofChildren = z.degree;
                Node x = z.child;
                Node tempRight;

                //each other of maximum node gets added to root list
                while (numberofChildren > 0) {
                    tempRight = x.right;

                    x.left.right = x.right;
                    x.right.left = x.left;

                    x.left = maxNode;
                    x.right = maxNode.right;
                    maxNode.right = x;
                    x.right.left = x;
                    x.parent = null;

                    x = tempRight;
                    numberofChildren--;
                }


                // remove z from root list of heap
                z.left.right = z.right;
                z.right.left = z.left;

                if (z == z.right) {
                    maxNode = null;

                } else {
                    maxNode = z.right; //any random node consolidate will take care
                    consolidate();
                }
                numberOfNodes--;
                return z;
            }
            return null;
        }


        //performs cut operation. Cuts x from y
        public void cut(Node x, Node y) {
            // removes x from child of y and decreases the degree of y
            x.left.right = x.right;
            x.right.left = x.left;
            y.degree--;

            // reset y.child if necessary
            if (y.child == x) {
                y.child = x.right;
            }

            if (y.degree == 0) {
                y.child = null;
            }

            // add x to root list of heap
            x.left = maxNode;
            x.right = maxNode.right;
            maxNode.right = x;
            x.right.left = x;

            // set parent of x to nil
            x.parent = null;

            // set mark to false
            x.mark = false;
        }

        //Performs cascading cut on the given node as given in Cormen
        public void cascadingCut(Node y) {
            Node x = y.parent;

            //if there is a parent
            if (x != null) {
                // if y is unmarked, set it marked
                if (!y.mark) {
                    y.mark = true;
                } else {
                    // it's marked, cut it from parent
                    cut(y, x);

                    // cut its parent as well
                    cascadingCut(x);
                }
            }
        }

        //Increase the value of key for the given node in heap
        public void increaseKey(Node x, int k) {
            if (k < x.key) {
            }

            x.key = k;

            Node y = x.parent;

            if ((y != null) && (x.key > y.key)) {
                cut(x, y);
                cascadingCut(y);
            }

            if (x.key > maxNode.key) {
                maxNode = x;
            }
        }


        //performs degree wise merge(if 2 degrees are same then it merges it)
        public void consolidate() {
            //Maxium degree can be logn
            double fi = (1 + Math.sqrt(5))/2;

            double s = Math.log(numberOfNodes)/Math.log(fi);
            int sizeofDegreeTable = 2*(int) s + 1;


            List<Node> degreeTable =
                    new ArrayList<Node>(sizeofDegreeTable);

            // Initialize degree table
            for (int i = 0; i < sizeofDegreeTable; i++) {
                degreeTable.add(null);
            }


            // Find the number of root nodes.
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

            // For each node in root list
            while (numRoots > 0) {

                int d = x.degree;
                Node next = x.right;

                // check if the degree is there in degree table, if not add,if yes then combine and merge
                for (; ; ) {
                    Node y = degreeTable.get(d);
                    if (y == null) {
                        break;
                    }

                    //Check whos key value is greater
                    if (x.key < y.key) {
                        Node temp = y;
                        y = x;
                        x = temp;
                    }

                    //make y the child of x as x key value is greater
                    makeChild(y, x);

                    //setthe degree to null as x and y are combined now
                    degreeTable.set(d, null);
                    d++;
                }

                //store the new x(x+y) in the respective degree table postion
                degreeTable.set(d, x);

                // Move forward through list.
                x = next;
                numRoots--;
            }


            //Deleting the max node
            maxNode = null;

            // combine entries of the degree table
            for (int i = 0; i < sizeofDegreeTable; i++) {
                Node y = degreeTable.get(i);
                if (y == null) {
                    continue;
                }

                //till max node is not null
                if (maxNode != null) {

                    // First remove node from root list.
                    y.left.right = y.right;
                    y.right.left = y.left;

                    // Now add to root list, again.
                    y.left = maxNode;
                    y.right = maxNode.right;
                    maxNode.right = y;
                    y.right.left = y;

                    // Check if this is a new maximum
                    if (y.key > maxNode.key) {
                        maxNode = y;
                    }
                } else {
                    maxNode = y;
                }
            }
        }

        //Makes y the child of node x
        public void makeChild(Node y, Node x) {
            // remove y from root list of heap
            y.left.right = y.right;
            y.right.left = y.left;

            // make y a child of x
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

            // increase degree of x by 1
            x.degree++;

            // make mark of y as false
            y.mark = false;
        }
    }