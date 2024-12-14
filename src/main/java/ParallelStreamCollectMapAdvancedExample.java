import java.util.*;
import java.util.stream.*;

class Student {
    private String name;
    private Map<String, Integer> grades;

    public Student(String name, Map<String, Integer> grades) {
        this.name = name;
        this.grades = grades;
    }

    public Map<String, Integer> getGrades() {
        return grades;
    }
}

public class ParallelStreamCollectMapAdvancedExample {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("Student1", Map.of("Math", 90, "Physics", 85)),
                new Student("Student2", Map.of("Math", 95, "Physics", 88)),
                new Student("Student3", Map.of("Math", 88, "Chemistry", 92)),
                new Student("Student4", Map.of("Physics", 78, "Chemistry", 85))
        );

        // Используем Parallel Stream для агрегации данных
        Map<String, Double> subjectAverageMap = students.parallelStream() // Параллельный поток студентов
                .flatMap(student -> student.getGrades().entrySet().stream()) // Преобразуем список студентов в поток записей по каждому предмету
                .collect(Collectors.groupingBy( // Группируем по предмету
                        Map.Entry::getKey, // Ключ группировки - предмет
                        Collectors.averagingInt(Map.Entry::getValue) // Для каждого предмета вычисляем среднюю оценку
                ));

        // Вывод результата
        System.out.println("Average grades by subject:");
        subjectAverageMap.forEach((subject, avgGrade) ->
                System.out.println(subject + ": " + avgGrade));
    }
}
