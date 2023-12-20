package com.kdy.diary;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


public class MyStickerDialogFragment extends DialogFragment {


    int selectedSticker;


    public static Button  sticker_confident,sticekr_normal,sticker_happy,sticker_angry,sticker_bored,sticker_excited,sticekr_awesome,sticker_nervous,sticker_sad,sticker_sick,sticker_tired;
    public static Button sticker_choose,sticker_cancel;


    public static String TAG = "MyStickerDialogFragment";


    static SharedPreferences pref= null;
    static SharedPreferences.Editor editor = null;


    public interface OnfinishListener{
        void onStickerSelectListener(int stickerId);
    }

    private OnfinishListener mycallback;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater= requireActivity().getLayoutInflater();
        View v= inflater.inflate(R.layout.fragment_sticker, null);
        builder.setView(v);

        sticker_confident=v.findViewById(R.id.sticker_confident);
        sticekr_normal=v.findViewById(R.id.sticker_normal);
        sticker_happy=v.findViewById(R.id.sticker_happy);

        sticker_angry=v.findViewById(R.id.sticker_angry);
        sticker_bored=v.findViewById(R.id.sticker_bored);
        sticker_excited=v.findViewById(R.id.sticker_excited);

        sticekr_awesome=v.findViewById(R.id.sticker_awesome);
        sticker_nervous=v.findViewById(R.id.sticker_nervous);
        sticker_sad=v.findViewById(R.id.sticker_sad);

        sticker_sick=v.findViewById(R.id.sticker_sick);
        sticker_tired=v.findViewById(R.id.sticker_tired);


        sticker_choose=v.findViewById(R.id.sticker_choose);
        sticker_cancel=v.findViewById(R.id.sticker_cancel);

        Button[] stickerset= {sticker_confident,sticekr_normal,sticker_happy,sticker_angry,sticker_bored,sticker_excited,sticekr_awesome,sticker_nervous,sticker_sad,sticker_sick,sticker_tired};

        for(int i =0;i<stickerset.length; i++){
            stickerset[i].setOnClickListener(click);
        }


        sticker_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mycallback.onStickerSelectListener(selectedSticker);
                MyStickerDialogFragment.this.getDialog().dismiss();
            }
        });
        sticker_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedSticker=R.drawable.sticker_normal;
                mycallback.onStickerSelectListener(selectedSticker);
                MyStickerDialogFragment.this.getDialog().dismiss();
            }
        });
/*
        pref= getContext().getSharedPreferences("pref",Context.MODE_PRIVATE);
        editor = pref.edit();*/

        return builder.create();

    }



    View.OnClickListener click= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch( v.getId()){
                case R.id.sticker_confident:
                    selectedSticker= R.drawable.sticker_confident;
                    break;
                case R.id.sticker_normal:
                    selectedSticker= R.drawable.sticker_normal;
                    break;
                case R.id.sticker_happy:
                    selectedSticker=  R.drawable.sticker_happy;
                    break;

                case R.id.sticker_angry:
                    selectedSticker=  R.drawable.sticker_angry;
                    break;
                case R.id.sticker_bored:
                    selectedSticker=  R.drawable.sticker_bored;
                    break;
                case R.id.sticker_excited:
                    selectedSticker=  R.drawable.sticker_excited;
                    break;

                case R.id.sticker_awesome:
                    selectedSticker=  R.drawable.sticker_awesome;
                    break;
                case R.id.sticker_nervous:
                    selectedSticker=  R.drawable.sticker_nervous;
                    break;
                case R.id.sticker_sad:
                    selectedSticker=  R.drawable.sticker_sad;
                    break;

                case R.id.sticker_sick:
                    selectedSticker=  R.drawable.sticker_sick;
                    break;
                case R.id.sticker_tired:
                    selectedSticker=  R.drawable.sticker_tired;
                    break;
            }
/*

            editor.putInt("sticker_tag", selectedSticker);
            editor.commit();
*/

        }
    };


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mycallback=(OnfinishListener) context;
        }catch (ClassCastException e){
            Log.d("Sticker", "Activity doesn't implement the OnCompleteListener interface");
        }
    }
}