package com.tokbox.movingpublisherview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;

public class MainActivity extends Activity implements Session.SessionListener, PublisherKit.PublisherListener {
    public static final String APIKEY = "";
    public static final String TOKEN = "";
    public static final String SESSION_ID = "";

    private Session sess;
    private Publisher pub;
    private boolean viewInC1 = true;
    private String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sess = new Session(this, APIKEY, SESSION_ID);
        sess.connect(TOKEN);

        pub = new Publisher(this);
        pub.startPreview();

        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        final LinearLayout c1 = ((LinearLayout)findViewById(R.id.container1));
        c1.addView(pub.getView(), lp);

        final LinearLayout c2 = ((LinearLayout)findViewById(R.id.container2));

        Button b = (Button)findViewById(R.id.changeBtn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewInC1) {
                    c1.removeAllViews();
                    c2.addView(pub.getView(), lp);
                } else {
                    c2.removeAllViews();
                    c1.addView(pub.getView(), lp);
                }
                viewInC1 = !viewInC1;
            }
        });


    }

    @Override
    public void onConnected(Session session) {
        sess.publish(pub);
    }

    @Override
    public void onDisconnected(Session session) {

    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {

    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {

    }

    @Override
    public void onError(Session session, OpentokError opentokError) {
        Log.d(TAG, opentokError.toString());
    }

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {
    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {

    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {
        Log.d(TAG, opentokError.toString());
    }
}
