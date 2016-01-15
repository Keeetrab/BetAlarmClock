package kosewski.bartosz.betalarmclock;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bartosz on 18.12.2015.
 */
public class Alarm implements Parcelable {
    private int mHour;
    private int mMinutes;
    private int mId;


    public Alarm (int hour, int minutes){
        mHour = hour;
        mMinutes = minutes;
    }
    public Alarm (int hour, int minutes, int id){
        mHour = hour;
        mMinutes = minutes;
        mId = id;
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

    //Parcelable

    public Alarm(Parcel source) {
        mHour = source.readInt();
        mMinutes = source.readInt();
        mId = source.readInt();
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
