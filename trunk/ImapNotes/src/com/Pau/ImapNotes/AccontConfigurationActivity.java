package com.Pau.ImapNotes;

import com.Pau.ImapNotes.Data.ConfigurationFile;
import com.Pau.ImapNotes.Miscs.Imaper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AccontConfigurationActivity extends Activity {
	
	private ConfigurationFile settings;
	private Imaper imapFolder;
	
	private TextView usernameTextView;
	private TextView passwordTextView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_selection);
        this.usernameTextView = (TextView)findViewById(R.id.usernameEdit);
        this.passwordTextView = (TextView)findViewById(R.id.passwordEdit);
        
        this.settings = ((ImapNotes)getApplicationContext()).GetConfigurationFile();
        this.imapFolder = ((ImapNotes)getApplicationContext()).GetImaper();
        
        this.usernameTextView.setText(this.settings.GetUsername()==null ? "you@gmail.com" : this.settings.GetUsername());
        this.passwordTextView.setText(this.settings.GetPassword()==null ? "your password" : this.settings.GetPassword());
        
	
	}
	
	public void DoLogin(View v){
		ProgressDialog loadingDialog = ProgressDialog.show(this, "ImapNotes" , "Logging in to your Gmail account... ", true);

		this.settings.SetUsername(this.usernameTextView.getText().toString());
		this.settings.SetPassword(this.passwordTextView.getText().toString());
		
        try {
			this.imapFolder.ConnectToProvider(this.settings.GetUsername(), this.settings.GetPassword());
			Toast.makeText(this, "Connected!", Toast.LENGTH_SHORT).show();
			this.settings.SaveConfigurationToXML();
			this.finish();
        } catch (Exception e) {
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
			Log.v("ImapNotes", e.getMessage());
		} finally {
			loadingDialog.dismiss();
		}
				
	}
	
}
