package com.paynesoftware.qualitativehealthsystems;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;

import com.google.gson.Gson;
import com.qualitativehealthsystems.helpers.QHSRestClient;
import com.qualitativehealthsystems.models.Poll;
import com.qualitativehealthsystems.models.responses.PollResponse;
import com.qualitativehealthsystems.services.MessengerService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends Activity {

    static String RANK = "rank";
    static String COUNTRY = "country";
    static String POPULATION = "population";
    static String FLAG = "flag";
    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());
    /** Messenger for communicating with service. */
    Messenger mService = null;
    /** Flag indicating whether we have called bind on the service. */
    boolean mIsBound = false;
    /** Some text view we are using to show state information. */
    TextView mCallbackText;
    TextView mPollsText;
    // JSON bullshit
    // Declare Variables
    JSONObject jsonobject;
    JSONArray jsonarray;
    ListView listview;
    ListViewAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;
    Spinner spinnerDropDown = null;
    String moods[] = {
            "alert",
            "excited",
            "elated",
            "happy",

            "contented",
            "serene",
            "relaxed",
            "calm",

            "Tense",
            "Nervous",
            "Stressed",
            "Upset",

            "Sad",
            "Depressed",
            "Bored",
            "Fatigued"
    };
    private Spinner spinner1, spinner2;
    private Button btnSubmit;
    /**
     * Class for interacting with the main interface of the service.
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  We are communicating with our
            // service through an IDL interface, so get a client-side
            // representation of that from the raw service object.
            mService = new Messenger(service);
            mCallbackText.setText("Attached.");

            // We want to monitor the service for as long as we are
            // connected to it.
            try {
                Message msg = Message.obtain(null,
                        MessengerService.MSG_REGISTER_CLIENT);
                msg.replyTo = mMessenger;
                mService.send(msg);

                // Give it some value as an example.
                msg = Message.obtain(null,
                        MessengerService.MSG_SET_VALUE, this.hashCode(), 0);
                mService.send(msg);
            } catch (RemoteException e) {
                // In this case the service has crashed before we could even
                // do anything with it; we can count on soon being
                // disconnected (and then reconnected if it can be restarted)
                // so there is no need to do anything here.
            }

            // As part of the sample, tell the user what happened.
            Toast.makeText(MainActivity.this, R.string.remote_service_connected,
                    Toast.LENGTH_SHORT).show();
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null;
            mCallbackText.setText("Disconnected.");

            // As part of the sample, tell the user what happened.
            Toast.makeText(MainActivity.this, R.string.remote_service_disconnected,
                    Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActiveAndroid.initialize(this);

        mCallbackText = (TextView) this.findViewById(R.id.txtMain);

        mPollsText = (TextView) this.findViewById(R.id.txtPolls);

//        addItemsOnSpinner2();
//        addListenerOnButton();
//        addListenerOnSpinnerItemSelection();

//        doBindService();
//
//        if (mMessenger != null){
//            Message m = new Message();
//            m.arg1 = 1;
//            try {
//                mMessenger.send(m);
//            } catch (RemoteException e) {
//                mCallbackText.setText("fuck christ.");
//            }
//        }

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Get the selected Spinner ID
                int sid = spinnerDropDown.getSelectedItemPosition();

                String patient_id = "01613580";
                String mood_id = String.valueOf(16 - sid);

                // Post the Mood Poll to the server
                //new PostMoodData(patient_id, mood_id).execute();



                int remote_id = 1;

                // Persist mood poll
                Poll poll = new Poll(remote_id, patient_id, mood_id);
                poll.save();

                List<Poll> polls = Poll.getAll();
                String pollText = "";
                for (int i = 0; i < polls.size(); i++) {
                    pollText += polls.get(i).mood_id;
                }
                mPollsText.setText(pollText);
            }

        });

        // Get reference of SpinnerView from layout/main_activity.xml
        spinnerDropDown =(Spinner)findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_dropdown_item, moods);

        spinnerDropDown.setAdapter(adapter);
        spinnerDropDown.setSelection(0, false);
        spinnerDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // Get select item
            //                int sid=spinnerDropDown.getSelectedItemPosition();
            //                //Toast.makeText(getBaseContext(), "You have selected : " + moods[sid], Toast.LENGTH_SHORT).show();
            //                new PostMoodData("01613580", String.valueOf(sid)).execute();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub
                    }
                });


        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    private int postMoodData(String patient_id, String mood_id) {

        int remote_id = -1;
        Poll request = new Poll(patient_id, mood_id);

        try {
            QHSRestClient.get().submitPoll(request, new Callback<PollResponse>() {
                @Override
                public void success(PollResponse pollResponse, Response response) {
                    // success!
                    if (pollResponse != null) {
                        Log.i("Retrofit", new Gson().toJson(pollResponse));

                        //remote_id = pollResponse.remote_id;
                    }

                    mProgressDialog.dismiss();

                }

                @Override
                public void failure(RetrofitError error) {
                    // something went wrong
                    Log.i("Retrofit", "Retrofit" + error.getMessage());

                    mProgressDialog.dismiss();
                }
            });
        } catch(Exception ex) {
            Log.e("AuthenticationError", ex.getMessage());
        }

        return remote_id;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    // add items into spinner dynamically
    public void addItemsOnSpinner2() {

//        spinner2 = (Spinner) findViewById(R.id.spinner2);
//        List<String> list = new ArrayList<String>();
//        list.add("list 1");
//        list.add("list 2");
//        list.add("list 3");
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, list);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner2.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setSelection(0, false);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        //spinner2 = (Spinner) findViewById(R.id.spinner2);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int sid=spinnerDropDown.getSelectedItemPosition();
                //Toast.makeText(getBaseContext(), "You have selected : " + moods[sid], Toast.LENGTH_SHORT).show();
                new PostMoodData("01613580", String.valueOf(sid)).execute();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }

    void doBindService() {
        // Establish a connection with the service.  We use an explicit
        // class name because there is no reason to be able to let other
        // applications replace our component.
        bindService(new Intent(MainActivity.this,
                MessengerService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
        mCallbackText.setText("Binding.");
    }

    void doUnbindService() {
        if (mIsBound) {
            // If we have received the service, and hence registered with
            // it, then now is the time to unregister.
            if (mService != null) {
                try {
                    Message msg = Message.obtain(null,
                            MessengerService.MSG_UNREGISTER_CLIENT);
                    msg.replyTo = mMessenger;
                    mService.send(msg);
                } catch (RemoteException e) {
                    // There is nothing special we need to do if the service
                    // has crashed.
                }
            }

            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
            mCallbackText.setText("Unbinding.");
        }
    }

    // PostMoodData AsyncTask
    private class PostMoodData extends AsyncTask<Void, Void, Void> {

        private String mPatientId;
        private String mMoodId;

        public PostMoodData(String patientId, String moodId){
            mPatientId = patientId;
            mMoodId = moodId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(MainActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Qualitative Health Systems");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
//            // Create an array
//            arraylist = new ArrayList<HashMap<String, String>>();
            JSONfunctions.postPoll("http://23.229.20.102/api/Poll", mPatientId, mMoodId);

//            try {
//
//                // Locate the array name in JSON
//                jsonarray = jsonobject.getJSONArray("");
//
//                for (int i = 0; i < jsonarray.length(); i++) {
//                    HashMap<String, String> map = new HashMap<String, String>();
//                    jsonobject = jsonarray.getJSONObject(i);
//                    // Retrive JSON Objects
//                    map.put("rank", jsonobject.getString("rank"));
//                    map.put("country", jsonobject.getString("country"));
//                    map.put("population", jsonobject.getString("population"));
//                    map.put("flag", jsonobject.getString("flag"));
//                    // Set the JSON Objects into the array
//                    arraylist.add(map);
//                }
//            } catch (JSONException e) {
//                Log.e("Error", e.getMessage());
//            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
//            // Locate the listview in listview_main.xml
//            listview = (ListView) findViewById(R.id.listview);
//            // Pass the results into ListViewAdapter.java
//            adapter = new ListViewAdapter(MainActivity.this, arraylist);
//            // Set the adapter to the ListView
//            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
            mCallbackText.setText("Success!");
        }
    }

    /**
     * Handler of incoming messages from service.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MessengerService.MSG_SET_VALUE:
                    mCallbackText.setText("Received from service: " + msg.arg1);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

}
