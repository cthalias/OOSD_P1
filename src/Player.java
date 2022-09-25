import bagel.*;
import bagel.Font;
import bagel.Image;

import java.awt.*;

public class Player {
    //attribute declaration
    protected Integer health;
    protected double playerX;
    protected double playerY;
    protected Rectangle playerRec;
    protected double playerX_before = 0;
    protected double playerY_before = 0;
    protected int boundTop;
    protected int boundLeft;
    protected int boundBottom;
    protected int boundRight;

    //colour declaration
    protected DrawOptions colour = new DrawOptions();

    //image declaration
    protected final Image playerLeftImage = new Image("res/faeLeft.png");
    protected final Image playerRightImage = new Image("res/faeRight.png");

    //font declaration
    protected final Font healthFont= new Font("res/frostbite.ttf", 30);

    //flag variable declaration
    private boolean draw;
    private boolean right;
    protected boolean move;

    //constructor
    public Player(double playerX, double playerY){
        this.health= 100;

        this.playerX = playerX;
        this.playerY = playerY;

        this.draw = true;
        this.right = true;

        double width = playerRightImage.getWidth();
        double height = playerRightImage.getHeight();

        this.playerRec = new Rectangle((int) playerX, (int) playerY, (int) width, (int) height);
    }

    //updates the player movements based on input
    public void update(Input input){

        double speed = 2;

        if(draw){
            if(right) {
                playerRightImage.drawFromTopLeft(playerX, playerY);
            } else {
                playerLeftImage.drawFromTopLeft(playerX, playerY);
            }
            //determines the colour of the health bar
            if(health > 65) {
                healthFont.drawString(health + "%", 20, 25, colour.setBlendColour(0, 0.8, 0.2));
            } else if (health > 35){
                healthFont.drawString(health + "%", 20, 25, colour.setBlendColour(0.9, 0.6, 0));
            } else {
                healthFont.drawString(health + "%", 20, 25, colour.setBlendColour(1, 0, 0));
            }
        }
        if(move){
            playerX_before = playerX;
            playerY_before = playerY;
        }

        //move the player according to the input
        if ((input.isDown(Keys.LEFT)) && (playerX >= boundLeft) && (move)) {
            playerX -= speed;
            right = false;
        }
        if ((input.isDown(Keys.RIGHT)) && (playerX <= boundRight) && (move)){
            playerX += speed;
            right = true;
        }
        if ((input.isDown(Keys.UP)) && (playerY >= boundTop) && (move)){
            playerY -= speed;
        }
         if ((input.isDown(Keys.DOWN)) && (playerY <= boundBottom) && (move)) {
            playerY += speed;
        }

         //the game is won after the player reaches the portal
         if((playerX > 950 )&&(playerY > 670)){
            draw = false;
            ShadowDimension.win = true;
        }

         //bounces back when player hits the border
        if ((input.isDown(Keys.LEFT)) && (playerX <= boundLeft)) {
            playerX += 1;
            right = false;
        }
        if ((input.isDown(Keys.RIGHT)) && (playerX >= boundRight)){
            playerX -= 1;
            right = true;
        }
        if ((input.isDown(Keys.UP)) && (playerY <= boundTop)){
            playerY += 1;
        }
        if ((input.isDown(Keys.DOWN)) && (playerY >= boundBottom)) {
            playerY -= speed;
        }

        //the game is over when player runs out of health points
        if(health <= 0){
            draw = false;
            ShadowDimension.lose = true;
        }

        //updates player's rectangle to follow player's image
        playerRec.x = (int) playerX;
        playerRec.y = (int) playerY;

    }

    //prints player as a string
    public String toString(){
        return "playerX =" + playerX + "\n" + "playerY =" + playerY + "\n" + "playerX_before =" + playerX_before + "\n"
        + "playerY_before =" + playerY_before + "\n";
    }
}
