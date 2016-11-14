package task16;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SalaryHtmlReportNotifier {

    private Connection connection;
    private JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    private final String HOST = "mail.google.com"

    public SalaryHtmlReportNotifier(Connection databaseConnection) {
        this.connection = databaseConnection;
        this.mailSender.setHost(this.HOST);
    }

    private ResultSet getData(String departmentId, LocalDate dateFrom, LocalDate dateTo) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT emp.id AS emp_id, emp.name AS amp_name, SUM(salary) AS salary FROM employee emp " +
                        "LEFT JOIN salary_payments sp ON emp.id = sp.employee_id " +
                        "WHERE emp.department_id = ? AND sp.date >= ? AND sp.date <= ? " +
                        "GROUP BY emp.id, emp.name");
        statement.setString(1, departmentId);
        statement.setDate(2, new java.sql.Date(dateFrom.toEpochDay()));
        statement.setDate(3, new java.sql.Date(dateTo.toEpochDay()));

        return statement.executeQuery();
    }

    private String createHtml(ResultSet salaryDataSet) throws SQLException {
        StringBuilder resultingHtml = new StringBuilder();
        resultingHtml.append("<html><body><table><tr><td>Employee</td><td>Salary</td></tr>");
        double totals = 0;
        while (salaryDataSet.next()) {
            resultingHtml
                    .append("<tr>")
                    .append("<td>").append(salaryDataSet.getString("emp_name")).append("</td>")
                    .append("<td>").append(salaryDataSet.getDouble("salary")).append("</td>")
                    .append("</tr>");
            totals += salaryDataSet.getDouble("salary");
        }
        resultingHtml.append("<tr><td>Total</td><td>").append(totals).append("</td></tr>");
        resultingHtml.append("</table></body></html>");
        return resultingHtml.toString();
    }



    public void generateAndSendReport(String departmentId, LocalDate dateFrom, LocalDate dateTo, String recipients) {
        try {
            // prepare statement with sql
            ResultSet results = getData(departmentId, dateFrom, dateTo);

            // write to html format string
            String resultingHtml = createHtml(results);

            // construct the message
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipients);
            // setting message text, last parameter 'true' says that it is HTML format
            helper.setText(resultingHtml.toString(), true);
            helper.setSubject("Monthly department salary report");
            // send the message
            mailSender.send(message);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}