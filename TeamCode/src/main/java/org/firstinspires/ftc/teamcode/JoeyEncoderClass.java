package org.firstinspires.ftc.teamcode;

public class JoeyEncoderClass
{
    float startPos;
    float destinationPos;
    float currentPos;
    double pComponent;
    final double P = .03;
    public JoeyEncoderClass()
    {
        startPos = 0;
    }

    public void setDestination( float cm ){
        startPos = Library.getEncoderValue();

        //Otherwise, the destination just becomes the entered degrees
        destinationPos = startPos + cmToEncoders(cm);
    }

    //Add code here that converts cm to encoders
    public float cmToEncoders(float cm){
        return cm;
    }

    public double[] updateAndDrive(  ){
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
            Library.drive( (float) pComponent, 0,0f);
        }

        double[] array = { destinationPos, currentPos, error };
        return array;
    }

    public float convertToTicks(float distanceInCm)//converts centimeters to encoders ticks so it can be used in start goal
    {
        return distanceInCm*1120/36;//probably is wrong but oh well
    }
}
