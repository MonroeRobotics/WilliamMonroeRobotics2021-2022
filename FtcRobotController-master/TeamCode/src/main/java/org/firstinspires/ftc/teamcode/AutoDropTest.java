/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(group = "Test")
@Disabled
public class AutoDropTest extends LinearOpMode{
    
    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;

    public void turnForTime(float time, double turnPower){


        double checkTime =  System.currentTimeMillis();
        double checkTimeEnd = checkTime + time;

        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");

        while (checkTimeEnd > System.currentTimeMillis() && opModeIsActive()) {
            backLeft.setPower(turnPower);
            frontRight.setPower(turnPower);
            backRight.setPower(turnPower);
            frontLeft.setPower(turnPower);
        }

        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
    }

    public void moveForTime(float time, float deg, double magnitude){
        
        double fRight;
        double bRight;
        double bLeft;
        double fLeft;

        double direction = deg * (Math.PI / 180);
        
        double checkTime =  System.currentTimeMillis();
        double checkTimeEnd = checkTime + time;
        
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        
        while (checkTimeEnd > System.currentTimeMillis() && opModeIsActive()) {


            fRight = (Math.sin(direction - 1.0/4.0 * Math.PI) * magnitude);
            bLeft = (-Math.sin(direction - 1.0/4.0 * Math.PI) * magnitude);
            bRight = (Math.sin(direction + 1.0/4.0 * Math.PI) * magnitude);
            fLeft = (- Math.sin(direction + 1.0/4.0 * Math.PI) * magnitude);
            
            if (fRight > 1 || fRight < -1){
                fLeft = (fLeft / Math.abs(fRight));
                fRight = (fRight / Math.abs(fRight));
                bRight = (bRight / Math.abs(fRight));
                bLeft = (bLeft / Math.abs(fRight));
            }
        
            if (fLeft > 1 || fLeft < -1){
                fLeft = (fLeft / Math.abs(fLeft));
                fRight = (fRight / Math.abs(fLeft));
                bLeft = (bLeft / Math.abs(fLeft));
                bRight = (bRight / Math.abs(fLeft));
            }
                
            backLeft.setPower(bLeft);
            frontRight.setPower(fRight);
            backRight.setPower(bRight);
            frontLeft.setPower(fLeft);
            
            
        }
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
    }

    public void dropArm(){
        int stage = 0;
        double checkTime;
        double checkTimeEnd = 0;
        double servoPos = 0.9;

        DcMotorEx lineMotor = hardwareMap.get(DcMotorEx.class, "leverMotor");
        DcMotorEx lineMotor2 = hardwareMap.get(DcMotorEx.class, "backMotor");
        Servo dropServo = hardwareMap.get(Servo.class, "dropServo");

        while (stage < 7) {
            if (stage == 0) {
                lineMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                lineMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                time = 700;
                checkTime = System.currentTimeMillis();
                checkTimeEnd = checkTime + time;
                stage = 1;
            }

            //while timer is active and stage 1 is active move arm up
            if (checkTimeEnd > System.currentTimeMillis() && opModeIsActive() && stage == 1) {
                lineMotor.setPower(1);
                lineMotor2.setPower(-0.5);
                telemetry.addData("Running", "True");
            }

            //when time exceeds limit, start new time, switch stage, and move servo
            if (checkTimeEnd < System.currentTimeMillis() && opModeIsActive() && stage == 1) {
                time = 3500;
                checkTime = System.currentTimeMillis();
                checkTimeEnd = checkTime + time;
                lineMotor.setPower(0);
                lineMotor2.setPower(0);
                servoPos = 0.8;
                stage = 2;
            }

            //while timer is active and stage 2 is active move arm up
            if (checkTimeEnd > System.currentTimeMillis() && opModeIsActive() && stage == 2) {
                lineMotor.setPower(1);
                lineMotor2.setPower(-0.5);
                telemetry.addData("Running", "True");
            }

            /* when time exceeds limit, start new time, switch stage, and move servo
            (holds in same spot for a while) */
            if (checkTimeEnd < System.currentTimeMillis() && opModeIsActive() && stage == 2) {
                time = 1400;
                checkTime = System.currentTimeMillis();
                checkTimeEnd = checkTime + time;
                lineMotor.setPower(0);
                lineMotor2.setPower(0);
                servoPos = 0.0;
                stage = 3;
            }

            //when time exceeds limit, start new time, switch stage, and move servo
            if (checkTimeEnd < System.currentTimeMillis() && opModeIsActive() && stage == 3) {
                time = 2800;
                checkTime = System.currentTimeMillis();
                checkTimeEnd = checkTime + time;
                lineMotor.setPower(0);
                lineMotor2.setPower(0);
                servoPos = 0.8;
                sleep(20);
                stage = 4;
            }

            //while timer is active and stage 4 is active move arm down
            if (checkTimeEnd > System.currentTimeMillis() && opModeIsActive() && stage == 4) {
                lineMotor.setPower(-0.9);
                lineMotor2.setPower(1);
                telemetry.addData("Running", "True");
            }

            //when time exceeds limit, start new time, switch stage, and move servo
            if (checkTimeEnd < System.currentTimeMillis() && opModeIsActive() && stage == 4) {
                time = 620;
                checkTime = System.currentTimeMillis();
                checkTimeEnd = checkTime + time;
                lineMotor.setPower(0);
                lineMotor2.setPower(0);
                servoPos = 0.97;
                sleep(20);
                stage = 5;
            }

            //while timer is active and stage 5 is active move arm down
            if (checkTimeEnd > System.currentTimeMillis() && opModeIsActive() && stage == 5) {
                lineMotor.setPower(-0.9);
                lineMotor2.setPower(1);
                telemetry.addData("Running", "True");
            }

            if (checkTimeEnd < System.currentTimeMillis() && opModeIsActive() && stage == 5) {
                time = 30;
                checkTime = System.currentTimeMillis();
                checkTimeEnd = checkTime + time;
                stage = 6;
                telemetry.addData("Running", "True");
            }

            if (checkTimeEnd > System.currentTimeMillis() && opModeIsActive() && stage == 6) {
                lineMotor.setPower(1);
                telemetry.addData("Running", "True");
            }

            //when timer is up and stage 5 is active stop motors and reset stage
            if (checkTimeEnd < System.currentTimeMillis() && opModeIsActive() && stage == 6) {
                stage = 7;
                lineMotor.setPower(0);
                lineMotor2.setPower(0);
                telemetry.addData("Running", "False");
            }
            dropServo.setPosition(servoPos);
        }
    }
    
    @Override
    public void runOpMode() {
        
        
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        // run until the end of the match (driver presses STOP)



        moveForTime(1000, 270, 1);
        
        moveForTime(1300, 180, 1);

        dropArm();

        moveForTime(3000, 0, 1);

        moveForTime(300, 270, 1);
    }
}
