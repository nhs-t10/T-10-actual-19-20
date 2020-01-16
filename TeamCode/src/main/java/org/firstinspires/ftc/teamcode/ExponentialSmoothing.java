package org.firstinspires.ftc.teamcode;

public class ExponentialSmoothing extends Library {
    double aVal = 0.3;
    double goodPercent = 0.1;
    // stop at once when needed

    // future value = current value + a (future - current value);

    // use if/then statements

    //vary if then; decelerate smoothly, but if future state is zero, get to zero
     */
    // st' = α * (xt) +(1 - α) * st−1 equation lol
    public void init()
    {
        imuData imu = new imuData(hardwareMap);
        //Turning turner = new Turning();
        hardwareInit();
    }

    public double smoothing(double[] data, double future) {
        double currentValue = imu.getXAcceleration();
        double futureValue = future;
        double previousSmoothing = 0.5;
        // attempt at building the algorithm for smoothing

        futureValue = aVal * (future - currentValue) + currentValue;
        // if (Fv - Cv) is less than 0, goto...
        // if Fv = 0, the go to (stop)
        // say; if (Fc - Cv) is within a certain threshold, get to that speed
    }
}
