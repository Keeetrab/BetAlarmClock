package kosewski.bartosz.betalarmclock;

/**
 * Created by Bartosz on 18.12.2015.
 */
public class Alarm {
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

}
