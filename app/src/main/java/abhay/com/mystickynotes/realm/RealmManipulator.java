package abhay.com.mystickynotes.realm;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import abhay.com.mystickynotes.model.StickyNote;

/**
 * Created by abhay on 21/8/17.
 */

public class RealmManipulator {

    private static RealmManipulator dbManager;
    private static Realm realm;
    private String realmName = "RealmNote";

    private RealmManipulator(Context context) {
        if (realm == null) {
            //Realm Object is Created
            Realm.init(context);
            RealmConfiguration configuration = new RealmConfiguration.Builder()
                    .name(realmName)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(configuration);
        }
    }

    public static RealmManipulator getRealmInstance(Context context) {

        if (dbManager == null) {
            //DBManager Object is Created
            dbManager = new RealmManipulator(context);
        }

        return dbManager;
    }

    //Note Added in Realm
    public void addOrUpdateRealmList(StickyNote note) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(note);
        realm.commitTransaction();
    }

    //Get Realm Data
    public RealmResults<StickyNote> getAllStickyNotes() {
        realm.beginTransaction();
        RealmResults<StickyNote> realmNotes = realm.where(StickyNote.class).findAll();
        realm.commitTransaction();

        return realmNotes;
    }

    //Realm Data Updated
    public void updateStickyNote(StickyNote note) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(note);
        realm.commitTransaction();

    }


    //Realm Data Deleted
    public void deleteStickyNote(StickyNote note) {
        realm.beginTransaction();
        note.deleteFromRealm();
        realm.commitTransaction();

    }

}
