package net.Wekston.createnuclear_radiation.content.PlayerData;

import net.minecraft.nbt.CompoundTag;

public class PlayerLevelsManager {
    private double radiation;
    private double gettingRadiation;
    private double immunityLevel;
    private double immunityXp;

    public double getRadiation() {
        return radiation;
    }
    public void setRadiation(double num) {
        this.radiation = num;
    }
    public void addRadiation(double num) {
        this.radiation += num;
    }

    public double gettingRadiation() {
        return gettingRadiation;
    }
    public void settingRadiation(double num) {
        this.gettingRadiation = num;
    }


    public double getImmunity() {
        return immunityLevel;
    }
    public void setImmunity(double num) {
        this.immunityLevel = num;
    }
    public double getImmunityXP() {
        return immunityXp;
    }
    public void setImmunityXP(double num) {
        this.immunityXp = num;
    }

    public CompoundTag saveToNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("radiation", radiation);
        tag.putDouble("gettingradiation", gettingRadiation);
        tag.putDouble("immunitylevel", immunityLevel);
        tag.putDouble("immunityxp", immunityXp);
        return tag;
    }


    public void loadFromNBT(CompoundTag tag) {
        if (tag.contains("radiation")) {
            this.radiation = tag.getDouble("radiation");
        }
        if (tag.contains("gettingradiation")) {
            this.gettingRadiation = tag.getDouble("gettingradiation");
        }
        if (tag.contains("immunitylevel")) {
            this.immunityLevel = tag.getDouble("immunitylevel");
        }
        if (tag.contains("immunityxp")) {
            this.immunityXp = tag.getDouble("immunityxp");
        }
    }
}