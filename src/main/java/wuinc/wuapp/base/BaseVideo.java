package wuinc.wuapp.base;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import wuinc.wuapp.*;


public class BaseVideo extends Activity{

    private CustomProgressDialog progressDialog;
    private AddDataAsyncTask AddData;
    private GetDataAsyncTask getData;
    private SuppDataAsyncTask suppData;
    private GetDataByPathAsyncTask getDataByPath;
    private int success;
    private String message;
    private String urlAdd="http://10.110.108.83/base_video/ajout_video.php";
    private String urlSupp="http://10.110.108.83/base_video/suppression_video.php";
    private String urlGet="http://10.110.108.83/base_video/affichage_video.php";
    private String urlGetByPath="http://10.110.108.83/base_video/getByPath_video.php";
    private ArrayList<Publication> mVideos;
    private BaseUser baseUser;

    public BaseVideo() {
        super();
    }

    public Publication add(Publication video) throws ParseException, ExecutionException, InterruptedException {
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
        nameValuePair.add(new BasicNameValuePair("user",video.getUser().getPseudo()));
        nameValuePair.add(new BasicNameValuePair("challenge", video.getChallenge()));
        nameValuePair.add(new BasicNameValuePair("pathVideoClip",video.getPathVideoClip()));
        nameValuePair.add(new BasicNameValuePair("score",String.valueOf(video.getScore())));
        nameValuePair.add(new BasicNameValuePair("dateCreation",video.getDateCreation()));
        nameValuePair.add(new BasicNameValuePair("comment",video.getComment()));

        for (int i=0 ;i< video.getUserLike().size();i++) {
            nameValuePair.add(new BasicNameValuePair("userLike[]", video.getUserLike().get(i)));
        }

        AddData = new AddDataAsyncTask();
        AddData.execute(nameValuePair);
        video.setId(AddData.get());
        return video;
    }

    private class AddDataAsyncTask extends AsyncTask<List<NameValuePair>,Void , Integer> {
        @Override
        protected void onPreExecute() {
            Log.i("add", "onPreExecute");
            super.onPreExecute();
            //progressDialog.show();
        }

        @Override
        protected Integer doInBackground(List<NameValuePair>... params) {
            Log.i("add", " start doInBackground");
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            List<NameValuePair> param = params[0];

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(urlAdd, ServiceHandler.POST,param);

            Log.d("Response: ",jsonStr);
            if (jsonStr != null) {
                try {

                    JSONObject jsonObj = new JSONObject(jsonStr);
                    success = jsonObj.getInt("success");
                    message = jsonObj.getString("message");
                    Log.i("sucess", String.valueOf(success));
                    Log.i("message", message);
                    Integer id = jsonObj.getInt("valeur");
                    return id;

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            Log.i("add", " end doInBackground");
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            Log.i("add", "onPostExecute");
            super.onPostExecute(result);
            //if (progressDialog.isShowing())
            //{
            //    progressDialog.dismiss();
            //}
            if(success==1)
            {
                Toast.makeText(getApplicationContext(), "Succés :"+message, Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Erreur" +message, Toast.LENGTH_LONG).show();
            }


        }

    }


    public void delete(Publication video){
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
        nameValuePair.add(new BasicNameValuePair("id",String.valueOf(video.getId())));
        suppData =new SuppDataAsyncTask();
        suppData.execute(nameValuePair);
    }


    private class SuppDataAsyncTask extends  AsyncTask<List<NameValuePair>, Void, Void> {
        @Override
        protected void onPreExecute() {
            Log.i("supp", "onPreExecute");
            super.onPreExecute();
            //progressDialog.show();
        }

        @Override
        protected Void doInBackground(List<NameValuePair>... params) {
            Log.i("supp", " start doInBackground");
            ServiceHandler sh = new ServiceHandler();

            List<NameValuePair> param = params[0];

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(urlSupp, ServiceHandler.POST,param);

            Log.d("Response: ",jsonStr);
            if (jsonStr != null) {
                try {

                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // return value of success
                    success=jsonObj.getInt("success");
                    message = jsonObj.getString("message");
                    Log.i("suucess", String.valueOf(success));
                    Log.i("message", message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            Log.i("supp", " end doInBackground");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i("supp", "onPostExecute");
            super.onPostExecute(result);
            //if (progressDialog.isShowing())
            //{
            //    progressDialog.dismiss();
            //}
            if(success == 1)
            {
                Toast.makeText(getApplicationContext(), "Supprimé ", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Erreur", Toast.LENGTH_LONG).show();
            }
        }
    }


    public ArrayList<Publication> returnAll() throws ExecutionException, InterruptedException {

        getData=new GetDataAsyncTask();
        getData.execute();
        this.mVideos = getData.get();
        BaseUser baseUser = new BaseUser();
        for(Publication video : mVideos){
            video.setUser(baseUser.getUserByPseudo(video.getUserS()));
        }

        return mVideos;

    }

    private class GetDataAsyncTask extends  AsyncTask<Void, Void, ArrayList<Publication>> {
        @Override
        protected void onPreExecute() {
            Log.i("add", "onPreExecute");
            super.onPreExecute();
            //progressDialog.show();
        }

        @Override
        protected ArrayList<Publication> doInBackground(Void... params) {
            Log.i("add", " start doInBackground");
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(urlGet, ServiceHandler.GET);

            Log.d("Response: ",jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // return value of success
                    success=jsonObj.getInt("success");
                    Log.i("success", String.valueOf(success));
                    if (success==0)
                    {
                        // success=0 ==> there is a string = message
                        message=jsonObj.getString("message");
                        Log.i("message", message);
                    }
                    else if (success==1)
                    {
                        ArrayList<Publication> videos = new ArrayList<Publication>();
                        // success=1 ==> there is an array of data = valeurs
                        JSONArray dataValues = jsonObj.getJSONArray("valeurs");
                        // loop each row in the array
                        for(int j=0;j<dataValues.length();j++)
                        {
                            JSONObject values = dataValues.getJSONObject(j);

                            Publication mVideo = new Publication(null,null,null,null);
                            mVideo.setId(Integer.valueOf(values.getString("id")));
                            mVideo.setChallenge(values.getString("challenge"));
                            mVideo.setUserS(values.getString("user"));
                            mVideo.setPathVideoClip(values.getString("pathVideoClip"));
                            mVideo.setScore(Integer.valueOf(values.getString("score")));
                            mVideo.setDateCreation(values.getString("dateCreation"));
                            mVideo.setComment(values.getString("comment"));

                            ArrayList<String> userLike = new ArrayList<>();
                            JSONArray dataLU = values.getJSONArray("LU");
                            for(int k=0;k<dataLU.length();k++){
                                JSONObject value = dataLU.getJSONObject(k);
                                values.getJSONArray("LU").get(k);
                                userLike.add(value.getString("like_user_name"));
                            }
                            mVideo.setUserLike(userLike);

                            videos.add(mVideo);
                        }
                        return videos;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            Log.i("add", " end doInBackground");
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Publication> result) {
            Log.i("add", "onPostExecute");
            super.onPostExecute(result);
            if (progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
            if(success==1)
            {
                Toast.makeText(getApplicationContext(), "Bien recu ", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Erreur", Toast.LENGTH_LONG).show();
            }


        }

    }


        public Publication update(Publication video) throws ParseException, ExecutionException, InterruptedException {

            this.delete(video);
            Publication newVideo = this.add(video);
            return newVideo;

        }



        public Publication getVideoByPath(String chemin) throws ExecutionException, InterruptedException {

            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
            nameValuePair.add(new BasicNameValuePair("pathVideoClip",chemin));
            getDataByPath = new GetDataByPathAsyncTask();
            getDataByPath.execute(nameValuePair);
            Publication video = getDataByPath.get();
            BaseUser baseUser = new BaseUser();
            video.setUser(baseUser.getUserByPseudo(video.getUserS()));
            return video;
        }

    private class GetDataByPathAsyncTask extends  AsyncTask<List<NameValuePair>, Void, Publication> {
        @Override
        protected void onPreExecute() {
            Log.i("add", "onPreExecute");
            super.onPreExecute();
            //progressDialog.show();
        }

        @Override
        protected Publication doInBackground(List<NameValuePair>... params) {
            Log.i("add", " start doInBackground");
            ServiceHandler sh = new ServiceHandler();

            List<NameValuePair> param = params[0];

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(urlGetByPath, ServiceHandler.POST,param);

            Log.d("Response: ",jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // return value of success
                    success=jsonObj.getInt("success");
                    Log.i("success", String.valueOf(success));
                    if (success==0)
                    {
                        // success=0 ==> there is a string = message
                        message=jsonObj.getString("message");
                        Log.i("message", message);
                    }
                    else if (success==1) {
                        Publication mVideo = new Publication(null,null,null,null);


                        mVideo.setId(Integer.valueOf(jsonObj.getString("id")));
                        mVideo.setChallenge(jsonObj.getString("challenge"));
                        mVideo.setUserS(jsonObj.getString("user"));
                        mVideo.setPathVideoClip(jsonObj.getString("pathVideoClip"));
                        mVideo.setScore(Integer.valueOf(jsonObj.getString("score")));
                        mVideo.setDateCreation(jsonObj.getString("dateCreation"));
                        mVideo.setComment(jsonObj.getString("comment"));

                        ArrayList<String> userLike = new ArrayList<>();
                        JSONArray dataLU = jsonObj.getJSONArray("LU");
                        for(int k=0;k<dataLU.length();k++){
                            JSONObject value = dataLU.getJSONObject(k);
                            userLike.add(value.getString("like_user_name"));
                        }
                        mVideo.setUserLike(userLike);

                        return mVideo;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            Log.i("add", " end doInBackground");
            return null;
        }

        @Override
        protected void onPostExecute(Publication result) {
            Log.i("add", "onPostExecute");
            super.onPostExecute(result);
            //if (progressDialog.isShowing())
            //{
            //    progressDialog.dismiss();
            //}
            if(success==1)
            {
                Toast.makeText(getApplicationContext(), "Bien recu ", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Erreur", Toast.LENGTH_LONG).show();
            }


        }

    }


}
