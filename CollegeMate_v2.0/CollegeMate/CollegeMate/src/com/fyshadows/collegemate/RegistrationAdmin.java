package com.fyshadows.collegemate;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.android.gcm.GCMRegistrar;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.provider.MediaStore;

public class RegistrationAdmin extends ActionBarActivity {
	
	// Global Declaration
	    private int serverResponseCode = 0;
	    private ProgressDialog dialog = null;
	    private String imagepath = null;
	    private static final int SELECT_PHOTO = 100;
	   
	    private String user_id=null;
	    
	    String	name  = null; 
		String	phone = null; 
		String	email  = null; 
		String	dob  = null; 
		String	Cityin = null; 
		String role= null;
		 String regId=null;
		
		  String Selected_sex="Male";
		  
		 
		
		
		 
	    Collegemate_DB db = new Collegemate_DB(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db.getWritableDatabase();
		setContentView(R.layout.activity_registration_admin);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		 if (android.os.Build.VERSION.SDK_INT > 9) {
	            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	            StrictMode.setThreadPolicy(policy);
	          }
		//Get the bundle--This contains the role selected in previous activity
		  
		    //Extract the data needed
		 Bundle bundle = getIntent().getExtras();
		     role = bundle.getString("role"); 
		    
		     
		   
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration_admin, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_registration_admin, container, false);
			return rootView;
		}
	}
	
	
	public void register(View view) {
		
	    Bitmap bmImg = null;
	    InputStream inputStream = null ;
		RadioGroup rg1=(RadioGroup)findViewById(R.id.radioGroup1);
		//Getting the user input and storing into variables
		name   = ((EditText)findViewById(R.id.editText_name)).getText().toString();
		phone = ((EditText)findViewById(R.id.editText_phone)).getText().toString();
		email   = ((EditText)findViewById(R.id.editText_email)).getText().toString();
		dob = ((EditText)findViewById(R.id.editText_age)).getText().toString();
		Cityin=((EditText)findViewById(R.id.editText_cityIn)).getText().toString();
		if(rg1.getCheckedRadioButtonId()!=-1){
		    int id= rg1.getCheckedRadioButtonId();
		    View radioButton = rg1.findViewById(id);
		    int radioId = rg1.indexOfChild(radioButton);
		    RadioButton btn = (RadioButton) rg1.getChildAt(radioId);
		     Selected_sex = (String) btn.getText();
		}
		 regId = GCMRegistrar.getRegistrationId(this);
		try {
	        String link =  "http://fyshadows.com/CollegeMate/collegemate_adminregistration.php?name="+name+"&phone="+phone+"&email="+email+"&dob="+dob+"&sex="+Selected_sex+"&role="+role+"&Cityin="+Cityin+"&regid="+regId;
	        Log.i("a",link);
	        HttpClient client = new DefaultHttpClient();
	        HttpGet request = new HttpGet();
	        request.setURI(new URI(link)); 
	        HttpResponse response = client.execute(request); 
	        BufferedReader in = new BufferedReader
	                (new InputStreamReader(response.getEntity().getContent()));

	                StringBuffer sb = new StringBuffer("");
	                String line="";
	                while ((line = in.readLine()) != null) {
	                   sb.append(line);
	                   break;
	                 }
	                 in.close();
	                 user_id=sb.toString();
	               
	       
	        Toast.makeText(RegistrationAdmin.this, "Registration successfull" + sb.toString(),Toast.LENGTH_LONG).show();
	    }catch(Exception e){
	        e.printStackTrace();
	    	Toast.makeText(RegistrationAdmin.this, "Registration Unsuccessfull",Toast.LENGTH_LONG).show();
	     }
		
	 
		
// TO execute image upload in back ground
new AsyncTaskEx().execute();
Intent i = new Intent(this,AdminCollegeregistraton.class );
Bundle bundle = new Bundle();
//Add your data to bundle
bundle.putString("user_id", user_id);  
//Add the bundle to the intent
i.putExtras(bundle);

//Fire that second activity
 startActivity(i);  
	}
	
//For selecting the image from gallery
	public void SelectImage(View view) {
		//Intent intent = new Intent();
        //intent.setType("image/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        //startActivityForResult(
          //      Intent.createChooser(intent, "Complete action using"), 1);
		Intent sdintent = new Intent(Intent.ACTION_PICK);
		sdintent.setType("image/*");
		startActivityForResult(sdintent, SELECT_PHOTO); 

	}
	
	 @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 super.onActivityResult(requestCode, resultCode, data); 
Log.i("a","i am into acitvity result");
	        if (resultCode == RESULT_OK) {
	            // Bitmap photo = (Bitmap) data.getData().getPath();
	        	Log.i("a","Got the result code");
	            Uri selectedImageUri = data.getData();
	             imagepath = getPath(selectedImageUri);
	             Log.i("a",imagepath);
	            Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
	            ImageView imageview = (ImageView)findViewById(R.id.imageView1);
	            imageview.setImageBitmap(bitmap);
	            
//storing image as bitmap
	            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

	            //you can create a new file name "test.jpg" in sdcard folder.
	            File f = new File(Environment.getExternalStorageDirectory()+ "/collegemate/Profilepic"
                        + File.separator + "myprofpic.jpg");
	          
	            try {
					f.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	          
	            //write the bytes in file
	            FileOutputStream fo=null;
				try {
					fo = new FileOutputStream(f);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            try {
					fo.write(bytes.toByteArray());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	            // remember close de FileOutput
	            try {
					fo.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	           
	            
	        }
	    }
	           
	
	public String getPath(Uri uri) {
	        String[] projection = { MediaStore.Images.Media.DATA };
			@SuppressWarnings("deprecation")
			Cursor cursor = managedQuery(uri, projection, null, null, null);
	        int column_index = cursor
	                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        cursor.moveToFirst();
	        return cursor.getString(column_index);
	    }
	           
// This task will be called to store the images in back ground	
	private class AsyncTaskEx extends AsyncTask<Void, Void, Void> {
		
		protected void  onPreExecute(){
			
			
			
			Log.d("a","into async pre execute");
			db.registeradmin(new Masterusertable(user_id,name,dob,Selected_sex,email,phone,Cityin,role)); 
			
		
			
		}
		

	    /** The system calls this to perform work in a worker thread and
	    * delivers it the parameters given to AsyncTask.execute() */
	    protected Void doInBackground(Void... arg0) {
	    	Log.d("a","into background");
	    	Log.i("c","into background");
	    	uploadFile(imagepath);
	        return null;
	    }
	}
	public int uploadFile(String sourceFileUri) {

        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            dialog.dismiss();

            Log.e("uploadFile", "Source File not exist :" + imagepath);

            runOnUiThread(new Runnable() {
                public void run() {
                   
                }
            });

            return 0;

        } else {
            try {

                // open a URL connection to the 
                FileInputStream fileInputStream = new FileInputStream(
                        sourceFile);
                //URl which is used to upload the image and store the path in DB
       		String upLoadServerUri = "http://fyshadows.com/CollegeMate/Collegemate_Imageupload.php?title=" + user_id;
                URL url = new URL(upLoadServerUri);
                Log.i("a",upLoadServerUri);

                // Open a HTTP connection to the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
               // conn.setChunkedStreamingMode(maxBufferSize);
              conn.setRequestProperty("ENCTYPE", "multipart/form-data");
              conn.setRequestProperty("userid", user_id);
                conn.setRequestProperty("Content-Type",
                        "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);
               // conn.setRequestProperty("uploadname", user_id);
            Log.i("s",fileName);
                dos = new DataOutputStream(conn.getOutputStream());

Log.i("c","1");
                
                
               //to send image
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd );
                dos.writeBytes(lineEnd);
                


                //sending text with image
              //  dos.writeBytes(twoHyphens + boundary + lineEnd);
               //dos.writeBytes("Content-Disposition: form-data; name=\"title\""  + lineEnd);
                //dos.writeBytes(lineEnd);
                //dos.writeBytes("h");
               // dos.writeBytes(lineEnd);
               // dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                
                
Log.i("c","2");
                // create a buffer of maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                Log.i("c","3");
                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                fileInputStream.close();
                
                //to send text
                Log.i("a",user_id);
              
              
            
                

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {

                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(RegistrationAdmin.this,
                                    "File Upload Complete.", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });
                }

                // close the streams //
              
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                dialog.dismiss();
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                  
                        Toast.makeText(RegistrationAdmin.this,
                                "MalformedURLException", Toast.LENGTH_SHORT)
                                .show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                //dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                    	
                        Toast.makeText(RegistrationAdmin.this,
                                "Got Exception : see logcat ",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload file to server Exception",
                        "Exception : " + e.getMessage(), e);
            }
          //  dialog.dismiss();
            return serverResponseCode;

        } // End else block
    }
	    
	}
	
	
	
	


