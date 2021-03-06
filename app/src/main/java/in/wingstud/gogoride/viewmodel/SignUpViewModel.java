package in.wingstud.gogoride.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Observable;

import in.wingstud.gogoride.R;
import in.wingstud.gogoride.activity.Dashboard;
import in.wingstud.gogoride.activity.LoginActi;
import in.wingstud.gogoride.api.JsonDeserializer;
import in.wingstud.gogoride.api.RequestCode;
import in.wingstud.gogoride.api.WebCompleteTask;
import in.wingstud.gogoride.api.WebTask;
import in.wingstud.gogoride.api.WebUrls;
import in.wingstud.gogoride.model.LoginModelResponcse;
import in.wingstud.gogoride.util.Utils;

public class SignUpViewModel extends Observable implements WebCompleteTask {

    private Context context;
    public ObservableInt progressBar;
    public final ObservableField<String> f_name = new ObservableField<>("");
    public final ObservableField<String> l_name = new ObservableField<>("");
    public final ObservableField<String> email = new ObservableField<>("");
    public final ObservableField<String> mobile = new ObservableField<>("");
//    public final ObservableField<String> password = new ObservableField<>("");
//    public final ObservableField<String> referral_code = new ObservableField<>("");

    public SignUpViewModel(Context context) {
        this.context = context;
    }

    //----------------------------------Using Volley----------------------------
    public void SignUpRequest() {
//        if (TextUtils.isEmpty(f_name.get())) {
////            Utils.alertDialog(context, context.getString(R.string.app_name), "Required first name");
//        } else if (TextUtils.isEmpty(l_name.get())) {
////            Utils.alertDialog(context, context.getString(R.string.app_name), "Required last name");
//        } else if (TextUtils.isEmpty(mobile.get())) {
////            Utils.alertDialog(context, context.getString(R.string.app_name), "mobile number is Required");
//        } else {
            HashMap objectNew = new HashMap();
            objectNew.put("mobile", mobile.get());
            objectNew.put("f_name", f_name.get());
            objectNew.put("l_name", l_name.get());
            objectNew.put("email", email.get()+"");
//            objectNew.put("reff_code", password.get());
            Log.i("Sigup_res", objectNew + "");
            new WebTask(context, WebUrls.BASE_URL + WebUrls.Register, objectNew, SignUpViewModel.this, RequestCode.CODE_Register, 1);
//        }
//----------------------------------Using Retrofit------------
//        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
//        Call<JsonObject> call = getResponse.SignUpStep1(Mobile, fname, lname, Email, ReferralCode);
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                Log.i("Sigup_res",response.body()+"");
//                context.startActivity(new Intent(context, OtpScreen.class));
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Log.i("Sigup_error",t.getMessage());
//            }
//        });
    }

    public void Toast(String string) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_Register == taskcode) {
            Log.i("Sigup_res", response);

            LoginModelResponcse modelResponcse = JsonDeserializer.deserializeJson(response,LoginModelResponcse.class);
//                JSONObject jsonObject = new JSONObject(response);
            if (modelResponcse.statusCode == 1) {

//                    Log.i("sdfsafda",mobile.get());
                context.startActivity(new Intent(context, LoginActi.class)
                        .putExtra("activity", "signup")
                        .putExtra("mobile", response)
                );
            } else {
//                    JSONObject err_msg = jsonObject.optJSONObject("error_message");
//                    if (err_msg != null) {
//                        if (err_msg.has("mobile")) {
//                            Toast(err_msg.getJSONArray("mobile").get(0) + "");
//                        } else if (err_msg.has("email")) {
//                            Toast(err_msg.getJSONArray("email").get(0) + "");
//                        } else if (err_msg.has("f_name")) {
//                            Toast(err_msg.getJSONArray("f_name").get(0) + "");
//                        } else if (err_msg.has("l_name")) {
//                            Toast(err_msg.getJSONArray("l_name").get(0) + "");
//                        }
//                    } else {
                    Toast(modelResponcse.errorMessage);
//                    }
            }
        }

    }
}
