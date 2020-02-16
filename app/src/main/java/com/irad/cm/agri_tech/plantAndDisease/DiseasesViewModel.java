package com.irad.cm.agri_tech.plantAndDisease;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class DiseasesViewModel extends ViewModel {

    private LiveData<Diseases> liveDiseasesData;
    private DiseasesRepository diseasesRepository = new DiseasesRepository();

    // using live data
    public LiveData<Diseases> getLiveDiseasesData() {
        if (liveDiseasesData == null) {
            liveDiseasesData = diseasesRepository.getDiseasesData();
        }
        return liveDiseasesData;
    }

//    public void setLiveDiseasesData(int position) {
//        diseasesRepository = diseasesRepository.getDiseasesData().;
//    }

}
