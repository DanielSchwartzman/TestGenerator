package DataBase;

import id208133280_id207496068.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DbManager
{
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String DB_NAME = "DB_Name";
    private String DB_USER = "User_Name";
    private String DB_PASSWORD = "Password";
    private Connection conn = null;
    private static DbManager instance = null;
    public static int Aid = 1;
    public static int testId = 1;
    public static int tid = 1;
    public static int sid = 1;
    public static int qid = 1;
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private DbManager()
    {
        DB_NAME = "DB_NAME";
        DB_USER = "USERNAME";
        DB_PASSWORD = "PASSWORD";
        DbManager.instance = this;
    }

    public static DbManager init()
    {
        if(instance == null){
            DbManager.instance= new DbManager();
            return DbManager.instance;
        }
        else{
            return DbManager.instance;
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void connect(){
        try{
            Class.forName("org.postgresql.Driver");
            String dbUrl = "jdbc:postgresql://localhost:5432/" + DB_NAME;
            conn = DriverManager.getConnection(dbUrl, DB_USER, DB_PASSWORD);

        }catch (SQLException ex){
            while (ex != null) {
                System.out.println("SQL exception: "+ ex.getMessage());
                ex = ex.getNextException();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean saveQuestionToDB(Question q){
        connect();
        if(q instanceof OpenQuestion)
        {
            try{
                Statement stmt = conn.createStatement();
                String Query = "INSERT INTO questions(Qid,QuestionText) VALUES("+q.getId()+",'"+q.getqText()+"')";
                ResultSet rs = stmt.executeQuery(Query);
            }
            catch (SQLException ex)
            {
                while (ex != null) {
                    System.out.println("SQL exception: "+ ex.getMessage());
                    ex = ex.getNextException();
                }
            }
            try{
                Statement stmt = conn.createStatement();
                String Query = "INSERT INTO Answers(Aid,AnswerText) VALUES("+((OpenQuestion) q).getAid()+",'"+((OpenQuestion) q).getRightAnswer()+"');";
                String QueryTwo = "INSERT INTO QuestionAnswerRel(Qid,Aid) VALUES("+q.getId()+",'"+((OpenQuestion) q).getAid()+"');";
                ResultSet rs = stmt.executeQuery(Query+QueryTwo);
            }
            catch (SQLException ex)
            {
                while (ex != null) {
                    System.out.println("SQL exception: "+ ex.getMessage());
                    ex = ex.getNextException();
                }
            }
        }
        else if(q instanceof AmericanQuestion)
        {
            try{
                Statement stmt = conn.createStatement();
                String Query = "INSERT INTO questions(Qid,QuestionText,RightAnswerIndex) VALUES("+q.getId()+",'"+q.getqText()+"',"+((AmericanQuestion) q).getRightAnswerIndex()+");";
                ResultSet rs = stmt.executeQuery(Query);
            }
            catch (SQLException ex)
            {
                while (ex != null) {
                    System.out.println("SQL exception: "+ ex.getMessage());
                    ex = ex.getNextException();
                }
            }
            StringBuffer sb = new StringBuffer();
            StringBuffer relSb = new StringBuffer();
            for(int i = 0; i< ((AmericanQuestion) q).getArraySize(); i++){
                sb.append("INSERT INTO Answers(Aid,AnswerText) VALUES("+((AmericanQuestion) q).getAnswerId(i)+",'"+((AmericanQuestion) q).getAnswer(i)+"');");
                sb.append("INSERT INTO QuestionAnswerRel(Qid,Aid) VALUES("+q.getId()+",'"+((AmericanQuestion) q).getAnswerId(i)+"');");
            }
            sb.append(relSb);
            try{
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sb.toString());
            }
            catch (SQLException ex)
            {
                while (ex != null) {
                    System.out.println("SQL exception: "+ ex.getMessage());
                    ex = ex.getNextException();
                }
            }
        }
        closeConn();
        return true;
    }

    private void closeConn()
    {
        if (conn != null) {
            try{
                conn.close();
            }catch (SQLException ex){}
            conn = null;
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void incrQid()
    {
        qid++;
    }
    public static void incrId()
    {
        Aid++;
    }
    public static void incrTestId()
    {
        testId++;
    }
    public static void incrTid()
    {
        tid++;
    }
    public static void incrSid()
    {
        sid++;
    }
    public static void setQid(int newQid)
    {
        qid = newQid;
    }
    public static void setAid(int newAid)
    {
        Aid = newAid;
    }
    public static void setTestId(int newTestId)
    {
        testId = newTestId;
    }
    public static void setTid(int newTid)
    {
        tid = newTid;
    }
    public static void setSid(int newSid)
    {
        sid = newSid;
    }
    private static void resetId()
    {
        qid = 1;
        Aid = 1;
        testId = 1;
        tid = 1;
        sid = 1;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void getQuestionStockFromDB(QuestionStock Sq)
    {
        ArrayList<Integer> allQuestionId = new ArrayList<>();
        ArrayList<String> allQuestionTexts = new ArrayList<>();
        ArrayList<Integer> RightAnswerIndex = new ArrayList<>();

        try{
            Statement stmt = conn.createStatement();
            String Query = "SELECT * FROM Questions";
            ResultSet rs = stmt.executeQuery(Query);
            while (rs.next()) {
                allQuestionId.add(rs.getInt("Qid"));
                allQuestionTexts.add(rs.getString("QuestionText"));
                RightAnswerIndex.add(rs.getInt("RightAnswerIndex"));
            }
        }
        catch (SQLException ex)
        {
            while (ex != null) {
                System.out.println("SQL exception: "+ ex.getMessage());
                ex = ex.getNextException();
            }
        }

        for(int i = 0; i < allQuestionId.size(); i++)
        {
            try{
                Statement stmt = conn.createStatement();
                String Query = "SELECT * \n" +
                        "FROM Questions\n" +
                        "JOIN QuestionAnswerRel ON Questions.Qid = QuestionAnswerRel.Qid\n" +
                        "JOIN Answers ON QuestionAnswerRel.Aid = Answers.Aid\n" +
                        "WHERE Questions.Qid ="+allQuestionId.get(i)+";";
                ResultSet rs = stmt.executeQuery(Query);
                ArrayList<String> AllAnswers = new ArrayList<>();
                ArrayList<Integer> AllAnswerID = new ArrayList<>();
                int QuestionID = 1;
                while (rs.next()) {
                    AllAnswers.add(rs.getString("AnswerText"));
                    AllAnswerID.add(rs.getInt("Aid"));
                    QuestionID = rs.getInt("qid");
                }
                if(AllAnswers.size()>1)
                {
                    AmericanQuestion Aq = new AmericanQuestion(allQuestionTexts.get(i));
                    for (int j = 0; j < AllAnswers.size(); j++)
                    {
                        Aq.addAnswers(AllAnswers.get(j));
                    }
                    Aq.setID(QuestionID);
                    Aq.setAnswerID(AllAnswerID);
                    int max = 0;
                    for(int j = 0; j < AllAnswerID.size(); j++)
                        if(AllAnswerID.get(j) > max)
                            max = AllAnswerID.get(j);
                    setAid(max+1);
                    Aq.setRightAnswer(RightAnswerIndex.get(i));
                    Sq.addQuestionAndAnswers(Aq);
                }
                else
                {
                    OpenQuestion Oq = new OpenQuestion(allQuestionTexts.get(i),AllAnswers.getFirst());
                    Oq.setID(QuestionID);
                    Oq.setAid(AllAnswerID.getFirst());
                    setAid(AllAnswerID.getFirst()+1);
                    Sq.addQuestionAndAnswers(Oq);
                }
            }
            catch (SQLException ex)
            {
                while (ex != null) {
                    System.out.println("SQL exception: "+ ex.getMessage());
                    ex = ex.getNextException();
                }
            }
        }
    }

    public void updateQuestionInDB(int Qid, String text)
    {
        String query = "UPDATE questions SET questiontext='"+text+"' WHERE qid="+Qid+";";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        }
        catch (SQLException ex)
        {
            while (ex != null) {
                System.out.println("SQL exception: "+ ex.getMessage());
                ex = ex.getNextException();
            }
        }
    }

    public void updateAnswerInDB(int Aid, String text)
    {
        String query = "UPDATE answers SET answertext='"+text+"' WHERE Aid="+Aid+";";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        }
        catch (SQLException ex)
        {
            while (ex != null) {
                System.out.println("SQL exception: "+ ex.getMessage());
                ex = ex.getNextException();
            }
        }
    }

    public void saveTestToDB(Test test)
    {
        String date = test.getDate().getYear()+"-"+test.getDate().getMonth()+"-"+test.getDate().getDay();
        connect();
        String query = "INSERT INTO tests(testid,Date,course,duration) VALUES("+test.getTid()+",'"+date+"','"+test.getCourse()+"',"+test.getDurationInMinutes()+");";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        }
        catch (SQLException ex)
        {
            while (ex != null) {
                System.out.println("SQL exception: "+ ex.getMessage());
                ex = ex.getNextException();
            }
        }

        StringBuffer sb = new StringBuffer();
        int amountOfQ = test.getAllTestQuestions().size();
        for (int i=0; i < amountOfQ; i++)
        {
            sb.append("INSERT INTO testquestionrel(testid,qid) VALUES("+test.getTid()+","+test.getAllTestQuestions().get(i).getId()+");");
        }
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sb.toString());
        }
        catch (SQLException ex)
        {
            while (ex != null) {
                System.out.println("SQL exception: "+ ex.getMessage());
                ex = ex.getNextException();
            }
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void deleteAllQuestionsAndTests()
    {
        resetId();
        StringBuffer sb = new StringBuffer();
        sb.append("DELETE FROM tests WHERE 1=1;");
        sb.append("DELETE FROM testquestionrel WHERE 1=1;");
        sb.append("DELETE FROM questions WHERE 1=1;");
        sb.append("DELETE FROM questionanswerrel WHERE 1=1;");
        sb.append("DELETE FROM answers WHERE 1=1;");
        sb.append("DELETE FROM students WHERE 1=1;");
        sb.append("DELETE FROM teststudentrel WHERE 1=1;");
        sb.append("DELETE FROM teachers WHERE 1=1;");
        sb.append("DELETE FROM testteacherrel WHERE 1=1;");
        try{
            connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sb.toString());
        }
        catch (SQLException ex)
        {
            while (ex != null) {
                System.out.println("SQL exception: "+ ex.getMessage());
                ex = ex.getNextException();
            }
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void saveTeacherToDB(Teacher teacher)
    {
        String query = "INSERT INTO teachers(tid,firstname,lastname) VALUES("+teacher.getTid()+",'"+teacher.getFirstName()+"','"+teacher.getLastName()+"');";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        }
        catch (SQLException ex)
        {
            while (ex != null) {
                System.out.println("SQL exception: "+ ex.getMessage());
                ex = ex.getNextException();
            }
        }
    }

    public void saveStudentToDB(Student student)
    {
        String query = "INSERT INTO students(sid,firstname,lastname) VALUES("+student.getSid()+",'"+student.getFirstName()+"','"+student.getLastName()+"');";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        }
        catch (SQLException ex)
        {
            while (ex != null) {
                System.out.println("SQL exception: "+ ex.getMessage());
                ex = ex.getNextException();
            }
        }
    }

    public void saveTeacherTestToDB(int Tid, int TestId)
    {
        String query = "INSERT INTO TestTeacherRel(testid,tid) VALUES("+TestId+","+Tid+");";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        }
        catch (SQLException ex)
        {
            while (ex != null) {
                System.out.println("SQL exception: "+ ex.getMessage());
                ex = ex.getNextException();
            }
        }
    }

    public void saveTestStudentToDB(int TestId, int Sid)
    {
        String query = "INSERT INTO TestStudentRel(tid,sid) VALUES("+TestId+","+Sid+");";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        }
        catch (SQLException ex)
        {
            while (ex != null) {
                System.out.println("SQL exception: "+ ex.getMessage());
                ex = ex.getNextException();
            }
        }
    }

    public void readTestsFromDB(ArrayList<Test> allTests)
    {
        String query = "SELECT * FROM tests;";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next())
            {
                String date = rs.getDate("date").toString();
                String[] tokens = date.split("-");
                int day = Integer.parseInt(tokens[2]);
                int month = Integer.parseInt(tokens[1]);
                int year = Integer.parseInt(tokens[0]);
                Test test = new Test(new MyDate(day,month,year),rs.getString("course"),rs.getInt("duration"));
                allTests.add(test);
            }
        }
        catch (SQLException ex)
        {
            while (ex != null) {
                System.out.println("SQL exception: "+ ex.getMessage());
                ex = ex.getNextException();
            }
        }
    }

    public void readTeachersFromDB(ArrayList<Teacher> allTeachers)
    {
        connect();
        String query = "SELECT * FROM teachers;";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next())
            {
                Teacher teacher = new Teacher(rs.getString("firstname"),rs.getString("lastname"));
                allTeachers.add(teacher);
            }
        }
        catch (SQLException ex)
        {
            while (ex != null) {
                System.out.println("SQL exception: "+ ex.getMessage());
                ex = ex.getNextException();
            }
        }
    }

    public void readStudentsFromDB(ArrayList<Student> allStudents)
    {
        connect();
        String query = "SELECT * FROM students;";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next())
            {
                Student student = new Student(rs.getString("firstname"),rs.getString("lastname"));
                allStudents.add(student);
            }
        }
        catch (SQLException ex)
        {
            while (ex != null) {
                System.out.println("SQL exception: "+ ex.getMessage());
                ex = ex.getNextException();
            }
        }
    }

    public void readTestQuestionRel(ArrayList<Test> allTests, QuestionStock stock)
    {
        connect();
        HashMap<Integer,Question> questionHash = new HashMap<>();
        for(int i=0; i < stock.allQuestions.size(); i++)
        {
            questionHash.put(stock.allQuestions.get(i).getId(),stock.allQuestions.get(i));
        }
        HashMap<Integer,Test> testHash = new HashMap<>();
        for(int i=0; i < allTests.size(); i++)
        {
            testHash.put(allTests.get(i).getTid(),allTests.get(i));
        }
        String query = "SELECT * FROM testquestionrel;";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next())
            {
                testHash.get(rs.getInt("testid")).addTestQuestion(questionHash.get(rs.getInt("qid")));
            }
        }
        catch (SQLException ex)
        {
            while (ex != null) {
                System.out.println("SQL exception: "+ ex.getMessage());
                ex = ex.getNextException();
            }
        }
    }

    public void readTestTeacherRel(ArrayList<Test> allTests, ArrayList<Teacher> allTeachers)
    {
        connect();
        HashMap<Integer,Test> testHash = new HashMap<>();
        for(int i=0; i < allTests.size(); i++)
        {
            testHash.put(allTests.get(i).getTid(),allTests.get(i));
        }
        HashMap<Integer,Teacher> teacherHash = new HashMap<>();
        for(int i=0; i < allTeachers.size(); i++)
        {
            teacherHash.put(allTeachers.get(i).getTid(),allTeachers.get(i));
        }

        String query = "SELECT * FROM testteacherrel;";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next())
            {
                teacherHash.get(rs.getInt("tid")).assignTeacherToTest(testHash.get(rs.getInt("testid")));
            }
        }
        catch (SQLException ex)
        {
            while (ex != null) {
                System.out.println("SQL exception: "+ ex.getMessage());
                ex = ex.getNextException();
            }
        }
    }

    public void readTestStudentRel(ArrayList<Test> allTests, ArrayList<Student> allStudents)
    {
        connect();
        HashMap<Integer,Test> testHash = new HashMap<>();
        for(int i=0; i < allTests.size(); i++)
        {
            testHash.put(allTests.get(i).getTid(),allTests.get(i));
        }
        HashMap<Integer,Student> studentHash = new HashMap<>();
        for(int i=0; i < allStudents.size(); i++)
        {
            studentHash.put(allStudents.get(i).getSid(),allStudents.get(i));
        }

        String query = "SELECT * FROM teststudentrel;";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next())
            {
                testHash.get(rs.getInt("tid")).assignStudentToThisTest(studentHash.get(rs.getInt("sid")));
            }
        }
        catch (SQLException ex)
        {
            while (ex != null) {
                System.out.println("SQL exception: "+ ex.getMessage());
                ex = ex.getNextException();
            }
        }
    }

    public void removeTeacherFromTest(int testd, int tid)
    {
        connect();
        String query = "DELETE FROM testteacherrel WHERE testid="+testd+" AND tid="+tid+";";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        }
        catch (SQLException ex)
        {
            while (ex != null) {
                System.out.println("SQL exception: "+ ex.getMessage());
                ex = ex.getNextException();
            }
        }
    }

    public void DeleteAnswerFromDB(int Qid, int Aid)
    {
        connect();
        String query = "DELETE FROM answers WHERE aid="+Aid+";";
        query += "DELETE FROM questionanswerrel WHERE aid="+Aid+" AND qid="+Qid+";";
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        }
        catch (SQLException ex)
        {
            while (ex != null) {
                System.out.println("SQL exception: "+ ex.getMessage());
                ex = ex.getNextException();
            }
        }
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
