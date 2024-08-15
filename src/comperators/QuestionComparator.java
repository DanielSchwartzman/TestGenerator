package comperators;

import java.util.Comparator;

import id208133280_id207496068.AmericanQuestion;
import id208133280_id207496068.OpenQuestion;
import id208133280_id207496068.Question;

public class QuestionComparator implements Comparator<Question> {
	@Override
	public int compare(Question q1, Question q2) {
		if(findNumOfLettersInAnswers(q1)>findNumOfLettersInAnswers(q2)) {
			return 1;
		}
		else if(findNumOfLettersInAnswers(q1)<findNumOfLettersInAnswers(q2)) {
			return -1;
		}
		else {
			return 0;
		}
	}
	
	public int findNumOfLettersInAnswers(Question q) {
		if(q instanceof AmericanQuestion) {
			int size=((AmericanQuestion)q).getArraySize();
			int numOfLettersInAnswers=0;
			for (int i = 0; i < size; i++) {
				numOfLettersInAnswers+=((AmericanQuestion)q).getAnswer(i).length();
			}
			return numOfLettersInAnswers;
		}
		else {
			return ((OpenQuestion)q).getRightAnswer().length();
		}
		
	}
}
