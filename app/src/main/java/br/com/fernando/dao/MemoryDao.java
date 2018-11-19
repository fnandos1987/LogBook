package br.com.fernando.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.fernando.model.Memory;

public interface MemoryDao {

    @Query("SELECT * FROM memory")
    List<Memory> fetchMemorys();

    @Query("SELECT * FROM memory WHERE id = :id")
    Memory findById(int id);

    @Insert
    void insert(Memory memory);

    @Update
    void update(Memory memory);

    @Delete
    void delete(Memory memory);

}
