package com.irad.cm.agri_tech.plantAndDisease;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.irad.cm.agri_tech.RetrofitClientInstance;
import com.irad.cm.agri_tech.Utilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiseasesRepository {

    public LiveData<Diseases> getDiseasesData() {
        final MutableLiveData<Diseases> diseasesMutableLiveData = new MutableLiveData<>();
        DiseaseService service = RetrofitClientInstance.getRetrofitInstance().create(DiseaseService.class);
        Call<Diseases> call = service.getDiseaseList(Utilities.LANGUAGE);
        call.enqueue(new Callback<Diseases>() {
            @Override
            public void onResponse(Call<Diseases> call, Response<Diseases> response) {
                Diseases diseases = response.body();
                diseasesMutableLiveData.setValue(diseases);
                System.out.println("List of DISEASES: "+ response.body());
            }

            @Override
            public void onFailure(Call<Diseases> call, Throwable t) {
//                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("DISEASE REPO", "Error get the Diseases data");
            }
        });
        return diseasesMutableLiveData;
    }

}
