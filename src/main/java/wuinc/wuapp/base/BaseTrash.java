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

import wuinc.wuapp.CustomProgressDialog;
import wuinc.wuapp.Mail;
import wuinc.wuapp.ServiceHandler;

/**
 * Created by greg on 15/06/2016.
 */
public class BaseTrash extends Activity {

    private CustomProgressDialog progressDialog;
    private AddDataAsyncTask AddData;
    private GetDataAsyncTask getData;
    private GetDataByIdAsyncTask getDataById;
    private SuppDataAsyncTask suppData;
    private GetDataByPathAsyncTask getDataByPath;
    private int success;
    private String message;
    private String urlAdd="http://192.168.43.233/base_trash/ajout_trash.php";
    private String urlSupp="http://192.168.43.233/base_trash/suppression_trash.php";
    private String urlGet="http://192.168.43.233/base_trash/affichage_trash.php";
    private String urlGetByPseudoUser="http://192.168.43.233/base_trash/getByPseudoUser_trash.php";
    private String urlGetById="http://192.168.43.233/base_trash/getById_trash.php";
    private ArrayList<Mail> mails;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");


    public BaseTrash() {
        super();
    }

    public Mail add(Mail mail) throws ParseException, ExecutionException, InterruptedException {
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
        nameValuePair.add(new BasicNameValuePair("trash_message", mail.getMessage()));
        nameValuePair.add(new BasicNameValuePair("trash_subject", mail.getSubject()));
        nameValuePair.add(new BasicNameValuePair("trash_date_envoi", mail.getDateEnvoi()));
        nameValuePair.add(new BasicNameValuePair("trash_date_reception", mail.getDateReception()));
        nameValuePair.add(new BasicNameValuePair("trash_sender", mail.getSender().getPseudo()));

        for (int i=0 ;i< mail.getRecipients().size();i++) {
            nameValuePair.add(new BasicNameValuePair("trash_recipient[]", mail.getRecipients().get(i).getPseudo()));
        }

        AddData =new AddDataAsyncTask();
        AddData.execute(nameValuePair);
        mail.setId(AddData.get());
        return mail;
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


    public void delete(Mail mail){
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
        nameValuePair.add(new BasicNameValuePair("trash_id",String.valueOf(mail.getId())));
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


    public ArrayList<Mail> returnAll() throws ExecutionException, InterruptedException {

        getData=new GetDataAsyncTask();
        getData.execute();
        this.mails = getData.get();
        return mails;

    }




    private class GetDataAsyncTask extends  AsyncTask<Void, Void, ArrayList<Mail>> {
        @Override
        protected void onPreExecute() {
            Log.i("add", "onPreExecute");
            super.onPreExecute();
            //progressDialog.show();
        }

        @Override
        protected ArrayList<Mail> doInBackground(Void... params) {
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
                        ArrayList<Mail> allMail = new ArrayList<Mail>();
                        // success=1 ==> there is an array of data = valeurs
                        JSONArray dataValues = jsonObj.getJSONArray("valeurs");
                        // loop each row in the array
                        for(int j=0;j<dataValues.length();j++)
                        {
                            JSONObject values = dataValues.getJSONObject(j);

                            ArrayList<String> recipients = new ArrayList<>();
                            JSONArray dataRE = values.getJSONArray("IR");

                            for(int k=0;k<dataRE.length();k++){
                                JSONObject value = dataRE.getJSONObject(k);
                                recipients.add(value.getString("trash_recipient"));
                            }

                            Mail mail = new Mail(null,null);
                            mail.setId(Integer.valueOf(values.getString("trash_id")));
                            mail.setSenderS(values.getString("trash_sender"));
                            mail.setMessage(values.getString("trash_message"));
                            mail.setSubject(values.getString("trash_subject"));
                            mail.setDateReception(values.getString("trash_date_reception"));
                            mail.setDateEnvoi(values.getString("trash_date_envoi"));
                            mail.setRecipientsS(recipients);

                            allMail.add(mail);
                        }
                        return allMail;
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
        protected void onPostExecute(ArrayList<Mail> result) {
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


    public Mail update(Mail mail) throws ParseException, ExecutionException, InterruptedException {

        this.delete(mail);
        Mail newMail = this.add(mail);
        return newMail;

    }



    public ArrayList<Mail> getTrashByPseudoUser(String pseudo) throws ExecutionException, InterruptedException {

        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
        nameValuePair.add(new BasicNameValuePair("pseudo",pseudo));
        getDataByPath = new GetDataByPathAsyncTask();
        getDataByPath.execute(nameValuePair);
        this.mails = getDataByPath.get();
        return this.mails;
    }




    private class GetDataByPathAsyncTask extends  AsyncTask<List<NameValuePair>, Void, ArrayList<Mail>> {
        @Override
        protected void onPreExecute() {
            Log.i("add", "onPreExecute");
            super.onPreExecute();
            //progressDialog.show();
        }

        @Override
        protected ArrayList<Mail> doInBackground(List<NameValuePair>... params) {
            Log.i("add", " start doInBackground");
            ServiceHandler sh = new ServiceHandler();

            List<NameValuePair> param = params[0];

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(urlGetByPseudoUser, ServiceHandler.POST,param);

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
                        ArrayList<Mail> allMail = new ArrayList<Mail>();
                        // success=1 ==> there is an array of data = valeurs
                        JSONArray dataValues = jsonObj.getJSONArray("valeurs");
                        // loop each row in the array
                        for(int j=0;j<dataValues.length();j++)
                        {
                            JSONObject values = dataValues.getJSONObject(j);

                            ArrayList<String> recipients = new ArrayList<>();
                            JSONArray dataRE = values.getJSONArray("IR");

                            for(int k=0;k<dataRE.length();k++){
                                JSONObject value = dataRE.getJSONObject(k);
                                recipients.add(value.getString("trash_recipient"));
                            }

                            Mail mail = new Mail(null,null);
                            mail.setId(Integer.valueOf(values.getString("trash_id")));
                            mail.setSenderS(values.getString("trash_sender"));
                            mail.setMessage(values.getString("trash_message"));
                            mail.setSubject(values.getString("trash_subject"));
                            mail.setDateReception(values.getString("trash_date_reception"));
                            mail.setDateEnvoi(values.getString("trash_date_envoi"));
                            mail.setRecipientsS(recipients);

                            allMail.add(mail);
                        }
                        return allMail;
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
        protected void onPostExecute(ArrayList<Mail> result) {
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


    public Mail getTrashById(int id) throws ExecutionException, InterruptedException {

        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
        nameValuePair.add(new BasicNameValuePair("trash_id",String.valueOf(id)));
        getDataById = new GetDataByIdAsyncTask();
        getDataById.execute(nameValuePair);
        Mail mail = getDataById.get();
        return mail;
    }




    private class GetDataByIdAsyncTask extends  AsyncTask<List<NameValuePair>, Void, Mail> {
        @Override
        protected void onPreExecute() {
            Log.i("add", "onPreExecute");
            super.onPreExecute();
            //progressDialog.show();
        }

        @Override
        protected Mail doInBackground(List<NameValuePair>... params) {
            Log.i("add", " start doInBackground");
            ServiceHandler sh = new ServiceHandler();

            List<NameValuePair> param = params[0];

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(urlGetById, ServiceHandler.POST,param);

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

                        ArrayList<String> recipients = new ArrayList<>();
                        JSONArray dataRE = jsonObj.getJSONArray("IR");

                        for(int k=0;k<dataRE.length();k++){
                                JSONObject value = dataRE.getJSONObject(k);
                                recipients.add(value.getString("trash_recipient"));
                        }

                        Mail mail = new Mail(null,null);
                        mail.setId(Integer.valueOf(jsonObj.getString("trash_id")));
                        mail.setSenderS(jsonObj.getString("trash_sender"));
                        mail.setMessage(jsonObj.getString("trash_message"));
                        mail.setSubject(jsonObj.getString("trash_subject"));
                        mail.setDateReception(jsonObj.getString("trash_date_reception"));
                        mail.setDateEnvoi(jsonObj.getString("trash_date_envoi"));
                        mail.setRecipientsS(recipients);

                        return mail;
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
        protected void onPostExecute(Mail result) {
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
