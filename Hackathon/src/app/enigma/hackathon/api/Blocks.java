package app.enigma.hackathon.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;
import app.enigma.hackathon.adapter.BlocksLvAdapter;
import app.enigma.hackathon.object.Block;
import app.enigma.hackathon.object.User;
import app.enigma.hackathon.util.Hackathon;

import com.google.gson.Gson;

public class Blocks extends AsyncTask<String, Void, Block[]> {
	public static final String BLOCKS = Hackathon.API_ROOT + "blocks.get.json?";

	Activity activity;
	SwipeRefreshLayout swipeLayout;
	ArrayList<Block> alBlock;
	BlocksLvAdapter aaBlock;

	public Blocks(Activity activity, SwipeRefreshLayout swipeLayout, ArrayList<Block> alBlock, BlocksLvAdapter aaBlock) {
		this.swipeLayout = swipeLayout;
		this.activity = activity;
		this.alBlock = alBlock;
		this.aaBlock = aaBlock;
	}

	@Override
	protected void onPreExecute() {
		swipeLayout.setRefreshing(true);
	}

	@Override
	protected Block[] doInBackground(String... arg0) {
		Block[] list = null;

		User user = Hackathon.getLogin(activity);

		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();

		params.add(new BasicNameValuePair(Hackathon.USERNAME, user.getUsername()));
		params.add(new BasicNameValuePair(Hackathon.PASSWORD, user.getPassword()));

		String uri = BLOCKS + URLEncodedUtils.format(params, "utf-8");

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet request = new HttpGet();

			request.setURI(new URI(uri));
			HttpResponse response = httpclient.execute(request);

			String json = EntityUtils.toString(response.getEntity());

			Gson gson = new Gson();
			list = gson.fromJson(json, Block[].class);
		} 
		catch (URISyntaxException e) {e.printStackTrace();} 
		catch (ClientProtocolException e) {e.printStackTrace();} 
		catch (IOException e) {e.printStackTrace();}

		return list;
	}

	@Override
	protected void onPostExecute(Block[] list) {
		swipeLayout.setRefreshing(false);

		if (list != null) {
			if (list.length != 0) {
				alBlock.clear();
				alBlock.addAll(Arrays.asList(list));
				aaBlock.notifyDataSetChanged();
			}
			else {
				Toast.makeText(activity, "No records", Toast.LENGTH_LONG).show();
			}
		}
		else {
			Toast.makeText(activity, "Unable to retrieve block list", Toast.LENGTH_LONG).show();
		}
	}
}
