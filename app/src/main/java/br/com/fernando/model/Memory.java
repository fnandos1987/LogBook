package br.com.fernando.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "memory")
public class Memory {

    @PrimaryKey(autoGenerate = true)
    private final Integer id;
    private final String title;
    private final String description;
    private final String photo;
    private final Date creationDate;

    public Memory(Integer id, String title, String description, String photo, Date creationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.photo = photo;
        this.creationDate = creationDate;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoto() {
        return photo;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
