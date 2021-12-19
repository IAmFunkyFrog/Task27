package com.homework;

import com.homework.model.Applicant;
import com.homework.model.Commission;

import java.io.*;

public class Main {

    private static Commission commission = new Commission();

    private static String convertCompetitionTypeToString(Applicant.CompetitionType competitionType) {
        switch (competitionType) {
            case Target:
                return "целевой";
            case FeeBased:
                return "внебюджетный";
            case StateFunded:
                return "бюджетный";
        }
        return "";
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("You must specify filename as first command line parameter");
            return;
        }

        File applicantsDatabase = new File(args[0]);
        if (!applicantsDatabase.exists()) {
            System.err.println("File not exists");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(applicantsDatabase))) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                Applicant applicant = Applicant.fromString(line);
                if (applicant == null) {
                    System.err.println("Malformed string in file " + args[0] + ":");
                    System.err.println(line);
                    return;
                } else commission.addApplicant(applicant);
            }
        } catch (IOException ex) {
            System.err.println("Unexpected IO exception");
            ex.printStackTrace();
            return;
        }

        System.out.println("Все арбитуриенты:");
        int ix = 1;
        for (Applicant applicant : commission.getAllApplicants()) {
            System.out.printf("%d: ФИО: %s, Тип конкурса: %s, Математика: %d, Русский: %d, Физика: %d%n", ix++, applicant.getName(), convertCompetitionTypeToString(applicant.getCompetitionType()), applicant.getMathScore(), applicant.getRusScore(), applicant.getPhysicsScore());
        }

        System.out.println("Арбитуриенты с правом на поступление");
        ix = 1;
        for (Applicant applicant : commission.getApplicantsWithRightToAdmission()) {
            System.out.printf("%d: ФИО: %s, Тип конкурса: %s, Математика: %d, Русский: %d, Физика: %d%n", ix++, applicant.getName(), convertCompetitionTypeToString(applicant.getCompetitionType()), applicant.getMathScore(), applicant.getRusScore(), applicant.getPhysicsScore());
        }
    }
}
