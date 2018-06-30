package tn.triforce.balti.DTO;

import java.util.ArrayList;
import java.util.List;

import tn.triforce.balti.DAO.YTVideoAPI.Video;
import tn.triforce.balti.DAO.YTVideoAPI.Item;
import tn.triforce.balti.DAO.YTVideoAPI.ListVideoResult;
import tn.triforce.balti.DAO.YTVideoAPI.Thumbnails;
import tn.triforce.balti.YTAPI.ApiUtils;
import tn.triforce.balti.YTAPI.YTService;

/**
 * Created by noure on 04/06/2017.
 */

public class VideoManager {
    //YT API call
    public static final YTService mService = ApiUtils.getYTIService();

    public static List<Video> getVideoList(ListVideoResult listVideoResult) {
        List<Video> listVideo = new ArrayList<>();
        for (Item item : listVideoResult.getItems()) {

            Video video = new Video();

            video.setId(item.getId().getVideoId());
            video.setTitle(item.getSnippet().getTitle());

                video.setTitle(item.getSnippet().getTitle());

                video.setDescription(item.getSnippet().getDescription());


            video.setPublishedAt(item.getSnippet().getPublishedAt());//todo convert to datetime
            Thumbnails thumbnails = item.getSnippet().getThumbnails();
            if (thumbnails != null) {
                 if (thumbnails.getHigh() != null) {
                    video.setImage(thumbnails.getHigh().getUrl());//todo convert to url
                } else if (thumbnails.getMedium() != null) {
                    video.setImage(thumbnails.getMedium().getUrl());//todo convert to url
                } else if (thumbnails.getDefault() != null) {
                    video.setImage(thumbnails.getDefault().getUrl());//todo convert to url
                } else {
                    video.setImage(thumbnails.getDefault().getUrl());//todo convert to url
                }
            }
            listVideo.add(video);
        }
        return listVideo;
    }
}
