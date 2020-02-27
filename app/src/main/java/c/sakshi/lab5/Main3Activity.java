package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main3Activity extends AppCompatActivity {
    int noteid = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        EditText noteText = (EditText) findViewById(R.id.editText);
        Intent intent = getIntent();
        noteid = intent.getIntExtra("noteid", -1);

        if (noteid!= -1) {
            Note note = Main2Activity.notes.get(noteid);
            String noteContent = note.getContent();
            noteText.setText(noteContent);
        }

    }

    public void saveFunction(View view) {
        EditText noteText = (EditText) findViewById(R.id.editText);
        String content = noteText.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        String username = sharedPreferences.getString("username", "");

        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date= dateFormat.format(new Date());

        if (noteid == -1) {
            title = "NOTE_"+ (Main2Activity.notes.size() + 1);
            dbHelper.saveNotes(username,title,content,date);
        } else {
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNote(title,date,content);
        }

        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
