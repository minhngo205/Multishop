package minh.project.multishop.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import minh.project.multishop.database.entity.User;
import minh.project.multishop.database.dao.UserDAO;

@Database(entities = User.class, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
}