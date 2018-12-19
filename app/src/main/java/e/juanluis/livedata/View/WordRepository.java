package e.juanluis.livedata.View;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import e.juanluis.livedata.Model.Word;
import e.juanluis.livedata.Model.WordDao;
import e.juanluis.livedata.dataBase.WordRoomDatabase;

public class WordRepository {

    private WordDao mWordDao;
    private LiveData<Word> mAllWords;

    WordRepository(Application application){
        WordRoomDatabase db= WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    LiveData<Word> getAllWords(){
        return mAllWords;
    }

    public void insert (Word word){
        new insertAsyncTask(mWordDao).execute(word);
    }

    private static class    insertAsyncTask extends AsyncTask<Word,Void, Void>{

        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Word... words) {
            mAsyncTaskDao.insert(words[0]);
            return null;
        }
    }
}
