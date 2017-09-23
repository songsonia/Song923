package com.example.geren;
/**
 * 
 * @author syn
 *
 */
//0923
//okok
//fitBug001
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.compile.HttpUrl;
import com.example.compile.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

public class InfoActivity extends Activity {
	public static TextView sView;
	
	String result = "";	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		StrictMode.enableDefaults();
		sView = (TextView) findViewById(R.id.stunameView);
		
		getStuName();
		System.out.println(sView.getText());
		
	}
	
	public void getStuName(){
		
		InputStream is1 = null;
		
		try {
			ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("txtUsername", LoginActivity.userNameValue));
			pairs.add(new BasicNameValuePair("txtPassword", LoginActivity.passwordValue));
			//pairs.add(new BasicNameValuePair("mobile", "android"));
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(HttpUrl.urlstuname);
			post.setEntity(new UrlEncodedFormEntity(pairs));					
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			is1 = entity.getContent();				
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection"+e.toString());
			sView.setText("数据传输错误");
		} 
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is1 ,"iso-8859-1"), 8);
			StringBuilder sBuilder = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				sBuilder.append(line+"\n");
			}
			is1.close();
			result = sBuilder.toString();
			
		} catch (Exception e) {
			Log.e("Log_tag", "Error converting result"+e.toString());
		} 	
		
		try {
			String s = "";
			JSONArray jsonArray = new JSONArray(result);
			for(int i=0;i<jsonArray.length();i++){
				
				JSONObject jsonChildNode = jsonArray.getJSONObject(i);
				s = jsonChildNode.optString("sname");			
			}
			sView.setText(s);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("Log_tag", "Error Parsing Data..."+e.toString());
		}
}
		
}