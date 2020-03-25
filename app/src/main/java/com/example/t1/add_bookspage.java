package com.example.t1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.example.t1.RetrofitApis.ApiInterface;
import com.example.t1.RetrofitApis.RetrofitClient;
import com.example.t1.RetrofitRegisPage.Getregisformat;
import com.example.t1.Retrofitaddbooks.Sendadbkformat;
import com.google.android.gms.vision.barcode.Barcode;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_bookspage extends AppCompatActivity {
    private Toolbar mtoolbar;
    public CircularProgressButton btn;
    private EditText bkname,bkauth,bkisb;
    private Barcode barcodeResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bookspage);
        mtoolbar=(Toolbar)findViewById(R.id.add_pagetoolbar);
        bkname=findViewById(R.id.adbkname);
        bkauth=findViewById(R.id.adbkauthor);
        bkisb=findViewById(R.id.adbkisbn);
        btn= (CircularProgressButton) findViewById(R.id.addbkbtn);
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName("Pritam").withEmail("pkspritam10@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile_photo))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();



        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Profile");
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("Transactions History");
        PrimaryDrawerItem item3=new PrimaryDrawerItem().withIdentifier(3).withName("Logout");


        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mtoolbar)
                .withTranslucentStatusBar(false)
                .withActionBarDrawerToggle(true)
                .withSavedInstance(savedInstanceState)
                .withDisplayBelowStatusBar(false)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item1,item2,item3

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D

                        switch (position)
                        {
                            case 1:
                                //pass token for getting user details
                                break;
                            case 2:

                                break;
                            case 3:
                                //  logout();

                                //during logout activity ends
                                //clear shared preferences
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("Secrets", MODE_PRIVATE);
                                SharedPreferences.Editor editor=pref.edit();
                                editor.clear();
                                editor.apply();

                                startActivity(new Intent(add_bookspage.this,Loginpage.class));
                                finish();
                                break;

                        }
                        return true;
                    }
                })
                .build();
        //NAVIGATION DRAWER ENDS


    }


    public void scanbarclick(View view) {
        startScan();

    }


    public void subaddbkclick(View view) {
      btn.startAnimation();
      String bname=bkname.getText().toString();
      String bauthor=bkauth.getText().toString();
      String bisbn=bkisb.getText().toString();

      if(bname.isEmpty()||bauthor.isEmpty()||bisbn.isEmpty())
      {
          Toasty.warning(add_bookspage.this,"All details are mandatory",Toasty.LENGTH_SHORT).show();
          btn.revertAnimation();
      }
      else
      {
          SharedPreferences sharedPreferences=getSharedPreferences("Secrets",MODE_PRIVATE);
          String currenttoken=sharedPreferences.getString("token","");
         //backend starts
          ApiInterface apiInterface= RetrofitClient.getClient().create(ApiInterface.class);
          Sendadbkformat senddet=new Sendadbkformat(bisbn,bname,bauthor);
          Call<Getregisformat> c=apiInterface.addbookdetails(senddet,currenttoken);
          c.enqueue(new Callback<Getregisformat>() {
              @Override
              public void onResponse(Call<Getregisformat> call, Response<Getregisformat> response) {
                  if(response.isSuccessful())
                  {
                      btn.revertAnimation();

                      Toasty.success(add_bookspage.this, response.body().getMsg(), Toast.LENGTH_SHORT, true).show();

                      startActivity(new Intent(add_bookspage.this,Homepage.class));
                      finish();


                  }
              }

              @Override
              public void onFailure(Call<Getregisformat> call, Throwable t) {
                  Toasty.success(add_bookspage.this, t.getMessage(), Toast.LENGTH_SHORT, true).show();
                  btn.revertAnimation();
              }
          });


         //backend ends


      }




    }
    private void startScan() {
        /**
         * Build a new MaterialBarcodeScanner
         */
        final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                .withActivity(add_bookspage.this)
                .withEnableAutoFocus(true)
                .withBleepEnabled(true)
                .withBackfacingCamera()
                .withText("Scanning...")
                .withResultListener(new MaterialBarcodeScanner.OnResultListener() {

                    @Override
                    public void onResult(Barcode barcode) {
                        barcodeResult = barcode;
                        // result.setText(barcode.rawValue);
                       // Toast.makeText(add_bookspage.this, barcode.rawValue, Toast.LENGTH_SHORT).show();
                        //setting value to edit text field
                        bkisb.setText(barcode.rawValue);
                    }

                })
                .build();
        materialBarcodeScanner.startScan();
    }


}
