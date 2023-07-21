import java.sql.*;

public class StudentManagementSystem {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    public static void main(String[] args) {
        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Create a new student
            Student newStudent = new Student("12345", "John Doe", "Computer Science", "A");

            // Add the student to the database
            addStudent(connection, newStudent);

            // Retrieve a student by ID
            Student retrievedStudent = getStudentById(connection, "12345");
            System.out.println("Retrieved Student: " + retrievedStudent);

            // Update the student's information
            retrievedStudent.setName("Jane Smith");
            updateStudent(connection, retrievedStudent);

            // Retrieve the updated student by ID
            Student updatedStudent = getStudentById(connection, "12345");
            System.out.println("Updated Student: " + updatedStudent);

            // Delete the student from the database
            deleteStudent(connection, "12345");

            // Close the database connection
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addStudent(Connection connection, Student student) throws SQLException {
        String query = "INSERT INTO students (id, name, course, grades) VALUES (?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, student.getId());
        statement.setString(2, student.getName());
        statement.setString(3, student.getCourse());
        statement.setString(4, student.getGrades());

        statement.executeUpdate();
        statement.close();
    }

    public static Student getStudentById(Connection connection, String id) throws SQLException {
        String query = "SELECT * FROM students WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, id);

        ResultSet resultSet = statement.executeQuery();
        Student student = null;
        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String course = resultSet.getString("course");
            String grades = resultSet.getString("grades");

            student = new Student(id, name, course, grades);
        }

        resultSet.close();
        statement.close();

        return student;
    }

    public static void updateStudent(Connection connection, Student student) throws SQLException {
        String query = "UPDATE students SET name = ?, course = ?, grades = ? WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, student.getName());
        statement.setString(2, student.getCourse());
        statement.setString(3, student.getGrades());
        statement.setString(4, student.getId());

        statement.executeUpdate();
        statement.close();
    }

    public static void deleteStudent(Connection connection, String id) throws SQLException {
        String query = "DELETE FROM students WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, id);

        statement.executeUpdate();
        statement.close();
    }
}

class Student {
    private String id;
    private String name;
    private String course;
    private String grades;

    public Student(String id, String name, String course, String grades) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.grades = grades;
    }

    // Getter and Setter methods for Student class attributes
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getGrades() {
        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", course='" + course + '\'' +
                ", grades='" + grades + '\'' +
                '}';
    }
}
