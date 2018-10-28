import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class keywordCounter {

    public static void main(String[] args){

        //String  pathtofile = args[0];

        String  pathtofile = "/Users/siddhantmittal/ADSProject/src/input2.txt";

        HashMap<String,Node> keywordMap = new HashMap<String,Node>();

        //Create an object of the fibonacci Heap
        FibonacciHeap fibonacciHeap = new FibonacciHeap();

        //Output File & writer pointer
        File file = new File("output_file.txt");
        BufferedWriter writer=null;

        // try IOException and other unchecked exception
        try {

            BufferedReader br = new BufferedReader(new FileReader(pathtofile));
            writer = new BufferedWriter( new FileWriter(file));

            String s = br.readLine();

            while(s != null){
                String word = "";
                int frequency = 0;
                if(s.contains("$")){
                    s = s.substring(1,s.length());
                    String[] stringPara = s.split(" ");
                    word = stringPara[0];
                    frequency = Integer.valueOf(stringPara[1]);

                    if(keywordMap.containsKey(word)){
                        Node node = keywordMap.get(word);
                        fibonacciHeap.increaseKey(node,frequency + node.key);
                    }else{
                        Node node = new Node(word,frequency);
                        fibonacciHeap.insert(node);
                        keywordMap.put(word,node);
                    }
                }
                else if(s.equals("stop")){
                    return;
                }else{
                    int count = Integer.valueOf(s);

                    List<Node> listOfRemovedNode = new ArrayList<Node>();

                    for(int i=0;i<count;i++){
                        Node node =  fibonacciHeap.removeMax();
                        listOfRemovedNode.add(node);
                    }

                    for(int i=0;i<listOfRemovedNode.size();i++){
                        Node node = listOfRemovedNode.get(i);
                        if(i == listOfRemovedNode.size()-1){
                            writer.write(node.word);
                        }else{
                            writer.write(node.word + ",");
                        }
                        fibonacciHeap.insert(node);
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

