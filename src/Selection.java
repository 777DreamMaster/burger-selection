import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Selection {
    public static void main(String[] args) throws Exception {
        Scanner enter= new Scanner(System.in);
        String next,next1;
        while (true){
            System.out.println("Enter 1 to continue, 0 to end:");
            next=enter.next();
            switch (next){
                case "1":{
                    while (true){
                        System.out.println("What you want to do?");
                        System.out.println("1-Add new customer 2-Export test data to train 3-Predictions");
                        next1=enter.next();
                        switch (next1) {
                            case "1": {
                                newCustomer();
                            }
                            break;
                            case "2": {
                                export();
                                break;
                            }
                            case "3": {
                                predictions();
                                break;
                            }
                            default: {
                                System.out.println("Invalid input, please try again");
                                continue;
                            }
                        }
                        break;
                    }
                }
                break;
                case "0":{
                    System.exit(0);
                }
                default:{
                    System.out.println("Invalid input, please try again");
                }
            }
        }

    }

    public static void predictions() throws Exception{
        //load training dataset
        //select path to train arff
        DataSource source = new DataSource("dataset/Atr_train.arff");
        Instances trainDataset = source.getDataSet();
        //set class index to the last attribute
        trainDataset.setClassIndex(trainDataset.numAttributes()-1);
        //get number of classes
        int numClasses = trainDataset.numClasses();
        //print out class values in the training dataset
        for(int i = 0; i < numClasses; i++) {
            //get class string value using the class index
            String classValue = trainDataset.classAttribute().value(i);
            System.out.println("Class Value " + i + " is " + classValue);
        }
        //create and build classifier
        NaiveBayes nb = new NaiveBayes();
        nb.buildClassifier(trainDataset);

        //load new dataset
        //select path to test arff
        DataSource source1 = new DataSource("dataset/Atr_test.arff");
        Instances testDataset = source1.getDataSet();
        //set class index to the last attribute
        testDataset.setClassIndex(testDataset.numAttributes()-1);

        //loop through the new dataset and make predictions
        System.out.println("=============");
        System.out.println("Actual Class, NB Predicted");
        for(int i = 0; i < testDataset.numInstances(); i++){
            //get class double value for current instance
            double actualClass = testDataset.instance(i).classValue();
            //get class string value using the class index and int value of the class
            String actual = testDataset.classAttribute().value((int) actualClass);
            //get Instance object of current instance
            Instance newInst = testDataset.instance(i);
            //call ClassifyInstance, which returns a double value for the class
            double predNB = nb.classifyInstance(newInst);
            //use this value to get string value of the predicted class
            String predString = testDataset.classAttribute().value((int) predNB);
            System.out.println("Selected by customer - "+actual+" ||| Predicted by NN - "+predString);
        }
        System.out.println("1-SaveModel 2-PrintModel other-nothing");
        Scanner model= new Scanner(System.in);
        String num=model.next();
        if (num.equals("1")){
            weka.core.SerializationHelper.write("dataset/burger.model", nb);
        }
        if (num.equals("2")){
            System.out.println(nb);
        }
    }

    public static void export() throws IOException{
        //String fileName = "dataset/Atr_test.arff";
        //Path path = Paths.get(fileName);
        Scanner scanner = new Scanner(new File("dataset/Atr_test.arff"));
        StringBuilder sb = new StringBuilder();
        StringBuilder afterDeleting = new StringBuilder();
        sb.append("\n");
        boolean f=false;
        while (scanner.hasNext()){
            if (scanner.hasNext("@data")){f=true; afterDeleting.append(scanner.nextLine()).append("\n"); }
            if (f) {
                sb.append(scanner.next()).append("\n");
            }
            else {
                afterDeleting.append(scanner.nextLine()).append("\n");
            }
        }
        scanner.close();

        sb.delete(sb.length()-1,sb.length());
        afterDeleting.delete(afterDeleting.length()-1,afterDeleting.length());

        //System.out.println(sb);
        //System.out.println(afterDeleting);
        PrintWriter out = new PrintWriter(new FileWriter("dataset/Atr_test.arff"));
        out.print(afterDeleting);
        out.close();

        Files.write(Paths.get("dataset/Atr_train.arff"), sb.toString().getBytes(), StandardOpenOption.APPEND);
    }

    public static void newCustomer() throws IOException{
        System.out.println("Creating a new customer");
        Scanner sc= new Scanner(System.in);
        StringBuilder newCust=new StringBuilder();
        newCust.append("\n");
        printAtribute(sc, newCust,1,5,"1. Enter the nationality of customer:","1-A 2-B 3-C 4-D 5-E");
        printAtribute(sc, newCust,1,2,"2. Enter gender of customer:","1-Male 2-Female");
        printAtribute(sc, newCust,0,100,"3. Enter age of customer:","");
        printAtribute(sc, newCust,100,250,"4. Enter height of customer (in sm):","");
        printAtribute(sc, newCust,1,3,"5. Enter physique of customer:","1-Q 2-R 3-D");
        printAtribute(sc, newCust,1,3,"6. Enter mood of customer:","1-Smile 2-Sad 3-Neutral");
        printAtribute(sc, newCust,0,0,"7. Enter which burger the customer chose:","1-Chickenburger 2-Humburger 3-Cheeseburger");
        System.out.println(newCust);
        Files.write(Paths.get("dataset/Atr_test.arff"), newCust.toString().getBytes(), StandardOpenOption.APPEND);

    }

    public static void printAtribute(Scanner scanner, StringBuilder newCust,int from, int to,String msg1,String msg2) {
        System.out.println(msg1);
        if (msg2!="")
            System.out.println(msg2);
        while(true) {
            String a = scanner.next();
            if (isNumber(a)) {
                if (from==to) {
                    switch (a){
                        case "1" :newCust.append("'chickenburger'");
                            break;
                        case "2" :newCust.append("'humburger'");
                            break;
                        case "3" :newCust.append("'cheeseburger'");
                            break;
                        default:{System.out.println("Invalid input, please try again"); continue;}
                    }
                    break;
                }
                else
                if (isInRange(Integer.parseInt(a), from, to)) {
                    newCust.append(a).append(",");
                    break;
                }

                else System.out.println("Invalid input, please try again");
            }
            else System.out.println("Invalid input, please try again");
        }
    }

    public static boolean isInRange(int value, int from, int to) {
        return value >= from && value <= to;
    }
    private static boolean isNumber(String str) {
        return str.matches("-?\\d+");
    }
}
