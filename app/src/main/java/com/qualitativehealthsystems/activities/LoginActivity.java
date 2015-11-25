package com.qualitativehealthsystems.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qualitativehealthsystems.R;
import com.qualitativehealthsystems.helpers.QHSRestClient;
import com.qualitativehealthsystems.models.requests.AuthenticationRequest;
import com.qualitativehealthsystems.models.responses.AuthenticationResponse;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

//    @Bind(R.id.input_email) EditText _emailText;
//    @Bind(R.id.input_password) EditText _passwordText;
//    @Bind(R.id.btn_login) Button _loginButton;
//    @Bind(R.id.link_signup) TextView _signupLink;
//    ProgressDialog mProgressDialog;

    @Bind(R.id.email) AutoCompleteTextView _emailText;
    @Bind(R.id.password) EditText _passwordText;
    @Bind(R.id.email_sign_in_button) Button _loginButton;
    @Bind(R.id.signUpTextView) TextView _signupLink;
    ProgressDialog mProgressDialog;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ButterKnife.bind(this);

//        ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
//        // Load image, decode it to Bitmap and display Bitmap in ImageView (or any other view
//        //  which implements ImageAware interface)
//        imageLoader.displayImage(imageUri, imageView);
        
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //login();
                login2();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

    }

    public void login2() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        mProgressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Authenticating...");
        mProgressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        AuthenticationRequest request = new AuthenticationRequest(email, password);

        try {
            QHSRestClient.get().login(request, new Callback<AuthenticationResponse>() {
                @Override
                public void success(AuthenticationResponse authenticationResponse, Response response) {
                    // success!
                    if (authenticationResponse != null) {
                        Log.i("Retrofit", new Gson().toJson(authenticationResponse));
                    }

                    if (authenticationResponse.IsAuthenticated) {
                        onLoginSuccess();
                    } else {
                        onLoginFailed();
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

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 8 || password.length() > 20) {
            _passwordText.setError("between 8 and 20 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}




//  ************************
//  Previous login method used an AsynTask for web method invocation
//  ************************
//    public void login() {
//        Log.d(TAG, "Login");
//
//        if (!validate()) {
//            onLoginFailed();
//            return;
//        }
//
//        _loginButton.setEnabled(false);
//
//        String email = _emailText.getText().toString();
//        String password = _passwordText.getText().toString();
//
//        // TODO: Implement your own authentication logic here.
//
////        new android.os.Handler().postDelayed(
////                new Runnable() {
////                    public void run() {
////                        // On complete call either onLoginSuccess or onLoginFailed
////                        onLoginSuccess();
////                        // onLoginFailed();
////                        progressDialog.dismiss();
////                    }
////                }, 3000);
//
//        new LoginAsyncTask(email, password).execute();
//    }

//  ******************************
//  AsyncTask for invoking login web method
//  ******************************

//public class LoginAsyncTask extends AsyncTask<Void, Void, Void> {
//    private String mUserName;
//    private String mPassword;
//    private boolean mIsAuthenticated = false;
//    private boolean mIsLockedOut = false;
//    private JSONObject mResponseObject = null;
//
//    public LoginAsyncTask(String username, String password){
//        mIsAuthenticated = false;
//        mUserName = username;
//        mPassword = password;
//    }
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
////            // Create a progressdialog
////            mProgressDialog2 = new ProgressDialog(MainActivity.this);
////            // Set progressdialog title
////            mProgressDialog2.setTitle("Qualitative Health Systems");
////            // Set progressdialog message
////            mProgressDialog2.setMessage("Loading...");
////            mProgressDialog2.setIndeterminate(false);
////            // Show progressdialog
////            mProgressDialog2.show();
//
//        mProgressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme);
//        mProgressDialog.setIndeterminate(true);
//        mProgressDialog.setMessage("Authenticating...");
//        mProgressDialog.show();
//    }
//
//    @Override
//    protected Void doInBackground(Void... params) {
//
//        //mIsAuthenticated = JSONfunctions.login(mUserName, mPassword);
//        mResponseObject = JSONfunctions.login(mUserName, mPassword, true);
//        try {
//            mIsAuthenticated = mResponseObject.getBoolean("IsAuthenticated");
//            JSONObject objUser = mResponseObject.getJSONObject("User");
//            mIsLockedOut = objUser.getBoolean("_IsLockedOut");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Void args) {
////            // Locate the listview in listview_main.xml
////            listview = (ListView) findViewById(R.id.listview);
////            // Pass the results into ListViewAdapter.java
////            adapter = new ListViewAdapter(MainActivity.this, arraylist);
////            // Set the adapter to the ListView
////            listview.setAdapter(adapter);
//
//
//        // Close the progressdialog
//        mProgressDialog.dismiss();
////            mCallbackText.setText("Success!");
//
//        // On complete call either onLoginSuccess or onLoginFailed
//        if (mIsAuthenticated) {
//            onLoginSuccess();
//        } else {
//            onLoginFailed();
//        }
//    }
//}

//  ************************
//  Weather API example
//  ************************
//        WeatherRestClient.get().getWeather("Benbrook,TX", "2de143494c0b295cca9337e1e96b00e0", new Callback<WeatherResponse>() {
//            @Override
//            public void success(WeatherResponse weatherResponse, Response response) {
//                // success!
//                if (weatherResponse != null) {
//                    Log.i("Retrofit", new Gson().toJson(weatherResponse));
//                    WeatherResponse.main Main = weatherResponse.main;
//                    if (Main != null) {
//                        Log.i("Retrofit", new Gson().toJson(Main));
//                    }
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                // something went wrong
//                Log.i("Retrofit", "Retrofit" + error.getMessage());
//            }
//        });


/*
// Method for constructing a JSON object to pass to a web method
//            // Create an array
//            arraylist = new ArrayList<HashMap<String, String>>();
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
 */