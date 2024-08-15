package id208133280_id207496068;

import DataBase.DbManager;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionStock implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<Question> allQuestions;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public QuestionStock() {
		allQuestions = new ArrayList<Question>();
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void updateQuestion(String text, int qNum) {
		allQuestions.get(qNum).qText = text;
		DbManager.init().updateQuestionInDB(allQuestions.get(qNum).getId(), text);
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void printAll() {
		System.out.println("Printing all current Questions and answers: ");
		System.out.println();
		for (int i = 0; i < allQuestions.size(); i++) {
			System.out.println("Question " + (i + 1) + ":");
			System.out.println("------------");
			System.out.println(allQuestions.get(i).toString());
		}
		System.out.println();
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void addQuestionAndAnswers(Question q) {
		if (q instanceof AmericanQuestion) {
			allQuestions.add((AmericanQuestion) q);
		} else if (q instanceof OpenQuestion) {
			allQuestions.add((OpenQuestion) q);
		}
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void deleteAnswer(int qNum, int aNum) {
		if (allQuestions.get(qNum) instanceof AmericanQuestion) {
			DbManager.init().DeleteAnswerFromDB(allQuestions.get(qNum).id,((AmericanQuestion) allQuestions.get(qNum)).answerId.get(aNum));
			((AmericanQuestion) allQuestions.get(qNum)).allAnswers.remove(aNum);
			((AmericanQuestion) allQuestions.get(qNum)).answerId.remove(aNum);
		} else if (allQuestions.get(qNum) instanceof OpenQuestion) {
			DbManager.init().DeleteAnswerFromDB(allQuestions.get(qNum).id,((OpenQuestion) allQuestions.get(qNum)).Aid);
			((OpenQuestion) allQuestions.get(qNum)).setAnswer("No answer (Please update answer)");
		}
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public void updateAnswer(String text, int questionNum, int answerNum) {
		if (allQuestions.get(questionNum) instanceof AmericanQuestion) {
			((AmericanQuestion) allQuestions.get(questionNum)).changeAnswer(text, answerNum);
			DbManager.init().updateAnswerInDB(((AmericanQuestion) allQuestions.get(questionNum)).getAnswerId(answerNum),text);
		} else {
			((OpenQuestion) allQuestions.get(questionNum)).setAnswer(text);
			DbManager.init().updateAnswerInDB(((OpenQuestion) allQuestions.get(questionNum)).getAid(),text);
		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void deleteAllQuestions() {
		for (int i = (allQuestions.size()-1); i>=0; i--) {
			allQuestions.remove(i);
		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//hard coded Questions
	public void addHardCodedQuestions() {
// Question 1
		AmericanQuestion Aq1 = new AmericanQuestion("Between which years did WWII last?");
		Aq1.addAnswers("1912-1913");
		Aq1.addAnswers("1939-1945");
		Aq1.addAnswers("1956-1999");
		Aq1.addAnswers("1234-1256");
		Aq1.addAnswers("1876-1900");
		Aq1.setRightAnswer(2);
		allQuestions.add(Aq1);
		DbManager.init().saveQuestionToDB(Aq1);

// Question 2
		AmericanQuestion Aq2 = new AmericanQuestion("How many chromosomes does a human have?");
		Aq2.addAnswers("46");
		Aq2.addAnswers("12");
		Aq2.addAnswers("69");
		Aq2.addAnswers("420");
		Aq2.addAnswers("9999999");
		Aq2.setRightAnswer(1);
		allQuestions.add(Aq2);
		DbManager.init().saveQuestionToDB(Aq2);

// Question 3
		AmericanQuestion Aq3 = new AmericanQuestion(
				"What type of material kills werewolves(according to the mythology)?");
		Aq3.addAnswers("Iron");
		Aq3.addAnswers("Star of david shaped boomerang blessed by a rabbi");
		Aq3.addAnswers("Blessed wood");
		Aq3.addAnswers("Holy water");
		Aq3.addAnswers("Silver");
		Aq3.setRightAnswer(5);
		allQuestions.add(Aq3);
		DbManager.init().saveQuestionToDB(Aq3);

// Question 4
		OpenQuestion Oq1 = new OpenQuestion("What is the speed of light", "299,792,458 m/s");
		allQuestions.add(Oq1);
		DbManager.init().saveQuestionToDB(Oq1);

// Question 5
		OpenQuestion Oq2 = new OpenQuestion("What is the Speed of a standart low orbit satelite?", "about 7,000 mph");
		allQuestions.add(Oq2);
		DbManager.init().saveQuestionToDB(Oq2);
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
