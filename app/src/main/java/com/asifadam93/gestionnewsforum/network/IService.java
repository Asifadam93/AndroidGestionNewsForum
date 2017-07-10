package com.asifadam93.gestionnewsforum.network;

import com.asifadam93.gestionnewsforum.model.User;

import java.util.Map;

/**
 * Created by Asifadam93 on 10/07/2017.
 */

public interface IService {

    void login(Map<String, String> loginMap, IServiceResultListener<String> result);

    void subscribe(Map<String, String> subscribeMap, IServiceResultListener<String> result);

    void getUser(String token, IServiceResultListener<User> result);

    void updateUser(String token, Map<String, String> updateMap, IServiceResultListener<String> result);

}
