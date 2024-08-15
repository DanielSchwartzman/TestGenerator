package id208133280_id207496068;

import DataBase.DbManager;

import java.util.ArrayList;

public class Teacher
{
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    protected int Tid;
    protected String FirstName;
    protected String LastName;
    protected ArrayList<Test> allWrittenTests;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Teacher(String firstName, String lastName)
    {
        Tid = DbManager.tid;
        DbManager.incrTid();
        FirstName = firstName;
        LastName = lastName;
        allWrittenTests = new ArrayList<>();
    }

    public ArrayList<Test> getAllWrittenTests()
    {
        return allWrittenTests;
    }

    public int getTid()
    {
        return Tid;
    }

    public void setTid(int tid)
    {
        Tid = tid;
    }

    public String getFirstName()
    {
        return FirstName;
    }

    public void setFirstName(String firstName)
    {
        FirstName = firstName;
    }

    public String getLastName()
    {
        return LastName;
    }

    public void setLastName(String lastName)
    {
        LastName = lastName;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("\nTid = "+Tid+" First name: "+FirstName+" Last name: "+LastName+"\n");
        sb.append("Tests written:\n");
        for(int i=0; i < allWrittenTests.size(); i++)
        {
            sb.append("TestID: "+ allWrittenTests.get(i).Tid+"\n");
        }
        return sb.toString();
    }

    public void assignTeacherToTest(Test test)
    {
        allWrittenTests.add(test);
        test.assignTeacherToThisTest(this);
    }

    public void removeFromTest(int tid)
    {
        DbManager.init().removeTeacherFromTest(tid,Tid);
        for(int i=0; i < allWrittenTests.size(); i++)
        {
            if(allWrittenTests.get(i).getTid()==tid)
            {
                allWrittenTests.remove(i);
                break;
            }
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
