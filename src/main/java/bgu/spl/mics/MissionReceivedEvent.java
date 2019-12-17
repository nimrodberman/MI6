package bgu.spl.mics;


import bgu.spl.mics.application.passiveObjects.MissionInfo;

public class MissionReceivedEvent implements Event {
    private MissionInfo missionInfo;

    public MissionReceivedEvent(MissionInfo missionInfo) {
        this.missionInfo = missionInfo;
    }

    public MissionInfo getMissionInfo() {
        return missionInfo;
    }
}
