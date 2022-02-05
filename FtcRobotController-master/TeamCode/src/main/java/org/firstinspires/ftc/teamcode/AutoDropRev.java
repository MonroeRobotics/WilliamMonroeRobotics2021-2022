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

@Autonomous(name = "AutoBlue",group = "Blue")

public class AutoDropRev extends LinearOpMode{

    private DcMotorEx frontRight;
    private DcMotorEx frontLeft;
    private DcMotorEx backRight;
    private DcMotorEx backLeft;
    private Servo dropServo;
    private Servo blockServo;

    double servoPos = 0.98;

    double servoBlockPos = 0.88;

    public void turnForTime(float time, double turnPower){

        double checkTime =  System.currentTimeMillis();
        double checkTimeEnd = checkTime + time;
        double motorSpeed = 2500;

        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(10);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if (turnPower > 0){
            backRight.setDirection(DcMotor.Direction.FORWARD);
            backLeft.setDirection(DcMotor.Direction.FORWARD);
            frontRight.setDirection(DcMotor.Direction.FORWARD);
            frontLeft.setDirection(DcMotor.Direction.REVERSE);
        }
        else{
            backRight.setDirection(DcMotor.Direction.REVERSE);
            backLeft.setDirection(DcMotor.Direction.REVERSE);
            frontRight.setDirection(DcMotor.Direction.REVERSE);
            frontLeft.setDirection(DcMotor.Direction.FORWARD);
        }


        while (checkTimeEnd > System.currentTimeMillis() && opModeIsActive()) {
            backLeft.setVelocity(Math.abs(motorSpeed * turnPower));
            frontRight.setVelocity(Math.abs(motorSpeed * turnPower));
            backRight.setVelocity(Math.abs(motorSpeed * turnPower));
            frontLeft.setVelocity(Math.abs(motorSpeed * turnPower));
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
        double motorSpeed = 2300;

        double direction = deg * (Math.PI / 180);

        double checkTime =  System.currentTimeMillis();
        double checkTimeEnd = checkTime + time;

        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sleep(10);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while (checkTimeEnd > System.currentTimeMillis() && opModeIsActive()) {

            fRight = (Math.sin(direction - 1.0/4.0 * Math.PI) * magnitude);
            bLeft = (-Math.sin(direction - 1.0/4.0 * Math.PI) * magnitude);
            bRight = (Math.sin(direction + 1.0/4.0 * Math.PI) * magnitude);
            fLeft = (- Math.sin(direction + 1.0/4.0 * Math.PI) * magnitude);


            if (bLeft > 0){
                backLeft.setDirection(DcMotor.Direction.FORWARD);
            }
            else {
                backLeft.setDirection(DcMotor.Direction.REVERSE);
            }
            if (bRight > 0){
                backRight.setDirection(DcMotor.Direction.FORWARD);
            }
            else {
                backRight.setDirection(DcMotor.Direction.REVERSE);
            }
            if (fRight > 0){
                frontRight.setDirection(DcMotor.Direction.FORWARD);
            }
            else {
                frontRight.setDirection(DcMotor.Direction.REVERSE);
            }
            if (fLeft > 0){
                frontLeft.setDirection(DcMotor.Direction.REVERSE);
            }
            else {
                frontLeft.setDirection(DcMotor.Direction.FORWARD);
            }

            backLeft.setVelocity(Math.abs(motorSpeed * bLeft));
            frontRight.setVelocity(Math.abs(motorSpeed * fRight));
            backRight.setVelocity(Math.abs(motorSpeed * bRight));
            frontLeft.setVelocity(Math.abs(motorSpeed * (fLeft)));
        }
        backLeft.setPower(0);
        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
    }

    public void wheelForTime(float time, double turnPower){


        double checkTime =  System.currentTimeMillis();
        double checkTimeEnd = checkTime + time;

        DcMotor wheelMotor = hardwareMap.get(DcMotor.class, "wheelMotor");

        while (checkTimeEnd > System.currentTimeMillis() && opModeIsActive()) {
            wheelMotor.setPower(turnPower);
        }

        wheelMotor.setPower(0);
    }

    public void dropArm(){
        int stage = 0;
        double checkTime;
        double checkTimeEnd = 0;
        servoPos = 0.85;

        DcMotorEx lineMotor = hardwareMap.get(DcMotorEx.class, "leverMotor");
        DcMotorEx lineMotor2 = hardwareMap.get(DcMotorEx.class, "backMotor");
        DcMotor intakeMotor  = hardwareMap.get(DcMotor.class, "intakeMotor");


        lineMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        lineMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        while (stage != 22) {
            if (stage == 0) {
                lineMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                lineMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                time = 575;
                checkTime = System.currentTimeMillis();
                checkTimeEnd = checkTime + time;
                servoBlockPos = .88;
                stage = 1;
            }

            //while timer is active and stage 1 is active move arm up
            if (checkTimeEnd > System.currentTimeMillis() && opModeIsActive() && stage == 1) {
                lineMotor.setPower(1);
                lineMotor2.setPower(-0.55);
                telemetry.addData("Running", "True");
            }

            if ((checkTimeEnd - 350) > System.currentTimeMillis() && opModeIsActive() && stage == 1) {
                intakeMotor.setPower(-0.5);
                telemetry.addData("Running", "True");
            }
            else {
                intakeMotor.setPower(0);
            }


            //when time exceeds limit, start new time, switch stage, and move servo
            if (checkTimeEnd < System.currentTimeMillis() && opModeIsActive() && stage == 1) {
                time = 2800;
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
                lineMotor2.setPower(-0.45);
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
                servoPos = 0.10;
                stage = 12;
            }

            if (checkTimeEnd < System.currentTimeMillis() && opModeIsActive() && stage == 12) {
                time = 500;
                checkTime = System.currentTimeMillis();
                checkTimeEnd = checkTime + time;
                lineMotor.setPower(0);
                lineMotor2.setPower(0);
                servoPos = 0.10;
                servoBlockPos = 0.4;
                stage = 3;
            }

            //when time exceeds limit, start new time, switch stage, and move servo
            if (checkTimeEnd < System.currentTimeMillis() && opModeIsActive() && stage == 3) {
                time = 2600;
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
                lineMotor.setPower(-0.55);
                lineMotor2.setPower(1);
                telemetry.addData("Running", "True");
            }

            //when time exceeds limit, start new time, switch stage, and move servo
            if (checkTimeEnd < System.currentTimeMillis() && opModeIsActive() && stage == 4) {
                time = 750;
                checkTime = System.currentTimeMillis();
                checkTimeEnd = checkTime + time;
                lineMotor.setPower(0);
                lineMotor2.setPower(0);
                servoPos = 0.98;
                servoBlockPos = .88;
                sleep(20);
                stage = 5;
            }

            //while timer is active and stage 5 is active move arm down
            if (checkTimeEnd > System.currentTimeMillis() && opModeIsActive() && stage == 5) {
                lineMotor.setPower(-0.55);
                lineMotor2.setPower(1);
                telemetry.addData("Running", "True");
            }

            if (checkTimeEnd < System.currentTimeMillis() && opModeIsActive() && stage == 5) {
                time = 30;
                checkTime = System.currentTimeMillis();
                checkTimeEnd = checkTime + time;
                lineMotor.setPower(0);
                lineMotor2.setPower(0);
                stage = 22;
                telemetry.addData("Running", "True");
            }
            //endregion

            dropServo.setPosition(servoPos);
            blockServo.setPosition(servoBlockPos);
        }
    }

    @Override
    public void runOpMode() {


        dropServo = hardwareMap.get(Servo.class, "dropServo");

        blockServo = hardwareMap.get(Servo.class, "blockServo");

        servoPos = 0.98;

        dropServo.setPosition(servoPos);
        blockServo.setPosition(servoBlockPos);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        // run until the end of the match (driver presses STOP)


        moveForTime(1600, 270, 0.5);

        turnForTime(600, 0.6);

        moveForTime(900, 270, 0.5);

        dropArm();

        moveForTime(900, 90, 0.5);

        turnForTime(1000, -1);

        moveForTime(1400, 270, 0.6);

        turnForTime(335, -1);

        moveForTime(750, 270, 0.6);

        wheelForTime(3500, 1);

        moveForTime(200, 90, 1);

        turnForTime(1450, 0.6);

        moveForTime(950, 270, 1);

        turnForTime(900, -0.6);

        moveForTime(200, 270, 1);
    }
}
