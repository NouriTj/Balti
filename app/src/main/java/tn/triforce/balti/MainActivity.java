package tn.triforce.balti;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.transition.Visibility;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.triforce.balti.Adapter.RecyclerTouchListener;
import tn.triforce.balti.Adapter.VideoListAdapter;
import tn.triforce.balti.DAO.YTVideoAPI.Video;
import tn.triforce.balti.DAO.YTVideoAPI.ListVideoResult;
import tn.triforce.balti.DTO.VideoManager;
import tn.triforce.balti.YTAPI.YTService;

import static android.provider.MediaStore.Video.Thumbnails.VIDEO_ID;

public class MainActivity extends AppCompatActivity {

    TextView offline;
    RecyclerView lvVideos;
    FloatingActionButton fab;
    List<Video> viedoList = new ArrayList<>();
    private ProgressDialog progressDialog;
      Snackbar snackBar;

    InterstitialAd interstitialAd;
    AdView bannerAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setDisplayUseLogoEnabled(true);
        //getSupportActionBar().setLogo(R.drawable.ic_home_logo);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        bannerAdView = (AdView) findViewById(R.id.adView);

        offline = (TextView) findViewById(R.id.offline);
        lvVideos = (RecyclerView) findViewById(R.id.lv_videos);
        lvVideos.setNestedScrollingEnabled(false);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        lvVideos.setLayoutManager(mLayoutManager);
        lvVideos.setHasFixedSize(true);
        lvVideos.addOnItemTouchListener(
                new RecyclerTouchListener(getApplicationContext(), new RecyclerTouchListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                       //Ad
                        showInterstitial();

                        List<String> idsList  = new ArrayList<String>();
                        for (Video video : viedoList) {
                            idsList.add(video.getId());
                        }
                         Intent intent = YouTubeStandalonePlayer.createVideosIntent
                                 (MainActivity.this, YTService.KEY, idsList, position,0, true, false);

                        startActivity(intent);
                    }
                })
        );
    }

    private void shareAction(String text){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getString(R.string.share)));
    }
    private void initAds(){
        MobileAds.initialize(getApplicationContext(), getString(R.string.admob_app_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        bannerAdView.loadAd(adRequest);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitiel_ad_unit_id));
    }
    private void showInterstitial() {
        if (!interstitialAd.isLoaded()) {
            AdRequest interstitialAdRequest = new AdRequest.Builder().build();
            interstitialAd.loadAd(interstitialAdRequest);
        }
        interstitialAd.show();
    }
    @Override
    protected void onStart ( ) {
        super.onStart( );
        loadVideoList();

    }
    public void loadVideoList() {
        if(Utils.isOnline(MainActivity.this)){
            initAds();
            offline.setVisibility(View.GONE);
            if(snackBar!=null)
                snackBar.dismiss();

        progressDialog = ProgressDialog.show(this, "", getResources().getString(R.string.loading) );
        final Call<ListVideoResult> call = VideoManager.mService.listVideos();
        call.enqueue(new Callback<ListVideoResult>() {
            @Override
            public void onResponse(Call<ListVideoResult> call, Response<ListVideoResult> response) {

                if(response.isSuccessful()) {
                    //Log.d("MainActivity", "posts loaded from API: "+response.body().getItems().get(0).getSnippet().getTitle());
                    viedoList = VideoManager.getVideoList(response.body());

                    VideoListAdapter videoListAdapter = new VideoListAdapter(MainActivity.this, viedoList);
                    lvVideos.setAdapter(videoListAdapter);
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                    //Log.d("MainActivity", "posts loaded from API statusCode : "+statusCode);
                }
            }

            @Override
            public void onFailure(Call<ListVideoResult> call, Throwable t) {
                Log.d("MainActivity", "error loading from API"+t.getMessage()+"\n"+t.getStackTrace().toString());
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
        }else{
            offline.setVisibility(View.VISIBLE);
            snackBar = Snackbar.make(findViewById(R.id.content_main), "offline mnt", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Online",  new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loadVideoList();}
                    });
            snackBar.show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareAction(getString(R.string.text_share_app));
                return true;

            case R.id.action_about:
                Intent aboutIntent = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(aboutIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
