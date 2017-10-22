package abhay.com.mystickynotes.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;
import abhay.com.mystickynotes.R;
import abhay.com.mystickynotes.model.StickyNote;
import abhay.com.mystickynotes.realm.RealmManipulator;

public class NewStickyNoteActivity extends AppCompatActivity {

    EditText etTitle, etContent;
    Button btnSave;

    StickyNote stickyNote;
    Button button_blog,button_app;

    int id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sticky_note);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo);

        button_blog=(Button)findViewById(R.id.button_blog);
        button_app=(Button)findViewById(R.id.button_app);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etContent = (EditText) findViewById(R.id.etContent);

        btnSave = (Button) findViewById(R.id.btn_save);

        final StickyNote parcelableStickyNote = getIntent().getParcelableExtra("StickyNoteData");
        if(parcelableStickyNote != null) {
            etTitle.setText(parcelableStickyNote.getNoteTitle());
            etContent.setText(parcelableStickyNote.getNoteContent());
            etTitle.setSelection(etTitle.getText().length());
            btnSave.setText("Update Note");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = etTitle.getText().toString();
                String content = etContent.getText().toString();

                if(title.equals("") && content.equals("")){
                    Snackbar.make(v, "Plese Enter Title and Content", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {

                    if(btnSave.getText().equals("Update Note")){
                        parcelableStickyNote.setNoteTitle(etTitle.getText().toString());
                        parcelableStickyNote.setNoteContent(etContent.getText().toString());
                        RealmManipulator.getRealmInstance(getApplicationContext()).updateStickyNote(parcelableStickyNote);
                        finish();
                    }
                    else {

                        RealmResults<StickyNote> realmNotes = RealmManipulator.getRealmInstance(getApplicationContext()).getAllStickyNotes();
                        if (realmNotes.size() != 0)
                            id = realmNotes.last().getId();

                        stickyNote = new StickyNote();
                        stickyNote.setId(id + 1);
                        stickyNote.setNoteTitle(etTitle.getText().toString());
                        stickyNote.setNoteContent(etContent.getText().toString());

                        RealmManipulator.getRealmInstance(getApplicationContext()).addOrUpdateRealmList(stickyNote);

                    Intent i = new Intent(getApplicationContext(),StickyHome.class);
                    startActivity(i);
                        finish();
                    }
                }
            }
        });



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
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            switch (item.getItemId()) {
                // Respond to the action bar's Up/Home button
                case android.R.id.home:
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
            }
        return super.onOptionsItemSelected(item);

    }
}
