package id208133280_id207496068;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import DataBase.DbManager;
import comperators.QuestionComparator;

public class Test implements Cloneable {
	protected ArrayList<Question> allTestQuestions;
	protected int Tid;
	protected Teacher teacher;
	protected ArrayList<Student> ParticipatingStudents;
	MyDate date;
	String course;
	int durationInMinutes;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Test(MyDate date, String course, int durationInMinutes) {
		allTestQuestions = new ArrayList<Question>();
		Tid = DbManager.testId;
		DbManager.incrTestId();
		ParticipatingStudents = new ArrayList<>();
		this.date = date;
		this.course = course;
		this.durationInMinutes = durationInMinutes;
	}

	public Test(Test other){
		allTestQuestions = other.allTestQuestions;
		Tid = DbManager.testId;
		DbManager.incrTestId();
	}

	public ArrayList<Question> getAllTestQuestions()
	{
		return allTestQuestions;
	}

	public int getTid()
	{
		return Tid;
	}

	public MyDate getDate() {
		return date;
	}

	public void setDate(MyDate date) {
		this.date = date;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public int getDurationInMinutes() {
		return durationInMinutes;
	}

	public void setDurationInMinutes(int durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void printAll() {
		System.out.println("Tid="+Tid+" Printing Current Test:\nTest date: "+date.day+"/"+date.month+"/"+date.year+"\nCourse: "+course+"\nTest duration: "+durationInMinutes);
		System.out.println();
		for (int i = 0; (i < allTestQuestions.size()) && (allTestQuestions.get(i) != null); i++) {
			System.out.println("Question " + (i + 1) + ":");
			System.out.println("------------");
			System.out.println(allTestQuestions.get(i).toString());

		}
		System.out.println();
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void addTestQuestion(Question q) {
		if (q instanceof AmericanQuestion) {
			allTestQuestions.add(q);
		} else {
			allTestQuestions.add(q);
		}
	}

	public void incrId(){
		Tid +=1;
	}

	public void setTid(int Tid)
	{
		this.Tid = Tid;
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void sortTest() {
		Collections.sort(allTestQuestions,new QuestionComparator());
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
	public void saveToTxt(PrintWriter pw) {
		pw.println("Saved test: ");
		pw.println();
		for (int i = 0; (i < allTestQuestions.size()) && (allTestQuestions.get(i) != null); i++) {
			pw.println("Question " + (i + 1) + ":");
			pw.println("------------");
			pw.println(allTestQuestions.get(i).toString());

		}
		pw.println();
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void assignTeacherToThisTest(Teacher teacher)
	{
		if(this.teacher != null)
			this.teacher.removeFromTest(this.getTid());
		this.teacher = teacher;
	}

	public void assignStudentToThisTest(Student student) {
		if (student.assignToTest(this))
		{
			ParticipatingStudents.add(student);
			DbManager.init().saveTestStudentToDB(Tid, student.getSid());
		}
		else
			System.out.println("Failed to assign student, student is already assigned to this test.\n\n");
	}
}
