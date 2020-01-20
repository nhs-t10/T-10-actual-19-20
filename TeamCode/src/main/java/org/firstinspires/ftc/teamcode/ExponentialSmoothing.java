package org.firstinspires.ftc.teamcode;

    /* stop at once when needed
    future value = current value + a (future - current value);
    st' = α * (xt) +(1 - α) * st−1 equation lol*/
public class ExponentialSmoothing extends Library {
    double aVal = 0.3;
    double goodPercent = 0.01;
    // what enoder value could be considered close enough? (acceptable margin of error)
    float goodEncodeValue = 10;

    public void init()
    {
        imuData imu = new imuData(hardwareMap);
        hardwareInit();
        encodersInit();
    }

    // goal might potentially be better to use as target acceleration;
    // method to return the smoothed value
    public float smoothing(double goal) {
        //double currentPosition = getEncoderValue();
        double currentValue = imu.getXAcceleration(); double futureValue;
        if(goal - currentValue > 0)
            futureValue = (aVal * (goal - currentValue) + currentValue) / goal;
        else if(goal == 0)
            futureValue = 0;
        else if(goal - currentValue < 0){
            // code to maybe go back?
            futureValue = 0;
        }

        return (float) futureValue;
        // if (Fv - Cv) is less than 0, goto...
        // if Fv = 0, the go to (stop)
        // say; if (Fc - Cv) is within a certain threshold, get to that speed
    }

    // method to loop through returned future value. double goal is an encoder value
    // closeEnough is a value for an encoder distance that is functionally close enough to
    public void smoothingController(double goal, double closeEnough) {
        double errorProp = (goal - imu.getXAcceleration()) / goal;
        //double currentPosition = getEncoderValue();
        while(errorProp >= goodPercent){
            drive(smoothing(goal), 0f, 0f);
            Thread.sleep(10)
        }
        if(goal - getEncoderValue() > goodEncodeValue){
            // some code to go until completion;
            // perhaps:
            float currentPosition = getEncoderValue();
            encodersDriveForButNoLoops(currentPosition, (float) goal, 1.0);
        }
    }

    // startMoving() is a method intended to avoid jerk upon the first instance of motion by the robot.
    // targetMotorValue should be below 0.9
    public void startMoving(double targetMotorValue) {
        double partStep = targetMotorValue / 5;
        double i = partStep;
        while(i <= targetMotorValue){
            drive((float) partStep, 0f, 0f);
            i+= partStep;
            Thread.sleep(10);
        }
    }

    public void decelerate() {
        double currentAcc = imu.getXAcceleration();
        // read remaining space
        /*
        i = getEncoderValue();
         */
    }
}
