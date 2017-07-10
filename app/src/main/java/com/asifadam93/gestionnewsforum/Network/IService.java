package com.asifadam93.gestionnewsforum.Network;

import java.util.Map;

/**
 * Created by Asifadam93 on 10/07/2017.
 */

public interface IService {

    void login(Map<String,String> loginMap, IServiceResultListener<String> result);

    void subscribe(Map<String,String> subscribeMap, IServiceResultListener<String> result);

}
