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



public class MainActivity extends Activity {

    private Button button1_1,button1_2,button1_3,button1_4,button1_5;
    private Button button2_1,button2_2,button2_3,button2_4,button2_5;
    private Button button3_1,button3_2,button3_3,button3_4,button3_5;
    private Button button4_1,button4_2,button4_3,button4_4,button4_5;
    private Button button5_1,button5_2,button5_3,button5_4,button5_5;
    private Button[][]button=new Button[5][5];
    private String Demo;
    private int day=0;
    private int classNext=0;
    private int Cweek=0;
    private String httpurl="Http://120.27.53.146:5000/api/schedule";
    private static String [][][]TB_class=new String[20][5][5];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWebTable();
        bindButton();
        bindView();
    }
    public void bindButton(){

        button1_1= (Button) findViewById(R.id.button1_1);
        button1_2= (Button) findViewById(R.id.button1_2);
        button1_3= (Button) findViewById(R.id.button1_3);
        button1_4= (Button) findViewById(R.id.button1_4);
        button1_5= (Button) findViewById(R.id.button1_5);
        button2_1= (Button) findViewById(R.id.button2_1);
        button2_2= (Button) findViewById(R.id.button2_2);
        button2_3= (Button) findViewById(R.id.button2_3);
        button2_4= (Button) findViewById(R.id.button2_4);
        button2_5= (Button) findViewById(R.id.button2_5);
        button3_1= (Button) findViewById(R.id.button3_1);
        button3_2= (Button) findViewById(R.id.button3_2);
        button3_3= (Button) findViewById(R.id.button3_3);
        button3_4= (Button) findViewById(R.id.button3_4);
        button3_5= (Button) findViewById(R.id.button3_5);
        button4_1= (Button) findViewById(R.id.button4_1);
        button4_2= (Button) findViewById(R.id.button4_2);
        button4_3= (Button) findViewById(R.id.button4_3);
        button4_4= (Button) findViewById(R.id.button4_4);
        button4_5= (Button) findViewById(R.id.button4_5);
        button5_1= (Button) findViewById(R.id.button5_1);
        button5_2= (Button) findViewById(R.id.button5_2);
        button5_3= (Button) findViewById(R.id.button5_3);
        button5_4= (Button) findViewById(R.id.button5_4);
        button5_5= (Button) findViewById(R.id.button5_5);

        button[0][0]=button1_1;
        button[0][1]=button1_2;
        button[0][2]=button1_3;
        button[0][3]=button1_4;
        button[0][4]=button1_5;
        button[1][0]=button2_1;
        button[1][1]=button2_2;
        button[1][2]=button2_3;
        button[1][3]=button2_4;
        button[1][4]=button2_5;
        button[2][0]=button3_1;
        button[2][1]=button3_2;
        button[2][2]=button3_3;
        button[2][3]=button3_4;
        button[2][4]=button3_5;
        button[3][0]=button4_1;
        button[3][1]=button4_2;
        button[3][2]=button4_3;
        button[3][3]=button4_4;
        button[3][4]=button4_5;
        button[4][0]=button5_1;
        button[4][1]=button5_2;
        button[4][2]=button5_3;
        button[4][3]=button5_4;
        button[4][4]=button5_5;
    }
    public void  getWebTable(){

        RequestQueue mQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, httpurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String s) {

                       JsonTable(s);
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
    public void JsonTable(String string){
        try {
            JSONTokener jsonTokener=new JSONTokener(string);
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
                while (C_class.hasNext()) {
                    String c_key = (String) C_class.next();
                    int  note=table_class.get(c_key).length();
                    for(int x=1;x<=note;x++){
                        String Test=table_class.get(c_key).getString(x-1);
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
                    }
                    if(++classNext>4){
                        classNext=0;
                    }

                }
                ++day;

            }
            Log.d("LSY",TB_class[12][0][1]);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void bindView(){
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                //button[i][j].setText();

                Log.d("LSY",TB_class[0][i][j]);
            }

        }

    }



}
