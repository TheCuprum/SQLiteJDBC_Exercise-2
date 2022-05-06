package priv.db;

// STUBBED FILE
import java.sql.*;

import priv.db.querybuilder.JoinType;
import priv.db.querybuilder.SelectQueryBuilder;
import priv.db.querybuilder.logicnode.AndNode;
import priv.db.querybuilder.logicnode.BetweenNode;
import priv.db.querybuilder.logicnode.ComparisonNode;
import priv.db.querybuilder.logicnode.ComparisonOperator;

// this is the class through which all Database calls go
public class DbUser extends DbBasic {

    private ResultSet rs = null;
    static private final int STR_SIZE = 25;

    // this method takes a String, converts it into an array of bytes;
    // copies those bytes into a bigger byte array (STR_SIZE worth), and
    // pads any remaining bytes with spaces. Finally, it converts the bigger
    // byte array back into a String, which it then returns.
    // e.g. if the String was "s_name", the new string returned is
    // "s_name                  " (the six characters followed by 18 spaces).
    private String pad(String in) {
        byte[] org_bytes = in.getBytes();
        byte[] new_bytes = new byte[STR_SIZE];
        int upb = in.length();

        if (upb > STR_SIZE)
            upb = STR_SIZE;

        for (int i = 0; i < upb; i++)
            new_bytes[i] = org_bytes[i];

        for (int i = upb; i < STR_SIZE; i++)
            new_bytes[i] = ' ';

        return new String(new_bytes);
    }

    private boolean queryAndPrint(String sql){
        try {
            Statement statement = this.con.createStatement();
            ResultSet result = statement.executeQuery(sql);
            ResultSetMetaData meta = result.getMetaData();
            for (int index = 1; index <= meta.getColumnCount(); index++){
                System.out.print(pad(meta.getColumnName(index)));
                System.out.print("| ");
            }
            System.out.println();
            int rows = 0;
            while(result.next()){
                rows++;
                for (int index = 1; index <= meta.getColumnCount(); index++){
                    System.out.print(pad(result.getString(index)));
                    System.out.print("| ");
                }
                System.out.println();
            }
            System.out.println(rows + " row(s) in set");
            System.out.println();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean query(String sql){
        try {
            Statement statement = this.con.createStatement();
            
            System.out.println("NO RESULT");
            System.out.println();
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * Creates a connection to the named database
     */
    public DbUser(String dbName) {
        super(dbName);
    }

    /*
     * You need to complete the following methods
     */
    public void doQuery_1_a() {
        // 1 a) what is the title of the course with code 361?';
        // Write the Java code that executes the "select" statement that answers query
        // 1a).
        // See the example output to see what should be produced.
        System.out.println("1 a) what is the title of the course with code 361?");

        SelectQueryBuilder sQueryBuilder = new SelectQueryBuilder();
        sQueryBuilder.select("*").from("courses")
            .where(new ComparisonNode("code", ComparisonOperator.EQUAL, "361"));
        // System.out.println(sQueryBuilder.buildQuery());
        queryAndPrint(sQueryBuilder.buildQuery());
    }

    public void doQuery_1_b() {
        // 1 b) what position and qualifications does Davies have?';
        // Write the Java code that executes the "select" statement that answers query
        // 1b).
        // See the example output to see what should be produced.
        System.out.println("1 b) what position and qualifications does Davies have?");
        
        SelectQueryBuilder sQueryBuilder = new SelectQueryBuilder();
        String[] keys = {"s_name", "pos", "qual"};
        sQueryBuilder.select(keys).from("staff")
            .where(new ComparisonNode("s_name", ComparisonOperator.EQUAL, "\"Davies\""));
        // System.out.println(sQueryBuilder.buildQuery());
        queryAndPrint(sQueryBuilder.buildQuery());
    }

    public void doQuery_2_a() {
        System.out.println("2 a) what is the name of the department that Bear works for?");

        SelectQueryBuilder sQueryBuilder = new SelectQueryBuilder();
        sQueryBuilder.select("*").from("department")
            .join(JoinType.NATURAL, "staff", null, null)
            .where(new ComparisonNode("s_name", ComparisonOperator.EQUAL, "\"Bear\""));
        // System.out.println(sQueryBuilder.buildQuery());
        queryAndPrint(sQueryBuilder.buildQuery());

    }

    public void doQuery_2_b() {
        System.out.println("2 b) what are the course codes of courses offered by the Computing department? ");
        
        SelectQueryBuilder sQueryBuilder = new SelectQueryBuilder();
        String[] keys = {"code", "d_id"};
        sQueryBuilder.select(keys).from("courses")
            .join(JoinType.NATURAL, "department", null, null)
            .where(new ComparisonNode("d_title", ComparisonOperator.EQUAL, "\"Computing\""));
        // System.out.println(sQueryBuilder.buildQuery());
        queryAndPrint(sQueryBuilder.buildQuery());

    }

    public void doQuery_3_a() {
        System.out.println("3 a) What are the titles of course given by Mariani?");

        SelectQueryBuilder sQueryBuilder = new SelectQueryBuilder();
        String[] keys = {"give_course.c_id", "courses.c_title", "staff.s_name"};
        sQueryBuilder.select(keys).from("courses")
            .join(JoinType.INNER, "give_course", "courses.c_id", "give_course.c_id")
            .join(JoinType.NATURAL, "staff", null, null)
            .where(new ComparisonNode("staff.s_name", ComparisonOperator.EQUAL, "\"Mariani\""));
        // System.out.println(sQueryBuilder.buildQuery());
        queryAndPrint(sQueryBuilder.buildQuery());
    }

    public void doQuery_3_b() {
        System.out.println("3 b) What are the names and initials of the staff who work on the COMIC project?");

        SelectQueryBuilder sQueryBuilder = new SelectQueryBuilder();
        sQueryBuilder.select("staff.s_name").select("staff.initials").from("staff")
            .join(JoinType.NATURAL, "work_on", null, null)
            .join(JoinType.NATURAL, "projects", null, null)
            .where(new ComparisonNode("staff.s_name", ComparisonOperator.EQUAL, "\"Mariani\""));
        // System.out.println(sQueryBuilder.buildQuery());
        queryAndPrint(sQueryBuilder.buildQuery());
    }

    public void doQuery_4_a() {
        System.out.println(
                "4 a) add a row to the Department relation regarding the Sociology department, with a location at Cartmel college and a code of SOCIO");

        query("INSERT OR IGNORE INTO department VALUES (\"SOCIO\", \"Sociology\", \"Cartmel\")");
    }

    public void doQuery_4_b() {
        System.out.println(
                "4 b) add a row to Staff regarding J. Hughes, who is a Professor and has a PhD, an S_ID of JH, and works for Sociology");
        
        query("INSERT OR IGNORE INTO staff VALUES (\"JH\", \"J.H.\", \"Hughes\", \"Professor\", \"PhD\", \"SOCIO\")");
    }

    public void doQuery_4_c() {
        System.out.println("4 c) add a row to Work_on for J.Hughes working on COMIC from 1991 to 1994.");

        query("INSERT OR IGNORE INTO work_on VALUES (\"JH\", \"COMIC\", 1991, 1994)");
    }

    public void doQuery_5_a() {
        System.out.println("5 a) what are the names of people who started work on COMIC between 1990 and 1992?");

        SelectQueryBuilder sQueryBuilder = new SelectQueryBuilder();
        sQueryBuilder.select("staff.s_name").from("staff")
            .join(JoinType.NATURAL, "work_on", null, null)
            .join(JoinType.NATURAL, "projects", null, null)
            .where(
                new AndNode(
                    new ComparisonNode(
                        "projects.p_title", ComparisonOperator.EQUAL, "\"COMIC\""
                        ),
                    new BetweenNode("work_on.start_date", "1990", "1992")
                )
            );
        System.out.println(sQueryBuilder.buildQuery());
        queryAndPrint(sQueryBuilder.buildQuery());

    }

    public void doQuery_5_b() {
        System.out.println(
                "5 b) what is the total amount of funding, and the average amount of funding, of all projects?");
                
        SelectQueryBuilder sQueryBuilder = new SelectQueryBuilder();
        sQueryBuilder.select("SUM(funding)").select("AVG(funding)").from("projects");
        // System.out.println(sQueryBuilder.buildQuery());
        queryAndPrint(sQueryBuilder.buildQuery());
    }

    public void doQuery_6() {
        System.out.println("6. List staff names and the number of projects they have worked on.");

        SelectQueryBuilder sQueryBuilder = new SelectQueryBuilder();
        sQueryBuilder.select("staff.s_name").select("COUNT(work_on.p_id)").from("work_on")
            .join(JoinType.NATURAL, "staff", null, null)
            .groupBy("staff.s_name");
        // System.out.println(sQueryBuilder.buildQuery());
        queryAndPrint(sQueryBuilder.buildQuery());
    }

    public void execute_7c() {
        System.out.println("7 c) Delete tuples in work_on for s_id equal tp 'JH'");

        query("DELETE FROM department WHERE d_id = \"SOCIO\"");
    }

    public void execute_7a() {
        System.out.println("7 a) Delete tuples in department for d_id equal to 'SOCIO'");

        query("DELETE FROM staff WHERE s_id = \"JH\"");
    }

    public void execute_7b() {
        System.out.println("7 b) Delete tuples in staff where s_id equal to 'JH'");

        query("DELETE FROM work_on WHERE s_id = \"JH\"");
    }

    public void parameterise_3_a(String lecturerName) {
        // in this method, we want you to "parameterise" query 3a) so that, in the
        // argument to this method, your provide the name of the staff member, and can
        // subsequently find out what course code and name they give
        System.out.println("Parameterising Q3 a) using " + lecturerName);

        SelectQueryBuilder sQueryBuilder = new SelectQueryBuilder();
        String[] keys = {"give_course.c_id", "courses.c_title", "staff.s_name"};
        sQueryBuilder.select(keys).from("courses")
            .join(JoinType.INNER, "give_course", "courses.c_id", "give_course.c_id")
            .join(JoinType.NATURAL, "staff", null, null)
            .where(new ComparisonNode("staff.s_name", ComparisonOperator.EQUAL, '\"' + lecturerName + '\"'));
        // System.out.println(sQueryBuilder.buildQuery());
        queryAndPrint(sQueryBuilder.buildQuery());
    }
}
