package app.schooltable.com.schooltable;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getTable();
    }
    public void  getTable(){
        String httpurl="Http://120.27.53.146:5000/api/schedule";
        RequestQueue mQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, httpurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONTokener jsonTokener=new JSONTokener(s);
                            JSONObject Table= (JSONObject) jsonTokener.nextValue();
                            JSONObject schedule=Table.getJSONObject("schedule");
                            HashMap<String,JSONObject> table_date=new HashMap<>();//每周的课
                            table_date.put("Mon", schedule.getJSONObject("Mon"));
                            table_date.put("Tue", schedule.getJSONObject("Tue"));
                            table_date.put("Wed",schedule.getJSONObject("Wed"));
                            table_date.put("Thu", schedule.getJSONObject("Thu"));
                            table_date.put("Fri", schedule.getJSONObject("Fri"));
                            Iterator D_date=table_date.keySet().iterator();
                            while (D_date.hasNext()) {
                                String d_key= (String) D_date.next();
                                HashMap<String, JSONArray> table_class = new HashMap<>();//每天的课
                                table_class.put("1-2", table_date.get(d_key).getJSONArray("1-2"));
                                table_class.put("3-4", table_date.get(d_key).getJSONArray("3-4"));
                                table_class.put("5-6", table_date.get(d_key).getJSONArray("5-6"));
                                table_class.put("7-8", table_date.get(d_key).getJSONArray("7-8"));
                                table_class.put("9-10", table_date.get(d_key).getJSONArray("9-10"));
                                Iterator C_class = table_class.keySet().iterator();
                                while (C_class.hasNext()) {//
                                    String c_key = (String) C_class.next();
                                    String class_name = table_class.get(c_key).getString(0);
                                    if (!class_name.equals("null")) {
                                        JSONTokener json_class = new JSONTokener(class_name);
                                        JSONObject myclass = (JSONObject) json_class.nextValue();
                                        Log.d("lsy", "周几:"+d_key+ "第几堂课：" + c_key + "课名:" + myclass.getString("class_name"));
                                    }

                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener(){


            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Log.d("123",volleyError.getMessage(),volleyError);
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("username", "201412067");
                map.put("password", "199511");
                map.put("action", "update");
                return map;
            }

        };
        mQueue.add(stringRequest);
    }



}
