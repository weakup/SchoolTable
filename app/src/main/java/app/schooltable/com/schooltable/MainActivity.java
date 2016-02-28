package app.schooltable.com.schooltable;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class MainActivity extends Activity {

    private Button button1_1;
    private String Demo;
    private int day=0;
    private int classNext=0;
    private int Cweek=0;
    private String httpurl="Http://120.27.53.146:5000/api/schedule";
    private String [][][]TB_class=new String[20][5][5];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getTable();
       // button1_1.findViewById(R.id.button1_1);


    }
    public void  getTable(){

        RequestQueue mQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, httpurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONTokener jsonTokener=new JSONTokener(s);
                            JSONObject Table= (JSONObject) jsonTokener.nextValue();
                            JSONObject schedule=Table.getJSONObject("schedule");
                            Map<String,JSONObject> table_date=new LinkedHashMap<>();//每周的课
                            table_date.put("Mon", schedule.getJSONObject("Mon"));
                            table_date.put("Tue", schedule.getJSONObject("Tue"));
                            table_date.put("Wed",schedule.getJSONObject("Wed"));
                            table_date.put("Thu", schedule.getJSONObject("Thu"));
                            table_date.put("Fri", schedule.getJSONObject("Fri"));
                            Iterator D_date=table_date.keySet().iterator();
                            for (int a=0;a<20;a++)
                                for (int b=0;b<5;b++)
                                    for(int c=0;c<5;c++)
                                        TB_class[a][b][c]="no";
                            while (D_date.hasNext()) {

                                String d_key = (String) D_date.next();
                                Map<String, JSONArray> table_class = new LinkedHashMap<>();//每天的课
                                table_class.put("1-2", table_date.get(d_key).getJSONArray("1-2"));
                                table_class.put("3-4", table_date.get(d_key).getJSONArray("3-4"));
                                table_class.put("5-6", table_date.get(d_key).getJSONArray("5-6"));
                                table_class.put("7-8", table_date.get(d_key).getJSONArray("7-8"));
                                table_class.put("9-10", table_date.get(d_key).getJSONArray("9-10"));
                                Iterator C_class = table_class.keySet().iterator();
                               /* while (C_class.hasNext()) {
                                    String c_key = (String) C_class.next();
                                    String Test=table_class.get(c_key).getString(0);
                                    if (Test.length()>5){//这周这天这节有课
                                        JSONTokener json_class = new JSONTokener(Test);
                                        JSONObject myclass = (JSONObject) json_class.nextValue();
                                        myclass.getString("class_name");
                                        String infos=myclass.getString("weeks");//转化成数组
                                        infos = infos.replaceAll("[\\[\\]]", "");
                                        String[] info=infos.split(",");
                                        int[] w=new int[info.length];
                                        for (int i=0;i<info.length;i++){
                                            w[i]=Integer.parseInt(info[i]);

                                        }
                                        for (int i=0;i<info.length;i++){
                                            for(Cweek=0;Cweek<20;Cweek++){
                                                if (w[i] == Cweek + 1) {

                                                    TB_class[Cweek][day][classNext] = myclass.getString("class_name");

                                                }
                                            }
                                        }


                                    }
                                    if(++classNext>4){
                                        classNext=0;
                                    }

                                }*/
                                ++day;

                            }
                             Log.d("LSY",TB_class[1][4][1]);

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
