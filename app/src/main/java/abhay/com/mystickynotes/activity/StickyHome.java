package abhay.com.mystickynotes.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import io.realm.RealmResults;
import abhay.com.mystickynotes.R;
import abhay.com.mystickynotes.adapter.RVAdapter;
import abhay.com.mystickynotes.model.StickyNote;
import abhay.com.mystickynotes.realm.RealmManipulator;

public class StickyHome extends AppCompatActivity {

    RealmResults<StickyNote> realmNotes;
    RecyclerView recyclerView;
    TextView text_noitem;
    Button button_blog,button_app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sicky_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        text_noitem=(TextView)findViewById(R.id.text_noitem);
        button_blog=(Button)findViewById(R.id.button_blog);
        button_app=(Button)findViewById(R.id.button_app);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), NewStickyNoteActivity.class);
                startActivity(i);
                finish();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        realmNotes = RealmManipulator.getRealmInstance(getApplicationContext()).getAllStickyNotes();

        if (realmNotes.size() != 0) {
            text_noitem.setVisibility(View.GONE);
            setAdapter(realmNotes);
        } else {
            text_noitem.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "There are Nothing to Show", Toast.LENGTH_LONG).show();

        }

        button_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +"com.abhay.androidtutorial")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.abhay.androidtutorial")));
                }
            }
        });


        button_blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.geekcodehub.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }
    public void setAdapter(List<StickyNote> noteList) {
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);

        RVAdapter rvAdapter = new RVAdapter(noteList, getApplicationContext());
        recyclerView.setAdapter(rvAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        realmNotes = RealmManipulator.getRealmInstance(getApplicationContext()).getAllStickyNotes();
        setAdapter(realmNotes);

        if (realmNotes.size() != 0) {
            text_noitem.setVisibility(View.GONE);
            setAdapter(realmNotes);
        } else {
            text_noitem.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "There are Nothing to Show", Toast.LENGTH_LONG).show();

        }

    }

}
