import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Selection {
    public static void main(String[] args) throws Exception {
        //load training dataset
        //select path to train arff
        DataSource source = new DataSource("/Users/John/IdeaProjects/burger-selection/dataset/Atr_train.arff");
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
        DataSource source1 = new DataSource("/Users/John/IdeaProjects/burger-selection/dataset/Atr_test.arff");
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
    }
}

