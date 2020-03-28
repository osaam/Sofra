package com.osamaelsh3rawy.otlop.data.local.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.osamaelsh3rawy.otlop.data.model.items.ItemsData;

import java.util.List;

@Dao
public interface RoomDao {

    @Insert
    void add(ItemsData... items);

    @Update
    void Update(ItemsData... items);

    @Delete
    void Delete(ItemsData... items);

    @Query("select * from ItemsData")
    List<ItemsData> getAll();

    @Query("delete from ItemsData")
    void deleteAll();

}
