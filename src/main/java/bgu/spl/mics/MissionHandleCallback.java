package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Report;
import bgu.spl.mics.application.subscribers.M;

public class MissionHandleCallback implements Callback<MissionReceivedEvent> {

    private M m;

    public MissionHandleCallback(M m) {
        this.m = m;
    }
    @Override
    public void call(MissionReceivedEvent c) {
        Report report = new Report();
        Diary diary = Diary.getInstance();

        diary.incrementTotal();
        // edit the preliminary details of the report
        report.setMissionName(c.getMissionInfo().getMissionName());
        report.setM();
        report.setMoneypenny();
        report.setAgentsSerialNumbersNumber(c.getMissionInfo().getSerialAgentsNumbers());
        report.setGadgetName(c.getMissionInfo().getGadget());
        report.setTimeIssued(c.getMissionInfo().getTimeIssued());
        report.getTimeCreated(timetick);





        ;

    }
}
