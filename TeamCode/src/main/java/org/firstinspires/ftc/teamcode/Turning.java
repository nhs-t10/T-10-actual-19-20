package org.firstinspires.ftc.teamcode;

public class Turning
{
    double currentAngle;
    double destination;
    double pComponent;
    state currentEvent;

    //double prevError = 0.0;
    double sumError = 0.0;
    double prevTime = 0.0;
    final double P = 0.03;
    double savedTime;

    enum state
    {
        IDLE,TURNING,TRAVELING_IN_A_LINEAR_FASHION;
    }

    public Turning()
    {
        destination = 0;
        currentEvent = state.IDLE;
    }

    public void setDestination(float degrees)
    {
        savedTime = getCurrTime();

        if (degrees > 180)
            destination = degrees - 360;
        else
            destination = degrees;

        prevTime = getCurrTime();
        destination = degrees;
        currentEvent = state.TURNING;
    }

    public void startSkewnting()
    {
        destination = currentAngle;
        currentEvent = state.TRAVELING_IN_A_LINEAR_FASHION;
    }

    public void updateDrive(imuData data)
    {
        currentAngle = data.getAngle();
        double error = getError();
        pComponent = error * P;

        if (currentEvent == state.TURNING)
        {
            if (getCurrTime() - savedTime > 2000)
                stopTurning();
            else
                Library.drive(0f, (float) (pComponent), 0f);
        }

        else if (currentEvent == state.TRAVELING_IN_A_LINEAR_FASHION)
            Library.drive(0.5f, (float) (pComponent), 0f);
    }

    public void stopTurning()
    {
        currentEvent = state.IDLE;
        sumError = 0.0;
        Library.drive(0f, 0f, 0f);
    }

    public void stopSkewednting()
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