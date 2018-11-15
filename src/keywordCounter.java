import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class keywordCounter {

    public static void main(String[] args){

        String  pathtofile = args[0];

        //String  pathtofile = "/Users/siddhantmittal/Documents/ADS/Programing assignment/input4.txt";

        HashMap<String,Node> keywordMap = new HashMap<String,Node>();

        //Create an object of the fibonacci Heap
        FibonacciHeap fibonacciHeap = new FibonacciHeap();

        //Output File & writer pointer
        File file = new File("output_file.txt");
        BufferedWriter writer=null;

        // try IOException and other unchecked exception
        try {

            //Setting up of reading input file
            BufferedReader br = new BufferedReader(new FileReader(pathtofile));
            writer = new BufferedWriter( new FileWriter(file));

            String s = br.readLine();

            //Various patterns to  match input
            String keywordMatch = "([$])([^\\s]+)(\\s)(\\d+)";
            String queryMatch = "(^\\d)";
            String endMatch = "stop";

            Pattern case1 = Pattern.compile(keywordMatch);
            Pattern case2 = Pattern.compile(queryMatch);
            Pattern case3 = Pattern.compile(endMatch);

            while(s != null){
                String word = "";
                int frequency = 0;


                Matcher result1 = case1.matcher(s);
                Matcher result2 = case2.matcher(s);
                Matcher result3 = case3.matcher(s);

                //Case 1 match that is matching the keyword frequency match
                if(result1.find()){
                    word = result1.group(2);
                    frequency = Integer.parseInt(result1.group(4));
                    s = s.substring(1,s.length());
                    String[] stringPara = s.split(" ");
                    word = stringPara[0];
                    frequency = Integer.valueOf(stringPara[1]);

                    //if keyword already exist we increase the key of the exiting node
                    if(keywordMap.containsKey(word)){
                        Node node = keywordMap.get(word);
                        fibonacciHeap.increaseKey(node,frequency + node.key);
                    }else{ // else we insert a new node into the heap
                        Node node = new Node(word,frequency);
                        fibonacciHeap.insert(node);
                        keywordMap.put(word,node);
                    }
                }
                else if(result3.find()){//case 3 if we encounter a stop we terminate the program
                    break;
                }
                else if(result2.find()){//case 2 if a numeric number is found a query is executed
                    int count = Integer.valueOf(result2.group(1));

                    List<Node> listOfRemovedNode = new ArrayList<Node>();

                    for(int i=0;i<count;i++){
                        //fibonacciHeap.printRootList();
                        //System.out.println("removing max");
                        Node node =  fibonacciHeap.removeMax();
                        if(node != null){
                            keywordMap.remove(node.word);
                            //fibonacciHeap.printRootList();
                            listOfRemovedNode.add(node);
                        }
                    }

                    //reinserting the keyword back into the heap
                    for(int i=0;i<listOfRemovedNode.size();i++){
                        Node node = listOfRemovedNode.get(i);
                        if(i == listOfRemovedNode.size()-1){
                            writer.write(node.word);
                        }else{
                            writer.write(node.word + ",");
                        }
                        //new node is created as we want to remove any previous history of the node
                        String wordNew = node.word;
                        int freq = node.key;
                        Node nodeNew = new Node(wordNew,freq);
                        fibonacciHeap.insert(nodeNew);
                        keywordMap.put(wordNew,nodeNew);
                    }
                    writer.newLine();
                }

                s = br.readLine();
            }
        }    catch(Exception e){
            System.out.println(e);
        }    finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

        return;
    }
}

