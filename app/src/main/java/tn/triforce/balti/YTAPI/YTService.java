package tn.triforce.balti.YTAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tn.triforce.balti.DAO.YTVideoAPI.ListVideoResult;

/**
 * Created by noure on 01/06/2017.
 */

public interface YTService {
      static final String KEY = "AIzaSyD3BO6T-xEeQA0-IGzegiAcGGBEKeWbd-U";
      static final String BALTI_CHANNEL_ID = "UCV9x1Bo83ByXbqJga1ZxaJg";

    @GET("search?part=snippet&type=video&key="+KEY+"&channelId="+BALTI_CHANNEL_ID+"&maxResults=50")
    Call<ListVideoResult> listVideos();
    //Call<ListVideoResult> listVideos(@Query("channelId") String channelId, @Query("maxResults") Integer maxResults);

}
