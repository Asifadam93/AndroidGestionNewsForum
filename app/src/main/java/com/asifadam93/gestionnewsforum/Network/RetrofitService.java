package com.asifadam93.gestionnewsforum.Network;

import java.util.Map;

/**
 * Created by Asifadam93 on 10/07/2017.
 */

public class RetrofitService implements IService {

    private IRetrofitService retrofitService;

    @Override
    public void login(Map<String, String> loginMap) {

    }

    public IRetrofitService getRetrofitService() {

        if(retrofitService == null){
            retrofitService = RetrofitSession.getInstance().create(IRetrofitService.class);
        }

        return retrofitService;
    }
}
