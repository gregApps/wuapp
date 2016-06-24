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
 * Created by greg on 14/06/2016.
 */
public class BaseUser extends Activity{

    private CustomProgressDialog progressDialog;
    private AddDataAsyncTask AddData;
    private GetDataAsyncTask getData;
    private SuppDataAsyncTask suppData;
    private GetDataByPseudoAsyncTask getDataByPseudo;
    private int success;
    private String message;
    private String urlAdd="http://10.110.108.83/base_utilisateur/ajout_utilisateur.php";
    private String urlSupp="http://10.110.108.83/base_utilisateur/suppression_utilisateur.php";
    private String urlGet="http://10.110.108.83/base_utilisateur/affichage_utilisateur.php";
    private String urlGetByPseudo = "http://10.110.108.83/base_utilisateur/getByPseudo_utilisateur.php";
    private ArrayList<User> mUsers;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private BaseInbox baseInbox;
    private BaseTrash baseTrash;

    public BaseUser() {
        super();
    }

    public void add(User user) throws ExecutionException, InterruptedException, ParseException {
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
        nameValuePair.add(new BasicNameValuePair("pseudo",user.getPseudo()));
        nameValuePair.add(new BasicNameValuePair("password",user.getPassword()));
        nameValuePair.add(new BasicNameValuePair("first_name",user.getFirstName()));
        nameValuePair.add(new BasicNameValuePair("last_name",user.getLastName()));
        nameValuePair.add(new BasicNameValuePair("email",user.getUserEmail()));
        nameValuePair.add(new BasicNameValuePair("gender",String.valueOf(user.getGender())));
        nameValuePair.add(new BasicNameValuePair("birth_date",user.getBirthDate()));
        for (int h=0 ;h< user.getFriendsList().getNbFriends();h++) {
            nameValuePair.add(new BasicNameValuePair("friends[]", user.getFriendsList().getFriends().get(h)));
        }

        ArrayList<Mail> inboxMails = user.getMailBox().getInbox();
        baseInbox = new BaseInbox();
        for (int i=0 ;i< inboxMails.size();i++) {
            baseInbox.add(inboxMails.get(i));
        }


        for (int j=0 ;j< user.getMailBox().getRequests().size();j++) {
            nameValuePair.add(new BasicNameValuePair("request_sender[]", user.getMailBox().getRequests().get(j).getSender()));
            nameValuePair.add(new BasicNameValuePair("request_recipient[]", user.getMailBox().getRequests().get(j).getRecipient()));
            nameValuePair.add(new BasicNameValuePair("request_message[]", user.getMailBox().getRequests().get(j).getMessage()));
        }

        ArrayList<Mail> trashMails = user.getMailBox().getTrash();
        baseTrash = new BaseTrash();
        for (int k=0 ;k< trashMails.size();k++) {
            baseTrash.add(trashMails.get(k));
        }

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


    public void delete(User user) throws ExecutionException, InterruptedException {
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
        nameValuePair.add(new BasicNameValuePair("pseudo",user.getPseudo()));

        this.baseInbox = new BaseInbox();
        ArrayList<Mail> inboxMails = this.baseInbox.getInboxByPseudoUser(user.getPseudo());
        for (int k=0 ;k< inboxMails.size();k++) {
                baseInbox.delete(inboxMails.get(k));
            }
        this.baseTrash = new BaseTrash();
        ArrayList<Mail> trashMails = this.baseTrash.getTrashByPseudoUser(user.getPseudo());
        for (int j=0 ;j< trashMails.size();j++) {
            baseInbox.delete(trashMails.get(j));
        }

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


    public ArrayList<User> returnAll() throws ExecutionException, InterruptedException {

        getData=new GetDataAsyncTask();
        getData.execute();
        this.mUsers = getData.get();
        return mUsers;

    }




    private class GetDataAsyncTask extends  AsyncTask<Void, Void, ArrayList<User>> {
        @Override
        protected void onPreExecute() {
            Log.i("add", "onPreExecute");
            super.onPreExecute();
            //progressDialog.show();
        }

        @Override
        protected ArrayList<User> doInBackground(Void... params) {
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
                        ArrayList<User> users = new ArrayList<User>();
                        // success=1 ==> there is an array of data = valeurs
                        JSONArray dataValues = jsonObj.getJSONArray("valeurs");
                        // loop each row in the array
                        for(int j=0;j<dataValues.length();j++)
                        {
                            JSONObject values = dataValues.getJSONObject(j);
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                            User user = new User(values.getString("pseudo"),values.getString("email"),values.getString("password"));
                            user.setFirstName(values.getString("first_name"));
                            user.setLastName(values.getString("last_name"));
                            user.setGender(Boolean.valueOf(values.getString("gender")));
                            user.setBirthDate(values.getString("birth_date"));

                            ArrayList<String> friends = new ArrayList<>();
                            JSONArray dataFR = values.getJSONArray("FR");
                            for(int k=0;k<dataFR.length();k++){
                                JSONObject value = dataFR.getJSONObject(k);
                                friends.add(value.getString("pseudo_friend"));
                            }
                            FriendsList friendsList = new FriendsList();
                            friendsList.setWho(user.getPseudo());
                            friendsList.setFriends(friends);
                            friendsList.setNbFriends(friends.size());
                            user.setFriendsList(friendsList);

                            ArrayList<FriendRequest> friendRequests = new ArrayList<>();
                            JSONArray dataRQ = values.getJSONArray("RQ");
                            for(int k=0;k<dataRQ.length();k++){
                                JSONObject value = dataRQ.getJSONObject(k);
                                friendRequests.add(new FriendRequest(value.getString("request_sender"), value.getString("request_recipient"), value.getString("request_message")));
                            }

                            BaseInbox baseInbox = new BaseInbox();
                            BaseTrash baseTrash = new BaseTrash();

                            ArrayList<Mail> inboxMails = baseInbox.getInboxByPseudoUser(user.getPseudo());
                            ArrayList<Mail> trashMails = baseTrash.getTrashByPseudoUser(user.getPseudo());
                            Mailbox mailbox = new Mailbox();
                            mailbox.setInbox(inboxMails);
                            mailbox.setTrash(trashMails);
                            mailbox.setRequests(friendRequests);
                            user.setMailBox(mailbox);

                            users.add(user);
                        }
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
        protected void onPostExecute(ArrayList<User> result) {
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


    public Void update(User user) throws ParseException, ExecutionException, InterruptedException {

        this.delete(user);
        this.add(user);
        return null;
    }


    public User getUserByPseudo(String pseudo) throws ExecutionException, InterruptedException {

        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
        nameValuePair.add(new BasicNameValuePair("pseudo",pseudo));
        getDataByPseudo = new GetDataByPseudoAsyncTask();
        getDataByPseudo.execute(nameValuePair);
        User user = getDataByPseudo.get();
        return user;
    }




    private class GetDataByPseudoAsyncTask extends  AsyncTask<List<NameValuePair>, Void, User> {
        @Override
        protected void onPreExecute() {
            Log.i("add", "onPreExecute");
            super.onPreExecute();
            //progressDialog.show();
        }

        @Override
        protected User doInBackground(List<NameValuePair>... params) {
            Log.i("add", " start doInBackground");
            ServiceHandler sh = new ServiceHandler();

            List<NameValuePair> param = params[0];

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(urlGetByPseudo, ServiceHandler.POST,param);

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
                        User user = new User(jsonObj.getString("pseudo"),jsonObj.getString("email"),jsonObj.getString("password"));
                        user.setFirstName(jsonObj.getString("first_name"));
                        user.setLastName(jsonObj.getString("last_name"));
                        user.setGender(Boolean.valueOf(jsonObj.getString("gender")));
                        user.setBirthDate(jsonObj.getString("birth_date"));

                        ArrayList<String> friends = new ArrayList<>();
                        JSONArray dataFR = jsonObj.getJSONArray("FR");
                        for(int k=0;k<dataFR.length();k++){
                            JSONObject value = dataFR.getJSONObject(k);
                            friends.add(value.getString("pseudo_friend"));
                        }
                        FriendsList friendsList = new FriendsList();
                        friendsList.setWho(user.getPseudo());
                        friendsList.setFriends(friends);
                        friendsList.setNbFriends(friends.size());
                        user.setFriendsList(friendsList);

                        ArrayList<FriendRequest> friendRequests = new ArrayList<>();
                        JSONArray dataRQ = jsonObj.getJSONArray("RQ");
                        for(int k=0;k<dataRQ.length();k++){
                            JSONObject value = dataRQ.getJSONObject(k);
                            friendRequests.add(new FriendRequest(value.getString("request_sender"), value.getString("request_recipient"), value.getString("request_message")));
                        }

                        BaseInbox baseInbox = new BaseInbox();
                        BaseTrash baseTrash = new BaseTrash();

                        ArrayList<Mail> inboxMails = baseInbox.getInboxByPseudoUser(user.getPseudo());
                        ArrayList<Mail> trashMails = baseTrash.getTrashByPseudoUser(user.getPseudo());
                        Mailbox mailbox = new Mailbox();
                        mailbox.setInbox(inboxMails);
                        mailbox.setTrash(trashMails);
                        mailbox.setRequests(friendRequests);
                        user.setMailBox(mailbox);

                        return user;
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
        protected void onPostExecute(User result) {
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
