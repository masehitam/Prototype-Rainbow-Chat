package me.masehitam.prototypechatrainbow;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ale.listener.SigninResponseListener;
import com.ale.listener.StartResponseListener;
import com.ale.rainbowsdk.RainbowSdk;

public class MainActivity extends AppCompatActivity implements ChatFeedFragment.OnListFragmentInteractionListener {

    private FrameLayout frameContainer;
    private AppCompatActivity mActivity;

    String email = "farianti@mailinator.com";
    String pass = "ZXasqw12!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;

        frameContainer = (FrameLayout) findViewById(R.id.frame_container);

        RainbowSdk.instance().connection().start(new StartResponseListener() {
            @Override
            public void onStartSucceeded() {
                RainbowSdk.instance().connection().signin(email, pass, new SigninResponseListener() {
                    @Override
                    public void onSigninSucceeded() {
                        // You are now connected
                        // Do something on the thread UI
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mActivity, "Sukses Login", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onRequestFailed(RainbowSdk.ErrorCode errorCode, String s) {
                        // Do something on the thread UI
                        final String errorCodeFinal = errorCode.toString();
                        final String sFinal = s;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mActivity, "Gagal Login kode: " + errorCodeFinal + "karena: " + sFinal, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onRequestFailed(RainbowSdk.ErrorCode errorCode, String err) {
                // Do something
                final String errorCodeFinal = errorCode.toString();
                final String sFinal = err;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mActivity, "Gagal Login kode: " + errorCodeFinal + "karena: " + sFinal, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        // Create fragment and give it an argument specifying the article it should show
        ChatFeedFragment newFragment = new ChatFeedFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.frame_container, newFragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onListFragmentInteraction(String name) {

    }
}
