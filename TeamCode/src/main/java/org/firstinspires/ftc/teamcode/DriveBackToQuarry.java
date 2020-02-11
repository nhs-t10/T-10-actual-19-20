package org.firstinspires.ftc.teamcode;

public class DriveBackToQuarry
{
    float startEncoderValue;
    final double ticksPerRotation = 194.13;

    public DriveBackToQuarry()
    {
        startEncoderValue = Library.getEncoderValue();
    }

    public void driveToQuarry(boolean isOnBlueSide)
    {
        if (Library.getEncoderValue() + 100 * ticksPerRotation > startEncoderValue)
            Library.drive(.5f, 0, 0);
    }
}