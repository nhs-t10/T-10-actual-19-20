package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DriveToQuarry
{
    float error;
    float startEncoderValue;
    final float CMPerRotation = (float) 194.13;

    public DriveToQuarry()
    {
        startEncoderValue = 5 * CMPerRotation + Library.getEncoderValue();
    }

    public float driveToQuarry()
    {
        error = startEncoderValue - Library.getEncoderValue();

        if( error > 1 )
            Library.drive(.001f * error, 0, 0);
        else
            Library.drive(0, 0, 0);

        return error;
    }
}