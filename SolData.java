/***************************************************************************
 * Solution Data                                                           *
 *                                                                         *
 * Programmer: Dylan Jones                                                 *
 * Course: CS201                                                           *
 * Date: 12/10/2024                                                        *
 * Requirement: Final Project                                              *
 *                                                                         *
 * Description:                                                            *
 * The following class is made to store data to be generated for the TDC.  *
 * Three variables are made for the speed, bearing, and range of the target*
 * The data is declared, set, and given.                                   *
 *                                                                         *
 * Copyright: This code is copyright (C) 2024 Dylan Jones and Dean Zeller. *
 *                                                                         *
 * Credits: N/A                                                            *
 **************************************************************************/

public class SolData {
    // Declaring Variables
    private int Bearing;
    private int Range;
    private int Speed;

    /**************************************
     * Method: SolData()                  *
     * Desc: Calls the created vars       *
     * Para: this.var                     *
     * Return: The setted or getted vars  *
     *************************************/

    // Constructor
    public SolData(int Bearing, int Range, int Speed) {
        this.Bearing = Bearing;
        this.Range = Range;
        this.Speed = Speed;
    }

    /**************************************************
     * Method: getBearing()                           *
     * Desc: gets the Bearing var                     *
     * Para: none                                     *
     * Return: stored Bearing                         *
     *************************************************/

    // Getter
    public int getBearing() {
        return Bearing;
    }

    /**************************************************
     * Method: getRange()                             *
     * Desc: gets the Range var                       *
     * Para: none                                     *
     * Return: stored Range                           *
     *************************************************/

    public int getRange() {
        return Range;
    }

    /**************************************************
     * Method: getSpeed()                             *
     * Desc: gets the Range var                       *
     * Para: none                                     *
     * Return: stored Range                           *
     *************************************************/

    public int getSpeed() {
        return Speed;
    }

    /**************************************************
     * Method: setBearing()                           *
     * Desc: sets a generated bearing                 *
     * Para: bearing                                  *
     * Return: updated or new bearing value           *
     *************************************************/

    // Setter
    public void setBearing(int newBearing) {
        this.Bearing = newBearing;
    }

    /**************************************************
     * Method: setRange()                             *
     * Desc: sets a generated range                   *
     * Para: range                                    *
     * Return: updated or new range value             *
     *************************************************/

    public void setRange(int newRange) {
        this.Range = newRange;
    }

    /**************************************************
     * Method: setSpeed()                             *
     * Desc: sets a generated speed                   *
     * Para: speed                                    *
     * Return: updated or new speed value             *
     *************************************************/

    public void setSpeed(int newSpeed) {
        this.Speed = newSpeed;
    }
}
