package org.firstinspires.ftc.teamcode;

public class OldTurning {
    double currentAngle;
    double destinationAngle;
    double pComponent;
    state currentEvent;

    //double prevError = 0.0;
    double sumError = 0.0;
    final double P = 0.02;
//    double savedTime;

    //Different possible states during turning
    enum state{
        IDLE, TURNING, TRAVELING_IN_A_LINEAR_FASHION
    }

    //OldTurning object: Has a destination angle and a current event (state)
    public OldTurning(){
        destinationAngle = 0;
        currentEvent = state.IDLE;
    }

    //Setting the destination in degrees
    public void setDestination(imuData imu, float degrees){
        destinationAngle = imu.getAngle() + degrees;

        if( destinationAngle > 180 ){
            destinationAngle -= 360;
        }
        else if (destinationAngle < -180){
            destinationAngle += 360;
        }

        currentEvent = state.TURNING;
    }

    /*public void startSkewing()
    {
        destinationAngle = currentAngle;
        currentEvent = state.TRAVELING_IN_A_LINEAR_FASHION;
    }*/


    public double[] updateAndDrive( imuData imu ){
        //Setting the current angle
        currentAngle = imu.getAngle();

        //Finding the error
        double error = getError();
        /*This turnAngle is so that the robot turns the right direction and considers the
         *rotation system of IMU (-180 to 180)
         */
        double turnAngle = - error;
        if( turnAngle > 180 ){
            turnAngle -= 360;
        }
        else if (turnAngle < -180){
            turnAngle += 360;
        }
        pComponent = turnAngle * P;
        if( pComponent > .5f ){
            pComponent = .5f;
        }
        if( pComponent < -.5f ){
            pComponent = -.5f;
        }

        double stateTest = 0.0;

        if( currentEvent == state.TURNING ){
            if( Math.abs(error) < 3 ){
                stopTurning();
            }else{
                Library.drive(0f, (float) pComponent, 0f);
            }
        }
        else if( currentEvent == state.TRAVELING_IN_A_LINEAR_FASHION ){
            stateTest = 1.0;
        }

        double[] array = { destinationAngle, stateTest, currentAngle, error };
        return array;
    }

    public void stopTurning(){
        currentEvent = state.IDLE;
        sumError = 0.0;
        Library.drive(0f, 0f, 0f);
    }

    /*public void stopSkewing()
    {
        currentEvent = state.IDLE;
        Library.drive(0,0,0);
    }*/

    public double getError(){
        return currentAngle - destinationAngle;
    }

    public double getCurrTime(){
        return System.currentTimeMillis();
    }
}