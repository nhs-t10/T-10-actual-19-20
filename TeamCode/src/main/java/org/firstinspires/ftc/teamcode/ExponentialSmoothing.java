package org.firstinspires.ftc.teamcode;

    /* st' = α * (xt) +(1 - α) * st−1 equation lol*/

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.util.ElapsedTime;

// for future work; finish working on these methods; use acceleration for smoothing, also maybe distance sensor for error
public class ExponentialSmoothing {
    double aVal = 0.3;
    //double goodPercent = 0.01;
    double maxAccel = 0.9 * 2;
    //float goodEncodeValue = 10;
    ElapsedTime clock = new ElapsedTime();

    /*public void init()
    {
        imuData imu = new imuData(hardwareMap);
        hardwareInit();
        encodersInit();
        //distance.getDistance(DistanceUnit.CM);
    }
    */

    // goal might potentially be better to use as target acceleration;
    // method to return the smoothed value
    public void smoothing(double goal, imuData imu) {
        //double currentPosition = getEncoderValue();
        float currTime = (float) clock.milliseconds();
        double dist = Library.distance.getDistance(DistanceUnit.CM);
        double currentValue = imu.getXAcceleration(); double futureValue = 0;
        if(clock.milliseconds() < currTime + 100) {
            if (goal - currentValue > 0)
                futureValue = (aVal * (goal - currentValue) + currentValue) / goal;
            else if (goal == 0)
                futureValue = 0;
            else if (goal - currentValue < 0) {
                // code to maybe go back?
                futureValue = 0;
            }
        }
        Library.drive((float) futureValue, 0f, 0f);
        clock.reset();
        // if (Fv - Cv) is less than 0, goto...
        // if Fv = 0, the go to (stop)
        // say; if (Fc - Cv) is within a certain threshold, get to that speed
    }

    /*

        //import clock
        //float curTime = getTime()
        //if (getTime() > curTime + 10 && condition)
        //{
            //do stuff
            //curtime = getTime();
        //}
    }
     */

    // startMoving() is a method intended to avoid jerk upon the first instance of motion by the robot.
    // targetMotorValue should be below 0.9
    public void startAccelerating(double targetAcceleration) {
        clock.reset();
        float current = (float) clock.milliseconds();
        double partStep = (targetAcceleration / 2) / maxAccel;
        Library.drive( (float) partStep, 0f, 0f);
        if(clock.milliseconds() > current + 10) {
            partStep += partStep;
            Library.drive((float) partStep, 0f, 0f);
        }
    }

    public void accelerationLoop(double targetMotorValue) {
        startAccelerating(targetMotorValue / 3);
        startAccelerating(2 * targetMotorValue / 3);
        startAccelerating(targetMotorValue);
    }

    public void decelerate(imuData imu) {
        double currentAcc = imu.getXAcceleration(); double futureAcc = currentAcc + aVal * (0 - currentAcc);
        double inputVal = futureAcc / maxAccel;
        Library.drive((float) inputVal, 0f, 0f);
    }
}
