package View.CLI;

import model.cards.*;
import model.enums.SuitEnum;
import model.objectives.*;

import java.util.ArrayList;

public class CardMatrixCreator {

    private static final String black = "\033[0;30m";
    private static  final String red = "\033[0;31m";
    private static  final String green = "\033[0;32m";
    private static final String yellow = "\033[0;33m";
    private static final String reset = "\033[0m";
    private static final String purple = "\033[0;35m";
    private static final String blue = "\033[0;34m";
    private static final String beige = "\u001B[38;2;245;245;220m";
    private static final String lightBlue = "\u001B[38;5;39m";
    private static final String gold = "\u001B[38;2;255;215;0m";
    private static final String quill = gold + "Q";
    private static final String manuscript = gold + "M";
    private static final String inkwell = gold + "W";
    
    public void createFrontPlayingCard(CardPlaying card){
    }
    
    public void createBackPlayingCard(CardPlaying card){
    }

    public static String[][] createFrontPlayingCard(CardResource card) {
        String[][] resCard = new String[3][7];
        int rows = resCard.length;
        int cols = resCard[0].length;
        String color = colorResource(card);
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                resCard[i][j] = color + "█";
            }
        }
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if (isUpLeftCorner(i, j, rows, cols)) {
                    resCard[i][j] = cornerScanner(card.getFront().getUpLeft().getDrawing());
                    resCard[i][j+1] = " ";
                    j = j+1;
                } else if (isDownLeftCorner(i, j, rows, cols)) {
                    resCard[i][j] = cornerScanner(card.getFront().getDownLeft().getDrawing());
                    resCard[i][j+1] = " ";
                    j = j+1;
                } else if (isUpRightCorner(i, j, rows, cols)) {
                    resCard[i][j] = cornerScanner(card.getFront().getUpRight().getDrawing());
                    resCard[i][j-1] = " ";
                } else if (isDownRightCorner(i, j, rows, cols)) {
                    resCard[i][j] = cornerScanner(card.getFront().getDownRight().getDrawing()) + reset;
                    resCard[i][j-1] = " ";
                } else {
                    resCard[i][j] = color + "█" + "\u001B[0m";
                }
            }
            hasPoints(card, resCard);
        }
        return resCard;
    }
    
    public static String[][] createFrontPlayingCard(CardGold card){
        String[][] goldCard = createFrontPlayingCard((CardResource) card);
        hasPoints(card, goldCard); //TODO: mettere hasPoints nella create Card Res e mettere tutti gli obiettivi possibili
        hasCost(card, goldCard);
        return goldCard;
    }
    
    public static String[][] createFrontPlayingCard(CardStarting card) {
        String[][] startingCard = new String[3][7];
        int rows = startingCard.length;
        int cols = startingCard[0].length;
        //setto i corner
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if (isUpLeftCorner(i, j, rows, cols)) {
                    startingCard[i][j] = cornerScanner(card.getFront().getUpLeft().getDrawing());
                    startingCard[i][j+1] = " ";
                    j = j+1;
                } else if (isDownLeftCorner(i, j, rows, cols)) {
                    startingCard[i][j] = cornerScanner(card.getFront().getDownLeft().getDrawing());
                    startingCard[i][j+1] = " ";
                    j = j+1;
                } else if (isUpRightCorner(i, j, rows, cols)) {
                    startingCard[i][j] = cornerScanner(card.getFront().getUpRight().getDrawing());
                    startingCard[i][j-1] = " ";
                } else if (isDownRightCorner(i, j, rows, cols)) {
                    startingCard[i][j] = cornerScanner(card.getFront().getDownRight().getDrawing()) + reset;
                    startingCard[i][j-1] = " ";
                } else {
                    startingCard[i][j] = beige + "█" + "\u001B[0m";
                }
            }
        }
        //setto le risorse in mezzo alla carta.
        int numOfCentralResources = card.getSymbols().size();
        //startingCard [1][0] = "";
        if(numOfCentralResources == 1){
            startingCard[1][3] = cornerScanner(card.getSymbols().getFirst());
        } else if (numOfCentralResources == 2) {
            startingCard[0][3] = cornerScanner(card.getSymbols().getFirst());
            startingCard[1][3] = cornerScanner(card.getSymbols().get(1));
        } else if (numOfCentralResources == 3) {
            startingCard [1][6] = "";
            //startingCard[1][0] = " ";
            startingCard[0][3] = cornerScanner(card.getSymbols().getFirst());
            startingCard[1][3] = cornerScanner(card.getSymbols().get(1)) + beige + "█";
            startingCard[2][3] = cornerScanner(card.getSymbols().get(2));
        }
        return startingCard;
    }
    
    public static String[][] createBackPlayingCard(CardResource card){
        String[][] resCard = new String[3][7];
        int rows = resCard.length;
        int cols = resCard[0].length;
        String color = colorResource(card);
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                resCard[i][j] = color + "█";
            }
        }
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if (isUpLeftCorner(i, j, rows, cols)) {
                    resCard[i][j] = cornerScanner(card.getBack().getUpLeft().getDrawing());
                    resCard[i][j+1] = " ";
                    j = j+1;
                } else if (isDownLeftCorner(i, j, rows, cols)) {
                    resCard[i][j] = cornerScanner(card.getBack().getDownLeft().getDrawing());
                    resCard[i][j+1] = " ";
                    j = j+1;
                } else if (isUpRightCorner(i, j, rows, cols)) {
                    resCard[i][j] = cornerScanner(card.getBack().getUpRight().getDrawing());
                    resCard[i][j-1] = " ";
                } else if (isDownRightCorner(i, j, rows, cols)) {
                    resCard[i][j] = cornerScanner(card.getBack().getDownRight().getDrawing()) + reset;
                    resCard[i][j-1] = " ";
                } else {
                    resCard[i][j] = color + "█" + "\u001B[0m";
                }
            }
        }
        return resCard;
    }
    
    public static String[][] createBackPlayingCard(CardStarting card){
        String[][] startingCard = new String[3][7];
        int rows = startingCard.length;
        int cols = startingCard[0].length;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                startingCard[i][j] = beige + "█";
            }
        }
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if (isUpLeftCorner(i, j, rows, cols)) {
                    startingCard[i][j] = cornerScanner(card.getBack().getUpLeft().getDrawing());
                    startingCard[i][j+1] = " ";
                    j = j+1;
                } else if (isDownLeftCorner(i, j, rows, cols)) {
                    startingCard[i][j] = cornerScanner(card.getBack().getDownLeft().getDrawing());
                    startingCard[i][j+1] = " ";
                    j = j+1;
                } else if (isUpRightCorner(i, j, rows, cols)) {
                    startingCard[i][j] = cornerScanner(card.getBack().getUpRight().getDrawing());
                    startingCard[i][j-1] = " ";
                } else if (isDownRightCorner(i, j, rows, cols)) {
                    startingCard[i][j] = cornerScanner(card.getBack().getDownRight().getDrawing()) + reset;
                    startingCard[i][j-1] = " ";
                } else {
                    startingCard[i][j] = beige + "█" + "\u001B[0m";
                }
            }
        }
        return startingCard;
    }

    public static String[][] createBackResCardDeck(SuitEnum suit){
        String[][] resCard = new String[3][7];
        int rows = resCard.length;
        int cols = resCard[0].length;

        String color = switch(suit){
            case INSECT -> purple;
            case ANIMAL -> lightBlue;
            case FUNGI -> red;
            case PLANT -> green;
            default -> "_";
        };
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                resCard[i][j] = color + "█";
            }
        }
        resCard[0][0] = reset + "E"; //setto gli angoli a empty
        resCard[0][6] = reset + "E";
        resCard[2][0] = reset + "E";
        resCard[2][6] = reset + "E";
        return resCard;
    }

    public static String[][] createBackGoldCardDeck(SuitEnum suit){
        String[][] resCard = createBackResCardDeck(suit);
        resCard[1][3] = gold + "G";
        return resCard;
    }
    
    private static boolean isUpLeftCorner(int row, int col, int rows, int cols) {
        return (row == 0 && col == 0);
    }
    
    private static boolean isDownLeftCorner(int row, int col, int rows, int cols) {
        return (row == rows - 1 && col == 0);
    }
    
    private static boolean isUpRightCorner(int row, int col, int rows, int cols) {
        return (row == 0 && col == cols - 1);
    }
    
    private static boolean isDownRightCorner(int row, int col, int rows, int cols) {
        return (row == rows - 1 && col == cols-1);
    }
    
    public static String colorResource(CardResource card) {
        switch (card.getSymbol()) {
            case INSECT -> {
                return purple;
            }
            case ANIMAL -> {
                return lightBlue;
            }
            case FUNGI -> {
                return red;
            }
            case PLANT -> {
                return green;
            }
            default -> {
                return "_";
            }
        }
    }
    
    public static String cornerScanner(SuitEnum suit) {
        return switch (suit) {
            case ANIMAL -> lightBlue + "A" + reset;
            case INSECT -> purple + "I" + reset;
            case PLANT -> green + "P" + reset;
            case FUNGI -> red + "F" + reset;
            case QUILL -> gold + "Q" + reset;
            case INKWELL -> gold + "W" + reset;
            case MANUSCRIPT -> gold + "M" + reset;
            case EMPTY -> "E";
            default -> "N";
        };
    }
    
    private static void hasCost(Card card, String[][] mat) {
        String color = colorResource((CardResource) card);
        int costFungi = ((CardGold) card).getCostFungi();
        int costAnimal = ((CardGold) card).getCostAnimal();
        int costPlant = ((CardGold) card).getCostPlant();
        int costInsect = ((CardGold) card).getCostInsect();
        ArrayList<SuitEnum> costList = new ArrayList<SuitEnum>();
        while(costFungi > 0){
            costList.add(SuitEnum.FUNGI);
            costFungi--;
        }
        while(costAnimal > 0){
            costList.add(SuitEnum.ANIMAL);
            costAnimal--;
        }
        while(costPlant > 0){
            costList.add(SuitEnum.PLANT);
            costPlant--;
        }
        while(costInsect > 0){
            costList.add(SuitEnum.INSECT);
            costInsect--;
        }
        if(costList.size() == 3){
            int i = 0;
            for(int j = 2; j < 5; j++){
                mat[2][j] = cornerScanner(costList.get(i));
                i++;
            }
        }
        if(costList.size() == 4){
            int i = 0;
            for(int j = 2; j < 6; j++){
                mat[2][j] = cornerScanner(costList.get(i));
                i++;
            }
        }
        if(costList.size() == 5){
            int i = 0;
            for(int j = 1; j < 6; j++){
                mat[2][j] = cornerScanner(costList.get(i));
                i++;
            }
        }
        //return mat;
    }
    
    public static void hasPoints(CardResource card, String[][] mat) {
        int points = card.getPoints();
        if(points != 0) {
            mat[0][2] = " ";
            mat[0][3] = Integer.toString(points);
            if(card.getObjective() instanceof ObjectiveCountingGold && ((ObjectiveCountingGold) card.getObjective()).getCountInkwell() != 0){ // se ob è inkwell
                mat[0][4] = inkwell;
                mat[0][5] = " ";
            } else if(card.getObjective() instanceof ObjectiveCountingGold && ((ObjectiveCountingGold) card.getObjective()).getCountManuscript() != 0){ // se ob è inkwell
                mat[0][4] = manuscript;
                mat[0][5] = " ";
            } else if(card.getObjective() instanceof ObjectiveCountingGold && ((ObjectiveCountingGold) card.getObjective()).getCountQuill() != 0){ // se ob è inkwell
                mat[0][4] = quill;
                mat[0][5] = " ";
            } else if(card.getObjective() instanceof ObjectiveGoldCorners) {
                mat[0][4] = gold + "C" + reset;
                mat[0][5] = " ";
            } else {
                mat[0][4] = gold + "P" + reset;
                mat[0][5] = " ";
            }
        }
    }
    
    public static void printMatrix(String[][] mat){
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                System.out.print(mat[i][j]);
            }
            System.out.println();
        }
    }
    
    public static String[][] createFungiPositioning(){
        String[][] fungiPos = new String[3][7];
        int rows = fungiPos.length;
        int cols = fungiPos[0].length;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                fungiPos[i][j] = beige + "█" + reset;
            }
        }
        fungiPos[0][3] = "3";
        fungiPos[0][2] = red + "█" + reset;
        fungiPos[1][2] = red + "█" + reset;
        fungiPos[2][3] = green + "█" + reset;
        return fungiPos;
    }
    
    public static String[][] createPlantPositioning(){
        String[][] plantPos = new String[3][7];
        int rows = plantPos.length;
        int cols = plantPos[0].length;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                plantPos[i][j] = beige + "█" + reset;
            }
        }
        plantPos[0][3] = "3";
        plantPos[0][4] = green + "█" + reset;
        plantPos[1][4] = green + "█" + reset;
        plantPos[2][3] = purple + "█" + reset;
        return plantPos;
    }
    
    public static String[][] createAnimalPositioning(){
        String[][] animalPos = new String[3][7];
        int rows = animalPos.length;
        int cols = animalPos[0].length;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                animalPos[i][j] = beige + "█" + reset;
            }
        }
        animalPos[2][3] = "3";
        animalPos[2][2] = lightBlue + "█" + reset;
        animalPos[1][2] = lightBlue + "█" + reset;
        animalPos[0][3] = red + "█" + reset;
        return animalPos;
    }
    
    public static String[][] createInsectPositioning(){
        String[][] insPos = new String[3][7];
        int rows = insPos.length;
        int cols = insPos[0].length;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                insPos[i][j] = beige + "█" + reset;
            }
        }
        insPos[2][3] = "3";
        insPos[2][4] = purple + "█" + reset;
        insPos[1][4] = purple + "█" + reset;
        insPos[0][3] = lightBlue + "█" + reset;
        return insPos;
    }
    
    public static String[][] createCountGold(SuitEnum suit){
        String[][] countGold = new String[3][7];
        int rows = countGold.length;
        int cols = countGold[0].length;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                countGold[i][j] = beige + "█" + reset;
            }
        }
        countGold[0][3] = "2";
        countGold[1][4] = cornerScanner(suit);
        countGold[1][5] = cornerScanner(suit);
        return countGold;
    }
    
    public static String[][] createCountAll(){
        String[][] countGold = new String[3][7];
        int rows = countGold.length;
        int cols = countGold[0].length;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                countGold[i][j] = beige + "█" + reset;
            }
        }
        countGold[0][3] = "3";
        countGold[1][3] = quill;
        countGold[1][4] = inkwell;
        countGold[1][5] = manuscript;
        return countGold;
    }
    
    public static String[][] createDiagonal(SuitEnum suit){
        String[][] diag = new String[3][7];
        int rows = diag.length;
        int cols = diag[0].length;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                diag[i][j] = beige + "█" + reset;
            }
        }
        diag[0][3] = "2";
        if(suit == SuitEnum.FUNGI || suit == SuitEnum.ANIMAL){
            if(suit == SuitEnum.FUNGI){
                diag[2][2] = red + "█";
                diag[1][3] = red + "█";
                diag[0][4] = red + "█" + reset;
            } else {
                diag[2][2] = lightBlue + "█";
                diag[1][3] = lightBlue + "█";
                diag[0][4] = lightBlue + "█" + reset;
            }
        } else if (suit == SuitEnum.PLANT || suit == SuitEnum.INSECT) {
            if(suit == SuitEnum.PLANT){
                diag[0][2] = green + "█";
                diag[1][3] = green + "█";
                diag[2][4] = green + "█" + reset;
            } else {
                diag[0][2] = purple + "█";
                diag[1][3] = purple + "█";
                diag[2][4] = purple + "█" + reset;
            }
        }
        return diag;
    }
    
    public static String[][] createCountRes(SuitEnum suit){
        String[][] countRes = new String[3][7];
        int rows = countRes.length;
        int cols = countRes[0].length;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                countRes[i][j] = beige + "█" + reset;
            }
        }
        countRes[0][3] = "2";
        countRes[1][3] = cornerScanner(suit);
        countRes[2][2] = cornerScanner(suit);
        countRes[2][4] = cornerScanner(suit);
        return countRes;
    }
    
    public static String[][] createObjective(Objective objective){
        return new String[3][7];
    }

    public static String[][] createObjective(ObjectivePositioning objective){
        if(objective.getColorTwoCards() == SuitEnum.FUNGI){
            return createFungiPositioning();
        } else if(objective.getColorTwoCards() == SuitEnum.PLANT) {
            return createPlantPositioning();
        } else if(objective.getColorTwoCards() == SuitEnum.ANIMAL){
            return createAnimalPositioning();
        } else if(objective.getColorTwoCards() == SuitEnum.INSECT){
            return createInsectPositioning();
        }else{
            return null;
        }
    }

    public static String[][] createObjective(ObjectiveCountingGold objective){
        int countInkwell = objective.getCountInkwell();
        int countManuscript = objective.getCountManuscript();
        int countQuill = objective.getCountQuill();
        if((countInkwell == countManuscript)&&(countInkwell == countQuill)&&(countInkwell == 1)){
            return createCountAll();
        }
        else if((countQuill != 0)&&(countInkwell == 0)&&(countManuscript==0)){
            return createCountGold(SuitEnum.QUILL);
        }
        else if ((countQuill == 0)&&(countInkwell != 0)&&(countManuscript==0)) {
            return createCountGold(SuitEnum.INKWELL);
        }
        else if ((countQuill == 0)&&(countInkwell == 0)&&(countManuscript!=0)){
            return createCountGold(SuitEnum.MANUSCRIPT);
        } else{
            return null;
        }
    }

    public static String[][] createObjective(ObjectiveDiagonal objective){
        SuitEnum suit = objective.getColor();
        return createDiagonal(suit);
    }

    public static String[][] createObjective(ObjectiveCountingResource objective){
        SuitEnum suit = objective.getSymbol();
        return createCountRes(suit);
    }

}
