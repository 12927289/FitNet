package com.example.james.fitnet.com.achievement.shaohang.fitnet;



public class AchievementsPrinted {

    private int _id;
    private String achievement_name;
    private String achievement_status;
    private String achievement_description;
    private int achievement_target;
    private int achievement_obtaincount;

    public AchievementsPrinted(int _id, String achievement_name,
                               String achievement_status,
                               String achievement_description,int achievement_target,int achievement_obtaincount) {
        this._id = _id;
        this.achievement_name = achievement_name;
        this.achievement_status = achievement_status;
        this.achievement_description = achievement_description;
        this.achievement_target = achievement_target;
        this.achievement_obtaincount = achievement_obtaincount;
    }

    public void setAchievement_target(int achievement_target) {
        this.achievement_target = achievement_target;
    }

    public final int getAchievement_target() {
        return achievement_target;
    }

    public int setAchievement_obtaincount(){
        achievement_obtaincount+=1;
        return achievement_obtaincount;}

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setAchievement_name(String achievement_name) {
        this.achievement_name = achievement_name;
    }

    public void setAchievement_status(String achievement_status) {
        this.achievement_status = achievement_status;
    }

    public void setAchievement_description(String achievement_description) {
        this.achievement_description = achievement_description;
    }

    public final int getAchievement_obtaincount(){return  achievement_obtaincount;}

    public final int get_id() {

        return _id;
    }

    public final String getAchievement_name() {
        return achievement_name;
    }

    public final String getAchievement_status() {
        return achievement_status;
    }

    public final String getAchievement_description() {
        return achievement_description;
    }

    @Override
    public String toString(){
        return achievement_name+" "+achievement_status;
    }

}
