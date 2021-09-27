package com.satyajit.Speak_Japanese;

/** A class which contains default translation and
 * miwok translation of the word*/
public class Word {
    /** Defalut translation of the word */
    private final String mDefaultTranslation;

    /**Miwok Translation of the word*/
    private final String mMiwokTranslation;
    private int mImageResource = NO_IMAGE_PROVIDED;
    private int mAudioResourceid;

    private static final int NO_IMAGE_PROVIDED = -1;

    public Word(String defaultTranslation,String miwokTranslation,Integer imageView,Integer audioResouceId){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResource = imageView;
        mAudioResourceid = audioResouceId;
    }
    public Word(String defaultTranslation,String miwokTranslation,Integer audioResouceId){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceid = audioResouceId;
    }

    /**
     * get the default translation of the word
     **/
    public String getmDefaultTranslation(){
        return mDefaultTranslation;
    }
    /**
     * get the miwok translation of the word
     * */
    public String getmMiwokTranslation(){
        return mMiwokTranslation;
    }

    /**
     * get the image of the word
     *
     */
    public Integer getImageResource(){
        return mImageResource;
    }
    /**
     * returns the value is true or false
     */
    public boolean hasImage(){
        //return true or false
        return mImageResource != NO_IMAGE_PROVIDED;
    }

    /**
     * returns the audioResourceId of the word
     * @return
     */
    public Integer getAudioResourceId(){
        return mAudioResourceid;
    }

}
