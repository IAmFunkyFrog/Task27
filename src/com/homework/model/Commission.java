package com.homework.model;

import javax.swing.plaf.ListUI;
import java.util.ArrayList;

public class Commission {
    private final ArrayList<Applicant> applicants;

    public Commission() {
        applicants = new ArrayList<>();
    }

    public Applicant[] getAllApplicants() {
        return applicants.toArray(new Applicant[0]);
    }

    private static int applicantComparator(Applicant a1, Applicant a2) {
        return a2.getMathScore() + a2.getPhysicsScore() + a2.getRusScore() - (a1.getMathScore() + a1.getPhysicsScore() + a1.getRusScore());
    }

    public Applicant[] getApplicantsWithRightToAdmission() {
        ArrayList<Applicant> applicantsWithRightToAdmission = new ArrayList<>();
        ArrayList<Applicant> filteredApplicants = new ArrayList<>();
        for (Applicant applicant: applicants) {
            if(applicant.getMathScore() >= 39 && applicant.getRusScore() >= 40 && applicant.getPhysicsScore() >= 37) filteredApplicants.add(applicant);
        }
        // Подсчеты для целевых мест
        ArrayList<Applicant> targetApplicants = new ArrayList<>();
        for (Applicant applicant: filteredApplicants) {
            if(applicant.getCompetitionType() == Applicant.CompetitionType.Target) targetApplicants.add(applicant);
        }
        targetApplicants.sort(Commission::applicantComparator);
        if(targetApplicants.size() >= 2) targetApplicants.remove(targetApplicants.size() - 1);
        applicantsWithRightToAdmission.addAll(targetApplicants);
        // Подсчеты для бюджетных мест
        ArrayList<Applicant> stateFundedApplicants = new ArrayList<>();
        for (Applicant applicant: filteredApplicants) {
            if(applicant.getCompetitionType() == Applicant.CompetitionType.StateFunded) stateFundedApplicants.add(applicant);
        }
        stateFundedApplicants.sort(Commission::applicantComparator);
        int maxSum = -1;
        for (Applicant applicant: filteredApplicants) {
            maxSum = Math.max(maxSum, applicant.getMathScore() + applicant.getRusScore() + applicant.getPhysicsScore());
        }
        int ix = 0;
        while(ix < stateFundedApplicants.size() && stateFundedApplicants.get(ix).getMathScore() + stateFundedApplicants.get(ix).getRusScore() + stateFundedApplicants.get(ix).getPhysicsScore() > 0.75 * maxSum) ix++;
        applicantsWithRightToAdmission.addAll(filteredApplicants.subList(0, ix));
        // Подсчеты для внебюджетных мест
        ArrayList<Applicant> feeBasedApplicants = new ArrayList<>();
        for (Applicant applicant: filteredApplicants) {
            if(applicant.getCompetitionType() == Applicant.CompetitionType.FeeBased) feeBasedApplicants.add(applicant);
        }
        feeBasedApplicants.sort(Commission::applicantComparator);
        if(feeBasedApplicants.size() > stateFundedApplicants.size()) {
            int lastIndex = stateFundedApplicants.size();
            while(lastIndex < feeBasedApplicants.size() && applicantComparator(feeBasedApplicants.get(lastIndex), feeBasedApplicants.get(lastIndex - 1)) == 0) lastIndex++;
            applicantsWithRightToAdmission.addAll(feeBasedApplicants.subList(0, lastIndex));
        }
        else applicantsWithRightToAdmission.addAll(feeBasedApplicants);

        return applicantsWithRightToAdmission.toArray(new Applicant[0]);
    }

    public boolean addApplicant(Applicant applicant) {
        return applicants.add(applicant);
    }
}
