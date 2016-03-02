package kosewski.bartosz.betalarmclock;

import android.os.Parcel;
import android.os.Parcelable;

import kosewski.bartosz.betalarmclock.Utils.GeneralUtilities;

/**
 * Created by Bartosz on 18.12.2015.
 */
public class Alarm implements Parcelable {
    private int mHour;
    private int mMinutes;
    private int mId;
    private boolean[] mDays;
    private boolean mIsEnabled;
    private boolean mIsOneShot;


    //Constructor for creating alarm
    public Alarm (int hour, int minutes, boolean[] days){
        mHour = hour;
        mMinutes = minutes;
        mDays = new boolean[7];
        System.arraycopy(days, 0, mDays, 0, days.length);
        mIsEnabled = true;
        mIsOneShot = setIsOneShot(mDays);
    }

    //Constructor for populating list
    public Alarm (int hour, int minutes, int id, boolean[] days, int isEnabled){
        mHour = hour;
        mMinutes = minutes;
        mId = id;
        mDays = new boolean[7];
        System.arraycopy(days, 0, mDays, 0, days.length);
        mIsEnabled = isEnabled == 1;
        mIsOneShot = setIsOneShot(mDays);
    }


    public boolean isOneShot() {
        return mIsOneShot;
    }

    public boolean setIsOneShot(boolean[] days) {
        for(boolean day : days){
            if(day){
                return false;
            }
        }
        return true;
    }

    public boolean isEnabled() {
        return mIsEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        mIsEnabled = isEnabled;
    }

    public boolean[] getDays() {
        return mDays;
    }

    public void setDays(boolean[] days) {
        mDays = days;
    }

    public int getMinutes() {
        return mMinutes;
    }

    public void setMinutes(int minutes) {
        mMinutes = minutes;
    }

    public int getHour() {
        return mHour;
    }

    public void setHour(int hour) {
        mHour = hour;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setRepeatingDay(int dayOfWeek, boolean value) {
        mDays[dayOfWeek] = value;
    }

    public boolean getRepeatingDay(int dayOfWeek) {
            return mDays[dayOfWeek];
    }

    //Parcelable

    public Alarm(Parcel source) {
        mHour = source.readInt();
        mMinutes = source.readInt();
        mId = source.readInt();
        mDays = source.createBooleanArray();
        mIsEnabled = source.readInt() != 0;
        mIsOneShot = setIsOneShot(mDays);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mHour);
        dest.writeInt(mMinutes);
        dest.writeInt(mId);
        dest.writeBooleanArray(mDays);
        dest.writeInt((mIsEnabled ? 1 : 0));
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel source) {
            return new Alarm(source);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };
}
