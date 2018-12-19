package e.juanluis.livedata.View;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import e.juanluis.livedata.Model.Word;

public class WordViewModel extends AndroidViewModel {

    public WordRepository mRepository;
    private LiveData<Word> mAllWords;

    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    public LiveData<Word> getAllWords(){return mAllWords;}

    public void insert(Word word) {mRepository.insert(word);}
}
