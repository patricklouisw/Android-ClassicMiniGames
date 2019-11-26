package com.example.myapplication.FlappyFish.GameObjects;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.myapplication.FlappyFish.FlappyGameStatus;

public class FlappyGameFish extends FlappyGameObjects implements Parcelable {

    /** The default x coordinate of the fish. */
    private static final int FISH_X = 10;

    /** The default y coordinate of the fish. */
    private static final int FISH_Y = 500;

    /** The default speed of the fish when falling down. */
    private static final int DEFAULT_DROP_SPEED = 2;

    private static final double STAGE_INCREASE_SPEED = 0.1;

    /** The default speed of the fish when jumping up. */
    private static final int DEFAULT_JUMP_SPEED = -35;

    private int currDropSpeed;

    private int currJumpSpeed;

    /**
     * Construct a new fish object at the default starting coordinates with velocity of 0.
     */
    public FlappyGameFish() {
        super(FISH_X, FISH_Y, 0);
    }

    /**
     * Build the fish object from Parcel.
     * @param in the parcel that stores the previously saved fish object.
     */
    private FlappyGameFish(Parcel in) {
        super(in);
        currDropSpeed = in.readInt();
        currJumpSpeed = in.readInt();
    }

    @Override
    public void setGameDefault() {
        this.currDropSpeed = DEFAULT_DROP_SPEED;
        this.currJumpSpeed = DEFAULT_JUMP_SPEED;
    }

    @Override
    public void increaseGameStage() {
        this.currDropSpeed += STAGE_INCREASE_SPEED;
        this.currJumpSpeed += STAGE_INCREASE_SPEED;
    }

    /**
     * Move the fish according to its current velocity.
     */
    public void move() {
        setY(getY() + getVelocity());
    }

    /**
     * Update the game object according to the specified game status
     * and ensure the game object does not move out of the screen
     * using minY and maxY.
     * @param gameStatus the game status object that tracks this fish.
     * @param canvasWidth the width of the canvas this fish is drawn on.
     * @param minY the minimum value for this fish's y coordinate.
     * @param maxY the maximum value for this fish's coordinate.
     */
    public boolean update(FlappyGameStatus gameStatus, int canvasWidth, int minY, int maxY) {
        validCheck(canvasWidth, minY, maxY);
        setFishFallSpeed();
        return false;
    }

    /**
     * Ensure the fish does not move out of the screen.
     * @param minY the minimum value for this fish's y coordinate.
     * @param maxY the maximum value for this fish's coordinate.
     */
    void validCheck(int canvasWidth, int minY, int maxY) {
        if (getY() < minY) {
            setY(minY);
        }
        if (getY() > maxY) {
            setY(maxY);
        }
    }

    /** Set the jump speed for fish. */
    public void setFishJumpSpeed() {
        setVelocity(currJumpSpeed);
    }

    /** Set the fall speed for fish. */
    private void setFishFallSpeed() {
        setVelocity(getVelocity() + currDropSpeed);
    }

    /**
     * Binds the FlappyGameFish object.
     */
    public static final Creator<FlappyGameFish> CREATOR = new Parcelable.Creator<FlappyGameFish>() {
        public FlappyGameFish createFromParcel(Parcel in){
            return new FlappyGameFish(in);
        }

        public FlappyGameFish[] newArray(int size) {
            return new FlappyGameFish[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(currDropSpeed);
        parcel.writeInt(currJumpSpeed);
    }
}
