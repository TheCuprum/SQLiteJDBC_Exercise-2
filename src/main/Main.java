package main;

import db.DbUser;

public class Main {

    DbUser myDbUser = null;

    private void go() {
        String lecName = null;
        System.out.println("In go...");
        myDbUser = new DbUser("University.db");
        myDbUser.doQuery_1_a();
        myDbUser.doQuery_1_b();

        myDbUser.doQuery_2_a();
        myDbUser.doQuery_2_b();

        myDbUser.doQuery_3_a();
        myDbUser.doQuery_3_b();

        myDbUser.doQuery_4_a();
        myDbUser.doQuery_4_b();
        myDbUser.doQuery_4_c();
        myDbUser.doQuery_3_b();

        myDbUser.doQuery_5_a();
        myDbUser.doQuery_5_b();

        myDbUser.parameterise_3_a("Mariani");

        myDbUser.doQuery_6();

        myDbUser.execute_7c();
        myDbUser.execute_7a();
        myDbUser.execute_7b();

        myDbUser.doQuery_6();

        System.out.println("Processing over");

        // This piece of code allows you to read in the surname of a lecturer at
        // run-time and use it as the parameter
        // to parameterise_3_a, but there is no need to use this code unless you want
        // to.

        // BufferedReader brin = new BufferedReader(new InputStreamReader(System.in));
        // System.out.print("type in surname of lecturer: ");
        // try {
        // lecName = brin.readLine();
        // } catch (IOException e) {
        // System.out.println("Main.go() : Failure in I/O" + e);
        // }
        // ;

        // parameterise_3_a(lecName);

        // myDbUser.close();
    }; // end of method "go"

    public static void main(String[] args) {
        Main myMain = new Main();
        myMain.go();
    } // end of method "main"

} // end of class "Main"
