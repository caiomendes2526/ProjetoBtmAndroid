package com.caio.mendes.projetobtmandroid.api;

import com.caio.mendes.projetobtmandroid.model.NotificacaoDados;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotificacaoService {

    @Headers({
            "Authorization:Key=AAAATyK-iBo:APA91bGetPCSoDtvsx_Pm_kEyB2cqGdw6zoLfyQ2jZw9UxGziz3J4Ue30FjaPGSA20PnHJnLSUPvM2O-xlFBZgRbloS2Sm3_s7yaQsizBO6BrGrPTf3Wv9vF_aoszWvc8C-TmPT5bN1N",
            "Content-Type:application/json"

    })

    @POST("send")
    Call<NotificacaoDados> salvarNotificacao(@Body NotificacaoDados notificacaoDados);

}
