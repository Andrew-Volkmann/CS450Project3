package com.example.andrew.cs450project3;



import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {
    static private int imageButtonIDs[] = {R.id.image1, R.id.image2, R.id.image3, R.id.image4, R.id.image5,
            R.id.image6, R.id.image7, R.id.image8, R.id.image9, R.id.image10,
            R.id.image11, R.id.image12, R.id.image13, R.id.image14, R.id.image15, R.id.image16,};

    private final int NUMBER_OF_IMAGES = 12;
    private int pictuers_shown;
    SoundPool sp = null;
    private int horse = 0;
    private int correct =0;
    private int wrong =0;


    private int pictures_correct;
    private Handler myHandler = new Handler();

    static private List<Drawable> images_used = new ArrayList<Drawable>();
    static private List<Drawable> im = new ArrayList<Drawable>();

    static private HashMap<Drawable, Boolean> map_turned = new HashMap<Drawable, Boolean>();
    static private HashMap<Drawable, ArrayList<Integer>> map_total = new HashMap<Drawable, ArrayList<Integer>>();
    static private HashMap<String, Integer> drawable_strings_and_ids = new HashMap<String, Integer>();


    static private ArrayList<Drawable> comparedImages= new ArrayList<Drawable>();
    static private ArrayList<Integer> comparedImagesButtons = new ArrayList<Integer>();
    static private Integer tries=0;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        View root = inflater.inflate(R.layout.fragment_game, container, false);
        tries=0;
        images_used.clear();
        im.clear();
        map_total.clear();
        map_turned.clear();
        drawable_strings_and_ids.clear();
        getImages();
        initView(root);
        log_pictures();
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        this.sp = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        this.horse = this.sp.load(getActivity(), R.raw.horse, 1);
        this.correct = this.sp.load(getActivity(), R.raw.correct, 2);
        this.wrong = this.sp.load(getActivity(),R.raw.wrong, 3);

    }

    private void getImages() {
        int resourceID = 0;
        int image_number = 1;

        while (image_number <= NUMBER_OF_IMAGES) {

            resourceID = getResources().getIdentifier("image" + image_number, "drawable", "com.example.andrew.cs450project3");
            if (resourceID != 0) {
                Drawable d = getResources().getDrawable(resourceID);
                im.add(d);
                drawable_strings_and_ids.put(d.toString(), resourceID);
            }
            image_number++;
        }
        Collections.shuffle(im);
        for(int i=0; i<8; i++){
            images_used.add(im.get(i));
            images_used.add(im.get(i));
        }
        Collections.shuffle(images_used);

    }
    public void reset(){
        ((GameActivity) getActivity()).startThinking();
        tries = 0;
        final TextView count = (TextView) ((GameActivity)getActivity()).findViewById(R.id.count);
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                count.setText("0");
                for (int i = 0; i < map_turned.size(); i++) {
                    Drawable d = im.get(i);
                    if (!map_turned.get(d)) {

                        ImageButton ib_1 = (ImageButton) getView().findViewById(map_total.get(d).get(0));
                        ib_1.setImageDrawable(getResources().getDrawable(R.drawable.imagebackground));
                        ImageButton ib_2 = (ImageButton) getView().findViewById(map_total.get(d).get(1));
                        ib_2.setImageDrawable(getResources().getDrawable(R.drawable.imagebackground));

                    }
                }
                ((GameActivity) getActivity()).stopThinking();
            }

        }, 1000);

        images_used.clear();
        im.clear();
        map_total.clear();
        map_turned.clear();
        drawable_strings_and_ids.clear();
        getImages();
        initView(getView());
        log_pictures();
    }
    private void log_pictures(){
        for (int i = 0; i<imageButtonIDs.length; i++) {
            if (!map_turned.containsKey(images_used.get(i))) {
                ArrayList<Integer> temp = new ArrayList<Integer>();
                temp.add(imageButtonIDs[i]);
                map_turned.put(images_used.get(i), false);
                map_total.put(images_used.get(i), temp);
            }else{
                map_total.get(images_used.get(i)).add(imageButtonIDs[i]);
            }
        }
    }
    private void initView(View root){
        for(int i = 0; i<16; i++) {
            final int temp_i = i;
            final ImageButton ib = (ImageButton) root.findViewById(imageButtonIDs[temp_i]);
            final View root_f = root;
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pictuers_shown = pictuers_shown+1;
                    sp.play(horse, 1f, 1f, 1, 0, 1);


                    ib.setImageDrawable(images_used.get(temp_i));
                    ib.setClickable(false);

                    comparedImages.add(images_used.get(temp_i));
                    comparedImagesButtons.add(imageButtonIDs[temp_i]);

                    if(pictuers_shown%2==0 && pictuers_shown != 0) {
                        think(comparedImages, comparedImagesButtons, root_f);
                    }
                }
            });
        }
    }
    private void think(ArrayList<Drawable>temp_d, ArrayList<Integer> temp_i, View root){
        ((GameActivity) getActivity()).startThinking();
        final Drawable temp_d1 = temp_d.get(0);
        final Drawable temp_d2 = temp_d.get(1);

        tries = tries +1;
        final TextView count = (TextView) ((GameActivity)getActivity()).findViewById(R.id.count);


        final int temp_i1 = temp_i.get(0);
        final int temp_i2 = temp_i.get(1);
        final ImageButton ib_1 = (ImageButton) root.findViewById(temp_i1);
        final ImageButton ib_2 = (ImageButton) root.findViewById(temp_i2);

        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (temp_d1 != temp_d2) {
                    ib_1.setImageDrawable(getResources().getDrawable(R.drawable.imagebackground));
                    ib_1.setClickable(true);
                    ib_2.setImageDrawable(getResources().getDrawable(R.drawable.imagebackground));
                    ib_2.setClickable(true);
                    sp.play(correct, 1f, 1f, 1, 0, 1);
                } else {
                    sp.play(wrong, 1f, 1f, 1, 0, 1);
                    pictures_correct++;
                    map_turned.put(temp_d1, true);
                    ib_1.setClickable(false);
                    ib_2.setClickable(false);
                }
                count.setText(tries.toString());


                comparedImages.clear();
                comparedImagesButtons.clear();
                ((GameActivity) getActivity()).stopThinking();
            }
        }, 1000);
        System.out.println(pictures_correct);
        if(pictures_correct==7){
            Intent myIntent = new Intent(getActivity(), Winning_screen.class);
            myIntent.putExtra("intCount", tries);
            startActivity(myIntent);

        }


    }

    public String getState(){
        // get the drawable id and a true or false if it is turned over
        StringBuilder order = new StringBuilder();
        for(int i = 0; i<16; i++){

            order.append(drawable_strings_and_ids.get(images_used.get(i).toString()));
            order.append(",");
            order.append(map_turned.get(images_used.get(i)).toString());
            order.append(",");
        }
        order.append(tries);
        return order.toString();

    }
    public void putState(String data){
        String[] gameData=data.split(",");
        for (int i = 0; i<32; i++){



        }

    }

}
