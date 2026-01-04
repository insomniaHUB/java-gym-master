package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        if (!timetable.containsKey(trainingSession.getDayOfWeek())) {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingSessions = new TreeMap<>();
            ArrayList<TrainingSession> trainingSessionList = new ArrayList<>();
            trainingSessionList.add(trainingSession);
            trainingSessions.put(trainingSession.getTimeOfDay(), trainingSessionList);
            timetable.put(trainingSession.getDayOfWeek(), trainingSessions);
        } else {
            if (timetable.get(trainingSession.getDayOfWeek()).containsKey(trainingSession.getTimeOfDay())) {
                timetable.get(trainingSession.getDayOfWeek()).get(trainingSession.getTimeOfDay()).add(trainingSession);

            } else {
                ArrayList<TrainingSession> trainingSessionList = new ArrayList<>();
                trainingSessionList.add(trainingSession);
                timetable.get(trainingSession.getDayOfWeek()).put(trainingSession.getTimeOfDay(), trainingSessionList);
            }
        }

    }

    public ArrayList<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        if (timetable.get(dayOfWeek) == null) {
            return new ArrayList<>();
        }
        ArrayList<TrainingSession> listOfTrainingSessions = new ArrayList<>();
        for (TimeOfDay time : timetable.get(dayOfWeek).navigableKeySet()) {
            listOfTrainingSessions.addAll(timetable.get(dayOfWeek).get(time));
        }
        return listOfTrainingSessions;
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        if (timetable.get(dayOfWeek).get(timeOfDay) == null) {
            return new ArrayList<>();
        }
        return timetable.get(dayOfWeek).get(timeOfDay);
    }

    public int getCountByCoaches(Coach neededCoach) {
        Map<Coach, Integer> countByCoach = new HashMap<>();

        for (DayOfWeek dayOfWeek : timetable.keySet()) {
            for (TimeOfDay time : timetable.get(dayOfWeek).navigableKeySet()) {
                for (TrainingSession session : timetable.get(dayOfWeek).get(time)) {
                    countByCoach.put(session.getCoach(), countByCoach.getOrDefault(session.getCoach(), 0) + 1);
                }
            }
        }

        List<CounterOfTrainings> counterOfTrainingsList = new ArrayList<>();

        for (Coach coach : countByCoach.keySet()) {
            counterOfTrainingsList.add(new CounterOfTrainings(coach, countByCoach.get(coach)));
        }

        Collections.sort(counterOfTrainingsList);

        for (CounterOfTrainings coach : counterOfTrainingsList) {
            if (neededCoach.equals(coach.getCoach())) {
                return coach.getCountOfTrainings();
            }
        }
        return 0;
    }

}
