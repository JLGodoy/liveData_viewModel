package e.juanluis.livedata.dataBase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;

import e.juanluis.livedata.Model.Word;
import e.juanluis.livedata.Model.WordDao;
import io.reactivex.annotations.NonNull;

@Database(entities = {Word.class},version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    public abstract WordDao wordDao();
    private static WordRoomDatabase INSTACE;

    public static WordRoomDatabase getDatabase(final Context context){
        if (INSTACE == null){
            synchronized (WordRoomDatabase.class){
                INSTACE = Room.databaseBuilder(context.getApplicationContext(),
                        WordRoomDatabase.class, "word_database")
                        .fallbackToDestructiveMigration()
                        .build().addCallback(sRoomDatabaseCallback);
            }
        }
        return INSTACE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTACE).execute();
        }
    };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordDao mDao;
        String[] words ={"dolphin","crocodile", "cobra"};

        public PopulateDbAsync(WordRoomDatabase db) {

            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            mDao.deleteAll();

            for (int i = 0; i<= words.length -1; i++){
                Word word = new Word(words[i]);
                mDao.insert(word);
            }
            return null;
        }
    }
}
