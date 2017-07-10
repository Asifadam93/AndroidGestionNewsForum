package com.asifadam93.gestionnewsforum.Network;

/**
 * Created by Asifadam93 on 10/07/2017.
 */

public interface IServiceResultListener<T> {

    void onResult(ServiceResult<T> result);

}
