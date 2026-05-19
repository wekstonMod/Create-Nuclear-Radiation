package net.Wekston.createnuclear_radiation.content.PlayerData;

import net.minecraft.nbt.CompoundTag;

public class PlayerLevelsManager {
    private double radiation = 0;
    private double gettingRadiation = 0;

    public double getRadiation() {
        return radiation;
    }

    public double gettingRadiation() {
        return gettingRadiation;
    }
    public void setgettingRadiation(double num) {
        this.gettingRadiation = num;
    }

    public void setRadiation(double num) {
        this.radiation = num;
    }

    public void addRadiation(double num) {
        this.radiation += num;
    }

    public CompoundTag saveToNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("radiation", radiation);
        tag.putDouble("gettingradiation", gettingRadiation);
        return tag;
    }


    public void loadFromNBT(CompoundTag tag) {
        if (tag.contains("radiation")) {
            this.radiation = tag.getDouble("radiation");
        }
        if (tag.contains("gettingradiation")) {
            this.gettingRadiation = tag.getDouble("gettingradiation");
        }
    }
}