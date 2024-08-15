package id208133280_id207496068;

import DataBase.DbManager;

public abstract class Question {
	protected int id;
	protected String qText;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Question(String qText) {
		this.qText = qText;
		this.id = DbManager.qid;
		DbManager.incrQid();
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Question() {
		this.qText = "";
		this.id = -1;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Qid = "+id+"\n"+"Question text: " + qText);
		return sb.toString();
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public int getId() {
		return id;
	}

	public void setID(int id)
	{
		this.id = id;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String getqText() {
		return qText;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setqText(String qText) {
		this.qText = qText;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean Equals(Question other) {
		if (other.id == this.id) {
			return true;
		} else if (other.qText.equals(this.qText)) {
			return true;
		} else {
			return false;
		}
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
}
