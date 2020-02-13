package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import android.graphics.Color;


@TeleOp(name = "Distance Sensors")
public class DistanceSensors extends Library{
    public Library.DRIVING mode;
    boolean xToggle = false;
    boolean aToggle = false;
    boolean bToggle = false;
    boolean yToggle = false;
    private final double SCALE_FACTOR = 255;
    private float[] hsvValues = { 0F, 0F, 0F };

    public void init(){
        hardwareInit();
        vuforiaInit();
    }

    public void loop(){
        boolean x = gamepad1.x;
        boolean b = gamepad1.b;
        boolean a = gamepad1.a;
        boolean y = gamepad1.y;

        Color.RGBToHSV((int) ( color.red() * SCALE_FACTOR ), (int) ( color.green() * SCALE_FACTOR ), (int) ( color.blue() * SCALE_FACTOR ), hsvValues);

        //Movement inputs
        float linear = gamepad1.left_stick_y; //Forward and back
        float side = gamepad1.left_stick_x; //Right and left
        float rotation = gamepad1.right_stick_x; //Rotating in place

        if( x ){
            if( !xToggle ){
                xToggle = true;
            }else{
                xToggle = false;
            }
        }

        if( xToggle ){
            if( distance.getDistance(DistanceUnit.CM) >= 20 ){
                drive(.25f, 0, 0);
            }else{
                drive(0, 0, 0);
                xToggle = false;
            }
        }

        if( aToggle ){
            if( hsvValues[0] < 140 ){ //hsvValues[0] < 140 this is blue
                drive(0, 0, .4f);
            }else{
                drive(0, 0, 0);
                aToggle = false;
            }
        }
        if( a ){
            if( !aToggle ){
                aToggle = true;
            }else{
                aToggle = false;
            }
        }

        if( bToggle ){
            if( hsvValues[0] > 100 ){ //hsvValues[0] > 100 this is red
                drive(0, 0, -.4f);
            }else{
                drive(0, 0, 0);
                bToggle = false;
            }
        }

        if( b ){
            if( !bToggle ){
                bToggle = true;
            }else{
                bToggle = false;
            }
        }

        if( yToggle ){
            if( isSkystoneVisible() ){
                drive(0, 0, -.4f);
            }else{
                drive(0, 0, 0);
                bToggle = false;
            }
        }

        if( y ){
            if( !yToggle ){
                yToggle = true;
            }else{
                yToggle = false;
            }
        }

        if( gamepad1.right_stick_button ){
            mode = mode.getNext();
        }

        if( !aToggle && !bToggle && !xToggle && !yToggle ){
            drive(linear, rotation, side); // fast driving
        }

        telemetry.addData("Values: ", linear + "\n " + rotation + "\n " + side);
        telemetry.addData("Distance: ", distance.getDistance(DistanceUnit.CM));
        telemetry.addData("A: ", aToggle);
        telemetry.addData("B: ", bToggle);
        telemetry.addData("X: ", xToggle);
        telemetry.addData("Y: ", yToggle);
        telemetry.addData("Skystone: ", isSkystoneVisible());
    }
}