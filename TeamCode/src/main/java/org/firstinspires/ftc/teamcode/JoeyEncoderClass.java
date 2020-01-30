package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

public class JoeyEncoderClass
{
    float startPos;
    float destinationPos;
    float currentPos;
    double pComponent;
    final double P = .001;

    boolean started;
    ElapsedTime clock = new ElapsedTime();

    public JoeyEncoderClass()
    {
        startPos = 0;
    }

    public void setDestination( float cm ){
        startPos = Library.getEncoderValue();

        //Otherwise, the destination just becomes the entered degrees
        //Conversion from cm to encoders
        destinationPos = startPos + cm * 43;
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
        }

        double[] array = { destinationPos, currentPos, error, pComponent };
        return array;
    }

    public void driveForCM (int cm) {
        if (!started) {
            started = true;
            clock.reset();
        }

        if (started && clock.seconds() < 1) {
            setDestination(cm);
        }
        else if (started && clock.seconds() < 10) {
            updateAndDrive();
        }
    }
}
