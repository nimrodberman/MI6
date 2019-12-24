package bgu.spl.mics.application.passiveObjects;

import java.util.List;

public class BureaucracyPapers {
    private int MP;
    private List<String> agentsNames;
    private int QTime;
    private boolean gadetIsExist;
    private boolean agentsExists;

    public int getMP() {
        return MP;
    }

    public List<String> getAgentsNames() {
        return agentsNames;
    }

    public int getQTime() {
        return QTime;
    }

    public boolean isGadetIsExist() {
        return gadetIsExist;
    }

    public boolean isAgentsExists() {
        return agentsExists;
    }

    public void setMP(int MP) {
        this.MP = MP;
    }

    public void setAgentsNames(List<String> agentsNames) {
        this.agentsNames = agentsNames;
    }

    public void setQTime(int QTime) {
        this.QTime = QTime;
    }

    public void setGadetIsExist(boolean gadetIsExist) {
        this.gadetIsExist = gadetIsExist;
    }

    public void setAgentsExists(boolean agentsExists) {
        this.agentsExists = agentsExists;
    }
}
