package app.com.example.venepallyprajith.jsonparsing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Member> memberList;
        ArrayAdapter mAdapter;

        String data=loadJSONFromAsset();
        memberList=parseJson(data);

        ArrayList<String> list=new ArrayList<>();
        for(int i=0;i<memberList.size();i++){
            list.add(memberList.get(i).getName()+","+memberList.get(i).getAge()+","+memberList.get(i).getMaritalstatus());
        }


        mAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list, R.id.listText, list);
        ListView listView=(ListView)findViewById(R.id.listView);

        listView.setAdapter(mAdapter);




    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("jsonObject.txt");
            // check size
           int size = is.available();
            // create buffer for IO
            byte[] buffer = new byte[size];
            // get data to buffer
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public ArrayList<Member> parseJson(String json){
        ArrayList<Member> temp=new ArrayList<Member>();
        try{
            JSONObject jsonObject=new JSONObject(json);

            if(jsonObject.has("SEARCHRES")){
                if(jsonObject.getJSONObject("SEARCHRES").has("PROFILE")){
                    JSONArray jsonArray=jsonObject.getJSONObject("SEARCHRES").getJSONArray("PROFILE");

                    for(int i=0;i<jsonArray.length();i++){
                        Member mem=new Member();
                        if(jsonArray.getJSONObject(i).has("NAME")) {
                            mem.setName(jsonArray.getJSONObject(i).getString("NAME"));
                        }
                        if(jsonArray.getJSONObject(i).has("AGE")) {
                                mem.setAge(jsonArray.getJSONObject(i).getString("AGE"));
                        }
                        if(jsonArray.getJSONObject(i).has("MARITALSTATUS")){
                            mem.setMaritalstatus(jsonArray.getJSONObject(i).getString("MARITALSTATUS"));
                        }
                        temp.add(mem);


                    }

                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Member>();
        }
       return temp;
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
}
