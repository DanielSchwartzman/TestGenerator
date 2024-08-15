package interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import id208133280_id207496068.QuestionStock;
import id208133280_id207496068.Test;

public interface Testable {
	public void addQuestionsAndAnswers(QuestionStock s);
	public void updateAnswer(QuestionStock s);
	public void updateQuestion(QuestionStock s);
	public void deleteAnswer(QuestionStock s);
	public void saveToBinaryFile(QuestionStock s)throws IOException,FileNotFoundException;
	public QuestionStock readStock();
	public void GenerateManualTest(QuestionStock s, ArrayList<Test> allTests)throws FileNotFoundException;
	public void generateRandomTest(QuestionStock s, ArrayList<Test> allTests)throws FileNotFoundException;
	public void saveTest(Test t, ArrayList<Test> allTests);
	public void printAllTests(ArrayList<Test> allTests);
	public void cloneExistingTest(ArrayList<Test> allTests);
	public void saveTestInTxt(Test t)throws FileNotFoundException;
	public int checkExceptionWithNumber(int min, int max);
	public char checkExceptionWithCharacters(char a, char b);
	public int[] randomNumberArray(int num, int max);
	public boolean doesentExist(int[] randomNumbers, int num);
	public int findIfTooSmall(QuestionStock Stock, int ind);
}
