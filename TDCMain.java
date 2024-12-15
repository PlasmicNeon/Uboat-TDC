/*************************************************************************************
 *  Uboat TDC                                                                        *
 *                                                                                   *
 *  Programmer: Dylan Jones                                                          *
 *  Course: CS201                                                                    *
 *  Date: 12/10/2024                                                                 *
 *  Requirement: Final Project                                                       *
 *                                                                                   *
 *  Description:                                                                     *
 *  The following program is to show as a mock Uboat Torpedo Data Computer from WWII.*
 *  The start is the user presented at a hydrophone and must search a sound source.  *
 *  After the TDC is drawn and the user must input the correct data to ensure they   *
 *  hit the target.                                                                  *
 *                                                                                   *
 *  Copyright: This code is copyright (C) 2024 to Dylan Jones and Dean Zeller.       *
 *  The audio is copyright to Klaus Doldinger.                                       *
 *                                                                                   *
 *  Credits: Audio, Das Boot. Assisted by ChatGPT.                                   *
 ************************************************************************************/

import java.awt.*;
import java.io.IOException;
import java.util.Random;
import java.io.File;
import javax.sound.sampled.*;

public class TDCMain {
    public static void main(String[] args) {
        // Target Data //
        SolData Target = new SolData(0, 0, 0);

        // Canvas setup //
        StdDraw.setCanvasSize(1024, 512);
        StdDraw.setXscale(0.0, 2.0);

        // Audio import //
        String Alarm = "Alarm.wav";
        String hydroStatic = "HydroStatic.wav";
        String hydroScrews = "HydroScrews.wav";
        String Konvoi = "Konvoi.wav";
        String Hit = "Hit.wav";
        String Fire = "Fire.wav";

        Font title = new Font("Arial", Font.BOLD, 60);
        while (true) {
            // Menu Draw //
            StdDraw.enableDoubleBuffering();
            for (double t = 0.0; t >= 0.0; t += 0.01) {
                StdDraw.clear();
                // Sea //
                StdDraw.setPenColor(180, 250, 255);
                StdDraw.filledRectangle(1, .5, 2, 1);
                StdDraw.setPenColor(0, 140, 255);
                StdDraw.filledRectangle(1, .25, 2, .25);
                // Uboat //
                StdDraw.setPenColor(160, 170, 180);
                double[] uboatTowerx = {1.15, 1.15, 1.06, 1.03, 0.94, 0.948};
                double[] uboatTowery = {0.55, 0.655, 0.655, 0.615, 0.615, 0.55};
                StdDraw.filledPolygon(uboatTowerx, uboatTowery); // Tower Shape
                StdDraw.setPenColor(170, 180, 190);
                double[] uboatBodyx = {0.2, 1.64, 1.7, 0.4, 0.2, 0.2};
                double[] uboatBodyy = {0.5, 0.5, 0.57, 0.55, 0.52, 0.5};
                StdDraw.filledPolygon(uboatBodyx, uboatBodyy); // Uboat Shape
                StdDraw.setPenColor(100, 100, 100);
                StdDraw.setPenRadius(.001);
                StdDraw.line(1.15, 0.655, 1.6, 0.57);
                StdDraw.line(0.94, 0.615, 0.4, 0.55);
                // Title //
                StdDraw.setPenColor(0, 0, 0);
                StdDraw.filledRectangle(0.4, 0.8, .285, .125);
                StdDraw.setPenColor(255, 255, 255);
                StdDraw.setFont(title);
                StdDraw.text(0.4, 0.84, "SILENT");
                StdDraw.text(0.4, 0.73, "HUNTER");
                // Play Button //
                StdDraw.setPenColor(0, 0, 0);
                StdDraw.filledRectangle(1, 0.25, 0.2, .07);
                StdDraw.setPenColor(255, 255, 255);
                StdDraw.text(1, 0.24, "Play");
                // Draw set //
                StdDraw.show();
                StdDraw.pause(10);
                // Play Function //
                if (StdDraw.isMousePressed()) {
                    if (StdDraw.mouseX() > 0.8 && StdDraw.mouseX() < 1.2 && StdDraw.mouseY() > 0.17 && StdDraw.mouseY() < 0.31) {
                        t = -1.0;
                    }
                }
            }
            // Target location generation //
            Random rand = new Random();
            SolData tRand = new SolData(rand.nextInt(360), rand.nextInt(2500) + 500, rand.nextInt(20) + 5);

            // Are you winning son? //
            int winner = 0;

            // Hydrophone Draw //
            int bearingHun = 0; // Stores bearing in hundreds
            int bearingTen = 0; // Stores bearing in tens
            int bearingOne = 0; // Stores bearing in ones
            int bearingSum = 0; // Stores entire range
            double degrees = 90.0; // Degree of needle
            int loopStatic = 1; // Audio loop control for hydroStatic
            int loopScrews = 1; // Audio loop control for hydroScrews
            for (double t = 0.0; t >= 0.0; t += 0.01) {
                StdDraw.clear();
                // Math //
                double radians = Math.toRadians(degrees); // Radian converter
                double sinValue = Math.sin(radians); // Sin converter
                double cosValue = Math.cos(radians); // Cos converter
                double needleX = 0.4 + (0.285 * cosValue); // Moves needle x coords
                double needleY = 0.5 + (0.285 * sinValue); // Moves needle y coords
                // Hydro background //
                StdDraw.setPenColor(125, 125, 125);
                StdDraw.filledRectangle(1, 0.5, 1, 0.5);
                // Bearing circle //
                StdDraw.setPenColor(50, 50, 50);
                StdDraw.filledCircle(0.4, 0.5, 0.385);
                StdDraw.setPenColor(255, 250, 170);
                StdDraw.filledCircle(0.4, 0.5, 0.36);
                StdDraw.setPenColor(0, 0, 0);
                StdDraw.filledCircle(0.4, 0.5, 0.29);
                StdDraw.setPenColor(255, 250, 170);
                StdDraw.filledCircle(0.4, 0.5, 0.28);
                StdDraw.setPenColor(0, 0, 0);
                StdDraw.setPenRadius(.01);
                StdDraw.line(0.4, 0.5, 0 + needleX, 0 + needleY); // Needle Draw
                StdDraw.setPenColor(150, 150, 150);
                StdDraw.filledCircle(0.4, 0.5, 0.03);
                StdDraw.setPenColor(0, 0, 0);
                Font bearing = new Font("Arial", Font.PLAIN, 20);
                StdDraw.setFont(bearing);
                StdDraw.text(0.4, 0.825, "0");
                StdDraw.text(0.726, 0.5, "90");
                StdDraw.text(0.4, 0.172, "180");
                StdDraw.text(0.074, 0.5, "270");
                //StdDraw.text(1.6, 0.5, String.valueOf(bearingSum)); // Sum debug
                //StdDraw.text(1.6, 0.55, String.valueOf(t)); // Time debug
                //StdDraw.text(1.6, 0.45, String.valueOf(tRand.getBearing())); // Bearing debug
                // Bearing counter //
                StdDraw.setPenColor(255, 255, 255);
                StdDraw.filledRectangle(0.93, 0.7, 0.05, 0.08);
                StdDraw.filledRectangle(1.05, 0.7, 0.05, 0.08);
                StdDraw.filledRectangle(1.17, 0.7, 0.05, 0.08);
                StdDraw.setPenColor(0, 0, 0);
                Font bearingNum = new Font("Arial", Font.PLAIN, 60);
                StdDraw.setFont(bearingNum);
                StdDraw.text(0.93, 0.685, String.valueOf(bearingHun));
                StdDraw.text(1.05, 0.685, String.valueOf(bearingTen));
                StdDraw.text(1.17, 0.685, String.valueOf(bearingOne));
                // Control wheel //
                StdDraw.setPenColor(90, 90, 90);
                StdDraw.filledCircle(1.05, 0.35, 0.18);
                StdDraw.setPenColor(125, 125, 125);
                StdDraw.filledCircle(1.05, 0.35, 0.15);
                StdDraw.setPenColor(90, 90, 90);
                StdDraw.filledCircle(1.05, 0.35, 0.025);
                StdDraw.setPenRadius(.02);
                StdDraw.line(1.05, 0.5, 1.05, 0.2);
                StdDraw.line(1.2, 0.35, 0.9, 0.35);
                StdDraw.setPenColor(50, 50, 50);
                StdDraw.filledCircle(1.05, 0.35, 0.014);
                // Control wheel function //
                if (StdDraw.isMousePressed()) {
                    // Left side //
                    if (StdDraw.mouseX() > 0.87 && StdDraw.mouseX() < 1.05 && StdDraw.mouseY() > 0.17 && StdDraw.mouseY() < 0.53 && t > 0.05) {
                        if (bearingOne > 0) {
                            bearingOne -= 1;
                            bearingSum -= 1;
                            degrees += 1;
                        } else if (bearingOne == 0 && bearingTen > 0) {
                            bearingOne = 9;
                            bearingTen -= 1;
                            bearingSum -= 1;
                            degrees += 1;
                        } else if (bearingOne == 0 && bearingTen == 0) {
                            bearingOne = 9;
                            bearingTen = 9;
                            bearingHun -= 1;
                            bearingSum -= 1;
                            degrees += 1;
                        }
                        if (bearingSum < 0) {
                            bearingOne = 9;
                            bearingTen = 5;
                            bearingHun = 3;
                            bearingSum = 359;
                            degrees = 91;
                        }
                    }
                    // Right side //
                    if (StdDraw.mouseX() > 1.05 && StdDraw.mouseX() < 1.23 && StdDraw.mouseY() > 0.17 && StdDraw.mouseY() < 0.53 && t > 0.05) {
                        if (bearingOne < 9) {
                            bearingOne += 1;
                            bearingSum += 1;
                            degrees -= 1;
                        } else if (bearingOne == 9 && bearingTen < 9) {
                            bearingOne = 0;
                            bearingTen += 1;
                            bearingSum += 1;
                            degrees -= 1;
                        } else if (bearingOne == 9 && bearingTen == 9) {
                            bearingOne = 0;
                            bearingTen = 0;
                            bearingHun += 1;
                            bearingSum += 1;
                            degrees -= 1;
                        }
                        if (bearingSum > 359) {
                            bearingOne = 0;
                            bearingTen = 0;
                            bearingHun = 0;
                            bearingSum = 0;
                            degrees = 90;
                        }
                    }
                }
                // Bearing calc //
                StdDraw.setPenColor(0, 0, 0);
                StdDraw.filledRectangle(1.7, 0.2, 0.24, 0.07);
                StdDraw.setPenColor(255, 255, 255);
                StdDraw.text(1.7, 0.19, "Schiene");
                // Track function //
                if (bearingSum >= (tRand.getBearing() - 8) && bearingSum <= (tRand.getBearing() + 8)) {
                    if (loopScrews == 1) {
                        AudioPlayer.StopMusic();
                        AudioPlayer.PlayMusic(hydroScrews);
                        loopScrews = 0;
                        loopStatic = 1;
                    }
                    if (StdDraw.isMousePressed()) {
                        if (StdDraw.mouseX() > 1.5 && StdDraw.mouseX() < 1.9 && StdDraw.mouseY() > 0.13 && StdDraw.mouseY() < 0.27) {
                            t = -1.0;
                            AudioPlayer.StopMusic();
                        }
                    }
                }
                // Hydro sound //
                if (loopStatic == 1) {
                    if (bearingSum < (tRand.getBearing() - 8) || bearingSum > (tRand.getBearing() + 8)) {
                        AudioPlayer.StopMusic();
                        AudioPlayer.PlayMusic(hydroStatic);
                        loopStatic = 0;
                        loopScrews = 1;
                    }
                }
                // Draw set //
                StdDraw.show();
                StdDraw.pause(75);
            }
            AudioPlayer.StopMusic();
            double speedDegrees = 90.0; // Target speeds degrees
            int speedInput = 0; // User speed input
            double rangeDegrees = 90.0; // Target range degrees
            int rangeInputTen = 0; // User range input in tens
            int rangeInputOne = 0; // User range input in ones
            int rangeInputSum = 0; // User range total
            int bearingTrack = 0; // Checks user press
            int convoyLoop = 1;
            int alarmLoop = 1;
            for (double t = 0.0; t >= 0.0; t += 0.01) {
                StdDraw.clear();
                // Background //
                StdDraw.setPenColor(160, 160, 160);
                StdDraw.filledRectangle(1, .5, 2, 1);
                // Music //
                if (alarmLoop == 1) {
                    AudioPlayer.PlaySound(Alarm);
                    alarmLoop = 0;
                }
                if (convoyLoop == 1 && t > 0.13) {
                    AudioPlayer.PlayMusic("Konvoi.wav");
                    convoyLoop = 0;
                }
                // Data note //
                StdDraw.setPenColor(255, 255, 200);
                double[] noteDataX = {1.5, 1.5, 1.85, 1.9, 1.9};
                double[] noteDataY = {0.9, 0.5, 0.5, 0.55, 0.9};
                StdDraw.filledPolygon(noteDataX, noteDataY);
                StdDraw.setPenColor(255, 255, 105);
                double[] noteCornerX = {1.85, 1.9, 1.85};
                double[] noteCornerY = {0.5, 0.55, 0.55};
                StdDraw.filledPolygon(noteCornerX, noteCornerY);
                Font handwritten = new Font("Ink Free", Font.PLAIN, 20);
                StdDraw.setFont(handwritten);
                StdDraw.setPenColor(0, 0, 0);
                StdDraw.text(1.7, 0.85, "Target Data");
                StdDraw.text(1.6, 0.76, "Speed:");
                StdDraw.text(1.7, 0.76, String.valueOf(tRand.getSpeed()));
                StdDraw.text(1.8, 0.76, "Knots");
                StdDraw.text(1.58, 0.68, "Range:");
                StdDraw.text(1.7, 0.68, String.valueOf(tRand.getRange()));
                StdDraw.text(1.82, 0.68, "Meters");
                StdDraw.text(1.6, 0.6, "Bearing:");
                StdDraw.text(1.72, 0.6, String.valueOf(tRand.getBearing()));
                // Speed dial //
                double radians = Math.toRadians(speedDegrees); // Radian converter
                double sinValue = Math.sin(radians); // Sin converter
                double cosValue = Math.cos(radians); // Cos converter
                double speedX = 0.74 + (0.17 * cosValue); // Moves needle x coords
                double speedY = 0.7 + (0.17 * sinValue); // Moves needle y coords
                Font titlePlate = new Font("Arial", Font.PLAIN, 20);
                StdDraw.setFont(titlePlate);
                StdDraw.setPenColor(0, 0, 0);
                StdDraw.filledCircle(0.74, 0.7, 0.18);
                StdDraw.filledRectangle(0.74, 0.92, 0.13, 0.024);
                StdDraw.setPenColor(255, 255, 255);
                StdDraw.text(0.74, 0.92, "Gegnerfahrt");
                // Marker detailing //
                StdDraw.setPenRadius(0.005);
                StdDraw.line(0.74, 0.7, 0.8659, 0.8259); // 5 kts
                StdDraw.line(0.74, 0.7, 0.918, 0.7); // 10 kts
                StdDraw.line(0.74, 0.7, 0.8659, 0.5741); // 15 kts
                StdDraw.line(0.74, 0.7, 0.74, 0.522); // 20 kts
                StdDraw.line(0.74, 0.7, 0.6141, 0.5741); // 25 kts
                StdDraw.line(0.74, 0.7, 0.562, 0.7); // 30 kts
                StdDraw.line(0.74, 0.7, 0.6141, 0.8259); // 35 kts
                StdDraw.setPenColor(0, 0, 0);
                StdDraw.filledCircle(0.74, 0.7, 0.16);
                StdDraw.setPenColor(255, 255, 255);
                double[] triX = {0.73, 0.75, 0.74};
                double[] triY = {0.84, 0.84, 0.82};
                StdDraw.filledPolygon(triX, triY); // Triangle
                StdDraw.text(0.8344, 0.7944, "5");
                StdDraw.text(0.8735, 0.7, "10");
                StdDraw.text(0.8344, 0.6056, "15");
                StdDraw.text(0.74, 0.5665, "20");
                StdDraw.text(0.6456, 0.6056, "25");
                StdDraw.text(0.6065, 0.7, "30");
                StdDraw.text(0.6456, 0.7944, "35");
                StdDraw.setPenColor(255, 205, 155);
                StdDraw.setPenRadius(.007);
                StdDraw.line(0.74, 0.7, 0 + speedX, 0 + speedY); // Needle
                StdDraw.setPenColor(0, 0, 0);
                StdDraw.filledCircle(0.92, 0.84, 0.03); // Increase
                StdDraw.setPenColor(25, 50, 0);
                StdDraw.filledCircle(0.92, 0.84, 0.02);
                StdDraw.setPenColor(0, 0, 0);
                StdDraw.filledCircle(0.92, 0.56, 0.03); // Decrease
                StdDraw.setPenColor(50, 0, 0);
                StdDraw.filledCircle(0.92, 0.56, 0.02);
                // Speed calc //
                if (StdDraw.isMousePressed()) {
                    // Increase speed //
                    if (StdDraw.mouseX() > 0.89 && StdDraw.mouseX() < 0.95 && StdDraw.mouseY() > 0.81 && StdDraw.mouseY() < 0.87) {
                        if (speedDegrees != -225.0) {
                            speedInput += 1;
                            speedDegrees -= 9;
                        }
                    }
                    // Decrease speed //
                    if (StdDraw.mouseX() > 0.89 && StdDraw.mouseX() < 0.95 && StdDraw.mouseY() > 0.53 && StdDraw.mouseY() < 0.59) {
                        if (speedDegrees != 90) {
                            speedInput -= 1;
                            speedDegrees += 9;
                        }
                    }
                }
                // Range dial //
                Font rangePlate = new Font("Arial", Font.PLAIN, 12);
                StdDraw.setPenColor(0, 0, 0);
                StdDraw.filledCircle(0.74, 0.25, 0.18);
                StdDraw.filledRectangle(0.74, 0.47, 0.17, 0.024);
                StdDraw.setPenColor(255, 255, 255);
                StdDraw.text(0.74, 0.47, "Schußentfernung");
                StdDraw.setFont(rangePlate);
                StdDraw.setPenRadius(0.002);
                StdDraw.line(0.74, 0.25, 0.7713, 0.4273); // 3
                StdDraw.line(0.74, 0.25, 0.8016, 0.4191); // 4
                StdDraw.line(0.74, 0.25, 0.83, 0.4059); // 5
                StdDraw.line(0.74, 0.25, 0.8557, 0.3879); // 6
                StdDraw.line(0.74, 0.25, 0.8779, 0.3657); // 7
                StdDraw.line(0.74, 0.25, 0.8959, 0.34); // 8
                StdDraw.line(0.74, 0.25, 0.9091, 0.3116); // 9
                StdDraw.line(0.74, 0.25, 0.9173, 0.2813); // 10
                StdDraw.line(0.74, 0.25, 0.74, 0.07); // 20
                StdDraw.line(0.74, 0.25, 0.5627, 0.2813); // 30
                StdDraw.setPenColor(0, 0, 0);
                StdDraw.setPenRadius(0.032);
                StdDraw.circle(0.74, 0.24, 0.175);
                StdDraw.setPenColor(200, 200, 200);
                StdDraw.text(0.74, 0.39, "hm");
                StdDraw.text(0.7682, 0.4096, "3");
                StdDraw.text(0.7954, 0.4022, "4");
                StdDraw.text(0.821, 0.3903, "5");
                StdDraw.text(0.8441, 0.3741, "6");
                StdDraw.text(0.8641, 0.3541, "7");
                StdDraw.text(0.8803, 0.331, "8");
                StdDraw.text(0.8922, 0.3054, "9");
                StdDraw.text(0.903, 0.2782, "10");
                StdDraw.text(0.74, 0.066, "20");
                StdDraw.text(0.57, 0.2782, "30");
                StdDraw.setPenColor(0, 0, 0);
                StdDraw.filledCircle(0.74, 0.25, 0.135);
                // Range input //
                double radiansR = Math.toRadians(rangeDegrees); // Radian converter
                double sinValueR = Math.sin(radiansR); // Sin converter
                double cosValueR = Math.cos(radiansR); // Cos converter
                double rangeX = 0.74 + (0.13 * cosValueR); // Moves needle x coords
                double rangeY = 0.25 + (0.13 * sinValueR); // Moves needle y coords
                StdDraw.setPenColor(255, 255, 255);
                StdDraw.filledRectangle(0.22, 0.30, 0.04, 0.06); // Tens
                StdDraw.filledRectangle(0.32, 0.30, 0.04, 0.06); // Ones
                StdDraw.setPenColor(0, 0, 0);
                Font rangeNum = new Font("Arial", Font.PLAIN, 40);
                StdDraw.setFont(rangeNum);
                StdDraw.text(0.22, 0.29, String.valueOf(rangeInputTen));
                StdDraw.text(0.32, 0.29, String.valueOf(rangeInputOne));
                StdDraw.setPenColor(255, 255, 145);
                StdDraw.filledSquare(0.22, 0.16, 0.04);
                StdDraw.filledSquare(0.32, 0.16, 0.04);
                StdDraw.filledSquare(0.27, 0.06, 0.04);
                StdDraw.setPenColor(255, 255, 160);
                StdDraw.filledSquare(0.22, 0.16, 0.03);
                StdDraw.filledSquare(0.32, 0.16, 0.03);
                StdDraw.filledSquare(0.27, 0.06, 0.03);
                StdDraw.setFont(titlePlate);
                if (StdDraw.isMousePressed()) {
                    // Tens //
                    if (StdDraw.mouseX() > 0.19 && StdDraw.mouseX() < 0.25 && StdDraw.mouseY() > 0.13 && StdDraw.mouseY() < 0.19) {
                        if (rangeInputTen < 9) {
                            rangeInputTen += 1;
                        } else if (rangeInputTen == 9) {
                            rangeInputTen = 0;
                        }
                    }
                    // Ones //
                    if (StdDraw.mouseX() > 0.29 && StdDraw.mouseX() < 0.35 && StdDraw.mouseY() > 0.13 && StdDraw.mouseY() < 0.19) {
                        if (rangeInputOne < 9) {
                            rangeInputOne += 1;
                        } else if (rangeInputOne == 9) {
                            rangeInputOne = 0;
                        }
                    }
                    // Sum //
                    if (StdDraw.mouseX() > 0.23 && StdDraw.mouseX() < 0.31 && StdDraw.mouseY() > 0.02 && StdDraw.mouseY() < 0.1) {
                        for (int r = 2; r < rangeInputSum; r++) {
                            rangeDegrees -= 10;
                        }
                    }
                }
                // Range arrow //
                StdDraw.setPenColor(200, 0, 0);
                StdDraw.setPenRadius(0.002);
                StdDraw.line(0.74, 0.25, rangeX, rangeY);
                StdDraw.setPenColor(120, 120, 120);
                StdDraw.filledCircle(0.74, 0.25, 0.01);
                // Impact angle //
                StdDraw.setPenColor(0, 0, 0);
                StdDraw.filledCircle(0.28, 0.7, 0.18);
                StdDraw.filledRectangle(0.28, 0.92, 0.15, 0.024);
                StdDraw.setPenColor(255, 255, 255);
                StdDraw.text(0.28, 0.915, "Vorhaltewinkel");
                StdDraw.setPenColor(0, 200, 0);
                StdDraw.line(0.281, 0.85, 0.281, 0.55);
                StdDraw.arc(0.28, 0.7, 0.15, 270, 90);
                StdDraw.setPenColor(200, 0, 0);
                StdDraw.line(0.279, 0.85, 0.279, 0.55);
                StdDraw.arc(0.28, 0.7, 0.15, 90, 270);
                // Bearing dial //
                StdDraw.setPenColor(0, 0, 0);
                StdDraw.filledCircle(1.2, 0.7, 0.18); // Top bearing
                StdDraw.filledCircle(1.2, 0.2, 0.18); // Bottom bearing
                StdDraw.filledRectangle(1.2, 0.45, 0.18, 0.25);
                StdDraw.filledRectangle(1.2, 0.92, 0.15, 0.024);
                StdDraw.setPenColor(255, 255, 255);
                StdDraw.text(1.2, 0.92, "Schiffspeilung");
                StdDraw.setPenColor(255, 255, 255);
                StdDraw.filledCircle(1.2, 0.7, 0.14);
                StdDraw.filledCircle(1.2, 0.2, 0.14);
                StdDraw.setPenColor(0, 0, 0);
                Font bearingNum = new Font("Arial", Font.PLAIN, 60);
                StdDraw.setFont(bearingNum);
                StdDraw.text(1.2, 0.7, String.valueOf(bearingOne));
                StdDraw.text(1.2, 0.2, String.valueOf((bearingHun * 10) + bearingTen));
                // Track //
                StdDraw.setPenColor(0, 0, 0);
                StdDraw.filledCircle(1.44, 0.45, 0.03);
                StdDraw.setPenColor(0, 0, 255);
                StdDraw.filledCircle(1.44, 0.45, 0.02);
                if (bearingTrack == 0) {
                    StdDraw.setPenColor(255, 0, 0);
                    StdDraw.filledCircle(1.2, 0.45, 0.02);
                }
                else if (bearingTrack == 1) {
                    StdDraw.setPenColor(0, 255, 0);
                    StdDraw.filledCircle(1.2, 0.45, 0.02);
                }
                if (StdDraw.isMousePressed()) {
                    if (StdDraw.mouseX() > 1.41 && StdDraw.mouseX() < 1.47 && StdDraw.mouseY() > 0.42 && StdDraw.mouseY() < 0.48) {
                        bearingTrack = 1;
                    }
                }
                // Fire button //
                StdDraw.setPenColor(210, 210, 210);
                StdDraw.filledCircle(1.7, 0.2, 0.07);
                StdDraw.setPenColor(200, 0, 0);
                StdDraw.filledCircle(1.7, 0.2, 0.06);
                rangeInputSum = (rangeInputTen * 10) + rangeInputOne;
                if (StdDraw.isMousePressed()) {
                    if (StdDraw.mouseX() > 1.63 && StdDraw.mouseX() < 1.77 && StdDraw.mouseY() > 0.13 && StdDraw.mouseY() < 0.27 && t > 0.1) {
                        if (speedInput == tRand.getSpeed() && rangeInputSum == (tRand.getRange() / 100) && bearingSum >= (tRand.getBearing() - 5) && bearingSum <= (tRand.getBearing() + 5) && bearingTrack == 1) {
                            winner = 1;
                        } else {
                            winner = 0;
                        }
                        AudioPlayer.StopMusic();
                        t = -1.0;
                    }
                }
                // Draw set //
                StdDraw.show();
                StdDraw.pause(100);
            }
            // End screen //
            double torpWidth = 0.01;
            double torpLength = 0.04;
            double explosionAni = 0;
            double sinking = 0;
            double removeSmoke = 0;
            int hitLoop = 1;
            int fireLoop = 1;
            if (winner == 1) {
                for (double t = 0.0; t >= 0.0; t += 0.005) {
                    StdDraw.clear();
                    if (fireLoop == 1) {
                        AudioPlayer.PlaySound(Fire);
                        fireLoop = 0;
                    }
                    // Periscope background //
                    StdDraw.setPenColor(0, 0, 0);
                    StdDraw.filledSquare(1, .5, 1);
                    // Background //
                    StdDraw.setPenColor(21, 25, 69);
                    StdDraw.filledSquare(1, 0.54, 0.3);
                    StdDraw.setPenColor(71, 53, 75);
                    StdDraw.filledSquare(1, 0.46, 0.3);
                    StdDraw.setPenColor(178, 67, 66);
                    StdDraw.filledSquare(1, 0.38, 0.3);
                    StdDraw.setPenColor(225, 137, 87);
                    StdDraw.filledSquare(1, 0.3, 0.3);
                    // Boat //
                    if (t > 0.8) {
                        sinking += 0.0001;
                    }
                    StdDraw.setPenColor(80, 80, 80);
                    StdDraw.filledRectangle(1.013, 0.522 - sinking, 0.0026, 0.005);
                    StdDraw.filledRectangle(1.01, 0.515 - sinking, 0.01, 0.005);
                    StdDraw.filledRectangle(0.99, 0.52 - sinking, 0.0025, 0.01);
                    StdDraw.filledRectangle(0.965, 0.515 - sinking, 0.0025, 0.005);
                    StdDraw.filledRectangle(1.03, 0.52 - sinking, 0.0025, 0.01);
                    StdDraw.setPenColor(100, 100, 100);
                    double[] hullX = {0.95, 1.05, 1.05, 0.944, 0.95};
                    double[] hullY = {0.5 - sinking, 0.5 - sinking, 0.51 - sinking, 0.51 - sinking, 0.5 - sinking};
                    StdDraw.filledPolygon(hullX, hullY);
                    StdDraw.setPenColor(40, 40, 40);
                    if (t > 0.8) {
                        removeSmoke = 0.4;
                    }
                    StdDraw.filledEllipse(1.013, 0.53 - removeSmoke, 0.002, 0.005);
                    StdDraw.filledEllipse(1.015, 0.535 - removeSmoke, 0.002, 0.005);
                    StdDraw.filledEllipse(1.017, 0.54 - removeSmoke, 0.002, 0.005);
                    StdDraw.filledEllipse(1.019, 0.545 - removeSmoke, 0.002, 0.005);
                    StdDraw.filledEllipse(1.021, 0.55 - removeSmoke, 0.002, 0.005);
                    // Explosion //
                    if (t > 0.4) {
                        if (hitLoop == 1) {
                            AudioPlayer.PlayMusic(Hit);
                            hitLoop = 0;
                        }
                        StdDraw.setPenColor(200, 230, 255);
                        if (t <= 0.42) {
                            explosionAni += 0.015;
                            StdDraw.filledEllipse(1.01, 0.5, (explosionAni / 2), explosionAni);
                        } else if (explosionAni > 0.001) {
                            explosionAni -= 0.001;
                            StdDraw.filledEllipse(1.01, 0.5, (explosionAni / 2), explosionAni);
                        }
                    }
                    // Water //
                    StdDraw.setPenColor(0, 75, 150);
                    StdDraw.filledSquare(1, 0.2, 0.3);
                    // Torpedo //
                    torpWidth -= (t / 487.5);
                    torpLength -= (t / 149.5);
                    if (torpWidth >= 0) {
                        StdDraw.setPenColor(160, 160, 160);
                        StdDraw.filledRectangle(1.01, 0.18 + (t * 1.3), torpWidth, torpLength);
                    }
                    // Periscope //
                    StdDraw.setPenRadius(0.002); // Cross
                    StdDraw.setPenColor(0, 0, 0);
                    StdDraw.line(0, 0.5, 2, 0.5);
                    StdDraw.line(1, 0, 1, 1);
                    StdDraw.setPenRadius(0.2); // Circle
                    StdDraw.circle(1, 0.5, 0.4);
                    StdDraw.filledRectangle(1, 0.1, 1, 0.1);
                    // Win text //
                    StdDraw.setFont(title);
                    StdDraw.setPenColor(255, 255, 255);
                    if (t > 0.8) {
                        StdDraw.text(1, 0.1, "Target Sunk!");
                    }
                    // Draw //
                    StdDraw.show();
                    StdDraw.pause(75);
                    if (t > 1.6) {
                        AudioPlayer.StopMusic();
                        t = -1;
                    }
                }
            }
            if (winner == 0) {
                for (double t = 0.0; t >= 0.0; t += 0.005) {
                    StdDraw.clear();
                    if (fireLoop == 1) {
                        AudioPlayer.PlaySound(Fire);
                        fireLoop = 0;
                    }
                    // Periscope background //
                    StdDraw.setPenColor(0, 0, 0);
                    StdDraw.filledSquare(1, .5, 1);
                    // Background //
                    StdDraw.setPenColor(21, 25, 69);
                    StdDraw.filledSquare(1, 0.54, 0.3);
                    StdDraw.setPenColor(71, 53, 75);
                    StdDraw.filledSquare(1, 0.46, 0.3);
                    StdDraw.setPenColor(178, 67, 66);
                    StdDraw.filledSquare(1, 0.38, 0.3);
                    StdDraw.setPenColor(225, 137, 87);
                    StdDraw.filledSquare(1, 0.3, 0.3);
                    // Boat //
                    StdDraw.setPenColor(80, 80, 80);
                    StdDraw.filledRectangle(1.013, 0.522, 0.0026, 0.005);
                    StdDraw.filledRectangle(1.01, 0.515, 0.01, 0.005);
                    StdDraw.filledRectangle(0.99, 0.52, 0.0025, 0.01);
                    StdDraw.filledRectangle(0.965, 0.515, 0.0025, 0.005);
                    StdDraw.filledRectangle(1.03, 0.52, 0.0025, 0.01);
                    StdDraw.setPenColor(100, 100, 100);
                    double[] hullX = {0.95, 1.05, 1.05, 0.944, 0.95};
                    double[] hullY = {0.5, 0.5, 0.51, 0.51, 0.5};
                    StdDraw.filledPolygon(hullX, hullY);
                    StdDraw.setPenColor(40, 40, 40);
                    StdDraw.filledEllipse(1.013, 0.53, 0.002, 0.005);
                    StdDraw.filledEllipse(1.015, 0.535, 0.002, 0.005);
                    StdDraw.filledEllipse(1.017, 0.54, 0.002, 0.005);
                    StdDraw.filledEllipse(1.019, 0.545, 0.002, 0.005);
                    StdDraw.filledEllipse(1.021, 0.55, 0.002, 0.005);
                    // Water //
                    StdDraw.setPenColor(0, 75, 150);
                    StdDraw.filledSquare(1, 0.2, 0.3);
                    // Torpedo //
                    torpWidth -= (t / 487.5);
                    torpLength -= (t / 149.5);
                    if (torpWidth >= 0) {
                        StdDraw.setPenColor(160, 160, 160);
                        StdDraw.filledRectangle(1.01, 0.18 + (t * 1.3), torpWidth, torpLength);
                    }
                    // Periscope //
                    StdDraw.setPenRadius(0.002); // Cross
                    StdDraw.setPenColor(0, 0, 0);
                    StdDraw.line(0, 0.5, 2, 0.5);
                    StdDraw.line(1, 0, 1, 1);
                    StdDraw.setPenRadius(0.2); // Circle
                    StdDraw.circle(1, 0.5, 0.4);
                    StdDraw.filledRectangle(1, 0.1, 1, 0.1);
                    // Lose text //
                    StdDraw.setFont(title);
                    StdDraw.setPenColor(255, 255, 255);
                    if (t > 0.6) {
                        StdDraw.text(1, 0.1, "Target Missed!");
                    }
                    // Draw //
                    StdDraw.show();
                    StdDraw.pause(75);
                    if (t > 1) {
                        AudioPlayer.StopMusic();
                        t = -1;
                    }
                }
            }
        }
    }

    public static class AudioPlayer {
        private static Clip clip;

        /*****************************************************************************
         * Method: PlayMusic()                                                       *
         * Desc: checks if musicPath exists and loads the clip, loops it, and plays  *
         * Para: music clip in .wav                                                  *
         * Return: none                                                              *
         ****************************************************************************/

        public static void PlayMusic(String location) {
            try {
                File musicPath = new File(location);
                if (musicPath.exists()) {
                    AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                    clip = AudioSystem.getClip();
                    clip.open(audioInput);
                    clip.setLoopPoints(0, -1); // Set loop points
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    clip.start();
                } else {
                    System.out.println("File not found: " + location);
                }
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }

        /*****************************************************************************
         * Method: PlaySound()                                                       *
         * Desc: checks if soundPath exists and loads the clip, loops it, and plays  *
         * Para: sound clip in .wav                                                  *
         * Return: none                                                              *
         ****************************************************************************/

        public static void PlaySound(String location) {
            try {
                File soundPath = new File(location);
                if (soundPath.exists()) {
                    AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundPath);
                    clip = AudioSystem.getClip();
                    clip.open(audioInput);
                    clip.setLoopPoints(0, -1); // Set loop points
                    clip.loop(0);
                    clip.start();
                } else {
                    System.out.println("File not found: " + location);
                }
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }

        /*****************************************************************************
         * Method: StopMusic()                                                       *
         * Desc: checks if a clip is running and stops it                            *
         * Para: none                                                                *
         * Return: none                                                              *
         ****************************************************************************/

        public static void StopMusic() {
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close(); // Unloads the sound
            }
        }
    }
}