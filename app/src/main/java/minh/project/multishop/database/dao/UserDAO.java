package minh.project.multishop.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import minh.project.multishop.database.entity.User;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user WHERE (username=:username)")
    User queryByUserName(String username);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);
}
