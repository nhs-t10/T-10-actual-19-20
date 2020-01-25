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
    private final double SCALE_FACTOR = 255;
    private float[] hsvValues = {0F, 0F, 0F};

    public void init(){
        hardwareInit();
        driveInit();
    }

    public void loop(){
        //Stone gripping | both gamepads
        boolean x = gamepad1.x;
        boolean x2 = gamepad2.x;
        boolean b = gamepad1.b;
        boolean a = gamepad1.a;

        //Hook control to grab foundation | both gamepads
        boolean y = gamepad1.y;
        boolean y2 = gamepad2.y;

        //Lift controls | Both gamepads
        Color.RGBToHSV((int)(color.red()*SCALE_FACTOR), (int)(color.green()*SCALE_FACTOR), (int)(color.blue()*SCALE_FACTOR), hsvValues);
        //boolean skystone = gamepad1.dpad_up;

        //Movement inputs
        float linear = gamepad1.left_stick_y; //Forward and back
        float side = gamepad1.left_stick_x; //Right and left
        float rotation = gamepad1.right_stick_x; //Rotating in place

        //If controller two gives any commands (true) than the robot will use those inputs
        //Otherwise, it will use the inputs of controller one

        if( x ){
            if(!xToggle){
                xToggle = true;
            }else{
                xToggle = false;
            }
        }

        if(xToggle){
            if(distance.getDistance(DistanceUnit.CM) >= 20){
                drive(.25f,0,0);
            }else{
                drive(0,0,0);
            }
        }

        if(aToggle){
            if(hsvValues[0] < 140){
                drive(0,0,.25f);
            }else{
                drive(0,0,0);
            }
        }
        if( a ){
            if(!aToggle){
                aToggle = true;
            }else{
                aToggle = false;
            }
        }

        if(bToggle){
            if(hsvValues[0] > 100){
                drive(0,0,-.25f);
            }else{
                drive(0,0,0);
            }
        }
        if( b ){
            if(!bToggle){
                bToggle = true;
            }else{
                bToggle = false;
            }
        }

        if( gamepad1.right_stick_button ){
            mode = mode.getNext();
        }

        if(!aToggle && !bToggle && !xToggle){
            drive(linear, rotation, side); // fast driving
        }

        telemetry.addData("Values: ", linear + "\n " + rotation + "\n " + side);
        telemetry.addData("Distance: ", distance.getDistance(DistanceUnit.CM));
        telemetry.addData("A: ", aToggle);
        telemetry.addData("B: ", bToggle);
        telemetry.addData("X: ", xToggle);
    }
}