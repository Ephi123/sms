package com.project1.sms.Service.imp;

import com.project1.sms.Service.ResultService;
import com.project1.sms.dto.CourseGradeDto;
import com.project1.sms.dto.SemesterResultDto;
import com.project1.sms.model.Course;
import com.project1.sms.model.Grade;
import com.project1.sms.model.Result;
import com.project1.sms.model.Student;
import com.project1.sms.repository.GradeRepo;
import com.project1.sms.repository.ResultRepo;
import com.project1.sms.repository.StudentRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class ResultImpl implements ResultService {
    private static final int RESULT_SCALE = 2;
    private final ResultRepo resultRepository;
    private final GradeRepo gradeRepository;
    private final StudentRepo studentRepository;
    @Override
    public SemesterResultDto calculateSemesterResult(String studentId, Integer academicYear, Integer semester) {


        Student student = findStudent(studentId);
        List<Grade> semesterGrades = gradeRepository.findSemesterGrades(student, academicYear, semester);
        List<Grade> cumulativeGrades = gradeRepository.findGradesThroughSemester(student, academicYear, semester);

        return buildSemesterResult(student, academicYear, semester, semesterGrades, cumulativeGrades);
    }



    @Override
    public List<SemesterResultDto> calculateAllSemesterResults(String studentId) {

        Student student = findStudent(studentId);
        List<Grade> allGrades = gradeRepository.findAllGradesForStudent(student);

        return allGrades.stream()
                .collect(Collectors.groupingBy(
                        grade -> new SemesterKey(
                                grade.getOffering().getAcademicYear(),
                                grade.getOffering().getSem()
                        ),
                        LinkedHashMap::new,
                        Collectors.toList()
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    SemesterKey key = entry.getKey();
                    List<Grade> cumulativeGrades = allGrades.stream()
                            .filter(grade -> isSameOrBefore(grade, key.academicYear(), key.semester()))
                            .toList();
                    return buildSemesterResult(
                            student,
                            key.academicYear(),
                            key.semester(),
                            entry.getValue(),
                            cumulativeGrades
                    );
                })
                .toList();
    }


    @Override
    public BigDecimal calculateCgpa(String studentId) {
        Student student = findStudent(studentId);
        return calculateGpa(gradeRepository.findAllGradesForStudent(student));

    }

    @Override
    public SemesterResultDto recalculateAndSaveSemesterResult(String studentId, Integer academicYear, Integer semester) {
        Student student = findStudent(studentId);
        List<Grade> semesterGrades = gradeRepository.findSemesterGrades(student, academicYear, semester);
        List<Grade> cumulativeGrades = gradeRepository.findGradesThroughSemester(student, academicYear, semester);
        SemesterResultDto calculatedResult = buildSemesterResult(
                student,
                academicYear,
                semester,
                semesterGrades,
                cumulativeGrades
        );

        Result result = resultRepository
                .findByStudentAndAcademicYearAndSemester(student, academicYear, semester)
                .orElseGet(Result::new);
        result.setStudent(student);
        result.setAcademicYear(academicYear);
        result.setSemester(semester);
        result.setGpa(calculatedResult.gpa());
        result.setCgpa(calculatedResult.cgpa());
        result.setSemesterCreditHours(calculatedResult.semesterCreditHours());
        result.setCumulativeCreditHours(calculatedResult.cumulativeCreditHours());
        resultRepository.save(result);

        return calculatedResult;


    }




    private Student findStudent(String studentId) {
        return studentRepository.findByUserUserId(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found: " + studentId));
    }


    private SemesterResultDto buildSemesterResult(Student student, Integer academicYear, Integer semester, List<Grade> semesterGrades, List<Grade> cumulativeGrades) {


        return new SemesterResultDto(
                student.getUserId(),
                academicYear,
                semester,
                calculateGpa(semesterGrades),
                calculateGpa(cumulativeGrades),
                calculateTotalCreditHours(semesterGrades),
                calculateTotalCreditHours(cumulativeGrades),
                semesterGrades.stream()
                        .sorted(Comparator.comparing(grade -> grade.getOffering().getCourse().getCourseCode()))
                        .map(this::toCourseGradeDto)
                        .toList()
        );

    }

    private Integer calculateTotalCreditHours(List<Grade> grades) {


        return grades.stream()
                .filter(this::isIncludedInGpa)
                .map(Grade::getOffering)
                .map(offering -> offering.getCourse().getCreditHour())
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();
    }

    private BigDecimal calculateGpa(List<Grade> grades) {

        int totalCreditHours = calculateTotalCreditHours(grades);
        if (totalCreditHours == 0) {
            return BigDecimal.ZERO.setScale(RESULT_SCALE, RoundingMode.HALF_UP);
        }

        BigDecimal totalQualityPoints = grades.stream()
                .filter(this::isIncludedInGpa)
                .map(grade -> gradePointFor(grade.getGrade())
                        .multiply(BigDecimal.valueOf(grade.getOffering().getCourse().getCreditHour())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalQualityPoints.divide(
                BigDecimal.valueOf(totalCreditHours),
                RESULT_SCALE,
                RoundingMode.HALF_UP
        );


    }

    private CourseGradeDto toCourseGradeDto(Grade grade) {
        Course course = grade.getOffering().getCourse();
        BigDecimal gradePoint = gradePointFor(grade.getGrade());
        BigDecimal qualityPoint = gradePoint.multiply(BigDecimal.valueOf(course.getCreditHour()));

        return new CourseGradeDto(
                grade.getId(),
                course.getCourseCode(),
                course.getCourseName(),
                course.getCreditHour(),
                grade.getGrade(),
                gradePoint.setScale(RESULT_SCALE, RoundingMode.HALF_UP),
                qualityPoint.setScale(RESULT_SCALE, RoundingMode.HALF_UP)
        );
    }

    private BigDecimal gradePointFor(String letterGrade) {
        return switch (normalizeGrade(letterGrade)) {
            case "A+", "A" -> BigDecimal.valueOf(4.00);
            case "A-" -> BigDecimal.valueOf(3.75);
            case "B+" -> BigDecimal.valueOf(3.50);
            case "B" -> BigDecimal.valueOf(3.00);
            case "B-" -> BigDecimal.valueOf(2.75);
            case "C+" -> BigDecimal.valueOf(2.50);
            case "C" -> BigDecimal.valueOf(2.00);
            case "C-" -> BigDecimal.valueOf(1.75);
            case "D" -> BigDecimal.valueOf(1.00);
            case "F" -> BigDecimal.ZERO;
            default -> BigDecimal.ZERO;
        };
    }

    private String normalizeGrade(String grade) {
        return grade == null ? "" : grade.trim().toUpperCase(Locale.ROOT);
    }

    private boolean isIncludedInGpa(Grade grade) {
        String normalizedGrade = normalizeGrade(grade.getGrade());
        return !normalizedGrade.isBlank()
                && !List.of("NG", "IC", "IA").contains(normalizedGrade)
                && grade.getOffering() != null
                && grade.getOffering().getCourse() != null
                && grade.getOffering().getCourse().getCreditHour() != null;
    }


    private boolean isSameOrBefore(Grade grade, Integer academicYear, Integer semester) {
        Integer gradeYear = grade.getOffering().getAcademicYear();
        Integer gradeSemester = grade.getOffering().getSem();
        return gradeYear < academicYear || (gradeYear.equals(academicYear) && gradeSemester <= semester);
    }


    private record SemesterKey(Integer academicYear, Integer semester) implements Comparable<SemesterKey> {

        @Override
        public int compareTo(SemesterKey other) {
            int yearComparison = academicYear.compareTo(other.academicYear);
            if (yearComparison != 0) {
                return yearComparison;
            }
            return semester.compareTo(other.semester);
        }
    }
}
