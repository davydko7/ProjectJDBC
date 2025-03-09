import java.util.List;

public class App {

    public static void main(String[] args) {

        // Получаем список студентов из базы
        List<Student> students = CRUDUtils.getStudentData("SELECT * FROM students");

        // Выводим список студентов в консоль

            System.out.println(students);

            Student student = new Student();
            student.setName("Olga");
            student.setSurname("Novikova");
            student.setCourse_name("AQA");

            CRUDUtils.saveStudent(student);

            System.out.println(CRUDUtils.saveStudent(student));

            System.out.println(CRUDUtils.updateStudent(2, "Automation QA"));

            System.out.println( CRUDUtils.deleteStudents(1));

        }
    }
