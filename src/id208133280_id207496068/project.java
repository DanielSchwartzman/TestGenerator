
package id208133280_id207496068;
//Name:Daniel Shvartsman
//ID:208133280
//Name:Tomer Guzikov
//ID:207496068
//Name:Sean Pinchevsky
//ID:318647617

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import DataBase.DbManager;
import interfaces.Testable;

//in order to support more than one subject, we should create another class of "Subjects" and define for each "Subject" which question will belong to it. "אלמוש"
public class project  implements Testable{
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		DbManager db = DbManager.init();
		db.connect();
		project p=new project();
		boolean loop = true;
		QuestionStock stock = p.readStock();
		ArrayList<Test> allTests = p.readTests(stock);
		ArrayList<Teacher> allTeachers = p.readTeachers();
		ArrayList<Student> allStudents = p.readStudents();
		p.TestTeacherRel(allTests,allTeachers);
		p.TestStudentRel(allTests,allStudents);
		while (loop)
		{
			System.out.println("Please choose one of the following commands:");
			System.out.println("1-Display all questions and answers that are currently in the system");
			System.out.println("2-Add question and answers");
			System.out.println("3-Update question");
			System.out.println("4-Update answer");
			System.out.println("5-Delete answer");
			System.out.println("6-Manual Test insert");
			System.out.println("7-Automatic Test insert");
			System.out.println("8-Exit and save program");
			System.out.println("9-Display all Tests in system");
			System.out.println("10-Clone an existing test");
			System.out.println("11-Add 5 premade Questions");
			System.out.println("12-Delete all Questions and tests in stock");
			System.out.println("13-Add Teacher");
			System.out.println("14-Print all teachers");
			System.out.println("15-Add Student");
			System.out.println("16-Print all students");
			System.out.println("17-Assign teacher to test");
			System.out.println("18-Assign test to student");
			System.out.println();
			int chosen = p.checkExceptionWithNumber(1, 18);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
//Switch Start					
			switch (chosen)
			{
				case 1:
				{
					try {
						stock.printAll();
						break;
				}
					catch (Exception e)
					{
						System.out.println("General error detected, please try again");
						System.out.println();
						break;
					}
				}
				case 2:
				{
					try
					{
						p.addQuestionsAndAnswers(stock);
						break;
					}
					catch (Exception e)
					{
						System.out.println("General error detected, please try again");
						System.out.println();
						break;
					}

				}
				case 3:
				{
					try
					{
						p.updateQuestion(stock);
						break;
					}
					catch (Exception e)
					{
						System.out.println("General error detected, please try again");
						System.out.println();
						break;
					}
				}
				case 4:
				{
					try
					{
						p.updateAnswer(stock);
						break;
					}
					catch (Exception e)
					{
						System.out.println("General error detected, please try again");
						System.out.println();
					break;
					}
				}
				case 5:
				{
					try
					{
						p.deleteAnswer(stock);
						break;
					}
					catch (Exception e)
					{
						System.out.println("General error detected, please try again");
						System.out.println();
						break;
					}
				}
				case 6:
				{
					try
					{
						p.GenerateManualTest(stock, allTests);
						break;
					}
					catch (Exception e)
					{
						System.out.println("General error detected, please try again");
						System.out.println();
						break;
					}
				}
				case 7:
				{
					try
					{
						p.generateRandomTest(stock, allTests);
						break;
					}
					catch (Exception e)
					{
						System.out.println("General error detected, please try again");
						System.out.println();
						break;
					}
				}
				case 8:
				{
					System.out.println("Exiting Program");
					try
					{
						p.saveToBinaryFile(stock);
						System.out.println("File Saved Successfully");
					}
					catch (IOException e)
					{
						System.out.println("Failed to save file");
					}
					loop = false;
					break;
				}
				case 9:
				{
					try
					{
						p.printAllTests(allTests);
						break;
					}
					catch (Exception e)
					{
						System.out.println("General error detected, please try again");
						System.out.println();
						break;
					}
				}
				case 10:
				{
					try
					{
						p.cloneExistingTest(allTests);
						break;
					}
					catch (Exception e)
					{
						System.out.println("General error detected, please try again");
						System.out.println();
						break;
					}
				}
				case 11:
				{
					try
					{
						stock.addHardCodedQuestions();
						System.out.println("Questions added successfully");
						System.out.println();
						break;
					}
					catch (Exception e)
					{
						System.out.println("General error detected, please try again");
						System.out.println();
						break;
					}
				}
				case 12:
				{
					try
					{
						stock.deleteAllQuestions();
						for(int i = 0; i < allTests.size(); i++)
						{
							allTests.remove(i);
						}
						DbManager.init().deleteAllQuestionsAndTests();
						System.out.println("Questions and Tests were deleted successfully");
						System.out.println();
						break;
					}
					catch (Exception e)
					{
						System.out.println("General error detected, please try again");
						System.out.println();
						break;
					}
				}
				case 13:
				{
					p.addTeacher(allTeachers);
					break;
				}
				case 14:
				{
					p.printAllTeachers(allTeachers);
					break;
				}
				case 15:
				{
					p.addStudent(allStudents);
					break;
				}
				case 16:
				{
					p.printAllStudents(allStudents);
					break;
				}
				case 17:
				{
					p.assignTeacherToTest(allTests,allTeachers);
					break;
				}
				case 18:
				{
					p.assignStudentToTest(allTests,allStudents);
					break;
				}
			}
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//STOCK-Input/Output Block
	public void addQuestionsAndAnswers(QuestionStock s) {
		System.out.println("What type of question would you like to add?");
		System.out.println("1-American Question");
		System.out.println("2-Open Question");
		int num = checkExceptionWithNumber(1, 2);
		if (num == 1) {
			System.out.println("Please enter Question text:");
			String qText = sc.nextLine();
			AmericanQuestion Aq = new AmericanQuestion(qText);
			boolean innerLoop = true;
			while (innerLoop) {
				System.out.println("Would you like to add an answer?(y/n)");
				char answer = checkExceptionWithCharacters('y', 'n');
				sc.nextLine();
				if (answer == 'y') {
					System.out.println("Please enter answer text:");
					String answerText = sc.nextLine();
					if (Aq.addAnswers(answerText)) {
						System.out.println("Answer added successfully.");
						System.out.println();
					}
					else if(Aq.allAnswers.contains(answerText)) {
						System.out.println("Answer already exists");
						System.out.println();
					}
					else {
						System.out.println("Cannot add any more questions");
						System.out.println();
					}
				} else if (answer == 'n') {
					innerLoop = false;
					break;
				} else {
					System.out.println("Please answer the question.");
				}
			}
			System.out.println("Please enter the right answer index:");
			int index = checkExceptionWithNumber(1, Aq.allAnswers.size());
			Aq.setRightAnswer(index);
			System.out.println("Right answer added successfully.");
			System.out.println();
			s.addQuestionAndAnswers(Aq);
			DbManager.init().saveQuestionToDB(Aq);
		}

		else if (num == 2) {
			System.out.println("Please enter Question Text:");
			String text = sc.nextLine();
			System.out.println("Please eneter the right answer for the Question:");
			String rightText = sc.nextLine();
			OpenQuestion Oq = new OpenQuestion(text, rightText);
			System.out.println("Answer added successfully.");
			System.out.println();
			s.addQuestionAndAnswers(Oq);
			DbManager.init().saveQuestionToDB(Oq);
		}
	}

	public void updateAnswer(QuestionStock s) {
		s.printAll();
		System.out.println("Please enter which question's answer you would like to update:");
		int qNum = checkExceptionWithNumber(1, s.allQuestions.size());
		if (s.allQuestions.get(qNum - 1) instanceof AmericanQuestion) {
			System.out.println("Current question:");
			System.out.println(s.allQuestions.get(qNum - 1).toString());
			System.out.println("Please eneter which answer you would like to update");
			int aNum = checkExceptionWithNumber(1, ((AmericanQuestion) s.allQuestions.get(qNum - 1)).allAnswers.size());
			System.out.println("Please enter your new answer:");
			String text = sc.nextLine();
			s.updateAnswer(text, qNum - 1, aNum - 1);
			System.out.println("Answer Changed successfully");
			System.out.println();
		} else {
			System.out
					.println("Current answer:" + "\n" + ((OpenQuestion) s.allQuestions.get(qNum - 1)).getRightAnswer());
			System.out.println("Please enter your new answer:");
			String text = sc.nextLine();
			s.updateAnswer(text, qNum - 1, 0);
		}
	}

	public void updateQuestion(QuestionStock s) {
		s.printAll();
		System.out.println("Please enter which question you would like to update:");
		int num = checkExceptionWithNumber(1, s.allQuestions.size());
		System.out.println("Current question:" + "\n" + s.allQuestions.get(num - 1).getqText());
		System.out.println("Please enter your new text for the Question");
		String text = sc.nextLine();
		s.updateQuestion(text, num - 1);
		System.out.println("Question changed successfully");
		System.out.println();
	}

	public void deleteAnswer(QuestionStock s) {
		s.printAll();
		System.out.println("Which question would you like to delete the answer from?");
		int num = checkExceptionWithNumber(1, s.allQuestions.size());
		if (s.allQuestions.get(num - 1) instanceof AmericanQuestion) {
			System.out.println("Current Question:");
			System.out.println(((AmericanQuestion) s.allQuestions.get(num - 1)).toString());
			System.out.println("Please enter the index for the answer you would like to delete:");
			int index = checkExceptionWithNumber(1, ((AmericanQuestion) s.allQuestions.get(num - 1)).allAnswers.size());
			s.deleteAnswer(num - 1, index - 1);
			System.out.println("Answer deleted successfully.");
			System.out.println();
		} else {
			s.deleteAnswer(num - 1, 0);
			System.out.println("Answer deleted successfully");
			System.out.println();
		}
	}

	public void saveToBinaryFile(QuestionStock s) throws FileNotFoundException, IOException {
		FileOutputStream fops = new FileOutputStream("StockSave-[Do not touch]");
		ObjectOutputStream oos = new ObjectOutputStream(fops);
		oos.writeObject(s);
		oos.flush();
		oos.close();

	}

	public QuestionStock readStock() {
		QuestionStock Sq = new QuestionStock();
		DbManager.init().getQuestionStockFromDB(Sq);
		return Sq;
	}

	public ArrayList<Test> readTests(QuestionStock stock)
	{
		ArrayList<Test> tests = new ArrayList<>();
		DbManager.init().readTestsFromDB(tests);
		DbManager.init().readTestQuestionRel(tests,stock);
		return tests;
	}

	public ArrayList<Teacher> readTeachers()
	{
		ArrayList<Teacher> teachers = new ArrayList<>();
		DbManager.init().readTeachersFromDB(teachers);
		return teachers;
	}

	public ArrayList<Student> readStudents()
	{
		ArrayList<Student> students = new ArrayList<>();
		DbManager.init().readStudentsFromDB(students);
		return students;
	}

	public void TestTeacherRel(ArrayList<Test> allTests, ArrayList<Teacher> allTeachers)
	{
		DbManager.init().readTestTeacherRel(allTests,allTeachers);
	}

	public void TestStudentRel(ArrayList<Test> allTests, ArrayList<Student> allStudents)
	{
		DbManager.init().readTestStudentRel(allTests,allStudents);
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//TEST-Input/Output
	// Manual-Test:
	public void GenerateManualTest(QuestionStock s, ArrayList<Test> allTests) throws FileNotFoundException {
		System.out.println("Please enter the amount of questions you want your test to have:");
		int testSize = checkExceptionWithNumber(1, s.allQuestions.size());
		MyDate date = getDateFromUser();
		String course = getCourseNameFromUser();
		int duration = getTestDurationFromUser();
		Test manualTest = new Test(date,course,duration);
		s.printAll();
		for (int i = 0; i < testSize; i++) {
			System.out.println("Please enter which of the following questions you would like to add to your test:");
			int num = checkExceptionWithNumber(1, s.allQuestions.size());
			manualTest.allTestQuestions.add(s.allQuestions.get(num-1));
		}
		manualTest.sortTest();
		manualTest.printAll();
		DbManager.init().saveTestToDB(manualTest);
		allTests.add(manualTest);
	}

	// Random-Test
	public void generateRandomTest(QuestionStock s, ArrayList<Test> allTests) throws FileNotFoundException {
		System.out.println("Please enter the amount of questions you want you randomized test to have:");
		int num = checkExceptionWithNumber(1, s.allQuestions.size());
		int[] randomQuestionArray = randomNumberArray(num, s.allQuestions.size());
		MyDate date = getDateFromUser();
		String course = getCourseNameFromUser();
		int duration = getTestDurationFromUser();
		Test randomTest = new Test(date,course,duration);
		for (int i = 0; i < num; i++) {
			int randomQuestionIndex = randomQuestionArray[i];
			randomTest.addTestQuestion(s.allQuestions.get(randomQuestionIndex -1));
		}
		randomTest.printAll();
		DbManager.init().saveTestToDB(randomTest);
		allTests.add(randomTest);
	}

	public void saveTest(Test t, ArrayList<Test> allTests) {
		System.out.println("Would you like to save this test(Y/N)?");
		System.out.println("NOTE: your test will be saved only untill the program is reset");
		char answer = checkExceptionWithCharacters('y', 'n');
		if (Character.toUpperCase(answer) == 'Y') {
			allTests.add(t);
			System.out.println("Test saved successfully");
			System.out.println();
		}
	}

	public void printAllTests(ArrayList<Test> allTests) {
		for (int i = 0; i < allTests.size(); i++) {
			System.out.println("Test " + (i + 1) + ":");
			allTests.get(i).printAll();
		}
	}

	public void cloneExistingTest(ArrayList<Test> allTests) {
		printAllTests(allTests);
		System.out.println("Please enter the index for the test you would like to duplicate:");
		int index = checkExceptionWithNumber(1, allTests.size());
		try {
			Test clonedTest = (Test) allTests.get(index - 1).clone();
			clonedTest.incrId();
			allTests.add(clonedTest);
			DbManager.init().saveTestToDB(clonedTest);
		} catch (CloneNotSupportedException e) {
			System.out.println("Given test cannot be cloned");
			return;
		}
		System.out.println("Test cloned successfully");
		System.out.println();
		System.out.println();
	}

	public void saveTestInTxt(Test t) throws FileNotFoundException {
		String date = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		File testFile = new File("Exam_" + date);
		PrintWriter pw = new PrintWriter(testFile);
		t.saveToTxt(pw);
		pw.close();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Get Data from user

	public MyDate getDateFromUser()
	{
		System.out.println("Please enter Date:");
		System.out.println("Day:");
		int day = checkExceptionWithNumber(1, 28);
		System.out.println("Month:");
		int month = checkExceptionWithNumber(1, 12);
		System.out.println("year:");
		int year = checkExceptionWithNumber(2024, 2030);
		return new MyDate(day,month,year);
	}

	public String getCourseNameFromUser()
	{
		System.out.println("Please enter course name:");
		return sc.nextLine();
	}

	public int getTestDurationFromUser()
	{
		System.out.println("Please enter test duration(in minutes >60):");
        return checkExceptionWithNumber(60, 240);
	}


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Teacher and Student block

	public void addTeacher(ArrayList<Teacher> allTeachers)
	{
		System.out.println("Please enter teacher first name:");
		String fname = sc.nextLine();
		System.out.println("Please enter teacher last name:");
		String lname = sc.nextLine();
		Teacher teacher = new Teacher(fname,lname);
		allTeachers.add(teacher);
		DbManager.init().saveTeacherToDB(teacher);
	}

	public void printAllTeachers(ArrayList<Teacher> allTeachers)
	{
		System.out.println("All Teachers:");
		for(int i=0; i < allTeachers.size(); i++)
		{
			System.out.println(allTeachers.get(i).toString());
		}
	}

	public void addStudent(ArrayList<Student> allStudents)
	{
		System.out.println("Please enter student first name:");
		String fname = sc.nextLine();
		System.out.println("Please enter student last name:");
		String lname = sc.nextLine();
		Student student = new Student(fname,lname);
		allStudents.add(student);
		DbManager.init().saveStudentToDB(student);
	}

	public void printAllStudents(ArrayList<Student> allStudents)
	{
		System.out.println("All Students:");
		for(int i=0; i < allStudents.size(); i++)
		{
			System.out.println(allStudents.get(i).toString());
		}
	}

	public void assignTeacherToTest(ArrayList<Test> allTests, ArrayList<Teacher> allTeachers)
	{
		printAllTeachers(allTeachers);
		System.out.println("\n\nSelect which teacher you want to assign:");
		int teacherIndex = checkExceptionWithNumber(1, allTeachers.size());
		printAllTests(allTests);
		System.out.println("\n\nSelect which test you want the teacher to be assigned to:");
		int testIndex = checkExceptionWithNumber(1, allTests.size());
		allTeachers.get(teacherIndex - 1).assignTeacherToTest(allTests.get(testIndex - 1));
		DbManager.init().saveTeacherTestToDB(allTeachers.get(teacherIndex-1).getTid(),allTests.get(testIndex-1).getTid());
	}

	public void assignStudentToTest(ArrayList<Test> allTests, ArrayList<Student> allStudents)
	{
		printAllTests(allTests);
		System.out.println("\n\nSelect which test you want the Student to be assigned to:");
		int testIndex = checkExceptionWithNumber(1, allTests.size());
		printAllStudents(allStudents);
		System.out.println("\n\nSelect which student you want to assign to this test:");
		int studentIndex = checkExceptionWithNumber(1, allStudents.size());
		allTests.get(testIndex - 1).assignStudentToThisTest(allStudents.get(studentIndex - 1));
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
//Exception Block
	public int checkExceptionWithNumber(int min, int max) {
		boolean loop = true;
		int num = 0;
		while (loop) {
			try {
				num = sc.nextInt();
				sc.nextLine();
				if (num > max || num < min) {
					System.out.println("Number should be between " + min + " and " + max + " Please try again:");
				} else {
					loop = false;
				}
			} catch (InputMismatchException e) {
				System.out.println("Answer should be a number,Please try again:");
				System.out.println();
				sc.nextLine();
			}
		}
		return num;
	}

	public char checkExceptionWithCharacters(char a, char b) {
		char answer = 'a';
		boolean loop = true;
		while (loop) {
			try {
				answer = sc.next().charAt(0);
				if (Character.toUpperCase(answer) != Character.toUpperCase(a)
						&& Character.toUpperCase(answer) != Character.toUpperCase(b)) {
					System.out.println("Answers should be " + a + " or " + b + ", Please try again");
				} else {
					loop = false;
				}
			} catch (InputMismatchException e) {
				System.out.println("Answer should be a character, Please try again:");
			}
		}
		return answer;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Other-Block
	public int[] randomNumberArray(int num, int max) {
		int[] randomNumbers = new int[num];
		for (int i = 0; i < num;) {
			int randomNum = (int) (Math.random() * (max + 1));
			if (doesentExist(randomNumbers, randomNum)) {
				randomNumbers[i] = randomNum;
				i++;
			}
		}
		return randomNumbers;
	}

	public boolean doesentExist(int[] randomNumbers, int num) {
		for (int i = 0; i < randomNumbers.length; i++) {
			if (randomNumbers[i] == num) {
				return false;
			}
		}
		return true;
	}

	public int findIfTooSmall(QuestionStock Stock, int ind) {
		if (((AmericanQuestion) Stock.allQuestions.get(ind)).allAnswers.size() < 4) {
			return ((AmericanQuestion) Stock.allQuestions.get(ind)).allAnswers.size();
		} else {
			return 4;
		}
	}
}
