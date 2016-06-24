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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import wuinc.wuapp.*;

/**
 * Created by greg on 17/06/2016.
 */
public class BaseChallenge1vs1 extends Activity{

    private CustomProgressDialog progressDialog;
    private AddDataAsyncTask AddData;
    private GetDataAsyncTask getData;
    private SuppDataAsyncTask suppData;
    private GetDataByTitleAsyncTask getDataByTitle;
    private int success;
    private String message;
    private String urlAdd="http://192.168.43.233/base_challenge1vs1/ajout_challenge1vs1.php";
    private String urlSupp="http://192.168.43.233/base_challenge1vs1/suppression_challenge1vs1.php";
    private String urlGet="http://192.168.43.233/base_challenge1vs1/affichage_challenge1vs1.php";
    private String urlGetByTitle="http://192.168.43.233/base_challenge1vs1/getByTitle_challenge1vs1.php";
    private ArrayList<Challenge_1v1> mChallenges;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
    private BaseInbox baseInbox;
    private BaseTrash baseTrash;
    private BaseUser baseUser;
    private BaseVideo baseVideo;


    public BaseChallenge1vs1() {
        super();
    }

    public void add(Challenge_1v1 challenge) throws ExecutionException, InterruptedException {
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
        nameValuePair.add(new BasicNameValuePair("publisher",challenge.getPublisher().getPseudo()));
        nameValuePair.add(new BasicNameValuePair("title",challenge.getTitle()));
        nameValuePair.add(new BasicNameValuePair("description",challenge.getDescription()));
        nameValuePair.add(new BasicNameValuePair("date",challenge.getDate()));
        nameValuePair.add(new BasicNameValuePair("video_proposal",challenge.getProposal().getPathVideoClip()));
        nameValuePair.add(new BasicNameValuePair("challenger",challenge.getChallenger().getPseudo()));
        nameValuePair.add(new BasicNameValuePair("video_answer",challenge.getAnswer().getPathVideoClip()));

        AddData =new AddDataAsyncTask();
        AddData.execute(nameValuePair);
    }

    private class AddDataAsyncTask extends AsyncTask<List<NameValuePair>,Void , Void> {
        @Override
        protected void onPreExecute() {
            Log.i("add", "onPreExecute");
            super.onPreExecute();
            //progressDialog.show();
        }

        @Override
        protected Void doInBackground(List<NameValuePair>... params) {
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

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            Log.i("add", " end doInBackground");
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
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


    public void delete(String title) throws ExecutionException, InterruptedException {
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
        nameValuePair.add(new BasicNameValuePair("title",title));

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
            if(success==1)
            {
                Toast.makeText(getApplicationContext(), "Supprimé ", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Erreur", Toast.LENGTH_LONG).show();
            }
        }
    }


    public ArrayList<Challenge_1v1> returnAll() throws ExecutionException, InterruptedException {

        getData=new GetDataAsyncTask();
        getData.execute();
        this.mChallenges = getData.get();
        return this.mChallenges;

    }




    private class GetDataAsyncTask extends  AsyncTask<Void, Void, ArrayList<Challenge_1v1>> {
        @Override
        protected void onPreExecute() {
            Log.i("add", "onPreExecute");
            super.onPreExecute();
            //progressDialog.show();
        }

        @Override
        protected ArrayList<Challenge_1v1> doInBackground(Void... params) {
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
                        ArrayList<Challenge_1v1> challenges = new ArrayList<Challenge_1v1>();
                        // success=1 ==> there is an array of data = valeurs
                        JSONArray dataValues = jsonObj.getJSONArray("valeurs");
                        // loop each row in the array
                        for(int j=0;j<dataValues.length();j++)
                        {
                            JSONObject values = dataValues.getJSONObject(j);

                            BaseUser baseUser = new BaseUser();
                            BaseVideo baseVideo = new BaseVideo();

                            Challenge_1v1 challenge = new Challenge_1v1(baseUser.getUserByPseudo(values.getString("publisher")),values.getString("title"),values.getString("description"));
                            challenge.setDate(values.getString("birth_date"));
                            challenge.setProposal(baseVideo.getVideoByPath(values.getString("video_proposal")));
                            challenge.setChallenger(baseUser.getUserByPseudo(values.getString("challenger")));
                            challenge.setAnswer(baseVideo.getVideoByPath(values.getString("video_answer")));

                            challenges.add(challenge);
                        }
                        return challenges;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            Log.i("add", " end doInBackground");
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Challenge_1v1> result) {
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


    public Void update(Challenge_1v1 challenge) throws ParseException, ExecutionException, InterruptedException {

        this.delete(challenge.getTitle());
        this.add(challenge);
        return null;
    }


    public Challenge_1v1 getChallengeByTitle(String title) throws ExecutionException, InterruptedException {

        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
        nameValuePair.add(new BasicNameValuePair("title",title));
        getDataByTitle = new GetDataByTitleAsyncTask();
        getDataByTitle.execute(nameValuePair);
        Challenge_1v1 challenge = getDataByTitle.get();
        return challenge;
    }




    private class GetDataByTitleAsyncTask extends  AsyncTask<List<NameValuePair>, Void, Challenge_1v1> {
        @Override
        protected void onPreExecute() {
            Log.i("add", "onPreExecute");
            super.onPreExecute();
            //progressDialog.show();
        }

        @Override
        protected Challenge_1v1 doInBackground(List<NameValuePair>... params) {
            Log.i("add", " start doInBackground");
            ServiceHandler sh = new ServiceHandler();

            List<NameValuePair> param = params[0];

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(urlGetByTitle, ServiceHandler.POST,param);

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

                        BaseUser baseUser = new BaseUser();
                        BaseVideo baseVideo = new BaseVideo();

                        Challenge_1v1 challenge = new Challenge_1v1(baseUser.getUserByPseudo(jsonObj.getString("publisher")),jsonObj.getString("title"),jsonObj.getString("description"));
                        challenge.setDate(jsonObj.getString("birth_date"));
                        challenge.setProposal(baseVideo.getVideoByPath(jsonObj.getString("video_proposal")));
                        challenge.setChallenger(baseUser.getUserByPseudo(jsonObj.getString("challenger")));
                        challenge.setAnswer(baseVideo.getVideoByPath(jsonObj.getString("video_answer")));

                        return challenge;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            Log.i("add", " end doInBackground");
            return null;
        }

        @Override
        protected void onPostExecute(Challenge_1v1 result) {
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
