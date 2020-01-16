package org.firstinspires.ftc.teamcode;

public class Turning
{
    double currentAngle;
    double destination;
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

    //Turning object: Has a destination and a current event (state)
    public Turning()
    {
        destination = 0;
        currentEvent = state.IDLE;
    }

    //Setting the destination in degrees
    public void setDestination(float degrees)
    {
        savedTime = getCurrTime();

        //If the degrees are more than 180 it sets the destination to the smallest reference angle
        //Ex: 200 degrees becomes -160 degrees because they are the same turn, -160 is just shorter
        if (degrees > 180)
            destination = degrees - 360;

        //Otherwise, the destination just becomes the entered degrees
        destination = degrees;
        currentEvent = state.TURNING;
    }

    public void startSkewing()
    {
        destination = currentAngle;
        currentEvent = state.TRAVELING_IN_A_LINEAR_FASHION;
    }


    public void updateDrive(imuData data)
    {
        //Setting the current angle
        currentAngle = data.getAngle();

        //Finding the error
        double error = getError();
        pComponent = error * P;

        if (currentEvent == state.TURNING)
        {
            if (getCurrTime() - savedTime > 2000)
                stopTurning();
            else
                Library.drive(0f, (float) pComponent, 0f);

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

    public void stopSkewing()
    {
        currentEvent = state.IDLE;
        Library.drive(0,0,0);
    }

    public double getError()
    {
        return currentAngle - destination;
    }

    public double getCurrTime()
    {
        return System.currentTimeMillis();
    }
}