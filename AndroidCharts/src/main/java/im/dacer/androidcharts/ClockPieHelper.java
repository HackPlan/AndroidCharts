package im.dacer.androidcharts;

/**
 * Created by Dacer on 11/14/13.
 */
public class ClockPieHelper {

    int velocity = 5;
    private float start;
    private float end;
    private float targetStart;
    private float targetEnd;

    ClockPieHelper(float startDegree, float endDegree, ClockPieHelper targetPie) {
        start = startDegree;
        end = endDegree;
        targetStart = targetPie.getStart();
        targetEnd = targetPie.getEnd();
    }

    public ClockPieHelper(int startHour, int startMin, int endHour, int endMin) {
        start = 270 + startHour * 15 + startMin * 15 / 60;
        end = 270 + endHour * 15 + endMin * 15 / 60;
        while (end < start) {
            end += 360;
        }
    }

    public ClockPieHelper(int startHour, int startMin, int startSec, int endHour, int endMin,
            int endSec) {
        start = 270 + startHour * 15 + startMin * 15 / 60 + startSec * 15 / 3600;
        end = 270 + endHour * 15 + endMin * 15 / 60 + endSec * 15 / 3600;
        while (end < start) {
            end += 360;
        }
    }

    ClockPieHelper setTarget(float targetStart, float targetEnd) {
        this.targetStart = targetStart;
        this.targetEnd = targetEnd;
        return this;
    }

    ClockPieHelper setTarget(ClockPieHelper targetPie) {
        targetStart = targetPie.getStart();
        targetEnd = targetPie.getEnd();
        return this;
    }

    boolean isAtRest() {
        return (start == targetStart) && (end == targetEnd);
    }

    void update() {
        start = updateSelf(start, targetStart, velocity);
        end = updateSelf(end, targetEnd, velocity);
    }

    public float getSweep() {
        return end - start;
    }

    public float getStart() {
        return start;
    }

    public float getEnd() {
        return end;
    }

    private float updateSelf(float origin, float target, int velocity) {
        if (origin < target) {
            origin += velocity;
        } else if (origin > target) {
            origin -= velocity;
        }
        if (Math.abs(target - origin) < velocity) {
            origin = target;
        }
        return origin;
    }
}
