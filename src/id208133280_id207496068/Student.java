package id208133280_id207496068;

import DataBase.DbManager;

import java.util.ArrayList;

public class Student
{
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected int Sid;
    protected String firstName;
    protected String lastName;
    protected ArrayList<Test> allTakenTests;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Student(String lastName, String firstName) {
        Sid = DbManager.sid;
        DbManager.incrSid();
        this.lastName = lastName;
        this.firstName = firstName;
        allTakenTests = new ArrayList<>();
    }

    public ArrayList<Test> getAllTakenTests()
    {
        return allTakenTests;
    }

    public int getSid() {
        return Sid;
    }

    public void setSid(int sid) {
        Sid = sid;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("\nSid = "+Sid+" First name: "+firstName+" Last name: "+lastName+"\n");
        sb.append("Tests to take:\n");
        for(int i=0; i < allTakenTests.size(); i++)
        {
            sb.append("Tid: "+ allTakenTests.get(i).Tid+"\n");
        }
        return sb.toString();
    }

    public boolean assignToTest(Test test)
    {
        for(int i =0; i < allTakenTests.size(); i++)
        {
            if(allTakenTests.get(i).Tid == test.Tid)
                return false;
        }
        allTakenTests.add(test);
        return true;
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
