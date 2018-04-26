package com.example.james.fitnet;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.arch.persistence.room.RoomDatabase;

// https://developer.android.com/training/data-storage/room/
@Entity
public class Goal {
    @PrimaryKey
    private int uid;

    @ColumnInfo(name = "title")
    private String title;
}

@Dao
public interface GoalDao {
    @Query("SELECT * FROM goal")
    List<User> getAll();

    @Query("SELECT * FROM goal WHERE uid IN (:goalIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM goal WHERE title LIKE :title LIMIT 1")
    User findByName(String title);

    @Insert
    void insertAll(Goal... goals);

    @Delete
    void delete(Goal goal);
}

@Database(entities = {Goal.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GoalDao userDao();
}

AppDatabase db = Room.databaseBuilder(getApplicationContext(),
        AppDatabase.class, "fitnet").build();


public class NewGoalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
