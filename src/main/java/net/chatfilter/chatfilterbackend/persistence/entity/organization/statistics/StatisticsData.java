package net.chatfilter.chatfilterbackend.persistence.entity.organization.statistics;

import java.time.LocalDate;
import java.util.HashMap;

public class StatisticsData {

    private String organizationId;
    private String today;
    private int maxChecks;
    private HashMap<LocalDate, Integer> dailyChecks;

    public StatisticsData(String organizationId, String today, int maxChecks, HashMap<LocalDate, Integer> dailyChecks) {
        this.organizationId = organizationId;
        this.today = today;
        this.maxChecks = maxChecks;
        this.dailyChecks = dailyChecks;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public int getMaxChecks() {
        return maxChecks;
    }

    public void setMaxChecks(int maxChecks) {
        this.maxChecks = maxChecks;
    }

    public HashMap<LocalDate, Integer> getDailyChecks() {
        return dailyChecks;
    }

    public void setDailyChecks(HashMap<LocalDate, Integer> dailyChecks) {
        this.dailyChecks = dailyChecks;
    }
}
