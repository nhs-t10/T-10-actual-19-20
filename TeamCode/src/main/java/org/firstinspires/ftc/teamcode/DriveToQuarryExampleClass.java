package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DriveToQuarryExampleClass{
    float error;
    float startEncoderValue;
    final float CMPerRotation = (float) 194.13;

    public DriveToQuarryExampleClass(){
        startEncoderValue = Library.getEncoderValue();
    }

    public float driveToQuarry( boolean isOnBlueSide ){
        error = 5 * CMPerRotation + startEncoderValue - Library.getEncoderValue();

        if( error > 1 ){
            Library.drive(.001f * error, 0, 0);
        }else{
            Library.drive(0, 0, 0);
        }

        return error;
    }
}