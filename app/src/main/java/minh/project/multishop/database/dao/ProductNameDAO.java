package minh.project.multishop.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import minh.project.multishop.database.entity.ProductName;

@Dao
public interface ProductNameDAO {
    @Query("SELECT * FROM ProductName")
    List<ProductName> getAllProductName();

    @Query("SELECT productName FROM ProductName")
    List<String> getListNameRecord();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertName(List<ProductName> productNames);
}
