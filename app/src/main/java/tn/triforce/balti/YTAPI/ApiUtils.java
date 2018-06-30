package tn.triforce.balti.YTAPI;

/**
 * Created by noure on 03/06/2017.
 */

public class ApiUtils {
    public static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";

    public static YTService getYTIService() {
        return RetrofitClient.getClient(BASE_URL).create(YTService.class);
}
}
