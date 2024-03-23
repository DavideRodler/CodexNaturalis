package model.decktameplate;

import java.util.List;

public class CardData {
    private List<String> suite;
    private String upright;
    private String upleft;
    private String downright;
    private String downleft;
    private String center;
    private List<Integer> numbers;
    private Integer points;
    private Integer costAnimal;
    private Integer costInsect;
    private Integer costFungi;
    private Integer costPlant;
    private String type;
    private String Objective;


    // getters and setters

    public List<String> getSuite() {
        return suite;
    }

    public String getUpright() {
        return upright;
    }

    public String getUpleft() {
        return upleft;
    }

    public String getDownright() {
        return downright;
    }

    public String getDownleft() {
        return downleft;
    }

    public String getCenter() {
        return center;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public Integer getPoints() {
        return points;
    }

    public Integer getCostAnimal() {
        return costAnimal;
    }

    public Integer getCostInsect() {
        return costInsect;
    }

    public Integer getCostFungi() {
        return costFungi;
    }

    public Integer getCostPlant() {
        return costPlant;
    }

    public String getType() {
        return type;
    }

    public void setSuite(List<String> suite) {
        this.suite = suite;
    }

    public void setUpright(String upright) {
        this.upright = upright;
    }

    public void setUpleft(String upleft) {
        this.upleft = upleft;
    }

    public void setDownright(String downright) {
        this.downright = downright;
    }

    public void setDownleft(String downleft) {
        this.downleft = downleft;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public void setCostAnimal(Integer costAnimal) {
        this.costAnimal = costAnimal;
    }

    public void setCostInsect(Integer costInsect) {
        this.costInsect = costInsect;
    }

    public void setCostFungi(Integer costFungi) {
        this.costFungi = costFungi;
    }

    public void setCostPlant(Integer costPlant) {
        this.costPlant = costPlant;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getObjective() {
        return Objective;
    }
}
