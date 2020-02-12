package org.firstinspires.ftc.teamcode;

public class PushFoundation
{
    float startEncoderValue;
    final double CMPerRotation = 194.13;

    public PushFoundation()
    {
        startEncoderValue = Library.getEncoderValue();
    }

    public void driveToQuarry(boolean isOnBlueSide)
    {
        if (Library.getEncoderValue() - 20 * CMPerRotation < startEncoderValue)
            Library.drive(5f, 0, 0);
    }
}