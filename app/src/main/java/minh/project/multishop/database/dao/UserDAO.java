package minh.project.multishop.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import minh.project.multishop.database.entity.User;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user")
    User getCurrentUser();

    @Query("SELECT * FROM user WHERE (username=:username)")
    User getUserByUSN(String username);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("DELETE FROM user")
    void deleteAll();
}
