package kosewski.bartosz.betalarmclock.Model;

import com.kinvey.java.User;
import com.parse.ParseUser;

/**
 * Created by Bartosz on 23.03.2016.
 */
public class Duel {

    //Player 1
    public ParseUser mPlayer1;
    public Alarm mAlarm1;
    public int mScore1;

    //Player 2
    public ParseUser mPlayer2;
    public Alarm mAlarm2;
    public int mScore2;

    public Duel(){

    }
}
