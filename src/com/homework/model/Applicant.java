package com.homework.model;

public class Applicant {

    public enum CompetitionType {
        Target,
        StateFunded,
        FeeBased
    }

    private final String name;
    private final CompetitionType competitionType;
    private final int mathScore;
    private final int rusScore;
    private final int physicsScore;

    public Applicant(String name, CompetitionType competitionType, int mathScores, int rusScores, int physicsScore) {
        this.name = name;
        this.competitionType = competitionType;
        this.mathScore = mathScores;
        this.rusScore = rusScores;
        this.physicsScore = physicsScore;
    }

    // Возвращает арбитуриента, если строка правильно отформатирована, иначе null
    public static Applicant fromString(String str) {
        String[] words = str.split(" ");
        if (words.length != 7) return null;
        // Достаем name (полное ФИО)
        String name = words[0] + " " + words[1] + " " + words[2];
        // Достаем competitionType
        CompetitionType competitionType;
        switch (words[3]) {
            case "целевой":
                competitionType = CompetitionType.Target;
                break;
            case "бюджетный":
                competitionType = CompetitionType.StateFunded;
                break;
            case "внебюджетный":
                competitionType = CompetitionType.FeeBased;
                break;
            default:
                return null;
        }
        // Достаем баллы mathScore, rusScore и physicsScore
        try {
            return new Applicant(name, competitionType, Integer.parseUnsignedInt(words[4]), Integer.parseUnsignedInt(words[5]), Integer.parseUnsignedInt(words[6]));
        } catch (Exception ex) {
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public CompetitionType getCompetitionType() {
        return competitionType;
    }

    public int getMathScore() {
        return mathScore;
    }

    public int getRusScore() {
        return rusScore;
    }

    public int getPhysicsScore() {
        return physicsScore;
    }
}
