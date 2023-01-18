package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {

        UserDaoHibernateImpl connection = new UserDaoHibernateImpl();

        connection.createUsersTable();
        connection.saveUser("SCARRA", "LEE", (byte) 30);
        connection.saveUser("VALKYRAE", "HOUFFSTETTER", (byte) 29);
        connection.saveUser("QUARTERJADE", "VALORANT", (byte) 26);
        connection.saveUser("MASSAYOSHII", "JOHN", (byte) 30);
        connection.getAllUsers();
        System.out.println(connection.getAllUsers().toString());
        connection.cleanUsersTable();
        connection.dropUsersTable();
    }
}
