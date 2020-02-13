package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class ColorParking extends Library{
    private ElapsedTime clock = new ElapsedTime();
    private boolean moving = false;
    private final double SCALE_FACTOR = 255;
    private float[] hsvValues = { 0F, 0F, 0F };

    @Override
    public void init(){
        hardwareInit();
    }

    public void loop(){
    }

    private void ColorParking( boolean isBlue, boolean isFoundation ){
        Color.RGBToHSV((int) ( color.red() * SCALE_FACTOR ), (int) ( color.green() * SCALE_FACTOR ), (int) ( color.blue() * SCALE_FACTOR ), hsvValues);
        boolean isDriveRight = isBlue == isFoundation;
        if( !moving ){
            clock.reset();
            moving = true;
        }else if( isBlue && ( hsvValues[0] >= 180 || clock.seconds() >= 6 ) ){
            moving = false;
            drive(0, 0, 0);
            //            currentState = State.END;
        }else if( !isBlue && ( hsvValues[0] <= 60 || clock.seconds() >= 6 ) ){
            moving = false;
            drive(0, 0, 0);
            //            currentState = State.END;
        }else if( clock.seconds() >= 5 ){
            if( isDriveRight ){
                drive(0, 0, -.3f);
            }else{
                drive(0, 0, .3f);
            }
        }else{
            if( isDriveRight ){
                drive(0, 0, .4f);
            }else{
                drive(0, 0, -.4f);
            }
        }

        if( distanceLeft.getDistance(DistanceUnit.CM) > 8 || distanceRight.getDistance(DistanceUnit.CM) > 8 ){
            drive(.3f, 0, 0);
        }
    }
}