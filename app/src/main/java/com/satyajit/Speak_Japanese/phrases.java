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
 * Use the {@link phrases#newInstance} factory method to
 * create an instance of this fragment.
 */
public class phrases extends Fragment {

    MediaPlayer player;

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

    public phrases() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment phrases.
     */
    // TODO: Rename and change types and number of parameters
    public static phrases newInstance(String param1, String param2) {
        phrases fragment = new phrases();
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
         * Word w = new Word("one","lutti");
         *
         * without creating any variable we can also create object like this
         **/

        words.add(new Word("where are you going?","Doko ni iku no?",R.raw.phrase2));
        words.add(new Word("What is your name?","Namae wa nani?",R.raw.phrase1));
        words.add(new Word("My name is...","Watashinonamaeha...",R.raw.phrase3));
        words.add(new Word("How are you feeling?","Go kibun wa ikagadesu ka?",R.raw.phrase4));
        words.add(new Word("I’m feeling good.","Watashi wa yoi kibundesu.",R.raw.phrase5));
        words.add(new Word("Are you coming?","Kimasu ka?",R.raw.phrase6));
        words.add(new Word("Yes, I’m coming.","Hai, kimasu.",R.raw.phrase7));
        words.add(new Word("I’m coming.","Ima okonatteru.",R.raw.phrase8));
        words.add(new Word("Let’s go.","Sāikō.",R.raw.phrase9));
        words.add(new Word("Come here.","Koko ni kite.",R.raw.phrase10));
        words.add(new Word("Hello,how are you?","Kon'nichiwa genkidesu ka?",R.raw.phrase11));
        words.add(new Word("I'm fine","Watashi wa genki",R.raw.phrase12));
        words.add(new Word("I love you","Aishitemasu",R.raw.phrase13));
        words.add(new Word("I hate you","Daikirai",R.raw.phrase14));
        words.add(new Word("Bye,see you again.","Sayōnara, mata aimashou.",R.raw.phrase15));

        WordAdapter adapter = new WordAdapter(getActivity(), words);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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