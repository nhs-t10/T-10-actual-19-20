package org.firstinspires.ftc.teamcode;

public class RedQuarryAuto
{
    // assuming (depot)OOOOOO(skybridge)
    // 0 = stone, X = skystone
    // quarryConfiguration = 0: 00X00X
    // quarryConfiguration = 1: 0X00X0
    // quarryConfiguration = 2: 00X00X
    // quarryConfiguration = 3: undetected
    int quarryConfiguration;

    private int getQuarryConfiguration()
    {
        for (int i = 0; i < 3; i++)
        {
            if(isSkystoneVisible())
                return i;
            //move left distance of 1 skystone
        }

        //skystone not detected
        return 3;
    }
}
//precondition: robot is placed camera facing quarry, near skybridge
//drive forward until 1 stone is in field of view (conztant)
//for(int i = 0; i < 3; i++)
//{
//  if (skystoneIsVisible)
//      return i;
//
//  slideFor(52);
//}
//
//return 3;