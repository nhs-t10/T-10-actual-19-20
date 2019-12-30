//
//// ---------------------------------------------------------
//// Class for Proportional Integral Derivative controler
//// Goal: reduce jerk in the SBotNick ( ͡~ ͜ʖ ͡° )
//// ---------------------------------------------------------
//
//package org.firstinspires.ftc.teamcode;
//
//import android.hardware.SensorEventListener;
//
//public class PID{
//    double prevError = 0.0;
//    double sumError = 0.0;
//    double prevTime = 0.0;
//    final double P = 0.3;
//    final double D = 0.0;
//    final double I = 0.0;
//    double dComponent;
//    double savedTime, error, error2;
//    double time, destination, distDestination;
//    double currentPosition;
//    String state; // IDLE,TURNING,TRAVELING_IN_A_LINEAR_FASHION
//    // setpoint;
//
//    public PID(double proport, double derivative, double integral){
//        super();
//        P = proport;
//        D = derivative;
//        I = integral;
//    }
//
//    // potentially need to create a trig function to calculate the angle
//
//    public double getDrivingError(){
//        return distDestination - motor.getCurrentPosition();
//    }
//
//
//    public void setDistDestination(float centimeters){
//        centimeters2 = encoderConversions(centimeters);
//        distDestination = centimeters2;
//    }
//
//    // method to find the
//
//
//    /*
//    public double getCurrTime() {
//        return System.currentTimeMillis();
//    }
//    */
//
//
//    // This code should take in the encoder value
//    /*
//    public void setSetpoint(double setpoint){
//		this.setpoint=setpoint;
//    }
//    */
//
//
//    public void updateMoving(float cm){
//        double output;
//        double Poutput;
//        double Doutput;
//
//        distDestination = setDistDestination(cm);
//        currentDistance = motor.getPosition();
//        double error = getDistanceError();
//        prevTime = (double) System.currentTimeMillis();
//        pComponent = error * P;
//        // Code to make slight delay, in order to avoid dividing by zero error
//        // double error2 = getDistanceError();
//        // double time = (double) System.currentTimeMillis();
//        dComponent = 0;
//        // dComponent = ((error2 - error) / (time - prevTime)) * D;
//        double sumError = pComponent + dComponent;
//        drive(0.5f, (float)(sumError), 0f);
//
//        // code to do control movemment of the robot, in order to get to an "ideal state"
//
//    }
//
//    // use getError() to get current angle, and use getCurrTime to get currTime
//
//
//    // Misc. code from FTC PID forum page:
//
//    // --------------------------------------------------------------
//    // "You have two components when driving forward: 1) Overall forward power;
//    // 2) Differential power between left and right. You calculate the overall forward
//    // power by checking your encoder against its target. So if the target is far away,
//    // the overall forward power is large. If you are almost there, the overall forward power
//    // would be small. Then the differential power is controlled by the gyro. If the robot is
//    // deviated from the center line in a big way (i.e. the error is large), the differential
//    // power is big. If you are right on straight, the differential power is zero. With these
//    // two components, the wheel powers are:""
//    // *mikets - Senior Member
//    // --------------------------------------------------------------
//
//
//    // adjust code to fit distinct variable- also, put this into a methos
//    /*
//    driveError = distanceTarget - currentEncoder;
//    turnError = angleTarget - currentGyroAngle;
//    drivePower = driveKp * driveError;
//    turnPower = turnKp*turnError;
//    leftPower = drivePower + turnPower;
//    rightPower = driverPower - turnPower;
//*/
////}