package br.com.fernando.dao;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

import br.com.fernando.model.Memory;

public class MemoryViewModel extends AndroidViewModel {

    public final MutableLiveData<List<Memory>> memorys = new MutableLiveData<>();
    public final MutableLiveData<Memory> memoryLiveData = new MutableLiveData<>();
    public final MutableLiveData<Boolean> sucess = new MutableLiveData<>();

    public MemoryViewModel(@NonNull Application application) {
        super(application);
    }

    public void fetchMemorys() {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                memorys.postValue(LogBookStore.getInstance(getApplication())
                        .getMemoryDao()
                        .fetchMemorys());
                return null;
            }
        }.execute();
    }

    public void findMemoryById(int id) {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                Memory Memory = LogBookStore.getInstance(getApplication())
                        .getMemoryDao()
                        .findById(id);
                memoryLiveData.postValue(Memory);
                return null;
            }
        }.execute();
    }

    public void insert(final Memory Memory) {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                LogBookStore.getInstance(getApplication())
                        .getMemoryDao()
                        .insert(Memory);
                sucess.postValue(true);
                return null;
            }
        }.execute();
    }

    public void delete(final Memory Memory) {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                LogBookStore.getInstance(getApplication())
                        .getMemoryDao()
                        .delete(Memory);
                sucess.postValue(true);
                return null;
            }
        }.execute();
    }
}
