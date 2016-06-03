package com.android.nova.uob_ot.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.nova.uob_ot.R;
import com.android.nova.uob_ot.activity.TrainDetailedActivity;
import com.android.nova.uob_ot.model.Trains;

import java.util.ArrayList;


public class CustomListviewAdapter extends ArrayAdapter<Trains> {
    private LayoutInflater inflater;
    private ArrayList<Trains> data;
    private Activity mContext;
    private int layoutResourceId;



    public CustomListviewAdapter(Activity context, int resource, ArrayList<Trains> objs) {
        super(context, resource, objs);
        data = objs;
        mContext = context;
        layoutResourceId = resource;
    }



    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getPosition(Trains item) {
        return super.getPosition(item);
    }

    @Override
    public Trains getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return data.size();
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View row = convertView;
        ViewHolder viewHolder = null;

        if ( row == null) {

             inflater = LayoutInflater.from(mContext);
            row = inflater.inflate(layoutResourceId, parent, false);

            viewHolder = new ViewHolder();


            //Get references to views

            viewHolder.arrivalStation = (TextView) row.findViewById(R.id.arrivalText);
            viewHolder.from = (TextView) row.findViewById(R.id.fromText);
            viewHolder.to = (TextView) row.findViewById(R.id.toText);
            viewHolder.duration = (TextView) row.findViewById(R.id.durationText);
            viewHolder.freq = (TextView) row.findViewById(R.id.freqText);
            viewHolder.type = (ImageView) row.findViewById(R.id.t_type);

            row.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) row.getTag();
        }

        viewHolder.train = data.get(position);

        //display the data



        viewHolder.arrivalStation.setText(viewHolder.train.getArrivalTime());
        viewHolder.from.setText(viewHolder.train.getStartStationName());
        viewHolder.to.setText(viewHolder.train.getEndStationName());
        viewHolder.duration.setText(viewHolder.train.getArrivalTimeEndSt());
        viewHolder.freq.setText(viewHolder.train.getFreq());


        if (viewHolder.train.getTrainType().equals("Slow")){

            viewHolder.type.setImageResource(R.drawable.ic_slow);

            Log.v("AAS","viewHolder.train.getTrainType()");

        }
        if (viewHolder.train.getTrainType().equals("Semi-Express")){

            viewHolder.type.setImageResource(R.drawable.ic_ex_s);

            Log.v("AAS","viewHolder.train.getTrainType()");

        }
        if (viewHolder.train.getTrainType().equals("Express")){

            viewHolder.type.setImageResource(R.drawable.ic_exp);

            Log.v("AAS","viewHolder.train.getTrainType()");

        }

        final ViewHolder finalViewHolder = viewHolder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext,TrainDetailedActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("trainObj", finalViewHolder.train);
                i.putExtras(mBundle);
                mContext.startActivity(i);

            }
        });

        return row;
    }


    public class ViewHolder {
        Trains train;
        TextView arrivalStation;
        TextView from;
        TextView to;
        TextView duration;
        TextView freq;
        ImageView type;



    }


}
