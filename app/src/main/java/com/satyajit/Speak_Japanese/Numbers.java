package com.satyajit.Speak_Japanese;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Numbers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Numbers extends Fragment {

    /** handles playback of all sound files */
    private MediaPlayer player;
    /** handles audio focus while playing a audio */
    public AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListner =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Permanent loss of audio focus
                        // Pause playback immediately
                        player.pause();
                        //start from the beginning
                        player.seekTo(0);
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // The AUDIOFOCUS_LOSS means we have lost audio focus and stop playback and clean up resources
                        releaseMediaPlayer();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Your app has been granted audio focus again
                        player.start();
                    }
                }
            };

    /**
     * this is a listner which gets triggered when the {@Link MediaPlayer} has
     * completed playing audio file.
     */
    private final MediaPlayer.OnCompletionListener mCompletionListner = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        // When the activity is stopped release the MediaPlayer resources
        // because we wont be plying any more sound
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer(){
        // If the media player is not null, then it may be currently playing a sound.
        if (player != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            player.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            player = null;

            // Abandon audio focus when playback complete
            audioManager.abandonAudioFocus(onAudioFocusChangeListner);
        }
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Numbers() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Numbers.
     */
    // TODO: Rename and change types and number of parameters
    public static Numbers newInstance(String param1, String param2) {
        Numbers fragment = new Numbers();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.words_list, container, false);

        //creating an instance of audio manager
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<>();
        /*words.add("one");*/
        /**
         * Word w = new Word("one","ichi");
         *
         * without creating any variable we can also create object like this
         **/

        words.add(new Word("One", "ichi", R.drawable.one, R.raw.one));
        words.add(new Word("Two", "ni", R.drawable.two, R.raw.two));
        words.add(new Word("Three", "san", R.drawable.three, R.raw.three));
        words.add(new Word("Four", "shi", R.drawable.four, R.raw.four));
        words.add(new Word("Five", "go", R.drawable.five, R.raw.five));
        words.add(new Word("Six", "roku", R.drawable.six, R.raw.six));
        words.add(new Word("Seven", "shichi", R.drawable.seven, R.raw.seven));
        words.add(new Word("Eight", "hachi", R.drawable.eight, R.raw.eight));
        words.add(new Word("Nine", "yuu", R.drawable.nine, R.raw.nine));
        words.add(new Word("Ten", "juu", R.drawable.ten, R.raw.ten));

        WordAdapter adapter = new WordAdapter(getActivity(), words);


        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(adapter);
        // Set a click listener to play the audio when the list item is clicked on
        // it includes Anonymous class
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // get the {@link word} object at the given position the user clicked
                Word word = words.get(position);

                //release the MediaPlayer if it currently exist because we are about to play different audio file.
                releaseMediaPlayer();

                // Request audio focus for playback
                int result = audioManager.requestAudioFocus(onAudioFocusChangeListner,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    //factory method to call constructor
                    player = MediaPlayer.create(getActivity(), word.getAudioResourceId());
                    // Start the audio file
                    player.start();

                    //set up a listner on the MediaPlayer so that we can stop and release the MediaPlayer once the
                    //sound has finished.
                    player.setOnCompletionListener(mCompletionListner);
                    /* mCompletionListner is anonymous class created globally */
                }
            }
        });
        return rootView;
    }

}