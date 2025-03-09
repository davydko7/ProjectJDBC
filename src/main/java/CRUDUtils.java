import javax.xml.soap.SOAPPart;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDUtils {
    private static final String INSERT_STUDENT = "INSERT INTO students(name, surname, course_name) VALUES (?, ?, ?);";
    private static final String UPDATE_STUDENT = "UPDATE students SET course_name = ? WHERE id = ?";
    private static final String DELETE_STUDENT = "DELETE FROM students WHERE id = ?";

    public static List<Student> getStudentData(String query) {
        List<Student> students = new ArrayList<>();

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String course_name = rs.getString("course_name");

                students.add(new Student(id, name, surname, course_name));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return students;
    }

    public static List<Student> saveStudent(Student student) {
        List<Student> students = new ArrayList<>();

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT)) {

            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getSurname());
            preparedStatement.setString(3, student.getCourse_name());
            preparedStatement.executeUpdate();

            // Второй запрос для получения всех студентов
            try (PreparedStatement allStudents = connection.prepareStatement("SELECT * FROM students");
                 ResultSet rs = allStudents.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String course_name = rs.getString("course_name");

                    students.add(new Student(id, name, surname, course_name));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return students;
    }

    public static List<Student> updateStudent (int studentId, String course_name) {
        List<Student> updateStudents = new ArrayList<>();

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STUDENT)) {

            preparedStatement.setString(1, course_name);
            preparedStatement.setInt(2, studentId);
            preparedStatement.executeUpdate();

            // Второй запрос для получения всех студентов
            try (PreparedStatement allStudents = connection.prepareStatement("SELECT * FROM students");
                 ResultSet rs = allStudents.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");


                    updateStudents.add(new Student(id, name, surname, course_name));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return updateStudents;
    }

    public static List<Student> deleteStudents (int studentId) {
        List<Student> updateStudents = new ArrayList<>();


        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STUDENT)) {

            preparedStatement.setInt(1, studentId);
            preparedStatement.executeUpdate();

            try (PreparedStatement allStudents = connection.prepareStatement("SELECT * FROM students");
                 ResultSet rs = allStudents.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    String course_name = rs.getString("course_name");


                    updateStudents.add(new Student(id, name, surname, course_name));
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return updateStudents;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    }

