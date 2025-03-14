import database.services.DatabaseInitService;
import database.services.DuplicateCheckerService;
import database.services.HashingService;
import database.services.LoginService;

import java.io.IOException;
import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Starting.....");
        DatabaseInitService.initialize();
    }
}