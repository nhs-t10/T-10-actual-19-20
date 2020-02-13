package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ExtendTapeMeasure extends Library{
    private ElapsedTime clock = new ElapsedTime();
    private boolean moving = false;

    @Override public void init(){
        hardwareInit();
    }
    public void loop(){ }

    public ExtendTapeMeasure(boolean isTenSeconds)
    {
        if(!moving){
            clock.reset();
            moving = true;
        }else if(isTenSeconds&&clock.seconds()<=10){
            tapeMeasure.setPower(1);
        }else if(!isTenSeconds&&clock.seconds()<=5){
            tapeMeasure.setPower(1);
        }else{
            tapeMeasure.setPower(0);
//            currentState = State.END;
        }
    }
}