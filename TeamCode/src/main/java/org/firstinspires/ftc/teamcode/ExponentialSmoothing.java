package org.firstinspires.ftc.teamcode;

    /* st' = α * (xt) +(1 - α) * st−1 [equation lol] */

//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.util.ElapsedTime;

// for future work; finish working on these methods; use acceleration for smoothing, also maybe distance sensor for error
public class ExponentialSmoothing {
    double aVal = 0.3;
    double vVal = 0.2;
    //double goodPercent = 0.01;
    double maxAccel = 0.9 * 2;
    //float goodEncodeValue = 10;
    ElapsedTime clock = new ElapsedTime();
    double currentTime;

    // clock methods; to run with the time loops in auto or teleop
    // ------------------------------------------------------------------------------------------

    // constructor, to run the time calculations
    public ExponentialSmoothing(){
        resetClock();
        currentTime = clock.milliseconds();
    }

    // returns the current clock value, useful for specific time intervals for smoothing
    public double getClockTime(){
        return currentTime;
    }

    // updates the current value of the instance variable currentTime with the current millisecond value
    // for: keeping track of how much time the loop has been running
    public void updateClock(){
        currentTime = clock.milliseconds();
    }

    // method used to reset the time, to have set the start of a loop as milliseconds zero
    private void resetClock(){
        clock.reset();
    }

    // Smoothing methods
    // ------------------------------------------------------------------------------------------

    // method to return the smoothed value, in terms of the acceleration
    // goal is a positive target acceleration; potentially add in PID
    public void smoothing(double goal, double goalVelocity, imuData imu) {
        //double dist = Library.distance.getDistance(DistanceUnit.CM);
        double currentAcc = imu.getXAcceleration(); double futureValue = 0;
        double currentVel = imu.getXVelocity();
        if(goal == currentAcc){
            futureValue = (aVal * (goal - currentAcc) + currentAcc) / maxAccel;
            Library.drive((float) futureValue, 0f, 0f);
        }
        if (goal - currentAcc > 0) {
            futureValue = (aVal * (goal - currentAcc) + vVal * (goalVelocity - currentVel) + currentAcc) / maxAccel;
            Library.drive((float) futureValue, 0f, 0f);
        }
        else if (goal == 0){
            Library.drive(0f, 0f, 0f); }
        else if(goal - futureValue < 0){
            decelerateToValue(imu, goal);
        }
        updateClock();
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

    // startAcceleration() is a method intended to avoid jerk upon the first instance of motion by the robot.
    // targetMotorValue should be below 0.9
    // preCondition; targetAcceleration should be larger than current (up to 2.0)
    public void smallAcceleration(double targetAccel, imuData imu) {
        //float current = (float) clock.milliseconds();
        double partStep = (imu.getXAcceleration() + aVal * (targetAccel - imu.getXAcceleration()) ) / maxAccel;
        Library.drive( (float) partStep, 0f, 0f);
        updateClock();
    }


    // decelerate strictly going to zero, in slow steps
    public void decelerate(imuData imu) {
        double currentAcc = imu.getXAcceleration(); double futureAcc = currentAcc - aVal * (currentAcc);
        double inputVal = futureAcc / maxAccel;
        Library.drive((float) inputVal, 0f, 0f);
        updateClock();
    }

    // decelrate to a target acceleration -> one that is less than the current speed, and non zero
    public void decelerateToValue(imuData imu, double targetAccel) {
        double currentAcc = imu.getXAcceleration(); double futureAcc = currentAcc - aVal * (currentAcc - targetAccel);
        double inputVal = futureAcc / maxAccel;
        Library.drive((float) inputVal, 0f, 0f);
        updateClock();
    }
}
