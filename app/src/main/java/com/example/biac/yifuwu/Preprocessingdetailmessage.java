package com.example.biac.yifuwu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import Pojo.PreDealResults;

public class Preprocessingdetailmessage extends Activity {


    private PreDealResults preDealResults;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_preprocessingdetailmessage);

        preDealResults = (PreDealResults)getIntent().getParcelableExtra("PreDealResults");

        textView = (TextView)findViewById(R.id.preprocessingmessage);

        //textView.setText(preDealResults.getPre_deal_results());

    }

}
