package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Auto Selections")
public class AutoSelection extends Library{
    //public enum Auto {STONE, FOUNDATION, PARKING, FINAL, VIBE}
    public enum Auto {COLOR, TYPE, VIBE}
    int stones = -1; int foundation = -1; int park = -1; int color = -1; int type = -1;
    private String Color, Type;
    private boolean up, down, left, right, foundationB, parkB;
    //Auto auto = Auto.STONE;
    Auto auto = Auto.COLOR;
    BlueBlockAuto BB = new BlueBlockAuto();

    public void init(){}

    public void init_loop()
    {
        up = gamepad1.dpad_up;
        down = gamepad1.dpad_down;
        right = gamepad1.dpad_right;
        left = gamepad1.dpad_left;

        switch (auto){
            /*case STONE:
            {
                telemetry.addLine("up for 0 Stone");
                telemetry.addLine("right for 1 Stone");
                telemetry.addLine("down for 2 Stone");
                telemetry.addLine("left for 3 Stone");

                if (up)
                    stones = 0;

                else if (right)
                    stones = 1;

                else if (down)
                    stones = 2;

                else if (left)
                    stones = 3;

                if (stones > -1)
                {
                    auto = Auto.FOUNDATION;

                    try
                    {
                        Thread.sleep(300);
                    }

                    catch (InterruptedException ie) { }
                }

                break;
            }

            case FOUNDATION:
                telemetry.addLine("up for yes foundation");
                telemetry.addLine("down for no foundation");

                if (up)
                {
                    foundation = 1;
                    foundationB = true;
                }

                else if (down)
                {
                    foundation = 0;
                    foundationB = false;
                }

                if (foundation > -1)
                {
                    auto = Auto.PARKING;

                    try
                    {
                        Thread.sleep(300);
                    }

                    catch (InterruptedException ie) { }
                }

                break;

            case PARKING:
                telemetry.addLine("up for yes parking");
                telemetry.addLine("down for no parking");

                if (up)
                {
                    park = 1;
                    parkB = true;
                }

                else if (down)
                {
                    park = 0;
                    parkB = false;
                }

                if (park > -1)
                {
                    auto = Auto.FINAL;

                    try
                    {
                        Thread.sleep(300);
                    }

                    catch (InterruptedException ie) { }
                }

                break;

                case FINAL:
                    telemetry.addLine(stones + " Stones");
                    telemetry.addLine(foundationB + " : will do foundation");
                    telemetry.addLine(parkB + " : will do parking");
                    auto = Auto.VIBE;

                default:*/
            case COLOR:{
                telemetry.addLine("UP for BLUE side");
                telemetry.addLine("DOWN for RED side");

                if( up ){
                    Color = "red";
                    color++;
                }

                else if( down ){
                    Color = "blue";
                    color++;
                }

                if( color > -1 ){
                    auto = Auto.TYPE;

                    try{
                        Thread.sleep(300);
                    }catch(Exception ie){
                    }
                }

                break;
            }

            case TYPE:
                {
                telemetry.addLine("UP for BLOCK");
                telemetry.addLine("DOWN for FOUNDATION");

                if( up ){
                    Type = "block";
                    type++;
                }

                else if( down ){
                    Type = "foundation";
                    type++;
                }

                if( type > -1 ){
                    auto = Auto.VIBE;

                    try{
                        Thread.sleep(300);
                    }catch( InterruptedException ie ){
                    }
                }

                break;
            }
        }
    }
    public void loop(){
        if (Color.equals("red") && Type.equals("foundation")){
            //            redFoundation();
        }

        else if (Color.equals("blue") && Type.equals("foundation")){
            //            blueFoundation();
        }

        else if (Color.equals("red") && Type.equals("block")){
            //            redBlock();
        }
        else if (Color.equals("blue") && Type.equals("block")){
            BB.start();
        }
    }
}