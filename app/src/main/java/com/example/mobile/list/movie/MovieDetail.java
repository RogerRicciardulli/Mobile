package com.example.mobile.list.movie;

public class MovieDetail {
    private String OriginalTitle;
    private String OriginalTitleRomanised;

    private String Description;
    private String DirectorName;
    private String ProductorName;
    private String ReleaseDate;
    private String RunningTime;
    private String RtScore;

    public MovieDetail(String originalTitle, String originalTitleRomanised, String description, String directorName, String productorName, String releaseDate, String runningTime, String rtScore) {
        OriginalTitle = originalTitle;
        OriginalTitleRomanised = originalTitleRomanised;
        Description = description;
        DirectorName = directorName;
        ProductorName = productorName;
        ReleaseDate = releaseDate;
        RunningTime = runningTime;
        RtScore = rtScore;
    }

    public String getOriginalTitle() {
        return OriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        OriginalTitle = originalTitle;
    }

    public String getOriginalTitleRomanised() {
        return OriginalTitleRomanised;
    }

    public void setOriginalTitleRomanised(String originalTitleRomanised) {
        OriginalTitleRomanised = originalTitleRomanised;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDirectorName() {
        return DirectorName;
    }

    public void setDirectorName(String directorName) {
        DirectorName = directorName;
    }

    public String getProductorName() {
        return ProductorName;
    }

    public void setProductorName(String productorName) {
        ProductorName = productorName;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }

    public String getRunningTime() {
        return RunningTime;
    }

    public void setRunningTime(String runningTime) {
        RunningTime = runningTime;
    }

    public String getRtScore() {
        return RtScore;
    }

    public void setRtScore(String rtScore) {
        RtScore = rtScore;
    }
}
