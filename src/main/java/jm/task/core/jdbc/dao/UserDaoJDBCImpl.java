package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.sql.ResultSet;

public class UserDaoJDBCImpl implements UserDao {
    private static Connection connection;
    static {
        connection = Util.getConnection();
    }

    public UserDaoJDBCImpl() {
    }

    public  void createUsersTable() {
        try {
            PreparedStatement pstmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS sys.users( " +
                    "id BIGINT  NOT NULL AUTO_INCREMENT , " +
                    " name VARCHAR(40) NOT NULL , " +
                    " lastName VARCHAR(40) NOT NULL ,  " +
                    "age TINYINT NOT NULL , " +
                    "PRIMARY KEY(id) ); ");
            System.out.println("Таблица создана");
            pstmt.execute();
            connection.commit();
            pstmt.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            PreparedStatement pstmt = connection.prepareStatement("USE sys");
            pstmt.execute("DROP TABLE  IF EXISTS users ; ");
            connection.commit();
            System.out.println("Таблица удалена");
            pstmt.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO users ( name , LastName ,age ) VALUES (?,?,?);");
            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setByte(3, age);
            pstmt.execute();
            connection.commit();
            pstmt.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM users WHERE id = " + id);
            pstmt.execute();
            connection.commit();
            System.out.println("User удален");
            pstmt.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public List< User > getAllUsers() {
        List< User > users = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM users ; ");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("LastName"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }
            connection.commit();
            pstmt.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            PreparedStatement pstmt = connection.prepareStatement(" TRUNCATE TABLE  users ;");
            pstmt.execute();
            connection.commit();
            System.out.println("Таблица очищена");
            pstmt.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            System.out.println("Не удалось очистить");
        }
    }
}
