package org.firstinspires.ftc.teamcode;

public class Turning
{
    double currentAngle;
    double destinationAngle;
    double pComponent;
    state currentEvent;

    //double prevError = 0.0;
    double sumError = 0.0;
    final double P = 0.03;
    double savedTime;

    //Different possible states during turning
    enum state
    {
        IDLE, TURNING, TRAVELING_IN_A_LINEAR_FASHION
    }

    //Turning object: Has a destination angle and a current event (state)
    public Turning()
    {
        destinationAngle = 0;
        currentEvent = state.IDLE;
    }

    //Setting the destination in degrees
    public void setDestination(float degrees)
    {
        savedTime = getCurrTime();

        if (degrees > 180)
            destinationAngle = degrees - 360;

        //Otherwise, the destination just becomes the entered degrees
        destinationAngle = degrees;
        currentEvent = state.TURNING;
    }

    /*public void startSkewing()
    {
        destinationAngle = currentAngle;
        currentEvent = state.TRAVELING_IN_A_LINEAR_FASHION;
    }*/


    public void updateDrive(imuData data)
    {
        //Setting the current angle
        currentAngle = data.getAngle();

        //Finding the error
        double error = getError();
        pComponent = error * P;

        if (currentEvent == state.TURNING)
        {
            //if (getCurrTime() - savedTime > 2000)
                //stopTurning();
            //else
                //Library.drive(0f, (float) pComponent, 0f);

            while (currentAngle != destinationAngle)
            {
                Library.drive(0f, (float) pComponent, 0f);
                currentAngle = data.getAngle();
            }
            stopTurning();
        }

        else if (currentEvent == state.TRAVELING_IN_A_LINEAR_FASHION)
            Library.drive(0.5f, (float) pComponent, 0f);
    }

    public void stopTurning()
    {
        currentEvent = state.IDLE;
        sumError = 0.0;
        Library.drive(0f, 0f, 0f);
    }

    /*public void stopSkewing()
    {
        currentEvent = state.IDLE;
        Library.drive(0,0,0);
    }*/

    public double getError()
    {
        return currentAngle - destinationAngle;
    }

    public double getCurrTime()
    {
        return System.currentTimeMillis();
    }
}