package org.bakku.yodatalks.network;

/**
 * Created by christian on 02/07/16.
 */
public interface YodaDownloadListener {

    void onSuccess(String response);
    void onError();

}
