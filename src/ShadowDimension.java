import bagel.*;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 2, 2022
 *
 * Please enter your name below
 * @author Christhalia Sanjaya
 */

public class ShadowDimension extends AbstractGame {

    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static int MAX_INPUT = 60;

    private final static String GAME_TITLE = "SHADOW DIMENSION";
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");

    //image declarations
    private final Image wallImage = new Image("res/wall.png");
    private final Image sinkholeImage = new Image("res/sinkhole.png");

    //font declarations
    private final Font fontTitle = new Font("res/frostbite.ttf", 75);
    private final Font font= new Font("res/frostbite.ttf", 40);

    //entities declarations
    protected Player player = new Player(0,0);
    protected Sinkhole[] sinkholeList = new Sinkhole[MAX_INPUT];
    public Wall[] wallList = new Wall[MAX_INPUT];

    //input list for csv file
    public String[] inputList = new String[MAX_INPUT];

    //flag variables declarations
    static boolean start = false;
    static boolean win = false;
    static boolean lose = false;

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDimension game = new ShadowDimension();
        game.run();
    }

    /**
     * Method used to read file and create objects (You can change this
     * method as you wish).
     */
    private void readCSV(){
        try (BufferedReader br = new BufferedReader(new FileReader("res/level0.csv"))) {
            String text;
            int num = 0;

            while ((text = br.readLine()) != null) {
                inputList[num] = text;
                num += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //constructor of ShadowDimension class and create objects
    public ShadowDimension(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        readCSV();

        int num_wall = 0;
        int num_sinkhole = 0;

        //iterates through the stored csv file and creates objects
        for(String elem : inputList) {

            String[] drawString;
            drawString = elem.split(",");

            int temp_x = Integer.parseInt(drawString[1]);
            int temp_y = Integer.parseInt(drawString[2]);

            switch (drawString[0]) {
                case "Wall":
                    wallList[num_wall] = new Wall(temp_x, temp_y);
                    num_wall += 1;
                    break;

                case "Sinkhole":
                    sinkholeList[num_sinkhole] = new Sinkhole(temp_x, temp_y);
                    num_sinkhole += 1;
                    break;

                case "Player":
                    player.playerX = temp_x;
                    player.playerY = temp_y;
                    break;

                case "Topleft":
                    player.boundLeft = temp_x;
                    player.boundTop = temp_y;
                    break;

                case "BottomRight":
                    player.boundRight = temp_x;
                    player.boundBottom = temp_y;
                    break;
            }
        }
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {
        //starts the game according to input
        if ((input.wasPressed(Keys.SPACE))&&(!start)){
            start = true;
        }

        //prints initial start screen
        if(!start){
            fontTitle.drawString("SHADOW DIMENSION!", 260, 250);
            font.drawString("PRESS SPACE TO START \nUSE ARROW KEYS TO FIND GATE", 350, 440);
        }

        //runs the game when it has been started, but still has no win or lose declaration
        if((start)&&(!win)&&(!lose)){
            BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
            player.move = true;

            //checks if any of the wall intersects with the player
            for (Wall wall : wallList){
                if(wall != null) {
                    wallImage.drawFromTopLeft(wall.x, wall.y);

                    if(wall.wallRec.intersects(player.playerRec)){
                        player.move = false;
                        player.playerX = player.playerX_before;
                        player.playerY = player.playerY_before;
                    }
                } else{
                    break;
                }
            }

            //checks if any of the sinkhole intersects with the player
            for (Sinkhole sinkhole : sinkholeList){
                if((sinkhole != null) && (!sinkhole.crashed)){
                    if (sinkhole.sinkholeRec.intersects(player.playerRec)){
                        sinkhole.crashed = true;
                        player.health -= 30;
                        System.out.println("Sinkhole inflicts 30 damage points on Fae. Fae’s current health : "
                                + player.health + "/100");
                    }
                    if (!sinkhole.crashed){
                        sinkholeImage.drawFromTopLeft(sinkhole.x, sinkhole.y);
                    }
                }
            }
            //calls the update method in player class
            player.update(input);

        }

        //win condition
        if(win){
            fontTitle.drawString("CONGRATULATIONS!",
                    (Window.getWidth()/2.0) - fontTitle.getWidth("CONGRATULATIONS!")/2,
                    Window.getHeight()/2.0 + 75/2.0);
        }

        //lose condition
        if(lose){
            fontTitle.drawString("GAME OVER!",
                    (Window.getWidth()/2.0) - fontTitle.getWidth("GAME OVER!")/2,
                    (Window.getHeight()/2.0) + 75/2.0);
        }

        //exit condition
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
    }
}