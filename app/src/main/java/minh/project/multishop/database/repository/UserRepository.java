package minh.project.multishop.database.repository;

import minh.project.multishop.database.AppDatabase;
import minh.project.multishop.database.DatabaseUtil;
import minh.project.multishop.database.dao.UserDAO;

public class UserRepository {
    private final UserDAO userDao;

    private final AppDatabase database;

    public UserRepository() {
        this.database = DatabaseUtil.getDatabase();
        this.userDao = database.userDAO();
    }
}
