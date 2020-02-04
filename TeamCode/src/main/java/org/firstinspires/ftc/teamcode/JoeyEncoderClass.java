package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class JoeyEncoderClass{
    float startPos;
    float destinationPos;
    float currentPos;
    double pComponent;
    final double P = .03;

    boolean started;
    ElapsedTime clock = new ElapsedTime();

    public JoeyEncoderClass(){
        startPos = 0;
    }

    public void setDestination( float cm ){

        //Otherwise, the destination just becomes the entered degrees
        //Conversion from cm to encoders
        destinationPos = cm * 43;
    }

    public double[] updateAndDrive(){
        //Setting the current angle
        currentPos = Library.getEncoderValue();

        //Finding the error
        double error = destinationPos - currentPos;

        pComponent = error * P;
        if( pComponent > .5f ){
            pComponent = .5f;
        }
        if( pComponent < -.5f ){
            pComponent = -.5f;
        }

        if( Math.abs(error) > 10 ){
            Library.drive((float) pComponent, 0, 0f);
        }else{
            Library.drive(0, 0, 0);
        }

        double[] array = { destinationPos, currentPos, error, pComponent };
        return array;
    }

    public double[] driveForCM( int cm ){
        double [] array = {0, 0, 0, 0};
        if( !started ){
            started = true;
            clock.reset();
        }

        if( started && clock.seconds() < 1 ){
            setDestination(cm);
        }else if( started && clock.seconds() < 10 ){
            array = updateAndDrive();
            return array;
        }
        return array;
    }
}
