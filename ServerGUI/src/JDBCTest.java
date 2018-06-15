/*
 * Copyright (c) 2018. Alikhan Mussabekov
 * Gmail: alikhanmussabekov@gmail.com
 */

public class JDBCTest {
    public static void main(String[] args) {
        JDBCPostgre database = new JDBCPostgre(Citizens.class);
        database.delete(new Citizens("andre",13));
        //database.insert(new Citizens("andre",13));

    }
}
